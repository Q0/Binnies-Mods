package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenApple {
    public WorldGenApple() {
        super();
    }

    public static class FloweringCrabapple extends WorldGenTree {
        public FloweringCrabapple(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            int leafSpawn = this.height;
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 1.5F, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 3.0F, 1, this.leaf, false);

            while (leafSpawn > 3) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 4.0F, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 2.0F, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(3, 6);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class OrchardApple extends WorldGenTree {
        public OrchardApple(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            int leafSpawn = this.height;
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 1.5F, 1, this.leaf, false);

            while (leafSpawn > 2) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 2.5F, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 2.0F, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(3, 6);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class PrairieCrabapple extends WorldGenTree {
        public PrairieCrabapple(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            int leafSpawn = this.height;
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 1.5F, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 2.5F, 1, this.leaf, false);

            while (leafSpawn > 3) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 3.0F, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 3.0F, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 2.0F, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(4, 4);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class SweetCrabapple extends WorldGenTree {
        public SweetCrabapple(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            int leafSpawn = this.height;
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 1.5F, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 2.5F, 1, this.leaf, false);

            while (leafSpawn > 3) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 3.0F, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 3.5F, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float) (leafSpawn--), 0.0F), 1.5F, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(4, 4);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }
}
