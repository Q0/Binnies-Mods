package binnie.core.block;

import binnie.core.block.MultipassBlockRenderer;
import binnie.core.block.TileEntityMetadata;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class MultipassItemRenderer implements IItemRenderer {
   public MultipassItemRenderer() {
      super();
   }

   private void render(RenderBlocks renderer, ItemStack item, float f, float g, float h) {
      GL11.glTranslatef(f, g, h);
      Block block = ((ItemBlock)item.getItem()).field_150939_a;
      GL11.glEnable(3008);
      if(block.getRenderBlockPass() != 0) {
         GL11.glAlphaFunc(516, 0.1F);
         GL11.glEnable(3042);
         OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      } else {
         GL11.glAlphaFunc(516, 0.5F);
         GL11.glDisable(3042);
      }

      MultipassBlockRenderer.instance.renderInventoryBlock(block, TileEntityMetadata.getItemDamage(item), 0, renderer);
      if(block.getRenderBlockPass() == 0) {
         GL11.glAlphaFunc(516, 0.1F);
      }

      GL11.glTranslatef(-f, -g, -h);
   }

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      switch(type) {
      case ENTITY:
         return true;
      case EQUIPPED:
         return true;
      case INVENTORY:
         return true;
      case EQUIPPED_FIRST_PERSON:
         return true;
      default:
         return false;
      }
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return true;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
      switch(type) {
      case ENTITY:
         this.render((RenderBlocks)data[0], item, 0.0F, 0.0F, 0.0F);
         break;
      case EQUIPPED:
      case EQUIPPED_FIRST_PERSON:
         this.render((RenderBlocks)data[0], item, 0.5F, 0.5F, 0.5F);
         break;
      case INVENTORY:
         this.render((RenderBlocks)data[0], item, 0.0F, 0.0F, 0.0F);
      }

   }
}
