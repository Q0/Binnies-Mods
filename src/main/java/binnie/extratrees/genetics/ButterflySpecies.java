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
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

    String name;
    String branchName;
    String scientific;
    BinnieResource texture;
    public IClassification branch;
    int colour;
    private Map butterflyLoot = new HashMap();
    private Map caterpillarLoot = new HashMap();

    private ButterflySpecies(String name, String scientific, int colour) {
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
        IAllele[] def = (IAllele[]) this.getRoot().getDefaultTemplate().clone();
        def[0] = this;
        return def;
    }

    public IButterflyRoot getRoot() {
        return Binnie.Genetics.getButterflyRoot();
    }

    public float getRarity() {
        return 0.5F;
    }

    public boolean isNocturnal() {
        return false;
    }

    public int getIconColour(int renderPass) {
        return renderPass > 0 ? 16777215 : this.colour;
    }

    public Map getButterflyLoot() {
        return new HashMap();
    }

    public Map getCaterpillarLoot() {
        return new HashMap();
    }

    public int getComplexity() {
        return 4;
    }

    public float getResearchSuitability(ItemStack itemstack) {
        if (itemstack == null) {
            return 0.0F;
        } else if (itemstack.getItem() == Items.glass_bottle) {
            return 0.9F;
        } else {
            for (ItemStack stack : this.butterflyLoot.keySet()) {
                if (stack.isItemEqual(itemstack)) {
                    return 1.0F;
                }
            }

            for (ItemStack stack : this.caterpillarLoot.keySet()) {
                if (stack.isItemEqual(itemstack)) {
                    return 1.0F;
                }
            }

            if (itemstack.getItem() == Mods.Forestry.item("honeyDrop")) {
                return 0.5F;
            } else if (itemstack.getItem() == Mods.Forestry.item("honeydew")) {
                return 0.7F;
            } else if (itemstack.getItem() == Mods.Forestry.item("beeComb")) {
                return 0.4F;
            } else if (AlleleManager.alleleRegistry.isIndividual(itemstack)) {
                return 1.0F;
            } else {
                for (Entry<ItemStack, Float> entry : this.getRoot().getResearchCatalysts().entrySet()) {
                    if (((ItemStack) entry.getKey()).isItemEqual(itemstack)) {
                        return ((Float) entry.getValue()).floatValue();
                    }
                }

                return 0.0F;
            }
        }
    }

    public ItemStack[] getResearchBounty(World world, GameProfile researcher, IIndividual individual, int bountyLevel) {
        return new ItemStack[]{this.getRoot().getMemberStack(individual.copy(), EnumFlutterType.SERUM.ordinal())};
    }

    public EnumSet getSpawnBiomes() {
        return EnumSet.noneOf(Type.class);
    }

    public boolean strictSpawnMatch() {
        return false;
    }

    public float getFlightDistance() {
        return 5.0F;
    }

    public String getUnlocalizedName() {
        return this.getUID();
    }
}
