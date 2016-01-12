package binnie.botany.ceramic;

import binnie.core.block.ItemMetadata;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemCeramic extends ItemMetadata {
    public ItemCeramic(final Block block) {
        super(block);
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(final ItemStack stack, final int p_82790_2_) {
        return 16711935;
    }
}
