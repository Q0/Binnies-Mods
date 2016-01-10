package binnie.extratrees.block;

import binnie.Binnie;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.DoorType;
import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.block.WoodManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockETDoor extends BlockDoor implements IBlockMetadata {
   private IIcon getFlippedIcon(boolean upper, boolean flip, int tileMeta) {
      DoorType type = getDoorType(tileMeta);
      return upper?(flip?type.iconDoorUpperFlip:type.iconDoorUpper):(flip?type.iconDoorLowerFlip:type.iconDoorLower);
   }

   public static DoorType getDoorType(int tileMeta) {
      int type = (tileMeta & 3840) >> 8;
      return type >= 0 && type < DoorType.values().length?DoorType.values()[type]:DoorType.Standard;
   }

   protected BlockETDoor() {
      super(Material.wood);
      this.setHardness(3.0F).setStepSound(soundTypeWood);
      this.setCreativeTab(Tabs.tabArboriculture);
      this.setBlockName("door");
   }

   public IIcon getIcon(int side, int meta) {
      return DoorType.Standard.iconDoorLower;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
      if(par5 != 1 && par5 != 0) {
         int i1 = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
         int j1 = i1 & 3;
         boolean flag = (i1 & 4) != 0;
         boolean flag1 = false;
         boolean flag2 = (i1 & 8) != 0;
         if(flag) {
            if(j1 == 0 && par5 == 2) {
               flag1 = !flag1;
            } else if(j1 == 1 && par5 == 5) {
               flag1 = !flag1;
            } else if(j1 == 2 && par5 == 3) {
               flag1 = !flag1;
            } else if(j1 == 3 && par5 == 4) {
               flag1 = !flag1;
            }
         } else {
            if(j1 == 0 && par5 == 5) {
               flag1 = !flag1;
            } else if(j1 == 1 && par5 == 3) {
               flag1 = !flag1;
            } else if(j1 == 2 && par5 == 4) {
               flag1 = !flag1;
            } else if(j1 == 3 && par5 == 2) {
               flag1 = !flag1;
            }

            if((i1 & 16) != 0) {
               flag1 = !flag1;
            }
         }

         int tileMeta = 0;
         if(flag2) {
            tileMeta = TileEntityMetadata.getTileMetadata(par1IBlockAccess, par2, par3 - 1, par4);
         } else {
            tileMeta = TileEntityMetadata.getTileMetadata(par1IBlockAccess, par2, par3, par4);
         }

         return this.getFlippedIcon(flag2, flag1, tileMeta);
      } else {
         return DoorType.Standard.iconDoorLower;
      }
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister register) {
      for(DoorType type : DoorType.values()) {
         type.iconDoorLower = ExtraTrees.proxy.getIcon(register, "door." + type.id + ".lower");
         type.iconDoorUpper = ExtraTrees.proxy.getIcon(register, "door." + type.id + ".upper");
         type.iconDoorLowerFlip = new IconFlipped(type.iconDoorLower, true, false);
         type.iconDoorUpperFlip = new IconFlipped(type.iconDoorUpper, true, false);
      }

   }

   public int getRenderType() {
      return ExtraTrees.doorRenderId;
   }

   @SideOnly(Side.CLIENT)
   public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      int par5 = 2;
      int i1 = this.getFullMetadata(par1IBlockAccess, par2, par3, par4);
      int j1 = i1 & 3;
      boolean flag = (i1 & 4) != 0;
      boolean flag1 = false;
      boolean flag2 = (i1 & 8) != 0;
      if(flag) {
         if(j1 == 0 && par5 == 2) {
            flag1 = !flag1;
         } else if(j1 == 1 && par5 == 5) {
            flag1 = !flag1;
         } else if(j1 == 2 && par5 == 3) {
            flag1 = !flag1;
         } else if(j1 == 3 && par5 == 4) {
            flag1 = !flag1;
         }
      } else {
         if(j1 == 0 && par5 == 5) {
            flag1 = !flag1;
         } else if(j1 == 1 && par5 == 3) {
            flag1 = !flag1;
         } else if(j1 == 2 && par5 == 4) {
            flag1 = !flag1;
         } else if(j1 == 3 && par5 == 2) {
            flag1 = !flag1;
         }

         if((i1 & 16) != 0) {
            flag1 = !flag1;
         }
      }

      if(flag2) {
         int meta = TileEntityMetadata.getTileMetadata(par1IBlockAccess, par2, par3 - 1, par4);
         return WoodManager.getPlankType(meta & 255).getColour();
      } else {
         int meta = TileEntityMetadata.getTileMetadata(par1IBlockAccess, par2, par3, par4);
         return WoodManager.getPlankType(meta & 255).getColour();
      }
   }

   public int getFullMetadata(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
      int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
      boolean flag = (l & 8) != 0;
      int i1;
      int j1;
      if(flag) {
         i1 = par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4);
         j1 = l;
      } else {
         i1 = l;
         j1 = par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4);
      }

      boolean flag1 = (j1 & 1) != 0;
      return i1 & 7 | (flag?8:0) | (flag1?16:0);
   }

   @SideOnly(Side.CLIENT)
   public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer) {
      if(par6EntityPlayer.capabilities.isCreativeMode && (par5 & 8) != 0 && par1World.getBlock(par2, par3 - 1, par4) == this) {
         par1World.setBlockToAir(par2, par3 - 1, par4);
      }

   }

   public ArrayList getDrops(World world, int x, int y, int z, int blockMeta, int fortune) {
      return BlockMetadata.getBlockDropped(this, world, x, y, z, blockMeta);
   }

   public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
      return BlockMetadata.breakBlock(this, player, world, x, y, z);
   }

   public TileEntity createNewTileEntity(World var1, int k) {
      return new TileEntityMetadata();
   }

   public boolean hasTileEntity(int meta) {
      return true;
   }

   public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6) {
      super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
      TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
      return tileentity != null?tileentity.receiveClientEvent(par5, par6):false;
   }

   public int getPlacedMeta(ItemStack stack, World world, int x, int y, int z, ForgeDirection clickedBlock) {
      return TileEntityMetadata.getItemDamage(stack);
   }

   public int getDroppedMeta(int blockMeta, int tileMeta) {
      return tileMeta;
   }

   public String getBlockName(ItemStack par1ItemStack) {
      int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
      String typeName = getDoorType(meta).getName();
      String woodName = WoodManager.getPlankType(meta & 255).getName();
      return typeName.equals("")?Binnie.Language.localise(ExtraTrees.instance, "block.door.name", new Object[]{woodName}):Binnie.Language.localise(ExtraTrees.instance, "block.door.name.adv", new Object[]{woodName, typeName});
   }

   public void getBlockTooltip(ItemStack par1ItemStack, List par3List) {
   }

   public void dropAsStack(World world, int x, int y, int z, ItemStack drop) {
      this.dropBlockAsItem(world, x, y, z, drop);
   }

   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
      for(IPlankType type : PlankType.ExtraTreePlanks.values()) {
         itemList.add(WoodManager.getDoor(type, DoorType.Standard));
      }

      for(IPlankType type : PlankType.ForestryPlanks.values()) {
         itemList.add(WoodManager.getDoor(type, DoorType.Standard));
      }

      for(IPlankType type : PlankType.ExtraBiomesPlank.values()) {
         if(type.getStack() != null) {
            itemList.add(WoodManager.getDoor(type, DoorType.Standard));
         }
      }

      for(IPlankType type : PlankType.VanillaPlanks.values()) {
         itemList.add(WoodManager.getDoor(type, DoorType.Standard));
      }

   }

   public boolean isWood(IBlockAccess world, int x, int y, int z) {
      return true;
   }

   public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
      return 20;
   }

   public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
      return true;
   }

   public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
      return 5;
   }

   public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
      super.breakBlock(par1World, par2, par3, par4, par5, par6);
      par1World.removeTileEntity(par2, par3, par4);
   }

   public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
      return BlockMetadata.getPickBlock(world, x, y, z);
   }
}
