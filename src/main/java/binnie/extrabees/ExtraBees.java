package binnie.extrabees;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extrabees.apiary.ModuleApiary;
import binnie.extrabees.config.ConfigurationMachines;
import binnie.extrabees.config.ConfigurationMain;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ModuleCore;
import binnie.extrabees.genetics.ModuleGenetics;
import binnie.extrabees.liquids.ModuleLiquids;
import binnie.extrabees.products.ModuleProducts;
import binnie.extrabees.proxy.ExtraBeesProxy;
import binnie.extrabees.worldgen.ModuleGeneration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

@Mod(
   modid = "ExtraBees",
   name = "Extra Bees",
   useMetadata = true,
   dependencies = "after:BinnieCore"
)
public class ExtraBees extends AbstractMod {
   @Instance("ExtraBees")
   public static ExtraBees instance;
   @SidedProxy(
      clientSide = "binnie.extrabees.proxy.ExtraBeesProxyClient",
      serverSide = "binnie.extrabees.proxy.ExtraBeesProxyServer"
   )
   public static ExtraBeesProxy proxy;
   public static Block hive;
   public static Material materialBeehive;
   public static Block ectoplasm;
   public static Block apiaristMachine;
   public static Block geneticMachine;
   public static Block advGeneticMachine;
   public static Item comb;
   public static Item propolis;
   public static Item honeyDrop;
   public static Item honeyCrystal;
   public static Item honeyCrystalEmpty;
   public static Item dictionary;
   public static Item itemMisc;

   @EventHandler
   public void preInit(FMLPreInitializationEvent evt) {
      this.addModule(new ModuleCore());
      this.addModule(new ModuleProducts());
      this.addModule(new ModuleGenetics());
      this.addModule(new ModuleGeneration());
      this.addModule(new ModuleLiquids());
      this.addModule(new ModuleApiary());
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

   public ExtraBees() {
      super();
      instance = this;
   }

   public IBinnieGUID[] getGUIDs() {
      return ExtraBeeGUID.values();
   }

   public Class[] getConfigs() {
      return new Class[]{ConfigurationMain.class, ConfigurationMachines.class};
   }

   public IProxyCore getProxy() {
      return proxy;
   }

   public String getChannel() {
      return "EB";
   }

   public String getModID() {
      return "extrabees";
   }

   protected Class getPacketHandler() {
      return ExtraBees.PacketHandler.class;
   }

   public boolean isActive() {
      return BinnieCore.isExtraBeesActive();
   }

   public static class PacketHandler extends BinniePacketHandler {
      public PacketHandler() {
         super(ExtraBees.instance);
      }
   }
}
