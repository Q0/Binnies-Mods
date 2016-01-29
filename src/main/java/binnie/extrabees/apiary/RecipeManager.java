package binnie.extrabees.apiary;

import binnie.core.Mods;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RecipeManager {

    //---------------------------------------------------------------------------
    //
    // CONSTRUCTOR
    //
    //---------------------------------------------------------------------------

    public RecipeManager() {

    }

    //---------------------------------------------------------------------------
    //
    // HANDLERS
    //
    //---------------------------------------------------------------------------

    //---------------------------------------------------------------------------
    //
    // PRIVATE METHODS
    //
    //---------------------------------------------------------------------------

    //---------------------------------------------------------------------------
    //
    // PUBLIC METHODS
    //
    //---------------------------------------------------------------------------

    public static void registerRecipes() {
        GameRegistry.addRecipe(
                new ItemStack(ItemManager.cocoaFrame),
                " c ",
                "cFc",
                " c ",
                'F', Mods.Forestry.stack("frameImpregnated"),
                'c', new ItemStack(Items.dye, 1, 3)
        );

        GameRegistry.addShapelessRecipe(
                new ItemStack(ItemManager.cageFrame),
                Mods.Forestry.stack("frameImpregnated"),
                Blocks.iron_bars
        );

        GameRegistry.addShapelessRecipe(
                new ItemStack(ItemManager.soulFrame),
                Mods.Forestry.stack("frameImpregnated"),
                Blocks.soul_sand
        );

        GameRegistry.addRecipe(
                new ItemStack(ItemManager.clayFrame),
                " c ",
                "cFc",
                " c ",
                'F', Mods.Forestry.stack("frameImpregnated"),
                'c', Items.clay_ball
        );

        GameRegistry.addRecipe(
                new ItemStack(ItemManager.cocoaFrame),
                " c ",
                "cFc",
                " c ",
                'F', Mods.Forestry.stack("frameImpregnated"),
                'c', new ItemStack(Items.dye, 1, 3)
        );

        GameRegistry.addShapelessRecipe(
                new ItemStack(ItemManager.cageFrame),
                Mods.Forestry.stack("frameImpregnated"),
                Blocks.iron_bars
        );

        GameRegistry.addShapelessRecipe(
                new ItemStack(ItemManager.soulFrame),
                Mods.Forestry.stack("frameImpregnated"),
                Blocks.soul_sand
        );

        GameRegistry.addRecipe(
                new ItemStack(ItemManager.clayFrame),
                " c ",
                "cFc",
                " c ",
                'F', Mods.Forestry.stack("frameImpregnated"),
                'c', Items.clay_ball
        );

        GameRegistry.addRecipe(
                AlvearyMachine.Mutator.get(1),
                "g g",
                " a ",
                "t t",
                'g', Items.gold_ingot,
                'a', Mods.Forestry.block("alveary"),
                't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 5)
        );

        GameRegistry.addRecipe(
                AlvearyMachine.Frame.get(1),
                "iii",
                "tat",
                " t ",
                'i', Items.iron_ingot,
                'a', Mods.Forestry.block("alveary"),
                't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 4)
        );

        GameRegistry.addRecipe(
                AlvearyMachine.RainShield.get(1),
                " b ",
                "bab",
                "t t",
                'b', Items.brick,
                'a', Mods.Forestry.block("alveary"),
                't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 4)
        );

        GameRegistry.addRecipe(
                AlvearyMachine.Lighting.get(1),
                "iii",
                "iai",
                " t ",
                'i', Items.glowstone_dust,
                'a', Mods.Forestry.block("alveary"),
                't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 4)
        );

        GameRegistry.addRecipe(
                AlvearyMachine.Stimulator.get(1),
                "kik",
                "iai",
                " t ",
                'i', Items.gold_nugget,
                'a', Mods.Forestry.block("alveary"),
                't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 4),
                'k', new ItemStack(Mods.Forestry.item("chipsets"), 1, 2)
        );

        GameRegistry.addRecipe(
                AlvearyMachine.Hatchery.get(1),
                "i i",
                " a ",
                "iti",
                'i', Blocks.glass_pane,
                'a', Mods.Forestry.block("alveary"),
                't', new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, 5)
        );

        GameRegistry.addRecipe(
                new ShapedOreRecipe(AlvearyMachine.Transmission.get(1),
                        " t ",
                        "tat",
                        " t ",
                        'a', Mods.Forestry.block("alveary"),
                        't', "gearTin"
                )
        );
    }

    //---------------------------------------------------------------------------
    //
    // PUBLIC ACCESSORS
    //
    //---------------------------------------------------------------------------
}
