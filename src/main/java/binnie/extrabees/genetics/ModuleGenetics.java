package binnie.extrabees.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.genetics.effect.BlockEctoplasm;
import binnie.extrabees.genetics.effect.ExtraBeesEffect;
import binnie.extrabees.genetics.items.ItemDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModuleGenetics implements IInitializable {
    @Override
    public void preInit() {
        for (final ExtraBeesSpecies species : ExtraBeesSpecies.values()) {
            AlleleManager.alleleRegistry.registerAllele((IAllele) species);
        }
        ExtraBees.dictionary = new ItemDictionary();
        GameRegistry.registerBlock(ExtraBees.ectoplasm = (Block) new BlockEctoplasm(), "ectoplasm");
    }

    @Override
    public void init() {
        ExtraBeesEffect.doInit();
        ExtraBeesFlowers.doInit();
        ExtraBeesSpecies.doInit();
        ExtraBeeMutation.doInit();
        ExtraBeesBranch.doInit();
    }

    @Override
    public void postInit() {
        int ebSpeciesCount = 0;
        int ebTotalSpeciesCount = 0;
        for (final ExtraBeesSpecies species : ExtraBeesSpecies.values()) {
            ++ebTotalSpeciesCount;
            if (!AlleleManager.alleleRegistry.isBlacklisted(species.getUID())) {
                ++ebSpeciesCount;
            }
        }
        RecipeManagers.carpenterManager.addRecipe(100, Binnie.Liquid.getLiquidStack("water", 2000), (ItemStack) null, new ItemStack(ExtraBees.dictionary), new Object[]{"X#X", "YEY", "RDR", '#', Blocks.glass_pane, 'X', Items.gold_ingot, 'Y', "ingotTin", 'R', Items.redstone, 'D', Items.diamond, 'E', Items.emerald});
    }

    public static IGenome getGenome(final IAlleleBeeSpecies allele0) {
        return (IGenome) Binnie.Genetics.getBeeRoot().templateAsGenome(Binnie.Genetics.getBeeRoot().getTemplate(allele0.getUID()));
    }

    public static ItemStack getBeeIcon(final IAlleleBeeSpecies species) {
        if (species == null) {
            return null;
        }
        final IAllele[] template = Binnie.Genetics.getBeeRoot().getTemplate(species.getUID());
        if (template == null) {
            return null;
        }
        final IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(template);
        final IBee bee = Binnie.Genetics.getBeeRoot().getBee(BinnieCore.proxy.getWorld(), genome);
        final ItemStack item = Binnie.Genetics.getBeeRoot().getMemberStack((IIndividual) bee, EnumBeeType.PRINCESS.ordinal());
        return item;
    }
}
