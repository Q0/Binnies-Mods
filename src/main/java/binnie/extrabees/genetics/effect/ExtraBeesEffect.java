package binnie.extrabees.genetics.effect;

import binnie.Binnie;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.genetics.ExtraBeesFlowers;
import binnie.extrabees.genetics.effect.EntityBeeLightning;
import binnie.extrabees.genetics.effect.FireworkCreator;
import cofh.api.energy.IEnergyReceiver;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IArmorApiarist;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IEffectData;
import forestry.api.genetics.IIndividual;
import forestry.plugins.PluginApiculture;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

public enum ExtraBeesEffect implements IAlleleBeeEffect {
   ECTOPLASM,
   ACID,
   SPAWN_ZOMBIE,
   SPAWN_SKELETON,
   SPAWN_CREEPER,
   LIGHTNING,
   RADIOACTIVE,
   METEOR,
   HUNGER,
   FOOD,
   BLINDNESS,
   CONFUSION,
   FIREWORKS,
   FESTIVAL,
   BIRTHDAY,
   TELEPORT,
   GRAVITY,
   THIEF,
   WITHER,
   WATER,
   SLOW,
   BonemealSapling,
   BonemealFruit,
   BonemealMushroom,
   Power;

   String fx = "";
   public boolean combinable = false;
   public boolean dominant = true;
   public int id;
   private String uid = this.toString().toLowerCase();
   static List birthdays = new ArrayList();

   private ExtraBeesEffect() {
   }

   public static void doInit() {
      BLINDNESS.setFX("blindness");
      FOOD.setFX("food");
      GRAVITY.setFX("gravity");
      THIEF.setFX("gravity");
      TELEPORT.setFX("gravity");
      LIGHTNING.setFX("lightning");
      METEOR.setFX("meteor");
      RADIOACTIVE.setFX("radioactive");
      WATER.setFX("water");
      WITHER.setFX("wither");

      for(ExtraBeesEffect effect : values()) {
         effect.register();
      }

   }

   private void setFX(String string) {
      this.fx = "particles/" + string;
   }

   public void register() {
      AlleleManager.alleleRegistry.registerAllele(this);
   }

   public boolean isCombinable() {
      return this.combinable;
   }

   public IEffectData validateStorage(IEffectData storedData) {
      return storedData;
   }

   public String getName() {
      return ExtraBees.proxy.localise("effect." + this.name().toString().toLowerCase() + ".name");
   }

   public boolean isDominant() {
      return this.dominant;
   }

   public void spawnMob(World world, int x, int y, int z, String name) {
      if(this.anyPlayerInRange(world, x, y, z, 16)) {
         double var1 = (double)((float)x + world.rand.nextFloat());
         double var3 = (double)((float)y + world.rand.nextFloat());
         double var5 = (double)((float)z + world.rand.nextFloat());
         world.spawnParticle("smoke", var1, var3, var5, 0.0D, 0.0D, 0.0D);
         world.spawnParticle("flame", var1, var3, var5, 0.0D, 0.0D, 0.0D);
         EntityLiving var9 = (EntityLiving)EntityList.createEntityByName(name, world);
         if(var9 == null) {
            return;
         }

         int var10 = world.getEntitiesWithinAABB(var9.getClass(), AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)(y + 1), (double)(z + 1)).expand(8.0D, 4.0D, 8.0D)).size();
         if(var10 >= 6) {
            return;
         }

         if(var9 != null) {
            double var11 = (double)x + (world.rand.nextDouble() - world.rand.nextDouble()) * 4.0D;
            double var13 = (double)(y + world.rand.nextInt(3) - 1);
            double var15 = (double)z + (world.rand.nextDouble() - world.rand.nextDouble()) * 4.0D;
            var9.setLocationAndAngles(var11, var13, var15, world.rand.nextFloat() * 360.0F, 0.0F);
            if(var9.getCanSpawnHere()) {
               world.spawnEntityInWorld(var9);
               world.playAuxSFX(2004, x, y, z, 0);
               var9.spawnExplosionParticle();
            }
         }
      }

   }

   private boolean anyPlayerInRange(World world, int x, int y, int z, int distance) {
      return world.getClosestPlayer((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, (double)distance) != null;
   }

   public static void doAcid(World world, int x, int y, int z) {
      Block block = world.getBlock(x, y, z);
      if(block != Blocks.cobblestone && block != Blocks.stone) {
         if(block == Blocks.dirt | block == Blocks.grass) {
            world.setBlock(x, y, z, Blocks.sand, 0, 0);
         }
      } else {
         world.setBlock(x, y, z, Blocks.gravel, 0, 0);
      }

   }

   public String getUID() {
      return "extrabees.effect." + this.uid;
   }

   public IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing) {
      World world = housing.getWorld();
      int xHouse = housing.getXCoord();
      int yHouse = housing.getYCoord();
      int zHouse = housing.getZCoord();
      int[] area = this.getModifiedArea(genome, housing);
      int xd = 1 + area[0] / 2;
      int yd = 1 + area[1] / 2;
      int zd = 1 + area[2] / 2;
      int x1 = xHouse - xd + world.rand.nextInt(2 * xd + 1);
      int y1 = yHouse - yd + world.rand.nextInt(2 * yd + 1);
      int z1 = zHouse - zd + world.rand.nextInt(2 * zd + 1);
      switch(this) {
      case BIRTHDAY:
      case FESTIVAL:
      case FIREWORKS:
         if(world.rand.nextInt(this == FIREWORKS?8:12) < 1) {
            FireworkCreator.Firework firework = new FireworkCreator.Firework();
            switch(this) {
            case BIRTHDAY:
               firework.setShape(FireworkCreator.Shape.Star);
               firework.addColor(16768256);

               for(ExtraBeesEffect.Birthday birthday : birthdays) {
                  if(birthday.isToday()) {
                     firework.addColor(16711680);
                     firework.addColor('\uff00');
                     firework.addColor(255);
                     firework.setTrail();
                     break;
                  }
               }
            case FESTIVAL:
            default:
               break;
            case FIREWORKS:
               firework.setShape(FireworkCreator.Shape.Ball);
               firework.addColor(genome.getPrimary().getIconColour(0));
               firework.addColor(genome.getPrimary().getIconColour(0));
               firework.addColor(genome.getPrimary().getIconColour(1));
               firework.addColor(genome.getSecondary().getIconColour(0));
               firework.addColor(genome.getSecondary().getIconColour(0));
               firework.addColor(genome.getPrimary().getIconColour(1));
               firework.setTrail();
            }

            EntityFireworkRocket var11 = new EntityFireworkRocket(world, (double)x1, (double)y1, (double)z1, firework.getFirework());
            if(world.canBlockSeeTheSky(x1, y1, z1)) {
               ((WorldServer)world).spawnEntityInWorld(var11);
            }
         }
         break;
      case ECTOPLASM:
         if(world.rand.nextInt(100) < 4) {
            if(world.isAirBlock(x1, y1, z1) && (world.isBlockNormalCubeDefault(x1, y1 - 1, z1, false) || world.getBlock(x1, y1 - 1, z1) == ExtraBees.ectoplasm)) {
               world.setBlock(x1, y1, z1, ExtraBees.ectoplasm, 0, 0);
            }

            return null;
         }
         break;
      case ACID:
         if(world.rand.nextInt(100) < 6) {
            doAcid(world, x1, y1, z1);
         }
         break;
      case SPAWN_ZOMBIE:
         if(world.rand.nextInt(200) < 2) {
            this.spawnMob(world, x1, y1, z1, "Zombie");
         }
         break;
      case SPAWN_SKELETON:
         if(world.rand.nextInt(200) < 2) {
            this.spawnMob(world, x1, y1, z1, "Skeleton");
         }
         break;
      case SPAWN_CREEPER:
         if(world.rand.nextInt(200) < 2) {
            this.spawnMob(world, x1, y1, z1, "Creeper");
         }
         break;
      case LIGHTNING:
         if(world.rand.nextInt(100) < 1 && world.canBlockSeeTheSky(x1, y1, z1) && world instanceof WorldServer) {
            ((WorldServer)world).addWeatherEffect(new EntityBeeLightning(world, (double)x1, (double)y1, (double)z1));
         }
         break;
      case METEOR:
         if(world.rand.nextInt(100) < 1 && world.canBlockSeeTheSky(x1, y1, z1)) {
            ((WorldServer)world).spawnEntityInWorld(new EntitySmallFireball(world, (double)x1, (double)(y1 + 64), (double)z1, 0.0D, -0.6D, 0.0D));
         }
         break;
      case RADIOACTIVE:
         Iterator i$ = this.getEntities(EntityLivingBase.class, genome, housing).iterator();

         while(true) {
            EntityLivingBase entity;
            int damage;
            while(true) {
               if(!i$.hasNext()) {
                  return null;
               }

               entity = (EntityLivingBase)i$.next();
               damage = 4;
               if(!(entity instanceof EntityPlayer)) {
                  break;
               }

               int count = wearsItems((EntityPlayer)entity);
               if(count <= 3) {
                  if(count > 2) {
                     damage = 1;
                  } else if(count > 1) {
                     damage = 2;
                  } else if(count > 0) {
                     damage = 3;
                  }
                  break;
               }
            }

            entity.attackEntityFrom(DamageSource.generic, (float)damage);
         }
      case FOOD:
         for(EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
            if(entity instanceof EntityPlayer) {
               EntityPlayer player = (EntityPlayer)entity;
               player.getFoodStats().addStats(2, 0.2F);
            }
         }

         return null;
      case HUNGER:
         for(EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
            if(entity instanceof EntityPlayer) {
               EntityPlayer player = (EntityPlayer)entity;
               if(world.rand.nextInt(4) >= wearsItems(player)) {
                  player.getFoodStats().addExhaustion(4.0F);
                  player.addPotionEffect(new PotionEffect(Potion.hunger.id, 100));
               }
            }
         }

         return null;
      case BLINDNESS:
         for(EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
            if(entity instanceof EntityPlayer) {
               EntityPlayer player = (EntityPlayer)entity;
               if(world.rand.nextInt(4) >= wearsItems(player)) {
                  player.addPotionEffect(new PotionEffect(Potion.blindness.id, 200));
               }
            }
         }

         return null;
      case SLOW:
         for(EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
            if(entity instanceof EntityPlayer) {
               EntityPlayer player = (EntityPlayer)entity;
               if(world.rand.nextInt(4) >= wearsItems(player)) {
                  player.addPotionEffect(new PotionEffect(Potion.weakness.id, 200));
               }
            }
         }

         return null;
      case CONFUSION:
         for(EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
            if(entity instanceof EntityPlayer) {
               EntityPlayer player = (EntityPlayer)entity;
               if(world.rand.nextInt(4) >= wearsItems(player)) {
                  player.addPotionEffect(new PotionEffect(Potion.confusion.id, 200));
               }
            }
         }

         return null;
      case GRAVITY:
         for(Entity entity : this.getEntities(Entity.class, genome, housing)) {
            float entityStrength = 1.0F;
            if(entity instanceof EntityPlayer) {
               entityStrength *= 100.0F;
            }

            double dx = (double)x1 - entity.posX;
            double dy = (double)y1 - entity.posY;
            double dz = (double)z1 - entity.posZ;
            if(dx * dx + dy * dy + dz * dz < 2.0D) {
               return null;
            }

            double strength = 0.5D / (dx * dx + dy * dy + dz * dz) * (double)entityStrength;
            entity.addVelocity(dx * strength, dy * strength, dz * strength);
         }

         return null;
      case THIEF:
         for(EntityPlayer entity : this.getEntities(EntityPlayer.class, genome, housing)) {
            double dx = (double)x1 - entity.posX;
            double dy = (double)y1 - entity.posY;
            double dz = (double)z1 - entity.posZ;
            if(dx * dx + dy * dy + dz * dz < 2.0D) {
               return null;
            }

            double strength = 0.5D / (dx * dx + dy * dy + dz * dz);
            entity.addVelocity(-dx * strength, -dy * strength, -dz * strength);
         }

         return null;
      case TELEPORT:
         if(world.rand.nextInt(80) > 1) {
            return null;
         }

         List<Entity> entities = this.getEntities(Entity.class, genome, housing);
         if(entities.size() == 0) {
            return null;
         }

         Entity entity = (Entity)entities.get(world.rand.nextInt(entities.size()));
         if(!(entity instanceof EntityLiving)) {
            return null;
         }

         float jumpDist = 5.0F;
         if(y1 < 4) {
            y1 = 4;
         }

         if(!world.isAirBlock(x1, y1, z1) || !world.isAirBlock(x1, y1 + 1, z1)) {
            return null;
         }

         ((EntityLiving)entity).setPositionAndUpdate((double)x1, (double)y1, (double)z1);
         ((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 160, 10));
         break;
      case WATER:
         if(world.rand.nextInt(120) > 1) {
            return null;
         }

         TileEntity tile = world.getTileEntity(x1, y1, z1);
         if(tile instanceof IFluidHandler) {
            ((IFluidHandler)tile).fill(ForgeDirection.UP, Binnie.Liquid.getLiquidStack("water", 100), true);
         }
         break;
      case BonemealSapling:
         if(world.rand.nextInt(20) > 1) {
            return null;
         }

         if(ExtraBeesFlowers.Sapling.isAcceptedFlower(world, (IIndividual)null, x1, y1, z1)) {
            ItemDye.applyBonemeal(new ItemStack(Blocks.dirt, 1), world, x1, y1, z1, (EntityPlayer)null);
         }
         break;
      case BonemealFruit:
         if(world.rand.nextInt(20) > 1) {
            return null;
         }

         if(ExtraBeesFlowers.Fruit.isAcceptedFlower(world, (IIndividual)null, x1, y1, z1)) {
            ItemDye.applyBonemeal(new ItemStack(Blocks.dirt, 1), world, x1, y1, z1, (EntityPlayer)null);
         }
         break;
      case BonemealMushroom:
         if(world.rand.nextInt(20) > 1) {
            return null;
         }

         if(world.getBlock(x1, y1, z1) == Blocks.brown_mushroom || world.getBlock(x1, y1, z1) == Blocks.red_mushroom) {
            ItemDye.applyBonemeal(new ItemStack(Blocks.dirt, 1), world, x1, y1, z1, (EntityPlayer)null);
         }
         break;
      case Power:
         TileEntity tile2 = world.getTileEntity(x1, y1, z1);
         if(tile2 instanceof IEnergyReceiver) {
            ((IEnergyReceiver)tile2).receiveEnergy(ForgeDirection.getOrientation(0), 5, true);
         }
      case WITHER:
      }

      return null;
   }

   protected int[] getModifiedArea(IBeeGenome genome, IBeeHousing housing) {
      int[] area = genome.getTerritory();
      area[0] = (int)((float)area[0] * housing.getTerritoryModifier(genome, 1.0F) * 3.0F);
      area[1] = (int)((float)area[1] * housing.getTerritoryModifier(genome, 1.0F) * 3.0F);
      area[2] = (int)((float)area[2] * housing.getTerritoryModifier(genome, 1.0F) * 3.0F);
      if(area[0] < 1) {
         area[0] = 1;
      }

      if(area[1] < 1) {
         area[1] = 1;
      }

      if(area[2] < 1) {
         area[2] = 1;
      }

      return area;
   }

   public IEffectData doFX(IBeeGenome genome, IEffectData storedData, IBeeHousing housing) {
      int[] area = genome.getTerritory();
      area[0] = (int)((float)area[0] * housing.getTerritoryModifier(genome, 1.0F));
      area[1] = (int)((float)area[1] * housing.getTerritoryModifier(genome, 1.0F));
      area[2] = (int)((float)area[2] * housing.getTerritoryModifier(genome, 1.0F));
      if(area[0] < 1) {
         area[0] = 1;
      }

      if(area[1] < 1) {
         area[1] = 1;
      }

      if(area[2] < 1) {
         area[2] = 1;
      }

      PluginApiculture.proxy.addBeeHiveFX("particles/swarm_bee", housing.getWorld(), (double)housing.getXCoord(), (double)housing.getYCoord(), (double)housing.getZCoord(), genome.getPrimary().getIconColour(0), area[0], area[1], area[2]);
      return storedData;
   }

   public String getFX() {
      return this.fx;
   }

   public List getEntities(Class eClass, IBeeGenome genome, IBeeHousing housing) {
      int[] area = genome.getTerritory();
      int[] offset = new int[]{-Math.round((float)(area[0] / 2)), -Math.round((float)(area[1] / 2)), -Math.round((float)(area[2] / 2))};
      int[] min = new int[]{housing.getXCoord() + offset[0], housing.getYCoord() + offset[1], housing.getZCoord() + offset[2]};
      int[] max = new int[]{housing.getXCoord() + offset[0] + area[0], housing.getYCoord() + offset[1] + area[1], housing.getZCoord() + offset[2] + area[2]};
      AxisAlignedBB box = AxisAlignedBB.getBoundingBox((double)min[0], (double)min[1], (double)min[2], (double)max[0], (double)max[1], (double)max[2]);
      return housing.getWorld().getEntitiesWithinAABB(eClass, box);
   }

   public static boolean wearsHelmet(EntityPlayer player) {
      ItemStack armorItem = player.inventory.armorInventory[3];
      return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
   }

   public static boolean wearsChest(EntityPlayer player) {
      ItemStack armorItem = player.inventory.armorInventory[2];
      return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
   }

   public static boolean wearsLegs(EntityPlayer player) {
      ItemStack armorItem = player.inventory.armorInventory[1];
      return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
   }

   public static boolean wearsBoots(EntityPlayer player) {
      ItemStack armorItem = player.inventory.armorInventory[0];
      return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
   }

   public static int wearsItems(EntityPlayer player) {
      int count = 0;
      if(wearsHelmet(player)) {
         ++count;
      }

      if(wearsChest(player)) {
         ++count;
      }

      if(wearsLegs(player)) {
         ++count;
      }

      if(wearsBoots(player)) {
         ++count;
      }

      return count;
   }

   public String getUnlocalizedName() {
      return this.getUID();
   }

   static {
      birthdays.add(new ExtraBeesEffect.Birthday(3, 10, "Binnie"));
   }

   public static class Birthday {
      int day;
      int month;
      String name;

      public boolean isToday() {
         return Calendar.getInstance().get(5) == this.month && Calendar.getInstance().get(2) == this.day;
      }

      public String getName() {
         return this.name;
      }

      private Birthday(int day, int month, String name) {
         super();
         this.day = day;
         this.month = month + 1;
         this.name = name;
      }
   }
}
