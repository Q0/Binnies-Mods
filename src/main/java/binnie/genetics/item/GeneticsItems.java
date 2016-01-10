package binnie.genetics.item;

import binnie.core.item.IItemMisc;
import binnie.genetics.Genetics;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public enum GeneticsItems implements IItemMisc {
    LaboratoryCasing("Reinforced Casing", "casingIron"),
    DNADye("DNA Dye", "dnaDye"),
    FluorescentDye("Fluorescent Dye", "dyeFluor"),
    Enzyme("Enzyme", "enzyme"),
    GrowthMedium("Growth Medium", "growthMedium"),
    EmptySequencer("Blank Sequence", "sequencerEmpty"),
    EmptySerum("Empty Serum Vial", "serumEmpty"),
    EmptyGenome("Empty Serum Array", "genomeEmpty"),
    Cylinder("Glass Cylinder", "cylinderEmpty"),
    IntegratedCircuit("Integrated Circuit Board", "integratedCircuit"),
    IntegratedCPU("Integrated CPU", "integratedCPU"),
    IntegratedCasing("Integrated Casing", "casingCircuit");

    IIcon icon;
    String name;
    String iconPath;

    private GeneticsItems(String name, String iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    public IIcon getIcon(ItemStack stack) {
        return this.icon;
    }

    public void registerIcons(IIconRegister register) {
        this.icon = Genetics.proxy.getIcon(register, this.iconPath);
    }

    public void addInformation(List par3List) {
    }

    public String getName(ItemStack stack) {
        return this.name;
    }

    public ItemStack get(int size) {
        return Genetics.itemGenetics == null ? null : new ItemStack(Genetics.itemGenetics, size, this.ordinal());
    }

    public boolean isActive() {
        return true;
    }
}
