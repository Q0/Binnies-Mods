package binnie.extratrees.block.decor;

import binnie.extratrees.block.decor.NBTShapedRecipes;
import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class NBTShapedRecipe implements IRecipe {
   public final int recipeWidth;
   public final int recipeHeight;
   public final ItemStack[] recipeItems;
   private ItemStack recipeOutput;
   private boolean field_92101_f;
   private static final String __OBFID = "CL_00000093";

   public ItemStack getRecipeOutput() {
      return this.recipeOutput;
   }

   public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
      for(int i = 0; i <= 3 - this.recipeWidth; ++i) {
         for(int j = 0; j <= 3 - this.recipeHeight; ++j) {
            if(this.checkMatch(p_77569_1_, i, j, true)) {
               return true;
            }

            if(this.checkMatch(p_77569_1_, i, j, false)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean checkMatch(InventoryCrafting p_77573_1_, int p_77573_2_, int p_77573_3_, boolean p_77573_4_) {
      for(int k = 0; k < 3; ++k) {
         for(int l = 0; l < 3; ++l) {
            int i1 = k - p_77573_2_;
            int j1 = l - p_77573_3_;
            ItemStack itemstack = null;
            if(i1 >= 0 && j1 >= 0 && i1 < this.recipeWidth && j1 < this.recipeHeight) {
               if(p_77573_4_) {
                  itemstack = this.recipeItems[this.recipeWidth - i1 - 1 + j1 * this.recipeWidth];
               } else {
                  itemstack = this.recipeItems[i1 + j1 * this.recipeWidth];
               }
            }

            ItemStack itemstack1 = p_77573_1_.getStackInRowAndColumn(k, l);
            if(itemstack1 != null || itemstack != null) {
               if(itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null) {
                  return false;
               }

               if(itemstack.getItem() != itemstack1.getItem()) {
                  return false;
               }

               if(itemstack.getItemDamage() != 32767 && itemstack.getItemDamage() != itemstack1.getItemDamage()) {
                  return false;
               }

               if(itemstack.hasTagCompound() && itemstack1.hasTagCompound() && !ItemStack.areItemStackTagsEqual(itemstack, itemstack1)) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
      ItemStack itemstack = this.getRecipeOutput().copy();
      if(this.field_92101_f) {
         for(int i = 0; i < p_77572_1_.getSizeInventory(); ++i) {
            ItemStack itemstack1 = p_77572_1_.getStackInSlot(i);
            if(itemstack1 != null && itemstack1.hasTagCompound()) {
               itemstack.setTagCompound((NBTTagCompound)itemstack1.stackTagCompound.copy());
            }
         }
      }

      return itemstack;
   }

   public int getRecipeSize() {
      return this.recipeWidth * this.recipeHeight;
   }

   public NBTShapedRecipe(ItemStack p_92103_1_, Object... p_92103_2_) {
      super();
      String s = "";
      int i = 0;
      int j = 0;
      int k = 0;
      if(p_92103_2_[i] instanceof String[]) {
         String[] astring = (String[])((String[])((String[])p_92103_2_[i++]));

         for(int l = 0; l < astring.length; ++l) {
            String s1 = astring[l];
            ++k;
            j = s1.length();
            s = s + s1;
         }
      } else {
         while(p_92103_2_[i] instanceof String) {
            String s2 = (String)p_92103_2_[i++];
            ++k;
            j = s2.length();
            s = s + s2;
         }
      }

      HashMap hashmap;
      for(hashmap = new HashMap(); i < p_92103_2_.length; i += 2) {
         Character character = (Character)p_92103_2_[i];
         ItemStack itemstack1 = null;
         if(p_92103_2_[i + 1] instanceof Item) {
            itemstack1 = new ItemStack((Item)p_92103_2_[i + 1]);
         } else if(p_92103_2_[i + 1] instanceof Block) {
            itemstack1 = new ItemStack((Block)p_92103_2_[i + 1], 1, 32767);
         } else if(p_92103_2_[i + 1] instanceof ItemStack) {
            itemstack1 = (ItemStack)p_92103_2_[i + 1];
         }

         hashmap.put(character, itemstack1);
      }

      ItemStack[] aitemstack = new ItemStack[j * k];

      for(int i1 = 0; i1 < j * k; ++i1) {
         char c0 = s.charAt(i1);
         if(hashmap.containsKey(Character.valueOf(c0))) {
            aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
         } else {
            aitemstack[i1] = null;
         }
      }

      this.recipeWidth = j;
      this.recipeHeight = k;
      this.recipeItems = aitemstack;
      this.recipeOutput = p_92103_1_;
      NBTShapedRecipes.addRecipe(this);
   }
}
