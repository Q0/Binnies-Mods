package binnie.extratrees;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.BinniePacketHandler;
import binnie.core.proxy.IProxyCore;
import binnie.extrabees.ExtraBees;
import binnie.extratrees.alcohol.ModuleAlcohol;
import binnie.extratrees.alcohol.drink.ItemDrink;
import binnie.extratrees.block.ModuleBlocks;
import binnie.extratrees.block.decor.BlockHedge;
import binnie.extratrees.block.decor.BlockMultiFence;
import binnie.extratrees.carpentry.BlockCarpentry;
import binnie.extratrees.carpentry.BlockStainedDesign;
import binnie.extratrees.carpentry.ModuleCarpentry;
import binnie.extratrees.config.ConfigurationMain;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.core.ModuleCore;
import binnie.extratrees.genetics.ModuleGenetics;
import binnie.extratrees.item.ModuleItems;
import binnie.extratrees.machines.ModuleMachine;
import binnie.extratrees.proxy.Proxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@Mod(
   modid = "ExtraTrees",
   name = "Extra Trees",
   useMetadata = true,
   dependencies = "after:BinnieCore"
)
public class ExtraTrees extends AbstractMod {
   @Instance("ExtraTrees")
   public static ExtraTrees instance;
   @SidedProxy(
      clientSide = "binnie.extratrees.proxy.ProxyClient",
      serverSide = "binnie.extratrees.proxy.ProxyServer"
   )
   public static Proxy proxy;
   public static Item itemDictionary;
   public static Item itemDictionaryLepi;
   public static Item itemMisc;
   public static Item itemFood;
   public static Block blockStairs;
   public static Block blockLog;
   public static BlockCarpentry blockCarpentry;
   public static Block blockPlanks;
   public static Block blockMachine;
   public static Block blockFence;
   public static BlockCarpentry blockPanel;
   public static Block blockKitchen;
   public static Block blockSlab;
   public static Block blockDoubleSlab;
   public static Item itemHammer;
   public static Item itemDurableHammer;
   public static Block blockGate;
   public static Block blockDoor;
   public static Block blockBranch;
   public static ItemDrink drink;
   public static BlockMultiFence blockMultiFence;
   public static Block blockDrink;
   public static BlockHedge blockHedge;
   public static BlockStainedDesign blockStained;
   public static int fruitPodRenderId;
   public static int doorRenderId;
   public static int branchRenderId;
   public static int fenceID;
   public static int stairsID;

   @EventHandler
   public void preInit(FMLPreInitializationEvent evt) {
      this.addModule(new ModuleBlocks());
      this.addModule(new ModuleItems());
      this.addModule(new ModuleAlcohol());
      this.addModule(new ModuleGenetics());
      this.addModule(new ModuleCarpentry());
      this.addModule(new ModuleMachine());
      this.addModule(new ModuleCore());
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

   public ExtraTrees() {
      super();
      instance = this;
   }

   public IBinnieGUID[] getGUIDs() {
      return ExtraTreesGUID.values();
   }

   public Class[] getConfigs() {
      return new Class[]{ConfigurationMain.class};
   }

   public String getChannel() {
      return "ET";
   }

   public IProxyCore getProxy() {
      return proxy;
   }

   public String getModID() {
      return "extratrees";
   }

   protected Class getPacketHandler() {
      return ExtraTrees.PacketHandler.class;
   }

   public boolean isActive() {
      return BinnieCore.isExtraTreesActive();
   }

   public static class PacketHandler extends BinniePacketHandler {
      public PacketHandler() {
         super(ExtraBees.instance);
      }
   }
}
