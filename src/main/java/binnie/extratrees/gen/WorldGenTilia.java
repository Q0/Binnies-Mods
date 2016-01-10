package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import forestry.api.arboriculture.ITree;

public class WorldGenTilia {
   public WorldGenTilia() {
      super();
   }

   public static class Basswood extends WorldGenTree {
      public Basswood(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 1);
         float bottom = (float)this.randBetween(2, 3);
         float width = (float)this.height * this.randBetween(0.4F, 0.5F);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.3F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.8F * width, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), this.randBetween(0.95F, 1.05F) * width, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(6, 3);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class CommonLime extends WorldGenTree {
      public CommonLime(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 1);
         float bottom = (float)this.randBetween(2, 3);
         float width = (float)this.height * this.randBetween(0.45F, 0.55F);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.3F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.8F * width, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), this.randBetween(0.95F, 1.05F) * width, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(7, 4);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class WhiteBasswood extends WorldGenTree {
      public WhiteBasswood(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 1);
         float bottom = (float)this.randBetween(2, 3);
         float width = (float)this.height * this.randBetween(0.4F, 0.5F);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.3F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.8F * width, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), this.randBetween(0.95F, 1.05F) * width, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(6, 3);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }
}
