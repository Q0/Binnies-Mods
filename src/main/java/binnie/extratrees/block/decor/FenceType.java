package binnie.extratrees.block.decor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FenceType {
   public int size;
   public boolean solid;
   public boolean embossed;

   public FenceType(int size, boolean solid, boolean embedded) {
      super();
      this.size = size;
      this.solid = solid;
      this.embossed = embedded;
   }

   public String getPrefix() {
      return (this.size == 0?"":(this.size == 1?"Full ":"Low ")) + (this.solid?"Solid ":"") + (this.embossed?"Embedded ":"");
   }

   public int ordinal() {
      return this.size + ((this.solid?1:0) << 2) + ((this.embossed?1:0) << 3);
   }

   public FenceType(int meta) {
      super();
      this.size = meta & 3;
      this.solid = (meta >> 2 & 1) > 0;
      this.embossed = (meta >> 3 & 1) > 0;
   }

   public static Collection values() {
      List<FenceType> list = new ArrayList();

      for(int size = 0; size < 3; ++size) {
         for(boolean solid : new boolean[]{false, true}) {
            for(boolean embedded : new boolean[]{false, true}) {
               list.add(new FenceType(size, solid, embedded));
            }
         }
      }

      return list;
   }

   public boolean isPlain() {
      return this.size == 0 && !this.embossed && !this.solid;
   }

   public boolean equals(Object obj) {
      if(!(obj instanceof FenceType)) {
         return super.equals(obj);
      } else {
         FenceType o = (FenceType)obj;
         return o.size == this.size && o.embossed == this.embossed && o.solid == this.solid;
      }
   }
}
