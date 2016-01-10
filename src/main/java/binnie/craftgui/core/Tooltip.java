package binnie.craftgui.core;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class Tooltip {
   List tooltip = new ArrayList();
   Tooltip.ITooltipType type = Tooltip.Type.Standard;
   public int maxWidth = 256;

   public Tooltip() {
      super();
   }

   public void add(String string) {
      this.tooltip.add(string);
   }

   public String getLine(int index) {
      String string = (String)this.getList().get(index);
      return string;
   }

   public void add(List list) {
      for(Object obj : list) {
         this.tooltip.add((String)obj);
      }

   }

   public List getList() {
      return this.tooltip;
   }

   public boolean exists() {
      return this.tooltip.size() > 0;
   }

   public void setType(Tooltip.ITooltipType type) {
      this.type = type;
   }

   public void setMaxWidth(int w) {
      this.maxWidth = w;
   }

   public Tooltip.ITooltipType getType() {
      return this.type;
   }

   public void add(ItemStack item, String string) {
      NBTTagCompound nbt = new NBTTagCompound();
      item.writeToNBT(nbt);
      nbt.setByte("nbt-type", (byte)105);
      this.add("~~~" + nbt.toString() + "~~~" + string);
   }

   public void add(FluidStack item, String string) {
      NBTTagCompound nbt = new NBTTagCompound();
      item.writeToNBT(nbt);
      nbt.setByte("nbt-type", (byte)102);
      this.add("~~~" + nbt.toString() + "~~~" + string);
   }

   public interface ITooltipType {
   }

   public static enum Type implements Tooltip.ITooltipType {
      Standard,
      Help,
      Information,
      User,
      Power;

      private Type() {
      }
   }
}
