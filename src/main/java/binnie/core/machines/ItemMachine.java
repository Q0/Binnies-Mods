package binnie.core.machines;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemMachine extends ItemBlock {
    private IBlockMachine associatedBlock;

    public ItemMachine(final Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.associatedBlock = (IBlockMachine) block;
    }

    public int getMetadata(final int i) {
        return i;
    }

    public String getItemStackDisplayName(final ItemStack itemstack) {
        return this.associatedBlock.getMachineName(itemstack.getItemDamage());
    }
}
