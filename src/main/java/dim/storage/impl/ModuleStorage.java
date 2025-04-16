package dim.storage.impl;

import dim.event.impl.KeyEvent;
import dim.module.Category;
import dim.module.Module;
import dim.module.impl.*;
import dim.screen.opengl.ClickGUI;
import dim.storage.Storage;
import dim.util.game.IMinecraft;
import io.github.nevalackin.radbus.Listen;

import java.util.Set;
import java.util.stream.Collectors;

public class ModuleStorage extends Storage<Module> implements IMinecraft {

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
	
	@Listen
	public void onKey(KeyEvent event) {
		if (event.key == 0x36) {
			mc.displayGuiScreen(new ClickGUI());
		}

		getHashSet().forEach(m -> {
			if (event.key == m.key) {
				m.setEnabled(!m.isEnabled());
			}
		});
	}
}