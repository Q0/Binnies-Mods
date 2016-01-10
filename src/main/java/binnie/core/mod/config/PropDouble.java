package binnie.core.mod.config;

import binnie.core.mod.config.BinnieConfiguration;
import binnie.core.mod.config.ConfigProperty;
import binnie.core.mod.config.PropertyBase;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import net.minecraftforge.common.config.Property;

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
         return this.file.get(this.getCategory(), this.getKey(), ((Double)this.defaultValue).doubleValue());
      }

      protected Double getConfigValue() {
         return Double.valueOf(this.property.getDouble(((Double)this.defaultValue).doubleValue()));
      }

      protected void addComments() {
         this.addComment("Default value is " + this.defaultValue + ".");
      }
   }
}
