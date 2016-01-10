package binnie.botany.gardening;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.ceramic.*;
import binnie.botany.farm.CircuitGarden;
import binnie.botany.flower.ItemInsulatedTube;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.botany.items.ItemClay;
import binnie.botany.items.ItemPigment;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.block.ItemMetadata;
import binnie.core.block.ItemMetadataRenderer;
import binnie.core.block.MultipassItemRenderer;
import binnie.core.block.TileEntityMetadata;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.HashMap;
import java.util.Map.Entry;

public class ModuleGardening implements IInitializable {
    public static HashMap queuedAcidFertilisers = new HashMap();
    public static HashMap queuedAlkalineFertilisers = new HashMap();
    public static HashMap queuedNutrientFertilisers = new HashMap();

    public ModuleGardening() {
        super();
    }

    public void preInit() {
        Botany.soil = new BlockSoil(EnumSoilType.SOIL, "soil", false);
        Botany.loam = new BlockSoil(EnumSoilType.LOAM, "loam", false);
        Botany.flowerbed = new BlockSoil(EnumSoilType.FLOWERBED, "flowerbed", false);
        Botany.soilNoWeed = new BlockSoil(EnumSoilType.SOIL, "soilNoWeed", true);
        Botany.loamNoWeed = new BlockSoil(EnumSoilType.LOAM, "loamNoWeed", true);
        Botany.flowerbedNoWeed = new BlockSoil(EnumSoilType.FLOWERBED, "flowerbedNoWeed", true);
        Botany.plant = new BlockPlant();
        GameRegistry.registerBlock(Botany.plant, ItemWeed.class, "plant");
        GameRegistry.registerBlock(Botany.soil, ItemSoil.class, "soil");
        GameRegistry.registerBlock(Botany.loam, ItemSoil.class, "loam");
        GameRegistry.registerBlock(Botany.flowerbed, ItemSoil.class, "flowerbed");
        GameRegistry.registerBlock(Botany.soilNoWeed, ItemSoil.class, "soilNoWeed");
        GameRegistry.registerBlock(Botany.loamNoWeed, ItemSoil.class, "loamNoWeed");
        GameRegistry.registerBlock(Botany.flowerbedNoWeed, ItemSoil.class, "flowerbedNoWeed");
        Botany.soilMeter = new ItemSoilMeter();
        Botany.insulatedTube = new ItemInsulatedTube();
        Botany.trowelWood = new ItemTrowel(ToolMaterial.WOOD, "Wood");
        Botany.trowelStone = new ItemTrowel(ToolMaterial.STONE, "Stone");
        Botany.trowelIron = new ItemTrowel(ToolMaterial.IRON, "Iron");
        Botany.trowelDiamond = new ItemTrowel(ToolMaterial.EMERALD, "Diamond");
        Botany.trowelGold = new ItemTrowel(ToolMaterial.GOLD, "Gold");
        Botany.misc = Binnie.Item.registerMiscItems(BotanyItems.values(), CreativeTabBotany.instance);
        Botany.pigment = new ItemPigment();
        Botany.clay = new ItemClay();
        OreDictionary.registerOre("pigment", Botany.pigment);
        Botany.ceramic = new BlockCeramic();
        Botany.stained = new BlockStained();
        GameRegistry.registerBlock(Botany.ceramic, ItemMetadata.class, "ceramic");
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(Botany.ceramic), new ItemMetadataRenderer());
        GameRegistry.registerBlock(Botany.stained, ItemMetadata.class, "stained");
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(Botany.stained), new ItemMetadataRenderer());
        Botany.ceramicTile = new BlockCeramicPatterned();
        Botany.ceramicBrick = new BlockCeramicBrick();
        GameRegistry.registerBlock(Botany.ceramicTile, ItemMetadata.class, "ceramicPattern");
        GameRegistry.registerBlock(Botany.ceramicBrick, ItemMetadata.class, "ceramicBrick");
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(Botany.ceramicTile), new MultipassItemRenderer());
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(Botany.ceramicBrick), new MultipassItemRenderer());
    }

    public void init() {
        RecipeSorter.register("botany:ceramictile", CeramicTileRecipe.class, Category.SHAPED, "");
        RecipeSorter.register("botany:pigment", PigmentRecipe.class, Category.SHAPED, "");
        ItemStack yellow = new ItemStack(Blocks.yellow_flower, 1);
        ItemStack red = new ItemStack(Blocks.red_flower, 1);
        ItemStack blue = new ItemStack(Blocks.red_flower, 1, 7);

        for (boolean manual : new boolean[]{true, false}) {
            for (boolean fertilised : new boolean[]{true, false}) {
                for (EnumMoisture moist : EnumMoisture.values()) {
                    ItemStack icon = moist == EnumMoisture.Dry ? yellow : (moist == EnumMoisture.Normal ? red : blue);
                    int insulate = 2 - moist.ordinal();
                    if (fertilised) {
                        insulate += 3;
                    }

                    new CircuitGarden(moist, (EnumAcidity) null, manual, fertilised, new ItemStack(Botany.insulatedTube, 1, 0 + 128 * insulate), icon);
                    new CircuitGarden(moist, EnumAcidity.Acid, manual, fertilised, new ItemStack(Botany.insulatedTube, 1, 1 + 128 * insulate), icon);
                    new CircuitGarden(moist, EnumAcidity.Neutral, manual, fertilised, new ItemStack(Botany.insulatedTube, 1, 2 + 128 * insulate), icon);
                    new CircuitGarden(moist, EnumAcidity.Alkaline, manual, fertilised, new ItemStack(Botany.insulatedTube, 1, 3 + 128 * insulate), icon);
                }
            }
        }

    }

    public void postInit() {
        GameRegistry.addRecipe(new CeramicTileRecipe());

        for (int mat = 0; mat < 4; ++mat) {
            for (int insulate = 0; insulate < 6; ++insulate) {
                ItemStack tubes = new ItemStack(Botany.insulatedTube, 2, mat + 128 * insulate);
                ItemStack insulateStack = ItemInsulatedTube.getInsulateStack(tubes);
                ItemStack forestryTube = new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, mat);
                GameRegistry.addShapelessRecipe(tubes, new Object[]{forestryTube, forestryTube, insulateStack});
            }
        }

        GameRegistry.addRecipe(new ShapedOreRecipe(Botany.trowelWood, new Object[]{"d  ", " x ", "  s", Character.valueOf('d'), Blocks.dirt, Character.valueOf('s'), "stickWood", Character.valueOf('x'), "plankWood"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(Botany.trowelStone, new Object[]{"d  ", " x ", "  s", Character.valueOf('d'), Blocks.dirt, Character.valueOf('s'), "stickWood", Character.valueOf('x'), "cobblestone"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(Botany.trowelIron, new Object[]{"d  ", " x ", "  s", Character.valueOf('d'), Blocks.dirt, Character.valueOf('s'), "stickWood", Character.valueOf('x'), "ingotIron"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(Botany.trowelGold, new Object[]{"d  ", " x ", "  s", Character.valueOf('d'), Blocks.dirt, Character.valueOf('s'), "stickWood", Character.valueOf('x'), "ingotGold"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(Botany.trowelDiamond, new Object[]{"d  ", " x ", "  s", Character.valueOf('d'), Blocks.dirt, Character.valueOf('s'), "stickWood", Character.valueOf('x'), "gemDiamond"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(Botany.soilMeter, new Object[]{" gg", " rg", "i  ", Character.valueOf('g'), "ingotGold", Character.valueOf('r'), "dustRedstone", Character.valueOf('i'), "ingotIron"}));
        GameRegistry.addShapelessRecipe(BotanyItems.Weedkiller.get(4), new Object[]{new ItemStack(Items.spider_eye), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds)});
        GameRegistry.addShapelessRecipe(BotanyItems.AshPowder.get(4), new Object[]{Mods.Forestry.stack("ash")});
        GameRegistry.addShapelessRecipe(BotanyItems.MulchPowder.get(4), new Object[]{Mods.Forestry.stack("mulch")});
        GameRegistry.addShapelessRecipe(BotanyItems.CompostPowder.get(4), new Object[]{Mods.Forestry.stack("fertilizerBio")});
        GameRegistry.addShapelessRecipe(BotanyItems.FertiliserPowder.get(4), new Object[]{Mods.Forestry.stack("fertilizerCompound")});
        GameRegistry.addShapelessRecipe(BotanyItems.PulpPowder.get(4), new Object[]{Mods.Forestry.stack("woodPulp")});
        GameRegistry.addRecipe(new ShapelessOreRecipe(BotanyItems.SulphurPowder.get(4), new Object[]{"dustSulphur"}));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Botany.pigment, 2, EnumFlowerColor.Black.ordinal()), new Object[]{"pigment", "pigment", "dyeBlack"}));
        queuedAcidFertilisers.put(BotanyItems.SulphurPowder.get(1), Integer.valueOf(1));
        queuedAcidFertilisers.put(BotanyItems.MulchPowder.get(1), Integer.valueOf(1));
        queuedAcidFertilisers.put(new ItemStack(GameRegistry.findItem("Forestry", "mulch")), Integer.valueOf(2));

        for (ItemStack stack : OreDictionary.getOres("dustSulfur")) {
            queuedAcidFertilisers.put(stack, Integer.valueOf(2));
        }

        queuedAlkalineFertilisers.put(BotanyItems.AshPowder.get(1), Integer.valueOf(1));
        queuedAlkalineFertilisers.put(BotanyItems.PulpPowder.get(1), Integer.valueOf(1));
        queuedAlkalineFertilisers.put(new ItemStack(GameRegistry.findItem("Forestry", "ash")), Integer.valueOf(2));
        queuedAlkalineFertilisers.put(new ItemStack(GameRegistry.findItem("Forestry", "woodPulp")), Integer.valueOf(2));
        queuedNutrientFertilisers.put(BotanyItems.CompostPowder.get(1), Integer.valueOf(1));
        queuedNutrientFertilisers.put(BotanyItems.FertiliserPowder.get(1), Integer.valueOf(1));
        queuedNutrientFertilisers.put(new ItemStack(GameRegistry.findItem("Forestry", "fertilizerBio")), Integer.valueOf(2));
        queuedNutrientFertilisers.put(new ItemStack(GameRegistry.findItem("Forestry", "fertilizerCompound")), Integer.valueOf(2));

        for (Entry<ItemStack, Integer> entry : queuedAcidFertilisers.entrySet()) {
            this.addAcidFertiliser((ItemStack) entry.getKey(), ((Integer) entry.getValue()).intValue());
        }

        for (Entry<ItemStack, Integer> entry : queuedAlkalineFertilisers.entrySet()) {
            this.addAlkalineFertiliser((ItemStack) entry.getKey(), ((Integer) entry.getValue()).intValue());
        }

        for (Entry<ItemStack, Integer> entry : queuedNutrientFertilisers.entrySet()) {
            this.addNutrientFertiliser((ItemStack) entry.getKey(), ((Integer) entry.getValue()).intValue());
        }

        GameRegistry.addRecipe(BotanyItems.Mortar.get(6), new Object[]{" c ", "cgc", " c ", Character.valueOf('c'), Items.clay_ball, Character.valueOf('g'), Blocks.gravel});

        for (EnumFlowerColor c : EnumFlowerColor.values()) {
            ItemStack clay = new ItemStack(Botany.clay, 1, c.ordinal());
            ItemStack pigment = new ItemStack(Botany.pigment, 1, c.ordinal());
            GameRegistry.addShapelessRecipe(clay, new Object[]{Items.clay_ball, Items.clay_ball, Items.clay_ball, pigment});
            GameRegistry.addSmelting(clay, TileEntityMetadata.getItemStack(Botany.ceramic, c.ordinal()), 0.0F);
            ItemStack glass = TileEntityMetadata.getItemStack(Botany.stained, c.ordinal());
            glass.stackSize = 4;
            GameRegistry.addShapedRecipe(glass, new Object[]{" g ", "gpg", " g ", Character.valueOf('g'), Blocks.glass, Character.valueOf('p'), pigment});
        }

        GameRegistry.addRecipe(new PigmentRecipe());
    }

    private ItemStack getStack(int type, int pH, int moisture) {
        return type >= 0 && type <= 2 && pH >= 0 && pH <= 2 && moisture >= 0 && moisture <= 2 ? new ItemStack(Gardening.getSoilBlock(EnumSoilType.values()[type]), 1, BlockSoil.getMeta(EnumAcidity.values()[pH], EnumMoisture.values()[moisture])) : null;
    }

    private void addAcidFertiliser(ItemStack stack, int strengthMax) {
        if (stack != null) {
            Gardening.fertiliserAcid.put(stack, Integer.valueOf(strengthMax));

            for (int moisture = 0; moisture < 3; ++moisture) {
                for (int pH = 0; pH < 3; ++pH) {
                    for (int type = 0; type < 3; ++type) {
                        int numOfBlocks = strengthMax * strengthMax;

                        for (int strength = 1; strength < strengthMax; ++strength) {
                            ItemStack start = this.getStack(type, pH, moisture);
                            ItemStack end = this.getStack(type, pH - strength, moisture);
                            if (end != null) {
                                end.stackSize = numOfBlocks;
                                Object[] stacks = new Object[numOfBlocks + 1];

                                for (int i = 0; i < numOfBlocks; ++i) {
                                    stacks[i] = start;
                                }

                                stacks[numOfBlocks] = stack.copy();
                                GameRegistry.addShapelessRecipe(end, stacks);
                            }

                            numOfBlocks /= 2;
                        }
                    }
                }
            }

        }
    }

    private void addAlkalineFertiliser(ItemStack stack, int strengthMax) {
        if (stack != null) {
            Gardening.fertiliserAlkaline.put(stack, Integer.valueOf(strengthMax));

            for (int moisture = 0; moisture < 3; ++moisture) {
                for (int pH = 0; pH < 3; ++pH) {
                    for (int type = 0; type < 3; ++type) {
                        int numOfBlocks = strengthMax * strengthMax;

                        for (int strength = 1; strength < strengthMax; ++strength) {
                            ItemStack start = this.getStack(type, pH, moisture);
                            ItemStack end = this.getStack(type, pH + strength, moisture);
                            if (end != null) {
                                end.stackSize = numOfBlocks;
                                Object[] stacks = new Object[numOfBlocks + 1];

                                for (int i = 0; i < numOfBlocks; ++i) {
                                    stacks[i] = start;
                                }

                                stacks[numOfBlocks] = stack.copy();
                                GameRegistry.addShapelessRecipe(end, stacks);
                            }

                            numOfBlocks /= 2;
                        }
                    }
                }
            }

        }
    }

    private void addNutrientFertiliser(ItemStack stack, int strengthMax) {
        if (stack != null) {
            Gardening.fertiliserNutrient.put(stack, Integer.valueOf(strengthMax));

            for (int moisture = 0; moisture < 3; ++moisture) {
                for (int pH = 0; pH < 3; ++pH) {
                    for (int type = 0; type < 3; ++type) {
                        int numOfBlocks = strengthMax * strengthMax;

                        for (int strength = 1; strength < strengthMax; ++strength) {
                            ItemStack start = this.getStack(type, pH, moisture);
                            ItemStack end = this.getStack(type + strength, pH, moisture);
                            if (end != null) {
                                end.stackSize = numOfBlocks;
                                Object[] stacks = new Object[numOfBlocks + 1];

                                for (int i = 0; i < numOfBlocks; ++i) {
                                    stacks[i] = start;
                                }

                                stacks[numOfBlocks] = stack.copy();
                                GameRegistry.addShapelessRecipe(end, stacks);
                            }

                            numOfBlocks /= 2;
                        }
                    }
                }
            }

        }
    }
}
