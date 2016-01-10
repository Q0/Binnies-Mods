package binnie.core.machines.component;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IInteraction {
    public interface ChunkUnload {
        void onChunkUnload();
    }

    public interface Invalidation {
        void onInvalidation();
    }

    public interface RightClick {
        void onRightClick(World var1, EntityPlayer var2, int var3, int var4, int var5);
    }
}
