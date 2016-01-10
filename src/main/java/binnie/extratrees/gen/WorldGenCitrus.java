package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import forestry.api.arboriculture.ITree;

public class WorldGenCitrus extends WorldGenTree {
   public WorldGenCitrus(ITree tree) {
      super(tree);
   }

   public void generate() {
      this.generateTreeTrunk(this.height, this.girth);
      int leafSpawn = this.height;
      float width = (float)this.height / this.randBetween(1.1F, 1.5F);
      int bottom = this.randBetween(1, 2);
      this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.4F * width, 1, this.leaf, false);
      this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.7F * width, 1, this.leaf, false);

      while(leafSpawn > bottom) {
         this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), width, 1, this.leaf, false);
      }

      this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.4F * width, 1, this.leaf, false);
   }

   public void preGenerate() {
      this.minHeight = this.randBetween(2, 3);
      this.height = this.determineHeight(6, 1);
      this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
   }
}
