package dim.screen.opengl.clickGUI;

import dim.util.game.IMinecraft;
import dim.util.math.SizeVector2d;

import javax.vecmath.Vector2d;
import java.util.ArrayList;
import java.util.List;

public abstract class Component implements IMinecraft {
    public Vector2d position;
    public final SizeVector2d size;

    public final List<Component> children = new ArrayList<>();

    public Component(Vector2d position, SizeVector2d size) {
        this.position = position;
        this.size = size;
    }

    public abstract void draw(int mouseX, int mouseY);
    public abstract void click(int mouseX, int mouseY, int mouseButton);
}