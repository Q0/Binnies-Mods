package binnie.extrabees.apiary;

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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import java.util.ArrayList;
import java.util.List;

public class TileExtraBeeAlveary extends TileEntityMachine implements IAlvearyComponent, IBeeModifier, IBeeListener {
    boolean init;
    //IStructureLogic structureLogic;
    private boolean isMaster;
    protected int masterX;
    protected int masterZ;
    protected int masterY;
    List<TileEntity> tiles;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!BinnieCore.proxy.isSimulating(worldObj)) {
            return;
        }
        if (worldObj.getWorldTime() % 200L == 0L) {
            if (!isIntegratedIntoStructure() || isMaster()) {
                validateStructure();
            }
           /* final ITileStructure master = getCentralTE();
            if (master == null) {
                return;
            }
            if (getBeeListener() != null) {
                ((IAlvearyComponent) master).registerBeeListener(getBeeListener());
            }
            if (getBeeModifier() != null) {
                ((IAlvearyComponent) master).registerBeeModifier(getBeeModifier());
            }*/
            init = true;
        }
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        isMaster = nbttagcompound.getBoolean("IsMaster");
        masterX = nbttagcompound.getInteger("MasterX");
        masterY = nbttagcompound.getInteger("MasterY");
        masterZ = nbttagcompound.getInteger("MasterZ");
        if (isMaster) {
            makeMaster();
        }
        //structureLogic.readFromNBT(nbttagcompound);
        //updateAlvearyBlocks();
        init = false;
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setBoolean("IsMaster", isMaster);
        nbttagcompound.setInteger("MasterX", masterX);
        nbttagcompound.setInteger("MasterY", masterY);
        nbttagcompound.setInteger("MasterZ", masterZ);
        //structureLogic.writeToNBT(nbttagcompound);
    }

    AlvearyMachine.AlvearyPackage getAlvearyPackage() {
        return (AlvearyMachine.AlvearyPackage) getMachine().getPackage();
    }

    public TileExtraBeeAlveary() {
        init = false;
        masterY = -99;
        tiles = new ArrayList<TileEntity>();
        //structureLogic = Binnie.Genetics.getBeeRoot().createAlvearyStructureLogic(this);
    }

    public TileExtraBeeAlveary(final AlvearyMachine.AlvearyPackage alvearyPackage) {
        super(alvearyPackage);
        init = false;
        masterY = -99;
        tiles = new ArrayList<TileEntity>();
        //structureLogic = Binnie.Genetics.getBeeRoot().createAlvearyStructureLogic(this);
    }

    public String getTypeUID() {
        //return structureLogic.getTypeUID();
        return null;
    }

    public void makeMaster() {
    }

    public void onStructureReset() {
        setCentralTE(null);
        isMaster = false;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        //updateAlvearyBlocks();
    }

    /*public ITileStructure getCentralTE() {
        if (worldObj == null || !isIntegratedIntoStructure()) {
            return null;
        }
        if (isMaster()) {
            return (ITileStructure) this;
        }
        final TileEntity tile = worldObj.getTileEntity(masterX, masterY, masterZ);
        if (!(tile instanceof ITileStructure)) {
            return null;
        }
        final ITileStructure master = (ITileStructure) worldObj.getTileEntity(masterX, masterY, masterZ);
        if (master.isMaster()) {
            return master;
        }
        return null;
    }*/

    public void validateStructure() {
        //structureLogic.validateStructure();
        //updateAlvearyBlocks();
    }

    private boolean isSameTile(final TileEntity tile) {
        return tile.xCoord == xCoord && tile.yCoord == yCoord && tile.zCoord == zCoord;
    }

    public void setCentralTE(final TileEntity tile) {
        if (tile == null || tile == this || isSameTile(tile)) {
            final boolean b = false;
            masterZ = (b ? 1 : 0);
            masterX = (b ? 1 : 0);
            masterY = -99;
            //updateAlvearyBlocks();
            return;
        }
        isMaster = false;
        masterX = tile.xCoord;
        masterY = tile.yCoord;
        masterZ = tile.zCoord;
        markDirty();
        /*if (getBeeListener() != null) {
            ((IAlvearyComponent) tile).registerBeeListener(getBeeListener());
        }
        if (getBeeModifier() != null) {
            ((IAlvearyComponent) tile).registerBeeModifier(getBeeModifier());
        }*/
        //updateAlvearyBlocks();
    }

    public boolean isMaster() {
        return isMaster;
    }

    protected boolean hasMaster() {
        return masterY >= 0;
    }

    public boolean isIntegratedIntoStructure() {
        return isMaster || masterY >= 0;
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
        return getMachine().getInterface(IBeeModifier.class);
    }

    public IBeeListener getBeeListener() {
        return getMachine().getInterface(IBeeListener.class);
    }

    public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
        return (getBeeModifier() == null) ? 1.0f : getBeeModifier().getTerritoryModifier(genome, currentModifier);
    }

    public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return (getBeeModifier() == null) ? 1.0f : getBeeModifier().getMutationModifier(genome, mate, currentModifier);
    }

    public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return (getBeeModifier() == null) ? 1.0f : getBeeModifier().getLifespanModifier(genome, mate, currentModifier);
    }

    public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
        return (getBeeModifier() == null) ? 1.0f : getBeeModifier().getProductionModifier(genome, currentModifier);
    }

    public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
        return (getBeeModifier() == null) ? 1.0f : getBeeModifier().getFloweringModifier(genome, currentModifier);
    }

    public boolean isSealed() {
        return getBeeModifier() != null && getBeeModifier().isSealed();
    }

    public boolean isSelfLighted() {
        return getBeeModifier() != null && getBeeModifier().isSelfLighted();
    }

    public boolean isSunlightSimulated() {
        return getBeeModifier() != null && getBeeModifier().isSunlightSimulated();
    }

    public boolean isHellish() {
        return getBeeModifier() != null && getBeeModifier().isHellish();
    }

    public void registerBeeListener(final IBeeListener event) {
    }

    public void removeBeeListener(final IBeeListener event) {
    }

    /*public void onQueenChange(final ItemStack queen) {
        if (getBeeListener() != null) {
            getBeeListener().onQueenChange(queen);
        }
    }*/

    public void wearOutEquipment(final int amount) {
        if (getBeeListener() != null) {
            getBeeListener().wearOutEquipment(amount);
        }
    }

    @Override
    public void onQueenDeath() {

    }

    @Override
    public boolean onPollenRetrieved(IIndividual pollen) {
        return false;
    }

    /*public void onQueenDeath(final IBee queen) {
        if (getBeeListener() != null) {
            getBeeListener().onQueenDeath(queen);
        }
    }*/

    /*public void onPostQueenDeath(final IBee queen) {
        if (getBeeListener() != null) {
            getBeeListener().onPostQueenDeath(queen);
        }
    }*/

    public boolean onPollenRetrieved(final IBee queen, final IIndividual pollen, final boolean isHandled) {
        return false;
    }

    public boolean onEggLaid(final IBee queen) {
        return false;
    }

    public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }

    /*public IBeeHousing getBeeHousing() {
        return (getCentralTE() == null) ? null : ((IBeeHousing) getCentralTE());
    }*/

    /*public List<TileEntity> getAlvearyBlocks() {
        updateAlvearyBlocks();
        return tiles;
    }*/

    /*private void updateAlvearyBlocks() {
        tiles.clear();
        if (getCentralTE() != null) {
            final ITileStructure struct = getCentralTE();
            if (!struct.isIntegratedIntoStructure()) {
                return;
            }
            final TileEntity central = (TileEntity) struct;
            for (int x = -2; x <= 2; ++x) {
                for (int z = -2; z <= 2; ++z) {
                    for (int y = -2; y <= 2; ++y) {
                        final TileEntity tile = getWorldObj().getTileEntity(xCoord + x, yCoord + y, zCoord + z);
                        if (tile != null && tile instanceof ITileStructure && ((ITileStructure) tile).getCentralTE() == struct) {
                            tiles.add(tile);
                        }
                    }
                }
            }
        }
    }*/

    public ISidedInventory getStructureInventory() {
        return getMachine().getInterface(ISidedInventory.class);
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

    public IBeeHousing getBeeHousing() {
        return null;
    }
}
