package binnie.extratrees.village;

import binnie.extratrees.village.ComponentVillageGrove;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import java.util.List;
import java.util.Random;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;

public class VillageHandlerExtraTrees implements IVillageCreationHandler {
   public VillageHandlerExtraTrees() {
      super();
   }

   public static void registerVillageComponents() {
      try {
         MapGenStructureIO.func_143031_a(ComponentVillageGrove.class, "ExtraTrees:Grove");
      } catch (Throwable var1) {
         ;
      }

   }

   public PieceWeight getVillagePieceWeight(Random random, int size) {
      return new PieceWeight(ComponentVillageGrove.class, 505, MathHelper.getRandomIntegerInRange(random, size, 1 + size));
   }

   public Class getComponentClass() {
      return ComponentVillageGrove.class;
   }

   public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5) {
      return ComponentVillageGrove.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
   }
}
