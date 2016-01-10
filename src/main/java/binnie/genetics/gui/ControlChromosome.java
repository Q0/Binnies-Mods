package binnie.genetics.gui;

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.core.texture.BinnieCoreTexture;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.genetics.gui.ControlChromoPicker;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.EnumButterflyChromosome;

public class ControlChromosome extends Control implements IControlValue {
   Texture BeeTexture = new StandardTexture(0, 0, 96, 96, BinnieCoreTexture.GUIBreeding);
   Texture TreeTexture = new StandardTexture(96, 0, 96, 96, BinnieCoreTexture.GUIBreeding);
   Texture MothTexture = new StandardTexture(96, 96, 96, 96, BinnieCoreTexture.GUIBreeding);
   Texture FlowerTexture = new StandardTexture(0, 96, 96, 96, BinnieCoreTexture.GUIBreeding);
   IChromosomeType chromo = null;
   ISpeciesRoot species = null;

   public ControlChromosome(IWidget parent, float x, float y) {
      super(parent, x, y, 96.0F, 96.0F);
   }

   public ISpeciesRoot getRoot() {
      return this.species;
   }

   public void setRoot(ISpeciesRoot root) {
      if(root != this.species) {
         this.species = root;
         this.deleteAllChildren();
         if(root == Binnie.Genetics.getBeeRoot()) {
            new ControlChromoPicker(this, 28.0F, 47.0F, EnumBeeChromosome.SPECIES);
            new ControlChromoPicker(this, 28.0F, 72.0F, EnumBeeChromosome.SPEED);
            new ControlChromoPicker(this, 19.0F, 20.0F, EnumBeeChromosome.LIFESPAN);
            new ControlChromoPicker(this, 55.0F, 65.0F, EnumBeeChromosome.FERTILITY);
            new ControlChromoPicker(this, 28.0F, 1.0F, EnumBeeChromosome.TEMPERATURE_TOLERANCE);
            new ControlChromoPicker(this, 61.0F, 37.0F, EnumBeeChromosome.NOCTURNAL);
            new ControlChromoPicker(this, 81.0F, 76.0F, EnumBeeChromosome.HUMIDITY_TOLERANCE);
            new ControlChromoPicker(this, 44.0F, 21.0F, EnumBeeChromosome.TOLERANT_FLYER);
            new ControlChromoPicker(this, 3.0F, 37.0F, EnumBeeChromosome.CAVE_DWELLING);
            new ControlChromoPicker(this, 4.0F, 65.0F, EnumBeeChromosome.FLOWER_PROVIDER);
            new ControlChromoPicker(this, 83.0F, 27.0F, EnumBeeChromosome.FLOWERING);
            new ControlChromoPicker(this, 71.0F, 10.0F, EnumBeeChromosome.TERRITORY);
            new ControlChromoPicker(this, 84.0F, 54.0F, EnumBeeChromosome.EFFECT);
         } else if(root == Binnie.Genetics.getTreeRoot()) {
            new ControlChromoPicker(this, 48.0F, 48.0F, EnumTreeChromosome.SPECIES);
            new ControlChromoPicker(this, 43.0F, 12.0F, EnumTreeChromosome.GROWTH);
            new ControlChromoPicker(this, 43.0F, 84.0F, EnumTreeChromosome.HEIGHT);
            new ControlChromoPicker(this, 25.0F, 63.0F, EnumTreeChromosome.FERTILITY);
            new ControlChromoPicker(this, 72.0F, 57.0F, EnumTreeChromosome.FRUITS);
            new ControlChromoPicker(this, 21.0F, 43.0F, EnumTreeChromosome.YIELD);
            new ControlChromoPicker(this, 38.0F, 32.0F, EnumTreeChromosome.PLANT);
            new ControlChromoPicker(this, 15.0F, 17.0F, EnumTreeChromosome.SAPPINESS);
            new ControlChromoPicker(this, 75.0F, 78.0F, EnumTreeChromosome.TERRITORY);
            new ControlChromoPicker(this, 67.0F, 15.0F, EnumTreeChromosome.EFFECT);
            new ControlChromoPicker(this, 70.0F, 34.0F, EnumTreeChromosome.MATURATION);
            new ControlChromoPicker(this, 45.0F, 67.0F, EnumTreeChromosome.GIRTH);
            new ControlChromoPicker(this, 5.0F, 70.0F, EnumTreeChromosome.FIREPROOF);
         } else if(root == Binnie.Genetics.getFlowerRoot()) {
            new ControlChromoPicker(this, 35.0F, 81.0F, EnumFlowerChromosome.SPECIES);
            new ControlChromoPicker(this, 36.0F, 28.0F, EnumFlowerChromosome.PRIMARY);
            new ControlChromoPicker(this, 27.0F, 13.0F, EnumFlowerChromosome.SECONDARY);
            new ControlChromoPicker(this, 47.0F, 15.0F, EnumFlowerChromosome.FERTILITY);
            new ControlChromoPicker(this, 54.0F, 31.0F, EnumFlowerChromosome.TERRITORY);
            new ControlChromoPicker(this, 15.0F, 55.0F, EnumFlowerChromosome.EFFECT);
            new ControlChromoPicker(this, 23.0F, 38.0F, EnumFlowerChromosome.LIFESPAN);
            new ControlChromoPicker(this, 17.0F, 77.0F, EnumFlowerChromosome.TEMPERATURE_TOLERANCE);
            new ControlChromoPicker(this, 52.0F, 51.0F, EnumFlowerChromosome.HUMIDITY_TOLERANCE);
            new ControlChromoPicker(this, 54.0F, 80.0F, EnumFlowerChromosome.PH_TOLERANCE);
            new ControlChromoPicker(this, 41.0F, 42.0F, EnumFlowerChromosome.SAPPINESS);
            new ControlChromoPicker(this, 37.0F, 63.0F, EnumFlowerChromosome.STEM);
         } else if(root == Binnie.Genetics.getButterflyRoot()) {
            new ControlChromoPicker(this, 40.0F, 40.0F, EnumButterflyChromosome.SPECIES);
            new ControlChromoPicker(this, 63.0F, 32.0F, EnumButterflyChromosome.SIZE);
            new ControlChromoPicker(this, 32.0F, 63.0F, EnumButterflyChromosome.SPEED);
            new ControlChromoPicker(this, 11.0F, 27.0F, EnumButterflyChromosome.LIFESPAN);
            new ControlChromoPicker(this, 16.0F, 12.0F, EnumButterflyChromosome.METABOLISM);
            new ControlChromoPicker(this, 17.0F, 63.0F, EnumButterflyChromosome.FERTILITY);
            new ControlChromoPicker(this, 34.0F, 12.0F, EnumButterflyChromosome.TEMPERATURE_TOLERANCE);
            new ControlChromoPicker(this, 22.0F, 46.0F, EnumButterflyChromosome.HUMIDITY_TOLERANCE);
            new ControlChromoPicker(this, 53.0F, 26.0F, EnumButterflyChromosome.NOCTURNAL);
            new ControlChromoPicker(this, 71.0F, 53.0F, EnumButterflyChromosome.TOLERANT_FLYER);
            new ControlChromoPicker(this, 78.0F, 12.0F, EnumButterflyChromosome.FIRE_RESIST);
            new ControlChromoPicker(this, 55.0F, 55.0F, EnumButterflyChromosome.FLOWER_PROVIDER);
            new ControlChromoPicker(this, 27.0F, 31.0F, EnumButterflyChromosome.EFFECT);
            new ControlChromoPicker(this, 87.0F, 45.0F, EnumButterflyChromosome.TERRITORY);
         }
      }

   }

   public IChromosomeType getValue() {
      return this.chromo;
   }

   public void setValue(IChromosomeType value) {
      this.chromo = value;
   }

   public void onRenderBackground() {
      if(this.species != null) {
         super.onRenderBackground();
         Texture text = this.getTypeTexture();
         CraftGUI.Render.texture(text, IPoint.ZERO);
      }
   }

   private Texture getTypeTexture() {
      return this.species == Binnie.Genetics.getBeeRoot()?this.BeeTexture:(this.species == Binnie.Genetics.getTreeRoot()?this.TreeTexture:(this.species == Binnie.Genetics.getButterflyRoot()?this.MothTexture:(this.species == Binnie.Genetics.getFlowerRoot()?this.FlowerTexture:null)));
   }
}
