package binnie.botany.farm;

public class Vect {
   public int x;
   public int y;
   public int z;

   public Vect(int[] dim) {
      super();
      if(dim.length != 3) {
         throw new RuntimeException("Cannot instantiate a vector with less or more than 3 points.");
      } else {
         this.x = dim[0];
         this.y = dim[1];
         this.z = dim[2];
      }
   }

   public Vect(int x, int y, int z) {
      super();
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vect add(Vect other) {
      Vect result = new Vect(this.x, this.y, this.z);
      result.x += other.x;
      result.y += other.y;
      result.z += other.z;
      return result;
   }

   public Vect multiply(float factor) {
      Vect result = new Vect(this.x, this.y, this.z);
      result.x = (int)((float)result.x * factor);
      result.y = (int)((float)result.y * factor);
      result.z = (int)((float)result.z * factor);
      return result;
   }

   public String toString() {
      return String.format("%sx%sx%s;", new Object[]{Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z)});
   }

   public int hashCode() {
      int prime = 31;
      int result = 1;
      result = 31 * result + this.x;
      result = 31 * result + this.y;
      result = 31 * result + this.z;
      return result;
   }

   public boolean equals(Object obj) {
      if(this == obj) {
         return true;
      } else if(obj == null) {
         return false;
      } else if(this.getClass() != obj.getClass()) {
         return false;
      } else {
         Vect other = (Vect)obj;
         return this.x != other.x?false:(this.y != other.y?false:this.z == other.z);
      }
   }
}
