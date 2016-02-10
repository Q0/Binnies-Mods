package binnie.extratrees.machines;

import net.minecraftforge.common.util.ForgeDirection;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MachineRendererForestry {
    static Map<String, Object> instances;
    static Method renderMethod;

    static {
        MachineRendererForestry.instances = new HashMap<String, Object>();
    }

    private static void loadMethod(final String file, final boolean waterTank, final boolean productTank) {
        try {
            final Class clss = Class.forName("forestry.core.render.RenderMachine");
            final Object instance = clss.getConstructor(String.class).newInstance(file);
            MachineRendererForestry.renderMethod = clss.getMethod("render", Integer.TYPE, Integer.TYPE, ForgeDirection.class, Double.TYPE, Double.TYPE, Double.TYPE);
            MachineRendererForestry.instances.put(file, instance);
        } catch (Exception ex) {
        }
    }

    public static void renderMachine(final String name, final double x, final double y, final double z, final float var8) {
        if (!MachineRendererForestry.instances.containsKey(name)) {
            loadMethod(name, false, false);
        }
        try {
            MachineRendererForestry.renderMethod.invoke(MachineRendererForestry.instances.get(name), 0, 0, ForgeDirection.UP, x, y, z);
        } catch (Exception ex) {
        }
    }
}
