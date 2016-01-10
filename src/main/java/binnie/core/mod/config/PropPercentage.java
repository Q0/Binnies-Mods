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
   propertyClass = PropPercentage.PropertyPercentage.class
)
public @interface PropPercentage {
   int upper() default 100;

   int lower() default 0;

   public static class PropertyPercentage extends PropertyBase {
      public PropertyPercentage(Field field, BinnieConfiguration file, ConfigProperty configProperty, PropPercentage annotedProperty) throws IllegalArgumentException, IllegalAccessException {
         super(field, file, configProperty, annotedProperty);
      }

      protected Integer getConfigValue() {
         return Integer.valueOf(this.property.getInt(((Integer)this.defaultValue).intValue()));
      }

      protected void addComments() {
         this.addComment("Default value is " + this.defaultValue + "%.");
         this.addComment("Range is " + ((PropPercentage)this.annotatedProperty).lower() + "-" + ((PropPercentage)this.annotatedProperty).upper() + "%.");
      }

      protected Property getProperty() {
         return this.file.get(this.getCategory(), this.getKey(), ((Integer)this.defaultValue).intValue());
      }
   }
}
