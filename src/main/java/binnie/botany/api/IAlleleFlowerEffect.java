package binnie.botany.api;

import forestry.api.genetics.IAlleleEffect;
import forestry.api.genetics.IEffectData;
import net.minecraft.world.World;

public interface IAlleleFlowerEffect extends IAlleleEffect {
    IEffectData doEffect(IFlowerGenome var1, IEffectData var2, World var3, int var4, int var5, int var6);
}
