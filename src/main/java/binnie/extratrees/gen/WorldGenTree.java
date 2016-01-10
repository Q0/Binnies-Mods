package binnie.extratrees.gen;

import binnie.extratrees.genetics.ExtraTreeSpecies;
import binnie.extratrees.worldgen.BlockType;
import binnie.extratrees.worldgen.BlockTypeLeaf;
import binnie.extratrees.worldgen.BlockTypeLog;
import binnie.extratrees.worldgen.BlockTypeVoid;
import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public abstract class WorldGenTree extends WorldGenerator {
    protected ITree tree;
    protected ITreeGenData treeGen;
    protected World world;
    protected Random rand;
    protected int startX;
    protected int startY;
    protected int startZ;
    protected int girth;
    protected int height;
    protected int minHeight = 3;
    protected int maxHeight = 80;
    protected boolean spawnPods = false;
    protected int minPodHeight = 3;
    BlockType leaf;
    BlockType wood;
    BlockType vine = new BlockType(Blocks.vine, 0);
    BlockType air = new BlockTypeVoid();
    float bushiness = 0.0F;

    protected int randBetween(int a, int b) {
        return a + this.rand.nextInt(b - a);
    }

    protected float randBetween(float a, float b) {
        return a + this.rand.nextFloat() * (b - a);
    }

    public void generate() {
        this.generateTreeTrunk(this.height, this.girth);
        int leafSpawn = this.height + 1;
        this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 1.0F, 1, this.leaf, false);
        this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 1.5F, 1, this.leaf, false);
        this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 2.9F, 1, this.leaf, false);
        this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 2.9F, 1, this.leaf, false);
    }

    public boolean canGrow() {
        return this.tree.canGrow(this.world, this.startX, this.startY, this.startZ, this.tree.getGirth(this.world, this.startX, this.startY, this.startZ), this.height);
    }

    public void preGenerate() {
        this.height = this.determineHeight(5, 3);
        this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
    }

    protected int determineGirth(int base) {
        return base;
    }

    protected int modifyByHeight(int val, int min, int max) {
        int determined = Math.round((float) val * this.treeGen.getHeightModifier());
        return determined < min ? min : (determined > max ? max : determined);
    }

    protected int determineHeight(int required, int variation) {
        int determined = Math.round((float) (required + this.rand.nextInt(variation)) * this.treeGen.getHeightModifier());
        return determined < this.minHeight ? this.minHeight : (determined > this.maxHeight ? this.maxHeight : determined);
    }

    public BlockType getLeaf() {
        return new BlockTypeLeaf();
    }

    public BlockType getWood() {
        return new BlockTypeLog(((ExtraTreeSpecies) this.tree.getGenome().getPrimary()).getLog());
    }

    public WorldGenTree(ITree tree) {
        super();
        this.tree = tree;
        this.spawnPods = tree.allowsFruitBlocks();
        if (tree instanceof ITreeGenData) {
            this.treeGen = tree;
        }

    }

    public final boolean generate(World world, Random random, int x, int y, int z) {
        return this.generate(world, random, x, y, z, false);
    }

    public final boolean generate(World world, Random random, int x, int y, int z, boolean force) {
        this.world = world;
        this.rand = random;
        this.startX = x;
        this.startY = y;
        this.startZ = z;
        this.leaf = this.getLeaf();
        this.wood = this.getWood();
        this.preGenerate();
        if (!force && !this.canGrow()) {
            return false;
        } else {
            int offset = (this.girth - 1) / 2;

            for (int x2 = 0; x2 < this.girth; ++x2) {
                for (int y2 = 0; y2 < this.girth; ++y2) {
                    this.addBlock(x2, 0, y2, new BlockTypeVoid(), true);
                }
            }

            this.generate();
            return true;
        }
    }

    public final WorldGenTree.Vector getStartVector() {
        return new WorldGenTree.Vector((float) this.startX, (float) this.startY, (float) this.startZ);
    }

    protected void generateTreeTrunk(int height, int width) {
        this.generateTreeTrunk(height, width, 0.0F);
    }

    protected void generateTreeTrunk(int height, int width, float vines) {
        int offset = (width - 1) / 2;

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < width; ++y) {
                for (int i = 0; i < height; ++i) {
                    this.addWood(x - offset, i, y - offset, true);
                    if (this.rand.nextFloat() < vines) {
                        this.addVine(x - offset - 1, i, y - offset);
                    }

                    if (this.rand.nextFloat() < vines) {
                        this.addVine(x - offset + 1, i, y - offset);
                    }

                    if (this.rand.nextFloat() < vines) {
                        this.addVine(x - offset, i, y - offset - 1);
                    }

                    if (this.rand.nextFloat() < vines) {
                        this.addVine(x - offset, i, y - offset + 1);
                    }
                }
            }
        }

        if (this.spawnPods) {
            for (int y = this.minPodHeight; y < height; ++y) {
                for (int x = 0; x < this.girth; ++x) {
                    for (int z = 0; z < this.girth; ++z) {
                        if (x <= 0 || x >= this.girth || z <= 0 || z >= this.girth) {
                            this.treeGen.trySpawnFruitBlock(this.world, this.startX + x + 1, this.startY + y, this.startZ + z);
                            this.treeGen.trySpawnFruitBlock(this.world, this.startX + x - 1, this.startY + y, this.startZ + z);
                            this.treeGen.trySpawnFruitBlock(this.world, this.startX + x, this.startY + y, this.startZ + z + 1);
                            this.treeGen.trySpawnFruitBlock(this.world, this.startX + x, this.startY + y, this.startZ + z - 1);
                        }
                    }
                }
            }

        }
    }

    protected void generateSupportStems(int height, int girth, float chance, float maxHeight) {
        int offset = 1;

        for (int x = -offset; x < girth + offset; ++x) {
            for (int z = -offset; z < girth + offset; ++z) {
                if ((x != -offset || z != -offset) && (x != girth + offset || z != girth + offset) && (x != -offset || z != girth + offset) && (x != girth + offset || z != -offset)) {
                    int stemHeight = this.rand.nextInt(Math.round((float) height * maxHeight));
                    if (this.rand.nextFloat() < chance) {
                        for (int i = 0; i < stemHeight; ++i) {
                            this.addWood(x, i, z, true);
                        }
                    }
                }
            }
        }

    }

    protected final void clearBlock(int x, int y, int z) {
        this.air.setBlock(this.world, this.tree, this.startX + x, this.startY + y, this.startZ + z);
    }

    protected final void addBlock(int x, int y, int z, BlockType type, boolean doReplace) {
        if (doReplace || this.world.isAirBlock(this.startX + x, this.startY + y, this.startZ + z)) {
            type.setBlock(this.world, this.tree, this.startX + x, this.startY + y, this.startZ + z);
        }

    }

    protected final void addWood(int x, int y, int z, boolean doReplace) {
        this.addBlock(x, y, z, this.wood, doReplace);
    }

    protected final void addLeaf(int x, int y, int z, boolean doReplace) {
        this.addBlock(x, y, z, this.leaf, doReplace);
    }

    protected final void addVine(int x, int y, int z) {
        this.addBlock(x, y, z, this.vine, false);
    }

    protected final void generateCuboid(WorldGenTree.Vector start, WorldGenTree.Vector area, BlockType block, boolean doReplace) {
        for (int x = (int) start.x; (float) x < (float) ((int) start.x) + area.x; ++x) {
            for (int y = (int) start.y; (float) y < (float) ((int) start.y) + area.y; ++y) {
                for (int z = (int) start.z; (float) z < (float) ((int) start.z) + area.z; ++z) {
                    this.addBlock(x, y, z, block, doReplace);
                }
            }
        }

    }

    protected final void generateCylinder(WorldGenTree.Vector center2, float radius, int height, BlockType block, boolean doReplace) {
        float centerOffset = (float) (this.girth - 1) / 2.0F;
        WorldGenTree.Vector center = new WorldGenTree.Vector(center2.x + centerOffset, center2.y, center2.z + centerOffset);
        WorldGenTree.Vector start = new WorldGenTree.Vector(center.x - radius, center.y, center.z - radius);
        WorldGenTree.Vector area = new WorldGenTree.Vector(radius * 2.0F + 1.0F, (float) height, radius * 2.0F + 1.0F);

        for (int x = (int) start.x; (float) x < (float) ((int) start.x) + area.x; ++x) {
            for (int y = (int) start.y; (float) y < (float) ((int) start.y) + area.y; ++y) {
                for (int z = (int) start.z; (float) z < (float) ((int) start.z) + area.z; ++z) {
                    if (WorldGenTree.Vector.distance(new WorldGenTree.Vector((float) x, (float) y, (float) z), new WorldGenTree.Vector(center.x, (float) y, center.z)) <= (double) radius + 0.01D && (WorldGenTree.Vector.distance(new WorldGenTree.Vector((float) x, (float) y, (float) z), new WorldGenTree.Vector(center.x, (float) y, center.z)) < (double) (radius - 0.5F) || this.rand.nextFloat() >= this.bushiness)) {
                        this.addBlock(x, y, z, block, doReplace);
                    }
                }
            }
        }

    }

    protected final void generateCircle(WorldGenTree.Vector center, float radius, int width, int height, BlockType block, boolean doReplace) {
        this.generateCircle(center, radius, width, height, block, 1.0F, doReplace);
    }

    protected final void generateCircle(WorldGenTree.Vector center2, float radius, int width, int height, BlockType block, float chance, boolean doReplace) {
        float centerOffset = this.girth % 2 == 0 ? 0.5F : 0.0F;
        WorldGenTree.Vector center = new WorldGenTree.Vector(center2.x + centerOffset, center2.y, center2.z + centerOffset);
        WorldGenTree.Vector start = new WorldGenTree.Vector(center.x - radius, center.y, center.z - radius);
        WorldGenTree.Vector area = new WorldGenTree.Vector(radius * 2.0F + 1.0F, (float) height, radius * 2.0F + 1.0F);

        for (int x = (int) start.x; (float) x < (float) ((int) start.x) + area.x; ++x) {
            for (int y = (int) start.y; (float) y < (float) ((int) start.y) + area.y; ++y) {
                for (int z = (int) start.z; (float) z < (float) ((int) start.z) + area.z; ++z) {
                    if (this.rand.nextFloat() <= chance) {
                        double distance = WorldGenTree.Vector.distance(new WorldGenTree.Vector((float) x, (float) y, (float) z), new WorldGenTree.Vector(center.x, (float) y, center.z));
                        if ((double) (radius - (float) width) - 0.01D < distance && distance <= (double) radius + 0.01D) {
                            this.addBlock(x, y, z, block, doReplace);
                        }
                    }
                }
            }
        }

    }

    protected final void generateSphere(WorldGenTree.Vector center2, int radius, BlockType block, boolean doReplace) {
        float centerOffset = (float) (this.girth - 1) / 2.0F;
        WorldGenTree.Vector center = new WorldGenTree.Vector(center2.x + centerOffset, center2.y, center2.z + centerOffset);
        WorldGenTree.Vector start = new WorldGenTree.Vector(center.x - (float) radius, center.y - (float) radius, center.z - (float) radius);
        WorldGenTree.Vector area = new WorldGenTree.Vector((float) (radius * 2 + 1), (float) (radius * 2 + 1), (float) (radius * 2 + 1));

        for (int x = (int) start.x; (float) x < (float) ((int) start.x) + area.x; ++x) {
            for (int y = (int) start.y; (float) y < (float) ((int) start.y) + area.y; ++y) {
                for (int z = (int) start.z; (float) z < (float) ((int) start.z) + area.z; ++z) {
                    if (WorldGenTree.Vector.distance(new WorldGenTree.Vector((float) x, (float) y, (float) z), new WorldGenTree.Vector(center.x, center.y, center.z)) <= (double) radius + 0.01D) {
                        this.addBlock(x, y, z, block, doReplace);
                    }
                }
            }
        }

    }

    static class Vector {
        float x;
        float y;
        float z;

        public Vector(float f, float h, float g) {
            super();
            this.x = f;
            this.y = h;
            this.z = g;
        }

        public static double distance(WorldGenTree.Vector a, WorldGenTree.Vector b) {
            return Math.sqrt(Math.pow((double) (a.x - b.x), 2.0D) + Math.pow((double) (a.y - b.y), 2.0D) + Math.pow((double) (a.z - b.z), 2.0D));
        }
    }
}
