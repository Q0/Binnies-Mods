package binnie.botany.gardening;

import binnie.botany.Botany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IFlower;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.items.BotanyItems;
import binnie.core.BinnieCore;
import com.mojang.authlib.GameProfile;
import forestry.api.core.EnumTemperature;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Gardening {
    public static Map fertiliserAcid = new LinkedHashMap();
    public static Map fertiliserAlkaline = new LinkedHashMap();
    public static Map fertiliserNutrient = new LinkedHashMap();

    public Gardening() {
        super();
    }

    public static boolean isSoil(Block block) {
        return block instanceof IBlockSoil;
    }

    public static boolean isSoil(Item item) {
        return item instanceof ItemBlock && isSoil(((ItemBlock) item).field_150939_a);
    }

    public static boolean isSoil(ItemStack item) {
        return isSoil(item.getItem());
    }

    public static EnumMoisture getNaturalMoisture(World world, int x, int y, int z) {
        float bias = getBiomeMoisture(world.getBiomeGenForCoords(x, z), y);

        for (int dx = -1; dx < 2; ++dx) {
            for (int dz = -1; dz < 2; ++dz) {
                if (dx != 0 || dz != 0) {
                    if (world.getBlock(x + dx, y, z + dz) == Blocks.sand) {
                        bias = (float) ((double) bias - 1.5D);
                    } else if (world.getBlock(x + dx, y, z + dz) == Blocks.water) {
                        bias = (float) ((double) bias + 1.5D);
                    }
                }
            }
        }

        if (world.isRaining() && world.canBlockSeeTheSky(x, y + 1, z) && world.getPrecipitationHeight(x, z) <= y + 1) {
            bias = (float) ((double) bias + 1.5D);
        }

        for (int dx = -1; dx < 2; ++dx) {
            for (int dz = -1; dz < 2; ++dz) {
                if ((dx != 0 || dz != 0) && world.getBlock(x + dx, y, z + dz) == Blocks.gravel && bias > 0.0F) {
                    bias *= 0.4F;
                }
            }
        }

        return bias <= -1.0F ? EnumMoisture.Dry : (bias >= 1.0F ? EnumMoisture.Damp : EnumMoisture.Normal);
    }

    public static EnumAcidity getNaturalPH(World world, int x, int y, int z) {
        float bias = getBiomePH(world.getBiomeGenForCoords(x, z), y);
        return bias <= -1.0F ? EnumAcidity.Acid : (bias >= 1.0F ? EnumAcidity.Alkaline : EnumAcidity.Neutral);
    }

    public static float getBiomeMoisture(BiomeGenBase biome, int H) {
        double R = (double) biome.rainfall;
        double T = (double) biome.temperature;
        double m = 3.2D * (R - 0.5D) - 0.4D * (1.0D + T + 0.5D * T * T) + 1.1D - 1.6D * (T - 0.9D) * (T - 0.9D) - 0.002D * (double) (H - 64);
        return (float) (m == 0.0D ? m : (m < 0.0D ? -Math.sqrt(m * m) : Math.sqrt(m * m)));
    }

    public static float getBiomePH(BiomeGenBase biome, int H) {
        double R = (double) biome.rainfall;
        double T = (double) biome.temperature;
        return (float) (-3.0D * (R - 0.5D) + 0.5D * (T - 0.699999988079071D) * (T - 0.699999988079071D) + (double) (0.02F * (float) (H - 64)) - 0.15000000596046448D);
    }

    public static void createSoil(World world, int x, int y, int z, EnumSoilType soil, EnumMoisture moisture, EnumAcidity acidity) {
        int meta = moisture.ordinal() + acidity.ordinal() * 3;
        world.setBlock(x, y, z, getSoilBlock(soil), meta, 2);
    }

    public static boolean plant(World world, int x, int y, int z, IFlower flower, GameProfile owner) {
        boolean set = world.setBlock(x, y, z, Botany.flower, 0, 2);
        if (!set) {
            return false;
        } else {
            TileEntity tileFlower = world.getTileEntity(x, y, z);
            TileEntity below = world.getTileEntity(x, y - 1, z);
            if (tileFlower != null && tileFlower instanceof TileEntityFlower) {
                if (below instanceof TileEntityFlower) {
                    ((TileEntityFlower) tileFlower).setSection(((TileEntityFlower) below).getSection());
                } else {
                    ((TileEntityFlower) tileFlower).create(flower, owner);
                }
            }

            tryGrowSection(world, x, y, z);
            return true;
        }
    }

    public static void tryGrowSection(World world, int x, int y, int z) {
        if (BinnieCore.proxy.isSimulating(world)) {
            TileEntity flower = world.getTileEntity(x, y, z);
            if (flower != null && flower instanceof TileEntityFlower) {
                IFlower iflower = ((TileEntityFlower) flower).getFlower();
                int section = ((TileEntityFlower) flower).getSection();
                if (iflower != null && section < iflower.getGenome().getPrimary().getType().getSections() - 1 && iflower.getAge() > 0) {
                    world.setBlock(x, y + 1, z, Botany.flower, 0, 2);
                    TileEntity tileFlower = world.getTileEntity(x, y + 1, z);
                    if (tileFlower != null && tileFlower instanceof TileEntityFlower) {
                        ((TileEntityFlower) tileFlower).setSection(section + 1);
                    }
                }
            }

        }
    }

    public static void onGrowFromSeed(World world, int x, int y, int z) {
        tryGrowSection(world, x, y, z);
    }

    public static Collection getAcidFertilisers() {
        return fertiliserAcid.keySet();
    }

    public static Collection getAlkalineFertilisers() {
        return fertiliserAlkaline.keySet();
    }

    public static Collection getNutrientFertilisers() {
        return fertiliserNutrient.keySet();
    }

    public static boolean isAcidFertiliser(ItemStack itemstack) {
        for (ItemStack stack : getAcidFertilisers()) {
            if (stack != null && stack.isItemEqual(itemstack)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isAlkalineFertiliser(ItemStack itemstack) {
        for (ItemStack stack : getAlkalineFertilisers()) {
            if (stack != null && stack.isItemEqual(itemstack)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isNutrientFertiliser(ItemStack itemstack) {
        for (ItemStack stack : getNutrientFertilisers()) {
            if (stack != null && stack.isItemEqual(itemstack)) {
                return true;
            }
        }

        return false;
    }

    public static int getFertiliserStrength(ItemStack stack) {
        if (stack == null) {
            return 1;
        } else {
            for (Entry<ItemStack, Integer> entry : fertiliserAcid.entrySet()) {
                if (((ItemStack) entry.getKey()).isItemEqual(stack)) {
                    return ((Integer) entry.getValue()).intValue();
                }
            }

            for (Entry<ItemStack, Integer> entry : fertiliserAlkaline.entrySet()) {
                if (((ItemStack) entry.getKey()).isItemEqual(stack)) {
                    return ((Integer) entry.getValue()).intValue();
                }
            }

            for (Entry<ItemStack, Integer> entry : fertiliserNutrient.entrySet()) {
                if (((ItemStack) entry.getKey()).isItemEqual(stack)) {
                    return ((Integer) entry.getValue()).intValue();
                }
            }

            return 1;
        }
    }

    public static boolean canTolerate(IFlower flower, World world, int x, int y, int z) {
        if (flower != null && flower.getGenome() != null) {
            world.getBlock(x, y - 1, z);
            int soilMeta = world.getBlockMetadata(x, y - 1, z);
            BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
            return canTolerate(flower, EnumAcidity.values()[soilMeta / 3 % 3], EnumMoisture.values()[soilMeta % 3], EnumTemperature.getFromValue(biome.temperature));
        } else {
            return false;
        }
    }

    public static EnumSoilType getSoilType(World world, int x, int y, int z) {
        return world.getBlock(x, y, z) instanceof IBlockSoil ? ((IBlockSoil) world.getBlock(x, y, z)).getType(world, x, y, z) : EnumSoilType.SOIL;
    }

    public static Block getSoilBlock(EnumSoilType type) {
        return getSoilBlock(type, false);
    }

    public static Block getSoilBlock(EnumSoilType type, boolean weedkill) {
        switch (type) {
            case FLOWERBED:
                return weedkill ? Botany.flowerbedNoWeed : Botany.flowerbed;
            case LOAM:
                return weedkill ? Botany.loamNoWeed : Botany.loam;
            case SOIL:
            default:
                return weedkill ? Botany.soilNoWeed : Botany.soil;
        }
    }

    public static boolean canTolerate(IFlower flower, EnumAcidity ePH, EnumMoisture eMoisture, EnumTemperature eTemp) {
        return flower.getGenome().canTolerate(ePH) && flower.getGenome().canTolerate(eMoisture) && flower.getGenome().canTolerate(eTemp);
    }

    public static boolean isWeedkiller(ItemStack heldItem) {
        return heldItem != null && heldItem.isItemEqual(BotanyItems.Weedkiller.get(1));
    }

    public static boolean addWeedKiller(World world, int x, int y, int z) {
        if (!(world.getBlock(x, y, z) instanceof IBlockSoil)) {
            return false;
        } else {
            EnumSoilType type = getSoilType(world, x, y, z);
            Block block = getSoilBlock(type, true);
            boolean done = world.setBlock(x, y, z, block, world.getBlockMetadata(x, y, z), 2);
            if (done && BlockPlant.isWeed(world, x, y + 1, z)) {
                world.setBlockToAir(x, y + 1, z);
            }

            return done;
        }
    }
}
