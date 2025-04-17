package dim.screen.opengl.clickGUI.impl;

import dim.screen.Colors;
import dim.screen.opengl.clickGUI.Component;
import dim.setting.Setting;
import dim.setting.impl.CheckboxSetting;
import dim.setting.impl.ModeSetting;
import dim.setting.impl.SliderSetting;
import dim.util.game.RenderUtil;
import dim.util.math.MathUtil;
import dim.util.math.SizeVector2d;
import org.lwjgl.input.Mouse;

import javax.vecmath.Vector2d;
import java.awt.*;

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
        double textY = (position.y + (size.height - mc.fontRendererObj.FONT_HEIGHT) / 2f);
        mc.fontRendererObj.drawString(setting.name.toLowerCase() + ":", (int) (position.x + 5), (int) (textY + 1), -1);

        switch (setting.type) {
            case MODE:
                ModeSetting modeSetting = (ModeSetting) setting;
                mc.fontRendererObj.drawString(modeSetting.getValue(), (int) (position.x + size.width - mc.fontRendererObj.getStringWidth(modeSetting.getValue())) - 5, (int) (textY + 1),  Colors.TEXT.color);
                break;
            case SLIDER:
                SliderSetting sliderSetting = (SliderSetting) setting;

                int decimalPoint = sliderSetting.getDecimals();
                double value = sliderSetting.getValue().doubleValue();
                double min = sliderSetting.getMin().doubleValue();
                double max = sliderSetting.getMax().doubleValue();
                double width = Math.min(Math.max((value - min) / (max - min), 0), 1) * size.width;

                boolean hoverSlider = RenderUtil.hovered(mouseX, mouseY, position.x, position.y, size.width, size.height);
                if (hoverSlider && Mouse.isButtonDown(0)) {
                    double normalizedX = (mouseX - position.x) / size.width;
                    double newValue = min + normalizedX * (max - min);
                    newValue = MathUtil.round(newValue, decimalPoint);
                    newValue = Math.min(Math.max(newValue, min), max);
                    sliderSetting.setValue(newValue);
                }

                RenderUtil.rectangle(position.x, position.y, width, size.height, new Color(255, 255, 255, 150));

                mc.fontRendererObj.drawString(sliderSetting.getValue().toString(), (int) (position.x + size.width - mc.fontRendererObj.getStringWidth(sliderSetting.getValue().toString()) - 5), (int) (textY + 1), -1);

                break;
            case CHECKBOX:
                CheckboxSetting checkboxSetting = (CheckboxSetting) setting;
                mc.fontRendererObj.drawString(checkboxSetting.getValue().toString(), (int) (position.x + size.width - mc.fontRendererObj.getStringWidth(checkboxSetting.getValue().toString())) - 5, (int) (textY + 1), checkboxSetting.getValue() ? Colors.CHECKBOX_TRUE.color : Colors.CHECKBOX_FALSE.color);
                break;
        }
    }

    @Override
    public void click(int mouseX, int mouseY, int mouseButton) {
        if (RenderUtil.hovered(mouseX, mouseY, position.x, position.y, size.width, size.height)) {
            switch (setting.type) {
                case MODE:
                    ModeSetting modeSetting = (ModeSetting) setting;
                    if (mouseButton == 0) {
                        modeSetting.next();
                    } else {
                        modeSetting.previous();
                    }
                    break;
                case CHECKBOX:
                    CheckboxSetting checkboxSetting = (CheckboxSetting) setting;
                    checkboxSetting.setValue(!checkboxSetting.getValue());
                    break;
            }
        }
    }
}