package binnie.genetics.gui;

import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.controls.ControlTextEdit;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.controls.scroll.ControlScrollBar;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import binnie.craftgui.window.Panel;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;

import java.util.ArrayList;
import java.util.Collection;

public class AnalystPageDatabase extends ControlAnalystPage {
    ControlScrollableContent scroll = null;
    boolean isMaster;

    public AnalystPageDatabase(IWidget parent, IArea area, final BreedingSystem system, boolean isMaster) {
        super(parent, area);
        this.isMaster = isMaster;
        int cOfSystem = system.getColour();
        int cr = (16711680 & cOfSystem) >> 16;
        int cg = ('\uff00' & cOfSystem) >> 8;
        int cb = 255 & cOfSystem;
        float brightness = 0.1F * (float) cb / 255.0F + 0.35F * (float) cr / 255.0F + 0.55F * (float) cg / 255.0F;
        brightness = 0.3F / brightness;
        if (brightness > 1.0F) {
            brightness = 1.0F;
        }

        int newColour = (int) ((float) cr * brightness) * 65536 + (int) ((float) cg * brightness) * 256 + (int) ((float) cb * brightness);
        this.setColour(newColour);
        int y = 4;
        (new ControlTextCentered(this, (float) y, "§nRegistry")).setColour(this.getColour());
        y = y + 16;
        ControlTextEdit var10001 = new ControlTextEdit(this, 20.0F, (float) y, this.w() - 40.0F, 16.0F) {
            public void onTextEdit(String value) {
                Collection<IAlleleSpecies> options = new ArrayList();
                AnalystPageDatabase.this.getSpecies(system);

                for (IAlleleSpecies species : AnalystPageDatabase.this.getSpecies(system)) {
                    if (value == null || value == "" || species.getName().toLowerCase().contains(value.toLowerCase())) {
                        options.add(species);
                    }
                }

                AnalystPageDatabase.this.scroll.deleteAllChildren();
                AnalystPageDatabase.this.scroll.setScrollableContent(AnalystPageDatabase.this.getItemScrollList(system, options));
            }

            public void onRenderBackground() {
                CraftGUI.Render.colour(5592405);
                CraftGUI.Render.texture((Object) CraftGUITexture.TabSolid, (IArea) this.getArea().inset(1));
                CraftGUI.Render.colour(AnalystPageDatabase.this.getColour());
                CraftGUI.Render.texture((Object) CraftGUITexture.TabOutline, (IArea) this.getArea());
                super.renderTextField();
            }
        };
        y = y + 22;
        (new Panel(this, 3.0F, (float) (y - 1), this.w() - 6.0F, this.h() - (float) y - 8.0F + 2.0F, MinecraftGUI.PanelType.TabOutline)).setColour(this.getColour());
        boolean textView = false;
        final Collection<IAlleleSpecies> options = this.getSpecies(system);

        for (IAlleleSpecies species : options) {
            system.getAlleleName(EnumTreeChromosome.HEIGHT, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.HEIGHT));
            system.getAlleleName(EnumTreeChromosome.FERTILITY, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.FERTILITY));
            system.getAlleleName(EnumTreeChromosome.YIELD, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.YIELD));
            system.getAlleleName(EnumTreeChromosome.SAPPINESS, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.SAPPINESS));
            system.getAlleleName(EnumTreeChromosome.MATURATION, system.getIndividual(species.getUID()).getGenome().getActiveAllele(EnumTreeChromosome.MATURATION));
        }

        if (textView) {
            this.scroll = new ControlListBox(this, 4.0F, (float) y, this.w() - 8.0F, this.h() - (float) y - 8.0F - 20.0F, 0.0F) {
                public void initialise() {
                    super.initialise();
                    this.setOptions(options);
                }

                public IWidget createOption(final IAlleleSpecies v, int y) {
                    return new Control(this.getContent(), 0.0F, (float) y, this.w(), 12.0F) {
                        IAlleleSpecies value = v;

                        public void onRenderBackground() {
                            CraftGUI.Render.text(this.getArea(), TextJustification.MiddleCenter, this.value.getName(), 16777215);
                        }
                    };
                }
            };
        } else {
            this.scroll = new ControlScrollableContent(this, 4.0F, (float) y, this.w() - 8.0F, this.h() - (float) y - 8.0F, 0.0F);
            this.scroll.setScrollableContent(this.getItemScrollList(system, options));
        }

        ControlScrollBar var24 = new ControlScrollBar(this, this.scroll.x() + this.scroll.w() - 6.0F, this.scroll.y() + 3.0F, 3.0F, this.scroll.h() - 6.0F, this.scroll) {
            public void onRenderBackground() {
                if (this.isEnabled()) {
                    CraftGUI.Render.gradientRect(this.getArea(), 1140850688 + AnalystPageDatabase.this.getColour(), 1140850688 + AnalystPageDatabase.this.getColour());
                    CraftGUI.Render.solid(this.getRenderArea(), AnalystPageDatabase.this.getColour());
                }
            }
        };
    }

    private IWidget getItemScrollList(final BreedingSystem system, final Collection options) {
        return new Control(this.scroll, 0.0F, 0.0F, this.scroll.w(), this.scroll.h()) {
            public void initialise() {
                int maxBiomePerLine = (int) ((this.w() - 4.0F + 2.0F) / 18.0F);
                float biomeListX = -6.0F + (this.w() - (float) (maxBiomePerLine * 18 - 2)) / 2.0F;
                int dx = 0;
                int dy = 0;

                for (final IAlleleSpecies species : options) {
                    final IIndividual ind = system.getSpeciesRoot().templateAsIndividual(system.getSpeciesRoot().getTemplate(species.getUID()));
                    ControlIndividualDisplay var10001 = new ControlIndividualDisplay(this, biomeListX + (float) dx, (float) (2 + dy), ind) {
                        public void initialise() {
                            this.addSelfEventHandler(new EventMouse.Down.Handler() {
                                public void onEvent(EventMouse.Down event) {
                                    WindowAnalyst window = (WindowAnalyst) AnalystPageDatabase.this.getSuperParent();
                                    window.setIndividual(ind);
                                }
                            });
                        }

                        public void onRenderBackground() {
                            WindowAnalyst window = (WindowAnalyst) AnalystPageDatabase.this.getSuperParent();
                            if (window.getIndividual() != null && window.getIndividual().getGenome().getPrimary() == species) {
                                CraftGUI.Render.colour(15658734);
                                CraftGUI.Render.texture((Object) CraftGUITexture.TabSolid, (IArea) this.getArea().outset(1));
                                CraftGUI.Render.colour(AnalystPageDatabase.this.getColour());
                                CraftGUI.Render.texture((Object) CraftGUITexture.TabOutline, (IArea) this.getArea().outset(1));
                            } else if (this.calculateIsMouseOver()) {
                                CraftGUI.Render.colour(15658734);
                                CraftGUI.Render.texture((Object) CraftGUITexture.TabSolid, (IArea) this.getArea().outset(1));
                            }

                            super.onRenderBackground();
                        }
                    };
                    dx += 18;
                    if (dx >= 18 * maxBiomePerLine) {
                        dx = 0;
                        dy += 18;
                    }
                }

                this.setSize(new IPoint(this.w(), (float) (4 + dy + 18)));
            }
        };
    }

    public String getTitle() {
        return "Registry";
    }

    private Collection getSpecies(BreedingSystem system) {
        Collection<IAlleleSpecies> species = new ArrayList();
        species.addAll(this.isMaster ? system.getAllSpecies() : system.getDiscoveredSpecies(this.getWindow().getWorld(), this.getWindow().getPlayer().getGameProfile()));
        return species;
    }
}
