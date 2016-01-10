package binnie.extrabees.genetics.effect;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;

public class FireworkCreator {
    public FireworkCreator() {
        super();
    }

    public static class Firework {
        boolean flicker = false;
        boolean trail = false;
        ArrayList colors = new ArrayList();
        byte shape = 0;

        public Firework() {
            super();
        }

        public void setFlicker() {
            this.flicker = true;
        }

        public void setTrail() {
            this.trail = true;
        }

        public void setShape(FireworkCreator.Shape shape) {
            this.shape = (byte) shape.ordinal();
        }

        public void addColor(int color) {
            this.colors.add(Integer.valueOf(color));
        }

        NBTTagCompound getNBT() {
            NBTTagCompound nbt = new NBTTagCompound();
            if (this.flicker) {
                nbt.setBoolean("Flicker", true);
            }

            if (this.trail) {
                nbt.setBoolean("Trail", true);
            }

            if (this.colors.size() == 0) {
                this.addColor(16777215);
            }

            int[] array = new int[this.colors.size()];

            for (int i = 0; i < this.colors.size(); ++i) {
                array[i] = ((Integer) this.colors.get(i)).intValue();
            }

            nbt.setIntArray("Colors", array);
            nbt.setByte("Type", this.shape);
            return nbt;
        }

        public ItemStack getFirework() {
            NBTTagCompound var15 = new NBTTagCompound();
            NBTTagCompound var18 = new NBTTagCompound();
            NBTTagList var25 = new NBTTagList();
            var25.appendTag(this.getNBT());
            var18.setTag("Explosions", var25);
            var18.setByte("Flight", (byte) 0);
            var15.setTag("Fireworks", var18);
            ItemStack item = new ItemStack(Items.fireworks);
            item.setTagCompound(var15);
            return item;
        }
    }

    public static enum Shape {
        Ball,
        LargeBall,
        Star,
        Creeper,
        Burst;

        private Shape() {
        }
    }
}
