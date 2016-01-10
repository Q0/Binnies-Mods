package binnie.core.machines.component;

import buildcraft.api.statements.IActionReceptor;

import java.util.List;

public interface IBuildcraft {
    public interface ActionProvider extends IActionReceptor {
        void getActions(List var1);
    }

    public interface TriggerProvider {
        void getTriggers(List var1);
    }
}
