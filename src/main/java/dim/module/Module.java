package dim.module;

import dim.DimClient;
import dim.util.game.IMinecraft;

public abstract class Module implements IMinecraft {
	public final String name, description;
	public final Category category;
	public int key;
	
	private boolean enabled;
	
	public Module(String name, String description, int key, Category category) {
		this.name = name;
		this.description = description;
		this.key = key;
		this.category = category;
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
	
	public boolean isEnabled() {
		return this.enabled;
	}
}