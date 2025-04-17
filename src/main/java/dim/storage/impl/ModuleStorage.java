package dim.storage.impl;

import dim.event.impl.KeyEvent;
import dim.module.Category;
import dim.module.Module;
import dim.module.impl.*;
import dim.screen.opengl.ClickGUI;
import dim.storage.Storage;
import dim.util.game.IMinecraft;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.Sys;

import java.util.Set;
import java.util.stream.Collectors;

public class ModuleStorage extends Storage<Module> implements IMinecraft {

	public Module currentModule;

	@Override
	public void init() {
		super.init();
		
		getHashSet().add(new Sprint());
		getHashSet().add(new Watermark());
		getHashSet().forEach(Module::onInitialize);
	}

	public Set<Module> getFromCategory(Category category) {
		return getHashSet().stream().filter(m -> m.category == category).collect(Collectors.toSet());
	}

	public Module getModuleByName(String name) {
		return getHashSet().stream().filter(m -> m.name.equalsIgnoreCase(name)).findFirst().orElse(null);
	}
	
	@Listen
	public void onKey(KeyEvent event) {
		if (event.key == 0x36) {
			mc.displayGuiScreen(new GuiChat());
			Sys.openURL("http://localhost:8080");
		} else if (event.key == 0x9D) {
			mc.displayGuiScreen(new ClickGUI());
		}

		getHashSet().forEach(m -> {
			if (event.key == m.key) {
				m.setEnabled(!m.isEnabled());
			}
		});
	}
}