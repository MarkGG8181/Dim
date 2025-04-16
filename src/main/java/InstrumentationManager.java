import java.io.*;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;

public class InstrumentationManager {
    public static native Instrumentation getInstrumentation();

    static {
        loadDllFromInputStream(InstrumentationManager.class.getResourceAsStream("instrument.dll"));
    }

    public static void loadDllFromInputStream(InputStream inputStream) {
        try {
            File tempFile = File.createTempFile("agent", ".dll");
            tempFile.deleteOnExit();

            try (OutputStream outputStream = Files.newOutputStream(tempFile.toPath())) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to write to temporary file", e);
            }

            System.load(tempFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to create or load DLL", e);
        }
    }
}
