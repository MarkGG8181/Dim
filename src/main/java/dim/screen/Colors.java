package dim.screen;

import java.awt.*;

public enum Colors {
    TEXT(new Color(166, 166, 166)),
    CHECKBOX_TRUE(new Color(140, 246, 104)),
    CHECKBOX_FALSE(new Color(232, 92, 92)),
    CATEGORY_PANEL(new Color(22,22,22)),
    MODULE_PANEL(new Color(27, 27, 27));

    public final Color color;
    Colors(Color color) {
        this.color = color;
    }
}
