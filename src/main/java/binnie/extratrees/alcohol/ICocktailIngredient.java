package binnie.extratrees.alcohol;

public interface ICocktailIngredient {
   String getName();

   String getIdentifier();

   int getColour();

   int getTransparency();

   String getTooltip(int var1);

   float getABV();
}
