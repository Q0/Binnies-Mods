package binnie.extrabees.worldgen;

import binnie.Binnie;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.genetics.IAllele;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;

public class HiveDrop implements IHiveDrop {
    private IAllele[] template;
    private ArrayList additional;
    private int chance;

    public HiveDrop(IAlleleBeeSpecies species, int chance) {
        this(Binnie.Genetics.getBeeRoot().getTemplate(species.getUID()), new ItemStack[0], chance);
    }

    public HiveDrop(IAllele[] template, ItemStack[] bonus, int chance) {
        super();
        this.additional = new ArrayList();
        if (template == null) {
            template = Binnie.Genetics.getBeeRoot().getDefaultTemplate();
        }

        this.template = template;
        this.chance = chance;

        for (ItemStack stack : bonus) {
            this.additional.add(stack);
        }

    }

    public ItemStack getPrincess(World world, int x, int y, int z, int fortune) {
        return Binnie.Genetics.getBeeRoot().getMemberStack(Binnie.Genetics.getBeeRoot().getBee(world, Binnie.Genetics.getBeeRoot().templateAsGenome(this.template)), EnumBeeType.PRINCESS.ordinal());
    }

    public ArrayList getDrones(World world, int x, int y, int z, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList();
        ret.add(Binnie.Genetics.getBeeRoot().getMemberStack(Binnie.Genetics.getBeeRoot().templateAsIndividual(this.template), EnumBeeType.DRONE.ordinal()));
        return ret;
    }

    public ArrayList getAdditional(World world, int x, int y, int z, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList();

        for (ItemStack stack : this.additional) {
            ret.add(stack.copy());
        }

        return ret;
    }

    public int getChance(World world, int x, int y, int z) {
        return this.chance;
    }
}
