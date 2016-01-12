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
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.HashMap;
import java.util.Map;

public class ModuleGardening implements IInitializable {
    public static HashMap<ItemStack, Integer> queuedAcidFertilisers;
    public static HashMap<ItemStack, Integer> queuedAlkalineFertilisers;
    public static HashMap<ItemStack, Integer> queuedNutrientFertilisers;

    @Override
    public void preInit() {
        Botany.soil = new BlockSoil(EnumSoilType.SOIL, "soil", false);
        Botany.loam = new BlockSoil(EnumSoilType.LOAM, "loam", false);
        Botany.flowerbed = new BlockSoil(EnumSoilType.FLOWERBED, "flowerbed", false);
        Botany.soilNoWeed = new BlockSoil(EnumSoilType.SOIL, "soilNoWeed", true);
        Botany.loamNoWeed = new BlockSoil(EnumSoilType.LOAM, "loamNoWeed", true);
        Botany.flowerbedNoWeed = new BlockSoil(EnumSoilType.FLOWERBED, "flowerbedNoWeed", true);
        GameRegistry.registerBlock((Block) (Botany.plant = new BlockPlant()), (Class) ItemWeed.class, "plant");
        GameRegistry.registerBlock((Block) Botany.soil, (Class) ItemSoil.class, "soil");
        GameRegistry.registerBlock((Block) Botany.loam, (Class) ItemSoil.class, "loam");
        GameRegistry.registerBlock((Block) Botany.flowerbed, (Class) ItemSoil.class, "flowerbed");
        GameRegistry.registerBlock((Block) Botany.soilNoWeed, (Class) ItemSoil.class, "soilNoWeed");
        GameRegistry.registerBlock((Block) Botany.loamNoWeed, (Class) ItemSoil.class, "loamNoWeed");
        GameRegistry.registerBlock((Block) Botany.flowerbedNoWeed, (Class) ItemSoil.class, "flowerbedNoWeed");
        Botany.soilMeter = new ItemSoilMeter();
        Botany.insulatedTube = new ItemInsulatedTube();
        Botany.trowelWood = new ItemTrowel(Item.ToolMaterial.WOOD, "Wood");
        Botany.trowelStone = new ItemTrowel(Item.ToolMaterial.STONE, "Stone");
        Botany.trowelIron = new ItemTrowel(Item.ToolMaterial.IRON, "Iron");
        Botany.trowelDiamond = new ItemTrowel(Item.ToolMaterial.EMERALD, "Diamond");
        Botany.trowelGold = new ItemTrowel(Item.ToolMaterial.GOLD, "Gold");
        Botany.misc = Binnie.Item.registerMiscItems(BotanyItems.values(), CreativeTabBotany.instance);
        Botany.pigment = new ItemPigment();
        Botany.clay = new ItemClay();
        OreDictionary.registerOre("pigment", (Item) Botany.pigment);
        Botany.ceramic = new BlockCeramic();
        Botany.stained = new BlockStained();
        GameRegistry.registerBlock((Block) Botany.ceramic, (Class) ItemMetadata.class, "ceramic");
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock((Block) Botany.ceramic), (IItemRenderer) new ItemMetadataRenderer());
        GameRegistry.registerBlock((Block) Botany.stained, (Class) ItemMetadata.class, "stained");
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock((Block) Botany.stained), (IItemRenderer) new ItemMetadataRenderer());
        Botany.ceramicTile = new BlockCeramicPatterned();
        Botany.ceramicBrick = new BlockCeramicBrick();
        GameRegistry.registerBlock((Block) Botany.ceramicTile, (Class) ItemMetadata.class, "ceramicPattern");
        GameRegistry.registerBlock((Block) Botany.ceramicBrick, (Class) ItemMetadata.class, "ceramicBrick");
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock((Block) Botany.ceramicTile), (IItemRenderer) new MultipassItemRenderer());
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock((Block) Botany.ceramicBrick), (IItemRenderer) new MultipassItemRenderer());
    }

    @Override
    public void init() {
        RecipeSorter.register("botany:ceramictile", (Class) CeramicTileRecipe.class, RecipeSorter.Category.SHAPED, "");
        RecipeSorter.register("botany:pigment", (Class) PigmentRecipe.class, RecipeSorter.Category.SHAPED, "");
        final ItemStack yellow = new ItemStack((Block) Blocks.yellow_flower, 1);
        final ItemStack red = new ItemStack((Block) Blocks.red_flower, 1);
        final ItemStack blue = new ItemStack((Block) Blocks.red_flower, 1, 7);
        for (final boolean manual : new boolean[]{true, false}) {
            for (final boolean fertilised : new boolean[]{true, false}) {
                for (final EnumMoisture moist : EnumMoisture.values()) {
                    final ItemStack icon = (moist == EnumMoisture.Dry) ? yellow : ((moist == EnumMoisture.Normal) ? red : blue);
                    int insulate = 2 - moist.ordinal();
                    if (fertilised) {
                        insulate += 3;
                    }
                    new CircuitGarden(moist, null, manual, fertilised, new ItemStack((Item) Botany.insulatedTube, 1, 0 + 128 * insulate), icon);
                    new CircuitGarden(moist, EnumAcidity.Acid, manual, fertilised, new ItemStack((Item) Botany.insulatedTube, 1, 1 + 128 * insulate), icon);
                    new CircuitGarden(moist, EnumAcidity.Neutral, manual, fertilised, new ItemStack((Item) Botany.insulatedTube, 1, 2 + 128 * insulate), icon);
                    new CircuitGarden(moist, EnumAcidity.Alkaline, manual, fertilised, new ItemStack((Item) Botany.insulatedTube, 1, 3 + 128 * insulate), icon);
                }
            }
        }
    }

    @Override
    public void postInit() {
        GameRegistry.addRecipe((IRecipe) new CeramicTileRecipe());
        for (int mat = 0; mat < 4; ++mat) {
            for (int insulate = 0; insulate < 6; ++insulate) {
                final ItemStack tubes = new ItemStack((Item) Botany.insulatedTube, 2, mat + 128 * insulate);
                final ItemStack insulateStack = ItemInsulatedTube.getInsulateStack(tubes);
                final ItemStack forestryTube = new ItemStack(Mods.Forestry.item("thermionicTubes"), 1, mat);
                GameRegistry.addShapelessRecipe(tubes, new Object[]{forestryTube, forestryTube, insulateStack});
            }
        }
        GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe((Item) Botany.trowelWood, new Object[]{"d  ", " x ", "  s", 'd', Blocks.dirt, 's', "stickWood", 'x', "plankWood"}));
        GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe((Item) Botany.trowelStone, new Object[]{"d  ", " x ", "  s", 'd', Blocks.dirt, 's', "stickWood", 'x', "cobblestone"}));
        GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe((Item) Botany.trowelIron, new Object[]{"d  ", " x ", "  s", 'd', Blocks.dirt, 's', "stickWood", 'x', "ingotIron"}));
        GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe((Item) Botany.trowelGold, new Object[]{"d  ", " x ", "  s", 'd', Blocks.dirt, 's', "stickWood", 'x', "ingotGold"}));
        GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe((Item) Botany.trowelDiamond, new Object[]{"d  ", " x ", "  s", 'd', Blocks.dirt, 's', "stickWood", 'x', "gemDiamond"}));
        GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe((Item) Botany.soilMeter, new Object[]{" gg", " rg", "i  ", 'g', "ingotGold", 'r', "dustRedstone", 'i', "ingotIron"}));
        GameRegistry.addShapelessRecipe(BotanyItems.Weedkiller.get(4), new Object[]{new ItemStack(Items.spider_eye), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds), new ItemStack(Items.wheat_seeds)});
        GameRegistry.addShapelessRecipe(BotanyItems.AshPowder.get(4), new Object[]{Mods.Forestry.stack("ash")});
        GameRegistry.addShapelessRecipe(BotanyItems.MulchPowder.get(4), new Object[]{Mods.Forestry.stack("mulch")});
        GameRegistry.addShapelessRecipe(BotanyItems.CompostPowder.get(4), new Object[]{Mods.Forestry.stack("fertilizerBio")});
        GameRegistry.addShapelessRecipe(BotanyItems.FertiliserPowder.get(4), new Object[]{Mods.Forestry.stack("fertilizerCompound")});
        GameRegistry.addShapelessRecipe(BotanyItems.PulpPowder.get(4), new Object[]{Mods.Forestry.stack("woodPulp")});
        GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(BotanyItems.SulphurPowder.get(4), new Object[]{"dustSulphur"}));
        GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack((Item) Botany.pigment, 2, EnumFlowerColor.Black.ordinal()), new Object[]{"pigment", "pigment", "dyeBlack"}));
        ModuleGardening.queuedAcidFertilisers.put(BotanyItems.SulphurPowder.get(1), 1);
        ModuleGardening.queuedAcidFertilisers.put(BotanyItems.MulchPowder.get(1), 1);
        ModuleGardening.queuedAcidFertilisers.put(new ItemStack(GameRegistry.findItem("Forestry", "mulch")), 2);
        for (final ItemStack stack : OreDictionary.getOres("dustSulfur")) {
            ModuleGardening.queuedAcidFertilisers.put(stack, 2);
        }
        ModuleGardening.queuedAlkalineFertilisers.put(BotanyItems.AshPowder.get(1), 1);
        ModuleGardening.queuedAlkalineFertilisers.put(BotanyItems.PulpPowder.get(1), 1);
        ModuleGardening.queuedAlkalineFertilisers.put(new ItemStack(GameRegistry.findItem("Forestry", "ash")), 2);
        ModuleGardening.queuedAlkalineFertilisers.put(new ItemStack(GameRegistry.findItem("Forestry", "woodPulp")), 2);
        ModuleGardening.queuedNutrientFertilisers.put(BotanyItems.CompostPowder.get(1), 1);
        ModuleGardening.queuedNutrientFertilisers.put(BotanyItems.FertiliserPowder.get(1), 1);
        ModuleGardening.queuedNutrientFertilisers.put(new ItemStack(GameRegistry.findItem("Forestry", "fertilizerBio")), 2);
        ModuleGardening.queuedNutrientFertilisers.put(new ItemStack(GameRegistry.findItem("Forestry", "fertilizerCompound")), 2);
        for (final Map.Entry<ItemStack, Integer> entry : ModuleGardening.queuedAcidFertilisers.entrySet()) {
            this.addAcidFertiliser(entry.getKey(), entry.getValue());
        }
        for (final Map.Entry<ItemStack, Integer> entry : ModuleGardening.queuedAlkalineFertilisers.entrySet()) {
            this.addAlkalineFertiliser(entry.getKey(), entry.getValue());
        }
        for (final Map.Entry<ItemStack, Integer> entry : ModuleGardening.queuedNutrientFertilisers.entrySet()) {
            this.addNutrientFertiliser(entry.getKey(), entry.getValue());
        }
        GameRegistry.addRecipe(BotanyItems.Mortar.get(6), new Object[]{" c ", "cgc", " c ", 'c', Items.clay_ball, 'g', Blocks.gravel});
        for (final EnumFlowerColor c : EnumFlowerColor.values()) {
            final ItemStack clay = new ItemStack((Item) Botany.clay, 1, c.ordinal());
            final ItemStack pigment = new ItemStack((Item) Botany.pigment, 1, c.ordinal());
            GameRegistry.addShapelessRecipe(clay, new Object[]{Items.clay_ball, Items.clay_ball, Items.clay_ball, pigment});
            GameRegistry.addSmelting(clay, TileEntityMetadata.getItemStack(Botany.ceramic, c.ordinal()), 0.0f);
            final ItemStack glass = TileEntityMetadata.getItemStack(Botany.stained, c.ordinal());
            glass.stackSize = 4;
            GameRegistry.addShapedRecipe(glass, new Object[]{" g ", "gpg", " g ", 'g', Blocks.glass, 'p', pigment});
        }
        GameRegistry.addRecipe((IRecipe) new PigmentRecipe());
    }

    private ItemStack getStack(final int type, final int pH, final int moisture) {
        if (type < 0 || type > 2 || pH < 0 || pH > 2 || moisture < 0 || moisture > 2) {
            return null;
        }
        return new ItemStack(Gardening.getSoilBlock(EnumSoilType.values()[type]), 1, BlockSoil.getMeta(EnumAcidity.values()[pH], EnumMoisture.values()[moisture]));
    }

    private void addAcidFertiliser(final ItemStack stack, final int strengthMax) {
        if (stack == null) {
            return;
        }
        Gardening.fertiliserAcid.put(stack, strengthMax);
        for (int moisture = 0; moisture < 3; ++moisture) {
            for (int pH = 0; pH < 3; ++pH) {
                for (int type = 0; type < 3; ++type) {
                    int numOfBlocks = strengthMax * strengthMax;
                    for (int strength = 1; strength < strengthMax; ++strength) {
                        final ItemStack start = this.getStack(type, pH, moisture);
                        final ItemStack end = this.getStack(type, pH - strength, moisture);
                        if (end != null) {
                            end.stackSize = numOfBlocks;
                            final Object[] stacks = new Object[numOfBlocks + 1];
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

    private void addAlkalineFertiliser(final ItemStack stack, final int strengthMax) {
        if (stack == null) {
            return;
        }
        Gardening.fertiliserAlkaline.put(stack, strengthMax);
        for (int moisture = 0; moisture < 3; ++moisture) {
            for (int pH = 0; pH < 3; ++pH) {
                for (int type = 0; type < 3; ++type) {
                    int numOfBlocks = strengthMax * strengthMax;
                    for (int strength = 1; strength < strengthMax; ++strength) {
                        final ItemStack start = this.getStack(type, pH, moisture);
                        final ItemStack end = this.getStack(type, pH + strength, moisture);
                        if (end != null) {
                            end.stackSize = numOfBlocks;
                            final Object[] stacks = new Object[numOfBlocks + 1];
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

    private void addNutrientFertiliser(final ItemStack stack, final int strengthMax) {
        if (stack == null) {
            return;
        }
        Gardening.fertiliserNutrient.put(stack, strengthMax);
        for (int moisture = 0; moisture < 3; ++moisture) {
            for (int pH = 0; pH < 3; ++pH) {
                for (int type = 0; type < 3; ++type) {
                    int numOfBlocks = strengthMax * strengthMax;
                    for (int strength = 1; strength < strengthMax; ++strength) {
                        final ItemStack start = this.getStack(type, pH, moisture);
                        final ItemStack end = this.getStack(type + strength, pH, moisture);
                        if (end != null) {
                            end.stackSize = numOfBlocks;
                            final Object[] stacks = new Object[numOfBlocks + 1];
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

    static {
        ModuleGardening.queuedAcidFertilisers = new HashMap<ItemStack, Integer>();
        ModuleGardening.queuedAlkalineFertilisers = new HashMap<ItemStack, Integer>();
        ModuleGardening.queuedNutrientFertilisers = new HashMap<ItemStack, Integer>();
    }
}
