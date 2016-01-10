package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import forestry.api.arboriculture.ITree;

public class WorldGenTree2 {
   public WorldGenTree2() {
      super();
   }

   public static class Box extends WorldGenTree {
      public Box(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)this.height;
         float bottom = 0.0F;
         float width = 1.5F;

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
         }

      }

      public void preGenerate() {
         this.height = this.determineHeight(3, 1);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Clove extends WorldGenTree {
      public Clove(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)this.height;
         float bottom = 2.0F;
         float width = (float)this.height * this.randBetween(0.25F, 0.3F);
         if(width < 2.0F) {
            width = 2.0F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.0F, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.6F, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.4F, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(4, 1);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Gingko extends WorldGenTree {
      public Gingko(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 2);
         float bottom = 2.0F;
         float width = (float)this.height * this.randBetween(0.55F, 0.6F);
         if(width > 7.0F) {
            width = 7.0F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 2.0F, 1, this.leaf, false);

         while(leafSpawn > bottom * 2.0F + 1.0F) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.5F, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.9F, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.9F, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(6, 2);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Iroko extends WorldGenTree {
      public Iroko(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)this.height;
         float bottom = (float)this.randBetween(2, 3);
         float width = (float)this.height * this.randBetween(0.45F, 0.5F);
         if(width < 2.5F) {
            width = 2.5F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width * 0.25F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width * 0.5F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width * 0.7F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 2.0F, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(6, 2);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Locust extends WorldGenTree {
      public Locust(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 1);
         float bottom = 2.0F;
         float width = (float)this.height * this.randBetween(0.35F, 0.4F);
         if(width < 2.0F) {
            width = 2.0F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.0F, 1, this.leaf, false);

         while(leafSpawn > bottom + 1.0F) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.3F, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.7F, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.4F, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(6, 2);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Olive extends WorldGenTree {
      public Olive(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)this.height;
         float bottom = (float)this.randBetween(2, 3);
         float width = (float)this.height * this.randBetween(0.35F, 0.4F);
         if((double)width < 1.2D) {
            width = 1.55F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.0F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.5F, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(4, 1);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Pear extends WorldGenTree {
      public Pear(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)this.height;
         float bottom = (float)this.randBetween(1, 2);
         float width = (float)this.height * this.randBetween(0.25F, 0.3F);
         if(width < 2.0F) {
            width = 2.0F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.0F, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
         }

      }

      public void preGenerate() {
         this.height = this.determineHeight(3, 1);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Sweetgum extends WorldGenTree {
      public Sweetgum(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 1);
         float bottom = (float)this.randBetween(1, 2);
         float width = (float)this.height * this.randBetween(0.7F, 0.75F);
         if(width > 7.0F) {
            width = 7.0F;
         }

         float coneHeight = leafSpawn - bottom;

         while(leafSpawn > bottom) {
            float radius = 1.0F - (leafSpawn - bottom) / coneHeight;
            radius = radius * (2.0F - radius);
            radius = radius * width;
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), radius, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(5, 1);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }
}
