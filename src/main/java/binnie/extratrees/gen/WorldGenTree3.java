package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenTree3 {
    public WorldGenTree3() {
        super();
    }

    public static class AcornOak extends WorldGenTree {
        public AcornOak(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 1);
            float bottom = 3.0F;
            float width = (float) this.height * this.randBetween(0.45F, 0.5F);
            if (width < 2.0F) {
                width = 2.0F;
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width * 0.4F, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width * 0.7F, 1, this.leaf, false);

            while (leafSpawn > bottom) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.2F, 1, this.leaf, false);
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.5F, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(6, 2);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class Elder extends WorldGenTree {
        public Elder(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 1);
            float bottom = 3.0F;
            float width = (float) this.height * this.randBetween(0.55F, 0.6F);
            if (width < 2.0F) {
                width = 2.0F;
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.0F, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.4F, 1, this.leaf, false);

            while (leafSpawn > bottom) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.4F, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.0F, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(4, 1);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class Elm extends WorldGenTree {
        public Elm(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 1);
            float bottom = (float) this.randBetween(2, 3);
            float width = (float) this.height * this.randBetween(0.45F, 0.5F);
            if (width < 2.0F) {
                width = 2.0F;
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.0F, 1, this.leaf, false);

            while (leafSpawn > bottom) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.5F, 1, this.leaf, false);
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.5F, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(6, 2);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class Hawthorn extends WorldGenTree {
        public Hawthorn(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 1);
            float bottom = (float) this.randBetween(1, 2);
            float width = (float) this.height * this.randBetween(0.4F, 0.45F);
            if (width < 1.5F) {
                width = 1.5F;
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.8F * width, 1, this.leaf, false);

            while (leafSpawn > bottom) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.5F, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(4, 1);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class Hazel extends WorldGenTree {
        public Hazel(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 1);
            float bottom = 3.0F;
            float width = (float) this.height * this.randBetween(0.45F, 0.5F);
            if (width < 2.5F) {
                width = 2.5F;
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.0F, 1, this.leaf, false);

            while (leafSpawn > bottom) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.3F, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.6F, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.2F, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(5, 1);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class Hornbeam extends WorldGenTree {
        public Hornbeam(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 1);
            float bottom = 2.0F;
            float width = (float) this.height * this.randBetween(0.55F, 0.6F);
            if (width < 1.5F) {
                width = 1.5F;
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.3F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);

            while (leafSpawn > bottom) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            }

        }

        public void preGenerate() {
            this.height = this.determineHeight(5, 2);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class Pecan extends WorldGenTree {
        public Pecan(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 1);
            float bottom = 2.0F;
            float width = (float) this.height * this.randBetween(0.6F, 0.65F);
            if (width < 2.0F) {
                width = 2.0F;
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.3F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.6F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.8F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.9F * width, 1, this.leaf, false);

            while (leafSpawn > bottom) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.6F * width, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(6, 2);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class Sallow extends WorldGenTree {
        public Sallow(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 1);
            float bottom = 2.0F;
            float width = (float) this.height * this.randBetween(0.6F, 0.65F);
            if (width < 2.0F) {
                width = 2.0F;
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.3F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.6F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.8F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.9F * width, 1, this.leaf, false);

            while (leafSpawn > bottom) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.6F * width, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(4, 1);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class Sycamore extends WorldGenTree {
        public Sycamore(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) this.height;
            float bottom = (float) this.randBetween(2, 3);
            float width = (float) this.height * this.randBetween(0.35F, 0.4F);
            if (width < 2.0F) {
                width = 2.0F;
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.3F, 1, this.leaf, false);

            while (leafSpawn > bottom) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.6F, 1, this.leaf, false);
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.8F, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(6, 2);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }
}
