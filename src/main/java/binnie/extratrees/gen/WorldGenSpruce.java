package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import forestry.api.arboriculture.ITree;

public class WorldGenSpruce {
   public WorldGenSpruce() {
      super();
   }

   public static class AlpineSpruce extends WorldGenTree {
      public AlpineSpruce(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 5);
         float bottom = (float)this.randBetween(2, 3);
         float width = (float)this.height / this.randBetween(2.0F, 2.5F);
         float coneHeight = leafSpawn - bottom;
         leafSpawn = leafSpawn - 2.0F;

         while(leafSpawn > bottom) {
            float radius = 1.0F - (leafSpawn - bottom) / coneHeight;
            radius = radius * radius;
            radius = radius * width;
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), radius, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(5, 3);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class BlackSpruce extends WorldGenTree {
      public BlackSpruce(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 2);
         float bottom = (float)this.randBetween(2, 3);
         float width = (float)this.height / this.randBetween(2.2F, 2.5F);
         float coneHeight = leafSpawn - bottom;

         while(leafSpawn > bottom) {
            float radius = 1.0F - (leafSpawn - bottom) / coneHeight;
            radius = (float)Math.sqrt((double)radius);
            radius = radius * width;
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), radius, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(6, 2);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class GiantSpruce extends WorldGenTree {
      public GiantSpruce(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 3);
         float bottom = (float)this.randBetween(3, 4);
         float width = (float)this.height / this.randBetween(2.5F, 3.0F);
         float coneHeight = leafSpawn - bottom;

         while(leafSpawn > bottom) {
            float radius = 1.0F - (leafSpawn - bottom) / coneHeight;
            radius = 0.15F + 0.85F * radius;
            radius = radius * width;
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), radius, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(15, 4);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class WhiteSpruce extends WorldGenTree {
      public WhiteSpruce(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 2);
         float bottom = (float)this.randBetween(2, 3);
         float width = (float)this.height / this.randBetween(2.2F, 2.5F);
         float coneHeight = leafSpawn - bottom;

         while(leafSpawn > bottom) {
            float radius = 1.0F - (leafSpawn - bottom) / coneHeight;
            radius = (float)Math.sqrt((double)radius);
            radius = radius * width;
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), radius, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(6, 2);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }
}
