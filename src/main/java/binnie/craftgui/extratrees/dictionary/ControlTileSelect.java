package binnie.craftgui.extratrees.dictionary;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.TileEntityMachine;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.controls.scroll.IControlScrollable;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignCategory;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.machines.Designer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlTileSelect extends Control implements IControlValue, IControlScrollable {
    IDesign value = EnumDesign.Blank;
    float shownHeight = 92.0F;

    protected ControlTileSelect(IWidget parent, float x, float y) {
        super(parent, x, y, 102.0F, (float) (20 * (CarpentryManager.carpentryInterface.getSortedDesigns().size() / 4) + 22));
        this.refresh("");
    }

    public float getPercentageIndex() {
        return 0.0F;
    }

    public float getPercentageShown() {
        return 0.0F;
    }

    public IDesign getValue() {
        return this.value;
    }

    public void movePercentage(float percentage) {
    }

    public void onUpdateClient() {
        super.onUpdateClient();
        TileEntityMachine tile = (TileEntityMachine) Window.get(this).getInventory();
        if (tile != null) {
            Designer.ComponentWoodworkerRecipe recipe = (Designer.ComponentWoodworkerRecipe) tile.getMachine().getComponent(Designer.ComponentWoodworkerRecipe.class);
            this.setValue(recipe.getDesign());
        }
    }

    public void refresh(String filterText) {
        this.deleteAllChildren();
        int cx = 2;
        int cy = 2;
        Map<IDesignCategory, List<IDesign>> designs = new HashMap();

        for (IDesignCategory category : CarpentryManager.carpentryInterface.getAllDesignCategories()) {
            designs.put(category, new ArrayList());

            for (IDesign tile : category.getDesigns()) {
                if (filterText == "" || tile.getName().toLowerCase().contains(filterText)) {
                    ((List) designs.get(category)).add(tile);
                }
            }

            if (((List) designs.get(category)).isEmpty()) {
                designs.remove(category);
            }
        }

        for (IDesignCategory category : designs.keySet()) {
            cx = 2;
            new ControlText(this, new IPoint((float) cx, (float) (cy + 3)), category.getName());
            cy = cy + 16;

            for (IDesign tile : (List) designs.get(category)) {
                if (cx > 90) {
                    cx = 2;
                    cy += 20;
                }

                new ControlTileSelect.ControlTile(this, (float) cx, (float) cy, tile);
                cx += 20;
            }

            cy = cy + 20;
        }

        this.setSize(new IPoint(this.getSize().x(), (float) cy));
    }

    public void setPercentageIndex(float index) {
    }

    public void setValue(IDesign value) {
        this.value = value;
    }

    public float getMovementRange() {
        return 0.0F;
    }

    public static class ControlTile extends Control implements IControlValue, ITooltip {
        IDesign value;

        protected ControlTile(IWidget parent, float x, float y, IDesign value) {
            super(parent, x, y, 18.0F, 18.0F);
            this.setValue(value);
            this.addAttribute(Attribute.MouseOver);
            this.addSelfEventHandler(new EventMouse.Down.Handler() {
                public void onEvent(EventMouse.Down event) {
                    TileEntityMachine tile = (TileEntityMachine) Window.get(ControlTile.this.getWidget()).getInventory();
                    if (tile != null) {
                        Designer.ComponentWoodworkerRecipe recipe = (Designer.ComponentWoodworkerRecipe) tile.getMachine().getComponent(Designer.ComponentWoodworkerRecipe.class);
                        NBTTagCompound nbt = new NBTTagCompound();
                        nbt.setShort("d", (short) CarpentryManager.carpentryInterface.getDesignIndex(ControlTile.this.getValue()));
                        Window.get(ControlTile.this.getWidget()).sendClientAction("design", nbt);
                    }
                }
            });
        }

        public void getTooltip(Tooltip tooltip) {
            tooltip.add(Binnie.Language.localise(BinnieCore.instance, "gui.designer.pattern", new Object[]{this.getValue().getName()}));
        }

        public IDesign getValue() {
            return this.value;
        }

        public void onRenderBackground() {
            CraftGUI.Render.texture((Object) CraftGUITexture.Slot, (IPoint) IPoint.ZERO);
        }

        public void onRenderForeground() {
            ItemStack image = ((WindowWoodworker) this.getSuperParent()).getDesignerType().getDisplayStack(this.getValue());
            CraftGUI.Render.item(new IPoint(1.0F, 1.0F), image);
            if (((IControlValue) this.getParent()).getValue() != this.getValue()) {
                if (Window.get(this).getMousedOverWidget() == this) {
                    CraftGUI.Render.gradientRect(this.getArea().inset(1), 1157627903, 1157627903);
                } else {
                    CraftGUI.Render.gradientRect(this.getArea().inset(1), -1433892728, -1433892728);
                }
            }

        }

        public void setValue(IDesign value) {
            this.value = value;
        }
    }
}
