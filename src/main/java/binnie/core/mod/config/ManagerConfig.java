package binnie.core.mod.config;

import binnie.core.AbstractMod;
import binnie.core.ManagerBase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class ManagerConfig extends ManagerBase {
    private Map configurations = new LinkedHashMap();
    private Map itemIDs = new HashMap();

    public ManagerConfig() {
        super();
    }

    public void registerConfiguration(Class cls, AbstractMod mod) {
        if (cls.isAnnotationPresent(ConfigFile.class)) {
            this.loadConfiguration(cls, mod);
        }

    }

    public void loadConfiguration(Class cls, AbstractMod mod) {
        try {
            String filename = ((ConfigFile) cls.getAnnotation(ConfigFile.class)).filename();
            BinnieConfiguration config = new BinnieConfiguration(filename, mod);
            config.load();

            for (Field field : cls.getFields()) {
                if (field.isAnnotationPresent(ConfigProperty.class)) {
                    ConfigProperty propertyAnnot = (ConfigProperty) field.getAnnotation(ConfigProperty.class);

                    for (Annotation annotation : field.getAnnotations()) {
                        if (annotation.annotationType().isAnnotationPresent(ConfigProperty.Type.class)) {
                            Class<?> propertyClass = ((ConfigProperty.Type) annotation.annotationType().getAnnotation(ConfigProperty.Type.class)).propertyClass();
                            PropertyBase property = (PropertyBase) propertyClass.getConstructor(new Class[]{Field.class, BinnieConfiguration.class, ConfigProperty.class, annotation.annotationType()}).newInstance(new Object[]{field, config, propertyAnnot, annotation.annotationType().cast(annotation)});
                        }
                    }
                }
            }

            config.save();
            this.configurations.put(cls, config);
        } catch (Exception var16) {
            var16.printStackTrace();
        }

    }

    public void addItemID(Integer configValue, String configKey, BinnieConfiguration configFile) {
        if (!this.itemIDs.containsKey(configFile.mod)) {
            this.itemIDs.put(configFile.mod, new ArrayList());
        }

        ((List) this.itemIDs.get(configFile.mod)).add(new BinnieItemData(configValue.intValue() + 256, configFile, configKey));
    }
}
