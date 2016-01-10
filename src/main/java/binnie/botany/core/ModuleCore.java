package binnie.botany.core;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.core.IInitializable;

public class ModuleCore implements IInitializable {
    public ModuleCore() {
        super();
    }

    public void preInit() {
        for (EnumAcidity pH : EnumAcidity.values()) {
            Binnie.Language.addObjectName(pH, Binnie.Language.unlocalised(Botany.instance, "ph." + pH.getID()));
        }

        for (EnumMoisture pH : EnumMoisture.values()) {
            Binnie.Language.addObjectName(pH, Binnie.Language.unlocalised(Botany.instance, "moisture." + pH.getID()));
        }

        for (EnumSoilType pH : EnumSoilType.values()) {
            Binnie.Language.addObjectName(pH, Binnie.Language.unlocalised(Botany.instance, "soil." + pH.getID()));
        }

    }

    public void init() {
    }

    public void postInit() {
    }
}
