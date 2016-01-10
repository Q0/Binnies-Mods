package binnie.extrabees.genetics;

import binnie.core.Mods;
import binnie.extrabees.ExtraBees;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleFlowers;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IFruitBearer;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

public enum ExtraBeesFlowers implements IFlowerProvider, IAlleleFlowers {
   WATER,
   SUGAR,
   ROCK,
   BOOK,
   DEAD,
   REDSTONE,
   WOOD,
   LEAVES,
   Sapling,
   Fruit,
   Mystical;

   boolean dominant = true;

   private ExtraBeesFlowers() {
   }

   public String getUID() {
      return "extrabees.flower." + this.toString().toLowerCase();
   }

   public boolean isDominant() {
      return this.dominant;
   }

   public IFlowerProvider getProvider() {
      return this;
   }

   public String getDescription() {
      return ExtraBees.proxy.localise("flowers." + this.name().toString().toLowerCase() + ".name");
   }

   public void register() {
      AlleleManager.alleleRegistry.registerAllele(this);
   }

   public static void doInit() {
      for(ExtraBeesFlowers effect : values()) {
         effect.register();
      }

   }

   public ItemStack[] getItemStacks() {
      switch(this) {
      case WATER:
         return new ItemStack[]{new ItemStack(Blocks.waterlily)};
      case SUGAR:
         return new ItemStack[]{new ItemStack(Blocks.reeds)};
      case ROCK:
         return new ItemStack[]{new ItemStack(Blocks.cobblestone)};
      case BOOK:
         return new ItemStack[]{new ItemStack(Blocks.bookshelf)};
      case REDSTONE:
         return new ItemStack[]{new ItemStack(Blocks.redstone_torch)};
      case DEAD:
         return new ItemStack[]{new ItemStack(Blocks.deadbush)};
      case Fruit:
         return new ItemStack[]{new ItemStack(Items.apple)};
      case LEAVES:
         return new ItemStack[]{new ItemStack(Blocks.leaves)};
      case Sapling:
         return new ItemStack[]{new ItemStack(Blocks.sapling)};
      case WOOD:
         return new ItemStack[]{new ItemStack(Blocks.log)};
      default:
         return new ItemStack[0];
      }
   }

   public boolean isAcceptedPollinatable(World world, IPollinatable pollinatable) {
      EnumSet<EnumPlantType> types = pollinatable.getPlantType();
      return types.size() > 1 || !types.contains(EnumPlantType.Nether);
   }

   public boolean isAcceptedFlower(World world, IIndividual individual, int x, int y, int z) {
      Block block = world.getBlock(x, y, z);
      if(block == null) {
         return false;
      } else {
         switch(this) {
         case WATER:
            return block == Blocks.waterlily;
         case SUGAR:
            return block == Blocks.reeds;
         case ROCK:
            return block.getMaterial() == Material.rock;
         case BOOK:
            return block == Blocks.bookshelf;
         case REDSTONE:
            return block == Blocks.redstone_torch;
         case DEAD:
            return block == Blocks.deadbush;
         case Fruit:
            return world.getTileEntity(x, y, z) instanceof IFruitBearer;
         case LEAVES:
            return block.isLeaves(world, x, y, z);
         case Sapling:
            return block.getClass().getName().toLowerCase().contains("sapling");
         case WOOD:
            return block.isWood(world, x, y, z);
         case Mystical:
            return block == Mods.Botania.block("flower");
         default:
            return false;
         }
      }
   }

   public boolean growFlower(World world, IIndividual individual, int x, int y, int z) {
      switch(this) {
      case WATER:
         if(world.isAirBlock(x, y, z) && world.getBlock(x, y - 1, z) == Blocks.water) {
            return world.setBlock(x, y, z, Blocks.waterlily, 0, 2);
         }

         return false;
      case SUGAR:
         if(world.getBlock(x, y - 1, z) == Blocks.reeds && world.isAirBlock(x, y, z)) {
            return world.setBlock(x, y, z, Blocks.reeds, 0, 0);
         }

         return false;
      default:
         return false;
      }
   }

   public ItemStack[] affectProducts(World world, IIndividual individual, int x, int y, int z, ItemStack[] products) {
      if(this != Mystical) {
         return products;
      } else {
         List<ItemStack> prods = new ArrayList();

         for(ItemStack stack : products) {
            prods.add(stack);
         }

         for(int k = 0; k < 50; ++k) {
            int tX = 7;
            int tY = 7;
            int tZ = 3;
            int x2 = x - tX + world.rand.nextInt(1 + 2 * tX);
            int y2 = y - tY + world.rand.nextInt(1 + 2 * tY);
            int z2 = z - tZ + world.rand.nextInt(1 + 2 * tZ);
            Block block = world.getBlock(x2, y2, z2);
            if(block != null && block == Mods.Botania.block("flower")) {
               int meta = world.getBlockMetadata(x2, y2, z2);
               Item item = Mods.Botania.item("petal");
               if(item != null) {
                  prods.add(new ItemStack(item, 1, meta));
               }
            }
         }

         return (ItemStack[])prods.toArray(new ItemStack[0]);
      }
   }

   public String getName() {
      return this.getDescription();
   }

   public String getUnlocalizedName() {
      return this.getUID();
   }

   public List getFlowers() {
      return new ArrayList();
   }
}
