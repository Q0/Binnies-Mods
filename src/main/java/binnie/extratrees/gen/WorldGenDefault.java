package binnie.extratrees.gen;

import binnie.extratrees.gen.WorldGenTree;
import binnie.extratrees.genetics.ExtraTreeSpecies;
import binnie.extratrees.worldgen.BlockType;
import binnie.extratrees.worldgen.BlockTypeLeaf;
import binnie.extratrees.worldgen.BlockTypeLog;
import forestry.api.arboriculture.ITree;

public class WorldGenDefault extends WorldGenTree {
   public WorldGenDefault(ITree tree) {
      super(tree);
   }

   public void generate() {
      this.generateTreeTrunk(this.height, this.girth);
      int leafSpawn = this.height + 1;
      this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 1.0F, 1, this.leaf, false);
      this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 1.5F, 1, this.leaf, false);
      this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 2.9F, 1, this.leaf, false);
      this.generateCylinder(new WorldGenTree.Vector(0.0F, (float)(leafSpawn--), 0.0F), 2.9F, 1, this.leaf, false);
   }

   public void preGenerate() {
      this.height = this.determineHeight(5, 2);
      this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
   }

   public BlockType getLeaf() {
      return new BlockTypeLeaf();
   }

   public BlockType getWood() {
      return new BlockTypeLog(((ExtraTreeSpecies)this.tree.getGenome().getPrimary()).getLog());
   }
}
