package dim.storage.impl;

import dim.event.impl.KeyEvent;
import dim.module.impl.*;
import dim.storage.Storage;
import io.github.nevalackin.radbus.Listen;

public class ModuleStorage extends Storage<dim.module.Module> {

	@Override
	public void init() {
		super.init();
		
		getHashSet().add(new Sprint());
		getHashSet().add(new Watermark());
		getHashSet().forEach(m -> m.onInitialize());
	}
	
	@Listen
	public void onKey(KeyEvent event) {
		getHashSet().forEach(m -> {
			if (event.key == m.key) {
				m.setEnabled(!m.isEnabled());
			}
		});
	}
}