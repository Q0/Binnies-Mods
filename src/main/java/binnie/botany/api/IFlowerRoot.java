package binnie.botany.api;

import com.mojang.authlib.GameProfile;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Collection;

public interface IFlowerRoot extends ISpeciesRoot {
    IFlower getMember(ItemStack var1);

    IFlower templateAsIndividual(IAllele[] var1);

    IFlower templateAsIndividual(IAllele[] var1, IAllele[] var2);

    IFlowerGenome templateAsGenome(IAllele[] var1);

    IFlowerGenome templateAsGenome(IAllele[] var1, IAllele[] var2);

    IBotanistTracker getBreedingTracker(World var1, GameProfile var2);

    Collection getMutations(boolean var1);

    EnumFlowerStage getType(ItemStack var1);

    IFlower getFlower(World var1, IFlowerGenome var2);

    void addConversion(ItemStack var1, IAllele[] var2);

    IFlower getConversion(ItemStack var1);

    Collection getColourMixes(boolean var1);

    void registerColourMix(IColourMix var1);
}
