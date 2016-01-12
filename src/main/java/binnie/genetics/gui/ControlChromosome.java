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
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.EnumButterflyChromosome;

public class ControlChromosome extends Control implements IControlValue<IChromosomeType> {
    Texture BeeTexture;
    Texture TreeTexture;
    Texture MothTexture;
    Texture FlowerTexture;
    IChromosomeType chromo;
    ISpeciesRoot species;

    public ControlChromosome(final IWidget parent, final float x, final float y) {
        super(parent, x, y, 96.0f, 96.0f);
        this.BeeTexture = new StandardTexture(0, 0, 96, 96, BinnieCoreTexture.GUIBreeding);
        this.TreeTexture = new StandardTexture(96, 0, 96, 96, BinnieCoreTexture.GUIBreeding);
        this.MothTexture = new StandardTexture(96, 96, 96, 96, BinnieCoreTexture.GUIBreeding);
        this.FlowerTexture = new StandardTexture(0, 96, 96, 96, BinnieCoreTexture.GUIBreeding);
        this.chromo = null;
        this.species = null;
    }

    public ISpeciesRoot getRoot() {
        return this.species;
    }

    public void setRoot(final ISpeciesRoot root) {
        if (root != this.species) {
            this.species = root;
            this.deleteAllChildren();
            if (root == Binnie.Genetics.getBeeRoot()) {
                new ControlChromoPicker(this, 28.0f, 47.0f, (IChromosomeType) EnumBeeChromosome.SPECIES);
                new ControlChromoPicker(this, 28.0f, 72.0f, (IChromosomeType) EnumBeeChromosome.SPEED);
                new ControlChromoPicker(this, 19.0f, 20.0f, (IChromosomeType) EnumBeeChromosome.LIFESPAN);
                new ControlChromoPicker(this, 55.0f, 65.0f, (IChromosomeType) EnumBeeChromosome.FERTILITY);
                new ControlChromoPicker(this, 28.0f, 1.0f, (IChromosomeType) EnumBeeChromosome.TEMPERATURE_TOLERANCE);
                new ControlChromoPicker(this, 61.0f, 37.0f, (IChromosomeType) EnumBeeChromosome.NOCTURNAL);
                new ControlChromoPicker(this, 81.0f, 76.0f, (IChromosomeType) EnumBeeChromosome.HUMIDITY_TOLERANCE);
                new ControlChromoPicker(this, 44.0f, 21.0f, (IChromosomeType) EnumBeeChromosome.TOLERANT_FLYER);
                new ControlChromoPicker(this, 3.0f, 37.0f, (IChromosomeType) EnumBeeChromosome.CAVE_DWELLING);
                new ControlChromoPicker(this, 4.0f, 65.0f, (IChromosomeType) EnumBeeChromosome.FLOWER_PROVIDER);
                new ControlChromoPicker(this, 83.0f, 27.0f, (IChromosomeType) EnumBeeChromosome.FLOWERING);
                new ControlChromoPicker(this, 71.0f, 10.0f, (IChromosomeType) EnumBeeChromosome.TERRITORY);
                new ControlChromoPicker(this, 84.0f, 54.0f, (IChromosomeType) EnumBeeChromosome.EFFECT);
            } else if (root == Binnie.Genetics.getTreeRoot()) {
                new ControlChromoPicker(this, 48.0f, 48.0f, (IChromosomeType) EnumTreeChromosome.SPECIES);
                new ControlChromoPicker(this, 43.0f, 12.0f, (IChromosomeType) EnumTreeChromosome.GROWTH);
                new ControlChromoPicker(this, 43.0f, 84.0f, (IChromosomeType) EnumTreeChromosome.HEIGHT);
                new ControlChromoPicker(this, 25.0f, 63.0f, (IChromosomeType) EnumTreeChromosome.FERTILITY);
                new ControlChromoPicker(this, 72.0f, 57.0f, (IChromosomeType) EnumTreeChromosome.FRUITS);
                new ControlChromoPicker(this, 21.0f, 43.0f, (IChromosomeType) EnumTreeChromosome.YIELD);
                new ControlChromoPicker(this, 38.0f, 32.0f, (IChromosomeType) EnumTreeChromosome.PLANT);
                new ControlChromoPicker(this, 15.0f, 17.0f, (IChromosomeType) EnumTreeChromosome.SAPPINESS);
                new ControlChromoPicker(this, 75.0f, 78.0f, (IChromosomeType) EnumTreeChromosome.TERRITORY);
                new ControlChromoPicker(this, 67.0f, 15.0f, (IChromosomeType) EnumTreeChromosome.EFFECT);
                new ControlChromoPicker(this, 70.0f, 34.0f, (IChromosomeType) EnumTreeChromosome.MATURATION);
                new ControlChromoPicker(this, 45.0f, 67.0f, (IChromosomeType) EnumTreeChromosome.GIRTH);
                new ControlChromoPicker(this, 5.0f, 70.0f, (IChromosomeType) EnumTreeChromosome.FIREPROOF);
            } else if (root == Binnie.Genetics.getFlowerRoot()) {
                new ControlChromoPicker(this, 35.0f, 81.0f, (IChromosomeType) EnumFlowerChromosome.SPECIES);
                new ControlChromoPicker(this, 36.0f, 28.0f, (IChromosomeType) EnumFlowerChromosome.PRIMARY);
                new ControlChromoPicker(this, 27.0f, 13.0f, (IChromosomeType) EnumFlowerChromosome.SECONDARY);
                new ControlChromoPicker(this, 47.0f, 15.0f, (IChromosomeType) EnumFlowerChromosome.FERTILITY);
                new ControlChromoPicker(this, 54.0f, 31.0f, (IChromosomeType) EnumFlowerChromosome.TERRITORY);
                new ControlChromoPicker(this, 15.0f, 55.0f, (IChromosomeType) EnumFlowerChromosome.EFFECT);
                new ControlChromoPicker(this, 23.0f, 38.0f, (IChromosomeType) EnumFlowerChromosome.LIFESPAN);
                new ControlChromoPicker(this, 17.0f, 77.0f, (IChromosomeType) EnumFlowerChromosome.TEMPERATURE_TOLERANCE);
                new ControlChromoPicker(this, 52.0f, 51.0f, (IChromosomeType) EnumFlowerChromosome.HUMIDITY_TOLERANCE);
                new ControlChromoPicker(this, 54.0f, 80.0f, (IChromosomeType) EnumFlowerChromosome.PH_TOLERANCE);
                new ControlChromoPicker(this, 41.0f, 42.0f, (IChromosomeType) EnumFlowerChromosome.SAPPINESS);
                new ControlChromoPicker(this, 37.0f, 63.0f, (IChromosomeType) EnumFlowerChromosome.STEM);
            } else if (root == Binnie.Genetics.getButterflyRoot()) {
                new ControlChromoPicker(this, 40.0f, 40.0f, (IChromosomeType) EnumButterflyChromosome.SPECIES);
                new ControlChromoPicker(this, 63.0f, 32.0f, (IChromosomeType) EnumButterflyChromosome.SIZE);
                new ControlChromoPicker(this, 32.0f, 63.0f, (IChromosomeType) EnumButterflyChromosome.SPEED);
                new ControlChromoPicker(this, 11.0f, 27.0f, (IChromosomeType) EnumButterflyChromosome.LIFESPAN);
                new ControlChromoPicker(this, 16.0f, 12.0f, (IChromosomeType) EnumButterflyChromosome.METABOLISM);
                new ControlChromoPicker(this, 17.0f, 63.0f, (IChromosomeType) EnumButterflyChromosome.FERTILITY);
                new ControlChromoPicker(this, 34.0f, 12.0f, (IChromosomeType) EnumButterflyChromosome.TEMPERATURE_TOLERANCE);
                new ControlChromoPicker(this, 22.0f, 46.0f, (IChromosomeType) EnumButterflyChromosome.HUMIDITY_TOLERANCE);
                new ControlChromoPicker(this, 53.0f, 26.0f, (IChromosomeType) EnumButterflyChromosome.NOCTURNAL);
                new ControlChromoPicker(this, 71.0f, 53.0f, (IChromosomeType) EnumButterflyChromosome.TOLERANT_FLYER);
                new ControlChromoPicker(this, 78.0f, 12.0f, (IChromosomeType) EnumButterflyChromosome.FIRE_RESIST);
                new ControlChromoPicker(this, 55.0f, 55.0f, (IChromosomeType) EnumButterflyChromosome.FLOWER_PROVIDER);
                new ControlChromoPicker(this, 27.0f, 31.0f, (IChromosomeType) EnumButterflyChromosome.EFFECT);
                new ControlChromoPicker(this, 87.0f, 45.0f, (IChromosomeType) EnumButterflyChromosome.TERRITORY);
            }
        }
    }

    @Override
    public IChromosomeType getValue() {
        return this.chromo;
    }

    @Override
    public void setValue(final IChromosomeType value) {
        this.chromo = value;
    }

    @Override
    public void onRenderBackground() {
        if (this.species == null) {
            return;
        }
        super.onRenderBackground();
        final Texture text = this.getTypeTexture();
        CraftGUI.Render.texture(text, IPoint.ZERO);
    }

    private Texture getTypeTexture() {
        if (this.species == Binnie.Genetics.getBeeRoot()) {
            return this.BeeTexture;
        }
        if (this.species == Binnie.Genetics.getTreeRoot()) {
            return this.TreeTexture;
        }
        if (this.species == Binnie.Genetics.getButterflyRoot()) {
            return this.MothTexture;
        }
        if (this.species == Binnie.Genetics.getFlowerRoot()) {
            return this.FlowerTexture;
        }
        return null;
    }
}
