package binnie.extratrees.worldgen;

import binnie.core.Mods;
import com.mojang.authlib.GameProfile;
import forestry.api.arboriculture.ITree;
import forestry.api.world.ITreeGenData;
import net.minecraft.world.World;

public class BlockTypeLeaf extends BlockType {
    public BlockTypeLeaf() {
        super(Mods.Forestry.block("leaves"), 0);
    }

    @Override
    public void setBlock(final World world, final ITree tree, final int x, final int y, final int z) {
        tree.setLeaves(world, null, x, y, z);
    }
}
