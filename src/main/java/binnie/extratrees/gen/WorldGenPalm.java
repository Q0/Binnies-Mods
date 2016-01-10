package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import forestry.api.arboriculture.ITree;

public class WorldGenPalm {
   public WorldGenPalm() {
      super();
   }

   public static class Coconut extends WorldGenTree {
      public Coconut(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)this.height;
         float width = (float)this.height * this.randBetween(0.35F, 0.4F);
         if((double)width < 1.2D) {
            width = 1.55F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.0F, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.6F, 1, this.leaf, false);
      }

      public void preGenerate() {
         this.height = this.determineHeight(6, 1);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }
}
