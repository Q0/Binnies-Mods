package binnie.botany.items;

import binnie.botany.Botany;
import binnie.core.item.IItemMisc;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public enum BotanyItems implements IItemMisc {
   AshPowder("Ash Powder", "powderAsh"),
   PulpPowder("Wood Pulp Powder", "powderPulp"),
   MulchPowder("Mulch Powder", "powderMulch"),
   SulphurPowder("Sulphur Powder", "powderSulphur"),
   FertiliserPowder("Fertiliser Powder", "powderFertiliser"),
   CompostPowder("Compost Powder", "powderCompost"),
   Mortar("Mortar", "mortar"),
   Weedkiller("Weedkiller", "weedkiller");

   IIcon icon;
   String name;
   String iconPath;

   private BotanyItems(String name, String iconPath) {
      this.name = name;
      this.iconPath = iconPath;
   }

   public IIcon getIcon(ItemStack stack) {
      return this.icon;
   }

   public void registerIcons(IIconRegister register) {
      this.icon = Botany.proxy.getIcon(register, this.iconPath);
   }

   public void addInformation(List par3List) {
   }

   public String getName(ItemStack stack) {
      return this.name;
   }

   public ItemStack get(int size) {
      return new ItemStack(Botany.misc, size, this.ordinal());
   }

   public boolean isActive() {
      return true;
   }
}
