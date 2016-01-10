package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.core.BinnieCore;
import binnie.core.block.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BlockCeramicBrick extends Block implements IBlockMetadata, IMultipassBlock {
    public BlockCeramicBrick() {
        super(Material.rock);
        this.setHardness(1.0F);
        this.setResistance(5.0F);
        this.setBlockName("ceramicBrick");
        this.setCreativeTab(CreativeTabBotany.instance);
    }

    private static BlockCeramicBrick.BlockType getType(int meta) {
        return new BlockCeramicBrick.BlockType(meta);
    }

    public ArrayList getDrops(World world, int x, int y, int z, int blockMeta, int fortune) {
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

    public int getPlacedMeta(ItemStack stack, World world, int x, int y, int z, ForgeDirection clickedBlock) {
        return TileEntityMetadata.getItemDamage(stack);
    }

    public int getDroppedMeta(int blockMeta, int tileMeta) {
        return tileMeta;
    }

    public String getBlockName(ItemStack par1ItemStack) {
        int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
        return getType(meta).getName();
    }

    public void getBlockTooltip(ItemStack par1ItemStack, List par3List) {
    }

    public void dropAsStack(World world, int x, int y, int z, ItemStack drop) {
        this.dropBlockAsItem(world, x, y, z, drop);
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
        for (EnumFlowerColor c : EnumFlowerColor.values()) {
            BlockCeramicBrick.BlockType type = new BlockCeramicBrick.BlockType(c, c, BlockCeramicBrick.TileType.Tile);
            itemList.add(TileEntityMetadata.getItemStack(this, type.ordinal()));
        }

        for (BlockCeramicBrick.TileType type : BlockCeramicBrick.TileType.values()) {
            if (type.canDouble()) {
                itemList.add((new BlockCeramicBrick.BlockType(EnumFlowerColor.Brown, EnumFlowerColor.Gold, type)).getStack(1));
            }
        }

        itemList.add((new BlockCeramicBrick.BlockType(EnumFlowerColor.Gold, EnumFlowerColor.Gold, BlockCeramicBrick.TileType.Split)).getStack(1));
        itemList.add((new BlockCeramicBrick.BlockType(EnumFlowerColor.Brown, EnumFlowerColor.Brown, BlockCeramicBrick.TileType.Chequered)).getStack(1));
        itemList.add((new BlockCeramicBrick.BlockType(EnumFlowerColor.Gold, EnumFlowerColor.Brown, BlockCeramicBrick.TileType.LargeBrick)).getStack(1));
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        return tile != null ? this.getIcon(side, tile.getTileMetadata()) : super.getIcon(world, x, y, z, side);
    }

    public IIcon getIcon(int side, int meta) {
        return getType(meta).getIcon(MultipassBlockRenderer.getLayer());
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        for (BlockCeramicBrick.TileType type : BlockCeramicBrick.TileType.values()) {
            for (int i = 0; i < 3; ++i) {
                type.icons[i] = Botany.proxy.getIcon(register, "ceramic." + type.id + "." + i);
            }
        }

    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        return tile != null ? this.getRenderColor(tile.getTileMetadata()) : 16777215;
    }

    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeTileEntity(par2, par3, par4);
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return BlockMetadata.getPickBlock(world, x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return this.colorMultiplier(meta);
    }

    public int getNumberOfPasses() {
        return 3;
    }

    public int colorMultiplier(int meta) {
        BlockCeramicBrick.BlockType type = getType(meta);
        return MultipassBlockRenderer.getLayer() == 0 ? 16777215 : (MultipassBlockRenderer.getLayer() == 1 ? type.color1.getColor(false) : type.color2.getColor(false));
    }

    public int getRenderType() {
        return BinnieCore.multipassRenderID;
    }

    public static class BlockType {
        EnumFlowerColor color1;
        EnumFlowerColor color2;
        BlockCeramicBrick.TileType type;

        private BlockType(EnumFlowerColor color1, EnumFlowerColor color2, BlockCeramicBrick.TileType type) {
            super();
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
            super();
            this.color1 = EnumFlowerColor.get(id & 255);
            this.color2 = EnumFlowerColor.get(id >> 8 & 255);
            this.type = BlockCeramicBrick.TileType.get(id >> 16 & 255);
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

        public static BlockCeramicBrick.TileType get(int id) {
            return values()[id % values().length];
        }

        public boolean canDouble() {
            return this != Tile;
        }

        public ItemStack getRecipe(List stacks) {
            switch (this) {
                case Tile:
                    if (stacks.size() != 4) {
                        return null;
                    } else {
                        int mortars = 0;
                        int blocks = 0;
                        int blockColor = -1;

                        for (ItemStack stack : stacks) {
                            if (this.isMortar(stack)) {
                                ++mortars;
                            } else {
                                if (stack.getItem() != Item.getItemFromBlock(Botany.ceramic)) {
                                    return null;
                                }

                                ++blocks;
                                int color = TileEntityMetadata.getItemDamage(stack);
                                if (blockColor == -1) {
                                    blockColor = color;
                                } else if (blockColor != color) {
                                    return null;
                                }
                            }
                        }

                        if (mortars != 1) {
                            return null;
                        }

                        EnumFlowerColor c = EnumFlowerColor.get(blockColor);
                        return (new BlockCeramicBrick.BlockType(c, c, Tile)).getStack(3);
                    }
                case Split:
                    if (stacks.size() != 4) {
                        return null;
                    } else {
                        int[] colors = new int[]{-1, -1};
                        int altCounter = 0;
                        Iterator i$ = stacks.iterator();

                        for (; i$.hasNext(); ++altCounter) {
                            ItemStack stack = (ItemStack) i$.next();
                            int alt = altCounter != 0 && altCounter != 3 ? 1 : 0;
                            if (stack.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                                return null;
                            }

                            BlockCeramicBrick.BlockType type = new BlockCeramicBrick.BlockType(stack);
                            if (type.type != Tile) {
                                return null;
                            }

                            int color = type.color1.ordinal();
                            if (colors[alt] == -1) {
                                colors[alt] = color;
                            } else if (colors[alt] != color) {
                                return null;
                            }
                        }

                        return (new BlockCeramicBrick.BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), Split)).getStack(4);
                    }
                case Chequered:
                    if (stacks.size() != 4) {
                        return null;
                    } else {
                        int[] var18 = new int[]{-1, -1};

                        for (ItemStack stack : stacks) {
                            if (stack.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                                return null;
                            }

                            BlockCeramicBrick.BlockType type = new BlockCeramicBrick.BlockType(stack);
                            if (type.type != Split) {
                                return null;
                            }

                            int color = type.color1.ordinal();
                            int color2 = type.color2.ordinal();
                            if (var18[0] != -1) {
                                if (var18[0] != color || var18[1] != color2) {
                                    return null;
                                }
                            } else {
                                var18[0] = color;
                                var18[1] = color2;
                            }
                        }

                        return (new BlockCeramicBrick.BlockType(EnumFlowerColor.get(var18[0]), EnumFlowerColor.get(var18[1]), Chequered)).getStack(4);
                    }
                case Mixed:
                    if (stacks.size() != 4) {
                        return null;
                    } else {
                        int[] var17 = new int[]{-1, -1};

                        for (int index = 0; index < stacks.size(); ++index) {
                            ItemStack stack = (ItemStack) stacks.get(index);
                            if (stack.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                                return null;
                            }

                            BlockCeramicBrick.BlockType type = new BlockCeramicBrick.BlockType(stack);
                            if (type.isTwoColors()) {
                                return null;
                            }

                            int color = type.color1.ordinal();
                            if (type.type == Split) {
                                if (var17[1] != -1) {
                                    return null;
                                }

                                var17[1] = color;
                            } else {
                                if (type.type != Chequered) {
                                    return null;
                                }

                                if (var17[0] == -1) {
                                    var17[0] = color;
                                } else if (var17[0] != color) {
                                    return null;
                                }
                            }

                            int color2 = type.color2.ordinal();
                        }

                        if (var17[0] != -1 && var17[1] != -1) {
                            return (new BlockCeramicBrick.BlockType(EnumFlowerColor.get(var17[0]), EnumFlowerColor.get(var17[1]), Mixed)).getStack(4);
                        }

                        return null;
                    }
                case LargeBrick:
                    if (stacks.size() == 1) {
                        ItemStack stack = (ItemStack) stacks.get(0);
                        BlockCeramicBrick.BlockType type = new BlockCeramicBrick.BlockType(stack);
                        if (type.type == VerticalLargeBrick) {
                            type.type = this;
                            return type.getStack(1);
                        }

                        return null;
                    } else if (stacks.size() != 3) {
                        return null;
                    } else {
                        int[] var16 = new int[]{-1, -1};
                        int a = 0;
                        int b = 0;

                        for (int index = 0; index < stacks.size(); ++index) {
                            ItemStack stack = (ItemStack) stacks.get(index);
                            if (stack.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                                return null;
                            }

                            BlockCeramicBrick.BlockType type = new BlockCeramicBrick.BlockType(stack);
                            if (type.type != Tile) {
                                return null;
                            }

                            int color = type.color1.ordinal();
                            if (var16[0] == -1) {
                                var16[0] = color;
                                ++a;
                            } else if (var16[0] == color) {
                                ++a;
                            } else if (var16[1] == -1) {
                                var16[1] = color;
                                ++b;
                            } else {
                                if (var16[1] != color) {
                                    return null;
                                }

                                ++b;
                            }
                        }

                        if (var16[1] == -1) {
                            var16[1] = var16[0];
                        }

                        return (new BlockCeramicBrick.BlockType(EnumFlowerColor.get(var16[a > b ? 1 : 0]), EnumFlowerColor.get(var16[a > b ? 0 : 1]), LargeBrick)).getStack(3);
                    }
                case Brick:
                case StripeBrick:
                    if (stacks.size() == 1) {
                        ItemStack stack = (ItemStack) stacks.get(0);
                        BlockCeramicBrick.BlockType type = new BlockCeramicBrick.BlockType(stack);
                        if (type.type == VerticalBrick) {
                            type.type = this;
                            return type.getStack(1);
                        } else {
                            if (type.type == StripeBrick) {
                                type.type = this;
                                return type.getStack(1);
                            }

                            return null;
                        }
                    } else if (stacks.size() != 4) {
                        return null;
                    } else {
                        int[] colors = new int[]{-1, -1};

                        for (int index = 0; index < stacks.size(); ++index) {
                            ItemStack stack = (ItemStack) stacks.get(index);
                            if (stack.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                                return null;
                            }

                            BlockCeramicBrick.BlockType type = new BlockCeramicBrick.BlockType(stack);
                            if (type.type != LargeBrick) {
                                return null;
                            }

                            int color = type.color1.ordinal();
                            int color2 = type.color2.ordinal();
                            int alt = index != 0 && index != 3 ? 1 : 0;
                            if (this == StripeBrick) {
                                alt = 0;
                            }

                            if (colors[alt] == -1) {
                                colors[alt] = color;
                                colors[1 - alt] = color2;
                            } else if (colors[alt] != color || colors[1 - alt] != color2) {
                                return null;
                            }
                        }

                        return (new BlockCeramicBrick.BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), this)).getStack(4);
                    }
                case VerticalLargeBrick:
                case VerticalBrick:
                case VerticalStripeBrick:
                    if (stacks.size() != 1) {
                        return null;
                    } else {
                        ItemStack stack = (ItemStack) stacks.get(0);
                        BlockCeramicBrick.BlockType type = new BlockCeramicBrick.BlockType(stack);
                        if (type.type == LargeBrick) {
                            type.type = VerticalLargeBrick;
                            return type.getStack(1);
                        } else if (type.type == Brick) {
                            type.type = VerticalBrick;
                            return type.getStack(1);
                        } else {
                            if (type.type == StripeBrick) {
                                type.type = VerticalStripeBrick;
                                return type.getStack(1);
                            }

                            return null;
                        }
                    }
                default:
                    return null;
            }
        }

        private boolean isMortar(ItemStack stack) {
            return stack.getItem() == Botany.misc && stack.getItemDamage() == BotanyItems.Mortar.ordinal();
        }
    }
}
