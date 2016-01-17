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
        
        GameRegistry.addRecipe(
            new ItemStack(EnumHiveFrame.Cocoa.item),
            " c ",
            "cFc",
            " c ",
            'F', Mods.Forestry.stack("frameImpregnated"),
            'c', new ItemStack(Items.dye, 1, 3)
        );

        GameRegistry.addShapelessRecipe(
            new ItemStack(EnumHiveFrame.Cage.item),
            Mods.Forestry.stack("frameImpregnated"),
            Blocks.iron_bars
        );

        GameRegistry.addShapelessRecipe(
            new ItemStack(EnumHiveFrame.Soul.item),
            Mods.Forestry.stack("frameImpregnated"),
            Blocks.soul_sand
        );

        GameRegistry.addRecipe(
            new ItemStack(EnumHiveFrame.Clay.item),
            " c ",
            "cFc",
            " c ",
            'F', Mods.Forestry.stack("frameImpregnated"),
            'c', Items.clay_ball
        );
    }

    public int getIconIndex() {
        return 55 + ordinal();
    }

    public void setMaxDamage(final int damage) {
        maxDamage = damage;
    }

    EnumHiveFrame() {
        maxDamage = 240;
        logic = new BeeModifierLogic();
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
        return logic.getModifier(EnumBeeModifier.Territory, currentModifier);
    }

    public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return logic.getModifier(EnumBeeModifier.Mutation, currentModifier);
    }

    public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return logic.getModifier(EnumBeeModifier.Lifespan, currentModifier);
    }

    public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
        return logic.getModifier(EnumBeeModifier.Production, currentModifier);
    }

    public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
        return logic.getModifier(EnumBeeModifier.Flowering, currentModifier);
    }

    public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
        return logic.getModifier(EnumBeeModifier.GeneticDecay, currentModifier);
    }

    public boolean isSealed() {
        return logic.getModifier(EnumBeeBooleanModifier.Sealed);
    }

    public boolean isSelfLighted() {
        return logic.getModifier(EnumBeeBooleanModifier.SelfLighted);
    }

    public boolean isSunlightSimulated() {
        return logic.getModifier(EnumBeeBooleanModifier.SunlightStimulated);
    }

    public boolean isHellish() {
        return logic.getModifier(EnumBeeBooleanModifier.Hellish);
    }

    public String getName() {
        return ExtraBees.proxy.localise("item.frame." + toString().toLowerCase());
    }
}
