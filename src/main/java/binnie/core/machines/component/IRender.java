package binnie.core.machines.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.world.World;

import java.util.Random;

public interface IRender {
    interface DisplayTick {
        @SideOnly(Side.CLIENT)
        void onDisplayTick(final World p0, final int p1, final int p2, final int p3, final Random p4);
    }

    interface RandomDisplayTick {
        @SideOnly(Side.CLIENT)
        void onRandomDisplayTick(final World p0, final int p1, final int p2, final int p3, final Random p4);
    }

    interface Render {
        @SideOnly(Side.CLIENT)
        void renderInWorld(final RenderItem p0, final double p1, final double p2, final double p3);
    }
}
