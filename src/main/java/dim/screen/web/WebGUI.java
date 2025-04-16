package dim.screen.web;

import fi.iki.elonen.NanoHTTPD;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class WebGUI extends NanoHTTPD {
    private int myVariable = 0;

    public WebGUI(int port) throws IOException {
        super(port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("Server running at http://localhost:8080/");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Method method = session.getMethod();

        if (Method.POST.equals(method)) {
            try {
                session.parseBody(new HashMap<>());
            } catch (Exception e) {
                return newFixedLengthResponse("Error parsing POST data.");
            }

            Map<String, String> params = session.getParms();
            if (params.containsKey("newValue")) {
                try {
                    myVariable = Integer.parseInt(params.get("newValue"));
                } catch (NumberFormatException ignored) {}
            }
            return newFixedLengthResponse("Value updated.");
        }

        if (uri.equals("/style.css")) {
            return serveResource("/web/style.css", "text/css");
        }

        String html = readResource("/web/index.html");
        if (html != null) {
            html = html.replace("{{value}}", String.valueOf(myVariable));
            return newFixedLengthResponse(Response.Status.OK, "text/html", html);
        }

        return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "404 Not Found");
    }

    private String readResource(String path) {
        try (InputStream in = getClass().getResourceAsStream(path)) {
            if (in == null) return null;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return new String(out.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }

    private Response serveResource(String path, String mime) {
        try (InputStream in = getClass().getResourceAsStream(path)) {
            if (in == null) return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "404 Not Found");
            return newFixedLengthResponse(Response.Status.OK, mime, in, in.available());
        } catch (IOException e) {
            return newFixedLengthResponse("Error loading resource.");
        }
    }
}
