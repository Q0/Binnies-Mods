package binnie.extrabees.apiary;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.circuits.BinnieCircuitLayout;
import binnie.core.machines.MachineGroup;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.machine.AlvearyMachine;
import binnie.extrabees.apiary.machine.AlvearyMutator;
import binnie.extrabees.apiary.machine.AlvearyStimulator;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModuleApiary implements IInitializable {
    public static Block blockComponent;
    BinnieCircuitLayout stimulatorLayout;

    //---------------------------------------------------------------------------
    //
    // CONSTRUCTOR
    //
    //---------------------------------------------------------------------------

    @Override
    public void preInit() {
        final MachineGroup machineGroup = new MachineGroup(ExtraBees.instance, "alveay", "alveary", AlvearyMachine.values());
        machineGroup.setCreativeTab(Tabs.tabApiculture);
        BinnieCore.proxy.registerTileEntity(TileExtraBeeAlveary.class, "extrabees.tile.alveary", BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine"));
        ModuleApiary.blockComponent = machineGroup.getBlock();
        AlvearyMutator.addMutationItem(new ItemStack(Blocks.soul_sand), 1.5f);
        AlvearyMutator.addMutationItem(Mods.IC2.stack("UranFuel"), 4.0f);
        AlvearyMutator.addMutationItem(Mods.IC2.stack("MOXFuel"), 10.0f);
        AlvearyMutator.addMutationItem(Mods.IC2.stack("Plutonium"), 8.0f);
        AlvearyMutator.addMutationItem(Mods.IC2.stack("smallPlutonium"), 5.0f);
        AlvearyMutator.addMutationItem(Mods.IC2.stack("Uran235"), 4.0f);
        AlvearyMutator.addMutationItem(Mods.IC2.stack("smallUran235"), 2.5f);
        AlvearyMutator.addMutationItem(Mods.IC2.stack("Uran238"), 2.0f);
        AlvearyMutator.addMutationItem(new ItemStack(Items.ender_pearl), 2.0f);
        AlvearyMutator.addMutationItem(new ItemStack(Items.ender_eye), 4.0f);
    }

    @Override
    public void init() {
        stimulatorLayout = new BinnieCircuitLayout(ExtraBees.instance, "Stimulator");
    }

    @Override
    public void postInit() {
        ItemManager.registerItems();
        RecipeManager.registerRecipes();

        for (final AlvearyStimulator.CircuitType type : AlvearyStimulator.CircuitType.values()) {
            type.createCircuit(stimulatorLayout);
        }
    }
}
