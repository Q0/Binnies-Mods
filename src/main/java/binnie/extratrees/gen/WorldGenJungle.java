package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import forestry.api.arboriculture.ITree;

public class WorldGenJungle {
   public WorldGenJungle() {
      super();
   }

   public static class BrazilNut extends WorldGenTree {
      public BrazilNut(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 1);
         float bottom = (float)(this.height - 3);
         float width = (float)this.height * this.randBetween(0.25F, 0.3F);
         if(width < 2.0F) {
            width = 2.0F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.0F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.5F, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.5F, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(7, 1);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Brazilwood extends WorldGenTree {
      public Brazilwood(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)this.height;
         float bottom = 1.0F;
         float width = (float)this.height * this.randBetween(0.15F, 0.2F);
         if(width < 2.0F) {
            width = 2.0F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.8F, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
         }

      }

      public void preGenerate() {
         this.height = this.determineHeight(4, 2);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Coffee extends WorldGenTree {
      public Coffee(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)this.height;
         float bottom = 1.0F;
         float width = (float)this.height * this.randBetween(0.25F, 0.3F);
         if(width < 2.0F) {
            width = 2.0F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.5F, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.3F, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(3, 1);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Logwood extends WorldGenTree {
      public Logwood(ITree tree) {
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
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.7F, 1, this.leaf, false);
         }

      }

      public void preGenerate() {
         this.height = this.determineHeight(4, 2);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class OldFustic extends WorldGenTree {
      public OldFustic(ITree tree) {
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

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.7F, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.5F, 1, this.leaf, false);
         }

      }

      public void preGenerate() {
         this.height = this.determineHeight(5, 2);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class OsangeOsange extends WorldGenTree {
      public OsangeOsange(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)this.height;
         float bottom = (float)this.randBetween(1, 2);
         float width = (float)this.height * this.randBetween(0.2F, 0.25F);
         if(width < 2.0F) {
            width = 2.0F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.0F, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.6F, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(5, 1);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Purpleheart extends WorldGenTree {
      public Purpleheart(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 1);
         float bottom = (float)(this.height - 3);
         float width = (float)this.height * this.randBetween(0.2F, 0.25F);
         if(width < 2.0F) {
            width = 2.0F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.0F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.5F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.7F, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(7, 2);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Rosewood extends WorldGenTree {
      public Rosewood(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 1);
         float bottom = (float)this.randBetween(1, 2);
         float width = (float)this.height * this.randBetween(0.2F, 0.25F);
         if(width < 2.0F) {
            width = 2.0F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.0F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.5F, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.7F, 1, this.leaf, false);
         }

      }

      public void preGenerate() {
         this.height = this.determineHeight(6, 2);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Shrub15 extends WorldGenTree {
      public Shrub15(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)this.height;
         float bottom = 1.0F;
         float width = (float)this.height * this.randBetween(0.15F, 0.2F);
         if(width < 1.5F) {
            width = 1.5F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.8F, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
         }

      }

      public void preGenerate() {
         this.height = this.determineHeight(4, 1);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }
}
