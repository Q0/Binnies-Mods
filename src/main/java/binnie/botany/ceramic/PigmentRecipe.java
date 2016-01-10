package binnie.botany.ceramic;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.IFlower;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class PigmentRecipe implements IRecipe {
   private final ItemStack unknown = null;
   ItemStack cached;

   public PigmentRecipe() {
      super();
   }

   public boolean matches(InventoryCrafting crafting, World world) {
      return this.getCraftingResult(crafting) != null;
   }

   public ItemStack getRecipeOutput() {
      return this.cached != null?this.cached:this.unknown;
   }

   public ItemStack getCraftingResult(InventoryCrafting crafting) {
      int n = 0;
      ItemStack stack = null;

      for(int i = 0; i < crafting.getSizeInventory(); ++i) {
         if(crafting.getStackInSlot(i) != null) {
            ++n;
            if(n > 1) {
               return null;
            }

            if(Binnie.Genetics.getFlowerRoot().isMember(crafting.getStackInSlot(i))) {
               IFlower flower = Binnie.Genetics.getFlowerRoot().getMember(crafting.getStackInSlot(i));
               if(flower.getAge() >= 1) {
                  stack = new ItemStack(Botany.pigment, 1, flower.getGenome().getPrimaryColor().getID());
               }
            }
         }
      }

      return stack;
   }

   public int getRecipeSize() {
      return 1;
   }
}
