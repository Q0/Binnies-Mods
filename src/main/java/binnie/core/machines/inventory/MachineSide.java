package binnie.core.machines.inventory;

import net.minecraftforge.common.util.ForgeDirection;

import java.util.Collection;
import java.util.EnumSet;

public class MachineSide {
    private static EnumSet All = EnumSet.of(ForgeDirection.UP, new ForgeDirection[]{ForgeDirection.DOWN, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST});
    public static EnumSet TopAndBottom = EnumSet.of(ForgeDirection.UP, ForgeDirection.DOWN);
    public static EnumSet None = EnumSet.noneOf(ForgeDirection.class);
    public static EnumSet Top = EnumSet.of(ForgeDirection.UP);
    public static EnumSet Bottom = EnumSet.of(ForgeDirection.DOWN);
    public static EnumSet Sides = EnumSet.of(ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST);

    public MachineSide() {
        super();
    }

    public static String asString(Collection sides) {
        if (sides.containsAll(All)) {
            return "Any";
        } else if (sides.isEmpty()) {
            return "None";
        } else {
            String text = "";
            if (sides.contains(ForgeDirection.UP)) {
                if (sides.size() > 0) {
                    text = text + ", ";
                }

                text = text + "Up";
            }

            if (sides.contains(ForgeDirection.DOWN)) {
                if (sides.size() > 0) {
                    text = text + ", ";
                }

                text = text + "Down";
            }

            if (sides.containsAll(Sides)) {
                if (sides.size() > 0) {
                    text = text + ", ";
                }

                text = text + "Sides";
            } else {
                if (sides.contains(ForgeDirection.NORTH)) {
                    if (sides.size() > 0) {
                        text = text + ", ";
                    }

                    text = text + "North";
                }

                if (sides.contains(ForgeDirection.EAST)) {
                    if (sides.size() > 0) {
                        text = text + ", ";
                    }

                    text = text + "East";
                }

                if (sides.contains(ForgeDirection.SOUTH)) {
                    if (sides.size() > 0) {
                        text = text + ", ";
                    }

                    text = text + "South";
                }

                if (sides.contains(ForgeDirection.WEST)) {
                    if (sides.size() > 0) {
                        text = text + ", ";
                    }

                    text = text + "West";
                }
            }

            return text;
        }
    }
}
