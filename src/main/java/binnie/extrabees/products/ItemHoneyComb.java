package binnie.extrabees.products;

import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.item.IItemEnum;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.core.ExtraBeeItems;
import binnie.extrabees.products.EnumHoneyComb;
import binnie.extrabees.products.EnumHoneyDrop;
import binnie.extrabees.products.EnumPropolis;
import binnie.extrabees.products.ItemProduct;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHoneyComb extends ItemProduct {
   IIcon icon1;
   IIcon icon2;

   @SideOnly(Side.CLIENT)
   public boolean requiresMultipleRenderPasses() {
      return true;
   }

   public ItemHoneyComb() {
      super(EnumHoneyComb.values());
      this.setCreativeTab(Tabs.tabApiculture);
      this.setUnlocalizedName("honeyComb");
   }

   public static void addSubtypes() {
      ItemStack beeswax = Mods.Forestry.stack("beeswax");
      ItemStack honeyDrop = Mods.Forestry.stack("honeyDrop");
      OreDictionary.registerOre("ingotIron", Items.iron_ingot);
      OreDictionary.registerOre("ingotGold", Items.gold_ingot);
      OreDictionary.registerOre("gemDiamond", Items.diamond);
      OreDictionary.registerOre("gemEmerald", Items.emerald);
      OreDictionary.registerOre("gemLapis", new ItemStack(Items.dye, 1, 4));
      EnumHoneyComb.BARREN.addProduct(beeswax, 100);
      EnumHoneyComb.BARREN.addProduct(honeyDrop, 50);
      EnumHoneyComb.ROTTEN.addProduct(beeswax, 20);
      EnumHoneyComb.ROTTEN.addProduct(honeyDrop, 20);
      EnumHoneyComb.ROTTEN.addProduct(new ItemStack(Items.rotten_flesh, 1, 0), 80);
      EnumHoneyComb.BONE.addProduct(beeswax, 20);
      EnumHoneyComb.BONE.addProduct(honeyDrop, 20);
      EnumHoneyComb.BONE.addProduct(new ItemStack(Items.dye, 1, 15), 80);
      EnumHoneyComb.OIL.tryAddProduct((IItemEnum)EnumPropolis.OIL, 60);
      EnumHoneyComb.OIL.addProduct(honeyDrop, 75);
      EnumHoneyComb.COAL.addProduct(beeswax, 80);
      EnumHoneyComb.COAL.addProduct(honeyDrop, 75);
      EnumHoneyComb.COAL.tryAddProduct((IItemEnum)ExtraBeeItems.CoalDust, 100);
      EnumHoneyComb.WATER.tryAddProduct((IItemEnum)EnumPropolis.WATER, 100);
      EnumHoneyComb.WATER.addProduct(honeyDrop, 90);
      EnumHoneyComb.STONE.addProduct(beeswax, 50);
      EnumHoneyComb.STONE.addProduct(honeyDrop, 25);
      EnumHoneyComb.MILK.tryAddProduct((IItemEnum)EnumHoneyDrop.MILK, 100);
      EnumHoneyComb.MILK.addProduct(honeyDrop, 90);
      EnumHoneyComb.FRUIT.tryAddProduct((IItemEnum)EnumHoneyDrop.APPLE, 100);
      EnumHoneyComb.FRUIT.addProduct(honeyDrop, 90);
      EnumHoneyComb.SEED.tryAddProduct((IItemEnum)EnumHoneyDrop.SEED, 100);
      EnumHoneyComb.SEED.addProduct(honeyDrop, 90);
      EnumHoneyComb.ALCOHOL.tryAddProduct((IItemEnum)EnumHoneyDrop.ALCOHOL, 100);
      EnumHoneyComb.ALCOHOL.addProduct(honeyDrop, 90);
      EnumHoneyComb.FUEL.tryAddProduct((IItemEnum)EnumPropolis.FUEL, 60);
      EnumHoneyComb.FUEL.addProduct(honeyDrop, 50);
      EnumHoneyComb.CREOSOTE.tryAddProduct((IItemEnum)EnumPropolis.CREOSOTE, 70);
      EnumHoneyComb.CREOSOTE.addProduct(honeyDrop, 50);
      EnumHoneyComb.LATEX.addProduct(honeyDrop, 50);
      EnumHoneyComb.LATEX.addProduct(beeswax, 85);
      if(!OreDictionary.getOres("itemRubber").isEmpty()) {
         EnumHoneyComb.LATEX.tryAddProduct((ItemStack)((ItemStack)OreDictionary.getOres("itemRubber").get(0)), 100);
      } else {
         EnumHoneyComb.LATEX.active = false;
      }

      EnumHoneyComb.REDSTONE.addProduct(beeswax, 80);
      EnumHoneyComb.REDSTONE.addProduct(new ItemStack(Items.redstone, 1, 0), 100);
      EnumHoneyComb.REDSTONE.addProduct(honeyDrop, 50);
      EnumHoneyComb.RESIN.addProduct(beeswax, 100);
      EnumHoneyComb.RESIN.tryAddProduct((ItemStack)Mods.IC2.stack("itemHarz"), 100);
      EnumHoneyComb.RESIN.tryAddProduct((ItemStack)Mods.IC2.stack("itemHarz"), 50);
      EnumHoneyComb.IC2ENERGY.addProduct(beeswax, 80);
      EnumHoneyComb.IC2ENERGY.addProduct(new ItemStack(Items.redstone, 1, 0), 75);
      EnumHoneyComb.IC2ENERGY.tryAddProduct((IItemEnum)EnumHoneyDrop.ENERGY, 100);
      EnumHoneyComb.IRON.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.IRON.tryAddProduct((IItemEnum)ExtraBeeItems.IronDust, 100);
      EnumHoneyComb.GOLD.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.GOLD.tryAddProduct((IItemEnum)ExtraBeeItems.GoldDust, 100);
      EnumHoneyComb.COPPER.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.COPPER.tryAddProduct((IItemEnum)ExtraBeeItems.CopperDust, 100);
      EnumHoneyComb.TIN.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.TIN.tryAddProduct((IItemEnum)ExtraBeeItems.TinDust, 100);
      EnumHoneyComb.NICKEL.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.NICKEL.tryAddProduct((IItemEnum)ExtraBeeItems.NickelDust, 100);
      EnumHoneyComb.SILVER.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.SILVER.tryAddProduct((IItemEnum)ExtraBeeItems.SilverDust, 100);
      EnumHoneyComb.URANIUM.copyProducts(EnumHoneyComb.STONE);
      if(!OreDictionary.getOres("crushedUranium").isEmpty()) {
         EnumHoneyComb.URANIUM.tryAddProduct((ItemStack)((ItemStack)OreDictionary.getOres("crushedUranium").get(0)), 50);
      }

      EnumHoneyComb.CLAY.addProduct(beeswax, 25);
      EnumHoneyComb.CLAY.addProduct(honeyDrop, 80);
      EnumHoneyComb.CLAY.addProduct(new ItemStack(Items.clay_ball), 80);
      EnumHoneyComb.OLD.addProduct(beeswax, 100);
      EnumHoneyComb.OLD.addProduct(honeyDrop, 90);
      EnumHoneyComb.FUNGAL.addProduct(beeswax, 90);
      EnumHoneyComb.FUNGAL.addProduct(new ItemStack(Blocks.brown_mushroom_block, 1, 0), 100);
      EnumHoneyComb.FUNGAL.addProduct(new ItemStack(Blocks.red_mushroom_block, 1, 0), 75);
      EnumHoneyComb.ACIDIC.addProduct(beeswax, 80);
      EnumHoneyComb.ACIDIC.tryAddProduct((IItemEnum)EnumHoneyDrop.ACID, 50);
      if(!OreDictionary.getOres("dustSulfur").isEmpty()) {
         EnumHoneyComb.ACIDIC.addProduct((ItemStack)OreDictionary.getOres("dustSulfur").get(0), 75);
      }

      EnumHoneyComb.VENOMOUS.addProduct(beeswax, 80);
      EnumHoneyComb.VENOMOUS.tryAddProduct((IItemEnum)EnumHoneyDrop.POISON, 80);
      EnumHoneyComb.SLIME.addProduct(beeswax, 100);
      EnumHoneyComb.SLIME.addProduct(honeyDrop, 75);
      EnumHoneyComb.SLIME.addProduct(new ItemStack(Items.slime_ball, 1, 0), 75);
      EnumHoneyComb.BLAZE.addProduct(beeswax, 75);
      EnumHoneyComb.BLAZE.addProduct(new ItemStack(Items.blaze_powder, 1, 0), 100);
      EnumHoneyComb.COFFEE.addProduct(beeswax, 90);
      EnumHoneyComb.COFFEE.addProduct(honeyDrop, 75);
      EnumHoneyComb.COFFEE.tryAddProduct((ItemStack)Mods.IC2.stack("itemCofeePowder"), 75);
      EnumHoneyComb.GLACIAL.tryAddProduct((IItemEnum)EnumHoneyDrop.ICE, 80);
      EnumHoneyComb.GLACIAL.addProduct(honeyDrop, 75);
      EnumHoneyComb.SHADOW.addProduct(honeyDrop, 50);
      if(!OreDictionary.getOres("dustObsidian").isEmpty()) {
         EnumHoneyComb.SHADOW.tryAddProduct((ItemStack)((ItemStack)OreDictionary.getOres("dustObsidian").get(0)), 75);
      } else {
         EnumHoneyComb.SHADOW.active = false;
      }

      EnumHoneyComb.LEAD.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.LEAD.tryAddProduct((IItemEnum)ExtraBeeItems.LeadDust, 100);
      EnumHoneyComb.ZINC.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.ZINC.tryAddProduct((IItemEnum)ExtraBeeItems.ZincDust, 100);
      EnumHoneyComb.TITANIUM.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.TITANIUM.tryAddProduct((IItemEnum)ExtraBeeItems.TitaniumDust, 100);
      EnumHoneyComb.TUNGSTEN.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.TUNGSTEN.tryAddProduct((IItemEnum)ExtraBeeItems.TungstenDust, 100);
      EnumHoneyComb.PLATINUM.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.PLATINUM.tryAddProduct((IItemEnum)ExtraBeeItems.PlatinumDust, 100);
      EnumHoneyComb.LAPIS.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.LAPIS.addProduct(new ItemStack(Items.dye, 6, 4), 100);
      EnumHoneyComb.EMERALD.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.EMERALD.tryAddProduct((IItemEnum)ExtraBeeItems.EmeraldShard, 100);
      EnumHoneyComb.RUBY.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.RUBY.tryAddProduct((IItemEnum)ExtraBeeItems.RubyShard, 100);
      EnumHoneyComb.SAPPHIRE.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.SAPPHIRE.tryAddProduct((IItemEnum)ExtraBeeItems.SapphireShard, 100);
      EnumHoneyComb.DIAMOND.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.DIAMOND.tryAddProduct((IItemEnum)ExtraBeeItems.DiamondShard, 100);
      EnumHoneyComb.RED.addProduct(honeyDrop, 80);
      EnumHoneyComb.RED.addProduct(beeswax, 80);
      EnumHoneyComb.GLOWSTONE.addProduct(honeyDrop, 25);
      EnumHoneyComb.GLOWSTONE.addProduct(new ItemStack(Items.glowstone_dust), 100);
      EnumHoneyComb.SALTPETER.addProduct(honeyDrop, 25);
      EnumHoneyComb.SALTPETER.tryAddProduct((ItemStack)getOreDictionary("dustSaltpeter"), 100);
      EnumHoneyComb.COMPOST.addProduct(honeyDrop, 25);
      EnumHoneyComb.COMPOST.tryAddProduct((ItemStack)Mods.Forestry.stack("fertilizerBio"), 100);
      EnumHoneyComb.SAWDUST.addProduct(honeyDrop, 25);
      if(!OreDictionary.getOres("dustSawdust").isEmpty()) {
         EnumHoneyComb.SAWDUST.tryAddProduct((ItemStack)((ItemStack)OreDictionary.getOres("dustSawdust").get(0)), 100);
      } else if(!OreDictionary.getOres("sawdust").isEmpty()) {
         EnumHoneyComb.SAWDUST.tryAddProduct((ItemStack)((ItemStack)OreDictionary.getOres("sawdust").get(0)), 100);
      }

      EnumHoneyComb.CERTUS.addProduct(honeyDrop, 25);
      EnumHoneyComb.CERTUS.addProduct(new ItemStack(Items.quartz), 25);
      if(!OreDictionary.getOres("dustCertusQuartz").isEmpty()) {
         EnumHoneyComb.CERTUS.tryAddProduct((ItemStack)((ItemStack)OreDictionary.getOres("dustCertusQuartz").get(0)), 20);
      }

      EnumHoneyComb.ENDERPEARL.addProduct(honeyDrop, 25);
      if(!OreDictionary.getOres("dustEnderPearl").isEmpty()) {
         EnumHoneyComb.ENDERPEARL.tryAddProduct((ItemStack)((ItemStack)OreDictionary.getOres("dustEnderPearl").get(0)), 25);
      }

      EnumHoneyComb.YELLORIUM.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.CYANITE.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.BLUTONIUM.copyProducts(EnumHoneyComb.STONE);
      EnumHoneyComb.YELLORIUM.tryAddProduct((IItemEnum)ExtraBeeItems.YelloriumDust, 25);
      EnumHoneyComb.CYANITE.tryAddProduct((IItemEnum)ExtraBeeItems.CyaniteDust, 25);
      EnumHoneyComb.BLUTONIUM.tryAddProduct((IItemEnum)ExtraBeeItems.BlutoniumDust, 25);
      OreDictionary.registerOre("beeComb", new ItemStack(ExtraBees.comb, 1, 32767));

      for(int i = 0; i < 16; ++i) {
         EnumHoneyComb type = EnumHoneyComb.values()[EnumHoneyComb.RED.ordinal() + i];
         if(type != EnumHoneyComb.RED) {
            type.copyProducts(EnumHoneyComb.RED);
         }
      }

      for(int i = 0; i < 16; ++i) {
         EnumHoneyComb type = EnumHoneyComb.values()[EnumHoneyComb.RED.ordinal() + i];
         EnumHoneyDrop drop = EnumHoneyDrop.values()[EnumHoneyDrop.RED.ordinal() + i];
         int[] dyeC = new int[]{1, 11, 4, 2, 0, 15, 3, 14, 6, 5, 8, 12, 9, 10, 13, 7};
         int k = dyeC[i];
         ItemStack dye = new ItemStack(Items.dye, 1, k);
         switch(k) {
         case 0:
            dye = ExtraBeeItems.BlackDye.get(1);
            break;
         case 1:
            dye = ExtraBeeItems.RedDye.get(1);
            break;
         case 2:
            dye = ExtraBeeItems.GreenDye.get(1);
            break;
         case 3:
            dye = ExtraBeeItems.BrownDye.get(1);
            break;
         case 4:
            dye = ExtraBeeItems.BlueDye.get(1);
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 12:
         case 13:
         case 14:
         default:
            break;
         case 11:
            dye = ExtraBeeItems.YellowDye.get(1);
            break;
         case 15:
            dye = ExtraBeeItems.WhiteDye.get(1);
         }

         type.addProduct(drop.get(1), 100);
         drop.addRemenant(dye);
      }

   }

   private static ItemStack getOreDictionary(String string) {
      return OreDictionary.getOres(string).size() > 0?(ItemStack)OreDictionary.getOres(string).get(0):null;
   }

   public int getColorFromItemStack(ItemStack itemStack, int j) {
      return EnumHoneyComb.get(itemStack) == null?16777215:(j == 0?EnumHoneyComb.get(itemStack).colour[0]:EnumHoneyComb.get(itemStack).colour[1]);
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamageForRenderPass(int i, int j) {
      return j > 0?this.icon1:this.icon2;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister register) {
      this.icon1 = BinnieCore.proxy.getIcon(register, "forestry", "beeCombs.0");
      this.icon2 = BinnieCore.proxy.getIcon(register, "forestry", "beeCombs.1");
   }

   public static enum VanillaComb {
      HONEY,
      COCOA,
      SIMMERING,
      STRINGY,
      FROZEN,
      DRIPPING,
      SILKY,
      PARCHED,
      MYSTERIOUS,
      IRRADIATED,
      POWDERY,
      REDDENED,
      DARKENED,
      OMEGA,
      WHEATEN,
      MOSSY,
      QUARTZ;

      private VanillaComb() {
      }

      public ItemStack get() {
         return new ItemStack(Mods.Forestry.item("beeCombs"), 1, this.ordinal());
      }
   }
}
