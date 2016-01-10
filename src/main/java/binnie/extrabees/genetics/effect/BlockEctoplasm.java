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
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = ExtraBees.proxy.getIcon(register, "ectoplasm");
    }

    public BlockEctoplasm() {
        super();
        this.setLightOpacity(1);
        this.setHardness(0.5F);
    }

    public int quantityDropped(Random rand) {
        return rand.nextInt(5) == 0 ? 1 : 0;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Items.slime_ball;
    }

    public String getUnlocalizedName() {
        return "extrabees.block.ectoplasm";
    }
}
