package binnie.extrabees.genetics.effect;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;

public class FireworkCreator {
    public enum Shape {
        Ball,
        LargeBall,
        Star,
        Creeper,
        Burst
    }

    public static class Firework {
        boolean flicker;
        boolean trail;
        ArrayList<Integer> colors;
        byte shape;

        public Firework() {
            flicker = false;
            trail = false;
            colors = new ArrayList<Integer>();
            shape = 0;
        }

        public void setFlicker() {
            flicker = true;
        }

        public void setTrail() {
            trail = true;
        }

        public void setShape(final Shape shape) {
            this.shape = (byte) shape.ordinal();
        }

        public void addColor(final int color) {
            colors.add(color);
        }

        NBTTagCompound getNBT() {
            final NBTTagCompound nbt = new NBTTagCompound();
            if (flicker) {
                nbt.setBoolean("Flicker", true);
            }

            if (trail) {
                nbt.setBoolean("Trail", true);
            }

            if (colors.size() == 0) {
                addColor(16777215);
            }

            final int[] array = new int[colors.size()];
            for (int i = 0; i < colors.size(); ++i) {
                array[i] = colors.get(i);
            }

            nbt.setIntArray("Colors", array);
            nbt.setByte("Type", shape);
            return nbt;
        }

        public ItemStack getFirework() {
            final NBTTagCompound var15 = new NBTTagCompound();
            final NBTTagCompound var16 = new NBTTagCompound();
            final NBTTagList var17 = new NBTTagList();
            var17.appendTag(getNBT());
            var16.setTag("Explosions", var17);
            var16.setByte("Flight", (byte) 0);
            var15.setTag("Fireworks", var16);
            final ItemStack item = new ItemStack(Items.fireworks);
            item.setTagCompound(var15);
            return item;
        }
    }
}
