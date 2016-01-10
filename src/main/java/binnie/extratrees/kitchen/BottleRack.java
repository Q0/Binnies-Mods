package binnie.extratrees.kitchen;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.kitchen.KitchenMachine;
import binnie.extratrees.machines.ExtraTreeMachine;

public class BottleRack {
   public BottleRack() {
      super();
   }

   public static class PackageBottleRack extends KitchenMachine.PackageKitchenMachine implements IMachineInformation {
      public PackageBottleRack() {
         super("bottleRack", ExtraTreeTexture.lumbermillTexture);
      }

      public void createMachine(Machine machine) {
         new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.KitchenBottleRack);
         ComponentTankContainer inventory = new ComponentTankContainer(machine);

         for(int x = 0; x < 36; ++x) {
            inventory.addTank(x, "input", 1000);
            inventory.getTankSlot(x);
         }

      }
   }
}
