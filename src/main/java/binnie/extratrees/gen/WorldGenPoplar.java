package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenPoplar {
    public WorldGenPoplar() {
        super();
    }

    public static class Aspen extends WorldGenTree {
        public Aspen(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) this.height;
            float bottom = (float) (this.randBetween(this.height / 2, this.height / 2 + 1) + 1);
            float width = (float) this.height * this.randBetween(0.25F, 0.35F);
            if (width < 2.0F) {
                width = 2.0F;
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.5F * width, 1, this.leaf, false);

            while (leafSpawn > bottom) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), this.randBetween(0.9F, 1.1F) * width, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.8F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(5, 2);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }
}
