package binnie.botany.farm;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlower;
import binnie.botany.core.BotanyCore;
import binnie.botany.gardening.Gardening;
import forestry.api.farming.ICrop;
import forestry.api.farming.IFarmable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FarmableFlower implements IFarmable {
    public boolean isSaplingAt(final World world, final int x, final int y, final int z) {
        return world.getBlock(x, y, z) == Botany.flower;
    }

    public ICrop getCropAt(final World world, final int x, final int y, final int z) {
        return null;
    }

    public boolean isGermling(final ItemStack itemstack) {
        final EnumFlowerStage stage = BotanyCore.speciesRoot.getType(itemstack);
        return stage == EnumFlowerStage.FLOWER || stage == EnumFlowerStage.SEED;
    }

    public boolean isWindfall(final ItemStack itemstack) {
        return false;
    }

    public boolean plantSaplingAt(final EntityPlayer player, final ItemStack germling, final World world, final int x, final int y, final int z) {
        final IFlower flower = BotanyCore.getFlowerRoot().getMember(germling);
        Gardening.plant(world, x, y, z, flower, player.getGameProfile());
        return true;
    }
}
