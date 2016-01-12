package binnie.extratrees.worldgen;

import binnie.extratrees.block.ILogType;
import forestry.api.arboriculture.ITree;
import net.minecraft.world.World;

public class BlockTypeLog extends BlockType {
    ILogType log;

    public BlockTypeLog(final ILogType log) {
        super(null, 0);
        this.log = log;
    }

    @Override
    public void setBlock(final World world, final ITree tree, final int x, final int y, final int z) {
        this.log.placeBlock(world, x, y, z);
    }
}
