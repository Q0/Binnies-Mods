package binnie.extratrees.carpentry;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.IPattern;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.carpentry.DesignerManager;
import binnie.extratrees.carpentry.EnumPattern;
import binnie.extratrees.carpentry.GlassType;
import binnie.extratrees.item.ExtraTreeItems;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public enum DesignSystem implements IDesignSystem {
   Wood,
   Glass;

   Map primary = new HashMap();
   Map secondary = new HashMap();

   private DesignSystem() {
      DesignerManager.instance.registerDesignSystem(this);
   }

   public IDesignMaterial getDefaultMaterial() {
      switch(this) {
      case Glass:
         return GlassType.get(0);
      case Wood:
         return PlankType.ExtraTreePlanks.Fir;
      default:
         return null;
      }
   }

   public IDesignMaterial getDefaultMaterial2() {
      switch(this) {
      case Glass:
         return GlassType.get(1);
      case Wood:
         return PlankType.ExtraTreePlanks.Whitebeam;
      default:
         return null;
      }
   }

   public IDesignMaterial getMaterial(int id) {
      switch(this) {
      case Glass:
         return GlassType.get(id);
      case Wood:
         return CarpentryManager.carpentryInterface.getWoodMaterial(id);
      default:
         return null;
      }
   }

   public int getMaterialIndex(IDesignMaterial id) {
      switch(this) {
      case Glass:
         return GlassType.getIndex(id);
      case Wood:
         return CarpentryManager.carpentryInterface.getCarpentryWoodIndex(id);
      default:
         return 0;
      }
   }

   public String getTexturePath() {
      switch(this) {
      case Glass:
         return "glass";
      case Wood:
         return "patterns";
      default:
         return "";
      }
   }

   public IDesignMaterial getMaterial(ItemStack stack) {
      switch(this) {
      case Glass:
         return GlassType.get(stack);
      case Wood:
         return CarpentryManager.carpentryInterface.getWoodMaterial(stack);
      default:
         return null;
      }
   }

   public ItemStack getAdhesive() {
      switch(this) {
      case Glass:
         return ExtraTreeItems.GlassFitting.get(1);
      case Wood:
         return ExtraTreeItems.WoodWax.get(1);
      default:
         return null;
      }
   }

   public IIcon getPrimaryIcon(IPattern pattern) {
      return pattern instanceof EnumPattern?(IIcon)this.primary.get(Integer.valueOf(((EnumPattern)pattern).ordinal())):null;
   }

   public IIcon getSecondaryIcon(IPattern pattern) {
      return pattern instanceof EnumPattern?(IIcon)this.secondary.get(Integer.valueOf(((EnumPattern)pattern).ordinal())):null;
   }

   public void registerIcons(IIconRegister register) {
      for(EnumPattern pattern : EnumPattern.values()) {
         this.primary.put(Integer.valueOf(pattern.ordinal()), BinnieCore.proxy.getIcon(register, this.getMod().getModID(), this.getTexturePath() + "/" + pattern.toString().toLowerCase() + ".0"));
         this.secondary.put(Integer.valueOf(pattern.ordinal()), BinnieCore.proxy.getIcon(register, this.getMod().getModID(), this.getTexturePath() + "/" + pattern.toString().toLowerCase() + ".1"));
      }

   }

   public AbstractMod getMod() {
      return ExtraTrees.instance;
   }
}
