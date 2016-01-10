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
import java.util.Iterator;
import java.util.List;

public class BlockExtraBeeHive extends Block {
    IIcon[][] icons;

    public BlockExtraBeeHive() {
        super(ExtraBees.materialBeehive);
        this.setLightLevel(0.2F);
        this.setHardness(1.0F);
        this.setTickRandomly(true);
        this.setBlockName("hive");
        this.setCreativeTab(Tabs.tabApiculture);
    }

    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return "extrabees.block.hive." + par1ItemStack.getItemDamage();
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
        for (int i = 0; i < 4; ++i) {
            itemList.add(new ItemStack(this, 1, i));
        }

    }

    public IIcon getIcon(int side, int metadata) {
        return metadata >= EnumHiveType.values().length ? null : (side < 2 ? this.icons[metadata][1] : this.icons[metadata][0]);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.icons = new IIcon[EnumHiveType.values().length][2];

        for (EnumHiveType hive : EnumHiveType.values()) {
            this.icons[hive.ordinal()][0] = ExtraBees.proxy.getIcon(register, "hive/" + hive.toString().toLowerCase() + ".0");
            this.icons[hive.ordinal()][1] = ExtraBees.proxy.getIcon(register, "hive/" + hive.toString().toLowerCase() + ".1");
        }

    }

    public ArrayList getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList();
        List<IHiveDrop> dropList = EnumHiveType.values()[metadata].drops;
        Collections.shuffle(dropList);
        int tries = 0;
        boolean hasPrincess = false;

        label43:
        while (tries <= 10 && !hasPrincess) {
            ++tries;
            Iterator i$ = dropList.iterator();

            IHiveDrop drop;
            while (true) {
                if (!i$.hasNext()) {
                    continue label43;
                }

                drop = (IHiveDrop) i$.next();
                if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
                    break;
                }
            }

            ret.add(drop.getPrincess(world, x, y, z, fortune));
            hasPrincess = true;
        }

        for (IHiveDrop drop : dropList) {
            if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
                ret.addAll(drop.getDrones(world, x, y, z, fortune));
                break;
            }
        }

        for (IHiveDrop drop : dropList) {
            if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
                ret.addAll(drop.getAdditional(world, x, y, z, fortune));
                break;
            }
        }

        return ret;
    }
}
