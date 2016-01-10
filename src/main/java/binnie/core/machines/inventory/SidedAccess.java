package binnie.core.machines.inventory;

import binnie.core.machines.inventory.AccessDirection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraftforge.common.util.ForgeDirection;

class SidedAccess {
   private Map accesses = new HashMap();
   private AccessDirection base = AccessDirection.Both;
   private boolean insertLocked = false;
   private boolean extractLocked = false;

   public SidedAccess() {
      super();
   }

   public AccessDirection getAccess(ForgeDirection side) {
      return this.accesses.containsKey(side)?(AccessDirection)this.accesses.get(side):this.base;
   }

   public boolean canInsert(ForgeDirection side) {
      return this.getAccess(side).canInsert();
   }

   public boolean canExtract(ForgeDirection side) {
      return this.getAccess(side).canExtract();
   }

   public boolean canAccess(ForgeDirection side) {
      return this.getAccess(side).canAccess();
   }

   public boolean canChangeInsert() {
      return !this.insertLocked;
   }

   public boolean canChangeExtract() {
      return !this.extractLocked;
   }

   public void forbidInsertChange() {
      this.insertLocked = true;
   }

   public void forbidExtractChange() {
      this.extractLocked = true;
   }

   public Collection getInsertionSides() {
      List<ForgeDirection> dirs = new ArrayList();

      for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
         if(this.getAccess(dir).canInsert()) {
            dirs.add(dir);
         }
      }

      return dirs;
   }

   public Collection getExtractionSides() {
      List<ForgeDirection> dirs = new ArrayList();

      for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
         if(this.getAccess(dir).canExtract()) {
            dirs.add(dir);
         }
      }

      return dirs;
   }

   public void setInsert(ForgeDirection side, boolean b) {
      if(this.getAccess(side).canInsert() != b) {
         this.accesses.put(side, this.getAccess(side).changeInsert(b));
      }

   }

   public void setExtract(ForgeDirection side, boolean b) {
      if(this.getAccess(side).canExtract() != b) {
         this.accesses.put(side, this.getAccess(side).changeExtract(b));
      }

   }

   public void setInsert(boolean b) {
      if(this.base.canInsert() != b) {
         this.base = this.base.changeInsert(b);
      }

   }

   public void setExtract(boolean b) {
      if(this.base.canExtract() != b) {
         this.base = this.base.changeExtract(b);
      }

   }
}
