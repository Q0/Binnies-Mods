package binnie.extratrees.village;

import forestry.apiculture.worldgen.ComponentVillageBeeHouse;
import java.util.List;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;

public class ComponentVillageGrove extends Village {
   public ComponentVillageGrove() {
      super();
   }

   public ComponentVillageGrove(Start start, int p_i2097_2_, Random p_i2097_3_, StructureBoundingBox boundingBox, int coordBaseMode) {
      super(start, p_i2097_2_);
      this.coordBaseMode = coordBaseMode;
      this.boundingBox = boundingBox;
   }

   public static StructureBoundingBox func_74904_a(Start p_74904_0_, List p_74904_1_, Random p_74904_2_, int p_74904_3_, int p_74904_4_, int p_74904_5_, int p_74904_6_) {
      StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74904_3_, p_74904_4_, p_74904_5_, 0, 0, 0, 3, 3, 3, p_74904_6_);
      return StructureComponent.findIntersecting(p_74904_1_, structureboundingbox) != null?null:structureboundingbox;
   }

   public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
      if(this.field_143015_k < 0) {
         this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);
         if(this.field_143015_k < 0) {
            return true;
         }

         this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
      }

      System.out.println("GROVE2");
      this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 3, 3, 3, Blocks.air, Blocks.air, false);
      this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 3, 3, 3, Blocks.stone, Blocks.stone, true);
      return true;
   }

   public static ComponentVillageBeeHouse buildComponent(Start startPiece, List par1List, Random random, int par3, int par4, int par5, int par6, int par7) {
      System.out.println("GROVE1");
      StructureBoundingBox bbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 9, 9, 10, par6);
      return canVillageGoDeeper(bbox) && StructureComponent.findIntersecting(par1List, bbox) == null?new ComponentVillageBeeHouse(startPiece, par7, random, bbox, par6):null;
   }
}
