package binnie.extratrees.gen;

import forestry.api.arboriculture.ITree;

public class WorldGenConifer {
    public WorldGenConifer() {
        super();
    }

    public static class Cedar extends WorldGenTree {
        public Cedar(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 3);
            float bottom = (float) this.randBetween(2, 3);
            float width = (float) this.height * this.randBetween(0.7F, 0.75F);
            if (width > 7.0F) {
                width = 7.0F;
            }

            float coneHeight = leafSpawn - bottom;

            while (leafSpawn > bottom) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), (float) this.girth, 1, this.leaf, false);
            }

            for (leafSpawn = (float) (this.height + 3); leafSpawn > bottom; leafSpawn += (float) (1 + this.rand.nextInt(2))) {
                float cone = 1.0F - (leafSpawn - bottom) / coneHeight;
                float radius = (0.7F + this.rand.nextFloat() * 0.3F) * width;
                float xOffset = (-width + this.rand.nextFloat() * 2.0F * width) / 2.0F;
                float yOffset = (-width + this.rand.nextFloat() * 2.0F * width) / 2.0F;
                cone = cone * (2.0F - cone);
                xOffset = xOffset * cone;
                yOffset = yOffset * cone;
                radius = radius * cone;
                if (radius < 2.0F) {
                    radius = 2.0F;
                }

                if (xOffset > radius / 2.0F) {
                    xOffset = radius / 2.0F;
                }

                if (yOffset > radius / 2.0F) {
                    yOffset = radius / 2.0F;
                }

                this.generateCylinder(new WorldGenTree.Vector(xOffset, leafSpawn--, yOffset), 0.7F * radius, 1, this.leaf, false);
                this.generateCylinder(new WorldGenTree.Vector(xOffset, leafSpawn--, yOffset), radius, 1, this.leaf, false);
                this.generateCylinder(new WorldGenTree.Vector(xOffset, leafSpawn--, yOffset), 0.5F * radius, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(6, 2);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class Cypress extends WorldGenTree {
        public Cypress(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 2);
            float bottom = 1.0F;
            float width = (float) this.height * this.randBetween(0.15F, 0.2F);
            if (width > 7.0F) {
                width = 7.0F;
            }

            float coneHeight = leafSpawn - bottom;

            while (leafSpawn > bottom) {
                float radius = 1.0F - (leafSpawn - bottom) / coneHeight;
                radius = radius * (width - 1.0F);
                ++radius;
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), radius, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(6, 2);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class LoblollyPine extends WorldGenTree {
        public LoblollyPine(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 2);
            float bottom = (float) this.height * this.randBetween(0.65F, 0.7F);
            float width = (float) this.height * this.randBetween(0.25F, 0.3F);
            if (width > 7.0F) {
                width = 7.0F;
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.6F * width, 1, this.leaf, false);

            while (leafSpawn > bottom) {
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(6, 2);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class MonkeyPuzzle extends WorldGenTree {
        public MonkeyPuzzle(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 2);
            float bottom = (float) this.randBetween(2, 3);
            float width = (float) this.height * this.randBetween(0.4F, 0.45F);
            if (width > 7.0F) {
                width = 7.0F;
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.35F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.55F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.75F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.9F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 1.0F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.5F * width, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(9, 2);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class WesternHemlock extends WorldGenTree {
        public WesternHemlock(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 6);
            float bottom = (float) this.randBetween(2, 3);
            float width = (float) this.height / this.randBetween(2.0F, 2.5F);
            if (width > 7.0F) {
                width = 7.0F;
            }

            float coneHeight = leafSpawn - bottom;

            while (leafSpawn > bottom) {
                float radius = 1.0F - (leafSpawn - bottom) / coneHeight;
                radius = radius * width;
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), radius, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(7, 3);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }

    public static class Yew extends WorldGenTree {
        public Yew(ITree tree) {
            super(tree);
        }

        public void generate() {
            this.generateTreeTrunk(this.height, this.girth);
            float leafSpawn = (float) (this.height + 2);
            float bottom = (float) this.randBetween(1, 2);
            float width = (float) this.height * this.randBetween(0.7F, 0.75F);
            if (width > 7.0F) {
                width = 7.0F;
            }

            float coneHeight = leafSpawn - bottom;

            while (leafSpawn > bottom) {
                float radius = 1.0F - (leafSpawn - bottom) / coneHeight;
                radius = radius * (2.0F - radius);
                radius = radius * width;
                this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), radius, 1, this.leaf, false);
            }

            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
        }

        public void preGenerate() {
            this.height = this.determineHeight(5, 2);
            this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
        }
    }
}
