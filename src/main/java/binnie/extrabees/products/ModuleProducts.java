package binnie.extrabees.products;

import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.products.EnumHoneyComb;
import binnie.extrabees.products.EnumHoneyDrop;
import binnie.extrabees.products.EnumPropolis;
import binnie.extrabees.products.ItemHoneyComb;
import binnie.extrabees.products.ItemHoneyCrystal;
import binnie.extrabees.products.ItemHoneyCrystalEmpty;
import binnie.extrabees.products.ItemHoneyDrop;
import binnie.extrabees.products.ItemPropolis;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleProducts implements IInitializable {
   public ModuleProducts() {
      super();
   }

   public void preInit() {
      ExtraBees.honeyCrystal = new ItemHoneyCrystal();
      ExtraBees.honeyCrystalEmpty = new ItemHoneyCrystalEmpty();
      ExtraBees.honeyDrop = new ItemHoneyDrop();
      ExtraBees.comb = new ItemHoneyComb();
      ExtraBees.propolis = new ItemPropolis();
      OreDictionary.registerOre("ingotIron", Items.iron_ingot);
      OreDictionary.registerOre("ingotGold", Items.gold_ingot);
      OreDictionary.registerOre("gemDiamond", Items.diamond);
   }

   public void init() {
      ItemHoneyComb.addSubtypes();
   }

   public void postInit() {
      GameRegistry.addRecipe(new ItemStack(ExtraBees.honeyCrystalEmpty), new Object[]{"#@#", "@#@", "#@#", Character.valueOf('@'), Mods.Forestry.stack("honeyDrop"), Character.valueOf('#'), EnumHoneyDrop.ENERGY.get(1)});

      for(EnumHoneyComb info : EnumHoneyComb.values()) {
         info.addRecipe();
      }

      for(EnumHoneyDrop info : EnumHoneyDrop.values()) {
         info.addRecipe();
      }

      for(EnumPropolis info : EnumPropolis.values()) {
         info.addRecipe();
      }

   }
}
