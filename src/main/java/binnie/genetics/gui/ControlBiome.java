package binnie.genetics.gui;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IPoint;
import forestry.core.render.TextureManager;
import net.minecraft.util.IIcon;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class ControlBiome extends Control implements ITooltip {
   BiomeGenBase biome = null;
   String iconCategory = "plains";

   public ControlBiome(IWidget parent, float x, float y, float w, float h, BiomeGenBase biome) {
      super(parent, x, y, w, h);
      this.biome = biome;
   }

   public void onRenderBackground() {
      if(BiomeDictionary.isBiomeOfType(this.biome, Type.MOUNTAIN)) {
         this.iconCategory = "hills";
      }

      if(BiomeDictionary.isBiomeOfType(this.biome, Type.HILLS)) {
         this.iconCategory = "hills";
      }

      if(BiomeDictionary.isBiomeOfType(this.biome, Type.SANDY)) {
         this.iconCategory = "desert";
      }

      if(BiomeDictionary.isBiomeOfType(this.biome, Type.SNOWY)) {
         this.iconCategory = "snow";
      }

      if(BiomeDictionary.isBiomeOfType(this.biome, Type.FOREST)) {
         this.iconCategory = "forest";
      }

      if(BiomeDictionary.isBiomeOfType(this.biome, Type.SWAMP)) {
         this.iconCategory = "swamp";
      }

      if(BiomeDictionary.isBiomeOfType(this.biome, Type.JUNGLE)) {
         this.iconCategory = "jungle";
      }

      if(BiomeDictionary.isBiomeOfType(this.biome, Type.COLD) && BiomeDictionary.isBiomeOfType(this.biome, Type.FOREST)) {
         this.iconCategory = "taiga";
      }

      if(BiomeDictionary.isBiomeOfType(this.biome, Type.MUSHROOM)) {
         this.iconCategory = "mushroom";
      }

      if(BiomeDictionary.isBiomeOfType(this.biome, Type.OCEAN)) {
         this.iconCategory = "ocean";
      }

      if(BiomeDictionary.isBiomeOfType(this.biome, Type.NETHER)) {
         this.iconCategory = "nether";
      }

      if(BiomeDictionary.isBiomeOfType(this.biome, Type.END)) {
         this.iconCategory = "end";
      }

      IIcon icon = TextureManager.getInstance().getDefault("habitats/" + this.iconCategory);
      CraftGUI.Render.iconItem(IPoint.ZERO, icon);
   }

   public void getTooltip(Tooltip tooltip) {
      tooltip.add(this.biome.biomeName.replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2"));
   }
}
