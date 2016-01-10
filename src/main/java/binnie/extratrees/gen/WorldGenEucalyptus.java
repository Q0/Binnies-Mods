package binnie.extratrees.gen;

import binnie.extratrees.block.ILogType;
import binnie.extratrees.gen.WorldGenTree;
import binnie.extratrees.worldgen.BlockTypeLog;
import forestry.api.arboriculture.ITree;

public class WorldGenEucalyptus {
   public WorldGenEucalyptus() {
      super();
   }

   public static class RainbowGum extends WorldGenTree {
      public RainbowGum(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 2);
         float bottom = this.randBetween(0.5F, 0.6F) * (float)this.height;
         float width = (float)this.height * this.randBetween(0.15F, 0.2F);
         if(width < 1.5F) {
            width = 1.5F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width, 1, this.leaf, false);
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), width - 0.5F, 1, this.leaf, false);
         }

      }

      public void preGenerate() {
         this.height = this.determineHeight(7, 3);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class RoseGum extends WorldGenTree {
      public RoseGum(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         int offset = (this.girth - 1) / 2;

         for(int x = 0; x < this.girth; ++x) {
            for(int y = 0; y < this.girth; ++y) {
               for(int i = 0; i < 2; ++i) {
                  this.addBlock(x - offset, i, y - offset, new BlockTypeLog(ILogType.ExtraTreeLog.Eucalyptus2), true);
               }
            }
         }

         float leafSpawn = (float)(this.height + 2);
         float bottom = this.randBetween(0.4F, 0.5F) * (float)this.height;
         float width = (float)this.height * this.randBetween(0.05F, 0.1F);
         if(width < 1.5F) {
            width = 1.5F;
         }

         this.bushiness = 0.5F;
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);

         while(leafSpawn > bottom) {
            this.bushiness = 0.1F;
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), this.randBetween(0.9F, 1.1F) * width, 1, this.leaf, false);
         }

      }

      public void preGenerate() {
         this.height = this.determineHeight(9, 3);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }

   public static class SwampGum extends WorldGenTree {
      public SwampGum(ITree tree) {
         super(tree);
      }

      public void generate() {
         this.generateTreeTrunk(this.height, this.girth);
         float leafSpawn = (float)(this.height + 2);
         float weakerBottm = this.randBetween(0.5F, 0.6F) * (float)this.height;
         float bottom = this.randBetween(0.4F, 0.5F) * (float)this.height;
         float width = (float)this.height * this.randBetween(0.15F, 0.2F);
         if(width > 7.0F) {
            width = 7.0F;
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.4F * width, 1, this.leaf, false);
         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.7F * width, 1, this.leaf, false);
         this.bushiness = 0.3F;

         while(leafSpawn > weakerBottm) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), this.randBetween(0.9F, 1.0F) * width, 1, this.leaf, false);
         }

         this.bushiness = 0.6F;

         while(leafSpawn > bottom) {
            this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), this.randBetween(0.8F, 0.9F) * width, 1, this.leaf, false);
         }

         this.generateCylinder(new WorldGenTree.Vector(0.0F, leafSpawn--, 0.0F), 0.5F * width, 1, this.leaf, false);

         for(int i = 0; i < 5; ++i) {
            this.generateSphere(new WorldGenTree.Vector((float)this.randBetween(-1, 1), leafSpawn--, (float)this.randBetween(-1, 1)), this.randBetween(1, 2), this.leaf, false);
         }

      }

      public void preGenerate() {
         this.height = this.determineHeight(14, 3);
         this.girth = this.determineGirth(this.tree.getGirth(this.world, this.startX, this.startY, this.startZ));
      }
   }
}
