package dim.screen.web;

import dim.DimClient;
import dim.module.Category;
import dim.module.Module;
import dim.util.client.ColorUtil;
import fi.iki.elonen.NanoHTTPD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WebGUI extends NanoHTTPD {
    private final Logger LOGGER = LogManager.getLogger("WebGUI");

    public WebGUI(int port) throws IOException {
        super(port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        String ip = InetAddress.getLocalHost().getHostAddress();
        LOGGER.info("Web ClickGUI running at http://" + ip + ":8080/");
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

        if (uri.equals("/font.otf")) {
            return serveResource("/dim/font.otf", "font/otf");
        }

        String htmlTemplate = readResource("/dim/index.html");
        if (htmlTemplate == null) return newFixedLengthResponse("Failed to load page");

        StringBuilder categoriesHtml = new StringBuilder();
        for (Category cat : Category.values()) {
            StringBuilder modulesHtml = new StringBuilder();
            List<Module> modules = new ArrayList<>(DimClient.INSTANCE.moduleStorage.getFromCategory(cat));

            boolean hasModules = !modules.isEmpty();

            for (Module mod : modules) {
                String style = "";
                if (mod.isEnabled()) {
                    Color categoryColor = ColorUtil.getColorFromCategory(cat);
                    String hexColor = String.format("#%02x%02x%02x", categoryColor.getRed(), categoryColor.getGreen(), categoryColor.getBlue());
                    style = " style='background-color: " + hexColor + "'";
                }
                modulesHtml.append(String.format(
                        "<div class='module%s' onclick=\"toggleModule('%s')\"%s>%s</div>",
                        mod.isEnabled() ? " enabled" : "", mod.name, style, mod.name.toLowerCase()
                ));
            }

            Color categoryColor = ColorUtil.getColorFromCategory(cat);
            String hexColor = String.format("#%02x%02x%02x", categoryColor.getRed(), categoryColor.getGreen(), categoryColor.getBlue());

            String categoryStyle = "style='border: 2px solid " + hexColor + "'";

            categoriesHtml.append(String.format(
                    "<div class='category' %s>" +
                            "<div class='category-header'>%s</div>%s</div>",
                    categoryStyle, cat.name().toLowerCase(), modulesHtml
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
