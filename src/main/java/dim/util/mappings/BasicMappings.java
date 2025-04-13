package dim.util.mappings;

import java.util.HashMap;
import java.util.Map;

public class BasicMappings {

	public static final Map<String, String> classNames = new HashMap<>();
	public static final Map<String, String> methodNames = new HashMap<>();
	public static final Map<String, String> reversedClassNames = new HashMap<>();
	public static final Map<String, String> reversedMethodNames = new HashMap<>();
	
	static {
		classNames.put("net.minecraft.client.Minecraft", "bsu");
		classNames.put("net.minecraft.client.entity.EntityPlayerSP", "cio");
		classNames.put("net.minecraft.client.gui.GuiIngame", "btz");
		
		methodNames.put("net.minecraft.client.Minecraft#startGame", "bsu#aj");
		methodNames.put("net.minecraft.client.Minecraft#runTick", "bsu#r");
		methodNames.put("net.minecraft.client.entity.EntityPlayerSP#onUpdate", "cio#s_");
		methodNames.put("net.minecraft.client.gui.GuiIngame#func_175180_a", "btz#a");
		
		for (Map.Entry<String, String> entry : classNames.entrySet()) {
			reversedClassNames.put(entry.getValue(), entry.getKey());
		}

		for (Map.Entry<String, String> entry : methodNames.entrySet()) {
			reversedMethodNames.put(entry.getValue(), entry.getKey());
		}
	}
	
	public static String getClass(String unobfuscatedClassName) {
		return classNames.get(unobfuscatedClassName);
	}
	
	public static String getMethod(String unobfuscatedMethodName) {
		return methodNames.get(unobfuscatedMethodName);
	}
	
	public static String getClassUnobfuscated(String obfuscatedClassName) {
		return reversedClassNames.get(obfuscatedClassName);
	}

	public static String getMethodUnobfuscated(String obfuscatedMethodName) {
		return reversedMethodNames.get(obfuscatedMethodName);
	}
}