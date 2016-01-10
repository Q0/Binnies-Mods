package binnie.craftgui.mod.database;

import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlOption;
import binnie.craftgui.minecraft.Window;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;

class ControlMutationItem extends ControlOption {
    private ControlDatabaseIndividualDisplay itemWidget1 = new ControlDatabaseIndividualDisplay(this, 4.0F, 4.0F);
    private ControlDatabaseIndividualDisplay itemWidget2 = new ControlDatabaseIndividualDisplay(this, 44.0F, 4.0F);
    private ControlDatabaseIndividualDisplay itemWidget3 = new ControlDatabaseIndividualDisplay(this, 104.0F, 4.0F);
    private ControlMutationSymbol addSymbol = new ControlMutationSymbol(this, 24, 4, 0);
    private ControlMutationSymbol arrowSymbol = new ControlMutationSymbol(this, 64, 4, 1);

    public ControlMutationItem(ControlList controlList, IMutation option, IAlleleSpecies species, int y) {
        super(controlList, option, y);
        boolean isNEI = ((WindowAbstractDatabase) this.getSuperParent()).isNEI();
        BreedingSystem system = ((WindowAbstractDatabase) this.getSuperParent()).getBreedingSystem();
        if (this.getValue() != null) {
            boolean isMutationDiscovered = system.isMutationDiscovered((IMutation) this.getValue(), Window.get(this).getWorld(), Window.get(this).getUsername());
            IAlleleSpecies allele = null;
            EnumDiscoveryState state = null;
            allele = (IAlleleSpecies) ((IMutation) this.getValue()).getAllele0();
            state = !isNEI && !isMutationDiscovered ? (species == allele ? EnumDiscoveryState.Show : EnumDiscoveryState.Undetermined) : EnumDiscoveryState.Show;
            this.itemWidget1.setSpecies(allele, state);
            allele = (IAlleleSpecies) ((IMutation) this.getValue()).getAllele1();
            state = !isNEI && !isMutationDiscovered ? (species == allele ? EnumDiscoveryState.Show : EnumDiscoveryState.Undetermined) : EnumDiscoveryState.Show;
            this.itemWidget2.setSpecies(allele, state);
            allele = (IAlleleSpecies) ((IMutation) this.getValue()).getTemplate()[0];
            state = !isNEI && !isMutationDiscovered ? (species == allele ? EnumDiscoveryState.Show : EnumDiscoveryState.Undetermined) : EnumDiscoveryState.Show;
            this.itemWidget3.setSpecies(allele, state);
            this.addSymbol.setValue((IMutation) this.getValue());
            this.arrowSymbol.setValue((IMutation) this.getValue());
        }

    }
}
