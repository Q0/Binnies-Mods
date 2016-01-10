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
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModuleGenetics implements IInitializable {
    static AlleleEffectNone alleleEffectNone = new AlleleEffectNone();

    public ModuleGenetics() {
        super();
    }

    public void preInit() {
        EnumFlowerColor.setupMutations();
        Botany.flower = new BlockFlower();
        Botany.flowerItem = new ItemFlower();
        Botany.pollen = new ItemPollen();
        Botany.seed = new ItemSeed();
        AlleleManager.alleleRegistry.registerSpeciesRoot(BotanyCore.speciesRoot);
        AlleleManager.alleleRegistry.registerAllele(alleleEffectNone);
        GameRegistry.registerBlock(Botany.flower, "flower");
        BinnieCore.proxy.registerTileEntity(TileEntityFlower.class, "botany.tile.flower", (Object) null);
        Botany.database = new ItemDictionary();
        Botany.encyclopedia = new ItemEncyclopedia(false);
        Botany.encyclopediaIron = new ItemEncyclopedia(true);
    }

    public void init() {
        for (EnumFlowerColor color : EnumFlowerColor.values()) {
            AlleleManager.alleleRegistry.registerAllele(color.getAllele());
        }

        FlowerSpecies.setupVariants();

        for (FlowerSpecies species : FlowerSpecies.values()) {
            AlleleManager.alleleRegistry.registerAllele(species);
            BotanyCore.getFlowerRoot().registerTemplate(species.getUID(), species.getTemplate());

            for (IAllele[] variant : species.getVariants()) {
                BotanyCore.getFlowerRoot().registerTemplate(variant);
            }
        }

        RendererBotany.renderID = RenderingRegistry.getNextAvailableRenderId();
        BinnieCore.proxy.registerBlockRenderer(new RendererBotany());
    }

    public void postInit() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Botany.encyclopedia), new Object[]{"fff", "fbf", "fff", Character.valueOf('f'), new ItemStack(Blocks.red_flower, 1, 32767), Character.valueOf('b'), Items.book}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Botany.encyclopedia), new Object[]{"fff", "fbf", "fff", Character.valueOf('f'), new ItemStack(Blocks.yellow_flower, 1, 32767), Character.valueOf('b'), Items.book}));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Botany.encyclopedia), new Object[]{"fff", "fbf", "fff", Character.valueOf('f'), new ItemStack(Botany.flower, 1, 32767), Character.valueOf('b'), Items.book}));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Botany.encyclopediaIron), new Object[]{new ItemStack(Botany.encyclopedia), "ingotIron"}));
        FlowerManager.flowerRegistry.registerAcceptableFlower(Botany.flower, new String[]{"flowersVanilla"});
        RecipeManagers.carpenterManager.addRecipe(100, Binnie.Liquid.getLiquidStack("water", 2000), (ItemStack) null, new ItemStack(Botany.database), new Object[]{"X#X", "YEY", "RDR", Character.valueOf('#'), Blocks.glass_pane, Character.valueOf('X'), Items.gold_ingot, Character.valueOf('Y'), Items.gold_nugget, Character.valueOf('R'), Items.redstone, Character.valueOf('D'), Items.diamond, Character.valueOf('E'), Items.emerald});
    }
}
