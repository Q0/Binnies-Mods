package binnie.botany.flower;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerType;
import binnie.botany.core.BotanyCore;
import binnie.botany.flower.RendererBotany;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.gardening.Gardening;
import binnie.botany.genetics.EnumFlowerType;
import binnie.core.BinnieCore;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFlower extends BlockContainer {
   public int getRenderType() {
      return RendererBotany.renderID;
   }

   @SideOnly(Side.CLIENT)
   public void registerBlockIcons(IIconRegister par1IconRegister) {
      for(EnumFlowerType type : EnumFlowerType.values()) {
         type.registerIcons(par1IconRegister);
      }

   }

   public BlockFlower() {
      super(Material.plants);
      float f = 0.2F;
      this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
      this.setTickRandomly(true);
      this.setBlockName("flower");
   }

   public TileEntity createNewTileEntity(World var1, int i) {
      return new TileEntityFlower();
   }

   public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
      return null;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack) {
      super.onBlockPlacedBy(world, x, y, z, living, stack);
      TileEntity flower = world.getTileEntity(x, y, z);
      if(!BinnieCore.proxy.isSimulating(world)) {
         if(flower != null && flower instanceof TileEntityFlower) {
            IFlower f = BotanyCore.getFlowerRoot().getMember(stack);
            ((TileEntityFlower)flower).setRender(new TileEntityFlower.RenderInfo(f, (TileEntityFlower)flower));
         }

      } else {
         TileEntity below = world.getTileEntity(x, y - 1, z);
         if(flower != null && flower instanceof TileEntityFlower) {
            if(below instanceof TileEntityFlower) {
               ((TileEntityFlower)flower).setSection(((TileEntityFlower)below).getSection());
            } else {
               GameProfile owner = living instanceof EntityPlayer?((EntityPlayer)living).getGameProfile():null;
               ((TileEntityFlower)flower).create(stack, owner);
            }
         }

         Gardening.tryGrowSection(world, x, y, z);
      }
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
      TileEntity tile = world.getTileEntity(x, y, z);
      if(tile instanceof TileEntityFlower) {
         TileEntityFlower f = (TileEntityFlower)tile;
         EnumFlowerStage stage = f.getAge() == 0?EnumFlowerStage.SEED:EnumFlowerStage.FLOWER;
         IFlowerType flower = f.getType();
         int section = f.getRenderSection();
         boolean flowered = f.isFlowered();
         return RendererBotany.pass == 0?flower.getStem(stage, flowered, section):(RendererBotany.pass == 1?flower.getPetalIcon(stage, flowered, section):flower.getVariantIcon(stage, flowered, section));
      } else {
         return super.getIcon(world, x, y, z, side);
      }
   }

   @SideOnly(Side.CLIENT)
   public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
      TileEntity tile = world.getTileEntity(x, y, z);
      if(tile instanceof TileEntityFlower) {
         TileEntityFlower f = (TileEntityFlower)tile;
         return RendererBotany.pass == 0?f.getStemColour():(RendererBotany.pass == 1?f.getPrimaryColour():f.getSecondaryColour());
      } else {
         return 16777215;
      }
   }

   public boolean canPlaceBlockAt(World world, int x, int y, int z) {
      return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
   }

   protected boolean canPlaceBlockOn(Block block) {
      return block == Blocks.grass || block == Blocks.dirt || block == Blocks.farmland || Gardening.isSoil(block);
   }

   public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
      super.onNeighborBlockChange(world, x, y, z, block);
      this.checkAndDropBlock(world, x, y, z);
      TileEntity tile = world.getTileEntity(x, y, z);
      if(tile instanceof TileEntityFlower) {
         TileEntityFlower flower = (TileEntityFlower)tile;
         if(flower.getSection() == 0 && flower.getFlower() != null && flower.getFlower().getAge() > 0 && flower.getFlower().getGenome().getPrimary().getType().getSections() > 1 && world.getBlock(x, y + 1, z) != Botany.flower) {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
         }
      }

   }

   public void updateTick(World world, int x, int y, int z, Random rand) {
      TileEntity tile = world.getTileEntity(x, y, z);
      if(tile instanceof TileEntityFlower) {
         ((TileEntityFlower)tile).randomUpdate(rand);
         this.checkAndDropBlock(world, x, y, z);
      } else {
         world.setBlockToAir(x, y, z);
      }
   }

   protected void checkAndDropBlock(World world, int x, int y, int z) {
      if(!this.canBlockStay(world, x, y, z)) {
         this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
         world.setBlockToAir(x, y, z);
      }

   }

   public boolean canBlockStay(World world, int x, int y, int z) {
      TileEntity tile = world.getTileEntity(x, y, z);
      return tile instanceof TileEntityFlower && ((TileEntityFlower)tile).getSection() > 0?world.getBlock(x, y - 1, z) == Botany.flower:this.canPlaceBlockOn(world.getBlock(x, y - 1, z));
   }

   public ArrayList getDrops(World world, int x, int y, int z, int blockMeta, int fortune) {
      ArrayList<ItemStack> drops = new ArrayList();
      TileEntity tile = world.getTileEntity(x, y, z);
      if(tile instanceof TileEntityFlower && ((TileEntityFlower)tile).getSection() == 0) {
         ItemStack flower = ((TileEntityFlower)tile).getItemStack();
         if(flower != null) {
            drops.add(flower);
         }
      }

      return drops;
   }

   public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
      List<ItemStack> drops = this.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
      boolean hasBeenBroken = world.setBlockToAir(x, y, z);
      if(hasBeenBroken && BinnieCore.proxy.isSimulating(world) && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
         for(ItemStack drop : drops) {
            this.dropBlockAsItem(world, x, y, z, drop);
         }
      }

      return hasBeenBroken;
   }
}
