package binnie.core.machines.power;

import forestry.api.core.INBTTagable;
import net.minecraft.nbt.NBTTagCompound;

public class ErrorState implements INBTTagable {
    private String name = "";
    private String desc = "";
    private int[] data = new int[0];
    private boolean progress = false;
    private boolean itemError = false;
    private boolean tankError = false;
    private boolean powerError = false;

    public ErrorState(String name, String desc) {
        super();
        this.name = name;
        this.desc = desc;
    }

    public ErrorState(String name, String desc, int[] data) {
        super();
        this.name = name;
        this.desc = desc;
        this.data = data;
    }

    public String toString() {
        return this.name;
    }

    public String getTooltip() {
        return this.desc;
    }

    public int[] getData() {
        return this.data;
    }

    public boolean isProgress() {
        return this.progress;
    }

    public void setIsProgress() {
        this.progress = true;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        this.name = nbt.getString("name");
        this.desc = nbt.getString("desc");
        this.data = nbt.getIntArray("data");
        this.itemError = nbt.getBoolean("item");
        this.tankError = nbt.getBoolean("tank");
        this.powerError = nbt.getBoolean("power");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setString("name", this.toString());
        nbt.setString("desc", this.getTooltip());
        nbt.setIntArray("data", this.data);
        if (this.isItemError()) {
            nbt.setBoolean("item", true);
        }

        if (this.isTankError()) {
            nbt.setBoolean("tank", true);
        }

        if (this.isPowerError()) {
            nbt.setBoolean("power", true);
        }

    }

    public boolean isItemError() {
        return this.itemError || this instanceof ErrorState.Item;
    }

    public boolean isTankError() {
        return this.tankError || this instanceof ErrorState.Tank;
    }

    public boolean isPowerError() {
        return this.powerError || this instanceof ErrorState.InsufficientPower;
    }

    public static class InsufficientLiquid extends ErrorState.Tank {
        public InsufficientLiquid(String desc, int tank) {
            super("Insufficient Liquid", desc, new int[]{tank});
        }
    }

    public static class InsufficientPower extends ErrorState {
        public InsufficientPower() {
            super("Insufficient Power", "Not enough power to operate");
        }
    }

    public static class InvalidItem extends ErrorState.Item {
        public InvalidItem(String desc, int slot) {
            this("Invalid Item", desc, slot);
        }

        public InvalidItem(String name, String desc, int slot) {
            super(name, desc, new int[]{slot});
        }
    }

    public static class InvalidRecipe extends ErrorState.Item {
        public InvalidRecipe(String string, int[] slots) {
            super("Invalid Recipe", string, slots);
        }
    }

    public static class Item extends ErrorState {
        public Item(String name, String desc, int[] slots) {
            super(name, desc, slots);
        }
    }

    public static class NoItem extends ErrorState.Item {
        public NoItem(String desc, int slot) {
            this(desc, new int[]{slot});
        }

        public NoItem(String desc, int[] slots) {
            super("No Item", desc, slots);
        }
    }

    public static class NoSpace extends ErrorState.Item {
        public NoSpace(String desc, int[] slots) {
            super("No Space", desc, slots);
        }
    }

    public static class Tank extends ErrorState {
        public Tank(String name, String desc, int[] slots) {
            super(name, desc, slots);
        }
    }

    public static class TankSpace extends ErrorState.Tank {
        public TankSpace(String desc, int tank) {
            super("Tank Full", desc, new int[]{tank});
        }
    }
}
