package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager extends SimpleMapProperty<String, Object> {

    private String bundleName = "sample.Main"; // a file Main.properties must be present at the root of your classpath

    public LocaleManager() {
        super(FXCollections.observableHashMap());
        reload();
    }

    public void changeLocale(Locale newLocale) {
        Locale.setDefault(newLocale);
        reload();
    }

    private void reload() {
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String value = bundle.getString(key);

            String[] parts = key.split("\\.");

            MapProperty<String, Object> map = this;

            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                if (i == parts.length - 1) {
                    map.put(part, value);
                } else {
                    if (!map.containsKey(part)) {
                        map.put(part, new SimpleMapProperty<>(FXCollections.observableHashMap()));
                    }
                    map = (MapProperty<String, Object>) map.get(part);
                }
            }
        }
    }

    public StringBinding bind(String key) {
        String[] parts = key.split("\\.");

        MapProperty<String, Object> map = this;

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (i == parts.length - 1) {
                return Bindings.valueAt(map, part).asString();
            } else {
                if (!map.containsKey(part)) {
                    map.put(part, new SimpleMapProperty<>(FXCollections.observableHashMap()));
                }
                map = (MapProperty<String, Object>) map.get(part);
            }
        }
        throw new NullPointerException("Unknown key : " + key);
    }
}

class BaseView{
    private LocaleManager lang;

    public BaseView() {
        lang = new LocaleManager();
    }

    public LocaleManager langProperty() {
        return lang;
    }

    public ObservableMap<String, Object> getLang() {
        return lang.get();
    }

    public void setLang(MapProperty<String, Object> resource) {
        this.lang.set(resource);
    }
}