package binnie.core;

import binnie.Binnie;
import binnie.core.block.MultipassBlockRenderer;
import binnie.core.block.TileEntityMetadata;
import binnie.core.gui.BinnieCoreGUI;
import binnie.core.gui.BinnieGUIHandler;
import binnie.core.gui.IBinnieGUID;
import binnie.core.item.ItemFieldKit;
import binnie.core.item.ItemGenesis;
import binnie.core.item.ModuleItems;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.ItemFluidContainer;
import binnie.core.machines.MachineGroup;
import binnie.core.machines.storage.ModuleStorage;
import binnie.core.mod.config.ConfigurationMain;
import binnie.core.mod.config.ConfigurationMods;
import binnie.core.mod.parser.FieldParser;
import binnie.core.mod.parser.ItemParser;
import binnie.core.network.BinnieCorePacketID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.proxy.BinnieProxy;
import binnie.core.proxy.IBinnieProxy;
import binnie.core.triggers.ModuleTrigger;
import binnie.craftgui.minecraft.ModuleCraftGUI;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.ForestryEvent.SpeciesDiscovered;
import forestry.plugins.PluginManager.Module;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;

import java.util.ArrayList;
import java.util.List;

@Mod(
        modid = "BinnieCore",
        name = "Binnie Core",
        useMetadata = true
)
public final class BinnieCore extends AbstractMod {
    @Instance("BinnieCore")
    public static BinnieCore instance;
    @SidedProxy(
            clientSide = "binnie.core.proxy.BinnieProxyClient",
            serverSide = "binnie.core.proxy.BinnieProxyServer"
    )
    public static BinnieProxy proxy;
    public static int multipassRenderID;
    private static List modList = new ArrayList();
    public static MachineGroup packageCompartment;
    public static ItemGenesis genesis;
    public static ItemFieldKit fieldKit;

    @EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        Binnie.Configuration.registerConfiguration(ConfigurationMods.class, this);

        for (ManagerBase baseManager : Binnie.Managers) {
            this.addModule(baseManager);
        }

        this.addModule(new ModuleCraftGUI());
        this.addModule(new ModuleStorage());
        this.addModule(new ModuleItems());
        if (Loader.isModLoaded("BuildCraft|Silicon")) {
            this.addModule(new ModuleTrigger());
        }

        this.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent evt) {
        this.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent evt) {
        this.postInit();
    }

    public BinnieCore() {
        super();
    }

    public IBinnieGUID[] getGUIDs() {
        return BinnieCoreGUI.values();
    }

    public void preInit() {
        instance = this;

        for (FluidContainer container : FluidContainer.values()) {
            Item item = new ItemFluidContainer(container);
            GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
        }

        FieldParser.parsers.add(new ItemParser());
        super.preInit();
    }

    public void init() {
        super.init();

        for (AbstractMod mod : getActiveMods()) {
            NetworkRegistry.INSTANCE.registerGuiHandler(mod, new BinnieGUIHandler(mod));
        }

        multipassRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new MultipassBlockRenderer());
        GameRegistry.registerTileEntity(TileEntityMetadata.class, "binnie.tile.metadata");
    }

    public static boolean isLepidopteryActive() {
        return Module.LEPIDOPTEROLOGY.isEnabled();
    }

    public static boolean isApicultureActive() {
        return Module.APICULTURE.isEnabled();
    }

    public static boolean isArboricultureActive() {
        return Module.ARBORICULTURE.isEnabled();
    }

    public static boolean isBotanyActive() {
        return ConfigurationMods.botany;
    }

    public static boolean isGeneticsActive() {
        return ConfigurationMods.genetics;
    }

    public static boolean isExtraBeesActive() {
        return ConfigurationMods.extraBees && isApicultureActive();
    }

    public static boolean isExtraTreesActive() {
        return ConfigurationMods.extraTrees && isArboricultureActive();
    }

    public void postInit() {
        super.postInit();
    }

    static void registerMod(AbstractMod mod) {
        modList.add(mod);
    }

    private static List getActiveMods() {
        List<AbstractMod> list = new ArrayList();

        for (AbstractMod mod : modList) {
            if (mod.isActive()) {
                list.add(mod);
            }
        }

        return list;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void handleSpeciesDiscovered(SpeciesDiscovered event) {
        try {
            EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(event.username.getName());
            if (player == null) {
                return;
            }

            event.tracker.synchToPlayer(player);
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("species", event.species.getUID());
        } catch (Exception var4) {
            ;
        }

    }

    public String getChannel() {
        return "BIN";
    }

    public IBinnieProxy getProxy() {
        return proxy;
    }

    public String getModID() {
        return "binniecore";
    }

    public IPacketID[] getPacketIDs() {
        return BinnieCorePacketID.values();
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void handleTextureRemap(Pre event) {
        if (event.map.getTextureType() == 0) {
            Binnie.Liquid.reloadIcons(event.map);
        }

        Binnie.Resource.registerIcons(event.map, event.map.getTextureType());
    }

    public Class[] getConfigs() {
        return new Class[]{ConfigurationMain.class};
    }

    protected Class getPacketHandler() {
        return BinnieCore.PacketHandler.class;
    }

    public boolean isActive() {
        return true;
    }

    public static class PacketHandler extends BinniePacketHandler {
        public PacketHandler() {
            super(BinnieCore.instance);
        }
    }
}
