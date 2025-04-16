package dim.screen.opengl.clickGUI.impl;

import dim.module.Module;
import dim.screen.Colors;
import dim.screen.opengl.clickGUI.Component;
import dim.util.client.ColorUtil;
import dim.util.game.RenderUtil;
import dim.util.math.SizeVector2d;

import javax.vecmath.Vector2d;
import java.util.ArrayList;
import java.util.List;

public class ModuleComponent extends Component {
    public final CategoryComponent parent;
    public final Module module;
    public final List<Component> children = new ArrayList<>();

    public ModuleComponent(CategoryComponent parent, Module module, Vector2d position, SizeVector2d size) {
        super(position, size);
        this.parent = parent;
        this.module = module;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        RenderUtil.rectangle(position.x, position.y, size.width, size.height, module.isEnabled() ? ColorUtil.getColorFromCategory(parent.category) : Colors.CATEGORY_PANEL.color);
        float textY = (float) (position.y + (size.height - mc.fontRendererObj.FONT_HEIGHT) / 2f);
        mc.fontRendererObj.drawString(module.name.toLowerCase(), (int) (position.x + 5), textY + 1, -1);
    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton) {
    }
}