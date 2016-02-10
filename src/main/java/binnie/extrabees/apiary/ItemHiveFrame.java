package binnie.extrabees.apiary;

import binnie.extrabees.ExtraBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IHiveFrame;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHiveFrame extends Item implements IHiveFrame {
    EnumHiveFrame frame;

    public ItemHiveFrame(final EnumHiveFrame frame) {
        this.frame = frame;
        this.setMaxDamage(frame.maxDamage);
        this.setCreativeTab(Tabs.tabApiculture);
        this.setMaxStackSize(1);
        this.setUnlocalizedName("hiveFrame");
    }

    public String getItemStackDisplayName(final ItemStack par1ItemStack) {
        return this.frame.getName();
    }

    public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
        return this.frame.getTerritoryModifier(genome, currentModifier);
    }

    public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return this.frame.getMutationModifier(genome, mate, currentModifier);
    }

    public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return this.frame.getLifespanModifier(genome, mate, currentModifier);
    }

    public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
        return this.frame.getProductionModifier(genome, currentModifier);
    }

    public ItemStack frameUsed(final IBeeHousing housing, final ItemStack frame, final IBee queen, final int wear) {
        frame.setItemDamage(frame.getItemDamage() + wear);
        if (frame.getItemDamage() >= frame.getMaxDamage()) {
            return null;
        }
        return frame;
    }

    public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }

    public boolean isSealed() {
        return false;
    }

    public boolean isSelfLighted() {
        return false;
    }

    public boolean isSunlightSimulated() {
        return false;
    }

    public boolean isHellish() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        this.itemIcon = ExtraBees.proxy.getIcon(register, "frame" + this.frame.toString());
    }

    public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }
}
