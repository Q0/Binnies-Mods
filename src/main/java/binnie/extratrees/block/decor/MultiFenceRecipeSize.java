package binnie.extratrees.block.decor;

import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import binnie.extratrees.block.decor.FenceType;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class MultiFenceRecipeSize implements IRecipe {
   ItemStack cached;

   public MultiFenceRecipeSize() {
      super();
   }

   public boolean matches(InventoryCrafting inv, World world) {
      String pattern = "";
      List<IPlankType> types = new ArrayList();

      for(int i = 0; i < inv.getSizeInventory(); ++i) {
         ItemStack stack = inv.getStackInSlot(i);
         IPlankType type = stack == null?null:WoodManager.get(stack);
         if(stack != null && type == null) {
            return false;
         }

         if(stack == null) {
            pattern = pattern + " ";
         } else {
            if(!types.contains(type)) {
               types.add(type);
               if(types.size() > 2) {
                  return false;
               }
            }

            pattern = pattern + types.indexOf(type);
         }
      }

      if(types.isEmpty()) {
         return false;
      } else {
         ItemStack fence = null;
         if(pattern.contains("0100 0   ")) {
            fence = WoodManager.getFence((IPlankType)types.get(0), (IPlankType)types.get(1), new FenceType(0, false, false), 4);
         } else if(pattern.contains("0000 0   ")) {
            fence = WoodManager.getFence((IPlankType)types.get(0), (IPlankType)types.get(0), new FenceType(0, false, false), 4);
         } else if(pattern.contains("0100 0 1 ")) {
            fence = WoodManager.getFence((IPlankType)types.get(0), (IPlankType)types.get(1), new FenceType(1, false, false), 4);
         } else if(pattern.contains("0000 0 0 ")) {
            fence = WoodManager.getFence((IPlankType)types.get(0), (IPlankType)types.get(0), new FenceType(1, false, false), 4);
         } else if(pattern.contains(" 0 1 1101")) {
            fence = WoodManager.getFence((IPlankType)types.get(1), (IPlankType)types.get(0), new FenceType(2, false, false), 4);
         } else if(pattern.contains(" 0 0 0000")) {
            fence = WoodManager.getFence((IPlankType)types.get(0), (IPlankType)types.get(0), new FenceType(2, false, false), 4);
         }

         this.cached = fence;
         return fence != null;
      }
   }

   public ItemStack getCraftingResult(InventoryCrafting inv) {
      return this.getRecipeOutput();
   }

   public int getRecipeSize() {
      return 3;
   }

   public ItemStack getRecipeOutput() {
      return this.cached == null?new ItemStack(Blocks.fence):this.cached;
   }

   public static void generateTypes() {
      int type = 0;

      for(int type2 = 0; type2 < 2; ++type2) {
         ;
      }

   }
}
