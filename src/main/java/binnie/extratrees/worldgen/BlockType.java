package binnie.extratrees.worldgen;

import forestry.api.arboriculture.ITree;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockType {
    int meta;
    Block block;

    public BlockType(Block block, int meta) {
        super();
        this.block = block;
        this.meta = meta;
    }

    public void setBlock(World world, ITree tree, int x, int y, int z) {
        world.setBlock(x, y, z, this.block, this.meta, 0);
        if (world.getTileEntity(x, y, z) != null) {
            world.removeTileEntity(x, y, z);
        }

    }
}
