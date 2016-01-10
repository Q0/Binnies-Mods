package binnie.extratrees.alcohol;

import binnie.extratrees.alcohol.Alcohol;
import binnie.extratrees.alcohol.Glassware;
import binnie.extratrees.alcohol.ICocktailIngredient;
import binnie.extratrees.alcohol.Juice;
import binnie.extratrees.alcohol.Liqueur;
import binnie.extratrees.alcohol.MiscFluid;
import binnie.extratrees.alcohol.Spirit;
import binnie.extratrees.alcohol.drink.DrinkLiquid;
import binnie.extratrees.alcohol.drink.DrinkManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public enum Cocktail {
   Bellini("Bellini", Glassware.Flute, 15974764),
   BlackRussian("Black Russian", Glassware.OldFashioned, 3347992),
   BloodyMary("Bloody Mary", Glassware.Highball, 12660480),
   Cosmopolitan("Cosmopolitan", Glassware.Cocktail, 14811136),
   CubaLibre("Cuba Libre", Glassware.Highball, 9044996),
   FrenchConnection("French Connection", Glassware.OldFashioned, 16166183),
   GodFather("God Father", Glassware.OldFashioned, 13134109),
   GodMother("God Mother", Glassware.OldFashioned, 14644737),
   Grasshopper("Grasshopper", Glassware.Cocktail, 4441194),
   French75("French 75", Glassware.Flute, 15521949),
   HarveyWallbanger("Harvey Wallbanger", Glassware.Highball, 16371968),
   HemingwaySpecial("Hemingway Special", Glassware.Cocktail, 15201205),
   HorsesNeck("Horse\'s Neck", Glassware.OldFashioned, 16692480),
   IrishCoffee("Irish Coffee", Glassware.Wine, 4460034),
   Kir("Kir", Glassware.Wine, 16004638),
   Bramble("Bramble", Glassware.OldFashioned, 12804974),
   B52("B-52", Glassware.Shot, 12934656),
   DarkNStormy("Dark \'N\' Stormy", Glassware.Highball, 12215848),
   DirtyMartini("Dirty Martini", Glassware.Cocktail, 14332499),
   ExpressoMartini("Expresso Martini", Glassware.Cocktail, 6498346),
   FrenchMartini("French Martini", Glassware.Cocktail, 12660045),
   Kamikaze("Kamikaze", Glassware.Cocktail, 14801069),
   LemonDropMartini("Lemon Drop Martini", Glassware.Cocktail, 16375437),
   PiscoSour("Pisco Sour", Glassware.OldFashioned, 15394463),
   RussianSpringPunch("Russian Spring Punch", Glassware.Highball, 11805740),
   SpritzVeneziano("Spritz Veneziano", Glassware.OldFashioned, 15355648),
   TommysMargarita("Tommy\'s Margartita", Glassware.Cocktail, 14867592),
   Vesper("Vesper", Glassware.Cocktail, 15658732),
   SexOnTheBeach("Sex on the Beach", Glassware.Highball, 16677426);

   static final Map cocktailIngredients = new HashMap();
   public String name;
   public Glassware glassware;
   public int colour;
   public Map ingredients = new HashMap();

   private Cocktail(String name, Glassware glassware, int colour) {
      this.name = name;
      this.glassware = glassware;
      this.colour = colour;
   }

   private void add(ICocktailIngredient ingredient, int ratio) {
      this.ingredients.put(ingredient, Integer.valueOf(ratio));
   }

   public static Cocktail get(Map ingredients) {
      for(Cocktail cocktail : values()) {
         boolean is = true;

         for(Entry<ICocktailIngredient, Integer> entry : ingredients.entrySet()) {
            if(cocktail.ingredients.get(entry.getKey()) != entry.getValue()) {
               is = false;
            }
         }

         if(is) {
            return cocktail;
         }
      }

      return null;
   }

   public static void registerIngredient(ICocktailIngredient ingredient) {
      cocktailIngredients.put(ingredient.getIdentifier().toLowerCase(), ingredient);
      DrinkManager.registerDrinkLiquid(ingredient.getIdentifier().toLowerCase(), new DrinkLiquid(ingredient.getName(), ingredient.getColour(), (float)ingredient.getTransparency(), ingredient.getABV()));
   }

   public static ICocktailIngredient getIngredient(String name2) {
      return (ICocktailIngredient)cocktailIngredients.get(name2.toLowerCase());
   }

   public static boolean isIngredient(String name) {
      return name != null && cocktailIngredients.containsKey(name.toLowerCase());
   }

   static {
      Bellini.add(Alcohol.SparklingWine, 2);
      Bellini.add(Juice.Peach, 1);
      BlackRussian.add(Spirit.Vodka, 5);
      BlackRussian.add(Liqueur.Coffee, 2);
      BloodyMary.add(Spirit.Vodka, 3);
      BloodyMary.add(Juice.Tomato, 6);
      BloodyMary.add(Juice.Lemon, 1);
      Cosmopolitan.add(Spirit.Vodka, 3);
      Cosmopolitan.add(Liqueur.Orange, 1);
      Cosmopolitan.add(Juice.Lime, 1);
      Cosmopolitan.add(Juice.Cranberry, 2);
      CubaLibre.add(MiscFluid.CarbonatedWater, 12);
      CubaLibre.add(Spirit.WhiteRum, 5);
      CubaLibre.add(Juice.Lime, 1);
      FrenchConnection.add(Spirit.Brandy, 1);
      FrenchConnection.add(Liqueur.Almond, 1);
      GodFather.add(Spirit.Whiskey, 1);
      GodFather.add(Liqueur.Almond, 1);
      GodMother.add(Spirit.Vodka, 1);
      GodFather.add(Liqueur.Almond, 1);
      Grasshopper.add(Liqueur.Mint, 1);
      Grasshopper.add(Liqueur.Chocolate, 1);
      Grasshopper.add(MiscFluid.Cream, 1);
      French75.add(Spirit.Gin, 2);
      French75.add(Juice.Lemon, 1);
      French75.add(Alcohol.SparklingWine, 4);
      HarveyWallbanger.add(Spirit.Vodka, 3);
      HarveyWallbanger.add(Liqueur.Herbal, 1);
      HarveyWallbanger.add(Juice.Orange, 6);
      HemingwaySpecial.add(Spirit.WhiteRum, 4);
      HemingwaySpecial.add(Juice.Grapefruit, 3);
      HemingwaySpecial.add(Liqueur.Cherry, 1);
      HemingwaySpecial.add(Juice.Lime, 1);
      HorsesNeck.add(Spirit.Brandy, 1);
      HorsesNeck.add(MiscFluid.GingerAle, 3);
      IrishCoffee.add(Spirit.Whiskey, 2);
      IrishCoffee.add(MiscFluid.Coffee, 4);
      IrishCoffee.add(MiscFluid.Cream, 2);
      Kir.add(Alcohol.WhiteWine, 9);
      Kir.add(Liqueur.Blackcurrant, 1);
      Bramble.add(Spirit.Gin, 4);
      Bramble.add(Juice.Lemon, 2);
      Bramble.add(MiscFluid.SugarSyrup, 1);
      Bramble.add(Liqueur.Blackberry, 2);
      B52.add(Liqueur.Coffee, 1);
      B52.add(Liqueur.Orange, 1);
      DarkNStormy.add(Spirit.DarkRum, 1);
      DarkNStormy.add(MiscFluid.GingerAle, 3);
      DirtyMartini.add(Spirit.Vodka, 6);
      DirtyMartini.add(Spirit.FortifiedWine, 1);
      DirtyMartini.add(Juice.Olive, 1);
      ExpressoMartini.add(Spirit.Vodka, 5);
      ExpressoMartini.add(Liqueur.Coffee, 1);
      ExpressoMartini.add(MiscFluid.SugarSyrup, 1);
      ExpressoMartini.add(MiscFluid.Coffee, 1);
      FrenchMartini.add(Spirit.Vodka, 3);
      FrenchMartini.add(Liqueur.Raspberry, 1);
      FrenchMartini.add(Juice.Pineapple, 1);
      Kamikaze.add(Spirit.Vodka, 1);
      Kamikaze.add(Liqueur.Orange, 1);
      Kamikaze.add(Juice.Lime, 1);
      LemonDropMartini.add(Spirit.Vodka, 5);
      LemonDropMartini.add(Liqueur.Orange, 4);
      LemonDropMartini.add(Juice.Lemon, 3);
      PiscoSour.add(Spirit.Brandy, 8);
      PiscoSour.add(Juice.Lemon, 4);
      PiscoSour.add(MiscFluid.SugarSyrup, 3);
      RussianSpringPunch.add(Spirit.Vodka, 5);
      RussianSpringPunch.add(Juice.Lemon, 5);
      RussianSpringPunch.add(Liqueur.Blackcurrant, 3);
      RussianSpringPunch.add(MiscFluid.SugarSyrup, 1);
      SpritzVeneziano.add(Alcohol.SparklingWine, 6);
      SpritzVeneziano.add(MiscFluid.CarbonatedWater, 1);
      TommysMargarita.add(Spirit.Tequila, 6);
      TommysMargarita.add(Juice.Lime, 2);
      TommysMargarita.add(MiscFluid.AgaveNectar, 1);
      Vesper.add(Spirit.Gin, 8);
      Vesper.add(Spirit.Vodka, 2);
      Vesper.add(Spirit.FortifiedWine, 1);
      SexOnTheBeach.add(Spirit.Vodka, 2);
      SexOnTheBeach.add(Liqueur.Peach, 1);
      SexOnTheBeach.add(Juice.Orange, 2);
      SexOnTheBeach.add(Juice.Cranberry, 2);
   }
}
