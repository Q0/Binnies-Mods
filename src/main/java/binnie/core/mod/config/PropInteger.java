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
   propertyClass = PropInteger.PropertyInteger.class
)
public @interface PropInteger {
   public static class PropertyInteger extends PropertyBase {
      public PropertyInteger(Field field, BinnieConfiguration file, ConfigProperty configProperty, PropInteger annotedProperty) throws IllegalArgumentException, IllegalAccessException {
         super(field, file, configProperty, annotedProperty);
      }

      protected Property getProperty() {
         return this.file.get(this.getCategory(), this.getKey(), ((Integer)this.defaultValue).intValue());
      }

      protected Integer getConfigValue() {
         return Integer.valueOf(this.property.getInt(((Integer)this.defaultValue).intValue()));
      }

      protected void addComments() {
         this.addComment("Default value is " + this.defaultValue + ".");
      }
   }
}
