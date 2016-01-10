package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import forestry.api.arboriculture.ITree;

public class WorldGenBeech {
   public WorldGenBeech() {
      super();
   }

   public static class CommonBeech extends WorldGenTree {
      public CommonBeech(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 1);
         float bottom = (float)this.randBetween(2, 3);
         float width = (float)this.height * this.randBetween(0.47F, 0.5F);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.8F * width, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.5F, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 1.0F, 1, this.leaf, false);
      }
   }

   public static class CopperBeech extends WorldGenBeech.CommonBeech {
      public CopperBeech(ITree tree) {
         super(tree);
      }
   }
}
