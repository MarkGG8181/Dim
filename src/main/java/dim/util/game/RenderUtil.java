package dim.util.game;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;

import java.awt.Color;

public class RenderUtil {

    public static void color(Color color) {
        GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
    }
    
    public static void start() {
        GlStateManager.pushMatrix();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GlStateManager.func_179090_x();
        GlStateManager.disableCull();

        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
    }

    public static void stop() {
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();

        GlStateManager.enableCull();
        GlStateManager.func_179098_w();

        GlStateManager.disableBlend();
        color(Color.WHITE);
        GlStateManager.popMatrix();
    }

    public static void rectangle(double x, double y, double width, double height, Color color) {
        rectangle(x, y, width, height, true, color);
    }
    
    public static void rectangle(double x, double y, double width, double height, boolean filled, Color color) {
        start();

        if (color != null)
            color(color);

        GL11.glLineWidth(2.0f);

        GL11.glBegin(filled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINE_LOOP);

        {
        	GL11.glVertex2d(x, y);
        	GL11.glVertex2d(x + width, y);
        	GL11.glVertex2d(x + width, y + height);
        	GL11.glVertex2d(x, y + height);
        }

        GL11.glEnd();
        stop();
    }

}
