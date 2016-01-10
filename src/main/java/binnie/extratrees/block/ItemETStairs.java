package binnie.extratrees.block;

import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.block.PlankType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemETStairs extends ItemBlock {
   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return PlankType.ExtraTreePlanks.values()[par1].getIcon();
   }

   public ItemETStairs(Block block) {
      super(block);
      this.setCreativeTab(CreativeTabs.tabBlock);
      this.setUnlocalizedName("stairs");
   }

   public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
      boolean done = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
      TileEntityMetadata tile = (TileEntityMetadata)world.getTileEntity(x, y, z);
      if(tile != null) {
         tile.setTileMetadata(stack.getItemDamage(), false);
      }

      return done;
   }

   @SideOnly(Side.CLIENT)
   public String getItemStackDisplayName(ItemStack par1ItemStack) {
      return ((IBlockMetadata)this.field_150939_a).getBlockName(par1ItemStack);
   }
}
