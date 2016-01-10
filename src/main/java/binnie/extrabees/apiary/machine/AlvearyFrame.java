package binnie.extrabees.apiary.machine;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.SlotValidator;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IHiveFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AlvearyFrame {
   public static int slotFrame = 0;

   public AlvearyFrame() {
      super();
   }

   public static class ComponentFrameModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
      public ComponentFrameModifier(Machine machine) {
         super(machine);
      }

      public void wearOutEquipment(int amount) {
         if(this.getHiveFrame() != null) {
            World world = this.getMachine().getTileEntity().getWorldObj();
            int wear = Math.round((float)(amount * 5) * Binnie.Genetics.getBeeRoot().getBeekeepingMode(world).getWearModifier());
            this.getInventory().setInventorySlotContents(AlvearyFrame.slotFrame, this.getHiveFrame().frameUsed((IBeeHousing)((TileExtraBeeAlveary)this.getMachine().getTileEntity()).getCentralTE(), this.getInventory().getStackInSlot(AlvearyFrame.slotFrame), (IBee)null, wear));
         }
      }

      public IHiveFrame getHiveFrame() {
         return this.getInventory().getStackInSlot(AlvearyFrame.slotFrame) != null?(IHiveFrame)this.getInventory().getStackInSlot(AlvearyFrame.slotFrame).getItem():null;
      }

      public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
         return this.getHiveFrame() == null?1.0F:this.getHiveFrame().getTerritoryModifier(genome, currentModifier);
      }

      public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
         return this.getHiveFrame() == null?1.0F:this.getHiveFrame().getMutationModifier(genome, mate, currentModifier);
      }

      public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
         return this.getHiveFrame() == null?1.0F:this.getHiveFrame().getLifespanModifier(genome, mate, currentModifier);
      }

      public float getProductionModifier(IBeeGenome genome, float currentModifier) {
         return this.getHiveFrame() == null?1.0F:this.getHiveFrame().getProductionModifier(genome, currentModifier);
      }

      public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
         return this.getHiveFrame() == null?1.0F:this.getHiveFrame().getFloweringModifier(genome, currentModifier);
      }
   }

   public static class PackageAlvearyFrame extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
      public PackageAlvearyFrame() {
         super("frame", ExtraBeeTexture.AlvearyFrame.getTexture(), false);
      }

      public void createMachine(Machine machine) {
         new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyFrame);
         ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
         inventory.addSlot(AlvearyFrame.slotFrame, "frame");
         inventory.getSlot(AlvearyFrame.slotFrame).setValidator(new AlvearyFrame.SlotValidatorFrame());
         new AlvearyFrame.ComponentFrameModifier(machine);
      }
   }

   public static class SlotValidatorFrame extends SlotValidator {
      public SlotValidatorFrame() {
         super(SlotValidator.IconFrame);
      }

      public boolean isValid(ItemStack itemStack) {
         return itemStack != null && itemStack.getItem() instanceof IHiveFrame;
      }

      public String getTooltip() {
         return "Hive Frames";
      }
   }
}
