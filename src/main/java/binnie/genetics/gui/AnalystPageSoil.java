package binnie.genetics.gui;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.IFlower;
import binnie.botany.gardening.BlockSoil;
import binnie.core.genetics.Tolerance;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import forestry.api.genetics.EnumTolerance;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class AnalystPageSoil extends ControlAnalystPage {
    public AnalystPageSoil(IWidget parent, IArea area, IFlower flower) {
        super(parent, area);
        this.setColour(6697728);
        EnumMoisture moisture = flower.getGenome().getPrimary().getMoisture();
        EnumTolerance moistureTol = flower.getGenome().getToleranceMoisture();
        EnumAcidity pH = flower.getGenome().getPrimary().getPH();
        EnumTolerance pHTol = flower.getGenome().getTolerancePH();
        int y = 4;
        (new ControlTextCentered(this, (float) y, "§nSoil")).setColour(this.getColour());
        y = y + 16;
        (new ControlText(this, new IArea(4.0F, (float) y, this.w() - 8.0F, 14.0F), "Moisture Tolerance", TextJustification.MiddleCenter)).setColour(this.getColour());
        y = y + 12;
        this.createMoisture(this, (this.w() - 100.0F) / 2.0F, (float) y, 100.0F, 10.0F, moisture, moistureTol);
        y = y + 16;
        (new ControlText(this, new IArea(4.0F, (float) y, this.w() - 8.0F, 14.0F), "pH Tolerance", TextJustification.MiddleCenter)).setColour(this.getColour());
        y = y + 12;
        this.createAcidity(this, (this.w() - 100.0F) / 2.0F, (float) y, 100.0F, 10.0F, pH, pHTol);
        y = y + 16;
        (new ControlText(this, new IArea(4.0F, (float) y, this.w() - 8.0F, 14.0F), "Recommended Soil", TextJustification.MiddleCenter)).setColour(this.getColour());
        y = y + 12;
        EnumMoisture recomMoisture = EnumMoisture.Normal;
        boolean canTolNormal = Tolerance.canTolerate(moisture, EnumMoisture.Normal, moistureTol);
        boolean canTolDamp = Tolerance.canTolerate(moisture, EnumMoisture.Damp, moistureTol);
        boolean canTolDry = Tolerance.canTolerate(moisture, EnumMoisture.Dry, moistureTol);
        if (canTolNormal) {
            if (canTolDamp && !canTolDry) {
                recomMoisture = EnumMoisture.Damp;
            } else if (canTolDry && !canTolDamp) {
                recomMoisture = EnumMoisture.Dry;
            }
        } else {
            if (canTolDamp) {
                recomMoisture = EnumMoisture.Damp;
            }

            if (canTolDry) {
                recomMoisture = EnumMoisture.Dry;
            }
        }

        EnumAcidity recomPH = EnumAcidity.Neutral;
        boolean canTolNeutral = Tolerance.canTolerate(pH, EnumAcidity.Neutral, pHTol);
        boolean canTolAcid = Tolerance.canTolerate(pH, EnumAcidity.Acid, pHTol);
        boolean canTolAlkaline = Tolerance.canTolerate(pH, EnumAcidity.Alkaline, pHTol);
        if (canTolNeutral) {
            if (canTolAcid && !canTolAlkaline) {
                recomPH = EnumAcidity.Acid;
            } else if (canTolAlkaline && !canTolAcid) {
                recomPH = EnumAcidity.Alkaline;
            }
        } else {
            if (canTolAcid) {
                recomPH = EnumAcidity.Acid;
            }

            if (canTolAlkaline) {
                recomPH = EnumAcidity.Alkaline;
            }
        }

        ItemStack stack = new ItemStack(Botany.soil, 1, BlockSoil.getMeta(recomPH, recomMoisture));
        ControlItemDisplay recomSoil = new ControlItemDisplay(this, (this.w() - 24.0F) / 2.0F, (float) y, 24.0F);
        recomSoil.setItemStack(stack);
        recomSoil.setTooltip();
        y = y + 32;
        (new ControlText(this, new IArea(4.0F, (float) y, this.w() - 8.0F, 14.0F), "Other Soils", TextJustification.MiddleCenter)).setColour(this.getColour());
        y = y + 12;
        List<ItemStack> stacks = new ArrayList();

        for (EnumAcidity a : EnumSet.range(EnumAcidity.Acid, EnumAcidity.Alkaline)) {
            for (EnumMoisture b : EnumSet.range(EnumMoisture.Dry, EnumMoisture.Damp)) {
                if (Tolerance.canTolerate(pH, a, pHTol) && Tolerance.canTolerate(moisture, b, moistureTol) && (a != recomPH || b != recomMoisture)) {
                    stacks.add(new ItemStack(Botany.soil, 1, BlockSoil.getMeta(a, b)));
                }
            }
        }

        float soilListWidth = (float) (17 * stacks.size() - 1);
        float soilListX = (this.w() - soilListWidth) / 2.0F;
        int t = 0;

        for (ItemStack soilStack : stacks) {
            ControlItemDisplay display = new ControlItemDisplay(this, soilListX + (float) (17 * t++), (float) y);
            display.setItemStack(soilStack);
            display.setTooltip();
        }

    }

    protected void createMoisture(final IWidget parent, final float x, final float y, final float w, final float h, EnumMoisture value, EnumTolerance tol) {
        (new ControlToleranceBar(parent, x, y, w, h, EnumMoisture.class) {
            protected String getName(EnumMoisture value) {
                return Binnie.Language.localise(value);
            }

            protected int getColour(EnumMoisture value) {
                return (new int[]{13434828, 6737151, 3368703})[value.ordinal()];
            }
        }).setValues(value, tol);
    }

    protected void createAcidity(final IWidget parent, final float x, final float y, final float w, final float h, EnumAcidity value, EnumTolerance tol) {
        (new ControlToleranceBar(parent, x, y, w, h, EnumAcidity.class) {
            protected String getName(EnumAcidity value) {
                return Binnie.Language.localise(value);
            }

            protected int getColour(EnumAcidity value) {
                return (new int[]{16711782, '\uff00', 26367})[value.ordinal()];
            }
        }).setValues(value, tol);
    }

    public String getTitle() {
        return "Soil";
    }
}
