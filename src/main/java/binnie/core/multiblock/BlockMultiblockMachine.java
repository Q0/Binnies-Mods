package binnie.core.multiblock;

import binnie.core.multiblock.TileEntityMultiblockMachine;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMultiblockMachine extends BlockContainer {
   public BlockMultiblockMachine(String blockName) {
      super(Material.iron);
      this.setHardness(1.5F);
      this.setBlockName(blockName);
   }

   public TileEntity createTileEntity(World world, int metadata) {
      return new TileEntityMultiblockMachine();
   }

   public TileEntity createNewTileEntity(World var1, int i) {
      return new TileEntityMultiblockMachine();
   }
}
