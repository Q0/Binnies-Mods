package binnie.botany.genetics;

import binnie.Binnie;
import binnie.botany.api.IBotanistTracker;
import com.mojang.authlib.GameProfile;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IIndividual;
import forestry.core.genetics.BreedingTracker;
import net.minecraft.entity.player.EntityPlayer;

public class BotanistTracker extends BreedingTracker implements IBotanistTracker {
    public BotanistTracker(final String s) {
        this(s, null);
    }

    public BotanistTracker(final String s, final GameProfile player) {
        super(s);
    }

    public void registerPickup(final IIndividual individual) {
    }

    protected IBreedingTracker getBreedingTracker(final EntityPlayer player) {
        return (IBreedingTracker) Binnie.Genetics.getFlowerRoot().getBreedingTracker(player.worldObj, player.getGameProfile());
    }

    protected String speciesRootUID() {
        return "rootFlowers";
    }
}
