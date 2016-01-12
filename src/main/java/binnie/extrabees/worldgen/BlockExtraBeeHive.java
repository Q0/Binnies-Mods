package binnie.extrabees.worldgen;

import binnie.extrabees.ExtraBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockExtraBeeHive extends Block {
    IIcon[][] icons;

    public BlockExtraBeeHive() {
        super(ExtraBees.materialBeehive);
        this.setLightLevel(0.2f);
        this.setHardness(1.0f);
        this.setTickRandomly(true);
        this.setBlockName("hive");
        this.setCreativeTab(Tabs.tabApiculture);
    }

    public String getUnlocalizedName(final ItemStack par1ItemStack) {
        return "extrabees.block.hive." + par1ItemStack.getItemDamage();
    }

    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
        for (int i = 0; i < 4; ++i) {
            itemList.add(new ItemStack((Block) this, 1, i));
        }
    }

    public IIcon getIcon(final int side, final int metadata) {
        if (metadata >= EnumHiveType.values().length) {
            return null;
        }
        if (side < 2) {
            return this.icons[metadata][1];
        }
        return this.icons[metadata][0];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister register) {
        this.icons = new IIcon[EnumHiveType.values().length][2];
        for (final EnumHiveType hive : EnumHiveType.values()) {
            this.icons[hive.ordinal()][0] = ExtraBees.proxy.getIcon(register, "hive/" + hive.toString().toLowerCase() + ".0");
            this.icons[hive.ordinal()][1] = ExtraBees.proxy.getIcon(register, "hive/" + hive.toString().toLowerCase() + ".1");
        }
    }

    public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int metadata, final int fortune) {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        final List<IHiveDrop> dropList = EnumHiveType.values()[metadata].drops;
        Collections.shuffle(dropList);
        int tries = 0;
        for (boolean hasPrincess = false; tries <= 10 && !hasPrincess; hasPrincess = true) {
            ++tries;
            for (final IHiveDrop drop : dropList) {
                if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
                    ret.add(drop.getPrincess(world, x, y, z, fortune));
                    break;
                }
            }
        }
        for (final IHiveDrop drop : dropList) {
            if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
                ret.addAll(drop.getDrones(world, x, y, z, fortune));
                break;
            }
        }
        for (final IHiveDrop drop : dropList) {
            if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
                ret.addAll(drop.getAdditional(world, x, y, z, fortune));
                break;
            }
        }
        return ret;
    }
}
