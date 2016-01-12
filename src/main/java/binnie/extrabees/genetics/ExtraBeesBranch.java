package binnie.extrabees.genetics;

import binnie.extrabees.ExtraBees;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;

import java.util.LinkedHashSet;
import java.util.Set;

public enum ExtraBeesBranch implements IClassification {
    BARREN("Vacapis"),
    HOSTILE("Infenapis"),
    ROCKY("Monapis"),
    METALLIC("Lamminapis"),
    METALLIC2("Metalapis"),
    ALLOY("Allapis"),
    PRECIOUS("Pluriapis"),
    MINERAL("Niphapis"),
    GEMSTONE("Gemmapis"),
    NUCLEAR("Levapis"),
    HISTORIC("Priscapis"),
    FOSSILIZED("Fosiapis"),
    REFINED("Petrapis"),
    AQUATIC("Aquapis"),
    SACCHARINE("Sacchapis"),
    CLASSICAL("Grecapis"),
    VOLCANIC("Irrapis"),
    VIRULENT("Virapis"),
    VISCOUS("Viscapis"),
    CAUSTIC("Morbapis"),
    ENERGETIC("Incitapis"),
    FARMING("Agriapis"),
    SHADOW("Pullapis"),
    PRIMARY("Primapis"),
    SECONDARY("Secapis"),
    TERTIARY("Tertiapis"),
    FTB("Eftebeapis"),
    QUANTUM("Quantapis"),
    BOTANIA("Botaniapis");

    private String uid;
    private String scientific;
    private Set<IAlleleBeeSpecies> speciesSet;
    IClassification parent;

    public String getUID() {
        return "extrabees.genus." + this.uid;
    }

    public String getName() {
        return ExtraBees.proxy.localise("branch." + this.toString().toLowerCase() + ".name");
    }

    public String getScientific() {
        return this.scientific;
    }

    public String getDescription() {
        return ExtraBees.proxy.localiseOrBlank("branch." + this.toString().toLowerCase() + ".desc");
    }

    private ExtraBeesBranch(final String scientific) {
        this.uid = "";
        this.scientific = "";
        this.speciesSet = new LinkedHashSet<IAlleleBeeSpecies>();
        this.scientific = scientific;
        this.uid = this.toString().toLowerCase();
    }

    public void register() {
        if (!this.speciesSet.isEmpty()) {
            AlleleManager.alleleRegistry.registerClassification((IClassification) this);
            final IClassification parent = AlleleManager.alleleRegistry.getClassification("family.apidae");
            if (parent != null) {
                parent.addMemberGroup((IClassification) this);
                this.setParent(parent);
            }
        }
    }

    public static void doInit() {
        final IClassification frozenBranch = AlleleManager.alleleRegistry.getClassification("genus.bees.frozen");
        if (frozenBranch != null) {
            frozenBranch.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ARTIC);
            ExtraBeesSpecies.ARTIC.setBranch(frozenBranch);
            frozenBranch.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.FREEZING);
            ExtraBeesSpecies.FREEZING.setBranch(frozenBranch);
        }
        final IClassification agrarianBranch = AlleleManager.alleleRegistry.getClassification("genus.bees.agrarian");
        if (agrarianBranch != null) {
            agrarianBranch.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.FARM);
            ExtraBeesSpecies.FARM.setBranch(agrarianBranch);
            ExtraBeesSpecies.GROWING.setBranch(agrarianBranch);
            ExtraBeesSpecies.THRIVING.setBranch(agrarianBranch);
            ExtraBeesSpecies.BLOOMING.setBranch(agrarianBranch);
        }
        final IClassification boggyBranch = AlleleManager.alleleRegistry.getClassification("genus.bees.boggy");
        if (boggyBranch != null) {
            boggyBranch.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.SWAMP);
            boggyBranch.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.BOGGY);
            boggyBranch.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.FUNGAL);
            ExtraBeesSpecies.SWAMP.setBranch(boggyBranch);
            ExtraBeesSpecies.BOGGY.setBranch(boggyBranch);
            ExtraBeesSpecies.FUNGAL.setBranch(boggyBranch);
        }
        final IClassification festiveBranch = AlleleManager.alleleRegistry.getClassification("genus.bees.festive");
        if (festiveBranch != null) {
            festiveBranch.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.CELEBRATORY);
            ExtraBeesSpecies.CELEBRATORY.setBranch(festiveBranch);
        }
        final IClassification austereBranch = AlleleManager.alleleRegistry.getClassification("genus.bees.austere");
        if (austereBranch != null) {
            austereBranch.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.HAZARDOUS);
            ExtraBeesSpecies.HAZARDOUS.setBranch(austereBranch);
        }
        ExtraBeesBranch.FARMING.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ALCOHOL);
        ExtraBeesBranch.FARMING.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.MILK);
        ExtraBeesBranch.FARMING.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.COFFEE);
        ExtraBeesBranch.FARMING.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.CITRUS);
        ExtraBeesBranch.FARMING.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.MINT);
        ExtraBeesBranch.FARMING.register();
        ExtraBeesBranch.BARREN.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ARID);
        ExtraBeesBranch.BARREN.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.BARREN);
        ExtraBeesBranch.BARREN.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.DESOLATE);
        ExtraBeesBranch.BARREN.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.DECOMPOSING);
        ExtraBeesBranch.BARREN.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.GNAWING);
        ExtraBeesBranch.BARREN.register();
        ExtraBeesBranch.HOSTILE.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ROTTEN);
        ExtraBeesBranch.HOSTILE.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.BONE);
        ExtraBeesBranch.HOSTILE.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.CREEPER);
        ExtraBeesBranch.HOSTILE.register();
        ExtraBeesBranch.ROCKY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ROCK);
        ExtraBeesBranch.ROCKY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.STONE);
        ExtraBeesBranch.ROCKY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.GRANITE);
        ExtraBeesBranch.ROCKY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.MINERAL);
        ExtraBeesBranch.ROCKY.register();
        ExtraBeesBranch.METALLIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.IRON);
        ExtraBeesBranch.METALLIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.COPPER);
        ExtraBeesBranch.METALLIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.TIN);
        ExtraBeesBranch.METALLIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.LEAD);
        ExtraBeesBranch.METALLIC.register();
        ExtraBeesBranch.METALLIC2.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.NICKEL);
        ExtraBeesBranch.METALLIC2.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ZINC);
        ExtraBeesBranch.METALLIC2.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.TUNGSTATE);
        ExtraBeesBranch.METALLIC2.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.TITANIUM);
        ExtraBeesBranch.METALLIC2.register();
        ExtraBeesBranch.ALLOY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.BRONZE);
        ExtraBeesBranch.ALLOY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.BRASS);
        ExtraBeesBranch.ALLOY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.STEEL);
        ExtraBeesBranch.ALLOY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.INVAR);
        ExtraBeesBranch.ALLOY.register();
        ExtraBeesBranch.PRECIOUS.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.SILVER);
        ExtraBeesBranch.PRECIOUS.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.GOLD);
        ExtraBeesBranch.PRECIOUS.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ELECTRUM);
        ExtraBeesBranch.PRECIOUS.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.PLATINUM);
        ExtraBeesBranch.PRECIOUS.register();
        ExtraBeesBranch.MINERAL.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.LAPIS);
        ExtraBeesBranch.MINERAL.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.SODALITE);
        ExtraBeesBranch.MINERAL.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.PYRITE);
        ExtraBeesBranch.MINERAL.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.BAUXITE);
        ExtraBeesBranch.MINERAL.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.CINNABAR);
        ExtraBeesBranch.MINERAL.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.SPHALERITE);
        ExtraBeesBranch.MINERAL.register();
        ExtraBeesBranch.GEMSTONE.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.EMERALD);
        ExtraBeesBranch.GEMSTONE.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.RUBY);
        ExtraBeesBranch.GEMSTONE.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.SAPPHIRE);
        ExtraBeesBranch.GEMSTONE.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.OLIVINE);
        ExtraBeesBranch.GEMSTONE.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.DIAMOND);
        ExtraBeesBranch.GEMSTONE.register();
        ExtraBeesBranch.NUCLEAR.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.UNSTABLE);
        ExtraBeesBranch.NUCLEAR.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.NUCLEAR);
        ExtraBeesBranch.NUCLEAR.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.RADIOACTIVE);
        ExtraBeesBranch.NUCLEAR.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.YELLORIUM);
        ExtraBeesBranch.NUCLEAR.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.CYANITE);
        ExtraBeesBranch.NUCLEAR.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.BLUTONIUM);
        ExtraBeesBranch.NUCLEAR.register();
        ExtraBeesBranch.HISTORIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ANCIENT);
        ExtraBeesBranch.HISTORIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.PRIMEVAL);
        ExtraBeesBranch.HISTORIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.PREHISTORIC);
        ExtraBeesBranch.HISTORIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.RELIC);
        ExtraBeesBranch.HISTORIC.register();
        ExtraBeesBranch.FOSSILIZED.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.COAL);
        ExtraBeesBranch.FOSSILIZED.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.RESIN);
        ExtraBeesBranch.FOSSILIZED.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.OIL);
        ExtraBeesBranch.FOSSILIZED.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.PEAT);
        ExtraBeesBranch.FOSSILIZED.register();
        ExtraBeesBranch.REFINED.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.DISTILLED);
        ExtraBeesBranch.REFINED.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.FUEL);
        ExtraBeesBranch.REFINED.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.CREOSOTE);
        ExtraBeesBranch.REFINED.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.LATEX);
        ExtraBeesBranch.REFINED.register();
        ExtraBeesBranch.AQUATIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.WATER);
        ExtraBeesBranch.AQUATIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.RIVER);
        ExtraBeesBranch.AQUATIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.OCEAN);
        ExtraBeesBranch.AQUATIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.INK);
        ExtraBeesBranch.AQUATIC.register();
        ExtraBeesBranch.SACCHARINE.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.SWEET);
        ExtraBeesBranch.SACCHARINE.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.SUGAR);
        ExtraBeesBranch.SACCHARINE.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.FRUIT);
        ExtraBeesBranch.SACCHARINE.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.RIPENING);
        ExtraBeesBranch.SACCHARINE.register();
        ExtraBeesBranch.CLASSICAL.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.MARBLE);
        ExtraBeesBranch.CLASSICAL.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ROMAN);
        ExtraBeesBranch.CLASSICAL.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.GREEK);
        ExtraBeesBranch.CLASSICAL.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.CLASSICAL);
        ExtraBeesBranch.CLASSICAL.register();
        ExtraBeesBranch.VOLCANIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.BASALT);
        ExtraBeesBranch.VOLCANIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.TEMPERED);
        ExtraBeesBranch.VOLCANIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ANGRY);
        ExtraBeesBranch.VOLCANIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.VOLCANIC);
        ExtraBeesBranch.VOLCANIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.GLOWSTONE);
        ExtraBeesBranch.VOLCANIC.register();
        ExtraBeesBranch.VISCOUS.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.VISCOUS);
        ExtraBeesBranch.VISCOUS.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.GLUTINOUS);
        ExtraBeesBranch.VISCOUS.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.STICKY);
        ExtraBeesBranch.VISCOUS.register();
        ExtraBeesBranch.VIRULENT.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.MALICIOUS);
        ExtraBeesBranch.VIRULENT.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.INFECTIOUS);
        ExtraBeesBranch.VIRULENT.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.VIRULENT);
        ExtraBeesBranch.VIRULENT.register();
        ExtraBeesBranch.CAUSTIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.CORROSIVE);
        ExtraBeesBranch.CAUSTIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.CAUSTIC);
        ExtraBeesBranch.CAUSTIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ACIDIC);
        ExtraBeesBranch.CAUSTIC.register();
        ExtraBeesBranch.ENERGETIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.EXCITED);
        ExtraBeesBranch.ENERGETIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ENERGETIC);
        ExtraBeesBranch.ENERGETIC.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ECSTATIC);
        ExtraBeesBranch.ENERGETIC.register();
        ExtraBeesBranch.SHADOW.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.SHADOW);
        ExtraBeesBranch.SHADOW.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.DARKENED);
        ExtraBeesBranch.SHADOW.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ABYSS);
        ExtraBeesBranch.SHADOW.register();
        ExtraBeesBranch.PRIMARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.RED);
        ExtraBeesBranch.PRIMARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.YELLOW);
        ExtraBeesBranch.PRIMARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.BLUE);
        ExtraBeesBranch.PRIMARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.GREEN);
        ExtraBeesBranch.PRIMARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.BLACK);
        ExtraBeesBranch.PRIMARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.WHITE);
        ExtraBeesBranch.PRIMARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.BROWN);
        ExtraBeesBranch.PRIMARY.register();
        ExtraBeesBranch.SECONDARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.ORANGE);
        ExtraBeesBranch.SECONDARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.CYAN);
        ExtraBeesBranch.SECONDARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.PURPLE);
        ExtraBeesBranch.SECONDARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.GRAY);
        ExtraBeesBranch.SECONDARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.LIGHTBLUE);
        ExtraBeesBranch.SECONDARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.PINK);
        ExtraBeesBranch.SECONDARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.LIMEGREEN);
        ExtraBeesBranch.SECONDARY.register();
        ExtraBeesBranch.TERTIARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.MAGENTA);
        ExtraBeesBranch.TERTIARY.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.LIGHTGRAY);
        ExtraBeesBranch.TERTIARY.register();
        ExtraBeesBranch.FTB.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.JADED);
        ExtraBeesBranch.FTB.register();
        ExtraBeesBranch.QUANTUM.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.UNUSUAL);
        ExtraBeesBranch.QUANTUM.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.SPATIAL);
        ExtraBeesBranch.QUANTUM.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.QUANTUM);
        ExtraBeesBranch.QUANTUM.register();
        ExtraBeesBranch.BOTANIA.addMemberSpecies((IAlleleSpecies) ExtraBeesSpecies.MYSTICAL);
        ExtraBeesBranch.BOTANIA.register();
    }

    public IClassification.EnumClassLevel getLevel() {
        return IClassification.EnumClassLevel.GENUS;
    }

    public IClassification[] getMemberGroups() {
        return null;
    }

    public void addMemberGroup(final IClassification group) {
    }

    public IAlleleSpecies[] getMemberSpecies() {
        return this.speciesSet.toArray(new IAlleleSpecies[0]);
    }

    public void addMemberSpecies(final IAlleleSpecies species) {
        this.speciesSet.add((IAlleleBeeSpecies) species);
        if (species instanceof ExtraBeesSpecies) {
            ((ExtraBeesSpecies) species).setBranch((IClassification) this);
        }
    }

    public IClassification getParent() {
        return this.parent;
    }

    public void setParent(final IClassification parent) {
        this.parent = parent;
    }
}
