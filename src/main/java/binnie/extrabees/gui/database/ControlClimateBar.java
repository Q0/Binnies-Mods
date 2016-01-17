package binnie.extrabees.gui.database;

import binnie.Binnie;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;

import java.util.ArrayList;
import java.util.List;

public class ControlClimateBar extends Control implements ITooltip {
    boolean isHumidity;
    List<Integer> tolerated;
    int[] tempColours;
    int[] humidColours;

    public ControlClimateBar(final IWidget parent, final int x, final int y, final int width, final int height) {
        super(parent, x, y, width, height);
        isHumidity = false;
        tolerated = new ArrayList<Integer>();
        tempColours = new int[]{0xfffb, 0x78bbff, 0x4fff30, 0xffff00, 0xffa200, 0xff0000};
        humidColours = new int[]{0xffe7a3, 0x1aff00, 0x307cff};
        addAttribute(Attribute.MouseOver);
    }

    public ControlClimateBar(final IWidget parent, final int x, final int y, final int width, final int height, final boolean humidity) {
        super(parent, x, y, width, height);
        tolerated = new ArrayList<Integer>();
        tempColours = new int[]{0xfffb, 0x78bbff, 0x4fff30, 0xffff00, 0xffa200, 0xff0000};
        humidColours = new int[]{0xffe7a3, 0x1aff00, 0x307cff};
        addAttribute(Attribute.MouseOver);
        isHumidity = humidity;
    }

    @Override
    public void getTooltip(final Tooltip list) {
        if (tolerated.isEmpty()) {
            return;
        }

        final int types = isHumidity ? 3 : 6;
        final int type = (int) ((int) (getRelativeMousePosition().x() - 1.0f) / ((getSize().x() - 2.0f) / types));

        if (!tolerated.contains(type)) {
            return;
        }

        if (type < types) {
            if (isHumidity) {
                list.add(EnumHumidity.values()[type].name);
            }
            else {
                list.add(EnumTemperature.values()[type + 1].name);
            }
        }
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.Render.texture(CraftGUITexture.EnergyBarBack, getArea());
        final int types = isHumidity ? 3 : 6;
        final int w = (int) ((getSize().x() - 2.0f) / types);

        for (int i = 0; i < types; ++i) {
            final int x = i * w;

            if (tolerated.contains(i)) {
                int colour;

                if (isHumidity) {
                    colour = humidColours[i];
                }
                else {
                    colour = tempColours[i];
                }

                CraftGUI.Render.solid(new IArea(x + 1, 1.0f, w, getSize().y() - 2.0f), colour);
            }
        }

        CraftGUI.Render.texture(CraftGUITexture.EnergyBarGlass, getArea());
    }

    public void setSpecies(final IAlleleBeeSpecies species) {
        tolerated.clear();

        if (species == null) {
            return;
        }

        int main;
        EnumTolerance tolerance;

        if (!isHumidity) {
            main = species.getTemperature().ordinal() - 1;
            final IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(Binnie.Genetics.getBeeRoot().getTemplate(species.getUID()));
            tolerance = genome.getToleranceTemp();
        }
        else {
            main = species.getHumidity().ordinal();
            final IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(Binnie.Genetics.getBeeRoot().getTemplate(species.getUID()));
            tolerance = genome.getToleranceHumid();
        }

        tolerated.add(main);

        switch (tolerance) {
            case BOTH_5:
            case UP_5:
                tolerated.add(main + 5);
                break;

            case BOTH_4:
            case UP_4:
                tolerated.add(main + 4);
                break;

            case BOTH_3:
            case UP_3:
                tolerated.add(main + 3);
                break;

            case BOTH_2:
            case UP_2:
                tolerated.add(main + 2);
                break;

            case BOTH_1:
            case UP_1:
                tolerated.add(main + 1);
                break;
        }

        switch (tolerance) {
            case BOTH_5:
            case DOWN_5:
                tolerated.add(main - 5);
                break;

            case BOTH_4:
            case DOWN_4:
                tolerated.add(main - 4);
                break;

            case BOTH_3:
            case DOWN_3:
                tolerated.add(main - 3);
                break;

            case BOTH_2:
            case DOWN_2:
                tolerated.add(main - 2);
                break;

            case BOTH_1:
            case DOWN_1:
                tolerated.add(main - 1);
                break;
        }
    }
}
