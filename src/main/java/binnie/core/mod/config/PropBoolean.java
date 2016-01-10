package binnie.core.mod.config;

import net.minecraftforge.common.config.Property;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@ConfigProperty.Type(
        propertyClass = PropBoolean.PropertyBoolean.class
)
public @interface PropBoolean {
    public static class PropertyBoolean extends PropertyBase {
        public PropertyBoolean(Field field, BinnieConfiguration file, ConfigProperty configProperty, PropBoolean annotedProperty) throws IllegalArgumentException, IllegalAccessException {
            super(field, file, configProperty, annotedProperty);
        }

        protected Property getProperty() {
            return this.file.get(this.getCategory(), this.getKey(), ((Boolean) this.defaultValue).booleanValue());
        }

        protected Boolean getConfigValue() {
            return Boolean.valueOf(this.property.getBoolean(((Boolean) this.defaultValue).booleanValue()));
        }

        protected void addComments() {
            this.addComment("Default value is " + this.defaultValue + ".");
        }
    }
}
