package binnie.botany.flower;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.*;
import binnie.botany.core.BotanyCore;
import binnie.botany.gardening.BlockPlant;
import binnie.botany.gardening.Gardening;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.genetics.EnumFlowerType;
import binnie.botany.genetics.Flower;
import binnie.botany.network.MessageFlowerUpdate;
import binnie.core.BinnieCore;
import com.mojang.authlib.GameProfile;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IErrorState;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IButterflyNursery;
import forestry.lepidopterology.entities.EntityButterfly;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.EnumPlantType;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TileEntityFlower extends TileEntity implements IPollinatable, IButterflyNursery {
    IFlower flower = null;
    GameProfile owner;
    int section = 0;
    TileEntityFlower.RenderInfo renderInfo = null;
    IButterfly caterpillar;
    int matureTime = 0;

    public TileEntityFlower() {
        super();
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        this.flower = new Flower(nbttagcompound);
        this.section = nbttagcompound.getByte("section");
        if (this.flower.getAge() == 0) {
            this.flower.setFlowered(false);
        }

        this.owner = NBTUtil.func_152459_a(nbttagcompound.getCompoundTag("owner"));
        if (nbttagcompound.hasKey("CATER") && BinnieCore.isLepidopteryActive()) {
            this.matureTime = nbttagcompound.getInteger("caterTime");
            this.caterpillar = Binnie.Genetics.getButterflyRoot().getMember(nbttagcompound.getCompoundTag("cater"));
        }

        super.readFromNBT(nbttagcompound);
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        if (this.flower != null) {
            this.flower.writeToNBT(nbttagcompound);
        }

        if (this.owner != null) {
            NBTTagCompound nbt = new NBTTagCompound();
            NBTUtil.func_152460_a(nbt, this.owner);
            nbttagcompound.setTag("owner", nbt);
        }

        if (this.caterpillar != null) {
            nbttagcompound.setInteger("caterTime", this.matureTime);
            NBTTagCompound subcompound = new NBTTagCompound();
            this.caterpillar.writeToNBT(subcompound);
            nbttagcompound.setTag("cater", subcompound);
        }

        nbttagcompound.setByte("section", (byte) this.getSection());
        super.writeToNBT(nbttagcompound);
    }

    public void create(ItemStack stack, GameProfile owner) {
        this.create(BotanyCore.getFlowerRoot().getMember(stack), owner);
    }

    public void create(IFlower stack, GameProfile owner) {
        this.flower = stack;
        if (this.flower.getAge() == 0) {
            this.flower.setFlowered(false);
        }

        this.updateRender(true);
        this.owner = owner;
    }

    public EnumSet getPlantType() {
        return EnumSet.of(EnumPlantType.Plains);
    }

    public IIndividual getPollen() {
        return this.getFlower();
    }

    public boolean canMateWith(IIndividual individual) {
        return !this.isBreeding() ? false : (!(individual instanceof IFlower) ? false : (this.getFlower() == null ? false : (this.getFlower().getMate() != null ? false : (!this.getFlower().hasFlowered() ? false : !this.getFlower().isGeneticEqual(individual)))));
    }

    public void mateWith(IIndividual individual) {
        if (this.getFlower() != null && individual instanceof IFlower) {
            IAlleleFlowerSpecies primary = (IAlleleFlowerSpecies) individual.getGenome().getPrimary();
            IAlleleFlowerSpecies primary2 = this.getFlower().getGenome().getPrimary();
            if (primary == primary2 || this.worldObj.rand.nextInt(4) == 0) {
                this.getFlower().mate((IFlower) individual);
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }

        }
    }

    public IFlower getFlower() {
        if (this.getSection() > 0) {
            TileEntity tile = this.worldObj.getTileEntity(this.xCoord, this.yCoord - this.getSection(), this.zCoord);
            return tile instanceof TileEntityFlower ? ((TileEntityFlower) tile).getFlower() : null;
        } else {
            return this.flower;
        }
    }

    public boolean isBreeding() {
        Block roots = this.getWorldObj().getBlock(this.xCoord, this.yCoord - 1, this.zCoord);
        return Gardening.isSoil(roots);
    }

    public void randomUpdate(Random rand) {
        if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) != Botany.flower) {
            this.invalidate();
        } else if (this.getSection() <= 0) {
            if (this.flower != null) {
                if (this.flower.getGenome() == null) {
                    this.invalidate();
                } else if (this.isBreeding()) {
                    float light = (float) this.worldObj.getBlockLightValue(this.xCoord, this.yCoord, this.zCoord);
                    if (light < 6.0F) {
                        for (int dx = -2; dx <= 2; ++dx) {
                            for (int dy = -2; dy <= 2; ++dy) {
                                light -= !this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord) ? 0.5F : 0.0F;
                            }
                        }
                    }

                    boolean canTolerate = Gardening.canTolerate(this.getFlower(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
                    EnumSoilType soil = Gardening.getSoilType(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
                    if (rand.nextFloat() < this.getFlower().getGenome().getAgeChance()) {
                        if (this.flower.getAge() < 1) {
                            if (canTolerate && light > 6.0F) {
                                this.doFlowerAge();
                            }
                        } else {
                            this.doFlowerAge();
                        }
                    }

                    if (canTolerate && this.flower.getAge() > 1 && !this.flower.isWilted() && light > 6.0F) {
                        this.flower.setFlowered(true);
                    }

                    if (!canTolerate && this.flower.isWilted() && rand.nextInt(2 + Math.max(this.flower.getAge(), 2)) == 0) {
                        this.kill();
                    } else if (light < 2.0F && this.flower.isWilted()) {
                        this.kill();
                    } else {
                        if (canTolerate && light >= 1.0F) {
                            this.flower.setWilted(false);
                        } else {
                            this.flower.setWilted(true);
                        }

                        float CHANCE_DISPERSAL = 0.8F;
                        CHANCE_DISPERSAL = CHANCE_DISPERSAL + 0.2F * (float) this.flower.getGenome().getFertility();
                        CHANCE_DISPERSAL = CHANCE_DISPERSAL * (1.0F + (float) soil.ordinal() * 0.5F);
                        float CHANCE_POLLINATE = 1.0F;
                        CHANCE_POLLINATE = CHANCE_POLLINATE + 0.25F * (float) this.flower.getGenome().getFertility();
                        CHANCE_POLLINATE = CHANCE_POLLINATE * (1.0F + (float) soil.ordinal() * 0.5F);
                        float CHANCE_SELFPOLLINATE = 0.2F * CHANCE_POLLINATE;
                        if (this.worldObj.rand.nextFloat() < CHANCE_DISPERSAL && this.flower.hasFlowered() && !this.flower.isWilted()) {
                            IFlowerGenome mate = this.flower.getMate();
                            if (mate != null) {
                                boolean dispersed = false;

                                for (int a = 0; a < 5 && !dispersed; ++a) {
                                    int dx = 0;

                                    int dz;
                                    for (dz = 0; dx == 0 && dz == 0; dz = rand.nextInt(3) - 1) {
                                        dx = rand.nextInt(3) - 1;
                                    }

                                    Block b = this.getWorldObj().getBlock(this.xCoord + dx, this.yCoord, this.zCoord + dz);
                                    Block b2 = this.getWorldObj().getBlock(this.xCoord + dx, this.yCoord - 1, this.zCoord + dz);
                                    if (b == Blocks.air && Gardening.isSoil(b2)) {
                                        IFlower offspring = this.flower.getOffspring(this.getWorldObj());
                                        if (offspring != null) {
                                            Gardening.plant(this.getWorldObj(), this.xCoord + dx, this.yCoord, this.zCoord + dz, offspring, this.getOwner());
                                            this.flower.removeMate();
                                            dispersed = true;
                                        }
                                    }
                                }
                            }
                        }

                        if (this.worldObj.rand.nextFloat() < CHANCE_POLLINATE && this.flower.hasFlowered() && !this.flower.isWilted()) {
                            for (int a = 0; a < 4; ++a) {
                                int dx = 0;

                                int dz;
                                for (dz = 0; dx == 0 && dz == 0; dz = rand.nextInt(5) - 2) {
                                    dx = rand.nextInt(5) - 2;
                                }

                                TileEntity tile = this.getWorldObj().getTileEntity(this.xCoord + dx, this.yCoord, this.zCoord + dz);
                                if (tile instanceof IPollinatable && ((IPollinatable) tile).canMateWith(this.getFlower())) {
                                    ((IPollinatable) tile).mateWith(this.getFlower());
                                }
                            }
                        }

                        if (this.worldObj.rand.nextFloat() < CHANCE_SELFPOLLINATE && this.flower.hasFlowered() && this.flower.getMate() == null) {
                            this.mateWith(this.getFlower());
                        }

                        this.spawnButterflies();
                        this.matureCaterpillar();
                        this.checkIfDead(false);
                        this.updateRender(true);
                    }
                }
            }
        }
    }

    private void doFlowerAge() {
        this.getFlower().age();
        if (this.getFlower().getAge() == 1) {
            Gardening.onGrowFromSeed(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            if (this.getOwner() != null && this.getFlower() != null) {
                BotanyCore.getFlowerRoot().getBreedingTracker(this.getWorldObj(), this.getOwner()).registerBirth(this.getFlower());
            }
        }

    }

    public Packet getDescriptionPacket() {
        if (this.renderInfo == null && this.getFlower() != null && this.getFlower().getGenome() != null) {
            this.renderInfo = new TileEntityFlower.RenderInfo(this.getFlower(), this);
        }

        return this.renderInfo != null ? Botany.instance.getNetworkWrapper().getPacketFrom((new MessageFlowerUpdate(this.xCoord, this.yCoord, this.zCoord, this.renderInfo)).GetMessage()) : null;
    }

    public void updateRender(boolean update) {
        if (update && this.getFlower() != null && this.getFlower().getGenome() != null) {
            TileEntityFlower.RenderInfo newInfo = new TileEntityFlower.RenderInfo(this.getFlower(), this);
            if (this.renderInfo == null || !newInfo.equals(this.renderInfo)) {
                this.setRender(newInfo);
            }
        }

        TileEntity above = this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord);
        if (above instanceof TileEntityFlower) {
            ((TileEntityFlower) above).updateRender(true);
        }

    }

    public int getSection() {
        return this.section;
    }

    public void setSection(int i) {
        this.section = i;
        if (BinnieCore.proxy.isSimulating(this.worldObj)) {
            this.updateRender(true);
        }

    }

    public ItemStack getItemStack() {
        return this.flower == null ? null : Binnie.Genetics.getFlowerRoot().getMemberStack(this.getFlower(), this.flower.getAge() == 0 ? EnumFlowerStage.SEED.ordinal() : EnumFlowerStage.FLOWER.ordinal());
    }

    private TileEntityFlower getRoot() {
        if (this.getSection() == 0) {
            return null;
        } else {
            TileEntity tile = this.worldObj.getTileEntity(this.xCoord, this.yCoord - this.getSection(), this.zCoord);
            return tile instanceof TileEntityFlower ? (TileEntityFlower) tile : null;
        }
    }

    public void onShear() {
        if (this.getRoot() != null) {
            this.getRoot().onShear();
        }

        if (this.getFlower() != null && this.getFlower().getAge() > 1) {
            Random rand = new Random();
            IFlower cutting = (IFlower) this.getFlower().copy();
            cutting.setAge(0);
            ItemStack cuttingStack = BotanyCore.getFlowerRoot().getMemberStack(cutting, EnumFlowerStage.SEED.ordinal());
            float f = 0.7F;
            double d = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d2 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(this.worldObj, (double) this.xCoord + d, (double) this.yCoord + d1, (double) this.zCoord + d2, cuttingStack);
            entityitem.delayBeforeCanPickup = 10;
            this.worldObj.spawnEntityInWorld(entityitem);
            int maxAge = this.getFlower().getMaxAge();

            for (int i = 0; i < maxAge; ++i) {
                if (rand.nextBoolean()) {
                    this.getFlower().age();
                    if (this.checkIfDead(true)) {
                        return;
                    }
                }
            }
        }

    }

    public boolean checkIfDead(boolean wasCut) {
        if (this.getSection() != 0) {
            return this.getRoot().checkIfDead(wasCut);
        } else {
            EnumSoilType soil = Gardening.getSoilType(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            int maxAge = (int) ((float) this.flower.getMaxAge() * (1.0F + (float) soil.ordinal() * 0.25F));
            if (this.flower.getAge() <= maxAge) {
                return false;
            } else {
                if (!wasCut && this.flower.getMate() != null) {
                    this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
                    IFlower offspring = this.flower.getOffspring(this.getWorldObj());
                    TileEntity above = this.worldObj.getTileEntity(this.xCoord, this.yCoord + 1, this.zCoord);
                    if (above instanceof TileEntityFlower) {
                        this.worldObj.setBlockToAir(this.xCoord, this.yCoord + 1, this.zCoord);
                    }

                    Gardening.plant(this.worldObj, this.xCoord, this.yCoord, this.zCoord, offspring, this.getOwner());
                } else {
                    this.kill();
                }

                return true;
            }
        }
    }

    public void kill() {
        if (this.flower.getAge() > 0) {
            this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Botany.plant, BlockPlant.Type.DeadFlower.ordinal(), 2);
        } else {
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
        }

        for (int i = 1; this.worldObj.getTileEntity(this.xCoord, this.yCoord + i, this.zCoord) instanceof TileEntityFlower; ++i) {
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord + i, this.zCoord);
        }

    }

    public boolean onBonemeal() {
        if (this.getFlower() == null) {
            return false;
        } else if (!this.isBreeding()) {
            return false;
        } else if (this.getFlower().isWilted()) {
            return false;
        } else {
            this.doFlowerAge();
            if (this.getFlower().getAge() > 1 && !this.getFlower().hasFlowered()) {
                this.getFlower().setFlowered(true);
            }

            this.checkIfDead(false);
            this.updateRender(true);
            return true;
        }
    }

    public GameProfile getOwner() {
        return this.owner;
    }

    public void setOwner(GameProfile ownerName) {
        this.owner = ownerName;
    }

    public void spawnButterflies() {
        if (BinnieCore.isLepidopteryActive()) {
            int x = this.xCoord;
            int y = this.yCoord;
            int z = this.zCoord;
            World world = this.worldObj;
            if (this.getCaterpillar() == null) {
                if (world.rand.nextFloat() < this.getFlower().getGenome().getSappiness()) {
                    if (this.worldObj.rand.nextFloat() < 0.2F) {
                        IButterfly spawn = (IButterfly) Binnie.Genetics.getButterflyRoot().getIndividualTemplates().get(this.worldObj.rand.nextInt(Binnie.Genetics.getButterflyRoot().getIndividualTemplates().size()));
                        if (this.worldObj.rand.nextFloat() < spawn.getGenome().getPrimary().getRarity() * 0.5F) {
                            if (this.worldObj.countEntities(EntityButterfly.class) <= 100) {
                                if (spawn.canSpawn(this.worldObj, (double) x, (double) y, (double) z)) {
                                    if (world.isAirBlock(x, y + 1, z)) {
                                        this.attemptButterflySpawn(world, spawn, (double) x, (double) (y + 1), (double) z);
                                    } else if (world.isAirBlock(x - 1, y, z)) {
                                        this.attemptButterflySpawn(world, spawn, (double) (x - 1), (double) y, (double) z);
                                    } else if (world.isAirBlock(x + 1, y, z)) {
                                        this.attemptButterflySpawn(world, spawn, (double) (x + 1), (double) y, (double) z);
                                    } else if (world.isAirBlock(x, y, z - 1)) {
                                        this.attemptButterflySpawn(world, spawn, (double) x, (double) y, (double) (z - 1));
                                    } else if (world.isAirBlock(x, y, z + 1)) {
                                        this.attemptButterflySpawn(world, spawn, (double) x, (double) y, (double) (z + 1));
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void attemptButterflySpawn(World world, IButterfly butterfly, double x, double y, double z) {
        if (BinnieCore.isLepidopteryActive()) {
            Binnie.Genetics.getButterflyRoot().spawnButterflyInWorld(world, butterfly.copy(), x, y + 0.10000000149011612D, z);
        }

    }

    public GameProfile getOwnerName() {
        return this.getOwner();
    }

    public World getWorld() {
        return this.getWorldObj();
    }

    public int getXCoord() {
        return this.xCoord;
    }

    public int getYCoord() {
        return this.yCoord;
    }

    public int getZCoord() {
        return this.zCoord;
    }

    public int getBiomeId() {
        return this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord).biomeID;
    }

    public EnumTemperature getTemperature() {
        return EnumTemperature.getFromValue(this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord).temperature);
    }

    public EnumHumidity getHumidity() {
        return EnumHumidity.getFromValue(this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord).rainfall);
    }

    public void setErrorState(int state) {
    }

    public int getErrorOrdinal() {
        return 0;
    }

    public boolean addProduct(ItemStack product, boolean all) {
        return false;
    }

    public IButterfly getCaterpillar() {
        return this.caterpillar;
    }

    public IIndividual getNanny() {
        return this.getFlower();
    }

    public void setCaterpillar(IButterfly butterfly) {
        this.caterpillar = butterfly;
        this.matureTime = 0;
    }

    public boolean canNurse(IButterfly butterfly) {
        return this.getFlower() != null && !this.getFlower().isWilted() && this.getFlower().getAge() > 1;
    }

    private void matureCaterpillar() {
        if (this.getCaterpillar() != null) {
            ++this.matureTime;
            if ((float) this.matureTime >= (float) this.caterpillar.getGenome().getLifespan() / (float) (this.caterpillar.getGenome().getFertility() * 2) && this.caterpillar.canTakeFlight(this.worldObj, (double) this.xCoord, (double) this.yCoord, (double) this.zCoord)) {
                if (this.worldObj.isAirBlock(this.xCoord, this.yCoord + 1, this.zCoord)) {
                    this.attemptButterflySpawn(this.worldObj, this.caterpillar, (double) this.xCoord, (double) (this.yCoord + 1), (double) this.zCoord);
                } else if (this.worldObj.isAirBlock(this.xCoord - 1, this.yCoord, this.zCoord)) {
                    this.attemptButterflySpawn(this.worldObj, this.caterpillar, (double) (this.xCoord - 1), (double) this.yCoord, (double) this.zCoord);
                } else if (this.worldObj.isAirBlock(this.xCoord + 1, this.yCoord, this.zCoord)) {
                    this.attemptButterflySpawn(this.worldObj, this.caterpillar, (double) (this.xCoord + 1), (double) this.yCoord, (double) this.zCoord);
                } else if (this.worldObj.isAirBlock(this.xCoord, this.yCoord, this.zCoord - 1)) {
                    this.attemptButterflySpawn(this.worldObj, this.caterpillar, (double) this.xCoord, (double) this.yCoord, (double) (this.zCoord - 1));
                } else if (this.worldObj.isAirBlock(this.xCoord, this.yCoord, this.zCoord + 1)) {
                    this.attemptButterflySpawn(this.worldObj, this.caterpillar, (double) this.xCoord, (double) this.yCoord, (double) (this.zCoord + 1));
                }

                this.setCaterpillar((IButterfly) null);
            }

        }
    }

    public void setRender(TileEntityFlower.RenderInfo render) {
        this.renderInfo = render;
        this.section = this.renderInfo.section;
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    public int getAge() {
        return this.renderInfo == null ? 1 : this.renderInfo.age;
    }

    public int getRenderSection() {
        return this.renderInfo == null ? 1 : this.renderInfo.section;
    }

    public boolean isWilted() {
        return this.renderInfo == null ? false : this.renderInfo.wilted;
    }

    public boolean isFlowered() {
        return this.renderInfo == null ? true : this.renderInfo.flowered;
    }

    public int getPrimaryColour() {
        return this.renderInfo == null ? EnumFlowerColor.Red.getColor(false) : this.renderInfo.primary.getColor(this.isWilted());
    }

    public int getSecondaryColour() {
        return this.renderInfo == null ? EnumFlowerColor.Red.getColor(false) : this.renderInfo.secondary.getColor(this.isWilted());
    }

    public int getStemColour() {
        return this.renderInfo == null ? EnumFlowerColor.Green.getColor(false) : this.renderInfo.stem.getColor(this.isWilted());
    }

    public IFlowerType getType() {
        return (IFlowerType) (this.renderInfo == null ? EnumFlowerType.Poppy : this.renderInfo.type);
    }

    public BiomeGenBase getBiome() {
        return this.getWorld().getBiomeGenForCoords(this.xCoord, this.zCoord);
    }

    public void setErrorState(IErrorState state) {
    }

    public IErrorState getErrorState() {
        return null;
    }

    public boolean setErrorCondition(boolean condition, IErrorState errorState) {
        return false;
    }

    public Set getErrorStates() {
        return new HashSet();
    }

    public static class RenderInfo {
        public IFlowerColour primary;
        public IFlowerColour secondary;
        public IFlowerColour stem;
        public IFlowerType type;
        public byte age;
        public boolean wilted;
        public boolean flowered;
        public byte section;

        public boolean equals(Object obj) {
            if (!(obj instanceof TileEntityFlower.RenderInfo)) {
                return super.equals(obj);
            } else {
                TileEntityFlower.RenderInfo o = (TileEntityFlower.RenderInfo) obj;
                return o.age == this.age && o.wilted == this.wilted && o.flowered == this.flowered && o.primary == this.primary && o.secondary == this.secondary && o.stem == this.stem && o.type == this.type;
            }
        }

        public RenderInfo() {
            super();
        }

        public RenderInfo(IFlower flower, TileEntityFlower tile) {
            super();
            this.section = (byte) tile.getSection();
            this.primary = flower.getGenome().getPrimaryColor();
            this.secondary = flower.getGenome().getSecondaryColor();
            this.stem = flower.getGenome().getStemColor();
            this.age = (byte) flower.getAge();
            this.wilted = flower.isWilted();
            this.flowered = flower.hasFlowered();
            this.type = flower.getGenome().getType();
        }
    }
}
