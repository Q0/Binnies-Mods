package binnie.core.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemMetadataRenderer implements IItemRenderer {
    public ItemMetadataRenderer() {
        super();
    }

    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY || type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return type == ItemRenderType.INVENTORY ? helper == ItemRendererHelper.INVENTORY_BLOCK : (type != ItemRenderType.ENTITY ? (type != ItemRenderType.EQUIPPED && type != ItemRenderType.EQUIPPED_FIRST_PERSON ? false : helper == ItemRendererHelper.EQUIPPED_BLOCK) : helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
    }

    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        Block block = Block.getBlockFromItem(item.getItem());
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glTranslated(0.5D, 0.5D, 0.5D);
        }

        if (type == ItemRenderType.INVENTORY && block.getRenderBlockPass() != 0) {
            GL11.glAlphaFunc(516, 0.1F);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        }

        GL11.glPushMatrix();
        ((RenderBlocks) data[0]).renderBlockAsItem(block, TileEntityMetadata.getItemDamage(item), 1.0F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
