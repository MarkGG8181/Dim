package dim.screen.opengl.clickGUI.impl;

import dim.DimClient;
import dim.module.Category;
import dim.module.Module;
import dim.screen.Colors;
import dim.screen.opengl.clickGUI.Component;
import dim.util.client.ColorUtil;
import dim.util.game.RenderUtil;
import dim.util.math.SizeVector2d;

import javax.vecmath.Vector2d;
import java.util.ArrayList;
import java.util.List;

public class CategoryComponent extends Component {
    public final Category category;
    public SizeVector2d parentSize = new SizeVector2d(size.width, 15);

    public CategoryComponent(Category category, Vector2d position, SizeVector2d size) {
        super(position, size);
        this.category = category;


        double yOffset = position.y + size.height;
        for (Module mod : DimClient.INSTANCE.moduleStorage.getFromCategory(category)) {
            Vector2d modPosition = new Vector2d(position.x, yOffset);
            children.add(new ModuleComponent(this, mod, modPosition, parentSize));
            yOffset += parentSize.height;
        }
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        double newHeight = 0;
        if (!children.isEmpty()) {
            for (Component component : children) {
                if (!component.children.isEmpty() && component instanceof ModuleComponent && ((ModuleComponent) component).module.isExpanded()) {
                    for (Component component1 : component.children) {
                        newHeight += component1.size.height;
                    }
                }
                newHeight += component.size.height;
            }
        }

        RenderUtil.rectangle(position.x, position.y, size.width, size.height + newHeight, false, ColorUtil.getColorFromCategory(category));
        RenderUtil.rectangle(position.x, position.y, size.width, size.height, Colors.CATEGORY_PANEL.color);
        float textY = (float) (position.y + (size.height - mc.fontRendererObj.FONT_HEIGHT) / 2f);
        mc.fontRendererObj.drawString(category.name().toLowerCase(), (int) (position.x + 5), textY + 1, -1);

        for (Component component : children) {
            component.draw(mouseX, mouseY);
        }
    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton) {
        for (Component component : children) {
            component.click(mouseX, mouseY, mouseButton);
        }
    }
}