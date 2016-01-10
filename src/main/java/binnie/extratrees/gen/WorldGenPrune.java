package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import forestry.api.arboriculture.ITree;

public class WorldGenPrune extends WorldGenTree {
   public WorldGenPrune(ITree tree) {
      super(tree);
   }

   public void generate() {
      this.generateTreeTrunk(this.height, this.girth);
      int leafSpawn = this.height;
      float width = (float)this.height / this.randBetween(1.7F, 2.1F);
      int bottom = this.randBetween(2, 3);
      this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.4F * width, 1, this.leaf, false);
      this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.7F * width, 1, this.leaf, false);

      while(leafSpawn > bottom) {
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), width, 1, this.leaf, false);
      }

      this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.6F * width, 1, this.leaf, false);
      this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.3F * width, 1, this.leaf, false);
   }

   public void preGenerate() {
      this.height = this.determineHeight(6, 2);
      this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
   }
}
