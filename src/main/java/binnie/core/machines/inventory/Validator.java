package binnie.core.machines.inventory;

import binnie.core.util.IValidator;

public abstract class Validator implements IValidator {
    public Validator() {
        super();
    }

    public abstract String getTooltip();
}
