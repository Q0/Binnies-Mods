package binnie.botany.gardening;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.core.BinnieCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class ItemSoilMeter extends Item {
    public ItemSoilMeter() {
        this.setCreativeTab(CreativeTabBotany.instance);
        this.setUnlocalizedName("soilMeter");
        this.setMaxStackSize(1);
    }

    public boolean onItemUse(final ItemStack stack, final EntityPlayer player, final World world, final int x, int y, final int z, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        Block block = world.getBlock(x, y, z);
        if (!Gardening.isSoil(block)) {
            --y;
            block = world.getBlock(x, y, z);
        }
        if (Gardening.isSoil(block) && !BinnieCore.proxy.isSimulating(world)) {
            final IBlockSoil soil = (IBlockSoil) block;
            final EnumSoilType type = soil.getType(world, x, y, z);
            final EnumMoisture moisture = soil.getMoisture(world, x, y, z);
            final EnumAcidity pH = soil.getPH(world, x, y, z);
            String info = "Type: ";
            info = info + "§" + (new char[]{'8', '6', 'd'})[type.ordinal()] + Binnie.Language.localise(type) + "§f";
            info += ", Moisture: ";
            info = info + "§" + (new char[]{'e', '7', '9'})[moisture.ordinal()] + Binnie.Language.localise(moisture) + "§f";
            info += ", pH: ";
            info = info + "§" + (new char[]{'c', 'a', 'b'})[pH.ordinal()] + Binnie.Language.localise(pH) + "§f";
            final IChatComponent chat = (IChatComponent) new ChatComponentText(info);
            player.addChatMessage(chat);
        }
        return super.onItemUse(stack, player, world, x, y, z, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        this.itemIcon = Botany.proxy.getIcon(register, "soilMeter");
    }

    public String getItemStackDisplayName(final ItemStack i) {
        return "Soil Meter";
    }
}
