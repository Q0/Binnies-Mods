package binnie.extrabees.apiary;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.TileEntityMachine;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import forestry.api.apiculture.IAlvearyComponent;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.core.IStructureLogic;
import forestry.api.core.ITileStructure;
import forestry.api.genetics.IIndividual;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileExtraBeeAlveary extends TileEntityMachine implements IAlvearyComponent, IBeeModifier, IBeeListener {
   boolean init = false;
   IStructureLogic structureLogic = Binnie.Genetics.getBeeRoot().createAlvearyStructureLogic(this);
   private boolean isMaster;
   protected int masterX;
   protected int masterZ;
   protected int masterY = -99;
   List tiles = new ArrayList();

   public void updateEntity() {
      super.updateEntity();
      if(BinnieCore.proxy.isSimulating(this.worldObj)) {
         if(this.worldObj.getWorldTime() % 200L == 0L) {
            if(!this.isIntegratedIntoStructure() || this.isMaster()) {
               this.validateStructure();
            }

            ITileStructure master = this.getCentralTE();
            if(master == null) {
               return;
            }

            if(this.getBeeListener() != null) {
               ((IAlvearyComponent)master).registerBeeListener(this.getBeeListener());
            }

            if(this.getBeeModifier() != null) {
               ((IAlvearyComponent)master).registerBeeModifier(this.getBeeModifier());
            }

            this.init = true;
         }

      }
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      super.readFromNBT(nbttagcompound);
      this.isMaster = nbttagcompound.getBoolean("IsMaster");
      this.masterX = nbttagcompound.getInteger("MasterX");
      this.masterY = nbttagcompound.getInteger("MasterY");
      this.masterZ = nbttagcompound.getInteger("MasterZ");
      if(this.isMaster) {
         this.makeMaster();
      }

      this.structureLogic.readFromNBT(nbttagcompound);
      this.updateAlvearyBlocks();
      this.init = false;
   }

   public void writeToNBT(NBTTagCompound nbttagcompound) {
      super.writeToNBT(nbttagcompound);
      nbttagcompound.setBoolean("IsMaster", this.isMaster);
      nbttagcompound.setInteger("MasterX", this.masterX);
      nbttagcompound.setInteger("MasterY", this.masterY);
      nbttagcompound.setInteger("MasterZ", this.masterZ);
      this.structureLogic.writeToNBT(nbttagcompound);
   }

   AlvearyMachine.AlvearyPackage getAlvearyPackage() {
      return (AlvearyMachine.AlvearyPackage)this.getMachine().getPackage();
   }

   public TileExtraBeeAlveary() {
      super();
   }

   public TileExtraBeeAlveary(AlvearyMachine.AlvearyPackage alvearyPackage) {
      super(alvearyPackage);
   }

   public String getTypeUID() {
      return this.structureLogic.getTypeUID();
   }

   public void makeMaster() {
   }

   public void onStructureReset() {
      this.setCentralTE((TileEntity)null);
      this.isMaster = false;
      this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
      this.updateAlvearyBlocks();
   }

   public ITileStructure getCentralTE() {
      if(this.worldObj != null && this.isIntegratedIntoStructure()) {
         if(!this.isMaster()) {
            TileEntity tile = this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
            if(tile instanceof ITileStructure) {
               ITileStructure master = (ITileStructure)this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
               return master.isMaster()?master:null;
            } else {
               return null;
            }
         } else {
            return this;
         }
      } else {
         return null;
      }
   }

   public void validateStructure() {
      this.structureLogic.validateStructure();
      this.updateAlvearyBlocks();
   }

   private boolean isSameTile(TileEntity tile) {
      return tile.xCoord == this.xCoord && tile.yCoord == this.yCoord && tile.zCoord == this.zCoord;
   }

   public void setCentralTE(TileEntity tile) {
      if(tile != null && tile != this && !this.isSameTile(tile)) {
         this.isMaster = false;
         this.masterX = tile.xCoord;
         this.masterY = tile.yCoord;
         this.masterZ = tile.zCoord;
         this.markDirty();
         if(this.getBeeListener() != null) {
            ((IAlvearyComponent)tile).registerBeeListener(this.getBeeListener());
         }

         if(this.getBeeModifier() != null) {
            ((IAlvearyComponent)tile).registerBeeModifier(this.getBeeModifier());
         }

         this.updateAlvearyBlocks();
      } else {
         this.masterX = this.masterZ = 0;
         this.masterY = -99;
         this.updateAlvearyBlocks();
      }
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

   public void registerBeeModifier(IBeeModifier modifier) {
   }

   public void removeBeeModifier(IBeeModifier modifier) {
   }

   public void addTemperatureChange(float change, float boundaryDown, float boundaryUp) {
   }

   public void addHumidityChange(float change, float boundaryDown, float boundaryUp) {
   }

   public boolean hasFunction() {
      return true;
   }

   public IBeeModifier getBeeModifier() {
      return (IBeeModifier)this.getMachine().getInterface(IBeeModifier.class);
   }

   public IBeeListener getBeeListener() {
      return (IBeeListener)this.getMachine().getInterface(IBeeListener.class);
   }

   public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
      return this.getBeeModifier() == null?1.0F:this.getBeeModifier().getTerritoryModifier(genome, currentModifier);
   }

   public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
      return this.getBeeModifier() == null?1.0F:this.getBeeModifier().getMutationModifier(genome, mate, currentModifier);
   }

   public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
      return this.getBeeModifier() == null?1.0F:this.getBeeModifier().getLifespanModifier(genome, mate, currentModifier);
   }

   public float getProductionModifier(IBeeGenome genome, float currentModifier) {
      return this.getBeeModifier() == null?1.0F:this.getBeeModifier().getProductionModifier(genome, currentModifier);
   }

   public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
      return this.getBeeModifier() == null?1.0F:this.getBeeModifier().getFloweringModifier(genome, currentModifier);
   }

   public boolean isSealed() {
      return this.getBeeModifier() == null?false:this.getBeeModifier().isSealed();
   }

   public boolean isSelfLighted() {
      return this.getBeeModifier() == null?false:this.getBeeModifier().isSelfLighted();
   }

   public boolean isSunlightSimulated() {
      return this.getBeeModifier() == null?false:this.getBeeModifier().isSunlightSimulated();
   }

   public boolean isHellish() {
      return this.getBeeModifier() == null?false:this.getBeeModifier().isHellish();
   }

   public void registerBeeListener(IBeeListener event) {
   }

   public void removeBeeListener(IBeeListener event) {
   }

   public void onQueenChange(ItemStack queen) {
      if(this.getBeeListener() != null) {
         this.getBeeListener().onQueenChange(queen);
      }

   }

   public void wearOutEquipment(int amount) {
      if(this.getBeeListener() != null) {
         this.getBeeListener().wearOutEquipment(amount);
      }

   }

   public void onQueenDeath(IBee queen) {
      if(this.getBeeListener() != null) {
         this.getBeeListener().onQueenDeath(queen);
      }

   }

   public void onPostQueenDeath(IBee queen) {
      if(this.getBeeListener() != null) {
         this.getBeeListener().onPostQueenDeath(queen);
      }

   }

   public boolean onPollenRetrieved(IBee queen, IIndividual pollen, boolean isHandled) {
      return false;
   }

   public boolean onEggLaid(IBee queen) {
      return false;
   }

   public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
      return 1.0F;
   }

   public IBeeHousing getBeeHousing() {
      return this.getCentralTE() == null?null:(IBeeHousing)this.getCentralTE();
   }

   public List getAlvearyBlocks() {
      this.updateAlvearyBlocks();
      return this.tiles;
   }

   private void updateAlvearyBlocks() {
      this.tiles.clear();
      if(this.getCentralTE() != null) {
         ITileStructure struct = this.getCentralTE();
         if(!struct.isIntegratedIntoStructure()) {
            return;
         }

         TileEntity central = (TileEntity)struct;

         for(int x = -2; x <= 2; ++x) {
            for(int z = -2; z <= 2; ++z) {
               for(int y = -2; y <= 2; ++y) {
                  TileEntity tile = this.getWorldObj().getTileEntity(this.xCoord + x, this.yCoord + y, this.zCoord + z);
                  if(tile != null && tile instanceof ITileStructure && ((ITileStructure)tile).getCentralTE() == struct) {
                     this.tiles.add(tile);
                  }
               }
            }
         }
      }

   }

   public ISidedInventory getStructureInventory() {
      return (ISidedInventory)this.getMachine().getInterface(ISidedInventory.class);
   }
}
