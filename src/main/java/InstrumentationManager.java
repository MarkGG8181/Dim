import java.lang.instrument.Instrumentation;

public class InstrumentationManager {
    public static native Instrumentation getInstrumentation();
}
