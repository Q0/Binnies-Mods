package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import forestry.api.arboriculture.ITree;

public class WorldGenSorbus {
   public WorldGenSorbus() {
      super();
   }

   public static class Rowan extends WorldGenTree {
      public Rowan(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         int leafSpawn = this.height + 1;
         float bottom = (float)this.randBetween(2, 3);
         float width = (float)this.height * this.randBetween(0.5F, 0.6F);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.5F * width, 1, this.leaf, false);

         while((float)leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), this.randBetween(0.95F, 1.05F) * width, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.7F * width, 1, this.leaf, false);
      }
   }

   public static class ServiceTree extends WorldGenTree {
      public ServiceTree(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         int leafSpawn = this.height + 1;
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 1.0F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 1.5F, 1, this.leaf, false);

         while(leafSpawn > 2) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 2.4F + this.rand.nextFloat() * 0.7F, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 2.9F, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(8, 6);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Whitebeam extends WorldGenTree {
      public Whitebeam(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         int leafSpawn = this.height + 1;
         float bottom = (float)this.randBetween(2, 3);
         float width = (float)this.height * this.randBetween(0.5F, 0.6F);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.4F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.6F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.8F * width, 1, this.leaf, false);

         while((float)leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), this.randBetween(0.95F, 1.05F) * width, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.7F * width, 1, this.leaf, false);
      }
   }
}
