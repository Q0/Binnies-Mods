package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import forestry.api.arboriculture.ITree;

public class WorldGenWalnut {
   public WorldGenWalnut() {
      super();
   }

   public static class BlackWalnut extends WorldGenTree {
      public BlackWalnut(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         int leafSpawn = this.height + 1;
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 2.0F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 3.6F, 1, this.leaf, false);

         while(leafSpawn > this.randBetween(3, 4)) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 3.8F, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 2.7F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 1.8F, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(9, 6);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class Butternut extends WorldGenTree {
      public Butternut(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         int leafSpawn = this.height + 1;
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 2.0F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 3.5F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 4.5F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 5.0F, 1, this.leaf, false);

         while(leafSpawn > 3) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 5.0F, 1, this.leaf, false);
         }

         if(this.rand.nextBoolean()) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 4.0F, 1, this.leaf, false);
         }

      }

      public void preGenerate() {
         this.height = this.determineHeight(6, 3);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }
}
