package binnie.core;

import binnie.Binnie;

public abstract class ManagerBase implements IInitializable {
    public ManagerBase() {
        super();
        Binnie.Managers.add(this);
    }

    public void preInit() {
    }

    public void init() {
    }

    public void postInit() {
    }
}
