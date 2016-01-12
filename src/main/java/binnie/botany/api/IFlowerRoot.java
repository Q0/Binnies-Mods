package binnie.botany.api;

import com.mojang.authlib.GameProfile;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Collection;

public interface IFlowerRoot extends ISpeciesRoot {
    IFlower getMember(final ItemStack p0);

    IFlower templateAsIndividual(final IAllele[] p0);

    IFlower templateAsIndividual(final IAllele[] p0, final IAllele[] p1);

    IFlowerGenome templateAsGenome(final IAllele[] p0);

    IFlowerGenome templateAsGenome(final IAllele[] p0, final IAllele[] p1);

    IBotanistTracker getBreedingTracker(final World p0, final GameProfile p1);

    Collection<IFlowerMutation> getMutations(final boolean p0);

    EnumFlowerStage getType(final ItemStack p0);

    IFlower getFlower(final World p0, final IFlowerGenome p1);

    void addConversion(final ItemStack p0, final IAllele[] p1);

    IFlower getConversion(final ItemStack p0);

    Collection<IColourMix> getColourMixes(final boolean p0);

    void registerColourMix(final IColourMix p0);
}
