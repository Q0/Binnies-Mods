package binnie.craftgui.genetics.machine;

import binnie.core.machines.Machine;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlProgressBase;
import binnie.craftgui.window.Panel;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class ControlSequencerProgress extends ControlProgressBase {
    ControlText textControl;

    public ControlSequencerProgress(IWidget parent, int x, int y) {
        super(parent, (float) x, (float) y, 100.0F, 52.0F);
        Panel panel = new Panel(this, 0.0F, 0.0F, 100.0F, 52.0F, MinecraftGUI.PanelType.Gray);
        this.textControl = new ControlText(panel, new IArea(4.0F, 4.0F, 92.0F, 44.0F), "", TextJustification.MiddleCenter);
    }

    public void onUpdateClient() {
        super.onUpdateClient();
        ItemStack stack = Machine.getMachine(Window.get(this).getInventory()).getMachineUtil().getStack(5);
        if (stack == null) {
            this.textControl.setValue("");
        } else {
            Random rand = new Random((long) stack.getDisplayName().length());
            String text = "";
            String[] codes = new String[]{"A", "T", "G", "C"};
            String[] colors = new String[]{"a", "d", "b", "c"};

            for (int i = 0; i < 65; ++i) {
                int k = rand.nextInt(4);
                String code = codes[k];
                if (rand.nextFloat() < this.progress) {
                    String col = "§" + colors[k];
                    text = text + "§r" + col + "§l" + code;
                } else {
                    text = text + "§r§7§k§l" + code;
                }
            }

            this.textControl.setValue(text);
        }

    }
}
