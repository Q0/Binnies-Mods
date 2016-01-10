package binnie.botany.gardening;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.gardening.Gardening;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;

public class ItemTrowel extends Item {
   protected ToolMaterial theToolMaterial;
   String locName;

   public ItemTrowel(ToolMaterial p_i45343_1_, String material) {
      super();
      this.theToolMaterial = p_i45343_1_;
      this.maxStackSize = 1;
      this.setMaxDamage(p_i45343_1_.getMaxUses());
      this.setCreativeTab(CreativeTabBotany.instance);
      this.setUnlocalizedName("trowel" + material);
      this.locName = "trowel" + material;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister p_94581_1_) {
      this.itemIcon = Botany.proxy.getIcon(p_94581_1_, "tools/" + this.locName);
   }

   public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
      if(!player.canPlayerEdit(x, y, z, p_77648_7_, stack)) {
         return false;
      } else {
         Block block = world.getBlock(x, y, z);
         if(p_77648_7_ != 0 && (world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z) || world.getBlock(x, y + 1, z) == Botany.flower) && (block == Blocks.grass || block == Blocks.dirt)) {
            Block block1 = Botany.soil;
            world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);
            if(world.isRemote) {
               return true;
            } else {
               EnumMoisture moisture = Gardening.getNaturalMoisture(world, x, y, z);
               EnumAcidity acidity = Gardening.getNaturalPH(world, x, y, z);
               Gardening.createSoil(world, x, y, z, EnumSoilType.SOIL, moisture, acidity);
               stack.damageItem(1, player);
               return true;
            }
         } else {
            return false;
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public boolean isFull3D() {
      return true;
   }

   public String getToolMaterialName() {
      return this.theToolMaterial.toString();
   }
}
