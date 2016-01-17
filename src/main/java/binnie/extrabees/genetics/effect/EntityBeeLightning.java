package binnie.extrabees.genetics.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EntityBeeLightning extends EntityLightningBolt {
    int lightningState = 2;
    int boltLivingTime;

    public EntityBeeLightning(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
        boltLivingTime = rand.nextInt(3) + 1;
    }

    public void onUpdate() {
        onEntityUpdate();
        lightningState--;

        if (lightningState < 0) {
            if (boltLivingTime == 0) {
                setDead();
            }
            else if (lightningState < -rand.nextInt(10)) {
                boltLivingTime--;
                lightningState = 1;
                boltVertex = rand.nextLong();

                if (!worldObj.isRemote &&
                    worldObj.doChunksNearChunkExist(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), 10)) {
                    int i = MathHelper.floor_double(posX);
                    int j = MathHelper.floor_double(posY);
                    int k = MathHelper.floor_double(posZ);

                    if (worldObj.getBlock(i, j, k) == null && Blocks.fire.canPlaceBlockAt(worldObj, i, j, k)) {
                        worldObj.setBlock(i, j, k, Blocks.fire);
                    }
                }
            }
        }

        if (lightningState >= 0) {
            if (worldObj.isRemote) {
                worldObj.lastLightningBolt = 2;
            }
            else {
                double d0 = 3.0D;
                List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(posX - d0, posY - d0, posZ - d0, posX + d0, posY + 6.0D + d0, posZ + d0));

                for (Entity entity : list) {
                    entity.onStruckByLightning(this);
                }
            }
        }
    }
}
