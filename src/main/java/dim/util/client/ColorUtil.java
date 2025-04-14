package dim.util.client;

import dim.module.Category;

import java.awt.*;

public class ColorUtil {
    public static Color getColorFromCategory(Category category) {
        switch (category) {
            case COMBAT:
                return new Color(0xE83151);
            case MOVEMENT:
                return new Color(0x6DD3CE);
            case RENDER:
                return new Color(0xAB92BF);
            case PLAYER:
                return new Color(0xB5BA72);
            case OTHER:
                return new Color(0xEDC79B);
            default:
                return Color.WHITE;
        }
    }
}