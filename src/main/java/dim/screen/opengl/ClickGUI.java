package dim.screen.opengl;

import dim.module.Category;
import dim.screen.opengl.clickGUI.impl.CategoryComponent;
import dim.util.math.SizeVector2d;
import net.minecraft.client.gui.GuiScreen;

import javax.vecmath.Vector2d;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGUI extends GuiScreen {

    private final List<CategoryComponent> categories = new ArrayList<>(Category.values().length);

    private static final double PADDING = 5;
    private final SizeVector2d catSize = new SizeVector2d(100, 18);

    @Override
    public void initGui() {
        if (categories.isEmpty()) {
            double xOffset = PADDING;

            for (Category cat : Category.values()) {
                Vector2d catPosition = new Vector2d(xOffset, PADDING);
                CategoryComponent categoryComponent = new CategoryComponent(cat, catPosition, catSize);

                categories.add(categoryComponent);
                xOffset += catSize.width + PADDING;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        categories.forEach(c -> c.draw(mouseX, mouseY));

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        categories.forEach(c -> c.click(mouseX, mouseY, mouseButton));

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
