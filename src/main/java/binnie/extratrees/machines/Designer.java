package binnie.extratrees.machines;

import binnie.core.machines.Machine;
import binnie.core.machines.component.ComponentRecipe;
import binnie.core.machines.component.IComponentRecipe;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IErrorStateSource;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.machines.DesignerType;
import binnie.extratrees.machines.ExtraTreeMachine;
import binnie.extratrees.machines.Validators;
import cpw.mods.fml.relauncher.Side;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class Designer {
   public static int beeswaxSlot = 0;
   public static int design1Slot = 1;
   public static int design2Slot = 2;

   public Designer() {
      super();
   }

   public static class ComponentWoodworkerRecipe extends ComponentRecipe implements IComponentRecipe, INetwork.GuiNBT, IErrorStateSource {
      public DesignerType type;
      private IDesign design = EnumDesign.Diamond;

      public ComponentWoodworkerRecipe(Machine machine, DesignerType type) {
         super(machine);
         this.type = type;
      }

      public void readFromNBT(NBTTagCompound nbttagcompound) {
         super.readFromNBT(nbttagcompound);
         this.setDesign(CarpentryManager.carpentryInterface.getDesign(nbttagcompound.getInteger("design")));
      }

      public void writeToNBT(NBTTagCompound nbttagcompound) {
         super.writeToNBT(nbttagcompound);
         nbttagcompound.setInteger("design", CarpentryManager.carpentryInterface.getDesignIndex(this.design));
      }

      public boolean isRecipe() {
         return this.getProduct() != null;
      }

      public ItemStack getProduct() {
         ItemStack plank1 = this.getUtil().getStack(Designer.design1Slot);
         ItemStack plank2 = this.getUtil().getStack(Designer.design2Slot);
         if(plank1 != null && plank2 != null) {
            IDesignMaterial type1 = this.type.getSystem().getMaterial(plank1);
            IDesignMaterial type2 = this.type.getSystem().getMaterial(plank2);
            IDesign design = this.getDesign();
            ItemStack stack = this.type.getBlock(type1, type2, design);
            return stack;
         } else {
            return null;
         }
      }

      public ItemStack doRecipe(boolean takeItem) {
         if(!this.isRecipe()) {
            return null;
         } else if(this.canWork() != null) {
            return null;
         } else {
            ItemStack product = this.getProduct();
            if(takeItem) {
               ItemStack a = this.getUtil().decreaseStack(Designer.design1Slot, 1);
               if(a == null) {
                  this.getUtil().decreaseStack(Designer.design2Slot, 1);
               } else if(this.design != EnumDesign.Blank) {
                  this.getUtil().decreaseStack(Designer.design2Slot, 1);
               }

               this.getUtil().decreaseStack(Designer.beeswaxSlot, 1);
            }

            return product;
         }
      }

      public IDesign getDesign() {
         return this.design;
      }

      public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
         if(name.equals("recipe")) {
            InventoryPlayer playerInv = player.inventory;
            ItemStack recipe = this.doRecipe(false);
            if(recipe == null) {
               return;
            }

            if(playerInv.getItemStack() == null) {
               playerInv.setItemStack(this.doRecipe(true));
            } else if(playerInv.getItemStack().isItemEqual(recipe) && ItemStack.areItemStackTagsEqual(playerInv.getItemStack(), recipe)) {
               int fit = recipe.getMaxStackSize() - (recipe.stackSize + playerInv.getItemStack().stackSize);
               if(fit >= 0) {
                  ItemStack rec = this.doRecipe(true);
                  rec.stackSize += playerInv.getItemStack().stackSize;
                  playerInv.setItemStack(rec);
               }
            }

            player.openContainer.detectAndSendChanges();
            if(player instanceof EntityPlayerMP) {
               ((EntityPlayerMP)player).updateHeldItem();
            }
         } else if(name.equals("design")) {
            this.setDesign(CarpentryManager.carpentryInterface.getDesign(nbt.getShort("d")));
         }

      }

      private void setDesign(IDesign design) {
         this.design = design;
      }

      public ErrorState canWork() {
         return this.getUtil().isSlotEmpty(Designer.beeswaxSlot)?new ErrorState.NoItem("Requires Adhesive to Function", Designer.beeswaxSlot):null;
      }

      public ErrorState canProgress() {
         return null;
      }

      public void sendGuiNBT(Map nbt) {
         NBTTagCompound tag = new NBTTagCompound();
         tag.setShort("d", (short)CarpentryManager.carpentryInterface.getDesignIndex(this.getDesign()));
         nbt.put("design", tag);
      }

      public DesignerType getDesignerType() {
         return this.type;
      }
   }

   public abstract static class PackageCarpenter extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
      DesignerType type;

      public PackageCarpenter(DesignerType type) {
         super(type.name, type.texture, false);
         this.type = type;
      }

      public void createMachine(Machine machine) {
         new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Woodworker);
         ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
         inventory.addSlot(Designer.beeswaxSlot, "polish");
         inventory.addSlot(Designer.design1Slot, "wood");
         inventory.addSlot(Designer.design2Slot, "wood");
         inventory.getSlot(Designer.beeswaxSlot).setValidator(new Validators.SlotValidatorBeeswax(this.type));
         inventory.getSlot(Designer.design1Slot).setValidator(new Validators.SlotValidatorPlanks(this.type));
         inventory.getSlot(Designer.design2Slot).setValidator(new Validators.SlotValidatorPlanks(this.type));
         new Designer.ComponentWoodworkerRecipe(machine, this.type);
      }
   }

   public static class PackageGlassworker extends Designer.PackageCarpenter {
      public PackageGlassworker() {
         super(DesignerType.GlassWorker);
      }
   }

   public static class PackagePanelworker extends Designer.PackageCarpenter {
      public PackagePanelworker() {
         super(DesignerType.Panelworker);
      }
   }

   public static class PackageTileworker extends Designer.PackageCarpenter {
      public PackageTileworker() {
         super(DesignerType.Tileworker);
      }
   }

   public static class PackageWoodworker extends Designer.PackageCarpenter {
      public PackageWoodworker() {
         super(DesignerType.Woodworker);
      }
   }
}
