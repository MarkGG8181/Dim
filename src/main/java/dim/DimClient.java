package dim;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import dim.event.Event;
import dim.storage.impl.ModuleStorage;
import dim.util.ClientType;
import io.github.nevalackin.radbus.PubSub;

public class DimClient {

	public static final DimClient INSTANCE = new DimClient();
	public static ClientType TYPE = ClientType.STANDALONE;
	public static final PubSub<Event> BUS = PubSub.newInstance(System.err::println);
    public static final Logger LOGGER = LogManager.getLogger("Dim");
    
    public final ModuleStorage moduleStorage = new ModuleStorage();

	public void start() {
		LOGGER.info("{} Dim Client...", TYPE == ClientType.INJECTION ? "Injecting" : "Launching");
		Display.setTitle("Dim 1.8 [" + TYPE.name().toLowerCase() + "]");
		
		moduleStorage.init();
	}
	
	/**
	 * HOOKS:
	 * 
	 * method @see Minecraft#startGame() at tail = DimClient#start() ✅
	 * method @see EntityPlayerSP#onUpdate() inside if(isBlockLoaded) = new UpdateEvent().post();
	 * method @see GuiIngame#func_175180_a(param1) after showDebugInfo if check = new Render2DEvent(param1, param2).post();
	 * method @see Minecraft#runTick() inside if (getEventKeyState()) = KeyEvent keyEv = new KeyEvent(var1); keyEv.post(); if (keyEv.cancelled) return;
	 */
} 
