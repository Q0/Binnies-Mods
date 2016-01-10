package binnie.botany.gardening;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.gardening.Gardening;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPlant extends BlockBush {
   public ArrayList getDrops(World world, int x, int y, int z, int metadata, int fortune) {
      return new ArrayList();
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister p_149651_1_) {
      for(BlockPlant.Type t : BlockPlant.Type.values()) {
         t.icon = Botany.proxy.getIcon(p_149651_1_, t.name().toLowerCase());
      }

   }

   public BlockPlant() {
      super();
      this.setBlockName("plant");
      this.setCreativeTab(CreativeTabBotany.instance);
      this.setTickRandomly(true);
   }

   protected boolean canPlaceBlockOn(Block p_149854_1_) {
      return super.canPlaceBlockOn(p_149854_1_) || Gardening.isSoil(p_149854_1_);
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int side, int meta) {
      return BlockPlant.Type.values()[meta % BlockPlant.Type.values().length].icon;
   }

   public int damageDropped(int p_149692_1_) {
      return p_149692_1_;
   }

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
      for(BlockPlant.Type type : BlockPlant.Type.values()) {
         p_149666_3_.add(type.get());
      }

   }

   public void updateTick(World world, int x, int y, int z, Random random) {
      BlockPlant.Type type = BlockPlant.Type.get(world.getBlockMetadata(x, y, z));
      if(random.nextInt(4) == 0) {
         if(type == BlockPlant.Type.Weeds) {
            world.setBlockMetadataWithNotify(x, y, z, BlockPlant.Type.WeedsLong.ordinal(), 2);
         } else if(type == BlockPlant.Type.WeedsLong) {
            world.setBlockMetadataWithNotify(x, y, z, BlockPlant.Type.WeedsVeryLong.ordinal(), 2);
         } else if(type == BlockPlant.Type.DeadFlower) {
            world.setBlockMetadataWithNotify(x, y, z, BlockPlant.Type.DecayingFlower.ordinal(), 2);
         } else if(type == BlockPlant.Type.DecayingFlower) {
            world.setBlockToAir(x, y, z);
            return;
         }
      }

      if(random.nextInt(6) == 0) {
         if(type == BlockPlant.Type.Weeds) {
            world.setBlockToAir(x, y, z);
         } else if(type == BlockPlant.Type.WeedsLong) {
            world.setBlockMetadataWithNotify(x, y, z, BlockPlant.Type.Weeds.ordinal(), 2);
         } else if(type == BlockPlant.Type.WeedsVeryLong) {
            world.setBlockMetadataWithNotify(x, y, z, BlockPlant.Type.WeedsLong.ordinal(), 2);
         }
      }

      Block below = world.getBlock(x, y - 1, z);
      if(Gardening.isSoil(below)) {
         IBlockSoil soil = (IBlockSoil)below;
         if(random.nextInt(3) == 0) {
            if(type != BlockPlant.Type.Weeds && type != BlockPlant.Type.WeedsLong && type != BlockPlant.Type.WeedsVeryLong) {
               if(type == BlockPlant.Type.DecayingFlower && !soil.fertilise(world, x, y - 1, z, EnumSoilType.LOAM)) {
                  soil.fertilise(world, x, y - 1, z, EnumSoilType.FLOWERBED);
               }
            } else if(!soil.degrade(world, x, y - 1, z, EnumSoilType.LOAM)) {
               soil.degrade(world, x, y - 1, z, EnumSoilType.SOIL);
            }
         }
      }

   }

   public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
      return true;
   }

   public static boolean isWeed(IBlockAccess world, int x, int y, int z) {
      if(!(world.getBlock(x, y, z) instanceof BlockPlant)) {
         return false;
      } else {
         BlockPlant.Type type = BlockPlant.Type.get(world.getBlockMetadata(x, y, z));
         return type == BlockPlant.Type.Weeds || type == BlockPlant.Type.WeedsLong || type == BlockPlant.Type.WeedsVeryLong;
      }
   }

   public static enum Type {
      Weeds("Weeds"),
      WeedsLong("Long Weeds"),
      WeedsVeryLong("Very Long Weeds"),
      DeadFlower("Dead Flower"),
      DecayingFlower("Decaying Flower");

      public IIcon icon;
      String name;

      private Type(String name) {
         this.name = name;
      }

      public ItemStack get() {
         return new ItemStack(Botany.plant, 1, this.ordinal());
      }

      public static BlockPlant.Type get(int id) {
         return values()[id % values().length];
      }

      public String getName() {
         return this.name;
      }
   }
}
