package binnie.core.genetics;

import com.mojang.authlib.GameProfile;
import forestry.api.apiculture.*;
import forestry.api.core.IErrorLogic;
import forestry.api.genetics.IIndividual;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;

public class VirtualBeeHousing extends VirtualHousing implements IBeeHousing {
    public VirtualBeeHousing(final EntityPlayer player) {
        super(player);
    }

    public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }

    public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return 1.0f;
    }

    public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return 1.0f;
    }

    public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }

    public ItemStack getQueen() {
        return null;
    }

    public void setQueen(final ItemStack itemstack) {
    }

    public ItemStack getDrone() {
        return null;
    }

    public void setDrone(final ItemStack itemstack) {
    }

    public boolean canBreed() {
        return true;
    }

    @Override
    public boolean addProduct(final ItemStack product, final boolean all) {
        return false;
    }

    public void wearOutEquipment(final int amount) {
    }

    public void onQueenChange(final ItemStack queen) {
    }

    public boolean isSealed() {
        return false;
    }

    public boolean isSelfLighted() {
        return false;
    }

    public boolean isSunlightSimulated() {
        return false;
    }

    public boolean isHellish() {
        return this.getBiomeId() == BiomeGenBase.hell.biomeID;
    }

    public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }

    public void onQueenDeath(final IBee queen) {
    }

    public void onPostQueenDeath(final IBee queen) {
    }

    public boolean onPollenRetrieved(final IBee queen, final IIndividual pollen, final boolean isHandled) {
        return false;
    }

    public boolean onEggLaid(final IBee queen) {
        return false;
    }

    public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }

    @Override
    public Iterable<IBeeModifier> getBeeModifiers() {
        //TODO: UPD TO Forestry4
        return null;
    }

    @Override
    public Iterable<IBeeListener> getBeeListeners() {
        //TODO: UPD TO Forestry4
        return null;
    }

    @Override
    public IBeeHousingInventory getBeeInventory() {
        //TODO: UPD TO Forestry4
        return null;
    }

    @Override
    public IBeekeepingLogic getBeekeepingLogic() {
        //TODO: UPD TO Forestry4
        return null;
    }

    @Override
    public int getBlockLightValue() {
        //TODO: UPD TO Forestry4
        return 0;
    }

    @Override
    public boolean canBlockSeeTheSky() {
        //TODO: UPD TO Forestry4
        return false;
    }

    @Override
    public GameProfile getOwner() {
        //TODO: UPD TO Forestry4
        return null;
    }

    @Override
    public Vec3 getBeeFXCoordinates() {
        //TODO: UPD TO Forestry4
        return null;
    }

    @Override
    public IErrorLogic getErrorLogic() {
        //TODO: UPD TO Forestry4
        return null;
    }
}
