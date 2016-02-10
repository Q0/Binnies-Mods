package binnie.core.circuits;

import binnie.Binnie;
import binnie.core.AbstractMod;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuitLayout;
import forestry.api.circuits.ICircuitSocketType;

public class BinnieCircuitLayout implements ICircuitLayout {
    private String uid;
    private AbstractMod mod;

    public BinnieCircuitLayout(final AbstractMod mod, final String uid) {
        this.uid = uid;
        this.mod = mod;
        ChipsetManager.circuitRegistry.registerLayout((ICircuitLayout) this);
    }

    public String getUID() {
        return "binnie.circuitLayout" + this.uid;
    }

    public String getName() {
        return Binnie.Language.localise(this.mod, "circuit.layout." + this.uid.toLowerCase());
    }

    public String getUsage() {
        return Binnie.Language.localise(this.mod, "circuit.layout." + this.uid.toLowerCase() + ".usage");
    }

    @Override
    public ICircuitSocketType getSocketType() {
        //TODO: UPD TO Forestry4
        return null;
    }
}
