package binnie.extrabees.gui.database;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.core.IWidget;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.genetics.IAllele;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ControlProductsBox extends ControlListBox {
    private int index;
    private ControlProductsBox.Type type;
    IAlleleBeeSpecies species = null;

    public IWidget createOption(ControlProductsBox.Product value, int y) {
        return new ControlProductsItem((ControlList) getContent(), value, y);
    }

    public ControlProductsBox(IWidget parent, int x, int y, int width, int height, ControlProductsBox.Type type) {
        super(parent, x, y, width, height, 12.0F);
        this.type = type;
    }

    public void setSpecies(IAlleleBeeSpecies species) {
        if (species != this.species) {
            this.species = species;
            
            if (species != null) {
                IAllele[] template = Binnie.Genetics.getBeeRoot().getTemplate(species.getUID());
                if (template == null) {
                    return;
                }

                IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(template);
                float speed = genome.getSpeed();
                float modeSpeed = Binnie.Genetics.getBeeRoot().getBeekeepingMode(BinnieCore.proxy.getWorld()).getBeeModifier().getProductionModifier(genome, 1.0F);
                List<ControlProductsBox.Product> strings = new ArrayList();
                //TODO:FIX
                /*if(this.type == ControlProductsBox.Type.Products) {
                    for(Entry<ItemStack, Integer> entry : species.getProducts().entrySet()) {
                        strings.add(new ControlProductsBox.Product((ItemStack)entry.getKey(), speed * modeSpeed * (float)((Integer)entry.getValue()).intValue()));
                    }
                } else {
                    for(Entry<ItemStack, Integer> entry : species.getSpecialty().entrySet()) {
                        strings.add(new ControlProductsBox.Product((ItemStack)entry.getKey(), speed * modeSpeed * (float)((Integer)entry.getValue()).intValue()));
                    }
                }*/

                setOptions(strings);
            }
        }
    }

    class Product {
        ItemStack item;
        float chance;

        public Product(ItemStack item, float chance) {
            super();
            this.item = item;
            this.chance = chance;
        }
    }

    enum Type {
        Products,
        Specialties;

        Type() {
        }
    }
}
