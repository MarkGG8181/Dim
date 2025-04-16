package dim.util.mappings;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dim.DimClient;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;

public class ApplicationTransformer implements ClassFileTransformer {

	private static final Logger LOGGER = LogManager.getLogger("Transformer");

	private String targetClassName;
	private String mappedClassName;
	private ClassLoader targetClassLoader;

	public ApplicationTransformer(String targetClassName, ClassLoader targetClassLoader) {
		this.targetClassName = targetClassName;
		this.targetClassLoader = targetClassLoader;

		this.mappedClassName = BasicMappings.getClassUnobfuscated(targetClassName);
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		byte[] byteCode = classfileBuffer;

		String finalTargetClassName = this.targetClassName.replaceAll("\\.", "/");
		if (!className.equals(finalTargetClassName)) {
			return byteCode;
		}

		if (loader.equals(targetClassLoader)) {
			try {
				LOGGER.info("Transforming class: {}", finalTargetClassName);
			    LOGGER.info("Mapped class name: {}", mappedClassName);
			    
			    CtClass ctClass = getClassPool().get(targetClassName);
			    
			    if (mappedClassName.equals("net.minecraft.client.Minecraft")) {
				    String startGame = "aj";
				    String runTick = "r";
				    
				    {
				    	//startGame
					    LOGGER.info("Injecting into method: {}", startGame);
					    CtMethod method = ctClass.getDeclaredMethod(startGame);
					    method.insertAfter("dim.DimClient.INSTANCE.start();");
					}
			    }
			    
			    return ctClass.toBytecode();
			} catch (Exception e) {
				LOGGER.info("Exception occured while transforming {}", mappedClassName, e);
			}
		}
		return byteCode;
	}
	
	private ClassPool getClassPool() {
        // Return a ClassPool instance for loading and manipulating classes
        ClassPool pool = ClassPool.getDefault();
        pool.appendClassPath(new LoaderClassPath(targetClassLoader));
        return pool;
    }
}