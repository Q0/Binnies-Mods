package binnie.extrabees.products;

import binnie.Binnie;
import binnie.core.item.IItemEnum;
import binnie.extrabees.ExtraBees;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public enum EnumPropolis implements IItemEnum {
    WATER(0x24b3c9, 0xc2bea7, "Water"),
    OIL(0x172f33, 0xc2bea7, "oil"),
    FUEL(0xa38d12, 0xc2bea7, "fuel"),
    MILK,
    FRUIT,
    SEED,
    ALCOHOL,
    CREOSOTE(8877313, 12428819, "creosote"),
    GLACIAL,
    PEAT;

    int[] colour;
    String liquidName;
    boolean active;

    EnumPropolis() {
        this(16777215, 16777215, "");
        this.active = false;
    }

    EnumPropolis(final int colour, final int colour2, final String liquid) {
        this.colour = new int[0];
        this.active = true;
        this.colour = new int[]{colour, colour2};
        this.liquidName = liquid;
    }

    public void addRecipe() {
        final FluidStack liquid = Binnie.Liquid.getLiquidStack(this.liquidName, 500);
        if (liquid != null) {
            RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[]{this.get(1)}, liquid, null, 0);
        }
    }

    @Override
    public boolean isActive() {
        return this.active && Binnie.Liquid.getLiquidStack(this.liquidName, 100) != null;
    }

    public static EnumPropolis get(final ItemStack itemStack) {
        final int i = itemStack.getItemDamage();

        if (i >= 0 && i < values().length) {
            return values()[i];
        }

        return values()[0];
    }

    @Override
    public ItemStack get(final int size) {
        return new ItemStack(ExtraBees.propolis, size, this.ordinal());
    }

    @Override
    public String getName(final ItemStack stack) {
        return ExtraBees.proxy.localise("item.propolis." + this.name().toLowerCase());
    }
}
