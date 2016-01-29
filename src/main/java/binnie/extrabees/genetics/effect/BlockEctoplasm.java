package binnie.extrabees.genetics.effect;

import binnie.extrabees.ExtraBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockWeb;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockEctoplasm extends BlockWeb {
    public BlockEctoplasm() {
        setLightOpacity(1);
        setHardness(0.5f);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister register) {
        blockIcon = ExtraBees.proxy.getIcon(register, "ectoplasm");
    }

    public int quantityDropped(final Random rand) {
        return (rand.nextInt(5) == 0) ? 1 : 0;
    }

    public Item getItemDropped(final int p_149650_1_, final Random rand, final int p_149650_3_) {
        return Items.slime_ball;
    }

    public String getUnlocalizedName() {
        return "extrabees.block.ectoplasm";
    }
}
