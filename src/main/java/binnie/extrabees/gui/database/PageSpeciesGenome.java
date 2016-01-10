package binnie.extrabees.gui.database;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.mod.database.DatabaseTab;
import binnie.craftgui.mod.database.PageSpecies;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesGenome extends PageSpecies {
    ControlText pageSpeciesGenome_Title = new ControlTextCentered(this, 8.0F, "Genome");
    ControlText pageSpeciesGenome_SpeedText;
    ControlText pageSpeciesGenome_LifespanText;
    ControlText pageSpeciesGenome_FertilityText;
    ControlText pageSpeciesGenome_FloweringText;
    ControlText pageSpeciesGenome_TerritoryText;
    ControlText pageSpeciesGenome_NocturnalText;
    ControlText pageSpeciesGenome_CaveDwellingText;
    ControlText pageSpeciesGenome_TolerantFlyerText;
    ControlText pageSpeciesGenome_FlowerText;
    ControlText pageSpeciesGenome_EffectText;

    public PageSpeciesGenome(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
        new ControlText(this, new IArea(0.0F, 32.0F, 68.0F, 30.0F), "Speed:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0F, 44.0F, 68.0F, 30.0F), "Lifespan:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0F, 56.0F, 68.0F, 30.0F), "Fertility:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0F, 68.0F, 68.0F, 30.0F), "Flowering:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0F, 80.0F, 68.0F, 30.0F), "Territory:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0F, 97.0F, 68.0F, 30.0F), "Behavior:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0F, 109.0F, 68.0F, 30.0F), "Sunlight:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0F, 121.0F, 68.0F, 30.0F), "Rain:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0F, 138.0F, 68.0F, 30.0F), "Flower:", TextJustification.TopRight);
        new ControlText(this, new IArea(0.0F, 155.0F, 68.0F, 30.0F), "Effect:", TextJustification.TopRight);
        int x = 72;
        this.pageSpeciesGenome_SpeedText = new ControlText(this, new IArea((float) x, 32.0F, 72.0F, 30.0F), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_LifespanText = new ControlText(this, new IArea((float) x, 44.0F, 72.0F, 30.0F), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_FertilityText = new ControlText(this, new IArea((float) x, 56.0F, 72.0F, 30.0F), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_FloweringText = new ControlText(this, new IArea((float) x, 68.0F, 72.0F, 30.0F), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_TerritoryText = new ControlText(this, new IArea((float) x, 80.0F, 72.0F, 30.0F), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_NocturnalText = new ControlText(this, new IArea((float) x, 97.0F, 72.0F, 30.0F), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_CaveDwellingText = new ControlText(this, new IArea((float) x, 109.0F, 72.0F, 30.0F), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_TolerantFlyerText = new ControlText(this, new IArea((float) x, 121.0F, 72.0F, 30.0F), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_FlowerText = new ControlText(this, new IArea((float) x, 138.0F, 72.0F, 30.0F), "", TextJustification.TopLeft);
        this.pageSpeciesGenome_EffectText = new ControlText(this, new IArea((float) x, 155.0F, 72.0F, 30.0F), "", TextJustification.TopLeft);
    }

    public void onValueChanged(IAlleleSpecies species) {
        IAllele[] template = Binnie.Genetics.getBeeRoot().getTemplate(species.getUID());
        if (template != null) {
            IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(template);
            IBee bee = Binnie.Genetics.getBeeRoot().getBee(BinnieCore.proxy.getWorld(), genome);
            this.pageSpeciesGenome_SpeedText.setValue(rateSpeed(genome.getSpeed()));
            this.pageSpeciesGenome_LifespanText.setValue(rateLifespan(genome.getLifespan()));
            this.pageSpeciesGenome_FertilityText.setValue(genome.getFertility() + " children");
            this.pageSpeciesGenome_FloweringText.setValue(rateFlowering(genome.getFlowering()));
            int[] area = genome.getTerritory();
            this.pageSpeciesGenome_TerritoryText.setValue(area[0] + "x" + area[1] + "x" + area[2]);
            String behavior = "Daytime";
            if (genome.getPrimary().isNocturnal()) {
                behavior = "Nighttime";
            }

            if (genome.getNocturnal()) {
                behavior = "All Day";
            }

            this.pageSpeciesGenome_NocturnalText.setValue(behavior);
            if (genome.getCaveDwelling()) {
                this.pageSpeciesGenome_CaveDwellingText.setValue("Not Needed");
            } else {
                this.pageSpeciesGenome_CaveDwellingText.setValue("Required");
            }

            this.pageSpeciesGenome_TolerantFlyerText.setValue(tolerated(genome.getTolerantFlyer()));
            if (genome.getFlowerProvider() != null) {
                this.pageSpeciesGenome_FlowerText.setValue(genome.getFlowerProvider().getDescription());
            } else {
                this.pageSpeciesGenome_FlowerText.setValue("None");
            }

            this.pageSpeciesGenome_EffectText.setValue(genome.getEffect().getName());
        }

    }

    public static String rateFlowering(int flowering) {
        return flowering >= 99 ? "Maximum" : (flowering >= 35 ? "Fastest" : (flowering >= 30 ? "Faster" : (flowering >= 25 ? "Fast" : (flowering >= 20 ? "Normal" : (flowering >= 15 ? "Slow" : (flowering >= 10 ? "Slower" : "Slowest"))))));
    }

    public static String rateSpeed(float speed) {
        return speed >= 1.7F ? "Fastest" : (speed >= 1.4F ? "Faster" : (speed >= 1.2F ? "Fast" : (speed >= 1.0F ? "Normal" : (speed >= 0.8F ? "Slow" : (speed >= 0.6F ? "Slower" : "Slowest")))));
    }

    public static String rateLifespan(int life) {
        return life >= 70 ? "Longest" : (life >= 60 ? "Longer" : (life >= 50 ? "Long" : (life >= 45 ? "Elongated" : (life >= 40 ? "Normal" : (life >= 35 ? "Shortened" : (life >= 30 ? "Short" : (life >= 20 ? "Shorter" : "Shortest")))))));
    }

    public static String tolerated(boolean t) {
        return t ? "Tolerated" : "Not Tolerated";
    }
}
