package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import forestry.api.arboriculture.ITree;

public class WorldGenBanana extends WorldGenTree {
   public WorldGenBanana(ITree tree) {
      super(tree);
   }

   public void generate() {
      this.bushiness = 0.9F;
      this.generateTreeTrunk(this.height, this.girth);
      int leafSpawn = this.height + 1;
      float width = (float)this.height / this.randBetween(1.8F, 2.0F);
      int bottom = this.randBetween(3, 4);
      this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.4F * width, 1, this.leaf, false);
      this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), width, 1, this.leaf, false);
      this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 0.9F * width, 1, this.leaf, false);
   }

   public void preGenerate() {
      this.height = this.determineHeight(6, 1);
      this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
   }
}
