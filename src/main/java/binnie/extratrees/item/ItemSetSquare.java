package binnie.extratrees.item;

import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IToolHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSetSquare extends Item implements IToolHammer {
    EnumSetSquareMode mode;

    public ItemSetSquare(final EnumSetSquareMode mode) {
        this.mode = EnumSetSquareMode.Rotate;
        this.mode = mode;
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setUnlocalizedName("setSquare" + mode);
        this.setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        this.itemIcon = ExtraTrees.proxy.getIcon(register, "setSquare" + this.mode.ordinal());
    }

    public String getItemStackDisplayName(final ItemStack i) {
        return "Set Square";
    }

    public boolean isActive(final ItemStack item) {
        return this.mode == EnumSetSquareMode.Rotate;
    }

    public void onHammerUsed(final ItemStack item, final EntityPlayer player) {
    }

    public enum EnumSetSquareMode {
        Rotate,
        Edit,
        Swap;
    }
}
