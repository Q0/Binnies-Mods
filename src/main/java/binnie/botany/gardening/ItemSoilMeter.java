package binnie.botany.gardening;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.gardening.Gardening;
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
   public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
      Block block = world.getBlock(x, y, z);
      if(!Gardening.isSoil(block)) {
         --y;
         block = world.getBlock(x, y, z);
      }

      if(Gardening.isSoil(block) && !BinnieCore.proxy.isSimulating(world)) {
         IBlockSoil soil = (IBlockSoil)block;
         EnumSoilType type = soil.getType(world, x, y, z);
         EnumMoisture moisture = soil.getMoisture(world, x, y, z);
         EnumAcidity pH = soil.getPH(world, x, y, z);
         String info = "Type: ";
         info = info + "§" + (new char[]{'8', '6', 'd'})[type.ordinal()] + Binnie.Language.localise(type) + "§f";
         info = info + ", Moisture: ";
         info = info + "§" + (new char[]{'e', '7', '9'})[moisture.ordinal()] + Binnie.Language.localise(moisture) + "§f";
         info = info + ", pH: ";
         info = info + "§" + (new char[]{'c', 'a', 'b'})[pH.ordinal()] + Binnie.Language.localise(pH) + "§f";
         IChatComponent chat = new ChatComponentText(info);
         player.addChatMessage(chat);
      }

      return super.onItemUse(stack, player, world, x, y, z, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister register) {
      this.itemIcon = Botany.proxy.getIcon(register, "soilMeter");
   }

   public ItemSoilMeter() {
      super();
      this.setCreativeTab(CreativeTabBotany.instance);
      this.setUnlocalizedName("soilMeter");
      this.setMaxStackSize(1);
   }

   public String getItemStackDisplayName(ItemStack i) {
      return "Soil Meter";
   }
}
