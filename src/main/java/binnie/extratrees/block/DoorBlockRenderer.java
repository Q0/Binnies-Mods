package binnie.extratrees.block;

import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class DoorBlockRenderer implements ISimpleBlockRenderingHandler {
    public DoorBlockRenderer() {
        super();
    }

    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
    }

    public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4, Block par1Block, int modelId, RenderBlocks renderer) {
        int c = par1Block.colorMultiplier(renderer.blockAccess, par2, par3, par4);
        float c1 = (float) (c >> 16 & 255) / 255.0F;
        float c2 = (float) (c >> 8 & 255) / 255.0F;
        float c3 = (float) (c & 255) / 255.0F;
        Tessellator tessellator = Tessellator.instance;
        int l = renderer.blockAccess.getBlockMetadata(par2, par3, par4);
        if ((l & 8) != 0) {
            if (renderer.blockAccess.getBlock(par2, par3 - 1, par4) != par1Block) {
                return false;
            }
        } else if (renderer.blockAccess.getBlock(par2, par3 + 1, par4) != par1Block) {
            return false;
        }

        boolean flag = false;
        float f = 0.5F;
        float f1 = 1.0F;
        float f2 = 0.8F;
        float f3 = 0.6F;
        int i1 = par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4);
        tessellator.setBrightness(renderer.renderMinY > 0.0D ? i1 : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 - 1, par4));
        tessellator.setColorOpaque_F(f * c1, f * c2, f * c3);
        renderer.renderFaceYNeg(par1Block, (double) par2, (double) par3, (double) par4, renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 0));
        flag = true;
        tessellator.setBrightness(renderer.renderMaxY < 1.0D ? i1 : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3 + 1, par4));
        tessellator.setColorOpaque_F(f1 * c1, f1 * c2, f1 * c3);
        renderer.renderFaceYPos(par1Block, (double) par2, (double) par3, (double) par4, renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 1));
        flag = true;
        tessellator.setBrightness(renderer.renderMinZ > 0.0D ? i1 : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 - 1));
        tessellator.setColorOpaque_F(f2 * c1, f2 * c2, f2 * c3);
        IIcon icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 2);
        renderer.renderFaceZNeg(par1Block, (double) par2, (double) par3, (double) par4, icon);
        flag = true;
        renderer.flipTexture = false;
        tessellator.setBrightness(renderer.renderMaxZ < 1.0D ? i1 : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4 + 1));
        tessellator.setColorOpaque_F(f2 * c1, f2 * c2, f2 * c3);
        icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 3);
        renderer.renderFaceZPos(par1Block, (double) par2, (double) par3, (double) par4, icon);
        flag = true;
        renderer.flipTexture = false;
        tessellator.setBrightness(renderer.renderMinX > 0.0D ? i1 : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 - 1, par3, par4));
        tessellator.setColorOpaque_F(f3 * c1, f3 * c2, f3 * c3);
        icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 4);
        renderer.renderFaceXNeg(par1Block, (double) par2, (double) par3, (double) par4, icon);
        flag = true;
        renderer.flipTexture = false;
        tessellator.setBrightness(renderer.renderMaxX < 1.0D ? i1 : par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2 + 1, par3, par4));
        tessellator.setColorOpaque_F(f3 * c1, f3 * c2, f3 * c3);
        icon = renderer.getBlockIcon(par1Block, renderer.blockAccess, par2, par3, par4, 5);
        renderer.renderFaceXPos(par1Block, (double) par2, (double) par3, (double) par4, icon);
        flag = true;
        renderer.flipTexture = false;
        return flag;
    }

    public boolean shouldRender3DInInventory(int i) {
        return false;
    }

    public int getRenderId() {
        return ExtraTrees.doorRenderId;
    }
}
