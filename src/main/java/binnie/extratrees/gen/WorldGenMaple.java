package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import forestry.api.arboriculture.ITree;

public class WorldGenMaple {
   public WorldGenMaple() {
      super();
   }

   public static class RedMaple extends WorldGenTree {
      public RedMaple(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 2);
         float bottom = (float)this.randBetween(1, 2);
         float bottom2 = ((float)this.height + bottom) / 2.0F;
         float width = 2.0F;
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);

         while(leafSpawn > bottom2) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.4F, 1, this.leaf, false);
         }

         width = (float)((double)width + 0.6D);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.4F, 1, this.leaf, false);
         }

         if(leafSpawn == bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.6F, 1, this.leaf, false);
         }

      }

      public void preGenerate() {
         this.height = this.determineHeight(5, 2);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }
}
