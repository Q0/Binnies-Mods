package binnie.extratrees.item;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.item.IItemMisc;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.Juice;
import forestry.api.recipes.RecipeManagers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

public enum Food implements IItemMisc {
   Crabapple(2),
   Orange(4),
   Kumquat(2),
   Lime(2),
   WildCherry(2),
   SourCherry(2),
   BlackCherry(2),
   Blackthorn(3),
   CherryPlum(3),
   Almond(1),
   Apricot(4),
   Grapefruit(4),
   Peach(4),
   Satsuma(3),
   BuddhaHand(3),
   Citron(3),
   FingerLime(3),
   KeyLime(2),
   Manderin(3),
   Nectarine(3),
   Pomelo(3),
   Tangerine(3),
   Pear(4),
   SandPear(2),
   Hazelnut(2),
   Butternut(1),
   Beechnut(0),
   Pecan(0),
   Banana(4),
   RedBanana(4),
   Plantain(2),
   BrazilNut(0),
   Fig(2),
   Acorn(0),
   Elderberry(1),
   Olive(1),
   GingkoNut(1),
   Coffee(0),
   OsangeOrange(1),
   Clove(0),
   Papayimar(8),
   Blackcurrant(2),
   Redcurrant(2),
   Blackberry(2),
   Raspberry(2),
   Blueberry(2),
   Cranberry(2),
   Juniper(0),
   Gooseberry(2),
   GoldenRaspberry(2),
   Coconut(2),
   Cashew(0),
   Avacado(2),
   Nutmeg(0),
   Allspice(0),
   Chilli(2),
   StarAnise(0),
   Mango(4),
   Starfruit(2),
   Candlenut(0);

   IIcon icon;
   int hunger;
   private List ores;

   private Food() {
      this(0);
   }

   private Food(int hunger) {
      this.ores = new ArrayList();
      this.hunger = hunger;
   }

   public boolean isEdible() {
      return this.hunger > 0;
   }

   public int getHealth() {
      return this.hunger;
   }

   public boolean isActive() {
      return true;
   }

   public String getName(ItemStack stack) {
      return ExtraTrees.proxy.localise("item.food." + this.name().toLowerCase());
   }

   public ItemStack get(int i) {
      return new ItemStack(ExtraTrees.itemFood, i, this.ordinal());
   }

   public IIcon getIcon(ItemStack stack) {
      return this.icon;
   }

   public void registerIcons(IIconRegister register) {
      this.icon = ExtraTrees.proxy.getIcon(register, "food/" + this.toString());
   }

   public void addInformation(List par3List) {
   }

   public void addJuice(Juice juice, int time, int amount, int mulch) {
      RecipeManagers.squeezerManager.addRecipe(time, new ItemStack[]{this.get(1)}, Binnie.Liquid.getLiquidStack("juice", amount), Mods.Forestry.stack("mulch"), mulch);
   }

   public void addJuice(int time, int amount, int mulch) {
      RecipeManagers.squeezerManager.addRecipe(time, new ItemStack[]{this.get(1)}, Binnie.Liquid.getLiquidStack("juice", amount), Mods.Forestry.stack("mulch"), mulch);
   }

   public void addOil(int time, int amount, int mulch) {
      RecipeManagers.squeezerManager.addRecipe(time, new ItemStack[]{this.get(1)}, Binnie.Liquid.getLiquidStack("seedoil", amount), Mods.Forestry.stack("mulch"), mulch);
   }

   public static void registerOreDictionary() {
      Crabapple.ore("Apple").ore("Crabapple");
      Orange.ore("Orange");
      Kumquat.ore("Kumquat");
      Lime.ore("Lime");
      WildCherry.ore("Cherry").ore("WildCherry");
      SourCherry.ore("Cherry").ore("SourCherry");
      BlackCherry.ore("Cherry").ore("BlackCherry");
      Blackthorn.ore("Blackthorn");
      CherryPlum.ore("Plum").ore("CherryPlum");
      Almond.ore("Almond");
      Apricot.ore("Apricot");
      Grapefruit.ore("Grapefruit");
      Peach.ore("Peach");
      Satsuma.ore("Satsuma").ore("Orange");
      BuddhaHand.ore("BuddhaHand").ore("Citron");
      Citron.ore("Citron");
      FingerLime.ore("Lime").ore("FingerLime");
      KeyLime.ore("KeyLime").ore("Lime");
      Manderin.ore("Orange").ore("Manderin");
      Nectarine.ore("Peach").ore("Nectarine");
      Pomelo.ore("Pomelo");
      Tangerine.ore("Tangerine").ore("Orange");
      Pear.ore("Pear");
      SandPear.ore("SandPear");
      Hazelnut.ore("Hazelnut");
      Butternut.ore("Butternut").ore("Walnut");
      Beechnut.ore("Beechnut");
      Pecan.ore("Pecan");
      Banana.ore("Banana");
      RedBanana.ore("RedBanana").ore("Banana");
      Plantain.ore("Plantain");
      BrazilNut.ore("BrazilNut");
      Fig.ore("Fig");
      Acorn.ore("Acorn");
      Elderberry.ore("Elderberry");
      Olive.ore("Olive");
      GingkoNut.ore("GingkoNut");
      Coffee.ore("Coffee");
      OsangeOrange.ore("OsangeOrange");
      Clove.ore("Clove");
   }

   private Food ore(String string) {
      OreDictionary.registerOre("crop" + string, this.get(1));
      this.ores.add("crop" + string);
      return this;
   }

   public Collection getOres() {
      return this.ores;
   }
}
