package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenHolly {
    public WorldGenHolly() {
        super();
    }

    public static class Holly extends WorldGenTree {
        public Holly(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 1);
            float bottom = 1.0F;
            float width = (float) this.height * this.randBetween(0.4F, 0.45F);
            if (width < 1.5F) {
                width = 1.5F;
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.8F * width, 1, this.leaf, false);

            while (leafSpawn > bottom) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            }

            WorldGenTree.Vector var10001 = new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F);
            width = 1.0F;
            this.generateCylinder(var10001, 1.0F, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(4, 2);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }
}
