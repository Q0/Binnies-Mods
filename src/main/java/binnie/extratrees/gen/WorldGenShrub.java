package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenShrub {
    public WorldGenShrub() {
        super();
    }

    public static class Shrub extends WorldGenTree {
        public Shrub(ITree tree) {
            super(tree);
        }

        public void generate() {
            float leafSpawn = (float) this.height;
            float width = (float) this.height * this.randBetween(0.7F, 0.9F);
            if (width < 1.5F) {
                width = 1.5F;
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.8F * width, 1, this.leaf, false);

            while (leafSpawn >= 0.0F) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            }

        }

        public void preGenerate() {
            this.minHeight = 1;
            this.height = this.determineHeight(1, 1);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }
}
