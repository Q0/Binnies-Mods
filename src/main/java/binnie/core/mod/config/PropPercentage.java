package binnie.core.mod.config;

import net.minecraftforge.common.config.Property;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@ConfigProperty.Type(propertyClass = PropertyPercentage.class)
public @interface PropPercentage {
    int upper() default 100;

    int lower() default 0;

    public static class PropertyPercentage extends PropertyBase<Integer, PropPercentage> {
        public PropertyPercentage(final Field field, final BinnieConfiguration file, final ConfigProperty configProperty, final PropPercentage annotedProperty) throws IllegalArgumentException, IllegalAccessException {
            super(field, file, configProperty, annotedProperty);
        }

        @Override
        protected Integer getConfigValue() {
            return this.property.getInt((int) this.defaultValue);
        }

        @Override
        protected void addComments() {
            this.addComment("Default value is " + this.defaultValue + "%.");
            this.addComment("Range is " + ((PropPercentage) this.annotatedProperty).lower() + "-" + ((PropPercentage) this.annotatedProperty).upper() + "%.");
        }

        @Override
        protected Property getProperty() {
            return this.file.get(this.getCategory(), this.getKey(), (int) this.defaultValue);
        }
    }
}
