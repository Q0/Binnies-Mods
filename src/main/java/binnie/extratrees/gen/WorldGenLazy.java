package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import forestry.api.arboriculture.ITree;

public class WorldGenLazy {
   public WorldGenLazy() {
      super();
   }

   public static class Tree extends WorldGenTree {
      public Tree(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)this.height;
         float bottom = (float)(this.height / 2 - 1);
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
}
