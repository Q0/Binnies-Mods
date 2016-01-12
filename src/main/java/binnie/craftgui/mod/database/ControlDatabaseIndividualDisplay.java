package binnie.craftgui.mod.database;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import com.mojang.authlib.GameProfile;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.util.IIcon;

public class ControlDatabaseIndividualDisplay extends ControlItemDisplay implements ITooltip {
    private IAlleleSpecies species;
    EnumDiscoveryState discovered;

    public void setSpecies(final IAlleleSpecies species) {
        this.setSpecies(species, EnumDiscoveryState.Show);
    }

    public void setSpecies(final IAlleleSpecies species, EnumDiscoveryState state) {
        final ISpeciesRoot speciesRoot = Binnie.Genetics.getSpeciesRoot(species);
        final BreedingSystem system = Binnie.Genetics.getSystem(speciesRoot.getUID());
        final IIndividual ind = system.getSpeciesRoot().templateAsIndividual(system.getSpeciesRoot().getTemplate(species.getUID()));
        super.setItemStack(system.getSpeciesRoot().getMemberStack(ind, system.getDefaultType()));
        this.species = species;
        final GameProfile username = Window.get(this).getUsername();
        if (state == EnumDiscoveryState.Undetermined) {
            state = (system.isSpeciesDiscovered(species, Window.get(this).getWorld(), username) ? EnumDiscoveryState.Discovered : EnumDiscoveryState.Undiscovered);
        }
        if (Window.get(this) instanceof WindowAbstractDatabase && Window.get(this).isNEI) {
            state = EnumDiscoveryState.Show;
        }
        this.discovered = state;
        this.addAttribute(Attribute.MouseOver);
    }

    public ControlDatabaseIndividualDisplay(final IWidget parent, final float x, final float y) {
        this(parent, x, y, 16.0f);
    }

    public ControlDatabaseIndividualDisplay(final IWidget parent, final float x, final float y, final float size) {
        super(parent, x, y, size);
        this.species = null;
        this.discovered = EnumDiscoveryState.Show;
        this.addSelfEventHandler(new EventMouse.Down.Handler() {
            @Override
            public void onEvent(final EventMouse.Down event) {
                if (event.getButton() == 0 && ControlDatabaseIndividualDisplay.this.species != null && EnumDiscoveryState.Show == ControlDatabaseIndividualDisplay.this.discovered) {
                    ((WindowAbstractDatabase) ControlDatabaseIndividualDisplay.this.getSuperParent()).gotoSpeciesDelayed(ControlDatabaseIndividualDisplay.this.species);
                }
            }
        });
    }

    @Override
    public void onRenderForeground() {
        IIcon icon = null;
        if (this.species == null) {
            return;
        }
        final BreedingSystem system = Binnie.Genetics.getSystem(this.species.getRoot());
        switch (this.discovered) {
            case Show: {
                super.onRenderForeground();
                return;
            }
            case Discovered: {
                icon = system.getDiscoveredIcon();
                break;
            }
            case Undiscovered: {
                icon = system.getUndiscoveredIcon();
                break;
            }
        }
        if (icon != null) {
            CraftGUI.Render.iconItem(IPoint.ZERO, icon);
        }
    }

    @Override
    public void getTooltip(final Tooltip tooltip) {
        if (this.species != null) {
            switch (this.discovered) {
                case Show: {
                    tooltip.add(this.species.getName());
                    break;
                }
                case Discovered: {
                    tooltip.add("Discovered Species");
                    break;
                }
                case Undiscovered: {
                    tooltip.add("Undiscovered Species");
                    break;
                }
            }
        }
    }
}
