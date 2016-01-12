package binnie.core.triggers;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieIcon;
import buildcraft.api.statements.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

class BinnieAction implements IActionExternal {
    private static int incrementalID;
    public static BinnieAction actionPauseProcess;
    public static BinnieAction actionCancelTask;
    private String desc;
    private BinnieIcon icon;
    private String tag;
    private int id;

    BinnieAction(final String desc, final String tag, final String iconFile) {
        this(desc, tag, BinnieCore.instance, iconFile);
    }

    private BinnieAction(final String desc, final String tag, final AbstractMod mod, final String iconFile) {
        this.id = 0;
        this.id = BinnieAction.incrementalID++;
        this.tag = tag;
        StatementManager.registerStatement((IStatement) this);
        this.icon = Binnie.Resource.getItemIcon(mod, iconFile);
        this.desc = desc;
    }

    public String getDescription() {
        return this.desc;
    }

    public String getUniqueTag() {
        return this.tag;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon() {
        return this.icon.getIcon();
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconRegister) {
        this.icon.registerIcon(iconRegister);
    }

    public int maxParameters() {
        return 0;
    }

    public int minParameters() {
        return 0;
    }

    public IStatementParameter createParameter(final int index) {
        return null;
    }

    public IStatement rotateLeft() {
        return (IStatement) this;
    }

    public void actionActivate(final TileEntity target, final ForgeDirection side, final IStatementContainer source, final IStatementParameter[] parameters) {
    }

    static {
        BinnieAction.incrementalID = 800;
    }
}
