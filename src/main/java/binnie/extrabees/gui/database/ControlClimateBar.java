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
    boolean isHumidity = false;
    List tolerated = new ArrayList();
    int[] tempColours = new int[]{'\ufffb', 7912447, 5242672, 16776960, 16753152, 16711680};
    int[] humidColours = new int[]{16770979, 1769216, 3177727};

    public ControlClimateBar(IWidget parent, int x, int y, int width, int height) {
        super(parent, (float) x, (float) y, (float) width, (float) height);
        this.addAttribute(Attribute.MouseOver);
    }

    public ControlClimateBar(IWidget parent, int x, int y, int width, int height, boolean humidity) {
        super(parent, (float) x, (float) y, (float) width, (float) height);
        this.addAttribute(Attribute.MouseOver);
        this.isHumidity = true;
    }

    public void getTooltip(Tooltip list) {
        if (!this.tolerated.isEmpty()) {
            int types = this.isHumidity ? 3 : 6;
            int type = (int) ((float) ((int) (this.getRelativeMousePosition().x() - 1.0F)) / ((this.getSize().x() - 2.0F) / (float) types));
            if (this.tolerated.contains(Integer.valueOf(type))) {
                if (type < types) {
                    if (this.isHumidity) {
                        list.add(EnumHumidity.values()[type].name);
                    } else {
                        list.add(EnumTemperature.values()[type + 1].name);
                    }
                }

            }
        }
    }

    public void onRenderBackground() {
        CraftGUI.Render.texture((Object) CraftGUITexture.EnergyBarBack, (IArea) this.getArea());
        int types = this.isHumidity ? 3 : 6;
        int w = (int) ((this.getSize().x() - 2.0F) / (float) types);

        for (int i = 0; i < types; ++i) {
            int x = i * w;
            if (this.tolerated.contains(Integer.valueOf(i))) {
                int colour = 0;
                if (this.isHumidity) {
                    colour = this.humidColours[i];
                } else {
                    colour = this.tempColours[i];
                }

                CraftGUI.Render.solid(new IArea((float) (x + 1), 1.0F, (float) w, this.getSize().y() - 2.0F), colour);
            }
        }

        CraftGUI.Render.texture((Object) CraftGUITexture.EnergyBarGlass, (IArea) this.getArea());
    }

    public void setSpecies(IAlleleBeeSpecies species) {
        this.tolerated.clear();
        if (species != null) {
            int main;
            EnumTolerance tolerance;
            if (!this.isHumidity) {
                main = species.getTemperature().ordinal() - 1;
                IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(Binnie.Genetics.getBeeRoot().getTemplate(species.getUID()));
                tolerance = genome.getToleranceTemp();
            } else {
                main = species.getHumidity().ordinal();
                IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(Binnie.Genetics.getBeeRoot().getTemplate(species.getUID()));
                tolerance = genome.getToleranceHumid();
            }

            this.tolerated.add(Integer.valueOf(main));
            switch (tolerance) {
                case BOTH_5:
                case UP_5:
                    this.tolerated.add(Integer.valueOf(main + 5));
                case BOTH_4:
                case UP_4:
                    this.tolerated.add(Integer.valueOf(main + 4));
                case BOTH_3:
                case UP_3:
                    this.tolerated.add(Integer.valueOf(main + 3));
                case BOTH_2:
                case UP_2:
                    this.tolerated.add(Integer.valueOf(main + 2));
                case BOTH_1:
                case UP_1:
                    this.tolerated.add(Integer.valueOf(main + 1));
                default:
                    switch (tolerance) {
                        case BOTH_5:
                        case DOWN_5:
                            this.tolerated.add(Integer.valueOf(main - 5));
                        case BOTH_4:
                        case DOWN_4:
                            this.tolerated.add(Integer.valueOf(main - 4));
                        case BOTH_3:
                        case DOWN_3:
                            this.tolerated.add(Integer.valueOf(main - 3));
                        case BOTH_2:
                        case DOWN_2:
                            this.tolerated.add(Integer.valueOf(main - 2));
                        case BOTH_1:
                        case DOWN_1:
                            this.tolerated.add(Integer.valueOf(main - 1));
                        case UP_5:
                        case UP_4:
                        case UP_3:
                        case UP_2:
                        case UP_1:
                        default:
                    }
            }
        }
    }
}
