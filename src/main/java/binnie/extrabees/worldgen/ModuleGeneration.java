package binnie.extrabees.worldgen;

import binnie.core.IInitializable;
import binnie.core.genetics.ForestryAllele;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.config.ConfigurationMain;
import binnie.extrabees.genetics.ExtraBeesSpecies;
import buildcraft.api.core.BuildCraftAPI;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IHiveDrop;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class ModuleGeneration implements IWorldGenerator, IInitializable {
    static int waterRate;
    static int rockRate;
    static int netherRate;
    static int marbleRate;

    public void preInit() {
        ExtraBees.materialBeehive = new MaterialBeehive();
        GameRegistry.registerBlock(ExtraBees.hive = new BlockExtraBeeHive(), (Class) ItemBeehive.class, "hive");
    }

    public void init() {
        ModuleGeneration.waterRate = ConfigurationMain.waterHiveRate;
        ModuleGeneration.rockRate = ConfigurationMain.rockHiveRate;
        ModuleGeneration.netherRate = ConfigurationMain.netherHiveRate;
        GameRegistry.registerWorldGenerator((IWorldGenerator) new ModuleGeneration(), 0);
        if (!ConfigurationMain.canQuarryMineHives) {
            BuildCraftAPI.softBlocks.add(ExtraBees.hive);
        }
    }

    public void postInit() {
        EnumHiveType.Water.drops.add((IHiveDrop) new HiveDrop((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, 80));
        EnumHiveType.Water.drops.add((IHiveDrop) new HiveDrop(ForestryAllele.BeeSpecies.Valiant.getAllele(), 3));
        EnumHiveType.Rock.drops.add((IHiveDrop) new HiveDrop((IAlleleBeeSpecies) ExtraBeesSpecies.ROCK, 80));
        EnumHiveType.Rock.drops.add((IHiveDrop) new HiveDrop(ForestryAllele.BeeSpecies.Valiant.getAllele(), 3));
        EnumHiveType.Nether.drops.add((IHiveDrop) new HiveDrop((IAlleleBeeSpecies) ExtraBeesSpecies.BASALT, 80));
        EnumHiveType.Nether.drops.add((IHiveDrop) new HiveDrop(ForestryAllele.BeeSpecies.Valiant.getAllele(), 3));
        ExtraBees.hive.setHarvestLevel("scoop", 0, 0);
        ExtraBees.hive.setHarvestLevel("scoop", 0, 1);
        ExtraBees.hive.setHarvestLevel("scoop", 0, 2);
        ExtraBees.hive.setHarvestLevel("scoop", 0, 3);
    }

    public void generate(final Random rand, int chunkX, int chunkZ, final World world, final IChunkProvider chunkGenerator, final IChunkProvider chunkProvider) {
        chunkX <<= 4;
        chunkZ <<= 4;
        for (int i = 0; i < ModuleGeneration.waterRate; ++i) {
            final int randPosX = chunkX + rand.nextInt(16);
            final int randPosY = rand.nextInt(50) + 20;
            final int randPosZ = chunkZ + rand.nextInt(16);
            new WorldGenHiveWater().generate(world, rand, randPosX, randPosY, randPosZ);
        }
        for (int i = 0; i < ModuleGeneration.rockRate; ++i) {
            final int randPosX = chunkX + rand.nextInt(16);
            final int randPosY = rand.nextInt(50) + 20;
            final int randPosZ = chunkZ + rand.nextInt(16);
            new WorldGenHiveRock().generate(world, rand, randPosX, randPosY, randPosZ);
        }
        for (int i = 0; i < ModuleGeneration.netherRate; ++i) {
            final int randPosX = chunkX + rand.nextInt(16);
            final int randPosY = rand.nextInt(50) + 20;
            final int randPosZ = chunkZ + rand.nextInt(16);
            new WorldGenHiveNether().generate(world, rand, randPosX, randPosY, randPosZ);
        }
    }

    static {
        ModuleGeneration.waterRate = 2;
        ModuleGeneration.rockRate = 2;
        ModuleGeneration.netherRate = 2;
        ModuleGeneration.marbleRate = 2;
    }
}
