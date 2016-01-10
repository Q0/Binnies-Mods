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
import forestry.api.genetics.IFruitFamily;
import forestry.arboriculture.FruitProviderNone;
import forestry.arboriculture.FruitProviderRipening;
import forestry.arboriculture.genetics.AlleleTreeSpecies;
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
import java.util.Map.Entry;

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
    boolean isRipening = false;
    int diffR;
    int diffG;
    int diffB = 0;
    FruitPod pod = null;
    int ripeningPeriod = 0;
    int colourUnripe;
    int colour;
    FruitSprite index;
    HashMap products = new HashMap();

    public static void init() {
        IFruitFamily familyPrune = AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes");
        IFruitFamily familyPome = AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes");
        IFruitFamily familyJungle = AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle");
        IFruitFamily familyNuts = AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts");
        IFruitFamily familyBerry = ExtraTreeFruitFamily.Berry;
        IFruitFamily familyCitrus = ExtraTreeFruitFamily.Citrus;
        AlleleManager.alleleRegistry.registerFruitFamily(familyBerry);
        AlleleManager.alleleRegistry.registerFruitFamily(familyCitrus);
        Apple.addProduct(new ItemStack(Items.apple), 1.0F);
        Apple.setFamily(familyPome);
        Crabapple.addProduct(Food.Crabapple.get(1), 1.0F);
        Crabapple.setFamily(familyPome);
        Orange.addProduct(Food.Orange.get(1), 1.0F);
        Orange.setFamily(familyCitrus);
        Manderin.addProduct(Food.Manderin.get(1), 1.0F);
        Manderin.setFamily(familyCitrus);
        Tangerine.addProduct(Food.Tangerine.get(1), 1.0F);
        Tangerine.setFamily(familyCitrus);
        Satsuma.addProduct(Food.Satsuma.get(1), 1.0F);
        Satsuma.setFamily(familyCitrus);
        KeyLime.addProduct(Food.KeyLime.get(1), 1.0F);
        KeyLime.setFamily(familyCitrus);
        Lime.addProduct(Food.Lime.get(1), 1.0F);
        Lime.setFamily(familyCitrus);
        FingerLime.addProduct(Food.FingerLime.get(1), 1.0F);
        FingerLime.setFamily(familyCitrus);
        Pomelo.addProduct(Food.Pomelo.get(1), 1.0F);
        Pomelo.setFamily(familyCitrus);
        Grapefruit.addProduct(Food.Grapefruit.get(1), 1.0F);
        Grapefruit.setFamily(familyCitrus);
        Kumquat.addProduct(Food.Kumquat.get(1), 1.0F);
        Kumquat.setFamily(familyCitrus);
        Citron.addProduct(Food.Citron.get(1), 1.0F);
        Citron.setFamily(familyCitrus);
        BuddhaHand.addProduct(Food.BuddhaHand.get(1), 1.0F);
        BuddhaHand.setFamily(familyCitrus);
        Blackthorn.addProduct(Food.Blackthorn.get(1), 1.0F);
        Blackthorn.setFamily(familyPrune);
        CherryPlum.addProduct(Food.CherryPlum.get(1), 1.0F);
        CherryPlum.setFamily(familyPrune);
        Peach.addProduct(Food.Peach.get(1), 1.0F);
        Peach.setFamily(familyPrune);
        Nectarine.addProduct(Food.Nectarine.get(1), 1.0F);
        Nectarine.setFamily(familyPrune);
        Apricot.addProduct(Food.Apricot.get(1), 1.0F);
        Apricot.setFamily(familyPrune);
        Almond.addProduct(Food.Almond.get(1), 1.0F);
        Almond.setFamily(familyPrune);
        WildCherry.addProduct(Food.WildCherry.get(1), 1.0F);
        WildCherry.setFamily(familyPrune);
        SourCherry.addProduct(Food.SourCherry.get(1), 1.0F);
        SourCherry.setFamily(familyPrune);
        BlackCherry.addProduct(Food.BlackCherry.get(1), 1.0F);
        BlackCherry.setFamily(familyPrune);
        Hazelnut.addProduct(Food.Hazelnut.get(1), 1.0F);
        Hazelnut.setFamily(familyNuts);
        Butternut.addProduct(Food.Butternut.get(1), 1.0F);
        Butternut.setFamily(familyNuts);
        Beechnut.addProduct(Food.Beechnut.get(1), 1.0F);
        Beechnut.setFamily(familyNuts);
        Pecan.addProduct(Food.Pecan.get(1), 1.0F);
        Pecan.setFamily(familyNuts);
        Banana.addProduct(Food.Banana.get(2), 1.0F);
        Banana.setFamily(familyJungle);
        RedBanana.addProduct(Food.RedBanana.get(2), 1.0F);
        RedBanana.setFamily(familyJungle);
        Plantain.addProduct(Food.Plantain.get(2), 1.0F);
        Plantain.setFamily(familyJungle);
        BrazilNut.addProduct(Food.BrazilNut.get(4), 1.0F);
        BrazilNut.setFamily(familyNuts);
        Fig.addProduct(Food.Fig.get(1), 1.0F);
        Fig.setFamily(familyPrune);
        Acorn.addProduct(Food.Acorn.get(1), 1.0F);
        Acorn.setFamily(familyNuts);
        Elderberry.addProduct(Food.Elderberry.get(1), 1.0F);
        Elderberry.setFamily(familyPrune);
        Olive.addProduct(Food.Olive.get(1), 1.0F);
        Olive.setFamily(familyPrune);
        GingkoNut.addProduct(Food.GingkoNut.get(1), 1.0F);
        GingkoNut.setFamily(familyNuts);
        Coffee.addProduct(Food.Coffee.get(1), 1.0F);
        Coffee.setFamily(familyJungle);
        Pear.addProduct(Food.Pear.get(1), 1.0F);
        Pear.setFamily(familyPome);
        OsangeOsange.addProduct(Food.OsangeOrange.get(1), 1.0F);
        OsangeOsange.setFamily(familyPome);
        Clove.addProduct(Food.Clove.get(1), 1.0F);
        Clove.setFamily(familyNuts);
        Blackcurrant.addProduct(Food.Blackcurrant.get(2), 1.0F);
        Blackcurrant.setFamily(familyBerry);
        Redcurrant.addProduct(Food.Redcurrant.get(2), 1.0F);
        Redcurrant.setFamily(familyBerry);
        Blackberry.addProduct(Food.Blackberry.get(2), 1.0F);
        Blackberry.setFamily(familyBerry);
        Raspberry.addProduct(Food.Raspberry.get(2), 1.0F);
        Raspberry.setFamily(familyBerry);
        Blueberry.addProduct(Food.Blueberry.get(2), 1.0F);
        Blueberry.setFamily(familyBerry);
        Cranberry.addProduct(Food.Cranberry.get(2), 1.0F);
        Cranberry.setFamily(familyBerry);
        Juniper.addProduct(Food.Juniper.get(2), 1.0F);
        Juniper.setFamily(familyBerry);
        Gooseberry.addProduct(Food.Gooseberry.get(2), 1.0F);
        Gooseberry.setFamily(familyBerry);
        GoldenRaspberry.addProduct(Food.GoldenRaspberry.get(2), 1.0F);
        GoldenRaspberry.setFamily(familyBerry);
        Coconut.addProduct(Food.Coconut.get(1), 1.0F);
        Coconut.setFamily(familyJungle);
        Cashew.addProduct(Food.Cashew.get(1), 1.0F);
        Cashew.setFamily(familyJungle);
        Avacado.addProduct(Food.Avacado.get(1), 1.0F);
        Avacado.setFamily(familyJungle);
        Nutmeg.addProduct(Food.Nutmeg.get(1), 1.0F);
        Nutmeg.setFamily(familyJungle);
        Allspice.addProduct(Food.Allspice.get(1), 1.0F);
        Allspice.setFamily(familyJungle);
        Chilli.addProduct(Food.Chilli.get(1), 1.0F);
        Chilli.setFamily(familyJungle);
        StarAnise.addProduct(Food.StarAnise.get(1), 1.0F);
        StarAnise.setFamily(familyJungle);
        Mango.addProduct(Food.Mango.get(1), 1.0F);
        Mango.setFamily(familyPome);
        Starfruit.addProduct(Food.Starfruit.get(1), 1.0F);
        Starfruit.setFamily(familyJungle);
        Candlenut.addProduct(Food.Candlenut.get(1), 1.0F);
        Candlenut.setFamily(familyJungle);
        if (ConfigurationMain.alterLemon) {
            try {
                IAlleleFruit lemon = (IAlleleFruit) AlleleManager.alleleRegistry.getAllele("forestry.fruitLemon");
                FruitProviderNone prov = (FruitProviderRipening) lemon.getProvider();
                Field f = FruitProviderNone.class.getDeclaredField("family");
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                f.setAccessible(true);
                modifiersField.setAccessible(true);
                modifiersField.setInt(f, f.getModifiers() & -17);
                f.set(prov, familyCitrus);
            } catch (Exception var10) {
                var10.printStackTrace();
            }
        }

        for (IAlleleSpecies tree : Binnie.Genetics.treeBreedingSystem.getAllSpecies()) {
            if (tree instanceof AlleleTreeSpecies && ((IAlleleTreeSpecies) tree).getSuitableFruit().contains(familyPrune)) {
                ((AlleleTreeSpecies) tree).addFruitFamily(familyCitrus);
            }
        }

    }

    private void setFamily(IFruitFamily family) {
        this.family = family;
    }

    private ExtraTreeFruitGene(int time, int unripe, int colour, FruitSprite index) {
        this.colour = colour;
        this.index = index;
        this.setRipening(time, unripe);
    }

    private ExtraTreeFruitGene(String name, FruitPod pod) {
        this.pod = pod;
        this.ripeningPeriod = 2;
    }

    public void setRipening(int time, int unripe) {
        this.ripeningPeriod = time;
        this.colourUnripe = unripe;
        this.isRipening = true;
        this.diffR = (this.colour >> 16 & 255) - (unripe >> 16 & 255);
        this.diffG = (this.colour >> 8 & 255) - (unripe >> 8 & 255);
        this.diffB = (this.colour & 255) - (unripe & 255);
    }

    public void addProduct(ItemStack product, float chance) {
        this.products.put(product, Float.valueOf(chance));
    }

    public String getUID() {
        return "extratrees.fruit." + this.toString().toLowerCase();
    }

    public boolean isDominant() {
        return true;
    }

    public IFruitProvider getProvider() {
        return this;
    }

    public ItemStack[] getProducts() {
        return (ItemStack[]) this.products.keySet().toArray(new ItemStack[0]);
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

    public int getColour(ITreeGenome genome, IBlockAccess world, int x, int y, int z, int ripeningTime) {
        if (!this.isRipening) {
            return this.colour;
        } else {
            float stage = this.getRipeningStage(ripeningTime);
            int r = (this.colourUnripe >> 16 & 255) + (int) ((float) this.diffR * stage);
            int g = (this.colourUnripe >> 8 & 255) + (int) ((float) this.diffG * stage);
            int b = (this.colourUnripe & 255) + (int) ((float) this.diffB * stage);
            return (r & 255) << 16 | (g & 255) << 8 | b & 255;
        }
    }

    public boolean markAsFruitLeaf(ITreeGenome genome, World world, int x, int y, int z) {
        return this.pod == null;
    }

    public int getRipeningPeriod() {
        return this.ripeningPeriod;
    }

    public ItemStack[] getFruits(ITreeGenome genome, World world, int x, int y, int z, int ripeningTime) {
        if (this.pod == null) {
            ArrayList<ItemStack> product = new ArrayList();
            float stage = this.getRipeningStage(ripeningTime);
            if (stage < 0.5F) {
                return new ItemStack[0];
            } else {
                float modeYieldMod = 1.0F;

                for (Entry<ItemStack, Float> entry : this.products.entrySet()) {
                    if (world.rand.nextFloat() <= genome.getYield() * modeYieldMod * ((Float) entry.getValue()).floatValue() * 5.0F * stage) {
                        product.add(((ItemStack) entry.getKey()).copy());
                    }
                }

                return (ItemStack[]) product.toArray(new ItemStack[0]);
            }
        } else if (ripeningTime < 2) {
            return new ItemStack[0];
        } else {
            List<ItemStack> product = new ArrayList();

            for (Entry<ItemStack, Float> entry : this.products.entrySet()) {
                ItemStack single = ((ItemStack) entry.getKey()).copy();
                single.stackSize = 1;

                for (int i = 0; i < ((ItemStack) entry.getKey()).stackSize; ++i) {
                    if (world.rand.nextFloat() <= ((Float) entry.getValue()).floatValue()) {
                        product.add(single.copy());
                    }
                }
            }

            return (ItemStack[]) product.toArray(new ItemStack[0]);
        }
    }

    private float getRipeningStage(int ripeningTime) {
        return ripeningTime >= this.ripeningPeriod ? 1.0F : (float) ripeningTime / (float) this.ripeningPeriod;
    }

    public boolean requiresFruitBlocks() {
        return this.pod != null;
    }

    public boolean trySpawnFruitBlock(ITreeGenome genome, World world, int x, int y, int z) {
        return this.pod == null ? false : (world.rand.nextFloat() > genome.getSappiness() ? false : Binnie.Genetics.getTreeRoot().setFruitBlock(world, (IAlleleFruit) genome.getActiveAllele(EnumTreeChromosome.FRUITS), genome.getSappiness(), this.pod.getTextures(), x, y, z));
    }

    public boolean setFruitBlock(World world, IAlleleFruit allele, float sappiness, int x, int y, int z) {
        return true;
    }

    public static int getDirectionalMetadata(World world, int x, int y, int z) {
        for (int i = 0; i < 4; ++i) {
            if (isValidPot(world, x, y, z, i)) {
                return i;
            }
        }

        return -1;
    }

    public static boolean isValidPot(World world, int x, int y, int z, int notchDirection) {
        x = x + Direction.offsetX[notchDirection];
        z = z + Direction.offsetZ[notchDirection];
        Block block = world.getBlock(x, y, z);
        return block != Blocks.log && block != Blocks.log2 ? (block != null ? block.isWood(world, x, y, z) : false) : BlockLog.func_150165_c(world.getBlockMetadata(x, y, z)) == 3;
    }

    public short getIconIndex(ITreeGenome genome, IBlockAccess world, int x, int y, int z, int ripeningTime, boolean fancy) {
        return this.index.getIndex();
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        if (this.ordinal() == 0) {
            for (FruitSprite sprite : FruitSprite.values()) {
                sprite.registerIcons(register);
            }
        }

    }

    public String getName() {
        return this.getDescription();
    }

    public String getNameOfFruit() {
        if (this == Apple) {
            return "Apple";
        } else {
            for (ItemStack stack : this.products.keySet()) {
                if (stack.getItem() == ExtraTrees.itemFood) {
                    return Food.values()[stack.getItemDamage()].toString();
                }
            }

            return "NoFruit";
        }
    }

    public String getUnlocalizedName() {
        return this.getUID();
    }
}
