package binnie.core.network.packet;

import java.util.ArrayList;
import java.util.List;

public class MachinePayload {
   private List intPayload = new ArrayList();
   private List floatPayload = new ArrayList();
   private List stringPayload = new ArrayList();
   private int id = 0;

   public MachinePayload(int id) {
      super();
      this.id = id;
      this.intPayload.clear();
      this.floatPayload.clear();
      this.stringPayload.clear();
   }

   public MachinePayload() {
      super();
   }

   public void addInteger(int a) {
      this.intPayload.add(Integer.valueOf(a));
   }

   public void addFloat(float a) {
      this.floatPayload.add(Float.valueOf(a));
   }

   public void addString(String a) {
      this.stringPayload.add(a);
   }

   public int getInteger() {
      return ((Integer)this.intPayload.remove(0)).intValue();
   }

   public float getFloat() {
      return ((Float)this.floatPayload.remove(0)).floatValue();
   }

   public String getString() {
      return (String)this.stringPayload.remove(0);
   }

   public void append(MachinePayload other) {
      if(other != null) {
         this.intPayload.addAll(other.intPayload);
         this.floatPayload.addAll(other.floatPayload);
         this.stringPayload.addAll(other.stringPayload);
      }
   }

   public boolean isEmpty() {
      return this.intPayload.isEmpty() && this.floatPayload.isEmpty() && this.stringPayload.isEmpty();
   }

   public int getID() {
      return this.id;
   }

   public void setID(int readInt) {
      this.id = readInt;
   }
}
