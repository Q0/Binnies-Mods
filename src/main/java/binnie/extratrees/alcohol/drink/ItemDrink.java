package binnie.extratrees.alcohol.drink;

import binnie.extratrees.alcohol.Alcohol;
import binnie.extratrees.alcohol.AlcoholEffect;
import binnie.extratrees.alcohol.Glassware;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import java.util.List;

public class ItemDrink extends ItemFood implements IFluidContainerItem {
    public ItemDrink() {
        super(0, 0.0f, false);
        this.setCreativeTab(Tabs.tabArboriculture);
        this.setUnlocalizedName("drink");
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.setAlwaysEdible();
    }

    public Glassware getGlassware(final ItemStack container) {
        if (!container.hasTagCompound() || !container.getTagCompound().hasKey("glassware")) {
            return Glassware.BeerMug;
        }
        return Glassware.values()[container.getTagCompound().getShort("glassware")];
    }

    public ItemStack getStack(final Glassware glass, final FluidStack fluid) {
        final ItemStack stack = new ItemStack((Item) this);
        this.saveGlassware(glass, stack);
        this.saveFluid(fluid, stack);
        return stack;
    }

    public void saveGlassware(final Glassware container, final ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setShort("glassware", (short) container.ordinal());
    }

    public void saveFluid(final FluidStack fluid, final ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        final NBTTagCompound nbt = stack.getTagCompound();
        if (fluid == null) {
            nbt.removeTag("fluid");
        } else {
            final NBTTagCompound liq = new NBTTagCompound();
            fluid.writeToNBT(liq);
            nbt.setTag("fluid", (NBTBase) liq);
        }
    }

    public FluidStack getFluid(final ItemStack container) {
        if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("fluid")) {
            return null;
        }
        return FluidStack.loadFluidStackFromNBT(container.stackTagCompound.getCompoundTag("fluid"));
    }

    public int getCapacity(final ItemStack container) {
        return this.getGlassware(container).getCapacity();
    }

    public int fill(final ItemStack container, final FluidStack resource, final boolean doFill) {
        if (resource == null || !container.hasTagCompound()) {
            return 0;
        }
        if (DrinkManager.getLiquid(resource.getFluid()) == null) {
            return 0;
        }
        final FluidStack existing = this.getFluid(container);
        final int space = this.getGlassware(container).getCapacity() - ((existing == null) ? 0 : existing.amount);
        final int added = Math.min(space, resource.amount);
        if (space <= 0) {
            return 0;
        }
        if (existing == null) {
            if (doFill) {
                final FluidStack fill = resource.copy();
                fill.amount = added;
                this.saveFluid(fill, container);
            }
            return added;
        }
        if (!existing.isFluidEqual(resource)) {
            return 0;
        }
        if (doFill) {
            final FluidStack fill = existing.copy();
            fill.amount += added;
            this.saveFluid(fill, container);
        }
        return added;
    }

    public FluidStack drain(final ItemStack container, final int maxDrain, final boolean doDrain) {
        if (!container.hasTagCompound()) {
            return null;
        }
        final FluidStack content = this.getFluid(container);
        if (content == null) {
            return null;
        }
        final int toRemove = Math.min(maxDrain, content.amount);
        FluidStack fill = content.copy();
        final FluidStack drain = content.copy();
        drain.amount = toRemove;
        fill.amount -= toRemove;
        if (fill.amount == 0) {
            fill = null;
        }
        if (doDrain) {
            this.saveFluid(fill, container);
        }
        return drain;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (final Glassware glassware : Glassware.values()) {
            par3List.add(this.getStack(glassware, null));
        }
        par3List.add(this.getStack(Glassware.Wine, Alcohol.RedWine.get(Glassware.Wine.getCapacity())));
    }

    public String getItemStackDisplayName(final ItemStack stack) {
        final FluidStack fluid = this.getFluid(stack);
        final IDrinkLiquid liquid = (fluid == null) ? null : DrinkManager.getLiquid(fluid.getFluid());
        return this.getGlassware(stack).getName((liquid == null) ? null : liquid.getName());
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister par1IconRegister) {
        for (final Glassware glassware : Glassware.values()) {
            glassware.registerIcons(par1IconRegister);
        }
    }

    public IIcon getIcon(final ItemStack stack, final int pass) {
        final Glassware glass = this.getGlassware(stack);
        return (pass == 0) ? glass.glass : ((this.getFluid(stack) == null) ? glass.glass : glass.contents);
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(final ItemStack stack, final int pass) {
        final FluidStack fluid = this.getFluid(stack);
        final IDrinkLiquid drink = (fluid == null) ? null : DrinkManager.getLiquid(fluid.getFluid());
        return (pass == 0) ? 16777215 : ((drink == null) ? 16777215 : drink.getColour());
    }

    public int getRenderPasses(final int metadata) {
        return 2;
    }

    public ItemStack onEaten(final ItemStack p_77654_1_, final World p_77654_2_, final EntityPlayer p_77654_3_) {
        this.drain(p_77654_1_, 30, true);
        AlcoholEffect.makeDrunk(p_77654_3_, 2.1f);
        return p_77654_1_;
    }

    public EnumAction getItemUseAction(final ItemStack p_77661_1_) {
        return (this.getFluid(p_77661_1_) == null) ? EnumAction.none : EnumAction.drink;
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    public int getMaxItemUseDuration(final ItemStack p_77626_1_) {
        return 16;
    }

    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
        return p_77659_1_;
    }
}
