package binnie.core.machines.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.world.World;

import java.util.Random;

public interface IRender {
    public interface DisplayTick {
        @SideOnly(Side.CLIENT)
        void onDisplayTick(World var1, int var2, int var3, int var4, Random var5);
    }

    public interface RandomDisplayTick {
        @SideOnly(Side.CLIENT)
        void onRandomDisplayTick(World var1, int var2, int var3, int var4, Random var5);
    }

    public interface Render {
        @SideOnly(Side.CLIENT)
        void renderInWorld(RenderItem var1, double var2, double var4, double var6);
    }
}
