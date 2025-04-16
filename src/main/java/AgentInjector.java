import java.lang.instrument.Instrumentation;

import dim.DimClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dim.util.ClientType;
import dim.util.mappings.ApplicationTransformer;
import dim.util.mappings.BasicMappings;

public class AgentInjector {

	private static final Logger LOGGER = LogManager.getLogger("Injector");

	public static void premain(String agentArgs) throws Exception {
		DimClient.TYPE = ClientType.INJECTION;
		LOGGER.info("PreMain");

		Instrumentation inst = InstrumentationManager.getInstrumentation();

		String[] classNamesToTransform = { "net.minecraft.client.Minecraft"};
		
		for (String className : classNamesToTransform) {
			className = BasicMappings.getClass(className);
			
            Class<?> targetClass = Class.forName(className);
            ClassLoader classLoader = targetClass.getClassLoader();

            ApplicationTransformer transformer = new ApplicationTransformer(className, classLoader);
            inst.addTransformer(transformer, true);
            inst.retransformClasses(targetClass);
        }
	}

	public static void agentmain(String agentArgs) {
		LOGGER.info("AgentMain");
	}

}
