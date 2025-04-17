package dim.setting;

import dim.DimClient;
import dim.module.Module;

public class Setting<T> {
    public final String name;
    public final SettingType type;
    private T value;
    private final T defaultValue;
    private final Module parent;

    public Setting(String name, SettingType type, T value) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.defaultValue = value;

        this.parent = DimClient.INSTANCE.moduleStorage.currentModule;
        this.parent.settings.add(this);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getDefaultValue() {
        return defaultValue;
    }
}
