package dim.module;

import dim.DimClient;
import dim.setting.Setting;
import dim.util.game.IMinecraft;

import java.util.HashSet;

public abstract class Module implements IMinecraft {
	public final String name, description;
	public final Category category;
	public int key;
	
	private boolean enabled, expanded;

	public final HashSet<Setting<?>> settings = new HashSet<>();
	
	public Module(String name, String description, int key, Category category) {
		this.name = name;
		this.description = description;
		this.key = key;
		this.category = category;

		DimClient.INSTANCE.moduleStorage.currentModule = this;
	}
	
	public Module(String name, String description, Category category) {
		this(name, description, 0, category);
	}

	public void onEnable() {}
	public void onDisable() {}
	public void onInitialize() {}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		
		if (enabled) {
			onEnable();
			DimClient.BUS.subscribe(this);
		} else {
			DimClient.BUS.unsubscribe(this);
			onDisable();
		}
	}

	public void setExpanded(boolean expanded) {
		if (this.settings.isEmpty()) {
			return;
		}

		this.expanded = expanded;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public boolean isExpanded() {
		return !this.settings.isEmpty() && this.expanded;
	}

	public Setting<?> getSettingByName(String name) {
		for (Setting<?> setting : settings) {
			if (setting.name.equalsIgnoreCase(name)) return setting;
		}
		return null;
	}
}