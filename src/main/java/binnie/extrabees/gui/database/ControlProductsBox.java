package binnie.extrabees.gui.database;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.core.IWidget;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.IAllele;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControlProductsBox extends ControlListBox<ControlProductsBox.Product> {
    IAlleleBeeSpecies species;
    private int index;
    private Type type;

    public ControlProductsBox(final IWidget parent, final int x, final int y, final int width, final int height, final Type type) {
        super(parent, x, y, width, height, 12.0f);
        this.species = null;
        this.type = type;
    }

    @Override
    public IWidget createOption(final Product value, final int y) {
        return new ControlProductsItem(getContent(), value, y);
    }

    public void setSpecies(final IAlleleBeeSpecies species) {
        if (species != this.species && (this.species = species) != null) {
            final IAllele[] template = Binnie.Genetics.getBeeRoot().getTemplate(species.getUID());
            if (template == null) {
                return;
            }
            final IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(template);
            final float speed = genome.getSpeed();
            final float modeSpeed = Binnie.Genetics.getBeeRoot().getBeekeepingMode(BinnieCore.proxy.getWorld()).getBeeModifier().getProductionModifier(genome, 1.0f);
            final List<Product> strings = new ArrayList<Product>();
            if (this.type == Type.Products) {
                strings.addAll(species.getProductChances().entrySet().stream().map(entry -> new Product(entry.getKey(), speed * modeSpeed * entry.getValue())).collect(Collectors.toList()));
            } else {
                strings.addAll(species.getSpecialtyChances().entrySet().stream().map(entry -> new Product(entry.getKey(), speed * modeSpeed * entry.getValue())).collect(Collectors.toList()));
            }
            this.setOptions(strings);
        }
    }

    enum Type {
        Products,
        Specialties;
    }

    class Product {
        ItemStack item;
        float chance;

        public Product(final ItemStack item, final float chance) {
            this.item = item;
            this.chance = chance;
        }
    }
}
