package binnie.core.triggers;

import binnie.core.machines.Machine;
import binnie.core.machines.power.IProcess;
import buildcraft.api.statements.ITriggerExternal;

public class TriggerWorking {
    public static TriggerData isNotWorking(final Object inventory) {
        final IProcess process = Machine.getInterface(IProcess.class, inventory);
        boolean b = false;
        if (process != null) {
            b = (process.canWork() != null && process.canProgress() != null);
        }
        return new TriggerData((ITriggerExternal) BinnieTrigger.triggerIsNotWorking, b);
    }

    public static TriggerData isWorking(final Object inventory) {
        final IProcess process = Machine.getInterface(IProcess.class, inventory);
        boolean b = false;
        if (process != null) {
            b = (process.canWork() == null && process.canProgress() == null);
        }
        return new TriggerData((ITriggerExternal) BinnieTrigger.triggerIsWorking, b);
    }

    public static TriggerData canWork(final Object inventory) {
        final IProcess process = Machine.getInterface(IProcess.class, inventory);
        boolean b = false;
        if (process != null) {
            b = (process.canWork() == null);
        }
        return new TriggerData((ITriggerExternal) BinnieTrigger.triggerCanWork, b);
    }

    public static TriggerData cannotWork(final Object inventory) {
        final IProcess process = Machine.getInterface(IProcess.class, inventory);
        boolean b = false;
        if (process != null) {
            b = (process.canWork() != null);
        }
        return new TriggerData((ITriggerExternal) BinnieTrigger.triggerCannotWork, b);
    }
}
