package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.ceramic.BlockCeramic;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.botany.proxy.Proxy;
import binnie.core.BinnieCore;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.IMultipassBlock;
import binnie.core.block.MultipassBlockRenderer;
import binnie.core.block.TileEntityMetadata;
import binnie.core.item.ItemMisc;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCeramicBrick
        extends Block
        implements IBlockMetadata,
        IMultipassBlock {
    public BlockCeramicBrick() {
        super(Material.rock);
        this.setHardness(1.0f);
        this.setResistance(5.0f);
        this.setBlockName("ceramicBrick");
        this.setCreativeTab(CreativeTabBotany.instance);
    }

    private static BlockType getType(int meta) {
        return new BlockType(meta);
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int blockMeta, int fortune) {
        return BlockMetadata.getBlockDropped(this, world, x, y, z, blockMeta);
    }

    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
        return BlockMetadata.breakBlock(this, player, world, x, y, z);
    }

    public TileEntity createNewTileEntity(World var1, int i) {
        return new TileEntityMetadata();
    }

    public boolean hasTileEntity(int meta) {
        return true;
    }

    public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6) {
        super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
        TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
        return tileentity != null ? tileentity.receiveClientEvent(par5, par6) : false;
    }

    @Override
    public int getPlacedMeta(ItemStack stack, World world, int x, int y, int z, ForgeDirection clickedBlock) {
        return TileEntityMetadata.getItemDamage(stack);
    }

    @Override
    public int getDroppedMeta(int blockMeta, int tileMeta) {
        return tileMeta;
    }

    @Override
    public String getBlockName(ItemStack par1ItemStack) {
        int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
        return BlockCeramicBrick.getType(meta).getName();
    }

    @Override
    public void getBlockTooltip(ItemStack par1ItemStack, List par3List) {
    }

    @Override
    public void dropAsStack(World world, int x, int y, int z, ItemStack drop) {
        this.dropBlockAsItem(world, x, y, z, drop);
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
        for (EnumFlowerColor c : EnumFlowerColor.values()) {
            BlockType type = new BlockType(c, c, TileType.Tile);
            itemList.add(TileEntityMetadata.getItemStack(this, type.ordinal()));
        }
        for (TileType type : TileType.values()) {
            if (!type.canDouble()) continue;
            itemList.add(new BlockType(EnumFlowerColor.Brown, EnumFlowerColor.Gold, (TileType)((Object)type)).getStack(1));
        }
        itemList.add(new BlockType(EnumFlowerColor.Gold, EnumFlowerColor.Gold, TileType.Split).getStack(1));
        itemList.add(new BlockType(EnumFlowerColor.Brown, EnumFlowerColor.Brown, TileType.Chequered).getStack(1));
        itemList.add(new BlockType(EnumFlowerColor.Gold, EnumFlowerColor.Brown, TileType.LargeBrick).getStack(1));
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        if (tile != null) {
            return this.getIcon(side, tile.getTileMetadata());
        }
        return super.getIcon(world, x, y, z, side);
    }

    public IIcon getIcon(int side, int meta) {
        return BlockCeramicBrick.getType(meta).getIcon(MultipassBlockRenderer.getLayer());
    }

    @SideOnly(value=Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        for (TileType type : TileType.values()) {
            for (int i = 0; i < 3; ++i) {
                type.icons[i] = Botany.proxy.getIcon(register, "ceramic." + type.id + "." + i);
            }
        }
    }

    @SideOnly(value=Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        if (tile != null) {
            return this.getRenderColor(tile.getTileMetadata());
        }
        return 16777215;
    }

    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeTileEntity(par2, par3, par4);
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return BlockMetadata.getPickBlock(world, x, y, z);
    }

    @SideOnly(value=Side.CLIENT)
    public int getRenderColor(int meta) {
        return this.colorMultiplier(meta);
    }

    @Override
    public int getNumberOfPasses() {
        return 3;
    }

    @Override
    public int colorMultiplier(int meta) {
        BlockType type = BlockCeramicBrick.getType(meta);
        if (MultipassBlockRenderer.getLayer() == 0) {
            return 16777215;
        }
        if (MultipassBlockRenderer.getLayer() == 1) {
            return type.color1.getColor(false);
        }
        return type.color2.getColor(false);
    }

    public int getRenderType() {
        return BinnieCore.multipassRenderID;
    }

    public static class BlockType {
        EnumFlowerColor color1;
        EnumFlowerColor color2;
        TileType type;

        private BlockType(EnumFlowerColor color1, EnumFlowerColor color2, TileType type) {
            this.color1 = color1;
            this.color2 = color2;
            this.type = type;
        }

        public boolean isTwoColors() {
            return this.type.canDouble() && this.color2 != this.color1;
        }

        public BlockType(ItemStack stack) {
            this(TileEntityMetadata.getItemDamage(stack));
        }

        public ItemStack getStack(int i) {
            ItemStack s = TileEntityMetadata.getItemStack(Botany.ceramicBrick, this.ordinal());
            s.stackSize = i;
            return s;
        }

        public BlockType(int id) {
            this.color1 = EnumFlowerColor.get(id & 255);
            this.color2 = EnumFlowerColor.get(id >> 8 & 255);
            this.type = TileType.get(id >> 16 & 255);
        }

        public String getName() {
            String name = this.color1.getName();
            if (this.type.canDouble() && this.color2 != this.color1) {
                name = name + " & " + this.color2.getName();
            }
            return name + " " + this.type.name;
        }

        public int ordinal() {
            return this.color1.ordinal() + this.color2.ordinal() * 256 + this.type.ordinal() * 256 * 256;
        }

        public IIcon getIcon(int layer) {
            return this.type.icons[layer];
        }
    }

    public static enum TileType {
        Tile("tile", "Ceramic Tile"),
        Brick("brick", "Ceramic Bricks"),
        StripeBrick("brickstripe", "Striped Ceramic Bricks"),
        LargeBrick("bricklarge", "Large Ceramic Bricks"),
        Split("split", "Split Ceramic Tile"),
        Chequered("cheque", "Chequered Ceramic Tile"),
        Mixed("mixed", "Mixed Ceramic Tile"),
        VerticalBrick("verticalbrick", "Vertical Ceramic Bricks"),
        VerticalStripeBrick("verticalbrickstripe", "Vertical Striped Ceramic Bricks"),
        VerticalLargeBrick("verticalbricklarge", "Large Vertical Ceramic Bricks");

        String id;
        String name;
        IIcon[] icons = new IIcon[3];

        private TileType(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public static TileType get(int id) {
            return TileType.values()[id % TileType.values().length];
        }

        public boolean canDouble() {
            return this != Tile;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public ItemStack getRecipe(List<ItemStack> stacks) {
            switch (this) {
                case Tile: {
                    if (stacks.size() != 4) {
                        return null;
                    }
                    int mortars = 0;
                    int blocks = 0;
                    int blockColor = -1;
                    for (ItemStack stack : stacks) {
                        if (this.isMortar(stack)) {
                            ++mortars;
                            continue;
                        }
                        if (stack.getItem() != Item.getItemFromBlock((Block)Botany.ceramic)) return null;
                        ++blocks;
                        int color = TileEntityMetadata.getItemDamage(stack);
                        if (blockColor == -1) {
                            blockColor = color;
                            continue;
                        }
                        if (blockColor == color) continue;
                        return null;
                    }
                    if (mortars != 1) {
                        return null;
                    }
                    EnumFlowerColor c = EnumFlowerColor.get(blockColor);
                    return new BlockType(c, c, Tile).getStack(3);
                }
                case Split: {
                    if (stacks.size() != 4) {
                        return null;
                    }
                    int[] colors = new int[]{-1, -1};
                    int altCounter = 0;
                    for (ItemStack stack : stacks) {
                        int alt = altCounter == 0 || altCounter == 3 ? 0 : 1;
                        if (stack.getItem() != Item.getItemFromBlock((Block)Botany.ceramicBrick)) return null;
                        BlockType type = new BlockType(stack);
                        if (type.type != Tile) {
                            return null;
                        }
                        int color = type.color1.ordinal();
                        if (colors[alt] == -1) {
                            colors[alt] = color;
                        } else if (colors[alt] != color) {
                            return null;
                        }
                        ++altCounter;
                    }
                    return new BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), Split).getStack(4);
                }
                case Chequered: {
                    if (stacks.size() != 4) {
                        return null;
                    }
                    int[] colors = new int[]{-1, -1};
                    for (ItemStack stack : stacks) {
                        if (stack.getItem() != Item.getItemFromBlock((Block)Botany.ceramicBrick)) return null;
                        BlockType type = new BlockType(stack);
                        if (type.type != Split) {
                            return null;
                        }
                        int color = type.color1.ordinal();
                        int color2 = type.color2.ordinal();
                        if (colors[0] == -1) {
                            colors[0] = color;
                            colors[1] = color2;
                            continue;
                        }
                        if (colors[0] == color && colors[1] == color2) continue;
                        return null;
                    }
                    return new BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), Chequered).getStack(4);
                }
                case Mixed: {
                    if (stacks.size() != 4) {
                        return null;
                    }
                    int[] colors = new int[]{-1, -1};
                    for (int index = 0; index < stacks.size(); ++index) {
                        ItemStack stack = stacks.get(index);
                        if (stack.getItem() != Item.getItemFromBlock((Block)Botany.ceramicBrick)) {
                            return null;
                        }
                        BlockType type = new BlockType(stack);
                        if (type.isTwoColors()) {
                            return null;
                        }
                        int color = type.color1.ordinal();
                        if (type.type == Split) {
                            if (colors[1] != -1) return null;
                            colors[1] = color;
                        } else {
                            if (type.type != Chequered) return null;
                            if (colors[0] == -1) {
                                colors[0] = color;
                            } else if (colors[0] != color) {
                                return null;
                            }
                        }
                        int color2 = type.color2.ordinal();
                    }
                    if (colors[0] != -1 && colors[1] != -1) return new BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), Mixed).getStack(4);
                    return null;
                }
                case LargeBrick: {
                    if (stacks.size() == 1) {
                        ItemStack stack = stacks.get(0);
                        BlockType type = new BlockType(stack);
                        if (type.type != VerticalLargeBrick) return null;
                        type.type = this;
                        return type.getStack(1);
                    }
                    if (stacks.size() != 3) {
                        return null;
                    }
                    int[] colors = new int[]{-1, -1};
                    int a = 0;
                    int b = 0;
                    for (int index = 0; index < stacks.size(); ++index) {
                        ItemStack stack = stacks.get(index);
                        if (stack.getItem() != Item.getItemFromBlock((Block)Botany.ceramicBrick)) {
                            return null;
                        }
                        BlockType type = new BlockType(stack);
                        if (type.type != Tile) {
                            return null;
                        }
                        int color = type.color1.ordinal();
                        if (colors[0] == -1) {
                            colors[0] = color;
                            ++a;
                            continue;
                        }
                        if (colors[0] == color) {
                            ++a;
                            continue;
                        }
                        if (colors[1] == -1) {
                            colors[1] = color;
                            ++b;
                            continue;
                        }
                        if (colors[1] != color) return null;
                        ++b;
                    }
                    if (colors[1] != -1) return new BlockType(EnumFlowerColor.get(colors[a > b ? 1 : 0]), EnumFlowerColor.get(colors[a > b ? 0 : 1]), LargeBrick).getStack(3);
                    colors[1] = colors[0];
                    return new BlockType(EnumFlowerColor.get(colors[a > b ? 1 : 0]), EnumFlowerColor.get(colors[a > b ? 0 : 1]), LargeBrick).getStack(3);
                }
                case Brick:
                case StripeBrick: {
                    if (stacks.size() == 1) {
                        ItemStack stack = stacks.get(0);
                        BlockType type = new BlockType(stack);
                        if (type.type == VerticalBrick) {
                            type.type = this;
                            return type.getStack(1);
                        }
                        if (type.type != StripeBrick) return null;
                        type.type = this;
                        return type.getStack(1);
                    }
                    if (stacks.size() != 4) {
                        return null;
                    }
                    int[] colors = new int[]{-1, -1};
                    int index = 0;
                    while (index < stacks.size()) {
                        int alt;
                        ItemStack stack = stacks.get(index);
                        if (stack.getItem() != Item.getItemFromBlock((Block)Botany.ceramicBrick)) {
                            return null;
                        }
                        BlockType type = new BlockType(stack);
                        if (type.type != LargeBrick) {
                            return null;
                        }
                        int color = type.color1.ordinal();
                        int color2 = type.color2.ordinal();
                        int n = alt = index == 0 || index == 3 ? 0 : 1;
                        if (this == StripeBrick) {
                            alt = 0;
                        }
                        if (colors[alt] == -1) {
                            colors[alt] = color;
                            colors[1 - alt] = color2;
                        } else if (colors[alt] != color || colors[1 - alt] != color2) {
                            return null;
                        }
                        ++index;
                    }
                    return new BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), this).getStack(4);
                }
                case VerticalLargeBrick:
                case VerticalBrick:
                case VerticalStripeBrick: {
                    if (stacks.size() != 1) {
                        return null;
                    }
                    ItemStack stack = stacks.get(0);
                    BlockType type = new BlockType(stack);
                    if (type.type == LargeBrick) {
                        type.type = VerticalLargeBrick;
                        return type.getStack(1);
                    }
                    if (type.type == Brick) {
                        type.type = VerticalBrick;
                        return type.getStack(1);
                    }
                    if (type.type != StripeBrick) return null;
                    type.type = VerticalStripeBrick;
                    return type.getStack(1);
                }
            }
            return null;
        }

        private boolean isMortar(ItemStack stack) {
            return stack.getItem() == Botany.misc && stack.getItemDamage() == BotanyItems.Mortar.ordinal();
        }
    }

}