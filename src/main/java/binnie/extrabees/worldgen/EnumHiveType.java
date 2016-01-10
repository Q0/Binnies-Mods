package binnie.extrabees.worldgen;

import java.util.ArrayList;
import java.util.List;

public enum EnumHiveType {
    Water,
    Rock,
    Nether,
    Marble;

    public List drops = new ArrayList();

    private EnumHiveType() {
    }
}
