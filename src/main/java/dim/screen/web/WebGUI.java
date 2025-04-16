package dim.screen.web;

import dim.DimClient;
import dim.module.Category;
import dim.module.Module;
import fi.iki.elonen.NanoHTTPD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class WebGUI extends NanoHTTPD {
    private final Logger LOGGER = LogManager.getLogger("WebGUI");

    public WebGUI(int port) throws IOException {
        super(port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        LOGGER.info("Web ClickGUI running at http://localhost:8080/");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();

        if (uri.startsWith("/toggle")) {
            String moduleName = session.getParms().get("module");
            Module mod = DimClient.INSTANCE.moduleStorage.getModuleByName(moduleName);
            if (mod != null) mod.setEnabled(!mod.isEnabled());
            return newFixedLengthResponse("Toggled");
        }

        if (uri.equals("/style.css")) {
            return serveResource("/dim/style.css", "text/css");
        }

        String htmlTemplate = readResource("/dim/index.html");
        if (htmlTemplate == null) return newFixedLengthResponse("Failed to load page");

        StringBuilder categoriesHtml = new StringBuilder();
        for (Category cat : Category.values()) {
            StringBuilder modulesHtml = new StringBuilder();
            for (Module mod : DimClient.INSTANCE.moduleStorage.getFromCategory(cat)) {
                String enabledClass = mod.isEnabled() ? "enabled" : "";
                modulesHtml.append(String.format(
                        "<div class='module %s' onclick=\"toggleModule('%s')\">%s</div>",
                        enabledClass, mod.name, mod.name.toLowerCase()
                ));
            }
            categoriesHtml.append(String.format(
                    "<div class='category'>" +
                            "<div class='category-header'>%s</div>%s</div>",
                    cat.name().toLowerCase(), modulesHtml
            ));
        }

        String finalHtml = htmlTemplate.replace("{{categoryComponents}}", categoriesHtml.toString());
        return newFixedLengthResponse(Response.Status.OK, "text/html", finalHtml);
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
        InputStream in = getClass().getResourceAsStream(path);
        if (in == null) {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "404 Not Found");
        }

        try {
            return newFixedLengthResponse(Response.Status.OK, mime, in, in.available());
        } catch (IOException e) {
            return newFixedLengthResponse("Error loading resource.");
        }
    }

}
