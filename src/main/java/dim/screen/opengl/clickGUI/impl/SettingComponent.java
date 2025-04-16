package dim.screen.opengl.clickGUI.impl;

import dim.screen.Colors;
import dim.screen.opengl.clickGUI.Component;
import dim.setting.Setting;
import dim.util.game.RenderUtil;
import dim.util.math.SizeVector2d;

import javax.vecmath.Vector2d;

public class SettingComponent extends Component {
    public final ModuleComponent parent;
    public final Setting<?> setting;

    public SettingComponent(ModuleComponent parent, Setting<?> setting, Vector2d position, SizeVector2d size) {
        super(position, size);
        this.parent = parent;
        this.setting = setting;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        RenderUtil.rectangle(position.x, position.y, size.width, size.height, Colors.MODULE_PANEL.color);
        float textY = (float) (position.y + (size.height - mc.fontRendererObj.FONT_HEIGHT) / 2f);
        mc.fontRendererObj.drawString(setting.name.toLowerCase(), (int) (position.x + 5), textY + 1, -1);
    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton) {
    }
}