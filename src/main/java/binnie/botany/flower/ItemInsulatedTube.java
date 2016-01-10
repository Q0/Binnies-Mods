package binnie.botany.flower;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemInsulatedTube extends Item {
   IIcon[] icons = new IIcon[3];

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
      for(ItemInsulatedTube.Material mat : ItemInsulatedTube.Material.values()) {
         for(ItemInsulatedTube.Insulate ins : ItemInsulatedTube.Insulate.values()) {
            p_150895_3_.add(new ItemStack(this, 1, mat.ordinal() + ins.ordinal() * 128));
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister p_94581_1_) {
      for(int i = 0; i < 3; ++i) {
         this.icons[i] = Botany.proxy.getIcon(p_94581_1_, "insulatedTube." + i);
      }

   }

   @SideOnly(Side.CLIENT)
   public int getColorFromItemStack(ItemStack stack, int pass) {
      return pass == 0?16777215:(pass == 1?ItemInsulatedTube.Material.get(stack.getItemDamage()).getColor():ItemInsulatedTube.Insulate.get(stack.getItemDamage()).getColor());
   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
      super.addInformation(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
      p_77624_3_.add(ItemInsulatedTube.Insulate.get(p_77624_1_.getItemDamage()).getName());
   }

   public String getItemStackDisplayName(ItemStack p_77653_1_) {
      return ItemInsulatedTube.Material.get(p_77653_1_.getItemDamage()).getName() + " Insulated Tube";
   }

   public int getRenderPasses(int metadata) {
      return 3;
   }

   public IIcon getIcon(ItemStack stack, int pass) {
      return this.icons[pass % 3];
   }

   public boolean requiresMultipleRenderPasses() {
      return true;
   }

   public ItemInsulatedTube() {
      super();
      this.setUnlocalizedName("insulatedTube");
      this.setCreativeTab(CreativeTabBotany.instance);
   }

   public static String getInsulate(ItemStack stack) {
      return ItemInsulatedTube.Insulate.get(stack.getItemDamage()).getName();
   }

   public static ItemStack getInsulateStack(ItemStack stack) {
      return ItemInsulatedTube.Insulate.get(stack.getItemDamage()).getStack();
   }

   static enum Insulate {
      Clay(10595020, "Clay"),
      Cobble(8092539, "Cobblestone"),
      Sand(15723189, "Sand"),
      HardenedClay(9657411, "Hardened Clay"),
      Stone(7171437, "Smooth Stone"),
      Sandstone(12695945, "Sandstone");

      int color;
      String name;

      public int getColor() {
         return this.color;
      }

      public String getName() {
         return this.name;
      }

      private Insulate(int color, String name) {
         this.color = color;
         this.name = name;
      }

      public static ItemInsulatedTube.Insulate get(int i) {
         return values()[i / 128 % values().length];
      }

      public ItemStack getStack() {
         switch(this) {
         case Clay:
            return new ItemStack(Blocks.clay);
         case Cobble:
            return new ItemStack(Blocks.cobblestone);
         case HardenedClay:
            return new ItemStack(Blocks.hardened_clay);
         case Sand:
            return new ItemStack(Blocks.sand);
         case Sandstone:
            return new ItemStack(Blocks.sandstone);
         case Stone:
            return new ItemStack(Blocks.stone);
         default:
            return null;
         }
      }
   }

   static enum Material {
      Copper(14923662, "Copper"),
      Tin(14806772, "Tin"),
      Bronze(14533238, "Bronze"),
      Iron(14211288, "Iron");

      int color;
      String name;

      public int getColor() {
         return this.color;
      }

      public String getName() {
         return this.name;
      }

      private Material(int color, String name) {
         this.color = color;
         this.name = name;
      }

      public static ItemInsulatedTube.Material get(int i) {
         return values()[i % values().length];
      }
   }
}
