package binnie.craftgui.extratrees.dictionary;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.mod.database.*;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;

public class WindowLepidopteristDatabase extends WindowAbstractDatabase {
    public WindowLepidopteristDatabase(final EntityPlayer player, final Side side, final boolean nei) {
        super(player, side, nei, Binnie.Genetics.mothBreedingSystem, 160.0f);
    }

    public static Window create(final EntityPlayer player, final Side side, final boolean nei) {
        return new WindowLepidopteristDatabase(player, side, nei);
    }

    @Override
    protected void addTabs() {
        new PageSpeciesOverview(this.getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.overview", 0));
        new PageSpeciesClassification(this.getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.classification", 0));
        new PageSpeciesImage(this.getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.specimen", 0));
        new PageSpeciesResultant(this.getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.resultant", 0));
        new PageSpeciesMutations(this.getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.further", 0));
        new PageBranchOverview(this.getInfoPages(Mode.Branches), new DatabaseTab(ExtraTrees.instance, "butterfly.branches.overview", 0));
        new PageBranchSpecies(this.getInfoPages(Mode.Branches), new DatabaseTab(ExtraTrees.instance, "butterfly.branches.species", 0));
        new PageBreeder(this.getInfoPages(Mode.Breeder), this.getUsername(), new DatabaseTab(ExtraTrees.instance, "butterfly.breeder", 0));
    }

    @Override
    protected AbstractMod getMod() {
        return ExtraTrees.instance;
    }

    @Override
    protected String getName() {
        return "MothDatabase";
    }
}
