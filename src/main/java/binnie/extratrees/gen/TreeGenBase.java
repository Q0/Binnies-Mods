package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class TreeGenBase extends WorldGenerator {
    protected ITree tree;
    protected ITreeGenData treeGen;
    protected World world;
    protected Random rand;
    protected int startX;
    protected int startY;
    protected int startZ;
    protected int girth;
    protected int height;
    protected int minHeight = 4;
    protected int maxHeight = 80;
    protected boolean spawnPods = false;
    protected int minPodHeight = 3;

    public TreeGenBase() {
        super();
    }

    protected final int randBetween(int a, int b) {
        return a + this.rand.nextInt(b - a);
    }

    protected final float randBetween(float a, float b) {
        return a + this.rand.nextFloat() * (b - a);
    }

    public boolean generate(World world, Random random, int x, int y, int z) {
        this.world = world;
        this.startX = x;
        this.startY = y;
        this.startZ = z;
        this.girth = this.tree.getGirth(world, x, y, z);
        this.height = (int) ((float) this.randBetween(this.getHeight()[0], this.getHeight()[1]) * this.tree.getGenome().getHeight());
        if (this.tree.canGrow(world, x, y, z, this.girth, this.height)) {
            this.generate();
            return true;
        } else {
            return false;
        }
    }

    protected void generate() {
    }

    protected int[] getHeight() {
        return new int[]{5, 2};
    }

    private static class Vector {
        int x;
        int y;
        int z;

        public Vector(int x, int y, int z) {
            super();
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
