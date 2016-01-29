package binnie.botany.api;

import forestry.api.genetics.IIndividual;
import net.minecraft.world.World;

public interface IFlower extends IIndividual {
    IFlowerGenome getGenome();

    IFlowerGenome getMate();

    void mate(final IFlower p0);

    int getAge();

    void setAge(final int p0);

    void age();

    IFlower getOffspring(final World p0);

    int getMaxAge();

    boolean isWilted();

    void setWilted(final boolean p0);

    boolean hasFlowered();

    void setFlowered(final boolean p0);

    void removeMate();
}
