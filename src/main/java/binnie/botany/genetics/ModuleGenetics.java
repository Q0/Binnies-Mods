package binnie.botany.genetics;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.core.BotanyCore;
import binnie.botany.flower.*;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModuleGenetics implements IInitializable {
    static AlleleEffectNone alleleEffectNone;

    static {
        ModuleGenetics.alleleEffectNone = new AlleleEffectNone();
    }

    @Override
    public void preInit() {
        EnumFlowerColor.setupMutations();
        Botany.flower = new BlockFlower();
        Botany.flowerItem = new ItemFlower();
        Botany.pollen = new ItemPollen();
        Botany.seed = new ItemSeed();
        AlleleManager.alleleRegistry.registerSpeciesRoot((ISpeciesRoot) BotanyCore.speciesRoot);
        AlleleManager.alleleRegistry.registerAllele((IAllele) ModuleGenetics.alleleEffectNone);
        GameRegistry.registerBlock((Block) Botany.flower, "flower");
        BinnieCore.proxy.registerTileEntity(TileEntityFlower.class, "botany.tile.flower", null);
        Botany.database = new ItemDictionary();
        Botany.encyclopedia = new ItemEncyclopedia(false);
        Botany.encyclopediaIron = new ItemEncyclopedia(true);
    }

    @Override
    public void init() {
        for (final EnumFlowerColor color : EnumFlowerColor.values()) {
            AlleleManager.alleleRegistry.registerAllele((IAllele) color.getAllele());
        }
        FlowerSpecies.setupVariants();
        for (final FlowerSpecies species : FlowerSpecies.values()) {
            AlleleManager.alleleRegistry.registerAllele((IAllele) species);
            BotanyCore.getFlowerRoot().registerTemplate(species.getUID(), species.getTemplate());
            for (final IAllele[] variant : species.getVariants()) {
                BotanyCore.getFlowerRoot().registerTemplate(variant);
            }
        }
        RendererBotany.renderID = RenderingRegistry.getNextAvailableRenderId();
        BinnieCore.proxy.registerBlockRenderer(new RendererBotany());
    }

    @Override
    public void postInit() {
        GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack((Item) Botany.encyclopedia), new Object[]{"fff", "fbf", "fff", 'f', new ItemStack((Block) Blocks.red_flower, 1, 32767), 'b', Items.book}));
        GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack((Item) Botany.encyclopedia), new Object[]{"fff", "fbf", "fff", 'f', new ItemStack((Block) Blocks.yellow_flower, 1, 32767), 'b', Items.book}));
        GameRegistry.addRecipe((IRecipe) new ShapedOreRecipe(new ItemStack((Item) Botany.encyclopedia), new Object[]{"fff", "fbf", "fff", 'f', new ItemStack((Block) Botany.flower, 1, 32767), 'b', Items.book}));
        GameRegistry.addRecipe((IRecipe) new ShapelessOreRecipe(new ItemStack((Item) Botany.encyclopediaIron), new Object[]{new ItemStack((Item) Botany.encyclopedia), "ingotIron"}));
        FlowerManager.flowerRegistry.registerAcceptableFlower((Block) Botany.flower, new String[]{"flowersVanilla"});
        RecipeManagers.carpenterManager.addRecipe(100, Binnie.Liquid.getLiquidStack("water", 2000), (ItemStack) null, new ItemStack((Item) Botany.database), new Object[]{"X#X", "YEY", "RDR", '#', Blocks.glass_pane, 'X', Items.gold_ingot, 'Y', Items.gold_nugget, 'R', Items.redstone, 'D', Items.diamond, 'E', Items.emerald});
    }
}
