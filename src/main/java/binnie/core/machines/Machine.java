package binnie.core.machines;

import binnie.core.BinnieCore;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.component.IRender;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ITankMachine;
import binnie.core.network.BinnieCorePacketID;
import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.MessageTileNBT;
import binnie.core.network.packet.PacketPayload;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import forestry.api.core.INBTTagable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.*;

public class Machine implements INetworkedEntity, INBTTagable, INetwork.TilePacketSync, IMachine, INetwork.GuiNBT {
    private MachinePackage machinePackage;
    private Map componentInterfaceMap = new LinkedHashMap();
    private Map componentMap = new LinkedHashMap();
    private TileEntity tile;
    private boolean queuedInventoryUpdate = false;
    private int nextProgressBarID = 0;
    private GameProfile owner = null;

    public Machine(MachinePackage pack, TileEntity tile) {
        super();
        this.tile = tile;
        pack.createMachine(this);
        this.machinePackage = pack;
    }

    public void addComponent(MachineComponent component) {
        if (component == null) {
            throw new NullPointerException("Can\'t have a null machine component!");
        } else {
            component.setMachine(this);
            this.componentMap.put(component.getClass(), component);

            for (Class inter : component.getComponentInterfaces()) {
                if (!this.componentInterfaceMap.containsKey(inter)) {
                    this.componentInterfaceMap.put(inter, new ArrayList());
                }

                ((List) this.componentInterfaceMap.get(inter)).add(component);
            }

        }
    }

    public Collection getComponents() {
        return this.componentMap.values();
    }

    public MachineComponent getComponent(Class componentClass) {
        return this.hasComponent(componentClass) ? (MachineComponent) componentClass.cast(this.componentMap.get(componentClass)) : null;
    }

    public Object getInterface(Class interfaceClass) {
        if (this.hasInterface(interfaceClass)) {
            return this.getInterfaces(interfaceClass).get(0);
        } else if (interfaceClass.isInstance(this.getPackage())) {
            return interfaceClass.cast(this.getPackage());
        } else {
            for (MachineComponent component : this.getComponents()) {
                if (interfaceClass.isInstance(component)) {
                    return interfaceClass.cast(component);
                }
            }

            return null;
        }
    }

    public List getInterfaces(Class interfaceClass) {
        ArrayList<T> interfaces = new ArrayList();
        if (!this.hasInterface(interfaceClass)) {
            return interfaces;
        } else {
            for (MachineComponent component : (List) this.componentInterfaceMap.get(interfaceClass)) {
                interfaces.add(interfaceClass.cast(component));
            }

            return interfaces;
        }
    }

    public boolean hasInterface(Class interfaceClass) {
        return this.componentInterfaceMap.containsKey(interfaceClass);
    }

    public boolean hasComponent(Class componentClass) {
        return this.componentMap.containsKey(componentClass);
    }

    public TileEntity getTileEntity() {
        return this.tile;
    }

    public void sendPacket() {
        if (BinnieCore.proxy.isSimulating(this.getTileEntity().getWorldObj())) {
            BinnieCore.proxy.sendNetworkEntityPacket((INetworkedEntity) this.getTileEntity());
        }
    }

    public Side getSide() {
        return BinnieCore.proxy.isSimulating(this.getTileEntity().getWorldObj()) ? Side.SERVER : Side.CLIENT;
    }

    public void writeToPacket(PacketPayload payload) {
        for (MachineComponent component : this.getComponents()) {
            if (component instanceof INetworkedEntity) {
                ((INetworkedEntity) component).writeToPacket(payload);
            }
        }

    }

    public void readFromPacket(PacketPayload payload) {
        for (MachineComponent component : this.getComponents()) {
            if (component instanceof INetworkedEntity) {
                ((INetworkedEntity) component).readFromPacket(payload);
            }
        }

    }

    public void onRightClick(World world, EntityPlayer player, int x, int y, int z) {
        for (IInteraction.RightClick component : this.getInterfaces(IInteraction.RightClick.class)) {
            component.onRightClick(world, player, x, y, z);
        }

    }

    public void markDirty() {
        this.queuedInventoryUpdate = true;
    }

    public void onUpdate() {
        if (BinnieCore.proxy.isSimulating(this.getWorld())) {
            for (MachineComponent component : this.getComponents()) {
                component.onUpdate();
            }
        } else {
            for (IRender.DisplayTick renders : this.getInterfaces(IRender.DisplayTick.class)) {
                renders.onDisplayTick(this.getWorld(), this.getTileEntity().xCoord, this.getTileEntity().yCoord, this.getTileEntity().zCoord, this.getWorld().rand);
            }
        }

        if (this.queuedInventoryUpdate) {
            for (MachineComponent component : this.getComponents()) {
                component.onInventoryUpdate();
            }

            this.queuedInventoryUpdate = false;
        }

    }

    public IInventory getInventory() {
        return (IInventory) this.getInterface(IInventory.class);
    }

    public ITankMachine getTankContainer() {
        return (ITankMachine) this.getInterface(ITankMachine.class);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        for (MachineComponent component : this.getComponents()) {
            component.readFromNBT(nbttagcompound);
        }

        this.owner = NBTUtil.func_152459_a(nbttagcompound.getCompoundTag("owner"));
        this.markDirty();
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        for (MachineComponent component : this.getComponents()) {
            component.writeToNBT(nbttagcompound);
        }

        if (this.owner != null) {
            NBTTagCompound nbt = new NBTTagCompound();
            NBTUtil.func_152460_a(nbt, this.owner);
            nbttagcompound.setTag("owner", nbt);
        }

    }

    public MachinePackage getPackage() {
        return this.machinePackage;
    }

    public static IMachine getMachine(Object inventory) {
        return (IMachine) (inventory != null && inventory instanceof IMachine ? (IMachine) inventory : (inventory != null && inventory instanceof TileEntityMachine ? ((TileEntityMachine) inventory).getMachine() : (inventory != null && inventory instanceof MachineComponent ? ((MachineComponent) inventory).getMachine() : null)));
    }

    public static Object getInterface(Class interfac, Object inventory) {
        IMachine machine = getMachine(inventory);
        return machine != null ? machine.getInterface(interfac) : (interfac.isInstance(inventory) ? interfac.cast(inventory) : null);
    }

    public MachineUtil getMachineUtil() {
        return new MachineUtil(this);
    }

    public World getWorld() {
        return this.getTileEntity().getWorldObj();
    }

    public void onBlockDestroy() {
        for (MachineComponent component : this.getComponents()) {
            component.onDestruction();
        }

    }

    public int getUniqueProgressBarID() {
        return this.nextProgressBarID++;
    }

    public GameProfile getOwner() {
        return this.owner;
    }

    public void setOwner(GameProfile owner) {
        this.owner = owner;
    }

    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.syncToNBT(nbt);
        return nbt.hasNoTags() ? null : BinnieCore.instance.getNetworkWrapper().getPacketFrom((new MessageTileNBT(BinnieCorePacketID.TileDescriptionSync.ordinal(), this.getTileEntity(), nbt)).GetMessage());
    }

    public void syncToNBT(NBTTagCompound nbt) {
        for (INetwork.TilePacketSync comp : this.getInterfaces(INetwork.TilePacketSync.class)) {
            comp.syncToNBT(nbt);
        }

    }

    public void syncFromNBT(NBTTagCompound nbt) {
        for (INetwork.TilePacketSync comp : this.getInterfaces(INetwork.TilePacketSync.class)) {
            comp.syncFromNBT(nbt);
        }

    }

    public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
        for (INetwork.RecieveGuiNBT recieve : this.getInterfaces(INetwork.RecieveGuiNBT.class)) {
            recieve.recieveGuiNBT(side, player, name, nbt);
        }

    }

    public void sendGuiNBT(Map nbt) {
        for (INetwork.SendGuiNBT recieve : this.getInterfaces(INetwork.SendGuiNBT.class)) {
            recieve.sendGuiNBT(nbt);
        }

    }
}
