package binnie.core.genetics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeeModifierLogic {
    private Map modifiers = new HashMap();
    private List booleanModifiers = new ArrayList();

    public BeeModifierLogic() {
        super();
    }

    public float getModifier(EnumBeeModifier modifier, float currentModifier) {
        if (!this.modifiers.containsKey(modifier)) {
            return 1.0F;
        } else {
            float mult = ((Float[]) this.modifiers.get(modifier))[0].floatValue();
            float max = ((Float[]) this.modifiers.get(modifier))[1].floatValue();
            return max >= 1.0F ? (max <= currentModifier ? 1.0F : Math.min(max / currentModifier, mult)) : (max >= currentModifier ? 1.0F : Math.max(max / currentModifier, mult));
        }
    }

    public boolean getModifier(EnumBeeBooleanModifier modifier) {
        return this.booleanModifiers.contains(modifier);
    }

    public void setModifier(EnumBeeBooleanModifier modifier) {
        this.booleanModifiers.add(modifier);
    }

    public void setModifier(EnumBeeModifier modifier, float mult, float max) {
        this.modifiers.put(modifier, new Float[]{Float.valueOf(mult), Float.valueOf(max)});
    }
}
