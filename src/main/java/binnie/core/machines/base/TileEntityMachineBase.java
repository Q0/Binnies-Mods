package binnie.core.machines.base;

import binnie.core.machines.Machine;
import binnie.core.machines.base.DefaultInventory;
import binnie.core.machines.base.DefaultPower;
import binnie.core.machines.base.DefaultTankContainer;
import binnie.core.machines.inventory.IInventoryMachine;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.PowerInfo;
import binnie.core.machines.power.PowerInterface;
import binnie.core.machines.power.TankInfo;
import cpw.mods.fml.common.Optional.Method;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityMachineBase extends TileEntity implements IInventoryMachine, ITankMachine, IPoweredMachine {
   public TileEntityMachineBase() {
      super();
   }

   public IInventoryMachine getInventory() {
      IInventoryMachine inv = (IInventoryMachine)Machine.getInterface(IInventoryMachine.class, this);
      return (IInventoryMachine)(inv != null && inv != this?inv:new DefaultInventory());
   }

   public ITankMachine getTankContainer() {
      ITankMachine inv = (ITankMachine)Machine.getInterface(ITankMachine.class, this);
      return (ITankMachine)(inv != null && inv != this?inv:new DefaultTankContainer());
   }

   public IPoweredMachine getPower() {
      IPoweredMachine inv = (IPoweredMachine)Machine.getInterface(IPoweredMachine.class, this);
      return (IPoweredMachine)(inv != null && inv != this?inv:new DefaultPower());
   }

   public int getSizeInventory() {
      return this.getInventory().getSizeInventory();
   }

   public ItemStack getStackInSlot(int index) {
      return this.getInventory().getStackInSlot(index);
   }

   public ItemStack decrStackSize(int index, int amount) {
      return this.getInventory().decrStackSize(index, amount);
   }

   public ItemStack getStackInSlotOnClosing(int var1) {
      return this.getInventory().getStackInSlotOnClosing(var1);
   }

   public void setInventorySlotContents(int index, ItemStack itemStack) {
      this.getInventory().setInventorySlotContents(index, itemStack);
   }

   public String getInventoryName() {
      return this.getInventory().getInventoryName();
   }

   public int getInventoryStackLimit() {
      return this.getInventory().getInventoryStackLimit();
   }

   public boolean isUseableByPlayer(EntityPlayer entityplayer) {
      return this.isInvalid()?false:(this.getWorldObj().getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this?false:(entityplayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) > 64.0D?false:this.getInventory().isUseableByPlayer(entityplayer)));
   }

   public void openInventory() {
      this.getInventory().openInventory();
   }

   public void closeInventory() {
      this.getInventory().closeInventory();
   }

   public boolean hasCustomInventoryName() {
      return this.getInventory().hasCustomInventoryName();
   }

   public void markDirty() {
      super.markDirty();
      this.getInventory().markDirty();
   }

   public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
      return this.getInventory().isItemValidForSlot(slot, itemStack);
   }

   public int[] getAccessibleSlotsFromSide(int var1) {
      return this.getInventory().getAccessibleSlotsFromSide(var1);
   }

   public boolean canInsertItem(int i, ItemStack itemstack, int j) {
      return this.getInventory().canInsertItem(i, itemstack, j);
   }

   public boolean canExtractItem(int i, ItemStack itemstack, int j) {
      return this.getInventory().canExtractItem(i, itemstack, j);
   }

   public boolean isReadOnly(int slot) {
      return this.getInventory().isReadOnly(slot);
   }

   public PowerInfo getPowerInfo() {
      return this.getPower().getPowerInfo();
   }

   public TankInfo[] getTankInfos() {
      return this.getTankContainer().getTankInfos();
   }

   public boolean isTankReadOnly(int tank) {
      return this.getTankContainer().isTankReadOnly(tank);
   }

   public boolean isLiquidValidForTank(FluidStack liquid, int tank) {
      return this.getTankContainer().isLiquidValidForTank(liquid, tank);
   }

   public TankSlot addTank(int index, String name, int capacity) {
      return this.getTankContainer().addTank(index, name, capacity);
   }

   public IFluidTank getTank(int index) {
      return this.getTankContainer().getTank(index);
   }

   public TankSlot getTankSlot(int index) {
      return this.getTankContainer().getTankSlot(index);
   }

   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
      return this.getTankContainer().fill(from, resource, doFill);
   }

   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
      return this.getTankContainer().drain(from, resource, doDrain);
   }

   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
      return this.getTankContainer().drain(from, maxDrain, doDrain);
   }

   public boolean canFill(ForgeDirection from, Fluid fluid) {
      return this.getTankContainer().canFill(from, fluid);
   }

   public boolean canDrain(ForgeDirection from, Fluid fluid) {
      return this.getTankContainer().canDrain(from, fluid);
   }

   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
      return this.getTankContainer().getTankInfo(from);
   }

   public IFluidTank[] getTanks() {
      return this.getTankContainer().getTanks();
   }

   @Method(
      modid = "IC2"
   )
   public double getDemandedEnergy() {
      return this.getPower().getDemandedEnergy();
   }

   @Method(
      modid = "IC2"
   )
   public int getSinkTier() {
      return this.getPower().getSinkTier();
   }

   @Method(
      modid = "IC2"
   )
   public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
      return this.getPower().injectEnergy(directionFrom, amount, voltage);
   }

   @Method(
      modid = "IC2"
   )
   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
      return this.getPower().acceptsEnergyFrom(emitter, direction);
   }

   public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
      return this.getPower().receiveEnergy(from, maxReceive, simulate);
   }

   public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
      return this.getPower().extractEnergy(from, maxExtract, simulate);
   }

   public int getEnergyStored(ForgeDirection from) {
      return this.getPower().getEnergyStored(from);
   }

   public int getMaxEnergyStored(ForgeDirection from) {
      return this.getPower().getMaxEnergyStored(from);
   }

   public boolean canConnectEnergy(ForgeDirection from) {
      return this.getPower().canConnectEnergy(from);
   }

   public PowerInterface getInterface() {
      return this.getPower().getInterface();
   }
}
