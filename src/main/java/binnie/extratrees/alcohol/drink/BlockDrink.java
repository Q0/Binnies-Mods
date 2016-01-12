package binnie.extratrees.alcohol.drink;

import binnie.extratrees.alcohol.ModuleAlcohol;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockDrink extends BlockContainer {
    public BlockDrink() {
        super(Material.glass);
        this.setBlockName("drink");
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityDrink();
    }

    public int getRenderType() {
        return ModuleAlcohol.drinkRendererID;
    }

    public boolean isNormalCube() {
        return false;
    }

    public boolean isOpaqueCube() {
        return true;
    }
}
