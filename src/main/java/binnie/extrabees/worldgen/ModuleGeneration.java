package binnie.extrabees.worldgen;

import binnie.core.IInitializable;
import binnie.core.genetics.ForestryAllele;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.config.ConfigurationMain;
import binnie.extrabees.genetics.ExtraBeesSpecies;
import binnie.extrabees.worldgen.BlockExtraBeeHive;
import binnie.extrabees.worldgen.EnumHiveType;
import binnie.extrabees.worldgen.HiveDrop;
import binnie.extrabees.worldgen.ItemBeehive;
import binnie.extrabees.worldgen.MaterialBeehive;
import binnie.extrabees.worldgen.WorldGenHiveNether;
import binnie.extrabees.worldgen.WorldGenHiveRock;
import binnie.extrabees.worldgen.WorldGenHiveWater;
import buildcraft.api.core.BuildCraftAPI;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class ModuleGeneration implements IWorldGenerator, IInitializable {
   static int waterRate = 2;
   static int rockRate = 2;
   static int netherRate = 2;
   static int marbleRate = 2;

   public ModuleGeneration() {
      super();
   }

   public void preInit() {
      ExtraBees.materialBeehive = new MaterialBeehive();
      ExtraBees.hive = new BlockExtraBeeHive();
      GameRegistry.registerBlock(ExtraBees.hive, ItemBeehive.class, "hive");
   }

   public void init() {
      waterRate = ConfigurationMain.waterHiveRate;
      rockRate = ConfigurationMain.rockHiveRate;
      netherRate = ConfigurationMain.netherHiveRate;
      GameRegistry.registerWorldGenerator(new ModuleGeneration(), 0);
      if(!ConfigurationMain.canQuarryMineHives) {
         BuildCraftAPI.softBlocks.add(ExtraBees.hive);
      }

   }

   public void postInit() {
      EnumHiveType.Water.drops.add(new HiveDrop(ExtraBeesSpecies.WATER, 80));
      EnumHiveType.Water.drops.add(new HiveDrop(ForestryAllele.BeeSpecies.Valiant.getAllele(), 3));
      EnumHiveType.Rock.drops.add(new HiveDrop(ExtraBeesSpecies.ROCK, 80));
      EnumHiveType.Rock.drops.add(new HiveDrop(ForestryAllele.BeeSpecies.Valiant.getAllele(), 3));
      EnumHiveType.Nether.drops.add(new HiveDrop(ExtraBeesSpecies.BASALT, 80));
      EnumHiveType.Nether.drops.add(new HiveDrop(ForestryAllele.BeeSpecies.Valiant.getAllele(), 3));
      ExtraBees.hive.setHarvestLevel("scoop", 0, 0);
      ExtraBees.hive.setHarvestLevel("scoop", 0, 1);
      ExtraBees.hive.setHarvestLevel("scoop", 0, 2);
      ExtraBees.hive.setHarvestLevel("scoop", 0, 3);
   }

   public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
      chunkX = chunkX << 4;
      chunkZ = chunkZ << 4;

      for(int i = 0; i < waterRate; ++i) {
         int randPosX = chunkX + rand.nextInt(16);
         int randPosY = rand.nextInt(50) + 20;
         int randPosZ = chunkZ + rand.nextInt(16);
         (new WorldGenHiveWater()).generate(world, rand, randPosX, randPosY, randPosZ);
      }

      for(int i = 0; i < rockRate; ++i) {
         int randPosX = chunkX + rand.nextInt(16);
         int randPosY = rand.nextInt(50) + 20;
         int randPosZ = chunkZ + rand.nextInt(16);
         (new WorldGenHiveRock()).generate(world, rand, randPosX, randPosY, randPosZ);
      }

      for(int i = 0; i < netherRate; ++i) {
         int randPosX = chunkX + rand.nextInt(16);
         int randPosY = rand.nextInt(50) + 20;
         int randPosZ = chunkZ + rand.nextInt(16);
         (new WorldGenHiveNether()).generate(world, rand, randPosX, randPosY, randPosZ);
      }

   }
}
