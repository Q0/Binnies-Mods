package binnie.extratrees.block;

import binnie.core.block.ItemMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.BlockETDoor;
import binnie.extratrees.block.DoorType;
import binnie.extratrees.block.WoodManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemETDoor extends ItemMetadata {
   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister register) {
      for(DoorType type : DoorType.values()) {
         type.iconItem = ExtraTrees.proxy.getIcon(register, "door." + type.id);
      }

   }

   public ItemETDoor(Block block) {
      super(block);
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
      if(par7 != 1) {
         return false;
      } else {
         ++par5;
         Block block = ExtraTrees.blockDoor;
         if(par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack)) {
            if(!block.canPlaceBlockAt(par3World, par4, par5, par6)) {
               return false;
            } else {
               int i1 = MathHelper.floor_double((double)((par2EntityPlayer.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
               placeDoorBlock(par3World, par4, par5, par6, i1, block, par1ItemStack, par2EntityPlayer);
               --par1ItemStack.stackSize;
               return true;
            }
         } else {
            return false;
         }
      }
   }

   public static void placeDoorBlock(World par0World, int par1, int par2, int par3, int par4, Block par5Block, ItemStack item, EntityPlayer player) {
      byte b0 = 0;
      byte b1 = 0;
      if(par4 == 0) {
         b1 = 1;
      }

      if(par4 == 1) {
         b0 = -1;
      }

      if(par4 == 2) {
         b1 = -1;
      }

      if(par4 == 3) {
         b0 = 1;
      }

      int i1 = (par0World.isBlockNormalCubeDefault(par1 - b0, par2, par3 - b1, false)?1:0) + (par0World.isBlockNormalCubeDefault(par1 - b0, par2 + 1, par3 - b1, false)?1:0);
      int j1 = (par0World.isBlockNormalCubeDefault(par1 + b0, par2, par3 + b1, false)?1:0) + (par0World.isBlockNormalCubeDefault(par1 + b0, par2 + 1, par3 + b1, false)?1:0);
      boolean flag = par0World.getBlock(par1 - b0, par2, par3 - b1) == par5Block || par0World.getBlock(par1 - b0, par2 + 1, par3 - b1) == par5Block;
      boolean flag1 = par0World.getBlock(par1 + b0, par2, par3 + b1) == par5Block || par0World.getBlock(par1 + b0, par2 + 1, par3 + b1) == par5Block;
      boolean flag2 = false;
      if(flag && !flag1) {
         flag2 = true;
      } else if(j1 > i1) {
         flag2 = true;
      }

      par0World.setBlock(par1, par2, par3, par5Block, par4, 2);
      par0World.setBlock(par1, par2 + 1, par3, par5Block, 8 | (flag2?1:0), 2);
      if(par0World.getBlock(par1, par2, par3) == par5Block) {
         TileEntityMetadata tile = TileEntityMetadata.getTile(par0World, par1, par2, par3);
         if(tile != null) {
            tile.setTileMetadata(TileEntityMetadata.getItemDamage(item), false);
         }

         par5Block.onBlockPlacedBy(par0World, par1, par2, par3, player, item);
         par5Block.onPostBlockPlaced(par0World, par1, par2, par3, par4);
      }

      par0World.notifyBlocksOfNeighborChange(par1, par2, par3, par5Block);
      par0World.notifyBlocksOfNeighborChange(par1, par2 + 1, par3, par5Block);
   }

   @SideOnly(Side.CLIENT)
   public int getSpriteNumber() {
      return 1;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      DoorType type = BlockETDoor.getDoorType(par1);
      return type.iconItem;
   }

   @SideOnly(Side.CLIENT)
   public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
      return WoodManager.getPlankType(par1ItemStack == null?0:par1ItemStack.getItemDamage() & 255).getColour();
   }
}
