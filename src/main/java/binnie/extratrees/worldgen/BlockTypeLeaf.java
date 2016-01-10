package binnie.extratrees.worldgen;

import binnie.core.Mods;
import com.mojang.authlib.GameProfile;
import forestry.api.arboriculture.ITree;
import net.minecraft.world.World;

public class BlockTypeLeaf extends BlockType {
    public BlockTypeLeaf() {
        super(Mods.Forestry.block("leaves"), 0);
    }

    public void setBlock(World world, ITree tree, int x, int y, int z) {
        tree.setLeaves(world, (GameProfile) null, x, y, z);
    }
}
