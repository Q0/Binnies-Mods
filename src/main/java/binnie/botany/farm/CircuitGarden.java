package binnie.botany.farm;

import binnie.Binnie;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.core.circuits.BinnieCircuit;
import forestry.api.circuits.ChipsetManager;
import forestry.api.farming.FarmDirection;
import forestry.api.farming.IFarmHousing;
import forestry.api.farming.IFarmLogic;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class CircuitGarden extends BinnieCircuit {
    private Class<? extends FarmLogic> logicClass;
    private boolean isManual;
    private boolean isFertilised;
    private ItemStack icon;
    private EnumMoisture moisture;
    private EnumAcidity acidity;

    public CircuitGarden(final EnumMoisture moisture, final EnumAcidity ph, final boolean manual, final boolean fertilised, final ItemStack recipe, final ItemStack icon) {
        super("garden." + moisture.getID() + ((ph != null) ? ("." + ph.getID()) : "") + (manual ? ".manual" : "") + (fertilised ? ".fert" : ""), 4, manual ? ChipsetManager.circuitRegistry.getLayout("forestry.farms.manual") : ChipsetManager.circuitRegistry.getLayout("forestry.farms.managed"), recipe);
        this.isManual = false;
        this.isFertilised = false;
        this.isManual = manual;
        this.isFertilised = fertilised;
        this.moisture = moisture;
        this.acidity = ph;
        this.icon = icon;
        String info = "";
        if (moisture == EnumMoisture.Dry) {
            info += "§eDry§f";
        }
        if (moisture == EnumMoisture.Damp) {
            info += "§9Damp§f";
        }
        if (this.acidity == EnumAcidity.Acid) {
            if (info.length() > 0) {
                info += ", ";
            }
            info += "§cAcidic§f";
        }
        if (this.acidity == EnumAcidity.Neutral) {
            if (info.length() > 0) {
                info += ", ";
            }
            info += "§aNeutral§f";
        }
        if (this.acidity == EnumAcidity.Alkaline) {
            if (info.length() > 0) {
                info += ", ";
            }
            info += "§bAlkaline§f";
        }
        if (info.length() > 0) {
            info = " (" + info + "§f)";
        }
        this.addTooltipString("Flowers" + info);
    }

    @Override
    public boolean isCircuitable(final TileEntity tile) {
        return tile instanceof IFarmHousing;
    }

    @Override
    public void onInsertion(final int slot, final TileEntity tile) {
        if (!this.isCircuitable(tile)) {
            return;
        }
        final GardenLogic logic = new GardenLogic((IFarmHousing) tile);
        logic.setData(this.moisture, this.acidity, this.isManual, this.isFertilised, this.icon, Binnie.Language.localise(this.getName()));
        ((IFarmHousing) tile).setFarmLogic(FarmDirection.getFarmDirection(ForgeDirection.values()[slot + 2]), logic); //TODO: UPD TO Forestry4
    }

    @Override
    public void onLoad(final int slot, final TileEntity tile) {
        this.onInsertion(slot, tile);
    }

    @Override
    public void onRemoval(final int slot, final TileEntity tile) {
        if (!this.isCircuitable(tile)) {
            return;
        }
        ((IFarmHousing) tile).resetFarmLogic(FarmDirection.getFarmDirection(ForgeDirection.values()[slot + 2])); //TODO: UPD TO Forestry4
    }

    @Override
    public void onTick(final int slot, final TileEntity tile) {
    }
}
