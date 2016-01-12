package binnie.core.machines.base;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventoryMachine;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.*;
import cpw.mods.fml.common.Optional;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityMachineBase extends TileEntity implements IInventoryMachine, ITankMachine, IPoweredMachine {
    public IInventoryMachine getInventory() {
        final IInventoryMachine inv = Machine.getInterface(IInventoryMachine.class, this);
        return (inv == null || inv == this) ? new DefaultInventory() : inv;
    }

    public ITankMachine getTankContainer() {
        final ITankMachine inv = Machine.getInterface(ITankMachine.class, this);
        return (inv == null || inv == this) ? new DefaultTankContainer() : inv;
    }

    public IPoweredMachine getPower() {
        final IPoweredMachine inv = Machine.getInterface(IPoweredMachine.class, this);
        return (inv == null || inv == this) ? new DefaultPower() : inv;
    }

    public int getSizeInventory() {
        return this.getInventory().getSizeInventory();
    }

    public ItemStack getStackInSlot(final int index) {
        return this.getInventory().getStackInSlot(index);
    }

    public ItemStack decrStackSize(final int index, final int amount) {
        return this.getInventory().decrStackSize(index, amount);
    }

    public ItemStack getStackInSlotOnClosing(final int var1) {
        return this.getInventory().getStackInSlotOnClosing(var1);
    }

    public void setInventorySlotContents(final int index, final ItemStack itemStack) {
        this.getInventory().setInventorySlotContents(index, itemStack);
    }

    public String getInventoryName() {
        return this.getInventory().getInventoryName();
    }

    public int getInventoryStackLimit() {
        return this.getInventory().getInventoryStackLimit();
    }

    public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
        return !this.isInvalid() && this.getWorldObj().getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && entityplayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0 && this.getInventory().isUseableByPlayer(entityplayer);
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

    public boolean isItemValidForSlot(final int slot, final ItemStack itemStack) {
        return this.getInventory().isItemValidForSlot(slot, itemStack);
    }

    public int[] getAccessibleSlotsFromSide(final int var1) {
        return this.getInventory().getAccessibleSlotsFromSide(var1);
    }

    public boolean canInsertItem(final int i, final ItemStack itemstack, final int j) {
        return this.getInventory().canInsertItem(i, itemstack, j);
    }

    public boolean canExtractItem(final int i, final ItemStack itemstack, final int j) {
        return this.getInventory().canExtractItem(i, itemstack, j);
    }

    public boolean isReadOnly(final int slot) {
        return this.getInventory().isReadOnly(slot);
    }

    public PowerInfo getPowerInfo() {
        return this.getPower().getPowerInfo();
    }

    public TankInfo[] getTankInfos() {
        return this.getTankContainer().getTankInfos();
    }

    public boolean isTankReadOnly(final int tank) {
        return this.getTankContainer().isTankReadOnly(tank);
    }

    public boolean isLiquidValidForTank(final FluidStack liquid, final int tank) {
        return this.getTankContainer().isLiquidValidForTank(liquid, tank);
    }

    public TankSlot addTank(final int index, final String name, final int capacity) {
        return this.getTankContainer().addTank(index, name, capacity);
    }

    public IFluidTank getTank(final int index) {
        return this.getTankContainer().getTank(index);
    }

    public TankSlot getTankSlot(final int index) {
        return this.getTankContainer().getTankSlot(index);
    }

    public int fill(final ForgeDirection from, final FluidStack resource, final boolean doFill) {
        return this.getTankContainer().fill(from, resource, doFill);
    }

    public FluidStack drain(final ForgeDirection from, final FluidStack resource, final boolean doDrain) {
        return this.getTankContainer().drain(from, resource, doDrain);
    }

    public FluidStack drain(final ForgeDirection from, final int maxDrain, final boolean doDrain) {
        return this.getTankContainer().drain(from, maxDrain, doDrain);
    }

    public boolean canFill(final ForgeDirection from, final Fluid fluid) {
        return this.getTankContainer().canFill(from, fluid);
    }

    public boolean canDrain(final ForgeDirection from, final Fluid fluid) {
        return this.getTankContainer().canDrain(from, fluid);
    }

    public FluidTankInfo[] getTankInfo(final ForgeDirection from) {
        return this.getTankContainer().getTankInfo(from);
    }

    public IFluidTank[] getTanks() {
        return this.getTankContainer().getTanks();
    }

    @Optional.Method(modid = "IC2")
    public double getDemandedEnergy() {
        return this.getPower().getDemandedEnergy();
    }

    @Optional.Method(modid = "IC2")
    public int getSinkTier() {
        return this.getPower().getSinkTier();
    }

    @Optional.Method(modid = "IC2")
    public double injectEnergy(final ForgeDirection directionFrom, final double amount, final double voltage) {
        return this.getPower().injectEnergy(directionFrom, amount, voltage);
    }

    @Optional.Method(modid = "IC2")
    public boolean acceptsEnergyFrom(final TileEntity emitter, final ForgeDirection direction) {
        return this.getPower().acceptsEnergyFrom(emitter, direction);
    }

    public int receiveEnergy(final ForgeDirection from, final int maxReceive, final boolean simulate) {
        return this.getPower().receiveEnergy(from, maxReceive, simulate);
    }

    public int extractEnergy(final ForgeDirection from, final int maxExtract, final boolean simulate) {
        return this.getPower().extractEnergy(from, maxExtract, simulate);
    }

    public int getEnergyStored(final ForgeDirection from) {
        return this.getPower().getEnergyStored(from);
    }

    public int getMaxEnergyStored(final ForgeDirection from) {
        return this.getPower().getMaxEnergyStored(from);
    }

    public boolean canConnectEnergy(final ForgeDirection from) {
        return this.getPower().canConnectEnergy(from);
    }

    public PowerInterface getInterface() {
        return this.getPower().getInterface();
    }
}
