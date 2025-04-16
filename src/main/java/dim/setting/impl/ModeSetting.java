package dim.setting.impl;

import dim.setting.Setting;
import dim.setting.SettingType;

public class ModeSetting extends Setting<String> {
    private final String[] values;
    private int index = 0;

    public ModeSetting(String name, String value, String... values) {
        super(name, SettingType.MODE, value);
        this.values = values;
        setValue(value);
    }

    @Override
    public void setValue(String value) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].equalsIgnoreCase(value)) {
                index = i;
                break;
            }
        }
        super.setValue(values[index]);
    }

    public void next() {
        index = (index + 1) % values.length;
        super.setValue(values[index]);
    }

    public void previous() {
        index = (index - 1 + values.length) % values.length;
        super.setValue(values[index]);
    }

    public int getIndex() {
        return index;
    }

    public String[] getValues() {
        return values;
    }
}
