package binnie.core.multiblock;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMultiblockMachine extends BlockContainer {
    public BlockMultiblockMachine(final String blockName) {
        super(Material.iron);
        this.setHardness(1.5f);
        this.setBlockName(blockName);
    }

    public TileEntity createTileEntity(final World world, final int metadata) {
        return new TileEntityMultiblockMachine();
    }

    public TileEntity createNewTileEntity(final World var1, final int i) {
        return new TileEntityMultiblockMachine();
    }
}
