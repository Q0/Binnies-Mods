package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.FruitPod;
import binnie.extratrees.config.ConfigurationMain;
import binnie.extratrees.item.Food;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.arboriculture.*;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IFruitFamily;
import forestry.arboriculture.FruitProviderNone;
import forestry.arboriculture.genetics.alleles.AlleleTreeSpecies;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ExtraTreeFruitGene implements IAlleleFruit, IFruitProvider {
    Blackthorn(10, 7180062, 14561129, FruitSprite.Small),
    CherryPlum(10, 7180062, 15211595, FruitSprite.Small),
    Peach(10, 7180062, 16424995, FruitSprite.Average),
    Nectarine(10, 7180062, 16405795, FruitSprite.Average),
    Apricot(10, 7180062, 16437027, FruitSprite.Average),
    Almond(10, 7180062, 9364342, FruitSprite.Small),
    WildCherry(10, 7180062, 16711680, FruitSprite.Tiny),
    SourCherry(10, 7180062, 10225963, FruitSprite.Tiny),
    BlackCherry(10, 7180062, 4852249, FruitSprite.Tiny),
    Orange(10, 3665987, 16749578, FruitSprite.Average),
    Manderin(10, 3665987, 16749578, FruitSprite.Average),
    Tangerine(10, 3665987, 16749578, FruitSprite.Average),
    Satsuma(10, 3665987, 16749578, FruitSprite.Average),
    KeyLime(10, 3665987, 10223428, FruitSprite.Small),
    Lime(10, 3665987, 10223428, FruitSprite.Average),
    FingerLime(10, 3665987, 11156280, FruitSprite.Small),
    Pomelo(10, 3665987, 6083402, FruitSprite.Larger),
    Grapefruit(10, 3665987, 16749578, FruitSprite.Large),
    Kumquat(10, 3665987, 16749578, FruitSprite.Small),
    Citron(10, 3665987, 16772192, FruitSprite.Large),
    BuddhaHand(10, 3665987, 16772192, FruitSprite.Large),
    Apple(10, 7915859, 16193046, FruitSprite.Average),
    Crabapple(10, 7915859, 16760140, FruitSprite.Average),
    Banana("Banana", FruitPod.Banana),
    RedBanana("Red Banana", FruitPod.RedBanana),
    Plantain("Platain", FruitPod.Plantain),
    Hazelnut(7, 8223006, 14463606, FruitSprite.Small),
    Butternut(7, 11712336, 16102498, FruitSprite.Small),
    Beechnut(8, 14401148, 6241845, FruitSprite.Tiny),
    Pecan(8, 10660940, 15781769, FruitSprite.Small),
    BrazilNut(10, 5875561, 9852208, FruitSprite.Large),
    Fig(9, 14201186, 7094086, FruitSprite.Small),
    Acorn(6, 7516710, 11364893, FruitSprite.Tiny),
    Elderberry(9, 7444317, 5331779, FruitSprite.Tiny),
    Olive(9, 8887861, 6444842, FruitSprite.Small),
    GingkoNut(7, 9213787, 15063725, FruitSprite.Tiny),
    Coffee(8, 7433501, 16273254, FruitSprite.Tiny),
    Pear(10, 10456913, 10474833, FruitSprite.Pear),
    OsangeOsange(10, 9934674, 10665767, FruitSprite.Larger),
    Clove(9, 6847532, 11224133, FruitSprite.Tiny),
    Coconut("Coconut", FruitPod.Coconut),
    Cashew(8, 12879132, 15289111, FruitSprite.Average),
    Avacado(10, 10272370, 2170640, FruitSprite.Pear),
    Nutmeg(9, 14861101, 11305813, FruitSprite.Tiny),
    Allspice(9, 15180922, 7423542, FruitSprite.Tiny),
    Chilli(10, 7430757, 15145010, FruitSprite.Small),
    StarAnise(8, 8733742, 13917189, FruitSprite.Tiny),
    Mango(10, 6654997, 15902262, FruitSprite.Average),
    Starfruit(10, 9814541, 15061550, FruitSprite.Average),
    Candlenut(8, 8235123, 14600882, FruitSprite.Small),
    Papayimar("Papayimar", FruitPod.Papayimar),
    Blackcurrant(8, 9407571, 4935251, FruitSprite.Tiny),
    Redcurrant(8, 13008910, 15080974, FruitSprite.Tiny),
    Blackberry(8, 9399665, 4801393, FruitSprite.Tiny),
    Raspberry(8, 15520197, 14510449, FruitSprite.Tiny),
    Blueberry(8, 10203799, 6329278, FruitSprite.Tiny),
    Cranberry(8, 12232496, 14555696, FruitSprite.Tiny),
    Juniper(8, 10194034, 6316914, FruitSprite.Tiny),
    Gooseberry(8, 12164944, 12177232, FruitSprite.Tiny),
    GoldenRaspberry(8, 12496955, 15970363, FruitSprite.Tiny);

    IFruitFamily family;
    boolean isRipening;
    int diffR;
    int diffG;
    int diffB;
    FruitPod pod;
    int ripeningPeriod;
    int colourUnripe;
    int colour;
    FruitSprite index;
    HashMap<ItemStack, Float> products;

    public static void init() {
        final IFruitFamily familyPrune = AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes");
        final IFruitFamily familyPome = AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes");
        final IFruitFamily familyJungle = AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle");
        final IFruitFamily familyNuts = AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts");
        final IFruitFamily familyBerry = (IFruitFamily) ExtraTreeFruitFamily.Berry;
        final IFruitFamily familyCitrus = (IFruitFamily) ExtraTreeFruitFamily.Citrus;
        AlleleManager.alleleRegistry.registerFruitFamily(familyBerry);
        AlleleManager.alleleRegistry.registerFruitFamily(familyCitrus);
        ExtraTreeFruitGene.Apple.addProduct(new ItemStack(Items.apple), 1.0f);
        ExtraTreeFruitGene.Apple.setFamily(familyPome);
        ExtraTreeFruitGene.Crabapple.addProduct(Food.Crabapple.get(1), 1.0f);
        ExtraTreeFruitGene.Crabapple.setFamily(familyPome);
        ExtraTreeFruitGene.Orange.addProduct(Food.Orange.get(1), 1.0f);
        ExtraTreeFruitGene.Orange.setFamily(familyCitrus);
        ExtraTreeFruitGene.Manderin.addProduct(Food.Manderin.get(1), 1.0f);
        ExtraTreeFruitGene.Manderin.setFamily(familyCitrus);
        ExtraTreeFruitGene.Tangerine.addProduct(Food.Tangerine.get(1), 1.0f);
        ExtraTreeFruitGene.Tangerine.setFamily(familyCitrus);
        ExtraTreeFruitGene.Satsuma.addProduct(Food.Satsuma.get(1), 1.0f);
        ExtraTreeFruitGene.Satsuma.setFamily(familyCitrus);
        ExtraTreeFruitGene.KeyLime.addProduct(Food.KeyLime.get(1), 1.0f);
        ExtraTreeFruitGene.KeyLime.setFamily(familyCitrus);
        ExtraTreeFruitGene.Lime.addProduct(Food.Lime.get(1), 1.0f);
        ExtraTreeFruitGene.Lime.setFamily(familyCitrus);
        ExtraTreeFruitGene.FingerLime.addProduct(Food.FingerLime.get(1), 1.0f);
        ExtraTreeFruitGene.FingerLime.setFamily(familyCitrus);
        ExtraTreeFruitGene.Pomelo.addProduct(Food.Pomelo.get(1), 1.0f);
        ExtraTreeFruitGene.Pomelo.setFamily(familyCitrus);
        ExtraTreeFruitGene.Grapefruit.addProduct(Food.Grapefruit.get(1), 1.0f);
        ExtraTreeFruitGene.Grapefruit.setFamily(familyCitrus);
        ExtraTreeFruitGene.Kumquat.addProduct(Food.Kumquat.get(1), 1.0f);
        ExtraTreeFruitGene.Kumquat.setFamily(familyCitrus);
        ExtraTreeFruitGene.Citron.addProduct(Food.Citron.get(1), 1.0f);
        ExtraTreeFruitGene.Citron.setFamily(familyCitrus);
        ExtraTreeFruitGene.BuddhaHand.addProduct(Food.BuddhaHand.get(1), 1.0f);
        ExtraTreeFruitGene.BuddhaHand.setFamily(familyCitrus);
        ExtraTreeFruitGene.Blackthorn.addProduct(Food.Blackthorn.get(1), 1.0f);
        ExtraTreeFruitGene.Blackthorn.setFamily(familyPrune);
        ExtraTreeFruitGene.CherryPlum.addProduct(Food.CherryPlum.get(1), 1.0f);
        ExtraTreeFruitGene.CherryPlum.setFamily(familyPrune);
        ExtraTreeFruitGene.Peach.addProduct(Food.Peach.get(1), 1.0f);
        ExtraTreeFruitGene.Peach.setFamily(familyPrune);
        ExtraTreeFruitGene.Nectarine.addProduct(Food.Nectarine.get(1), 1.0f);
        ExtraTreeFruitGene.Nectarine.setFamily(familyPrune);
        ExtraTreeFruitGene.Apricot.addProduct(Food.Apricot.get(1), 1.0f);
        ExtraTreeFruitGene.Apricot.setFamily(familyPrune);
        ExtraTreeFruitGene.Almond.addProduct(Food.Almond.get(1), 1.0f);
        ExtraTreeFruitGene.Almond.setFamily(familyPrune);
        ExtraTreeFruitGene.WildCherry.addProduct(Food.WildCherry.get(1), 1.0f);
        ExtraTreeFruitGene.WildCherry.setFamily(familyPrune);
        ExtraTreeFruitGene.SourCherry.addProduct(Food.SourCherry.get(1), 1.0f);
        ExtraTreeFruitGene.SourCherry.setFamily(familyPrune);
        ExtraTreeFruitGene.BlackCherry.addProduct(Food.BlackCherry.get(1), 1.0f);
        ExtraTreeFruitGene.BlackCherry.setFamily(familyPrune);
        ExtraTreeFruitGene.Hazelnut.addProduct(Food.Hazelnut.get(1), 1.0f);
        ExtraTreeFruitGene.Hazelnut.setFamily(familyNuts);
        ExtraTreeFruitGene.Butternut.addProduct(Food.Butternut.get(1), 1.0f);
        ExtraTreeFruitGene.Butternut.setFamily(familyNuts);
        ExtraTreeFruitGene.Beechnut.addProduct(Food.Beechnut.get(1), 1.0f);
        ExtraTreeFruitGene.Beechnut.setFamily(familyNuts);
        ExtraTreeFruitGene.Pecan.addProduct(Food.Pecan.get(1), 1.0f);
        ExtraTreeFruitGene.Pecan.setFamily(familyNuts);
        ExtraTreeFruitGene.Banana.addProduct(Food.Banana.get(2), 1.0f);
        ExtraTreeFruitGene.Banana.setFamily(familyJungle);
        ExtraTreeFruitGene.RedBanana.addProduct(Food.RedBanana.get(2), 1.0f);
        ExtraTreeFruitGene.RedBanana.setFamily(familyJungle);
        ExtraTreeFruitGene.Plantain.addProduct(Food.Plantain.get(2), 1.0f);
        ExtraTreeFruitGene.Plantain.setFamily(familyJungle);
        ExtraTreeFruitGene.BrazilNut.addProduct(Food.BrazilNut.get(4), 1.0f);
        ExtraTreeFruitGene.BrazilNut.setFamily(familyNuts);
        ExtraTreeFruitGene.Fig.addProduct(Food.Fig.get(1), 1.0f);
        ExtraTreeFruitGene.Fig.setFamily(familyPrune);
        ExtraTreeFruitGene.Acorn.addProduct(Food.Acorn.get(1), 1.0f);
        ExtraTreeFruitGene.Acorn.setFamily(familyNuts);
        ExtraTreeFruitGene.Elderberry.addProduct(Food.Elderberry.get(1), 1.0f);
        ExtraTreeFruitGene.Elderberry.setFamily(familyPrune);
        ExtraTreeFruitGene.Olive.addProduct(Food.Olive.get(1), 1.0f);
        ExtraTreeFruitGene.Olive.setFamily(familyPrune);
        ExtraTreeFruitGene.GingkoNut.addProduct(Food.GingkoNut.get(1), 1.0f);
        ExtraTreeFruitGene.GingkoNut.setFamily(familyNuts);
        ExtraTreeFruitGene.Coffee.addProduct(Food.Coffee.get(1), 1.0f);
        ExtraTreeFruitGene.Coffee.setFamily(familyJungle);
        ExtraTreeFruitGene.Pear.addProduct(Food.Pear.get(1), 1.0f);
        ExtraTreeFruitGene.Pear.setFamily(familyPome);
        ExtraTreeFruitGene.OsangeOsange.addProduct(Food.OsangeOrange.get(1), 1.0f);
        ExtraTreeFruitGene.OsangeOsange.setFamily(familyPome);
        ExtraTreeFruitGene.Clove.addProduct(Food.Clove.get(1), 1.0f);
        ExtraTreeFruitGene.Clove.setFamily(familyNuts);
        ExtraTreeFruitGene.Blackcurrant.addProduct(Food.Blackcurrant.get(2), 1.0f);
        ExtraTreeFruitGene.Blackcurrant.setFamily(familyBerry);
        ExtraTreeFruitGene.Redcurrant.addProduct(Food.Redcurrant.get(2), 1.0f);
        ExtraTreeFruitGene.Redcurrant.setFamily(familyBerry);
        ExtraTreeFruitGene.Blackberry.addProduct(Food.Blackberry.get(2), 1.0f);
        ExtraTreeFruitGene.Blackberry.setFamily(familyBerry);
        ExtraTreeFruitGene.Raspberry.addProduct(Food.Raspberry.get(2), 1.0f);
        ExtraTreeFruitGene.Raspberry.setFamily(familyBerry);
        ExtraTreeFruitGene.Blueberry.addProduct(Food.Blueberry.get(2), 1.0f);
        ExtraTreeFruitGene.Blueberry.setFamily(familyBerry);
        ExtraTreeFruitGene.Cranberry.addProduct(Food.Cranberry.get(2), 1.0f);
        ExtraTreeFruitGene.Cranberry.setFamily(familyBerry);
        ExtraTreeFruitGene.Juniper.addProduct(Food.Juniper.get(2), 1.0f);
        ExtraTreeFruitGene.Juniper.setFamily(familyBerry);
        ExtraTreeFruitGene.Gooseberry.addProduct(Food.Gooseberry.get(2), 1.0f);
        ExtraTreeFruitGene.Gooseberry.setFamily(familyBerry);
        ExtraTreeFruitGene.GoldenRaspberry.addProduct(Food.GoldenRaspberry.get(2), 1.0f);
        ExtraTreeFruitGene.GoldenRaspberry.setFamily(familyBerry);
        ExtraTreeFruitGene.Coconut.addProduct(Food.Coconut.get(1), 1.0f);
        ExtraTreeFruitGene.Coconut.setFamily(familyJungle);
        ExtraTreeFruitGene.Cashew.addProduct(Food.Cashew.get(1), 1.0f);
        ExtraTreeFruitGene.Cashew.setFamily(familyJungle);
        ExtraTreeFruitGene.Avacado.addProduct(Food.Avacado.get(1), 1.0f);
        ExtraTreeFruitGene.Avacado.setFamily(familyJungle);
        ExtraTreeFruitGene.Nutmeg.addProduct(Food.Nutmeg.get(1), 1.0f);
        ExtraTreeFruitGene.Nutmeg.setFamily(familyJungle);
        ExtraTreeFruitGene.Allspice.addProduct(Food.Allspice.get(1), 1.0f);
        ExtraTreeFruitGene.Allspice.setFamily(familyJungle);
        ExtraTreeFruitGene.Chilli.addProduct(Food.Chilli.get(1), 1.0f);
        ExtraTreeFruitGene.Chilli.setFamily(familyJungle);
        ExtraTreeFruitGene.StarAnise.addProduct(Food.StarAnise.get(1), 1.0f);
        ExtraTreeFruitGene.StarAnise.setFamily(familyJungle);
        ExtraTreeFruitGene.Mango.addProduct(Food.Mango.get(1), 1.0f);
        ExtraTreeFruitGene.Mango.setFamily(familyPome);
        ExtraTreeFruitGene.Starfruit.addProduct(Food.Starfruit.get(1), 1.0f);
        ExtraTreeFruitGene.Starfruit.setFamily(familyJungle);
        ExtraTreeFruitGene.Candlenut.addProduct(Food.Candlenut.get(1), 1.0f);
        ExtraTreeFruitGene.Candlenut.setFamily(familyJungle);
        if (ConfigurationMain.alterLemon) {
            try {
                final IAlleleFruit lemon = (IAlleleFruit) AlleleManager.alleleRegistry.getAllele("forestry.fruitLemon");
                final FruitProviderNone prov = (FruitProviderNone) lemon.getProvider();
                final Field f = FruitProviderNone.class.getDeclaredField("family");
                final Field modifiersField = Field.class.getDeclaredField("modifiers");
                f.setAccessible(true);
                modifiersField.setAccessible(true);
                modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
                f.set(prov, familyCitrus);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (final IAlleleSpecies tree : Binnie.Genetics.treeBreedingSystem.getAllSpecies()) {
            if (tree instanceof AlleleTreeSpecies && ((IAlleleTreeSpecies) tree).getSuitableFruit().contains(familyPrune)) {
                ((AlleleTreeSpecies) tree).addFruitFamily(familyCitrus);
            }
        }
    }

    private void setFamily(final IFruitFamily family) {
        this.family = family;
    }

    private ExtraTreeFruitGene(final int time, final int unripe, final int colour, final FruitSprite index) {
        this.isRipening = false;
        this.diffB = 0;
        this.pod = null;
        this.ripeningPeriod = 0;
        this.products = new HashMap<ItemStack, Float>();
        this.colour = colour;
        this.index = index;
        this.setRipening(time, unripe);
    }

    private ExtraTreeFruitGene(final String name, final FruitPod pod) {
        this.isRipening = false;
        this.diffB = 0;
        this.pod = null;
        this.ripeningPeriod = 0;
        this.products = new HashMap<ItemStack, Float>();
        this.pod = pod;
        this.ripeningPeriod = 2;
    }

    public void setRipening(final int time, final int unripe) {
        this.ripeningPeriod = time;
        this.colourUnripe = unripe;
        this.isRipening = true;
        this.diffR = (this.colour >> 16 & 0xFF) - (unripe >> 16 & 0xFF);
        this.diffG = (this.colour >> 8 & 0xFF) - (unripe >> 8 & 0xFF);
        this.diffB = (this.colour & 0xFF) - (unripe & 0xFF);
    }

    public void addProduct(final ItemStack product, final float chance) {
        this.products.put(product, chance);
    }

    public String getUID() {
        return "extratrees.fruit." + this.toString().toLowerCase();
    }

    public boolean isDominant() {
        return true;
    }

    public IFruitProvider getProvider() {
        return (IFruitProvider) this;
    }

    public ItemStack[] getProducts() {
        return this.products.keySet().toArray(new ItemStack[0]);
    }

    public ItemStack[] getSpecialty() {
        return new ItemStack[0];
    }

    public String getDescription() {
        return ExtraTrees.proxy.localise("item.food." + this.name().toLowerCase());
    }

    public IFruitFamily getFamily() {
        return this.family;
    }

    public int getColour(final ITreeGenome genome, final IBlockAccess world, final int x, final int y, final int z, final int ripeningTime) {
        if (!this.isRipening) {
            return this.colour;
        }
        final float stage = this.getRipeningStage(ripeningTime);
        final int r = (this.colourUnripe >> 16 & 0xFF) + (int) (this.diffR * stage);
        final int g = (this.colourUnripe >> 8 & 0xFF) + (int) (this.diffG * stage);
        final int b = (this.colourUnripe & 0xFF) + (int) (this.diffB * stage);
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
    }

    public boolean markAsFruitLeaf(final ITreeGenome genome, final World world, final int x, final int y, final int z) {
        return this.pod == null;
    }

    public int getRipeningPeriod() {
        return this.ripeningPeriod;
    }

    public ItemStack[] getFruits(final ITreeGenome genome, final World world, final int x, final int y, final int z, final int ripeningTime) {
        if (this.pod != null) {
            if (ripeningTime >= 2) {
                final List<ItemStack> product = new ArrayList<ItemStack>();
                for (final Map.Entry<ItemStack, Float> entry : this.products.entrySet()) {
                    final ItemStack single = entry.getKey().copy();
                    single.stackSize = 1;
                    for (int i = 0; i < entry.getKey().stackSize; ++i) {
                        if (world.rand.nextFloat() <= entry.getValue()) {
                            product.add(single.copy());
                        }
                    }
                }
                return product.toArray(new ItemStack[0]);
            }
            return new ItemStack[0];
        } else {
            final ArrayList<ItemStack> product2 = new ArrayList<ItemStack>();
            final float stage = this.getRipeningStage(ripeningTime);
            if (stage < 0.5f) {
                return new ItemStack[0];
            }
            final float modeYieldMod = 1.0f;
            for (final Map.Entry<ItemStack, Float> entry2 : this.products.entrySet()) {
                if (world.rand.nextFloat() <= genome.getYield() * modeYieldMod * entry2.getValue() * 5.0f * stage) {
                    product2.add(entry2.getKey().copy());
                }
            }
            return product2.toArray(new ItemStack[0]);
        }
    }

    private float getRipeningStage(final int ripeningTime) {
        if (ripeningTime >= this.ripeningPeriod) {
            return 1.0f;
        }
        return ripeningTime / this.ripeningPeriod;
    }

    public boolean requiresFruitBlocks() {
        return this.pod != null;
    }

    public boolean trySpawnFruitBlock(final ITreeGenome genome, final World world, final int x, final int y, final int z) {
        return this.pod != null && world.rand.nextFloat() <= genome.getSappiness() && Binnie.Genetics.getTreeRoot().setFruitBlock(world, (IAlleleFruit) genome.getActiveAllele((IChromosomeType) EnumTreeChromosome.FRUITS), genome.getSappiness(), this.pod.getTextures(), x, y, z);
    }

    public boolean setFruitBlock(final World world, final IAlleleFruit allele, final float sappiness, final int x, final int y, final int z) {
        return true;
    }

    public static int getDirectionalMetadata(final World world, final int x, final int y, final int z) {
        for (int i = 0; i < 4; ++i) {
            if (isValidPot(world, x, y, z, i)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isValidPot(final World world, int x, final int y, int z, final int notchDirection) {
        x += Direction.offsetX[notchDirection];
        z += Direction.offsetZ[notchDirection];
        final Block block = world.getBlock(x, y, z);
        if (block == Blocks.log || block == Blocks.log2) {
            return BlockLog.func_150165_c(world.getBlockMetadata(x, y, z)) == 3;
        }
        return block != null && block.isWood((IBlockAccess) world, x, y, z);
    }

    public short getIconIndex(final ITreeGenome genome, final IBlockAccess world, final int x, final int y, final int z, final int ripeningTime, final boolean fancy) {
        return this.index.getIndex();
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        if (this.ordinal() == 0) {
            for (final FruitSprite sprite : FruitSprite.values()) {
                sprite.registerIcons(register);
            }
        }
    }

    public String getName() {
        return this.getDescription();
    }

    public String getNameOfFruit() {
        if (this == ExtraTreeFruitGene.Apple) {
            return "Apple";
        }
        for (final ItemStack stack : this.products.keySet()) {
            if (stack.getItem() == ExtraTrees.itemFood) {
                return Food.values()[stack.getItemDamage()].toString();
            }
        }
        return "NoFruit";
    }

    public String getUnlocalizedName() {
        return this.getUID();
    }
}
