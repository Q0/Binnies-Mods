package binnie.extrabees.apiary;

import binnie.core.Mods;
import binnie.core.genetics.BeeModifierLogic;
import binnie.core.genetics.EnumBeeBooleanModifier;
import binnie.core.genetics.EnumBeeModifier;
import binnie.extrabees.ExtraBees;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum EnumHiveFrame implements IHiveFrame {
    Cocoa,
    Cage,
    Soul,
    Clay,
    Debug;

    Item item;
    int maxDamage;
    BeeModifierLogic logic;

    public static void init() {
        EnumHiveFrame.Cocoa.logic.setModifier(EnumBeeModifier.Lifespan, 0.75f, 0.25f);
        EnumHiveFrame.Cocoa.logic.setModifier(EnumBeeModifier.Production, 1.5f, 5.0f);
        EnumHiveFrame.Cage.logic.setModifier(EnumBeeModifier.Territory, 0.5f, 0.1f);
        EnumHiveFrame.Cage.logic.setModifier(EnumBeeModifier.Lifespan, 0.75f, 0.5f);
        EnumHiveFrame.Cage.logic.setModifier(EnumBeeModifier.Production, 0.75f, 0.5f);
        EnumHiveFrame.Soul.logic.setModifier(EnumBeeModifier.Mutation, 1.5f, 5.0f);
        EnumHiveFrame.Soul.logic.setModifier(EnumBeeModifier.Lifespan, 0.75f, 0.5f);
        EnumHiveFrame.Soul.logic.setModifier(EnumBeeModifier.Production, 0.25f, 0.1f);
        EnumHiveFrame.Soul.setMaxDamage(80);
        EnumHiveFrame.Clay.logic.setModifier(EnumBeeModifier.Lifespan, 1.5f, 5.0f);
        EnumHiveFrame.Clay.logic.setModifier(EnumBeeModifier.Mutation, 0.5f, 0.2f);
        EnumHiveFrame.Clay.logic.setModifier(EnumBeeModifier.Production, 0.75f, 0.2f);
        EnumHiveFrame.Debug.logic.setModifier(EnumBeeModifier.Lifespan, 1.0E-4f, 1.0E-4f);
        GameRegistry.addRecipe(new ItemStack(EnumHiveFrame.Cocoa.item), new Object[]{" c ", "cFc", " c ", 'F', Mods.Forestry.stack("frameImpregnated"), 'c', new ItemStack(Items.dye, 1, 3)});
        GameRegistry.addShapelessRecipe(new ItemStack(EnumHiveFrame.Cage.item), new Object[]{Mods.Forestry.stack("frameImpregnated"), Blocks.iron_bars});
        GameRegistry.addShapelessRecipe(new ItemStack(EnumHiveFrame.Soul.item), new Object[]{Mods.Forestry.stack("frameImpregnated"), Blocks.soul_sand});
        GameRegistry.addRecipe(new ItemStack(EnumHiveFrame.Clay.item), new Object[]{" c ", "cFc", " c ", 'F', Mods.Forestry.stack("frameImpregnated"), 'c', Items.clay_ball});
    }

    public int getIconIndex() {
        return 55 + this.ordinal();
    }

    public void setMaxDamage(final int damage) {
        this.maxDamage = damage;
    }

    private EnumHiveFrame() {
        this.maxDamage = 240;
        this.logic = new BeeModifierLogic();
    }

    @Override
    public IBeeModifier getBeeModifier() {
        return null;
    }

    public ItemStack frameUsed(final IBeeHousing house, final ItemStack frame, final IBee queen, final int wear) {
        frame.setItemDamage(frame.getItemDamage() + wear);
        if (frame.getItemDamage() >= frame.getMaxDamage()) {
            return null;
        }
        return frame;
    }

    public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
        return this.logic.getModifier(EnumBeeModifier.Territory, currentModifier);
    }

    public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return this.logic.getModifier(EnumBeeModifier.Mutation, currentModifier);
    }

    public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return this.logic.getModifier(EnumBeeModifier.Lifespan, currentModifier);
    }

    public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
        return this.logic.getModifier(EnumBeeModifier.Production, currentModifier);
    }

    public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
        return this.logic.getModifier(EnumBeeModifier.Flowering, currentModifier);
    }

    public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
        return this.logic.getModifier(EnumBeeModifier.GeneticDecay, currentModifier);
    }

    public boolean isSealed() {
        return this.logic.getModifier(EnumBeeBooleanModifier.Sealed);
    }

    public boolean isSelfLighted() {
        return this.logic.getModifier(EnumBeeBooleanModifier.SelfLighted);
    }

    public boolean isSunlightSimulated() {
        return this.logic.getModifier(EnumBeeBooleanModifier.SunlightStimulated);
    }

    public boolean isHellish() {
        return this.logic.getModifier(EnumBeeBooleanModifier.Hellish);
    }

    public String getName() {
        return ExtraBees.proxy.localise("item.frame." + this.toString().toLowerCase());
    }
}
