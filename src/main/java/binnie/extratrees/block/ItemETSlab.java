package binnie.extratrees.block;

import binnie.core.block.ItemMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.BlockETSlab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemETSlab extends ItemMetadata {
   private final boolean isFullBlock;
   private final BlockETSlab theHalfSlab = (BlockETSlab)ExtraTrees.blockSlab;
   private final BlockETSlab doubleSlab = (BlockETSlab)ExtraTrees.blockDoubleSlab;

   public ItemETSlab(Block block) {
      super(block);
      this.isFullBlock = block != this.theHalfSlab;
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   @SideOnly(Side.CLIENT)
   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
      if(this.isFullBlock) {
         return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
      } else if(par1ItemStack.stackSize == 0) {
         return false;
      } else if(!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
         return false;
      } else {
         Block i1 = par3World.getBlock(par4, par5, par6);
         int j1 = par3World.getBlockMetadata(par4, par5, par6);
         int tileMeta = TileEntityMetadata.getTileMetadata(par3World, par4, par5, par6);
         int k1 = j1 & 7;
         boolean flag = (j1 & 8) != 0;
         if((par7 == 1 && !flag || par7 == 0 && flag) && i1 == this.theHalfSlab && tileMeta == par1ItemStack.getItemDamage()) {
            if(par3World.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlock(par4, par5, par6, this.doubleSlab, k1, 3)) {
               TileEntityMetadata tile = TileEntityMetadata.getTile(par3World, par4, par5, par6);
               if(tile != null) {
                  tile.setTileMetadata(tileMeta, true);
               }

               --par1ItemStack.stackSize;
            }

            return true;
         } else {
            return this.func_77888_a(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7)?true:super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
         }
      }
   }

   private boolean func_77888_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7) {
      if(par7 == 0) {
         --par5;
      }

      if(par7 == 1) {
         ++par5;
      }

      if(par7 == 2) {
         --par6;
      }

      if(par7 == 3) {
         ++par6;
      }

      if(par7 == 4) {
         --par4;
      }

      if(par7 == 5) {
         ++par4;
      }

      Block i1 = par3World.getBlock(par4, par5, par6);
      int j1 = par3World.getBlockMetadata(par4, par5, par6);
      int k1 = j1 & 7;
      int tileMeta = TileEntityMetadata.getTileMetadata(par3World, par4, par5, par6);
      if(i1 == this.theHalfSlab && tileMeta == par1ItemStack.getItemDamage()) {
         if(par3World.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6)) && par3World.setBlock(par4, par5, par6, this.doubleSlab, k1, 3)) {
            TileEntityMetadata tile = TileEntityMetadata.getTile(par3World, par4, par5, par6);
            if(tile != null) {
               tile.setTileMetadata(tileMeta, false);
               par3World.markBlockForUpdate(par4, par5, par6);
            }

            --par1ItemStack.stackSize;
         }

         return true;
      } else {
         return false;
      }
   }
}
