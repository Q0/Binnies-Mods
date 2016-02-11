package binnie.extrabees.worldgen;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBeehive extends ItemBlock {
    public ItemBeehive(final Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public int getMetadata(final int i) {
        return i;
    }

    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
        for (int i = 0; i < 4; ++i) {
            itemList.add(new ItemStack(this, 1, i));
        }
    }

    public String getItemStackDisplayName(final ItemStack itemStack) {
        return EnumHiveType.values()[itemStack.getItemDamage()].toString() + " Hive";
    }
}
