package binnie.extrabees.apiary;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class ItemIndustrialFrame extends Item {
    public ItemIndustrialFrame() {
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setMaxDamage(400);
        this.setMaxStackSize(1);
        this.setUnlocalizedName("industrialFrame");
    }

    public static IndustrialFrame getFrame(final ItemStack stack) {
        if (stack == null || !stack.hasTagCompound() || !stack.getTagCompound().hasKey("frame")) {
            return null;
        }
        return IndustrialFrame.values()[stack.getTagCompound().getInteger("frame")];
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (final IndustrialFrame frame : IndustrialFrame.values()) {
            final ItemStack stack = new ItemStack(this);
            final NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("frame", frame.ordinal());
            stack.setTagCompound(nbt);
            par3List.add(stack);
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        final IndustrialFrame frame = getFrame(par1ItemStack);
        if (frame == null) {
            par3List.add("Invalid Contents");
        } else {
            par3List.add(frame.getName());
        }
    }

    public String getItemStackDisplayName(final ItemStack par1ItemStack) {
        return "Industrial Frame";
    }
}
