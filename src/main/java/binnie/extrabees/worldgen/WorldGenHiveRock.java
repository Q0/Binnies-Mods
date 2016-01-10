package binnie.extrabees.worldgen;

import binnie.extrabees.ExtraBees;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenHiveRock extends WorldGenerator {
    public WorldGenHiveRock() {
        super();
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        world.getWorldChunkManager().getBiomeGenAt(i, k);
        Block block = world.getBlock(i, j, k);
        if (block == null) {
            return true;
        } else {
            if (block.isReplaceableOreGen(world, i, j, k, Blocks.stone)) {
                world.setBlock(i, j, k, ExtraBees.hive, 1, 0);
            }

            return true;
        }
    }
}
