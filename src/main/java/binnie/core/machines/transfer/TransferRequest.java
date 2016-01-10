package binnie.core.machines.transfer;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventorySlots;
import binnie.core.machines.inventory.IValidatedTankContainer;
import binnie.core.machines.power.ITankMachine;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidTank;

import java.util.ArrayList;
import java.util.List;

public class TransferRequest {
    private ItemStack itemToTransfer = null;
    private ItemStack returnItem = null;
    private IInventory origin;
    private IInventory destination;
    private int[] targetSlots = new int[0];
    private int[] targetTanks = new int[0];
    private boolean transferLiquids = true;
    private boolean ignoreReadOnly = false;
    private List insertedSlots = new ArrayList();
    private List insertedTanks = new ArrayList();

    public TransferRequest(ItemStack toTransfer, IInventory destination) {
        super();
        int[] target = new int[destination.getSizeInventory()];

        for (int i = 0; i < target.length; target[i] = i++) {
            ;
        }

        int[] targetTanks = new int[0];
        if (destination instanceof ITankMachine) {
            targetTanks = new int[((ITankMachine) destination).getTanks().length];

            for (int i = 0; i < targetTanks.length; targetTanks[i] = i++) {
                ;
            }
        }

        if (toTransfer != null) {
            this.setItemToTransfer(toTransfer.copy());
            this.setReturnItem(toTransfer.copy());
        }

        this.setOrigin((IInventory) null);
        this.setDestination(destination);
        this.setTargetSlots(target);
        this.setTargetTanks(targetTanks);
        this.transferLiquids = true;
    }

    private void setItemToTransfer(ItemStack itemToTransfer) {
        this.itemToTransfer = itemToTransfer;
    }

    private void setReturnItem(ItemStack returnItem) {
        this.returnItem = returnItem;
    }

    public TransferRequest setOrigin(IInventory origin) {
        this.origin = origin;
        return this;
    }

    private void setDestination(IInventory destination) {
        this.destination = destination;
    }

    public TransferRequest setTargetSlots(int[] targetSlots) {
        this.targetSlots = targetSlots;
        return this;
    }

    public TransferRequest setTargetTanks(int[] targetTanks) {
        this.targetTanks = targetTanks;
        return this;
    }

    public TransferRequest ignoreValidation() {
        this.ignoreReadOnly = true;
        return this;
    }

    public ItemStack getReturnItem() {
        return this.returnItem;
    }

    public ItemStack transfer(boolean doAdd) {
        ItemStack item = this.returnItem;
        if (item != null && this.destination != null) {
            if (this.transferLiquids && this.destination instanceof ITankMachine) {
                for (int tankID : this.targetTanks) {
                    item = this.transferToTank(item, this.origin, (ITankMachine) this.destination, tankID, doAdd);
                    if (item != null) {
                        item = this.transferFromTank(item, this.origin, (ITankMachine) this.destination, tankID, doAdd);
                    }
                }
            }

            if (item != null) {
                for (int slot : this.targetSlots) {
                    if ((this.destination.isItemValidForSlot(slot, item) || this.ignoreReadOnly) && (!(this.destination instanceof IInventorySlots) || ((IInventorySlots) this.destination).getSlot(slot) == null || !((IInventorySlots) this.destination).getSlot(slot).isRecipe()) && this.destination.getStackInSlot(slot) != null && item.isStackable()) {
                        ItemStack merged = this.destination.getStackInSlot(slot).copy();
                        ItemStack[] newStacks = mergeStacks(item.copy(), merged.copy());
                        item = newStacks[0];
                        if (!areItemsEqual(merged, newStacks[1])) {
                            this.insertedSlots.add(new TransferRequest.TransferSlot(slot, this.destination));
                        }

                        if (doAdd) {
                            this.destination.setInventorySlotContents(slot, newStacks[1]);
                        }

                        if (item == null) {
                            return null;
                        }
                    }
                }
            }

            if (item != null) {
                for (int slot : this.targetSlots) {
                    if ((this.destination.isItemValidForSlot(slot, item) || this.ignoreReadOnly) && (!(this.destination instanceof IInventorySlots) || ((IInventorySlots) this.destination).getSlot(slot) == null || !((IInventorySlots) this.destination).getSlot(slot).isRecipe()) && this.destination.getStackInSlot(slot) == null && item != null) {
                        this.insertedSlots.add(new TransferRequest.TransferSlot(slot, this.destination));
                        if (doAdd) {
                            this.destination.setInventorySlotContents(slot, item.copy());
                        }

                        return null;
                    }
                }
            }

            this.setReturnItem(item);
            return this.getReturnItem();
        } else {
            return null;
        }
    }

    private static boolean areItemsEqual(ItemStack merged, ItemStack itemstack) {
        return ItemStack.areItemStackTagsEqual(itemstack, merged) && itemstack.isItemEqual(merged);
    }

    public static ItemStack[] mergeStacks(ItemStack itemstack, ItemStack merged) {
        if (areItemsEqual(itemstack, merged)) {
            int space = merged.getMaxStackSize() - merged.stackSize;
            if (space > 0) {
                if (itemstack.stackSize > space) {
                    itemstack.stackSize -= space;
                    merged.stackSize += space;
                } else if (itemstack.stackSize <= space) {
                    merged.stackSize += itemstack.stackSize;
                    itemstack = null;
                }
            }
        }

        return new ItemStack[]{itemstack, merged};
    }

    private ItemStack transferToTank(ItemStack item, IInventory origin, ITankMachine destination, int tankID, boolean doAdd) {
        item = this.transferToTankUsingContainerData(item, origin, destination, tankID, doAdd);
        item = this.transferToTankUsingFluidContainer(item, origin, destination, tankID, doAdd);
        return item;
    }

    private ItemStack transferToTankUsingFluidContainer(ItemStack item, IInventory origin, ITankMachine destination, int tankID, boolean doAdd) {
        if (item != null && item.getItem() instanceof IFluidContainerItem) {
            IFluidContainerItem fluidContainer = (IFluidContainerItem) item.getItem();
            FluidStack fluid = fluidContainer.getFluid(item);
            if (fluid == null) {
                return item;
            } else {
                IFluidTank tank = destination.getTanks()[tankID];
                IValidatedTankContainer validated = (IValidatedTankContainer) Machine.getInterface(IValidatedTankContainer.class, destination);
                if (validated == null || validated.isLiquidValidForTank(fluid, tankID) && !validated.isTankReadOnly(tankID)) {
                    int maxFill = tank.fill(fluid, false);
                    FluidStack toTake = fluidContainer.drain(item, maxFill, true);
                    if (doAdd) {
                        tank.fill(toTake, true);
                    }

                    return item;
                } else {
                    return item;
                }
            }
        } else {
            return item;
        }
    }

    private ItemStack transferToTankUsingContainerData(ItemStack item, IInventory origin, ITankMachine destination, int tankID, boolean doAdd) {
        if (item == null) {
            return item;
        } else {
            FluidStack containerLiquid = null;
            FluidContainerData containerLiquidData = null;

            for (FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
                if (data.filledContainer.isItemEqual(item)) {
                    containerLiquidData = data;
                    containerLiquid = data.fluid.copy();
                    break;
                }
            }

            if (containerLiquid == null) {
                return item;
            } else {
                IFluidTank tank = destination.getTanks()[tankID];
                IValidatedTankContainer validated = (IValidatedTankContainer) Machine.getInterface(IValidatedTankContainer.class, destination);
                if (validated == null || validated.isLiquidValidForTank(containerLiquid, tankID) && !validated.isTankReadOnly(tankID)) {
                    FluidStack largeAmountOfLiquid = containerLiquid.copy();
                    largeAmountOfLiquid.amount = tank.getCapacity();
                    int amountAdded = tank.fill(largeAmountOfLiquid, false);
                    int numberOfContainersToAdd = amountAdded / containerLiquid.amount;
                    if (numberOfContainersToAdd > item.stackSize) {
                        numberOfContainersToAdd = item.stackSize;
                    }

                    ItemStack leftOverContainers = item.copy();
                    leftOverContainers.stackSize -= numberOfContainersToAdd;
                    if (leftOverContainers.stackSize <= 0) {
                        leftOverContainers = null;
                    }

                    ItemStack emptyContainers = containerLiquidData.emptyContainer.copy();
                    emptyContainers.stackSize = 0;
                    emptyContainers.stackSize += numberOfContainersToAdd;
                    if (emptyContainers.stackSize <= 0) {
                        emptyContainers = null;
                    }

                    TransferRequest containersDump = new TransferRequest(emptyContainers, origin);
                    ItemStack containersThatCantBeDumped = containersDump.transfer(false);
                    if (containersThatCantBeDumped != null) {
                        return item;
                    } else {
                        if (doAdd) {
                            FluidStack liquidToFillTank = containerLiquid.copy();
                            liquidToFillTank.amount *= numberOfContainersToAdd;
                            tank.fill(liquidToFillTank, true);
                            containersDump.transfer(true);
                        }

                        return leftOverContainers;
                    }
                } else {
                    return item;
                }
            }
        }
    }

    private ItemStack transferFromTank(ItemStack item, IInventory origin, ITankMachine destination, int tankID, boolean doAdd) {
        item = this.transferFromTankUsingContainerData(item, origin, destination, tankID, doAdd);
        item = this.transferFromTankUsingFluidContainer(item, origin, destination, tankID, doAdd);
        return item;
    }

    private ItemStack transferFromTankUsingFluidContainer(ItemStack item, IInventory origin, ITankMachine destination, int tankID, boolean doAdd) {
        if (item != null && item.getItem() instanceof IFluidContainerItem) {
            IFluidContainerItem fluidContainer = (IFluidContainerItem) item.getItem();
            IFluidTank tank = destination.getTanks()[tankID];
            FluidStack fluid = tank.getFluid();
            if (fluid == null) {
                return item;
            } else {
                int amount = fluidContainer.fill(item, fluid, false);
                amount = Math.min(amount, tank.drain(amount, false) == null ? 0 : tank.drain(amount, false).amount);
                if (amount <= 0) {
                    return item;
                } else {
                    fluidContainer.fill(item, tank.drain(amount, doAdd), doAdd);
                    return item;
                }
            }
        } else {
            return item;
        }
    }

    private ItemStack transferFromTankUsingContainerData(ItemStack item, IInventory origin, ITankMachine destination, int tankID, boolean doAdd) {
        if (item == null) {
            return item;
        } else {
            IFluidTank tank = destination.getTanks()[tankID];
            FluidStack liquidInTank = tank.getFluid();
            if (liquidInTank == null) {
                return item;
            } else {
                FluidContainerData containerLiquidData = null;

                for (FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
                    if (data.emptyContainer.isItemEqual(item) && liquidInTank.isFluidEqual(data.fluid)) {
                        containerLiquidData = data;
                        break;
                    }
                }

                FluidStack fluid = null;
                ItemStack filled = null;
                if (containerLiquidData != null) {
                    fluid = containerLiquidData.fluid;
                    filled = containerLiquidData.filledContainer;
                }

                if (fluid != null && filled != null) {
                    int maximumExtractedLiquid = item.stackSize * fluid.amount;
                    FluidStack drainedLiquid = tank.drain(maximumExtractedLiquid, false);
                    int amountInTank = drainedLiquid == null ? 0 : drainedLiquid.amount;
                    int numberOfContainersToFill = amountInTank / fluid.amount;
                    if (numberOfContainersToFill > item.stackSize) {
                        numberOfContainersToFill = item.stackSize;
                    }

                    ItemStack leftOverContainers = item.copy();
                    leftOverContainers.stackSize -= numberOfContainersToFill;
                    if (leftOverContainers.stackSize <= 0) {
                        leftOverContainers = null;
                    }

                    ItemStack filledContainers = filled.copy();
                    filledContainers.stackSize = 0;
                    filledContainers.stackSize += numberOfContainersToFill;
                    if (filledContainers.stackSize <= 0) {
                        filledContainers = null;
                    }

                    TransferRequest containersDump = new TransferRequest(filledContainers, origin);
                    ItemStack containersThatCantBeDumped = containersDump.transfer(false);
                    if (containersThatCantBeDumped != null) {
                        return item;
                    } else {
                        if (doAdd) {
                            tank.drain(maximumExtractedLiquid, true);
                            containersDump.transfer(true);
                        }

                        return leftOverContainers;
                    }
                } else {
                    return item;
                }
            }
        }
    }

    public List getInsertedSlots() {
        return this.insertedSlots;
    }

    public List getInsertedTanks() {
        return this.insertedTanks;
    }

    public IInventory getOrigin() {
        return this.origin;
    }

    public IInventory getDestination() {
        return this.destination;
    }

    public ItemStack getItemToTransfer() {
        return this.itemToTransfer;
    }

    public int[] getTargetSlots() {
        return this.targetSlots;
    }

    public int[] getTargetTanks() {
        return this.targetTanks;
    }

    public static class TransferSlot {
        public int id;
        public IInventory inventory;

        public TransferSlot(int id, IInventory inventory) {
            super();
            this.id = id;
            this.inventory = inventory;
        }
    }
}
