package binnie.botany.flower;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.*;
import binnie.botany.core.BotanyCore;
import binnie.botany.genetics.EnumFlowerType;
import binnie.botany.genetics.Flower;
import binnie.core.BinnieCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;
import forestry.core.config.Config;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public abstract class ItemBotany extends Item {
    @SideOnly(Side.CLIENT)
    public int getSpriteNumber() {
        return 0;
    }

    public ItemBotany(String name) {
        super();
        this.setCreativeTab(CreativeTabBotany.instance);
        this.setUnlocalizedName(name);
        this.hasSubtypes = true;
    }

    public boolean isDamageable() {
        return false;
    }

    public boolean isRepairable() {
        return false;
    }

    public boolean getShareTag() {
        return true;
    }

    public boolean hasEffect(ItemStack itemstack) {
        if (!itemstack.hasTagCompound()) {
            return false;
        } else {
            IIndividual individual = this.getIndividual(itemstack);
            return individual != null && individual.getGenome() != null && individual.hasEffect();
        }
    }

    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean flag) {
        if (itemstack.hasTagCompound()) {
            IFlower individual = (IFlower) this.getIndividual(itemstack);
            if (individual == null) {
                list.add("This item is bugged. Destroy it!");
            } else {
                list.add("§e" + individual.getGenome().getPrimaryColor().getName() + (individual.getGenome().getPrimaryColor() == individual.getGenome().getSecondaryColor() ? "" : " and " + individual.getGenome().getSecondaryColor().getName()) + ", " + individual.getGenome().getStemColor().getName() + " Stem");
                if (individual.isAnalyzed()) {
                    if (BinnieCore.proxy.isShiftDown()) {
                        individual.addTooltip(list);
                    } else {
                        list.add("§o<Hold shift for details>");
                    }
                } else {
                    list.add("<Unknown>");
                }

            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
    }

    protected IIndividual getIndividual(ItemStack itemstack) {
        return new Flower(itemstack.getTagCompound());
    }

    private IAlleleFlowerSpecies getPrimarySpecies(ItemStack itemstack) {
        IFlower tree = BotanyCore.speciesRoot.getMember(itemstack);
        return tree == null ? (IAlleleFlowerSpecies) BotanyCore.speciesRoot.getDefaultTemplate()[EnumFlowerChromosome.SPECIES.ordinal()] : tree.getGenome().getPrimary();
    }

    public String getItemStackDisplayName(ItemStack itemstack) {
        if (!itemstack.hasTagCompound()) {
            return "Unknown";
        } else {
            IIndividual individual = this.getIndividual(itemstack);
            return individual != null && individual.getGenome() != null ? individual.getDisplayName() + this.getTag() : "Corrupted Flower";
        }
    }

    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List itemList) {
        this.addCreativeItems(itemList, true);
    }

    public void addCreativeItems(List itemList, boolean hideSecrets) {
        for (IIndividual individual : BotanyCore.speciesRoot.getIndividualTemplates()) {
            if (!hideSecrets || !individual.isSecret() || Config.isDebug) {
                itemList.add(BotanyCore.speciesRoot.getMemberStack(individual.copy(), this.getStage().ordinal()));
            }
        }

    }

    public int getColorFromItemStack(ItemStack itemstack, int renderPass) {
        IFlower flower = BotanyCore.speciesRoot.getMember(itemstack);
        return flower != null && flower.getGenome() != null ? (renderPass == 0 ? flower.getGenome().getStemColor().getColor(flower.isWilted()) : (renderPass == 1 ? flower.getGenome().getPrimaryColor().getColor(flower.isWilted()) : flower.getGenome().getSecondaryColor().getColor(flower.isWilted()))) : 16777215;
    }

    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    public int getRenderPasses(int metadata) {
        return 3;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemstack, int renderPass) {
        IFlower flower = BotanyCore.speciesRoot.getMember(itemstack);
        if (flower != null && flower.getGenome() != null && flower.getGenome().getPrimary() != null) {
            IFlowerType type = flower.getGenome().getPrimary().getType();
            return renderPass == 0 ? type.getStem(this.getStage(), flower.hasFlowered(), type.getSections() - 1) : (renderPass == 1 ? type.getPetalIcon(this.getStage(), flower.hasFlowered(), type.getSections() - 1) : type.getVariantIcon(this.getStage(), flower.hasFlowered(), type.getSections() - 1));
        } else {
            return EnumFlowerType.Allium.getBlank();
        }
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float px, float py, float pz) {
        if (this.getStage() == EnumFlowerStage.POLLEN) {
            IFlower flower = Binnie.Genetics.getFlowerRoot().getMember(itemstack);
            TileEntity target = world.getTileEntity(x, y, z);
            if (!(target instanceof IPollinatable)) {
                return false;
            } else {
                IPollinatable pollinatable = (IPollinatable) target;
                if (!pollinatable.canMateWith(flower)) {
                    return false;
                } else {
                    pollinatable.mateWith(flower);
                    if (!player.capabilities.isCreativeMode) {
                        --itemstack.stackSize;
                    }

                    return true;
                }
            }
        } else {
            Block blockFlower = Botany.flower;
            Block blockAlreadyThere = world.getBlock(x, y, z);
            if (blockAlreadyThere == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
                side = 1;
            } else if (blockAlreadyThere != Blocks.vine && blockAlreadyThere != Blocks.tallgrass && blockAlreadyThere != Blocks.deadbush && !blockAlreadyThere.isReplaceable(world, x, y, z)) {
                if (side == 0) {
                    --y;
                }

                if (side == 1) {
                    ++y;
                }

                if (side == 2) {
                    --z;
                }

                if (side == 3) {
                    ++z;
                }

                if (side == 4) {
                    --x;
                }

                if (side == 5) {
                    ++x;
                }
            }

            if (itemstack.stackSize == 0) {
                return false;
            } else if (!player.canPlayerEdit(x, y, z, side, itemstack)) {
                return false;
            } else if (y == 255 && blockFlower.getMaterial().isSolid()) {
                return false;
            } else if (world.canPlaceEntityOnSide(blockFlower, x, y, z, false, side, player, itemstack)) {
                int i1 = this.getMetadata(itemstack.getItemDamage());
                int j1 = blockFlower.onBlockPlaced(world, x, y, z, side, px, py, pz, i1);
                if (this.placeBlockAt(itemstack, player, world, x, y, z, side, px, py, pz, j1)) {
                    world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), blockFlower.stepSound.func_150496_b(), (blockFlower.stepSound.getVolume() + 1.0F) / 2.0F, blockFlower.stepSound.getPitch() * 0.8F);
                    --itemstack.stackSize;
                }

                return true;
            } else {
                return false;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean func_150936_a(World p_150936_1_, int p_150936_2_, int p_150936_3_, int p_150936_4_, int p_150936_5_, EntityPlayer p_150936_6_, ItemStack p_150936_7_) {
        Block field_150939_a = Botany.flower;
        Block block = p_150936_1_.getBlock(p_150936_2_, p_150936_3_, p_150936_4_);
        if (block == Blocks.snow_layer) {
            p_150936_5_ = 1;
        } else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(p_150936_1_, p_150936_2_, p_150936_3_, p_150936_4_)) {
            if (p_150936_5_ == 0) {
                --p_150936_3_;
            }

            if (p_150936_5_ == 1) {
                ++p_150936_3_;
            }

            if (p_150936_5_ == 2) {
                --p_150936_4_;
            }

            if (p_150936_5_ == 3) {
                ++p_150936_4_;
            }

            if (p_150936_5_ == 4) {
                --p_150936_2_;
            }

            if (p_150936_5_ == 5) {
                ++p_150936_2_;
            }
        }

        return p_150936_1_.canPlaceEntityOnSide(field_150939_a, p_150936_2_, p_150936_3_, p_150936_4_, false, p_150936_5_, (Entity) null, p_150936_7_);
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        Block field_150939_a = Botany.flower;
        if (!world.setBlock(x, y, z, field_150939_a, metadata, 3)) {
            return false;
        } else {
            if (world.getBlock(x, y, z) == field_150939_a) {
                field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
                field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
            }

            return true;
        }
    }

    public abstract EnumFlowerStage getStage();

    public abstract String getTag();
}
