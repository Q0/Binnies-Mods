package binnie.core.machines;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.PowerSystem;
import binnie.core.util.ItemStackSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class MachineUtil {
   private IMachine machine;

   public MachineUtil(IMachine machine) {
      super();
      this.machine = machine;
   }

   public IInventory getInventory() {
      return (IInventory)this.machine.getInterface(IInventory.class);
   }

   public ITankMachine getTankContainer() {
      return (ITankMachine)this.machine.getInterface(ITankMachine.class);
   }

   public IPoweredMachine getPoweredMachine() {
      return (IPoweredMachine)this.machine.getInterface(IPoweredMachine.class);
   }

   public boolean isSlotEmpty(int slot) {
      return this.getInventory().getStackInSlot(slot) == null;
   }

   public IFluidTank getTank(int id) {
      return this.getTankContainer().getTanks()[id];
   }

   public boolean spaceInTank(int id, int amount) {
      IFluidTank tank = this.getTank(id);
      int space = tank.getCapacity() - tank.getFluidAmount();
      return amount <= space;
   }

   public ItemStack getStack(int slot) {
      return this.getInventory().getStackInSlot(slot);
   }

   public void deleteStack(int slot) {
      this.setStack(slot, (ItemStack)null);
   }

   public ItemStack decreaseStack(int slotWood, int amount) {
      return this.getInventory().decrStackSize(slotWood, amount);
   }

   public void setStack(int slot, ItemStack stack) {
      this.getInventory().setInventorySlotContents(slot, stack);
   }

   public void fillTank(int id, FluidStack liquidStack) {
      IFluidTank tank = this.getTank(id);
      tank.fill(liquidStack, true);
   }

   public void addStack(int slot, ItemStack addition) {
      if(this.isSlotEmpty(slot)) {
         this.setStack(slot, addition);
      } else {
         ItemStack merge = this.getStack(slot);
         if(merge.isItemEqual(addition) && merge.stackSize + addition.stackSize <= merge.getMaxStackSize()) {
            merge.stackSize += addition.stackSize;
            this.setStack(slot, merge);
         }
      }

   }

   public FluidStack drainTank(int tank, int amount) {
      return this.getTank(tank).drain(amount, true);
   }

   public boolean liquidInTank(int tank, int amount) {
      return this.getTank(tank).drain(amount, false) != null && this.getTank(tank).drain(amount, false).amount == amount;
   }

   public void damageItem(int slot, int damage) {
      ItemStack item = this.getStack(slot);
      if(damage < 0) {
         item.setItemDamage(Math.max(0, item.getItemDamage() + damage));
      } else if(item.attemptDamageItem(damage, new Random())) {
         this.setStack(slot, (ItemStack)null);
      }

      this.setStack(slot, item);
   }

   public boolean isTankEmpty(int tankInput) {
      return this.getTank(tankInput).getFluidAmount() == 0;
   }

   public FluidStack getFluid(int tankInput) {
      return this.getTank(tankInput).getFluid() == null?null:this.getTank(tankInput).getFluid();
   }

   public ItemStack[] getStacks(int[] slotGrains) {
      ItemStack[] stacks = new ItemStack[slotGrains.length];

      for(int i = 0; i < slotGrains.length; ++i) {
         stacks[i] = this.getStack(slotGrains[i]);
      }

      return stacks;
   }

   public ItemStack hasIngredients(int recipe, int[] inventory) {
      return null;
   }

   public boolean hasIngredients(int[] recipe, int[] inventory) {
      ItemStackSet requiredStacks = new ItemStackSet();

      for(ItemStack stack : this.getStacks(recipe)) {
         requiredStacks.add(stack);
      }

      ItemStackSet inventoryStacks = new ItemStackSet();

      for(ItemStack stack : this.getStacks(inventory)) {
         inventoryStacks.add(stack);
      }

      requiredStacks.removeAll(inventoryStacks);
      return requiredStacks.isEmpty();
   }

   public void useEnergyMJ(float powerUsage) {
      this.getPoweredMachine().getInterface().useEnergy(PowerSystem.MJ, (double)powerUsage, true);
   }

   public boolean hasEnergyMJ(float powerUsage) {
      return this.getPoweredMachine().getInterface().useEnergy(PowerSystem.MJ, (double)powerUsage, false) >= (double)powerUsage;
   }

   public float getSlotCharge(int slot) {
      return ((IChargedSlots)this.machine.getInterface(IChargedSlots.class)).getCharge(slot);
   }

   public void useCharge(int slot, float loss) {
      ((IChargedSlots)this.machine.getInterface(IChargedSlots.class)).alterCharge(slot, -loss);
   }

   public Random getRandom() {
      return new Random();
   }

   public void refreshBlock() {
      this.machine.getWorld().markBlockForUpdate(this.machine.getTileEntity().xCoord, this.machine.getTileEntity().yCoord, this.machine.getTileEntity().zCoord);
   }

   public IProcess getProcess() {
      return (IProcess)this.machine.getInterface(IProcess.class);
   }

   public List getNonNullStacks(int[] slotacclimatiser) {
      List<ItemStack> stacks = new ArrayList();

      for(ItemStack stack : this.getStacks(slotacclimatiser)) {
         if(stack != null) {
            stacks.add(stack);
         }
      }

      return stacks;
   }

   public boolean isServer() {
      return BinnieCore.proxy.isSimulating(this.machine.getWorld());
   }
}
