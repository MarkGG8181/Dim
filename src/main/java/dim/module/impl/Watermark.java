package dim.module.impl;

import java.awt.Color;

import dim.module.Category;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import dim.event.impl.Render2DEvent;
import dim.util.game.RenderUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.Minecraft;

public class Watermark extends dim.module.Module {
	public Watermark() {
		super("Watermark", "Displays a watermark", Keyboard.KEY_L, Category.RENDER);
		setEnabled(true);
	}
	
	@Listen
	public void onRender(Render2DEvent event) {
		String fps = "fps: " + Minecraft.func_175610_ah();
		float width = 20 + mc.fontRendererObj.getStringWidth(fps);
		RenderUtil.rectangle(2, 2, width, 21, true, new Color(0, 0, 0, 150));

		GL11.glPushMatrix();
		GL11.glScaled(2.0, 2.0, 2.0);
		mc.fontRendererObj.func_175063_a("D", 5 / 2f, 5 / 2f, Color.CYAN);
		GL11.glPopMatrix();
		
		mc.fontRendererObj.func_175063_a(fps, 20, 5, -1);
	}
}