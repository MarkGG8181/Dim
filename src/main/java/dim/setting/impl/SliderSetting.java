package dim.setting.impl;

import dim.setting.Setting;
import dim.setting.SettingType;

public class SliderSetting extends Setting<Number> {
    private final Number min, max;
    private final int decimals;

    public SliderSetting(String name, Number value, Number min, Number max, int decimals) {
        super(name, SettingType.SLIDER, value);
        this.min = min;
        this.max = max;
        this.decimals = decimals;
    }

    public Number getMin() {
        return min;
    }

    public Number getMax() {
        return max;
    }

    public int getDecimals() {
        return decimals;
    }
}
