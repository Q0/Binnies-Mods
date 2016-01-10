package binnie.craftgui.minecraft;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IErrorStateSource;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.PowerInfo;
import binnie.core.machines.power.ProcessInfo;
import binnie.core.machines.power.TankInfo;
import binnie.core.machines.transfer.TransferRequest;
import binnie.core.network.packet.MessageContainerUpdate;
import binnie.craftgui.minecraft.CustomSlot;
import binnie.craftgui.minecraft.IWindowAffectsShiftClick;
import binnie.craftgui.minecraft.InventoryType;
import binnie.craftgui.minecraft.ListMap;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.WindowInventory;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.minecraft.control.EnumHighlighting;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerCraftGUI extends Container {
   private Window window;
   private Map syncedNBT = new HashMap();
   private Map sentNBT = new HashMap();
   private Map syncedTanks = new HashMap();
   private PowerInfo syncedPower = new PowerInfo();
   private ProcessInfo syncedProcess = new ProcessInfo();
   private int errorType = 0;
   private ErrorState error = null;
   private int mousedOverSlotNumber = -1;

   public ContainerCraftGUI(Window window) {
      super();
      this.window = window;
      IMachine machine = Machine.getMachine(window.getInventory());
      if(this.getSide() == Side.SERVER) {
         this.inventoryItemStacks = new ListMap();
         this.inventorySlots = new ListMap();
         if(machine != null) {
            GameProfile user = machine.getOwner();
            if(user != null) {
               NBTTagCompound nbt = new NBTTagCompound();
               nbt.setString("username", user.getName());
               this.sendNBTToClient("username", nbt);
            }
         }
      }

   }

   protected Slot addSlotToContainer(Slot slot) {
      return super.addSlotToContainer(slot);
   }

   private Side getSide() {
      return this.window.isServer()?Side.SERVER:Side.CLIENT;
   }

   public Slot getSlot(int par1) {
      return par1 >= 0 && par1 < this.inventorySlots.size()?(Slot)this.inventorySlots.get(par1):null;
   }

   public void putStackInSlot(int par1, ItemStack par2ItemStack) {
      if(this.getSlot(par1) != null) {
         this.getSlot(par1).putStack(par2ItemStack);
      }

   }

   public void putStacksInSlots(ItemStack[] par1ArrayOfItemStack) {
      for(int i = 0; i < par1ArrayOfItemStack.length; ++i) {
         if(this.getSlot(i) != null) {
            this.getSlot(i).putStack(par1ArrayOfItemStack[i]);
         }
      }

   }

   public void onContainerClosed(EntityPlayer par1EntityPlayer) {
      super.onContainerClosed(par1EntityPlayer);
      WindowInventory inventory = this.window.getWindowInventory();

      for(int i = 0; i < inventory.getSizeInventory(); ++i) {
         if(inventory.dispenseOnClose(i)) {
            ItemStack stack = inventory.getStackInSlot(i);
            if(stack != null) {
               stack = (new TransferRequest(stack, par1EntityPlayer.inventory)).transfer(true);
               if(stack != null) {
                  par1EntityPlayer.dropPlayerItemWithRandomChoice(stack, false);
               }
            }
         }
      }

   }

   public ItemStack slotClick(int slotNum, int mouseButton, int modifier, EntityPlayer player) {
      Slot slot = this.getSlot(slotNum);
      if(slot instanceof CustomSlot && ((CustomSlot)slot).handleClick()) {
         ((CustomSlot)slot).onSlotClick(this, mouseButton, modifier, player);
         return player.inventory.getItemStack();
      } else {
         ItemStack stack = super.slotClick(slotNum, mouseButton, modifier, player);
         return stack;
      }
   }

   public void sendNBTToClient(String key, NBTTagCompound nbt) {
      this.syncedNBT.put(key, nbt);
   }

   public boolean canInteractWith(EntityPlayer var1) {
      return true;
   }

   public final ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
      return this.shiftClick(player, slotID);
   }

   private ItemStack shiftClick(EntityPlayer player, int slotnumber) {
      TransferRequest request = this.getShiftClickRequest(player, slotnumber);
      if(request == null) {
         return null;
      } else {
         ItemStack itemstack = request.transfer(true);
         Slot shiftClickedSlot = (Slot)this.inventorySlots.get(slotnumber);
         shiftClickedSlot.putStack(itemstack);
         shiftClickedSlot.onSlotChanged();
         return null;
      }
   }

   private TransferRequest getShiftClickRequest(EntityPlayer player, int slotnumber) {
      if(slotnumber < 0) {
         return null;
      } else {
         Slot shiftClickedSlot = (Slot)this.inventorySlots.get(slotnumber);
         ItemStack itemstack = null;
         if(shiftClickedSlot.getHasStack()) {
            itemstack = shiftClickedSlot.getStack().copy();
         }

         IInventory playerInventory = player.inventory;
         IInventory containerInventory = this.window.getInventory();
         IInventory windowInventory = this.window.getWindowInventory();
         IInventory fromPlayer = containerInventory == null?windowInventory:containerInventory;
         int[] target = new int[36];

         for(int i = 0; i < 36; target[i] = i++) {
            ;
         }

         TransferRequest request;
         if(shiftClickedSlot.inventory == playerInventory) {
            request = (new TransferRequest(itemstack, fromPlayer)).setOrigin(shiftClickedSlot.inventory);
         } else {
            request = (new TransferRequest(itemstack, playerInventory)).setOrigin(shiftClickedSlot.inventory).setTargetSlots(target);
         }

         if(this.window instanceof IWindowAffectsShiftClick) {
            ((IWindowAffectsShiftClick)this.window).alterRequest(request);
         }

         return request;
      }
   }

   public final ItemStack tankClick(EntityPlayer player, int slotID) {
      if(player.inventory.getItemStack() == null) {
         return null;
      } else {
         ItemStack heldItem = player.inventory.getItemStack().copy();
         heldItem = (new TransferRequest(heldItem, this.window.getInventory())).setOrigin(player.inventory).setTargetSlots(new int[0]).setTargetTanks(new int[]{slotID}).transfer(true);
         player.inventory.setItemStack(heldItem);
         if(player instanceof EntityPlayerMP) {
            ((EntityPlayerMP)player).updateHeldItem();
         }

         return heldItem;
      }
   }

   public boolean handleNBT(Side side, EntityPlayer player, String name, NBTTagCompound action) {
      if(side == Side.SERVER) {
         if(name.equals("tank-click")) {
            this.tankClick(player, action.getByte("id"));
         }

         if(name.equals("slot-reg")) {
            int type = action.getByte("t");
            int index = action.getShort("i");
            int slotNumber = action.getShort("n");
            this.getOrCreateSlot(InventoryType.values()[type % 4], index, slotNumber);
         }
      }

      if(name.contains("tank-update")) {
         this.onTankUpdate(action);
      } else if(name.equals("power-update")) {
         this.onPowerUpdate(action);
      } else if(name.equals("process-update")) {
         this.onProcessUpdate(action);
      } else if(name.equals("error-update")) {
         this.onErrorUpdate(action);
      } else if(name.equals("mouse-over-slot")) {
         this.onMouseOverSlot(player, action);
      } else if(name.equals("shift-click-info")) {
         this.onRecieveShiftClickHighlights(player, action);
      }

      return false;
   }

   public void detectAndSendChanges() {
      super.detectAndSendChanges();
      ITankMachine tanks = (ITankMachine)Machine.getInterface(ITankMachine.class, this.window.getInventory());
      IPoweredMachine powered = (IPoweredMachine)Machine.getInterface(IPoweredMachine.class, this.window.getInventory());
      IErrorStateSource error = (IErrorStateSource)Machine.getInterface(IErrorStateSource.class, this.window.getInventory());
      IProcess process = (IProcess)Machine.getInterface(IProcess.class, this.window.getInventory());
      if(tanks != null && this.window.isServer()) {
         for(int i = 0; i < tanks.getTankInfos().length; ++i) {
            TankInfo tank = tanks.getTankInfos()[i];
            if(!this.getTankInfo(i).equals(tank)) {
               this.syncedNBT.put("tank-update-" + i, this.createTankNBT(i, tank));
               this.syncedTanks.put(Integer.valueOf(i), tank);
            }
         }
      }

      if(powered != null && this.window.isServer()) {
         this.syncedNBT.put("power-update", this.createPowerNBT(powered.getPowerInfo()));
      }

      if(process != null && this.window.isServer()) {
         this.syncedNBT.put("process-update", this.createProcessNBT(process.getInfo()));
      }

      if(error != null && this.window.isServer()) {
         this.syncedNBT.put("error-update", this.createErrorNBT(error));
      }

      INetwork.SendGuiNBT machineSync = (INetwork.SendGuiNBT)Machine.getInterface(INetwork.SendGuiNBT.class, this.window.getInventory());
      if(machineSync != null) {
         machineSync.sendGuiNBT(this.syncedNBT);
      }

      Map<String, NBTTagCompound> sentThisTime = new HashMap();

      for(Entry<String, NBTTagCompound> nbt : this.syncedNBT.entrySet()) {
         ((NBTTagCompound)nbt.getValue()).setString("type", (String)nbt.getKey());
         boolean shouldSend = true;
         NBTTagCompound lastSent = (NBTTagCompound)this.sentNBT.get(nbt.getKey());
         if(lastSent != null) {
            shouldSend = !lastSent.equals(nbt.getValue());
         }

         if(shouldSend) {
            for(int j = 0; j < this.crafters.size(); ++j) {
               if(this.crafters.get(j) instanceof EntityPlayerMP) {
                  EntityPlayerMP player = (EntityPlayerMP)this.crafters.get(j);
                  BinnieCore.proxy.sendToPlayer(new MessageContainerUpdate((NBTTagCompound)nbt.getValue()), player);
               }
            }

            sentThisTime.put(nbt.getKey(), nbt.getValue());
         }
      }

      this.sentNBT.putAll(sentThisTime);
      this.syncedNBT.clear();
   }

   private NBTTagCompound createErrorNBT(IErrorStateSource error) {
      NBTTagCompound nbt = new NBTTagCompound();
      ErrorState state = null;
      if(error.canWork() != null) {
         nbt.setByte("type", (byte)0);
         state = error.canWork();
      } else if(error.canProgress() != null) {
         nbt.setByte("type", (byte)1);
         state = error.canProgress();
      }

      if(state != null) {
         state.writeToNBT(nbt);
      }

      return nbt;
   }

   public NBTTagCompound createPowerNBT(PowerInfo powerInfo) {
      NBTTagCompound nbt = new NBTTagCompound();
      powerInfo.writeToNBT(nbt);
      return nbt;
   }

   public NBTTagCompound createProcessNBT(ProcessInfo powerInfo) {
      NBTTagCompound nbt = new NBTTagCompound();
      powerInfo.writeToNBT(nbt);
      return nbt;
   }

   public NBTTagCompound createTankNBT(int tank, TankInfo tankInfo) {
      NBTTagCompound nbt = new NBTTagCompound();
      tankInfo.writeToNBT(nbt);
      nbt.setByte("tank", (byte)tank);
      return nbt;
   }

   public void onTankUpdate(NBTTagCompound nbt) {
      int tankID = nbt.getByte("tank");
      TankInfo tank = new TankInfo();
      tank.readFromNBT(nbt);
      this.syncedTanks.put(Integer.valueOf(tankID), tank);
   }

   public void onProcessUpdate(NBTTagCompound nbt) {
      this.syncedProcess = new ProcessInfo();
      this.syncedProcess.readFromNBT(nbt);
   }

   public void onPowerUpdate(NBTTagCompound nbt) {
      this.syncedPower = new PowerInfo();
      this.syncedPower.readFromNBT(nbt);
   }

   public PowerInfo getPowerInfo() {
      return this.syncedPower;
   }

   public ProcessInfo getProcessInfo() {
      return this.syncedProcess;
   }

   public TankInfo getTankInfo(int tank) {
      return this.syncedTanks.containsKey(Integer.valueOf(tank))?(TankInfo)this.syncedTanks.get(Integer.valueOf(tank)):new TankInfo();
   }

   public void onErrorUpdate(NBTTagCompound nbt) {
      this.errorType = nbt.getByte("type");
      if(nbt.hasKey("name")) {
         this.error = new ErrorState("", "");
         this.error.readFromNBT(nbt);
      } else {
         this.error = null;
      }

   }

   public ErrorState getErrorState() {
      return this.error;
   }

   public int getErrorType() {
      return this.errorType;
   }

   public CustomSlot[] getCustomSlots() {
      List<CustomSlot> slots = new ArrayList();

      for(Object object : this.inventorySlots) {
         if(object instanceof CustomSlot) {
            slots.add((CustomSlot)object);
         }
      }

      return (CustomSlot[])slots.toArray(new CustomSlot[0]);
   }

   public void setMouseOverSlot(Slot slot) {
      if(slot.slotNumber != this.mousedOverSlotNumber) {
         this.mousedOverSlotNumber = slot.slotNumber;
         ((List)ControlSlot.highlighting.get(EnumHighlighting.ShiftClick)).clear();
         NBTTagCompound nbt = new NBTTagCompound();
         nbt.setShort("slot", (short)slot.slotNumber);
         this.window.sendClientAction("mouse-over-slot", nbt);
      }

   }

   private void onMouseOverSlot(EntityPlayer player, NBTTagCompound data) {
      int slotnumber = data.getShort("slot");
      TransferRequest request = this.getShiftClickRequest(player, slotnumber);
      if(request != null) {
         request.transfer(false);
         NBTTagCompound nbt = new NBTTagCompound();
         List<Integer> slots = new ArrayList();

         for(TransferRequest.TransferSlot tslot : request.getInsertedSlots()) {
            Slot slot = this.getSlot(tslot.inventory, tslot.id);
            if(slot != null) {
               slots.add(Integer.valueOf(slot.slotNumber));
            }
         }

         int[] array = new int[slots.size()];

         for(int i = 0; i < ((List)slots).size(); ++i) {
            array[i] = ((Integer)slots.get(i)).intValue();
         }

         nbt.setIntArray("slots", array);
         nbt.setShort("origin", (short)slotnumber);
         this.syncedNBT.put("shift-click-info", nbt);
      }
   }

   private void onRecieveShiftClickHighlights(EntityPlayer player, NBTTagCompound data) {
      ((List)ControlSlot.highlighting.get(EnumHighlighting.ShiftClick)).clear();

      for(int slotnumber : data.getIntArray("slots")) {
         ((List)ControlSlot.highlighting.get(EnumHighlighting.ShiftClick)).add(Integer.valueOf(slotnumber));
      }

   }

   private CustomSlot getSlot(IInventory inventory, int id) {
      for(Object o : this.inventorySlots) {
         CustomSlot slot = (CustomSlot)o;
         if(slot.inventory == inventory && slot.getSlotIndex() == id) {
            return slot;
         }
      }

      return null;
   }

   public void recieveNBT(Side side, EntityPlayer player, NBTTagCompound action) {
      String name = action.getString("type");
      if(!this.handleNBT(side, player, name, action)) {
         this.window.recieveGuiNBT(this.getSide(), player, name, action);
         INetwork.RecieveGuiNBT machine = (INetwork.RecieveGuiNBT)Machine.getInterface(INetwork.RecieveGuiNBT.class, this.window.getInventory());
         if(machine != null) {
            machine.recieveGuiNBT(this.getSide(), player, name, action);
         }

      }
   }

   public Slot getOrCreateSlot(InventoryType type, int index) {
      IInventory inventory = this.getInventory(type);
      Slot slot = this.getSlot(inventory, index);
      if(slot == null) {
         slot = new CustomSlot(inventory, index);
         this.addSlotToContainer(slot);
      }

      NBTTagCompound nbt = new NBTTagCompound();
      nbt.setByte("t", (byte)type.ordinal());
      nbt.setShort("i", (short)index);
      nbt.setShort("n", (short)slot.slotNumber);
      this.window.sendClientAction("slot-reg", nbt);
      return slot;
   }

   protected IInventory getInventory(InventoryType type) {
      return (IInventory)(type == InventoryType.Machine?this.window.getInventory():(type == InventoryType.Player?this.window.getPlayer().inventory:(type == InventoryType.Window?this.window.getWindowInventory():null)));
   }

   private Slot getOrCreateSlot(InventoryType type, int index, int slotNumber) {
      IInventory inventory = this.getInventory(type);
      if(this.inventorySlots.get(slotNumber) != null) {
         return null;
      } else {
         Slot slot = new CustomSlot(inventory, index);
         slot.slotNumber = slotNumber;
         this.inventorySlots.add(slotNumber, slot);
         this.inventoryItemStacks.add(slotNumber, (Object)null);
         return slot;
      }
   }
}
