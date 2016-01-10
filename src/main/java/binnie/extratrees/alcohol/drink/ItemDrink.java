package binnie.extratrees.alcohol.drink;

import binnie.extratrees.alcohol.Alcohol;
import binnie.extratrees.alcohol.AlcoholEffect;
import binnie.extratrees.alcohol.Glassware;
import binnie.extratrees.alcohol.drink.DrinkManager;
import binnie.extratrees.alcohol.drink.IDrinkLiquid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

public class ItemDrink extends ItemFood implements IFluidContainerItem {
   public ItemDrink() {
      super(0, 0.0F, false);
      this.setCreativeTab(Tabs.tabArboriculture);
      this.setUnlocalizedName("drink");
      this.setHasSubtypes(true);
      this.setMaxStackSize(1);
      this.setAlwaysEdible();
   }

   public Glassware getGlassware(ItemStack container) {
      return container.hasTagCompound() && container.getTagCompound().hasKey("glassware")?Glassware.values()[container.getTagCompound().getShort("glassware")]:Glassware.BeerMug;
   }

   public ItemStack getStack(Glassware glass, FluidStack fluid) {
      ItemStack stack = new ItemStack(this);
      this.saveGlassware(glass, stack);
      this.saveFluid(fluid, stack);
      return stack;
   }

   public void saveGlassware(Glassware container, ItemStack stack) {
      if(!stack.hasTagCompound()) {
         stack.setTagCompound(new NBTTagCompound());
      }

      stack.getTagCompound().setShort("glassware", (short)container.ordinal());
   }

   public void saveFluid(FluidStack fluid, ItemStack stack) {
      if(!stack.hasTagCompound()) {
         stack.setTagCompound(new NBTTagCompound());
      }

      NBTTagCompound nbt = stack.getTagCompound();
      if(fluid == null) {
         nbt.removeTag("fluid");
      } else {
         NBTTagCompound liq = new NBTTagCompound();
         fluid.writeToNBT(liq);
         nbt.setTag("fluid", liq);
      }

   }

   public FluidStack getFluid(ItemStack container) {
      return container.stackTagCompound != null && container.stackTagCompound.hasKey("fluid")?FluidStack.loadFluidStackFromNBT(container.stackTagCompound.getCompoundTag("fluid")):null;
   }

   public int getCapacity(ItemStack container) {
      return this.getGlassware(container).getCapacity();
   }

   public int fill(ItemStack container, FluidStack resource, boolean doFill) {
      if(resource != null && container.hasTagCompound()) {
         if(DrinkManager.getLiquid(resource.getFluid()) == null) {
            return 0;
         } else {
            FluidStack existing = this.getFluid(container);
            int space = this.getGlassware(container).getCapacity() - (existing == null?0:existing.amount);
            int added = Math.min(space, resource.amount);
            if(space <= 0) {
               return 0;
            } else if(existing != null) {
               if(!existing.isFluidEqual(resource)) {
                  return 0;
               } else {
                  if(doFill) {
                     FluidStack fill = existing.copy();
                     fill.amount += added;
                     this.saveFluid(fill, container);
                  }

                  return added;
               }
            } else {
               if(doFill) {
                  FluidStack fill = resource.copy();
                  fill.amount = added;
                  this.saveFluid(fill, container);
               }

               return added;
            }
         }
      } else {
         return 0;
      }
   }

   public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
      if(!container.hasTagCompound()) {
         return null;
      } else {
         FluidStack content = this.getFluid(container);
         if(content == null) {
            return null;
         } else {
            int toRemove = Math.min(maxDrain, content.amount);
            FluidStack fill = content.copy();
            FluidStack drain = content.copy();
            drain.amount = toRemove;
            fill.amount -= toRemove;
            if(fill.amount == 0) {
               fill = null;
            }

            if(doDrain) {
               this.saveFluid(fill, container);
            }

            return drain;
         }
      }
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(Glassware glassware : Glassware.values()) {
         par3List.add(this.getStack(glassware, (FluidStack)null));
      }

      par3List.add(this.getStack(Glassware.Wine, Alcohol.RedWine.get(Glassware.Wine.getCapacity())));
   }

   public String getItemStackDisplayName(ItemStack stack) {
      FluidStack fluid = this.getFluid(stack);
      IDrinkLiquid liquid = fluid == null?null:DrinkManager.getLiquid(fluid.getFluid());
      return this.getGlassware(stack).getName(liquid == null?null:liquid.getName());
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister par1IconRegister) {
      for(Glassware glassware : Glassware.values()) {
         glassware.registerIcons(par1IconRegister);
      }

   }

   public IIcon getIcon(ItemStack stack, int pass) {
      Glassware glass = this.getGlassware(stack);
      return pass == 0?glass.glass:(this.getFluid(stack) == null?glass.glass:glass.contents);
   }

   @SideOnly(Side.CLIENT)
   public int getColorFromItemStack(ItemStack stack, int pass) {
      FluidStack fluid = this.getFluid(stack);
      IDrinkLiquid drink = fluid == null?null:DrinkManager.getLiquid(fluid.getFluid());
      return pass == 0?16777215:(drink == null?16777215:drink.getColour());
   }

   public int getRenderPasses(int metadata) {
      return 2;
   }

   public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
      this.drain(p_77654_1_, 30, true);
      AlcoholEffect.makeDrunk(p_77654_3_, 2.1F);
      return p_77654_1_;
   }

   public EnumAction getItemUseAction(ItemStack p_77661_1_) {
      return this.getFluid(p_77661_1_) == null?EnumAction.none:EnumAction.drink;
   }

   @SideOnly(Side.CLIENT)
   public boolean requiresMultipleRenderPasses() {
      return true;
   }

   public int getMaxItemUseDuration(ItemStack p_77626_1_) {
      return 16;
   }

   public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
      p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
      return p_77659_1_;
   }
}
