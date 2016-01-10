package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.util.UniqueItemStackSet;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IFruitFamily;
import forestry.arboriculture.FruitProviderPod;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;
import java.util.Collection;

public class AnalystPageFruit extends AnalystPageProduce {
    public AnalystPageFruit(IWidget parent, IArea area, ITree ind) {
        super(parent, area);
        this.setColour(13382400);
        ITreeGenome genome = ind.getGenome();
        int y = 4;
        (new ControlTextCentered(this, (float) y, "§nFruit")).setColour(this.getColour());
        y = y + 12;
        (new ControlTextCentered(this, (float) y, "§oYield: " + Binnie.Genetics.treeBreedingSystem.getAlleleName(EnumTreeChromosome.YIELD, ind.getGenome().getActiveAllele(EnumTreeChromosome.YIELD)))).setColour(this.getColour());
        y = y + 20;
        Collection<ItemStack> products = new UniqueItemStackSet();
        Collection<ItemStack> specialties = new UniqueItemStackSet();
        new UniqueItemStackSet();

        for (ItemStack stack : ind.getProduceList()) {
            products.add(stack);
        }

        for (ItemStack stack : ind.getSpecialtyList()) {
            specialties.add(stack);
        }

        try {
            if (ind.getGenome().getFruitProvider() instanceof FruitProviderPod) {
                FruitProviderPod pod = (FruitProviderPod) ind.getGenome().getFruitProvider();
                Field f = FruitProviderPod.class.getDeclaredField("drop");
                f.setAccessible(true);

                for (ItemStack stack : (ItemStack[]) ((ItemStack[]) f.get(pod))) {
                    products.add(stack);
                }
            }
        } catch (Exception var24) {
            var24.printStackTrace();
        }

        if (products.size() > 0) {
            (new ControlTextCentered(this, (float) y, "Natural Fruit")).setColour(this.getColour());
            y = y + 10;
            int w = products.size() * 18 - 2;
            int i = 0;

            for (ItemStack stack : products) {
                ControlItemDisplay d = new ControlItemDisplay(this, (this.w() - (float) w) / 2.0F + (float) (18 * i), (float) y);
                d.setTooltip();
                d.setItemStack(stack);
            }

            y = y + 26;
        }

        if (specialties.size() > 0) {
            (new ControlTextCentered(this, (float) y, "Specialty Fruit")).setColour(this.getColour());
            y = y + 10;
            int w = products.size() * 18 - 2;
            int i = 0;

            for (ItemStack stack : specialties) {
                ControlItemDisplay d = new ControlItemDisplay(this, (this.w() - (float) w) / 2.0F + (float) (18 * i), (float) y);
                d.setTooltip();
                d.setItemStack(stack);
            }

            y = y + 26;
        }

        Collection<ItemStack> allProducts = new UniqueItemStackSet();

        for (ItemStack stack : products) {
            allProducts.add(stack);
        }

        for (ItemStack stack : specialties) {
            allProducts.add(stack);
        }

        Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
        refinedProducts.addAll(this.getAllProductsAndFluids(allProducts));
        if (refinedProducts.size() > 0) {
            y = this.getRefined("Refined Products", y, refinedProducts);
            y = y + 8;
        }

        if (products.size() == 0 && specialties.size() == 0) {
            (new ControlTextCentered(this, (float) y, "This tree has no \nfruits or nuts")).setColour(this.getColour());
            y += 28;
        }

        (new ControlTextCentered(this, (float) y, "Possible Fruits")).setColour(this.getColour());
        y = y + 12;
        Collection<IAllele> fruitAlleles = (Collection) Binnie.Genetics.getChromosomeMap(Binnie.Genetics.getTreeRoot()).get(EnumTreeChromosome.FRUITS);

        for (IFruitFamily fam : ind.getGenome().getPrimary().getSuitableFruit()) {
            Collection<ItemStack> stacks = new UniqueItemStackSet();

            for (IAllele a : fruitAlleles) {
                if (((IAlleleFruit) a).getProvider().getFamily() == fam) {
                    for (ItemStack p : ((IAlleleFruit) a).getProvider().getProducts()) {
                        stacks.add(p);
                    }

                    for (ItemStack p : ((IAlleleFruit) a).getProvider().getSpecialty()) {
                        stacks.add(p);
                    }

                    try {
                        if (a.getUID().contains("fruitCocoa")) {
                            stacks.add(new ItemStack(Items.dye, 1, 3));
                        } else if (((IAlleleFruit) a).getProvider() instanceof FruitProviderPod) {
                            FruitProviderPod pod = (FruitProviderPod) ((IAlleleFruit) a).getProvider();
                            Field field = FruitProviderPod.class.getDeclaredField("drop");
                            field.setAccessible(true);

                            for (ItemStack stack : (ItemStack[]) ((ItemStack[]) field.get(pod))) {
                                stacks.add(stack);
                            }
                        }
                    } catch (Exception var23) {
                        ;
                    }
                }
            }

            y = this.getRefined("§o" + fam.getName(), y, stacks);
            y = y + 2;
        }

        this.setSize(new IPoint(this.w(), (float) (y + 8)));
    }

    public String getTitle() {
        return "Fruit";
    }
}
