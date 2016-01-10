package binnie.genetics.machine;

import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.core.resource.BinnieResource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MachineRendererLab {
    public static MachineRendererLab instance = new MachineRendererLab();
    private final EntityItem dummyEntityItem = new EntityItem((World) null);
    private final EntityItem[] itemSides = new EntityItem[]{new EntityItem((World) null), new EntityItem((World) null), new EntityItem((World) null), new EntityItem((World) null)};
    private final RenderItem customRenderItem = new RenderItem() {
        public boolean shouldBob() {
            return false;
        }

        public boolean shouldSpreadItems() {
            return false;
        }
    };
    private long lastTick;
    private ModelMachine model = new ModelMachine();

    public MachineRendererLab() {
        super();
        this.customRenderItem.setRenderManager(RenderManager.instance);
    }

    public void renderMachine(Machine machine, int colour, BinnieResource texture, double x, double y, double z, float var8) {
        GL11.glPushMatrix();
        int i1 = 0;
        int ix = machine.getTileEntity().xCoord;
        int iy = machine.getTileEntity().yCoord;
        int iz = machine.getTileEntity().zCoord;
        if (machine.getTileEntity() != null) {
            i1 = ix * iy * iz + ix * iy - ix * iz + iy * iz - ix + iy - iz;
        }

        label77:
        {
            float phase = (float) Math.max(0.0D, Math.sin((double) (System.currentTimeMillis() + (long) i1) * 0.003D));
            GL11.glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
            GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            BinnieCore.proxy.bindTexture(texture);
            GL11.glPushMatrix();
            this.model.render((float) x, (float) y, (float) z, 0.0625F, 0.0625F, 0.0625F);
            GL11.glPopMatrix();
            World world = machine.getWorld();
            LaboratoryMachine.ComponentGUIHolder holder = (LaboratoryMachine.ComponentGUIHolder) Machine.getInterface(LaboratoryMachine.ComponentGUIHolder.class, machine);
            if (world != null && holder != null && holder.getStack() != null) {
                BinnieCore.proxy.getMinecraftInstance();
                if (Minecraft.isFancyGraphicsEnabled()) {
                    ItemStack stack = holder.getStack();
                    this.dummyEntityItem.worldObj = world;
                    this.dummyEntityItem.setEntityItemStack(stack);
                    if (world.getTotalWorldTime() != this.lastTick) {
                        this.lastTick = world.getTotalWorldTime();
                        this.dummyEntityItem.onUpdate();
                    }

                    this.dummyEntityItem.age = 0;
                    this.dummyEntityItem.hoverStart = 0.0F;
                    GL11.glPushMatrix();
                    EntityPlayer player = BinnieCore.proxy.getPlayer();
                    double dx = (double) ix + 0.5D - player.lastTickPosX;
                    double dz = (double) iz + 0.5D - player.lastTickPosZ;
                    double t = Math.atan2(dz, dx) * 180.0D / 3.1415D;
                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glTranslatef(0.0F, 0.0F, -0.55F);
                    GL11.glRotatef(90.0F + (float) (-t), 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                    GL11.glScalef(1.2F, 1.2F, 1.2F);
                    GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glTranslatef(0.0F, 0.1F, 0.1F);
                    this.customRenderItem.doRender(this.dummyEntityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                    GL11.glPopMatrix();
                    int rot = 0;
                    EntityItem[] arr$ = this.itemSides;
                    int len$ = arr$.length;
                    int i$ = 0;

                    while (true) {
                        if (i$ >= len$) {
                            break label77;
                        }

                        EntityItem item = arr$[i$];
                        GL11.glPushMatrix();
                        item.worldObj = world;
                        item.setEntityItemStack(stack);
                        item.age = 0;
                        item.hoverStart = 0.0F;
                        GL11.glRotatef((float) rot, 0.0F, 1.0F, 0.0F);
                        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
                        GL11.glTranslated(0.0D, -1.13D, 0.4D);
                        GL11.glScalef(0.8F, 0.8F, 0.8F);
                        this.customRenderItem.doRender(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                        rot += 90;
                        GL11.glPopMatrix();
                        ++i$;
                    }
                }
            }

            this.dummyEntityItem.setEntityItemStack((ItemStack) null);

            for (EntityItem item : this.itemSides) {
                item.setEntityItemStack((ItemStack) null);
            }
        }

        GL11.glPopMatrix();
    }
}
