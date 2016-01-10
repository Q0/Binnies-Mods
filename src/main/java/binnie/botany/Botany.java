package binnie.botany;

import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.IFlower;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.botany.ceramic.BlockCeramic;
import binnie.botany.ceramic.BlockCeramicBrick;
import binnie.botany.ceramic.BlockCeramicPatterned;
import binnie.botany.ceramic.BlockStained;
import binnie.botany.core.BotanyCore;
import binnie.botany.core.BotanyGUI;
import binnie.botany.core.ModuleCore;
import binnie.botany.flower.BlockFlower;
import binnie.botany.flower.ItemFlower;
import binnie.botany.flower.ItemInsulatedTube;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.gardening.*;
import binnie.botany.genetics.ItemDictionary;
import binnie.botany.genetics.ItemEncyclopedia;
import binnie.botany.genetics.ModuleGenetics;
import binnie.botany.items.ItemClay;
import binnie.botany.items.ItemPigment;
import binnie.botany.network.PacketID;
import binnie.botany.proxy.Proxy;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.item.ItemMisc;
import binnie.core.network.BinniePacketHandler;
import binnie.core.network.IPacketID;
import binnie.core.proxy.IProxyCore;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

@Mod(
        modid = "Botany",
        name = "Botany",
        useMetadata = true,
        dependencies = "after:BinnieCore"
)
public class Botany extends AbstractMod {
    public static final float AGE_CHANCE = 0.2F;
    @Instance("Botany")
    public static Botany instance;
    @SidedProxy(
            clientSide = "binnie.botany.proxy.ProxyClient",
            serverSide = "binnie.botany.proxy.ProxyServer"
    )
    public static Proxy proxy;
    public static BlockFlower flower;
    public static Item seed;
    public static Item pollen;
    public static ItemDictionary database;
    public static BlockPlant plant;
    public static ItemTrowel trowelWood;
    public static ItemTrowel trowelStone;
    public static ItemTrowel trowelIron;
    public static ItemTrowel trowelDiamond;
    public static ItemTrowel trowelGold;
    public static BlockSoil soil;
    public static BlockSoil loam;
    public static BlockSoil flowerbed;
    public static BlockSoil soilNoWeed;
    public static BlockSoil loamNoWeed;
    public static BlockSoil flowerbedNoWeed;
    public static ItemInsulatedTube insulatedTube;
    public static ItemSoilMeter soilMeter;
    public static ItemMisc misc;
    public static ItemEncyclopedia encyclopedia;
    public static ItemEncyclopedia encyclopediaIron;
    public static ItemFlower flowerItem;
    public static ItemPigment pigment;
    public static ItemClay clay;
    public static BlockCeramic ceramic;
    public static BlockCeramicPatterned ceramicTile;
    public static BlockStained stained;
    public static BlockCeramicBrick ceramicBrick;

    @EventHandler
    public void preInit(FMLPreInitializationEvent evt) {
        this.addModule(new ModuleCore());
        this.addModule(new ModuleGenetics());
        this.addModule(new ModuleGardening());
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

    public Botany() {
        super();
        instance = this;
    }

    public IBinnieGUID[] getGUIDs() {
        return BotanyGUI.values();
    }

    public Class[] getConfigs() {
        return new Class[0];
    }

    public IPacketID[] getPacketIDs() {
        return PacketID.values();
    }

    public IProxyCore getProxy() {
        return proxy;
    }

    public String getChannel() {
        return "BOT";
    }

    public String getModID() {
        return "botany";
    }

    protected Class getPacketHandler() {
        return Botany.PacketHandler.class;
    }

    @SubscribeEvent
    public void onShearFlower(PlayerInteractEvent event) {
        if (event.action == Action.RIGHT_CLICK_BLOCK) {
            if (event.entityPlayer != null && event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() == Items.shears) {
                TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
                if (tile instanceof TileEntityFlower) {
                    TileEntityFlower flower = (TileEntityFlower) tile;
                    flower.onShear();
                    event.entityPlayer.getHeldItem().damageItem(1, event.entityPlayer);
                }
            }

            if (event.entityPlayer != null && event.entityPlayer.getHeldItem() != null && event.entityPlayer.getHeldItem().getItem() == pollen) {
                TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
                if (tile instanceof TileEntityFlower) {
                    TileEntityFlower flower = (TileEntityFlower) tile;
                    IFlower pollen = BotanyCore.getFlowerRoot().getMember(event.entityPlayer.getHeldItem());
                    if (pollen != null && flower.canMateWith(pollen)) {
                        flower.mateWith(pollen);
                        if (!event.entityPlayer.capabilities.isCreativeMode) {
                            --event.entityPlayer.getHeldItem().stackSize;
                        }
                    }
                }
            }

        }
    }

    @SubscribeEvent
    public void onFertiliseSoil(PlayerInteractEvent event) {
        if (event.action == Action.RIGHT_CLICK_BLOCK) {
            if (event.world != null) {
                if (event.entityPlayer != null && event.entityPlayer.getHeldItem() != null) {
                    int y = event.y;
                    Block block = event.world.getBlock(event.x, y, event.z);
                    if (block == null || !Gardening.isSoil(block)) {
                        --y;
                        block = event.world.getBlock(event.x, y, event.z);
                    }

                    if (block == null) {
                        return;
                    }

                    if (Gardening.isSoil(block)) {
                        IBlockSoil soil = (IBlockSoil) block;
                        if (Gardening.isNutrientFertiliser(event.entityPlayer.getHeldItem()) && soil.getType(event.world, event.x, y, event.z) != EnumSoilType.FLOWERBED) {
                            EnumSoilType type = soil.getType(event.world, event.x, y, event.z);
                            int next = Math.min(type.ordinal() + Gardening.getFertiliserStrength(event.entityPlayer.getHeldItem()), 2);
                            if (soil.fertilise(event.world, event.x, y, event.z, EnumSoilType.values()[next]) && !event.entityPlayer.capabilities.isCreativeMode) {
                                --event.entityPlayer.getHeldItem().stackSize;
                                return;
                            }
                        }

                        if (Gardening.isAcidFertiliser(event.entityPlayer.getHeldItem()) && soil.getPH(event.world, event.x, y, event.z) != EnumAcidity.Acid) {
                            EnumAcidity pH = soil.getPH(event.world, event.x, y, event.z);
                            int next = Math.max(pH.ordinal() - Gardening.getFertiliserStrength(event.entityPlayer.getHeldItem()), 0);
                            if (soil.setPH(event.world, event.x, y, event.z, EnumAcidity.values()[next]) && !event.entityPlayer.capabilities.isCreativeMode) {
                                --event.entityPlayer.getHeldItem().stackSize;
                                return;
                            }
                        }

                        if (Gardening.isAlkalineFertiliser(event.entityPlayer.getHeldItem()) && soil.getPH(event.world, event.x, y, event.z) != EnumAcidity.Alkaline) {
                            EnumAcidity pH = soil.getPH(event.world, event.x, y, event.z);
                            int next = Math.min(pH.ordinal() + Gardening.getFertiliserStrength(event.entityPlayer.getHeldItem()), 2);
                            if (soil.setPH(event.world, event.x, y, event.z, EnumAcidity.values()[next]) && !event.entityPlayer.capabilities.isCreativeMode) {
                                --event.entityPlayer.getHeldItem().stackSize;
                                return;
                            }
                        }

                        if (Gardening.isWeedkiller(event.entityPlayer.getHeldItem()) && Gardening.addWeedKiller(event.world, event.x, y, event.z) && !event.entityPlayer.capabilities.isCreativeMode) {
                            --event.entityPlayer.getHeldItem().stackSize;
                            return;
                        }
                    }
                }

            }
        }
    }

    @SubscribeEvent
    public void plantVanilla(PlaceEvent event) {
        World world = event.world;
        int x = event.x;
        int y = event.y;
        int z = event.z;
        Block block = world.getBlock(x, y - 1, z);
        if (block instanceof IBlockSoil) {
            IFlower flower = BotanyCore.getFlowerRoot().getConversion(event.itemInHand);
            if (flower != null) {
                Gardening.plant(world, x, y, z, flower, event.player.getGameProfile());
            }

        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void onPlantVanilla(PlayerInteractEvent event) {
        if (BinnieCore.proxy.isSimulating(event.world)) {
            if (event.action == Action.RIGHT_CLICK_BLOCK) {
                if (event.entityPlayer != null && event.entityPlayer.getHeldItem() != null) {
                    World world = event.world;
                    int x = event.x;
                    int y = event.y;
                    int z = event.z;
                    Block block = world.getBlock(x, y, z);
                    int py = -1;
                    if (block instanceof IBlockSoil && (world.isAirBlock(x, y + 1, z) || world.getBlock(x, y, z).isReplaceable(world, x, y, z))) {
                        py = 1;
                    }

                    if (py >= 0) {
                        IFlower flower = BotanyCore.getFlowerRoot().getConversion(event.entityPlayer.getHeldItem());
                        if (flower != null && Gardening.plant(world, x, y + py, z, flower, event.entityPlayer.getGameProfile()) && !event.entityPlayer.capabilities.isCreativeMode) {
                            --event.entityPlayer.getHeldItem().stackSize;
                        }
                    }
                }

            }
        }
    }

    @SubscribeEvent
    public void onBonemeal(BonemealEvent event) {
        Block block = event.world.getBlock(event.x, event.y, event.z);
        if (Gardening.isSoil(block)) {
            IBlockSoil soil = (IBlockSoil) block;
            if (soil.fertilise(event.world, event.x, event.y, event.z, EnumSoilType.LOAM)) {
                event.setResult(Result.ALLOW);
            }
        }

        TileEntity tile = event.world.getTileEntity(event.x, event.y, event.z);
        if (tile instanceof TileEntityFlower && ((TileEntityFlower) tile).onBonemeal()) {
            event.setResult(Result.ALLOW);
        }

    }

    public boolean isActive() {
        return BinnieCore.isBotanyActive();
    }

    @EventHandler
    public void onIMC(IMCEvent event) {
        for (IMCMessage message : event.getMessages()) {
            if (message.key.equals("add-nutrient-fertiliser-1")) {
                ModuleGardening.queuedNutrientFertilisers.put(message.getItemStackValue(), Integer.valueOf(1));
            }

            if (message.key.equals("add-nutrient-fertiliser-2")) {
                ModuleGardening.queuedNutrientFertilisers.put(message.getItemStackValue(), Integer.valueOf(2));
            }

            if (message.key.equals("add-acid-fertiliser-1")) {
                ModuleGardening.queuedAcidFertilisers.put(message.getItemStackValue(), Integer.valueOf(1));
            }

            if (message.key.equals("add-acid-fertiliser-2")) {
                ModuleGardening.queuedAcidFertilisers.put(message.getItemStackValue(), Integer.valueOf(2));
            }

            if (message.key.equals("add-alkaline-fertiliser-1")) {
                ModuleGardening.queuedAlkalineFertilisers.put(message.getItemStackValue(), Integer.valueOf(1));
            }

            if (message.key.equals("add-alkaline-fertiliser-2")) {
                ModuleGardening.queuedAlkalineFertilisers.put(message.getItemStackValue(), Integer.valueOf(1));
            }
        }

    }

    public static class PacketHandler extends BinniePacketHandler {
        public PacketHandler() {
            super(Botany.instance);
        }
    }
}
