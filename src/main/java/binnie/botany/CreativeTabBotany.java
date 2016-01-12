package binnie.botany;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabBotany extends CreativeTabs {
    public static CreativeTabs instance;

    public ItemStack getIconItemStack() {
        return new ItemStack((Block) Blocks.red_flower, 1, 5);
    }

    public CreativeTabBotany() {
        super("Botany");
    }

    public String getTranslatedTabLabel() {
        return this.getTabLabel();
    }

    @SideOnly(Side.CLIENT)
    public String getTabLabel() {
        return Botany.proxy.localise("tab.botany");
    }

    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return Item.getItemFromBlock((Block) Blocks.yellow_flower);
    }

    static {
        CreativeTabBotany.instance = new CreativeTabBotany();
    }
}
