package binnie.core.machines.inventory;

import binnie.core.machines.inventory.SidedAccess;
import binnie.core.machines.inventory.Validator;
import binnie.core.util.IValidator;
import forestry.api.core.INBTTagable;
import java.util.Collection;
import java.util.EnumSet;
import net.minecraftforge.common.util.ForgeDirection;

abstract class BaseSlot implements INBTTagable, IValidator {
   private SidedAccess access = new SidedAccess();
   Validator validator = null;
   private boolean readOnly = false;
   private int index;
   protected String unlocName = "";

   public BaseSlot(int index, String unlocName) {
      super();
      this.setIndex(index);
      this.setUnlocalisedName(unlocName);
   }

   public void setReadOnly() {
      this.readOnly = true;
      this.forbidInsertion();
   }

   public boolean isValid(Object item) {
      return item == null?true:(this.validator != null?this.validator.isValid(item):true);
   }

   public abstract Object getContent();

   public abstract void setContent(Object var1);

   public void setValidator(Validator val) {
      this.validator = val;
   }

   public boolean isEmpty() {
      return this.getContent() == null;
   }

   public boolean isReadOnly() {
      return this.readOnly;
   }

   public int getIndex() {
      return this.index;
   }

   private void setIndex(int index) {
      this.index = index;
   }

   public boolean canInsert() {
      return !this.access.getInsertionSides().isEmpty();
   }

   public boolean canExtract() {
      return !this.access.getExtractionSides().isEmpty();
   }

   public void forbidInteraction() {
      this.forbidInsertion();
      this.forbidExtraction();
   }

   public void setInputSides(EnumSet sides) {
      for(ForgeDirection side : EnumSet.complementOf(sides)) {
         if(side != ForgeDirection.UNKNOWN) {
            this.access.setInsert(side, false);
         }
      }

   }

   public void setOutputSides(EnumSet sides) {
      for(ForgeDirection side : EnumSet.complementOf(sides)) {
         if(side != ForgeDirection.UNKNOWN) {
            this.access.setExtract(side, false);
         }
      }

   }

   public void forbidExtraction() {
      this.access.setExtract(false);
      this.access.forbidExtractChange();
   }

   public void forbidInsertion() {
      this.access.setInsert(false);
      this.access.forbidInsertChange();
   }

   public boolean canInsert(ForgeDirection dir) {
      return this.access.canInsert(dir);
   }

   public boolean canExtract(ForgeDirection dir) {
      return this.access.canExtract(dir);
   }

   public Collection getInputSides() {
      return this.access.getInsertionSides();
   }

   public Collection getOutputSides() {
      return this.access.getExtractionSides();
   }

   public void setUnlocalisedName(String name) {
      this.unlocName = name;
   }

   public abstract String getName();

   public Validator getValidator() {
      return this.validator;
   }
}
