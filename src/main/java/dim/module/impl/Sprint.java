package dim.module.impl;

import org.lwjgl.input.Keyboard;

import dim.event.impl.UpdateEvent;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.settings.KeyBinding;

public class Sprint extends dim.module.Module {
	public Sprint() {
		super("Sprint", "Sprints automatically", Keyboard.KEY_G);
	}
	
	@Listen
	public void onUpdate(UpdateEvent event) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
	}

	@Override
	public void onDisable() {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
	}
}