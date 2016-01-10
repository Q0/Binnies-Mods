package binnie.core.mod.config;

import net.minecraftforge.common.config.Property;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@ConfigProperty.Type(
        propertyClass = PropDouble.PropertyDouble.class
)
public @interface PropDouble {
    public static class PropertyDouble extends PropertyBase {
        public PropertyDouble(Field field, BinnieConfiguration file, ConfigProperty configProperty, PropDouble annotedProperty) throws IllegalArgumentException, IllegalAccessException {
            super(field, file, configProperty, annotedProperty);
        }

        protected Property getProperty() {
            return this.file.get(this.getCategory(), this.getKey(), ((Double) this.defaultValue).doubleValue());
        }

        protected Double getConfigValue() {
            return Double.valueOf(this.property.getDouble(((Double) this.defaultValue).doubleValue()));
        }

        protected void addComments() {
            this.addComment("Default value is " + this.defaultValue + ".");
        }
    }
}
