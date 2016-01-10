package binnie.extratrees.worldgen;

import binnie.extratrees.worldgen.BlockType;
import forestry.api.arboriculture.ITree;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockTypeVoid extends BlockType {
   public BlockTypeVoid() {
      super((Block)null, 0);
   }

   public void setBlock(World world, ITree tree, int x, int y, int z) {
      world.setBlockToAir(x, y, z);
      if(world.getTileEntity(x, y, z) != null) {
         world.removeTileEntity(x, y, z);
      }

   }
}
