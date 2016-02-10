package binnie.botany.gardening;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockPlant extends BlockBush {
    public BlockPlant() {
        this.setBlockName("plant");
        this.setCreativeTab(CreativeTabBotany.instance);
        this.setTickRandomly(true);
    }

    public static boolean isWeed(final IBlockAccess world, final int x, final int y, final int z) {
        if (!(world.getBlock(x, y, z) instanceof BlockPlant)) {
            return false;
        }
        final Type type = Type.get(world.getBlockMetadata(x, y, z));
        return type == Type.Weeds || type == Type.WeedsLong || type == Type.WeedsVeryLong;
    }

    public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int metadata, final int fortune) {
        return new ArrayList<ItemStack>();
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        for (final Type t : Type.values()) {
            t.icon = Botany.proxy.getIcon(p_149651_1_, t.name().toLowerCase());
        }
    }

    protected boolean canPlaceBlockOn(final Block p_149854_1_) {
        return super.canPlaceBlockOn(p_149854_1_) || Gardening.isSoil(p_149854_1_);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int side, final int meta) {
        return Type.values()[meta % Type.values().length].icon;
    }

    public int damageDropped(final int p_149692_1_) {
        return p_149692_1_;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        for (final Type type : Type.values()) {
            p_149666_3_.add(type.get());
        }
    }

    public void updateTick(final World world, final int x, final int y, final int z, final Random random) {
        final Type type = Type.get(world.getBlockMetadata(x, y, z));
        if (random.nextInt(4) == 0) {
            if (type == Type.Weeds) {
                world.setBlockMetadataWithNotify(x, y, z, Type.WeedsLong.ordinal(), 2);
            } else if (type == Type.WeedsLong) {
                world.setBlockMetadataWithNotify(x, y, z, Type.WeedsVeryLong.ordinal(), 2);
            } else if (type == Type.DeadFlower) {
                world.setBlockMetadataWithNotify(x, y, z, Type.DecayingFlower.ordinal(), 2);
            } else if (type == Type.DecayingFlower) {
                world.setBlockToAir(x, y, z);
                return;
            }
        }
        if (random.nextInt(6) == 0) {
            if (type == Type.Weeds) {
                world.setBlockToAir(x, y, z);
            } else if (type == Type.WeedsLong) {
                world.setBlockMetadataWithNotify(x, y, z, Type.Weeds.ordinal(), 2);
            } else if (type == Type.WeedsVeryLong) {
                world.setBlockMetadataWithNotify(x, y, z, Type.WeedsLong.ordinal(), 2);
            }
        }
        final Block below = world.getBlock(x, y - 1, z);
        if (Gardening.isSoil(below)) {
            final IBlockSoil soil = (IBlockSoil) below;
            if (random.nextInt(3) == 0) {
                if (type == Type.Weeds || type == Type.WeedsLong || type == Type.WeedsVeryLong) {
                    if (!soil.degrade(world, x, y - 1, z, EnumSoilType.LOAM)) {
                        soil.degrade(world, x, y - 1, z, EnumSoilType.SOIL);
                    }
                } else if (type == Type.DecayingFlower && !soil.fertilise(world, x, y - 1, z, EnumSoilType.LOAM)) {
                    soil.fertilise(world, x, y - 1, z, EnumSoilType.FLOWERBED);
                }
            }
        }
    }

    public boolean isReplaceable(final IBlockAccess world, final int x, final int y, final int z) {
        return true;
    }

    public enum Type {
        Weeds("Weeds"),
        WeedsLong("Long Weeds"),
        WeedsVeryLong("Very Long Weeds"),
        DeadFlower("Dead Flower"),
        DecayingFlower("Decaying Flower");

        public IIcon icon;
        String name;

        private Type(final String name) {
            this.name = name;
        }

        public static Type get(final int id) {
            return values()[id % values().length];
        }

        public ItemStack get() {
            return new ItemStack((Block) Botany.plant, 1, this.ordinal());
        }

        public String getName() {
            return this.name;
        }
    }
}
