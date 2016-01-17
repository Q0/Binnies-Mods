package binnie.extrabees.worldgen;

import binnie.extrabees.ExtraBees;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenHiveRock extends WorldGenerator {
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final Block block = world.getBlock(i, j, k);

        if (block == null) {
            return true;
        }

        if (block.isReplaceableOreGen(world, i, j, k, Blocks.stone)) {
            world.setBlock(i, j, k, ExtraBees.hive, 1, 0);
        }

        return true;
    }
}
