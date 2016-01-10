package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenAsh {
    public WorldGenAsh() {
        super();
    }

    public static class CommonAsh extends WorldGenTree {
        public CommonAsh(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 1);
            float bottom = (float) this.randBetween(2, 3);
            float width = (float) this.height * this.randBetween(0.35F, 0.45F);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.8F * width, 1, this.leaf, false);

            while (leafSpawn > bottom) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), this.randBetween(0.95F, 1.05F) * width, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(5, 2);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }
}
