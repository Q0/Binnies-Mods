package binnie.extrabees.worldgen;

import binnie.Binnie;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.genetics.IAllele;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;

public class HiveDrop implements IHiveDrop {
    private IAllele[] template;
    private ArrayList<ItemStack> additional;
    private int chance;

    public HiveDrop(final IAlleleBeeSpecies species, final int chance) {
        this(Binnie.Genetics.getBeeRoot().getTemplate(species.getUID()), new ItemStack[0], chance);
    }

    public HiveDrop(IAllele[] template, final ItemStack[] bonus, final int chance) {
        additional = new ArrayList<ItemStack>();
        if (template == null) {
            template = Binnie.Genetics.getBeeRoot().getDefaultTemplate();
        }
        this.template = template;
        this.chance = chance;

        Collections.addAll(additional, bonus);
    }

    public ItemStack getPrincess(final World world, final int x, final int y, final int z, final int fortune) {
        return Binnie.Genetics.getBeeRoot().getMemberStack(Binnie.Genetics.getBeeRoot().getBee(world, Binnie.Genetics.getBeeRoot().templateAsGenome(template)), EnumBeeType.PRINCESS.ordinal());
    }

    public ArrayList<ItemStack> getDrones(final World world, final int x, final int y, final int z, final int fortune) {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(Binnie.Genetics.getBeeRoot().getMemberStack(Binnie.Genetics.getBeeRoot().templateAsIndividual(template), EnumBeeType.DRONE.ordinal()));
        return ret;
    }

    public ArrayList<ItemStack> getAdditional(final World world, final int x, final int y, final int z, final int fortune) {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        for (final ItemStack stack : additional) {
            ret.add(stack.copy());
        }
        return ret;
    }

    public int getChance(final World world, final int x, final int y, final int z) {
        return chance;
    }
}
