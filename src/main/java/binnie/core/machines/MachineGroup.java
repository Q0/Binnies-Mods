package binnie.core.machines;

import binnie.Binnie;
import binnie.core.AbstractMod;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class MachineGroup {
    private AbstractMod mod;
    private String blockName;
    private String uid;
    private Map packages = new LinkedHashMap();
    private Map packagesID = new LinkedHashMap();
    private BlockMachine block;
    public boolean customRenderer = true;
    private boolean renderedTileEntity = true;

    public MachineGroup(AbstractMod mod, String uid, String blockName, IMachineType[] types) {
        super();
        this.mod = mod;
        this.uid = uid;
        this.blockName = blockName;

        for (IMachineType type : types) {
            if (type.getPackageClass() != null && type.isActive()) {
                try {
                    MachinePackage pack = (MachinePackage) type.getPackageClass().newInstance();
                    pack.assignMetadata(type.ordinal());
                    pack.setActive(type.isActive());
                    this.addPackage(pack);
                } catch (Exception var10) {
                    throw new RuntimeException("Failed to create machine package " + type.toString(), var10);
                }
            }
        }

        Binnie.Machine.registerMachineGroup(this);
        this.block = new BlockMachine(this, blockName);
        if (this.block != null) {
            GameRegistry.registerBlock(this.block, ItemMachine.class, blockName);

            for (MachinePackage pack : this.getPackages()) {
                pack.register();
            }
        }

    }

    private void addPackage(MachinePackage pack) {
        this.packages.put(pack.getUID(), pack);
        this.packagesID.put(pack.getMetadata(), pack);
        pack.setGroup(this);
    }

    public Collection getPackages() {
        return this.packages.values();
    }

    public BlockMachine getBlock() {
        return this.block;
    }

    public MachinePackage getPackage(int metadata) {
        return (MachinePackage) this.packagesID.get(Integer.valueOf(metadata));
    }

    public MachinePackage getPackage(String name) {
        return (MachinePackage) this.packages.get(name);
    }

    public String getUID() {
        return this.mod.getModID() + "." + this.uid;
    }

    public String getShortUID() {
        return this.uid;
    }

    boolean isTileEntityRenderered() {
        return this.renderedTileEntity;
    }

    public void renderAsBlock() {
        this.renderedTileEntity = false;
    }

    public void setCreativeTab(CreativeTabs tab) {
        this.block.setCreativeTab(tab);
    }

    public AbstractMod getMod() {
        return this.mod;
    }
}
