package binnie.botany.genetics;

import binnie.Binnie;
import binnie.botany.api.IBotanistTracker;
import com.mojang.authlib.GameProfile;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IIndividual;
import forestry.core.genetics.BreedingTracker;
import net.minecraft.entity.player.EntityPlayer;

public class BotanistTracker extends BreedingTracker implements IBotanistTracker {
    public BotanistTracker(String s) {
        this(s, (GameProfile) null);
    }

    public BotanistTracker(String s, GameProfile player) {
        super(s, player);
    }

    public void registerPickup(IIndividual individual) {
    }

    protected IBreedingTracker getBreedingTracker(EntityPlayer player) {
        return Binnie.Genetics.getFlowerRoot().getBreedingTracker(player.worldObj, player.getGameProfile());
    }

    protected String speciesRootUID() {
        return "rootFlowers";
    }
}
