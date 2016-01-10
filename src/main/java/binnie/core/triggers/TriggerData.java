package binnie.core.triggers;

import buildcraft.api.statements.ITriggerExternal;
import java.util.Map.Entry;

public class TriggerData implements Entry {
   private final ITriggerExternal key;
   private Boolean value;

   public TriggerData(ITriggerExternal key, Boolean value) {
      super();
      if(key == null) {
         throw new NullPointerException();
      } else {
         this.key = key;
         this.value = value;
      }
   }

   public ITriggerExternal getKey() {
      return this.key;
   }

   public Boolean getValue() {
      return this.value;
   }

   public Boolean setValue(Boolean value) {
      Boolean old = this.value;
      this.value = value;
      return old;
   }
}
