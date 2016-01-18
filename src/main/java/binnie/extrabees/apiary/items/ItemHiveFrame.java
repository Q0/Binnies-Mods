package binnie.extrabees.apiary.items;

import binnie.core.genetics.BeeModifier;
import binnie.core.genetics.BeeModifierLogic;
import binnie.extrabees.ExtraBees;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.*;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHiveFrame extends Item implements IHiveFrame {
    private final IBeeModifier beeModifier;
    private final BeeModifierLogic logic;
    private final String name;

    //---------------------------------------------------------------------------
    //
    // CONSTRUCTOR
    //
    //---------------------------------------------------------------------------

    public ItemHiveFrame(String name, int maxDamage) {
        this.name = name;
        setMaxStackSize(1);
        setMaxDamage(maxDamage);
        setCreativeTab(Tabs.tabApiculture);
        setUnlocalizedName(name);

        logic = new BeeModifierLogic();
        beeModifier = new BeeModifier(logic);

        GameRegistry.registerItem(this, name);
    }

    //---------------------------------------------------------------------------
    //
    // PUBLIC METHODS
    //
    //---------------------------------------------------------------------------

    public ItemStack frameUsed(final IBeeHousing housing, final ItemStack frame, final IBee queen, final int wear) {
        frame.setItemDamage(frame.getItemDamage() + wear);

        if (frame.getItemDamage() >= frame.getMaxDamage()) {
            return null;
        }

        return frame;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        itemIcon = ExtraBees.proxy.getIcon(register, name);
    }

    //---------------------------------------------------------------------------
    //
    // ACCESSORS
    //
    //---------------------------------------------------------------------------

    @Override
    public IBeeModifier getBeeModifier() {
        return beeModifier;
    }

    public String getName() {
        return name;
    }

    public String getItemStackDisplayName(final ItemStack itemStack) {
        return ExtraBees.proxy.localise("item." + name);
    }
}
