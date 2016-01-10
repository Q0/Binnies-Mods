package binnie.extratrees.worldgen;

import binnie.extratrees.block.ILogType;
import binnie.extratrees.worldgen.BlockType;
import forestry.api.arboriculture.ITree;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockTypeLog extends BlockType {
   ILogType log;

   public BlockTypeLog(ILogType log) {
      super((Block)null, 0);
      this.log = log;
   }

   public void setBlock(World world, ITree tree, int x, int y, int z) {
      this.log.placeBlock(world, x, y, z);
   }
}
