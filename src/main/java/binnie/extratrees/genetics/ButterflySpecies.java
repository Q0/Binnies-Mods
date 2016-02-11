package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IIconProvider;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.EnumFlutterType;
import forestry.api.lepidopterology.IAlleleButterflySpecies;
import forestry.api.lepidopterology.IButterflyRoot;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ButterflySpecies implements IAlleleButterflySpecies {
    WhiteAdmiral("White Admiral", "Limenitis camilla", 16448250),
    PurpleEmperor("Purple Emperor", "Apatura iris", 4338374),
    RedAdmiral("Red Admiral", "Vanessa atalanta", 15101764),
    PaintedLady("Painted Lady", "Vanessa cardui", 15573064),
    SmallTortoiseshell("Small Tortoiseshell", "Aglais urticae", 15365387),
    CamberwellBeauty("Camberwell Beauty", "Aglais antiopa", 9806540),
    Peacock("Peacock", "Inachis io", 13842434),
    Wall("Wall", "Lasiommata megera", 15707678),
    CrimsonRose("Crimson Rose", "Atrophaneura hector", 16736891),
    KaiserIHind("Kaiser-i-Hind", "Teinopalpus imperialis", 7839808),
    GoldenBirdwing("Golden Birdwing", "Troides aeacus", 16374814),
    MarshFritillary("Marsh Fritillary", "Euphydryas aurinia", 16747520),
    PearlBorderedFritillary("Pearl-bordered Fritillary", "Boloria euphrosyne", 16747267),
    QueenOfSpainFritillary("Queen of Spain Fritillary", "Issoria lathonia", 16765247),
    SpeckledWood("Speckled Wood", "Pararge aegeria", 16119949),
    ScotchAngus("Scotch Angus", "Erebia aethiops", 12735523),
    Gatekeeper("Gatekeeper", "Pyronia tithonus", 16433962),
    MeadowBrown("Meadow Brown", "Maniola jurtina", 14914841),
    SmallHeath("Small Heath", "Coenonympha pamphilus", 16754226),
    Ringlet("Ringlet", "Aphantopus hyperantus", 9919799),
    Monarch("Monarch", "Danaus plexippus", 16757254),
    MarbledWhite("Marbled White", "Melanargia galathea", 15527148);

    public IClassification branch;
    String name;
    String branchName;
    String scientific;
    BinnieResource texture;
    int colour;
    private Map<ItemStack, Float> butterflyLoot;
    private Map<ItemStack, Float> caterpillarLoot;

    ButterflySpecies(final String name, final String scientific, final int colour) {
        this.butterflyLoot = new HashMap<ItemStack, Float>();
        this.caterpillarLoot = new HashMap<ItemStack, Float>();
        this.name = name;
        this.branchName = scientific.split(" ")[0].toLowerCase();
        this.scientific = scientific.split(" ")[1];
        this.texture = Binnie.Resource.getPNG(ExtraTrees.instance, ResourceType.Entity, this.toString());
        this.colour = colour;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return "";
    }

    public EnumTemperature getTemperature() {
        return EnumTemperature.NORMAL;
    }

    public EnumHumidity getHumidity() {
        return EnumHumidity.NORMAL;
    }

    public boolean hasEffect() {
        return false;
    }

    public boolean isSecret() {
        return false;
    }

    public boolean isCounted() {
        return true;
    }

    public String getBinomial() {
        return this.scientific;
    }

    public String getAuthority() {
        return "Binnie";
    }

    public IClassification getBranch() {
        return this.branch;
    }

    @SideOnly(Side.CLIENT)
    public IIconProvider getIconProvider() {
        return null;
    }

    public String getUID() {
        return "extrabutterflies.species." + this.toString().toLowerCase();
    }

    public boolean isDominant() {
        return true;
    }

    public String getEntityTexture() {
        return this.texture.getResourceLocation().toString();
    }

    public IAllele[] getTemplate() {
        final IAllele[] def = this.getRoot().getDefaultTemplate().clone();
        def[0] = this;
        return def;
    }

    public IButterflyRoot getRoot() {
        return Binnie.Genetics.getButterflyRoot();
    }

    public float getRarity() {
        return 0.5f;
    }

    public boolean isNocturnal() {
        return false;
    }

    public int getIconColour(final int renderPass) {
        return (renderPass > 0) ? 16777215 : this.colour;
    }

    public Map<ItemStack, Float> getButterflyLoot() {
        return new HashMap<ItemStack, Float>();
    }

    public Map<ItemStack, Float> getCaterpillarLoot() {
        return new HashMap<ItemStack, Float>();
    }

    public int getComplexity() {
        return 4;
    }

    public float getResearchSuitability(final ItemStack itemstack) {
        if (itemstack == null) {
            return 0.0f;
        }
        if (itemstack.getItem() == Items.glass_bottle) {
            return 0.9f;
        }
        for (final ItemStack stack : this.butterflyLoot.keySet()) {
            if (stack.isItemEqual(itemstack)) {
                return 1.0f;
            }
        }
        for (final ItemStack stack : this.caterpillarLoot.keySet()) {
            if (stack.isItemEqual(itemstack)) {
                return 1.0f;
            }
        }
        if (itemstack.getItem() == Mods.Forestry.item("honeyDrop")) {
            return 0.5f;
        }
        if (itemstack.getItem() == Mods.Forestry.item("honeydew")) {
            return 0.7f;
        }
        if (itemstack.getItem() == Mods.Forestry.item("beeComb")) {
            return 0.4f;
        }
        if (AlleleManager.alleleRegistry.isIndividual(itemstack)) {
            return 1.0f;
        }
        for (final Map.Entry<ItemStack, Float> entry : this.getRoot().getResearchCatalysts().entrySet()) {
            if (entry.getKey().isItemEqual(itemstack)) {
                return entry.getValue();
            }
        }
        return 0.0f;
    }

    public ItemStack[] getResearchBounty(final World world, final GameProfile researcher, final IIndividual individual, final int bountyLevel) {
        return new ItemStack[]{this.getRoot().getMemberStack(individual.copy(), EnumFlutterType.SERUM.ordinal())};
    }

    public EnumSet<BiomeDictionary.Type> getSpawnBiomes() {
        return EnumSet.noneOf(BiomeDictionary.Type.class);
    }

    public boolean strictSpawnMatch() {
        return false;
    }

    public float getFlightDistance() {
        return 5.0f;
    }

    public String getUnlocalizedName() {
        return this.getUID();
    }
}
