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

    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        return this.frame.getName();
    }

    public ItemHiveFrame(EnumHiveFrame frame) {
        super();
        this.frame = frame;
        this.setMaxDamage(frame.maxDamage);
        this.setCreativeTab(Tabs.tabApiculture);
        this.setMaxStackSize(1);
        this.setUnlocalizedName("hiveFrame");
    }

    public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
        return this.frame.getTerritoryModifier(genome, currentModifier);
    }

    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return this.frame.getMutationModifier(genome, mate, currentModifier);
    }

    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return this.frame.getLifespanModifier(genome, mate, currentModifier);
    }

    public float getProductionModifier(IBeeGenome genome, float currentModifier) {
        return this.frame.getProductionModifier(genome, currentModifier);
    }

    public ItemStack frameUsed(IBeeHousing housing, ItemStack frame, IBee queen, int wear) {
        frame.setItemDamage(frame.getItemDamage() + wear);
        return frame.getItemDamage() >= frame.getMaxDamage() ? null : frame;
    }

    public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
        return 1.0F;
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
    public void registerIcons(IIconRegister register) {
        this.itemIcon = ExtraBees.proxy.getIcon(register, "frame" + this.frame.toString());
    }

    public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
        return 1.0F;
    }
}
