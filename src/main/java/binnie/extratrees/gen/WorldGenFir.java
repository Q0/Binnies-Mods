package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import forestry.api.arboriculture.ITree;

public class WorldGenFir {
   public WorldGenFir() {
      super();
   }

   public static class BalsamFir extends WorldGenTree {
      public BalsamFir(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 2);
         float bottom = 1.0F;
         float width = (float)this.height / this.randBetween(2.0F, 2.5F);
         if(width > 7.0F) {
            width = 7.0F;
         }

         float coneHeight = leafSpawn - bottom;

         while(leafSpawn > bottom) {
            float radius = 1.0F - (leafSpawn - bottom) / coneHeight;
            radius = radius * width;
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

   public static class DouglasFir extends WorldGenTree {
      public DouglasFir(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 1);
         float patchyBottom = (float)(this.height / 2);
         float bottom = (float)this.randBetween(3, 4);
         float width = (float)this.height * this.randBetween(0.35F, 0.4F);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.8F * width, 1, this.leaf, false);
         this.bushiness = 0.1F;

         while(leafSpawn > patchyBottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), this.randBetween(0.9F, 1.1F) * width, 1, this.leaf, false);
         }

         this.bushiness = 0.5F;

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), this.randBetween(0.7F, 1.0F) * width, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.3F * width, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(7, 3);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class SilverFir extends WorldGenTree {
      public SilverFir(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 2);
         float bottom = (float)this.randBetween(2, 3);
         float width = (float)this.height / this.randBetween(2.5F, 3.0F);
         if(width > 7.0F) {
            width = 7.0F;
         }

         float coneHeight = leafSpawn - bottom;

         while(leafSpawn > bottom) {
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
}
