package binnie.core.machines.inventory;

import binnie.Binnie;
import binnie.core.BinnieCore;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class InventorySlot extends BaseSlot<ItemStack> {
    private ItemStack itemStack;
    private Type type;

    public InventorySlot(final int index, final String unlocName) {
        super(index, unlocName);
        this.itemStack = null;
        this.type = Type.Standard;
    }

    @Override
    public ItemStack getContent() {
        return this.itemStack;
    }

    @Override
    public void setContent(final ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.getContent();
    }

    public void setItemStack(final ItemStack duplicate) {
        this.setContent(duplicate);
    }

    public ItemStack decrStackSize(final int amount) {
        if (this.itemStack == null) {
            return null;
        }
        if (this.itemStack.stackSize <= amount) {
            final ItemStack returnStack = this.itemStack.copy();
            this.itemStack = null;
            return returnStack;
        }
        final ItemStack returnStack = this.itemStack.copy();
        final ItemStack itemStack = this.itemStack;
        itemStack.stackSize -= amount;
        returnStack.stackSize = amount;
        return returnStack;
    }

    public void readFromNBT(final NBTTagCompound slotNBT) {
        if (slotNBT.hasKey("item")) {
            final NBTTagCompound itemNBT = slotNBT.getCompoundTag("item");
            this.itemStack = ItemStack.loadItemStackFromNBT(itemNBT);
        } else {
            this.itemStack = null;
        }
    }

    public void writeToNBT(final NBTTagCompound slotNBT) {
        final NBTTagCompound itemNBT = new NBTTagCompound();
        if (this.itemStack != null) {
            this.itemStack.writeToNBT(itemNBT);
        }
        slotNBT.setTag("item", itemNBT);
    }

    @Override
    public SlotValidator getValidator() {
        return (SlotValidator) this.validator;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(final Type type) {
        this.type = type;
        if (type == Type.Recipe) {
            this.setReadOnly();
            this.forbidInteraction();
        }
    }

    public boolean isRecipe() {
        return this.type == Type.Recipe;
    }

    @Override
    public String getName() {
        return Binnie.Language.localise(BinnieCore.instance, "gui.slot." + this.unlocName);
    }

    public enum Type {
        Standard,
        Recipe
    }
}
