package binnie.core.machines;

import binnie.Binnie;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RendererMachine extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {
    RenderBlocks blockRenderer;

    public void renderTileEntityAt(final TileEntity entity, final double x, final double y, final double z, final float var8) {
        this.renderTileEntity((TileEntityMachine) entity, x, y, z, var8, this.blockRenderer);
    }

    public void renderTileEntity(final TileEntityMachine entity, final double x, final double y, final double z, final float var8, final RenderBlocks renderer) {
        if (entity != null && entity.getMachine() != null) {
            final MachinePackage machinePackage = entity.getMachine().getPackage();
            machinePackage.renderMachine(entity.getMachine(), x, y, z, var8, renderer);
        }
    }

    public void renderInvBlock(final RenderBlocks renderblocks, final Block block, final int i, final int j) {
        final TileEntity entity = block.createTileEntity(null, i);
        this.renderTileEntity((TileEntityMachine) entity, 0.0, -0.1, 0.0, 0.0625f, renderblocks);
    }

    public void renderInventoryBlock(final Block block, final int metadata, final int modelID, final RenderBlocks renderer) {
        if (modelID == Binnie.Machine.getMachineRenderID()) {
            this.renderInvBlock(renderer, block, metadata, modelID);
        }
    }

    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer) {
        final TileEntityMachine tile = (TileEntityMachine) world.getTileEntity(x, y, z);
        if (tile != null && tile.getMachine() != null && tile.getMachine().getPackage() != null && tile.getMachine().getPackage().getGroup() != null && !tile.getMachine().getPackage().getGroup().customRenderer) {
            this.renderTileEntity(tile, x, y, z, 1.0f, renderer);
        }
        return true;
    }

    public boolean shouldRender3DInInventory(final int i) {
        return true;
    }

    public int getRenderId() {
        return Binnie.Machine.getMachineRenderID();
    }

    public void func_147496_a(final World par1World) {
        this.blockRenderer = new RenderBlocks(par1World);
    }
}
