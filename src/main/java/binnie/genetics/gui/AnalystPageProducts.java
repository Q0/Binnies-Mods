package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.util.UniqueItemStackSet;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.extratrees.kitchen.ControlFluidDisplay;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.plugins.PluginApiculture;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AnalystPageProducts extends AnalystPageProduce {
    public AnalystPageProducts(IWidget parent, IArea area, IBee ind) {
        super(parent, area);
        this.setColour(13382400);
        IBeeGenome genome = ind.getGenome();
        float speed = genome.getSpeed();
        float modeSpeed = Binnie.Genetics.getBeeRoot().getBeekeepingMode(BinnieCore.proxy.getWorld()).getProductionModifier(genome, 1.0F);
        int y = 4;
        (new ControlTextCentered(this, (float) y, "§nProduce")).setColour(this.getColour());
        y = y + 12;
        (new ControlTextCentered(this, (float) y, "§oRate: " + Binnie.Genetics.beeBreedingSystem.getAlleleName(EnumBeeChromosome.SPEED, ind.getGenome().getActiveAllele(EnumBeeChromosome.SPEED)))).setColour(this.getColour());
        y = y + 20;
        Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
        Collection<ItemStack> productList = new UniqueItemStackSet();
        new UniqueItemStackSet();
        Map<ItemStack, Integer> products = new HashMap();
        products.putAll(genome.getPrimary().getProducts());
        products.putAll(genome.getSecondary().getProducts());
        if (!products.isEmpty()) {
            (new ControlTextCentered(this, (float) y, "Natural Products")).setColour(this.getColour());
            y = y + 12;

            for (Entry<ItemStack, Integer> entry : products.entrySet()) {
                if (productList.add(entry.getKey())) {
                    refinedProducts.addAll(this.getAllProducts((ItemStack) entry.getKey()));
                    this.createProductEntry((ItemStack) entry.getKey(), (Integer) entry.getValue(), y, speed * modeSpeed);
                    y += 18;
                }
            }

            y = y + 12;
        }

        products = genome.getPrimary().getSpecialty();
        if (!products.isEmpty()) {
            (new ControlTextCentered(this, (float) y, "Specialty Products")).setColour(this.getColour());
            y = y + 12;

            for (Entry<ItemStack, Integer> entry : products.entrySet()) {
                refinedProducts.addAll(this.getAllProducts((ItemStack) entry.getKey()));
                this.createProductEntry((ItemStack) entry.getKey(), (Integer) entry.getValue(), y, speed * modeSpeed);
                y += 18;
            }

            y = y + 12;
        }

        (new ControlTextCentered(this, (float) y, "Refined Products")).setColour(this.getColour());
        y = y + 12;
        Collection<ItemStack> level2Products = new UniqueItemStackSet();

        for (ItemStack stack : refinedProducts) {
            level2Products.addAll(this.getAllProducts(stack));
        }

        refinedProducts.addAll(level2Products);
        level2Products = new UniqueItemStackSet();

        for (ItemStack stack : refinedProducts) {
            level2Products.addAll(this.getAllProducts(stack));
        }

        refinedProducts.addAll(level2Products);
        Collection<FluidStack> allFluids = new ArrayList();

        for (ItemStack stack : refinedProducts) {
            for (FluidStack addition : this.getAllFluids(stack)) {
                boolean alreadyIn = false;

                for (FluidStack existing : allFluids) {
                    if (existing.isFluidEqual(addition)) {
                        alreadyIn = true;
                    }
                }

                if (!alreadyIn) {
                    allFluids.add(addition);
                }
            }
        }

        int maxBiomePerLine = (int) ((this.w() + 2.0F - 16.0F) / 18.0F);
        float biomeListX = (this.w() - (float) (Math.min(maxBiomePerLine, allFluids.size() + refinedProducts.size()) * 18 - 2)) / 2.0F;
        int dx = 0;
        int dy = 0;

        for (ItemStack soilStack : refinedProducts) {
            if (dx >= 18 * maxBiomePerLine) {
                dx = 0;
                dy += 18;
            }

            ControlItemDisplay display = new ControlItemDisplay(this, biomeListX + (float) dx, (float) (y + dy));
            display.setItemStack(soilStack);
            display.setTooltip();
            dx += 18;
        }

        for (FluidStack soilStack : allFluids) {
            if (dx >= 18 * maxBiomePerLine) {
                dx = 0;
                dy += 18;
            }

            ItemStack container = null;

            for (FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
                if (data.emptyContainer.isItemEqual(new ItemStack(Items.glass_bottle)) && data.fluid.isFluidEqual(soilStack)) {
                    container = data.filledContainer;
                    break;
                }

                if (data.emptyContainer.isItemEqual(new ItemStack(Items.bucket)) && data.fluid.isFluidEqual(soilStack)) {
                    container = data.filledContainer;
                    break;
                }

                if (data.fluid.isFluidEqual(soilStack)) {
                    container = data.filledContainer;
                    break;
                }
            }

            if (container == null) {
                ControlFluidDisplay display = new ControlFluidDisplay(this, biomeListX + (float) dx, (float) (y + dy));
                display.setItemStack(soilStack);
                display.setTooltip();
            } else {
                ControlItemDisplay display = new ControlItemDisplay(this, biomeListX + (float) dx, (float) (y + dy));
                display.setItemStack(container);
                display.setTooltip();
            }

            dx += 18;
        }

        this.setSize(new IPoint(this.w(), (float) (y + dy + 18 + 8)));
    }

    private void createProductEntry(final ItemStack key, Integer value, int y, float speed) {
        ControlItemDisplay item = new ControlItemDisplay(this, 16.0F, (float) y) {
            public void getTooltip(Tooltip tooltip) {
                super.getTooltip(tooltip);
                Collection<ItemStack> products = AnalystPageProducts.this.getCentrifuge(key);
                if (!products.isEmpty()) {
                    tooltip.add("Centrifuges to give: ");

                    for (ItemStack prod : products) {
                        NBTTagCompound nbt = new NBTTagCompound();
                        prod.writeToNBT(nbt);
                        tooltip.add(prod, prod.getDisplayName());
                    }
                }

                Collection<ItemStack> liquids = AnalystPageProducts.this.getSqueezer(key);
                if (!liquids.isEmpty()) {
                    tooltip.add("Squeezes to give: ");

                    for (ItemStack prod : liquids) {
                        NBTTagCompound nbt = new NBTTagCompound();
                        prod.writeToNBT(nbt);
                        tooltip.add(prod, prod.getDisplayName());
                    }
                }

            }
        };
        item.setTooltip();
        ControlText textWidget = new ControlTextCentered(this, (float) (y + 4), "");
        textWidget.setColour(this.getColour());
        CraftGUIUtil.moveWidget(textWidget, new IPoint(12.0F, 0.0F));
        item.setItemStack(key);
        float time = (float) ((int) ((double) PluginApiculture.ticksPerBeeWorkCycle * 100.0D / (double) (speed * (float) value.intValue())));
        textWidget.setValue("Every " + this.getTimeString(time));
    }

    public String getTitle() {
        return "Produce";
    }
}
