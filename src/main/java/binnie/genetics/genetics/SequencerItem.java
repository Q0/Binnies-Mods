package binnie.genetics.genetics;

import binnie.genetics.api.IGene;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SequencerItem extends GeneItem {
    public int sequenced;
    public boolean analysed;

    public SequencerItem(ItemStack stack) {
        super(stack);
    }

    public SequencerItem(IGene gene) {
        super(gene);
        this.sequenced = 0;
        this.analysed = false;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.sequenced = nbt.getByte("seq");
        this.analysed = nbt.getBoolean("ana");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setByte("seq", (byte) this.sequenced);
        nbt.setBoolean("ana", this.analysed);
    }
}
