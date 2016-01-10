package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.ceramic.BlockCeramicBrick;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class CeramicTileRecipe implements IRecipe {
   ItemStack cached;

   public CeramicTileRecipe() {
      super();
   }

   public boolean matches(InventoryCrafting inv, World world) {
      this.cached = this.getCraftingResult(inv);
      return this.cached != null;
   }

   public ItemStack getCraftingResult(InventoryCrafting inv) {
      Item ceramicBlock = Item.getItemFromBlock(Botany.ceramic);
      Item ceramicTile = Item.getItemFromBlock(Botany.ceramicTile);
      Item ceramicBrick = Item.getItemFromBlock(Botany.ceramicBrick);
      Item mortar = Botany.misc;
      List<ItemStack> stacks = new ArrayList();
      int ix = -1;
      int iy = -1;

      for(int i = 0; i < inv.getSizeInventory(); ++i) {
         ItemStack stack = inv.getStackInSlot(i);
         if(stack != null) {
            int x = i / 3;
            int y = i % 3;
            if(ix == -1) {
               ix = x;
               iy = y;
            }

            if(x - ix >= 2 || y - iy >= 2 || y < iy || x < ix) {
               return null;
            }

            if(stack.getItem() != ceramicBlock && stack.getItem() != ceramicTile && stack.getItem() != ceramicBrick && stack.getItem() != mortar) {
               return null;
            }

            stacks.add(stack);
         }
      }

      for(BlockCeramicBrick.TileType type : BlockCeramicBrick.TileType.values()) {
         ItemStack stack = type.getRecipe(stacks);
         if(stack != null) {
            return stack;
         }
      }

      return null;
   }

   public int getRecipeSize() {
      return 2;
   }

   public ItemStack getRecipeOutput() {
      return this.cached;
   }
}
