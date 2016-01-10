package binnie.botany.gardening;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.gardening.BlockPlant;
import binnie.botany.gardening.Gardening;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSoil extends Block implements IBlockSoil {
   boolean weedKilled = false;
   IIcon[] iconsTop = new IIcon[9];
   IIcon[] iconsSide = new IIcon[9];
   IIcon[] iconsNoWeed = new IIcon[9];
   EnumSoilType type;

   @SideOnly(Side.CLIENT)
   public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(int i = 0; i < 9; ++i) {
         par3List.add(new ItemStack(this, 1, i));
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
      return this.getIcon(side, world.getBlockMetadata(x, y, z));
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(int side, int meta) {
      meta = meta >= 9?8:meta;
      EnumMoisture moisture = EnumMoisture.values()[meta % 3];
      EnumAcidity acidity = EnumAcidity.values()[meta / 3];
      return side == 1?(this.weedKilled?this.iconsNoWeed[meta]:this.iconsTop[meta]):(side == 0?Blocks.dirt.getIcon(side, 0):this.iconsSide[meta]);
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister register) {
      for(EnumMoisture moisture : EnumMoisture.values()) {
         for(EnumAcidity pH : EnumAcidity.values()) {
            this.iconsTop[moisture.ordinal() + pH.ordinal() * 3] = Botany.proxy.getIcon(register, "soil/" + this.getType().getID() + "." + pH.getID() + "." + moisture.getID() + ".0");
            this.iconsSide[moisture.ordinal() + pH.ordinal() * 3] = Botany.proxy.getIcon(register, "soil/" + this.getType().getID() + "." + pH.getID() + "." + moisture.getID() + ".1");
            this.iconsNoWeed[moisture.ordinal() + pH.ordinal() * 3] = Botany.proxy.getIcon(register, "soil/" + this.getType().getID() + "." + pH.getID() + "." + moisture.getID() + ".2");
         }
      }

   }

   public EnumSoilType getType() {
      return this.type;
   }

   public BlockSoil(EnumSoilType type, String blockName, boolean weedKilled) {
      super(Material.ground);
      this.weedKilled = weedKilled;
      this.setCreativeTab(CreativeTabBotany.instance);
      this.setBlockName(blockName);
      this.setTickRandomly(true);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
      this.setLightOpacity(255);
      this.setHardness(0.5F);
      this.setStepSound(soundTypeGravel);
      this.type = type;
   }

   public int damageDropped(int p_149692_1_) {
      return p_149692_1_;
   }

   public void updateTick(World world, int x, int y, int z, Random random) {
      int meta = world.getBlockMetadata(x, y, z);
      EnumMoisture moisture = EnumMoisture.values()[meta % 3];
      EnumAcidity acidity = EnumAcidity.values()[meta / 3];
      EnumMoisture desiredMoisture = Gardening.getNaturalMoisture(world, x, y, z);
      if(desiredMoisture.ordinal() > moisture.ordinal()) {
         moisture = moisture == EnumMoisture.Dry?EnumMoisture.Normal:EnumMoisture.Damp;
      }

      if(desiredMoisture.ordinal() < moisture.ordinal()) {
         moisture = moisture == EnumMoisture.Damp?EnumMoisture.Normal:EnumMoisture.Dry;
      }

      int meta2 = getMeta(acidity, moisture);
      if(meta != meta2) {
         world.setBlockMetadataWithNotify(x, y, z, meta2, 2);
      }

      if(!this.weedKilled && random.nextInt(5 - this.getType(world, x, y, z).ordinal()) == 0 && world.getBlock(x, y + 1, z) == Blocks.air) {
         world.setBlock(x, y + 1, z, Botany.plant, BlockPlant.Type.Weeds.ordinal(), 2);
      }

   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
      return AxisAlignedBB.getBoundingBox((double)(p_149668_2_ + 0), (double)(p_149668_3_ + 0), (double)(p_149668_4_ + 0), (double)(p_149668_2_ + 1), (double)(p_149668_3_ + 1), (double)(p_149668_4_ + 1));
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public EnumAcidity getPH(World world, int x, int y, int z) {
      return EnumAcidity.values()[world.getBlockMetadata(x, y, z) / 3];
   }

   public EnumMoisture getMoisture(World world, int x, int y, int z) {
      return EnumMoisture.values()[world.getBlockMetadata(x, y, z) % 3];
   }

   public EnumSoilType getType(World world, int x, int y, int z) {
      return this.type;
   }

   public boolean fertilise(World world, int x, int y, int z, EnumSoilType maxLevel) {
      EnumSoilType type = this.getType(world, x, y, z);
      if(type.ordinal() >= maxLevel.ordinal()) {
         return false;
      } else {
         int meta = world.getBlockMetadata(x, y, z);
         return world.setBlock(x, y, z, Gardening.getSoilBlock(maxLevel, this.weedKilled), meta, 2);
      }
   }

   public boolean degrade(World world, int x, int y, int z, EnumSoilType minLevel) {
      EnumSoilType type = this.getType(world, x, y, z);
      if(type.ordinal() <= minLevel.ordinal()) {
         return false;
      } else {
         int meta = world.getBlockMetadata(x, y, z);
         return world.setBlock(x, y, z, Gardening.getSoilBlock(minLevel, this.weedKilled), meta, 2);
      }
   }

   public boolean setPH(World world, int x, int y, int z, EnumAcidity pH) {
      int meta = getMeta(pH, this.getMoisture(world, x, y, z));
      return world.setBlockMetadataWithNotify(x, y, z, meta, 2);
   }

   public boolean setMoisture(World world, int x, int y, int z, EnumMoisture moisture) {
      int meta = getMeta(this.getPH(world, x, y, z), moisture);
      return world.setBlockMetadataWithNotify(x, y, z, meta, 2);
   }

   public static int getMeta(EnumAcidity acid, EnumMoisture moisture) {
      return acid.ordinal() * 3 + moisture.ordinal();
   }

   public void onNeighborBlockChange(World world, int x, int y, int z, Block p_149695_5_) {
      super.onNeighborBlockChange(world, x, y, z, p_149695_5_);
      if(world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN, false)) {
         world.setBlock(x, y, z, Blocks.dirt, 0, 2);
      }

   }

   public static String getPH(ItemStack stack) {
      return Binnie.Language.localise(EnumAcidity.values()[stack.getItemDamage() / 3]);
   }

   public static String getMoisture(ItemStack stack) {
      return Binnie.Language.localise(EnumMoisture.values()[stack.getItemDamage() % 3]);
   }

   public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
      Block plant = plantable.getPlant(world, x, y + 1, z);
      return plant == Botany.flower?true:(plant != Botany.plant?(!(world instanceof World)?false:Blocks.dirt.canSustainPlant(world, x, y, z, direction, plantable)):!this.weedKilled || !BlockPlant.isWeed(world, x, y, z));
   }

   public boolean resistsWeeds(World world, int x, int y, int z) {
      return this.weedKilled;
   }
}
