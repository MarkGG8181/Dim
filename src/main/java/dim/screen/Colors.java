package dim.screen;

import java.awt.*;

public enum Colors {
    CATEGORY_PANEL(new Color(22,22,22)),
    MODULE_PANEL(new Color(27, 27, 27));

    public final Color color;
    Colors(Color color) {
        this.color = color;
    }
}
