package binnie.extrabees.genetics.effect;

import binnie.Binnie;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.genetics.ExtraBeesFlowers;
import cofh.api.energy.IEnergyReceiver;
import forestry.api.apiculture.*;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IEffectData;
import forestry.plugins.PluginApiculture;
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
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    static List<Birthday> birthdays;

    static {
        (ExtraBeesEffect.birthdays = new ArrayList<Birthday>()).add(new Birthday(3, 10, "Binnie"));
    }

    public boolean combinable;
    public boolean dominant;
    public int id;
    String fx;
    private String uid;

    private ExtraBeesEffect() {
        this.fx = "";
        this.uid = this.toString().toLowerCase();
        this.combinable = false;
        this.dominant = true;
    }

    public static void doInit() {
        ExtraBeesEffect.BLINDNESS.setFX("blindness");
        ExtraBeesEffect.FOOD.setFX("food");
        ExtraBeesEffect.GRAVITY.setFX("gravity");
        ExtraBeesEffect.THIEF.setFX("gravity");
        ExtraBeesEffect.TELEPORT.setFX("gravity");
        ExtraBeesEffect.LIGHTNING.setFX("lightning");
        ExtraBeesEffect.METEOR.setFX("meteor");
        ExtraBeesEffect.RADIOACTIVE.setFX("radioactive");
        ExtraBeesEffect.WATER.setFX("water");
        ExtraBeesEffect.WITHER.setFX("wither");
        for (final ExtraBeesEffect effect : values()) {
            effect.register();
        }
    }

    public static void doAcid(final World world, final int x, final int y, final int z) {
        final Block block = world.getBlock(x, y, z);
        if (block == Blocks.cobblestone || block == Blocks.stone) {
            world.setBlock(x, y, z, Blocks.gravel, 0, 0);
        } else if (block == Blocks.dirt | block == Blocks.grass) {
            world.setBlock(x, y, z, (Block) Blocks.sand, 0, 0);
        }
    }

    public static boolean wearsHelmet(final EntityPlayer player) {
        final ItemStack armorItem = player.inventory.armorInventory[3];
        return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
    }

    public static boolean wearsChest(final EntityPlayer player) {
        final ItemStack armorItem = player.inventory.armorInventory[2];
        return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
    }

    public static boolean wearsLegs(final EntityPlayer player) {
        final ItemStack armorItem = player.inventory.armorInventory[1];
        return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
    }

    public static boolean wearsBoots(final EntityPlayer player) {
        final ItemStack armorItem = player.inventory.armorInventory[0];
        return armorItem != null && armorItem.getItem() instanceof IArmorApiarist;
    }

    public static int wearsItems(final EntityPlayer player) {
        int count = 0;
        if (wearsHelmet(player)) {
            ++count;
        }
        if (wearsChest(player)) {
            ++count;
        }
        if (wearsLegs(player)) {
            ++count;
        }
        if (wearsBoots(player)) {
            ++count;
        }
        return count;
    }

    public void register() {
        AlleleManager.alleleRegistry.registerAllele((IAllele) this);
    }

    public boolean isCombinable() {
        return this.combinable;
    }

    public IEffectData validateStorage(final IEffectData storedData) {
        return storedData;
    }

    public String getName() {
        return ExtraBees.proxy.localise("effect." + this.name().toString().toLowerCase() + ".name");
    }

    public boolean isDominant() {
        return this.dominant;
    }

    public void spawnMob(final World world, final int x, final int y, final int z, final String name) {
        if (this.anyPlayerInRange(world, x, y, z, 16)) {
            final double var1 = x + world.rand.nextFloat();
            final double var2 = y + world.rand.nextFloat();
            final double var3 = z + world.rand.nextFloat();
            world.spawnParticle("smoke", var1, var2, var3, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", var1, var2, var3, 0.0, 0.0, 0.0);
            final EntityLiving var4 = (EntityLiving) EntityList.createEntityByName(name, world);
            if (var4 == null) {
                return;
            }
            final int var5 = world.getEntitiesWithinAABB((Class) var4.getClass(), AxisAlignedBB.getBoundingBox((double) x, (double) y, (double) z, (double) (x + 1), (double) (y + 1), (double) (z + 1)).expand(8.0, 4.0, 8.0)).size();
            if (var5 >= 6) {
                return;
            }
            if (var4 != null) {
                final double var6 = x + (world.rand.nextDouble() - world.rand.nextDouble()) * 4.0;
                final double var7 = y + world.rand.nextInt(3) - 1;
                final double var8 = z + (world.rand.nextDouble() - world.rand.nextDouble()) * 4.0;
                var4.setLocationAndAngles(var6, var7, var8, world.rand.nextFloat() * 360.0f, 0.0f);
                if (var4.getCanSpawnHere()) {
                    world.spawnEntityInWorld((Entity) var4);
                    world.playAuxSFX(2004, x, y, z, 0);
                    var4.spawnExplosionParticle();
                }
            }
        }
    }

    private boolean anyPlayerInRange(final World world, final int x, final int y, final int z, final int distance) {
        return world.getClosestPlayer(x + 0.5, y + 0.5, z + 0.5, (double) distance) != null;
    }

    public String getUID() {
        return "extrabees.effect." + this.uid;
    }

    public IEffectData doEffect(final IBeeGenome genome, final IEffectData storedData, final IBeeHousing housing) {
        final World world = housing.getWorld();
        ChunkCoordinates housingCoords = housing.getCoordinates();
        final int xHouse = housingCoords.posX;
        final int yHouse = housingCoords.posY;
        final int zHouse = housingCoords.posZ;
        final int[] area = this.getModifiedArea(genome, housing);
        final int xd = 1 + area[0] / 2;
        final int yd = 1 + area[1] / 2;
        final int zd = 1 + area[2] / 2;
        final int x1 = xHouse - xd + world.rand.nextInt(2 * xd + 1);
        int y1 = yHouse - yd + world.rand.nextInt(2 * yd + 1);
        final int z1 = zHouse - zd + world.rand.nextInt(2 * zd + 1);
        switch (this) {
            case ECTOPLASM: {
                if (world.rand.nextInt(100) < 4) {
                    if (world.isAirBlock(x1, y1, z1) && (world.isBlockNormalCubeDefault(x1, y1 - 1, z1, false) || world.getBlock(x1, y1 - 1, z1) == ExtraBees.ectoplasm)) {
                        world.setBlock(x1, y1, z1, ExtraBees.ectoplasm, 0, 0);
                    }
                    return null;
                }
                break;
            }
            case ACID: {
                if (world.rand.nextInt(100) < 6) {
                    doAcid(world, x1, y1, z1);
                    break;
                }
                break;
            }
            case SPAWN_ZOMBIE: {
                if (world.rand.nextInt(200) < 2) {
                    this.spawnMob(world, x1, y1, z1, "Zombie");
                    break;
                }
                break;
            }
            case SPAWN_SKELETON: {
                if (world.rand.nextInt(200) < 2) {
                    this.spawnMob(world, x1, y1, z1, "Skeleton");
                    break;
                }
                break;
            }
            case SPAWN_CREEPER: {
                if (world.rand.nextInt(200) < 2) {
                    this.spawnMob(world, x1, y1, z1, "Creeper");
                    break;
                }
                break;
            }
            case LIGHTNING: {
                if (world.rand.nextInt(100) < 1 && world.canBlockSeeTheSky(x1, y1, z1) && world instanceof WorldServer) {
                    ((WorldServer) world).addWeatherEffect((Entity) new EntityBeeLightning(world, x1, y1, z1));
                    break;
                }
                break;
            }
            case METEOR: {
                if (world.rand.nextInt(100) < 1 && world.canBlockSeeTheSky(x1, y1, z1)) {
                    ((WorldServer) world).spawnEntityInWorld((Entity) new EntitySmallFireball(world, (double) x1, (double) (y1 + 64), (double) z1, 0.0, -0.6, 0.0));
                    break;
                }
                break;
            }
            case RADIOACTIVE: {
                for (final EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
                    int damage = 4;
                    if (entity instanceof EntityPlayer) {
                        final int count = wearsItems((EntityPlayer) entity);
                        if (count > 3) {
                            continue;
                        }
                        if (count > 2) {
                            damage = 1;
                        } else if (count > 1) {
                            damage = 2;
                        } else if (count > 0) {
                            damage = 3;
                        }
                    }
                    entity.attackEntityFrom(DamageSource.generic, (float) damage);
                }
                break;
            }
            case FOOD: {
                for (final EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
                    if (entity instanceof EntityPlayer) {
                        final EntityPlayer player = (EntityPlayer) entity;
                        player.getFoodStats().addStats(2, 0.2f);
                    }
                }
                break;
            }
            case HUNGER: {
                for (final EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
                    if (entity instanceof EntityPlayer) {
                        final EntityPlayer player = (EntityPlayer) entity;
                        if (world.rand.nextInt(4) < wearsItems(player)) {
                            continue;
                        }
                        player.getFoodStats().addExhaustion(4.0f);
                        player.addPotionEffect(new PotionEffect(Potion.hunger.id, 100));
                    }
                }
                break;
            }
            case BLINDNESS: {
                for (final EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
                    if (entity instanceof EntityPlayer) {
                        final EntityPlayer player = (EntityPlayer) entity;
                        if (world.rand.nextInt(4) < wearsItems(player)) {
                            continue;
                        }
                        player.addPotionEffect(new PotionEffect(Potion.blindness.id, 200));
                    }
                }
                break;
            }
            case SLOW: {
                for (final EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
                    if (entity instanceof EntityPlayer) {
                        final EntityPlayer player = (EntityPlayer) entity;
                        if (world.rand.nextInt(4) < wearsItems(player)) {
                            continue;
                        }
                        player.addPotionEffect(new PotionEffect(Potion.weakness.id, 200));
                    }
                }
                break;
            }
            case CONFUSION: {
                for (final EntityLivingBase entity : this.getEntities(EntityLivingBase.class, genome, housing)) {
                    if (entity instanceof EntityPlayer) {
                        final EntityPlayer player = (EntityPlayer) entity;
                        if (world.rand.nextInt(4) < wearsItems(player)) {
                            continue;
                        }
                        player.addPotionEffect(new PotionEffect(Potion.confusion.id, 200));
                    }
                }
                break;
            }
            case BIRTHDAY:
            case FESTIVAL:
            case FIREWORKS: {
                if (world.rand.nextInt((this == ExtraBeesEffect.FIREWORKS) ? 8 : 12) < 1) {
                    final FireworkCreator.Firework firework = new FireworkCreator.Firework();
                    switch (this) {
                        case BIRTHDAY: {
                            firework.setShape(FireworkCreator.Shape.Star);
                            firework.addColor(16768256);
                            for (final Birthday birthday : ExtraBeesEffect.birthdays) {
                                if (birthday.isToday()) {
                                    firework.addColor(16711680);
                                    firework.addColor(65280);
                                    firework.addColor(255);
                                    firework.setTrail();
                                    break;
                                }
                            }
                        }
                        case FIREWORKS: {
                            firework.setShape(FireworkCreator.Shape.Ball);
                            firework.addColor(genome.getPrimary().getIconColour(0));
                            firework.addColor(genome.getPrimary().getIconColour(0));
                            firework.addColor(genome.getPrimary().getIconColour(1));
                            firework.addColor(genome.getSecondary().getIconColour(0));
                            firework.addColor(genome.getSecondary().getIconColour(0));
                            firework.addColor(genome.getPrimary().getIconColour(1));
                            firework.setTrail();
                            break;
                        }
                    }
                    final EntityFireworkRocket var11 = new EntityFireworkRocket(world, (double) x1, (double) y1, (double) z1, firework.getFirework());
                    if (world.canBlockSeeTheSky(x1, y1, z1)) {
                        ((WorldServer) world).spawnEntityInWorld((Entity) var11);
                    }
                    break;
                }
                break;
            }
            case GRAVITY: {
                final List<Entity> entities2 = this.getEntities(Entity.class, genome, housing);
                for (final Entity entity2 : entities2) {
                    float entityStrength = 1.0f;
                    if (entity2 instanceof EntityPlayer) {
                        entityStrength *= 100.0f;
                    }
                    final double dx = x1 - entity2.posX;
                    final double dy = y1 - entity2.posY;
                    final double dz = z1 - entity2.posZ;
                    if (dx * dx + dy * dy + dz * dz < 2.0) {
                        return null;
                    }
                    final double strength = 0.5 / (dx * dx + dy * dy + dz * dz) * entityStrength;
                    entity2.addVelocity(dx * strength, dy * strength, dz * strength);
                }
                break;
            }
            case THIEF: {
                final List<EntityPlayer> entities3 = this.getEntities(EntityPlayer.class, genome, housing);
                for (final EntityPlayer entity3 : entities3) {
                    final double dx = x1 - entity3.posX;
                    final double dy = y1 - entity3.posY;
                    final double dz = z1 - entity3.posZ;
                    if (dx * dx + dy * dy + dz * dz < 2.0) {
                        return null;
                    }
                    final double strength = 0.5 / (dx * dx + dy * dy + dz * dz);
                    entity3.addVelocity(-dx * strength, -dy * strength, -dz * strength);
                }
                break;
            }
            case TELEPORT: {
                if (world.rand.nextInt(80) > 1) {
                    return null;
                }
                final List<Entity> entities4 = this.getEntities(Entity.class, genome, housing);
                if (entities4.size() == 0) {
                    return null;
                }
                final Entity entity4 = entities4.get(world.rand.nextInt(entities4.size()));
                if (!(entity4 instanceof EntityLiving)) {
                    return null;
                }
                final float jumpDist = 5.0f;
                if (y1 < 4) {
                    y1 = 4;
                }
                if (!world.isAirBlock(x1, y1, z1) || !world.isAirBlock(x1, y1 + 1, z1)) {
                    return null;
                }
                ((EntityLiving) entity4).setPositionAndUpdate((double) x1, (double) y1, (double) z1);
                ((EntityLiving) entity4).addPotionEffect(new PotionEffect(Potion.confusion.id, 160, 10));
                break;
            }
            case WATER: {
                if (world.rand.nextInt(120) > 1) {
                    return null;
                }
                final TileEntity tile = world.getTileEntity(x1, y1, z1);
                if (tile instanceof IFluidHandler) {
                    ((IFluidHandler) tile).fill(ForgeDirection.UP, Binnie.Liquid.getLiquidStack("water", 100), true);
                    break;
                }
                break;
            }
            case BonemealSapling: {
                if (world.rand.nextInt(20) > 1) {
                    return null;
                }
                if (ExtraBeesFlowers.Sapling.isAcceptedFlower(world, null, x1, y1, z1)) {
                    ItemDye.applyBonemeal(new ItemStack(Blocks.dirt, 1), world, x1, y1, z1, (EntityPlayer) null);
                    break;
                }
                break;
            }
            case BonemealFruit: {
                if (world.rand.nextInt(20) > 1) {
                    return null;
                }
                if (ExtraBeesFlowers.Fruit.isAcceptedFlower(world, null, x1, y1, z1)) {
                    ItemDye.applyBonemeal(new ItemStack(Blocks.dirt, 1), world, x1, y1, z1, (EntityPlayer) null);
                    break;
                }
                break;
            }
            case BonemealMushroom: {
                if (world.rand.nextInt(20) > 1) {
                    return null;
                }
                if (world.getBlock(x1, y1, z1) == Blocks.brown_mushroom || world.getBlock(x1, y1, z1) == Blocks.red_mushroom) {
                    ItemDye.applyBonemeal(new ItemStack(Blocks.dirt, 1), world, x1, y1, z1, (EntityPlayer) null);
                    break;
                }
                break;
            }
            case Power: {
                final TileEntity tile2 = world.getTileEntity(x1, y1, z1);
                if (tile2 instanceof IEnergyReceiver) {
                    ((IEnergyReceiver) tile2).receiveEnergy(ForgeDirection.getOrientation(0), 5, true);
                    break;
                }
                break;
            }
        }
        return null;
    }

    protected int[] getModifiedArea(final IBeeGenome genome, final IBeeHousing housing) {
        final int[] territory;
        final int[] area = territory = genome.getTerritory();
        final int n = 0;
        IBeeModifier beeModifier = BeeManager.beeRoot.createBeeHousingModifier(housing);
        territory[n] *= (int) (beeModifier.getTerritoryModifier(genome, 1.0f) * 3.0f);
        final int[] array = area;
        final int n2 = 1;
        array[n2] *= (int) (beeModifier.getTerritoryModifier(genome, 1.0f) * 3.0f);
        final int[] array2 = area;
        final int n3 = 2;
        array2[n3] *= (int) (beeModifier.getTerritoryModifier(genome, 1.0f) * 3.0f);
        if (area[0] < 1) {
            area[0] = 1;
        }
        if (area[1] < 1) {
            area[1] = 1;
        }
        if (area[2] < 1) {
            area[2] = 1;
        }
        return area;
    }

    public IEffectData doFX(final IBeeGenome genome, final IEffectData storedData, final IBeeHousing housing) {
        final int[] territory;
        final int[] area = territory = genome.getTerritory();
        final int n = 0;
        IBeeModifier beeModifier = BeeManager.beeRoot.createBeeHousingModifier(housing);
        territory[n] *= (int) beeModifier.getTerritoryModifier(genome, 1.0f);
        final int[] array = area;
        final int n2 = 1;
        array[n2] *= (int) beeModifier.getTerritoryModifier(genome, 1.0f);
        final int[] array2 = area;
        final int n3 = 2;
        array2[n3] *= (int) beeModifier.getTerritoryModifier(genome, 1.0f);
        if (area[0] < 1) {
            area[0] = 1;
        }
        if (area[1] < 1) {
            area[1] = 1;
        }
        if (area[2] < 1) {
            area[2] = 1;
        }
        PluginApiculture.proxy.addBeeHiveFX("particles/swarm_bee", housing.getWorld(), (double) housing.getXCoord(), (double) housing.getYCoord(), (double) housing.getZCoord(), genome.getPrimary().getIconColour(0), area[0], area[1], area[2]);
        return storedData;
    }

    public String getFX() {
        return this.fx;
    }

    private void setFX(final String string) {
        this.fx = "particles/" + string;
    }

    public <T extends Entity> List<T> getEntities(final Class<T> eClass, final IBeeGenome genome, final IBeeHousing housing) {
        final int[] area = genome.getTerritory();
        ChunkCoordinates coords = housing.getCoordinates();
        final int[] offset = {-Math.round(area[0] / 2), -Math.round(area[1] / 2), -Math.round(area[2] / 2)};
        final int[] min = {coords.posX + offset[0], coords.posY + offset[1], coords.posZ + offset[2]};
        final int[] max = {coords.posX + offset[0] + area[0], coords.posY + offset[1] + area[1], coords.posZ + offset[2] + area[2]};
        final AxisAlignedBB box = AxisAlignedBB.getBoundingBox((double) min[0], (double) min[1], (double) min[2], (double) max[0], (double) max[1], (double) max[2]);
        return (List<T>) housing.getWorld().getEntitiesWithinAABB((Class) eClass, box);
    }

    public String getUnlocalizedName() {
        return this.getUID();
    }

    public static class Birthday {
        int day;
        int month;
        String name;

        private Birthday(final int day, final int month, final String name) {
            this.day = day;
            this.month = month + 1;
            this.name = name;
        }

        public boolean isToday() {
            return Calendar.getInstance().get(5) == this.month && Calendar.getInstance().get(2) == this.day;
        }

        public String getName() {
            return this.name;
        }
    }
}
