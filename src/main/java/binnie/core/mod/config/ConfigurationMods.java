package binnie.core.mod.config;

import binnie.core.mod.config.ConfigFile;
import binnie.core.mod.config.ConfigProperty;
import binnie.core.mod.config.PropBoolean;

@ConfigFile(
   filename = "/config/forestry/binnie-mods.conf"
)
public class ConfigurationMods {
   @ConfigProperty(
      key = "extraBees",
      comment = {"Enables the Extra Bees Mod."}
   )
   @PropBoolean
   public static boolean extraBees = true;
   @ConfigProperty(
      key = "extraTrees",
      comment = {"Enables the Extra Trees Mod."}
   )
   @PropBoolean
   public static boolean extraTrees = true;
   @ConfigProperty(
      key = "botany",
      comment = {"Enables the Botany Mod."}
   )
   @PropBoolean
   public static boolean botany = true;
   @ConfigProperty(
      key = "genetics",
      comment = {"Enables the Genetics Mod."}
   )
   @PropBoolean
   public static boolean genetics = true;

   public ConfigurationMods() {
      super();
   }
}
