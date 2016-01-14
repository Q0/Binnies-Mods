package binnie.extrabees.apiary;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.TileEntityMachine;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import com.mojang.authlib.GameProfile;
import forestry.api.apiculture.*;
import forestry.api.genetics.IIndividual;
import forestry.api.multiblock.IAlvearyComponent;
import forestry.api.multiblock.IMultiblockController;
import forestry.api.multiblock.IMultiblockLogicAlveary;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import java.util.ArrayList;
import java.util.List;

public class TileExtraBeeAlveary extends TileEntityMachine implements IAlvearyComponent, IBeeModifier, IBeeListener {
    boolean init;
    private boolean isMaster;
    protected int masterX;
    protected int masterZ;
    protected int masterY;
    List<TileEntity> tiles;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!BinnieCore.proxy.isSimulating(this.worldObj)) {
            return;
        }
        if (this.worldObj.getWorldTime() % 200L == 0L) {
            if (!this.isIntegratedIntoStructure() || this.isMaster()) {
                this.validateStructure();
            }
            this.init = true;
        }
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.isMaster = nbttagcompound.getBoolean("IsMaster");
        this.masterX = nbttagcompound.getInteger("MasterX");
        this.masterY = nbttagcompound.getInteger("MasterY");
        this.masterZ = nbttagcompound.getInteger("MasterZ");
        if (this.isMaster) {
            this.makeMaster();
        }

        this.updateAlvearyBlocks();
        this.init = false;
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setBoolean("IsMaster", this.isMaster);
        nbttagcompound.setInteger("MasterX", this.masterX);
        nbttagcompound.setInteger("MasterY", this.masterY);
        nbttagcompound.setInteger("MasterZ", this.masterZ);

    }

    AlvearyMachine.AlvearyPackage getAlvearyPackage() {
        return (AlvearyMachine.AlvearyPackage) this.getMachine().getPackage();
    }

    public TileExtraBeeAlveary() {
        this.init = false;
        this.masterY = -99;
        this.tiles = new ArrayList<TileEntity>();
    }

    public TileExtraBeeAlveary(final AlvearyMachine.AlvearyPackage alvearyPackage) {
        super(alvearyPackage);
        this.init = false;
        this.masterY = -99;
        this.tiles = new ArrayList<TileEntity>();
    }

    public void makeMaster() {
    }

    public void onStructureReset() {
        this.setCentralTE(null);
        this.isMaster = false;
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.updateAlvearyBlocks();
    }

    public void validateStructure() {
        this.updateAlvearyBlocks();
    }

    private boolean isSameTile(final TileEntity tile) {
        return tile.xCoord == this.xCoord && tile.yCoord == this.yCoord && tile.zCoord == this.zCoord;
    }

    public void setCentralTE(final TileEntity tile) {
        if (tile == null || tile == this || this.isSameTile(tile)) {
            final boolean b = false;
            this.masterZ = (b ? 1 : 0);
            this.masterX = (b ? 1 : 0);
            this.masterY = -99;
            this.updateAlvearyBlocks();
            return;
        }
        this.isMaster = false;
        this.masterX = tile.xCoord;
        this.masterY = tile.yCoord;
        this.masterZ = tile.zCoord;
        this.markDirty();
        this.updateAlvearyBlocks();
    }

    public boolean isMaster() {
        return this.isMaster;
    }

    protected boolean hasMaster() {
        return this.masterY >= 0;
    }

    public boolean isIntegratedIntoStructure() {
        return this.isMaster || this.masterY >= 0;
    }

    public void registerBeeModifier(final IBeeModifier modifier) {
    }

    public void removeBeeModifier(final IBeeModifier modifier) {
    }

    public void addTemperatureChange(final float change, final float boundaryDown, final float boundaryUp) {
    }

    public void addHumidityChange(final float change, final float boundaryDown, final float boundaryUp) {
    }

    public boolean hasFunction() {
        return true;
    }

    public IBeeModifier getBeeModifier() {
        return this.getMachine().getInterface(IBeeModifier.class);
    }

    public IBeeListener getBeeListener() {
        return this.getMachine().getInterface(IBeeListener.class);
    }

    public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
        return (this.getBeeModifier() == null) ? 1.0f : this.getBeeModifier().getTerritoryModifier(genome, currentModifier);
    }

    public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return (this.getBeeModifier() == null) ? 1.0f : this.getBeeModifier().getMutationModifier(genome, mate, currentModifier);
    }

    public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return (this.getBeeModifier() == null) ? 1.0f : this.getBeeModifier().getLifespanModifier(genome, mate, currentModifier);
    }

    public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
        return (this.getBeeModifier() == null) ? 1.0f : this.getBeeModifier().getProductionModifier(genome, currentModifier);
    }

    public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
        return (this.getBeeModifier() == null) ? 1.0f : this.getBeeModifier().getFloweringModifier(genome, currentModifier);
    }

    public boolean isSealed() {
        return this.getBeeModifier() != null && this.getBeeModifier().isSealed();
    }

    public boolean isSelfLighted() {
        return this.getBeeModifier() != null && this.getBeeModifier().isSelfLighted();
    }

    public boolean isSunlightSimulated() {
        return this.getBeeModifier() != null && this.getBeeModifier().isSunlightSimulated();
    }

    public boolean isHellish() {
        return this.getBeeModifier() != null && this.getBeeModifier().isHellish();
    }

    public void registerBeeListener(final IBeeListener event) {
    }

    public void removeBeeListener(final IBeeListener event) {
    }

    public void onQueenChange(final ItemStack queen) {
        if (this.getBeeListener() != null) {
        }
    }

    public void wearOutEquipment(final int amount) {
        if (this.getBeeListener() != null) {
            this.getBeeListener().wearOutEquipment(amount);
        }
    }

    @Override
    public void onQueenDeath() {

    }

    @Override
    public boolean onPollenRetrieved(IIndividual pollen) {
        return false;
    }

    public void onQueenDeath(final IBee queen) {
        if (this.getBeeListener() != null) {
        }
    }

    public void onPostQueenDeath(final IBee queen) {
        if (this.getBeeListener() != null) {
        }
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

    public List<TileEntity> getAlvearyBlocks() {
        this.updateAlvearyBlocks();
        return this.tiles;
    }

    private void updateAlvearyBlocks() {
        this.tiles.clear();
    }

    public ISidedInventory getStructureInventory() {
        return this.getMachine().getInterface(ISidedInventory.class);
    }

    @Override
    public ChunkCoordinates getCoordinates() {
        return null;
    }

    @Override
    public GameProfile getOwner() {
        return null;
    }

    @Override
    public IMultiblockLogicAlveary getMultiblockLogic() {
        return null;
    }

    @Override
    public void onMachineAssembled(IMultiblockController multiblockController, ChunkCoordinates minCoord, ChunkCoordinates maxCoord) {

    }

    @Override
    public void onMachineBroken() {

    }
}
