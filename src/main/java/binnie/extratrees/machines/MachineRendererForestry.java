package binnie.extratrees.machines;

import net.minecraftforge.common.util.ForgeDirection;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MachineRendererForestry {
    static Map instances = new HashMap();
    static Method renderMethod;

    public MachineRendererForestry() {
        super();
    }

    private static void loadMethod(String file, boolean waterTank, boolean productTank) {
        try {
            Class clss = Class.forName("forestry.core.render.RenderMachine");
            Object instance = clss.getConstructor(new Class[]{String.class}).newInstance(new Object[]{file});
            renderMethod = clss.getMethod("render", new Class[]{Integer.TYPE, Integer.TYPE, ForgeDirection.class, Double.TYPE, Double.TYPE, Double.TYPE});
            instances.put(file, instance);
        } catch (Exception var5) {
            ;
        }

    }

    public static void renderMachine(String name, double x, double y, double z, float var8) {
        if (!instances.containsKey(name)) {
            loadMethod(name, false, false);
        }

        try {
            renderMethod.invoke(instances.get(name), new Object[]{Integer.valueOf(0), Integer.valueOf(0), ForgeDirection.UP, Double.valueOf(x), Double.valueOf(y), Double.valueOf(z)});
        } catch (Exception var9) {
            ;
        }

    }
}
