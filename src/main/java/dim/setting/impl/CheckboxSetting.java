package dim.setting.impl;

import dim.setting.Setting;
import dim.setting.SettingType;

public class CheckboxSetting extends Setting<Boolean> {
    public CheckboxSetting(String name, Boolean value) {
        super(name, SettingType.CHECKBOX, value);
    }
}
