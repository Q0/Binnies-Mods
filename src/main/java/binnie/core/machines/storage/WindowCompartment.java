package binnie.core.machines.storage;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.core.machines.storage.CompartmentTab;
import binnie.core.machines.storage.ComponentCompartmentInventory;
import binnie.core.machines.storage.ControlColourSelector;
import binnie.core.machines.transfer.TransferRequest;
import binnie.craftgui.controls.ControlCheckbox;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextEdit;
import binnie.craftgui.controls.button.ControlButton;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.page.ControlPage;
import binnie.craftgui.controls.page.ControlPages;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.controls.tab.ControlTab;
import binnie.craftgui.controls.tab.ControlTabBar;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IBorder;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.events.EventTextEdit;
import binnie.craftgui.events.EventValueChanged;
import binnie.craftgui.genetics.machine.WindowMachine;
import binnie.craftgui.minecraft.Dialog;
import binnie.craftgui.minecraft.EnumColor;
import binnie.craftgui.minecraft.IWindowAffectsShiftClick;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlide;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.minecraft.control.ControlSlotArray;
import binnie.craftgui.minecraft.control.ControlTabIcon;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import binnie.craftgui.window.Panel;
import cpw.mods.fml.relauncher.Side;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class WindowCompartment extends WindowMachine implements IWindowAffectsShiftClick {
    private final Map panels = new HashMap();
    private ControlTextEdit tabName;
    private ControlItemDisplay tabIcon;
    private ControlColourSelector tabColour;
    boolean dueUpdate;
    private int currentTab = 0;

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowCompartment(player, inventory, side);
    }

    public WindowCompartment(EntityPlayer player, IInventory inventory, Side side) {
        super(320, 226, player, inventory, side);
    }

    public void initialiseClient() {
        this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
        int x = 16;
        int y = 32;
        ComponentCompartmentInventory inv = (ComponentCompartmentInventory)Machine.getMachine(this.getInventory()).getInterface(ComponentCompartmentInventory.class);
        Integer[] tabs1 = new Integer[0];
        Integer[] tabs2 = new Integer[0];
        if(inv.getTabNumber() == 4) {
            tabs1 = new Integer[]{Integer.valueOf(0), Integer.valueOf(1)};
            tabs2 = new Integer[]{Integer.valueOf(2), Integer.valueOf(3)};
        }

        if(inv.getTabNumber() == 6) {
            tabs1 = new Integer[]{Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2)};
            tabs2 = new Integer[]{Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5)};
        }

        if(inv.getTabNumber() == 8) {
            tabs1 = new Integer[]{Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)};
            tabs2 = new Integer[]{Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7)};
        }

        boolean doubleTabbed = tabs2.length > 0;
        int compartmentPageWidth = 16 + 18 * inv.getPageSize() / 5;
        int compartmentPageHeight = 106;
        int compartmentWidth = compartmentPageWidth + (doubleTabbed?48:24);
        final Control controlCompartment = new Control(this, (float)x, (float)y, (float)compartmentWidth, (float)compartmentPageHeight);
        final ControlTabBar<Integer> tab = new ControlTabBar(controlCompartment, 0.0F, 0.0F, 24.0F, (float)compartmentPageHeight, Position.Left) {
            public ControlTab createTab(final float x, final float y, final float w, final float h, final Integer value) {
                return new ControlTabIcon(this, x, y, w, h, value) {
                    public ItemStack getItemStack() {
                        return WindowCompartment.this.getTab(((Integer)this.value).intValue()).getIcon();
                    }

                    public String getName() {
                        return WindowCompartment.this.getTab(((Integer)this.value).intValue()).getName();
                    }

                    public int getOutlineColour() {
                        return WindowCompartment.this.getTab(((Integer)this.value).intValue()).getColor().getColour();
                    }

                    public boolean hasOutline() {
                        return true;
                    }
                };
            }
        };
        String[] tabHelp = new String[]{"Compartment Tab", "Tabs that divide the inventory into sections. Each one can be labelled seperately."};
        tab.addHelp(tabHelp);
        tab.setValues(Arrays.asList(tabs1));
        tab.setValue(Integer.valueOf(0));
        tab.addEventHandler((new EventValueChanged.Handler() {
            public void onEvent(EventValueChanged event) {
                NBTTagCompound nbt = new NBTTagCompound();
                int i = ((Integer)event.getValue()).intValue();
                nbt.setByte("i", (byte)i);
                Window.get(tab).sendClientAction("tab-change", nbt);
                WindowCompartment.this.currentTab = i;
            }
        }).setOrigin(EventHandler.Origin.DirectChild, tab));
        x = x + 24;
        ControlPages<Integer> compartmentPages = new ControlPages(controlCompartment, 24.0F, 0.0F, (float)compartmentPageWidth, (float)compartmentPageHeight);
        ControlPage[] page = new ControlPage[inv.getTabNumber()];

        for(int p = 0; p < inv.getTabNumber(); ++p) {
            page[p] = new ControlPage(compartmentPages, Integer.valueOf(p));
        }

        CraftGUIUtil.linkWidgets(tab, compartmentPages);
        int i = 0;

        for(int p = 0; p < inv.getTabNumber(); ++p) {
            final ControlPage thisPage = page[p];
            Panel panel = new Panel(thisPage, 0.0F, 0.0F, thisPage.w(), thisPage.h(), MinecraftGUI.PanelType.Black) {
                public void onRenderForeground() {
                    Texture iTexture = CraftGUI.Render.getTexture(CraftGUITexture.TabOutline);
                    CraftGUI.Render.colour(WindowCompartment.this.getTab(((Integer)WindowCompartment.this.panels.get(this)).intValue()).getColor().getColour());
                    CraftGUI.Render.texture(iTexture, this.getArea().inset(3));
                }
            };
            this.panels.put(panel, Integer.valueOf(p));
            int[] slotsIDs = new int[inv.getPageSize()];

            for(int k = 0; k < inv.getPageSize(); ++k) {
                slotsIDs[k] = i++;
            }

            (new ControlSlotArray(thisPage, 8, 8, inv.getPageSize() / 5, 5)).create(slotsIDs);
        }

        x = x + compartmentPageWidth;
        if(tabs2.length > 0) {
            ControlTabBar<Integer> tab2 = new ControlTabBar(controlCompartment, (float)(24 + compartmentPageWidth), 0.0F, 24.0F, (float)compartmentPageHeight, Position.Right) {
                public ControlTab createTab(final float x, final float y, final float w, final float h, final Integer value) {
                    return new ControlTabIcon(this, x, y, w, h, value) {
                        public ItemStack getItemStack() {
                            return WindowCompartment.this.getTab(((Integer)this.value).intValue()).getIcon();
                        }

                        public String getName() {
                            return WindowCompartment.this.getTab(((Integer)this.value).intValue()).getName();
                        }

                        public int getOutlineColour() {
                            return WindowCompartment.this.getTab(((Integer)this.value).intValue()).getColor().getColour();
                        }

                        public boolean hasOutline() {
                            return true;
                        }
                    };
                }
            };
            tab2.setValues(Arrays.asList(tabs2));
            tab2.setValue(Integer.valueOf(0));
            tab2.addHelp(tabHelp);
            tab2.addEventHandler((new EventValueChanged.Handler() {
                public void onEvent(EventValueChanged event) {
                    NBTTagCompound nbt = new NBTTagCompound();
                    int i = ((Integer)event.getValue()).intValue();
                    nbt.setByte("i", (byte)i);
                    Window.get(tab).sendClientAction("tab-change", nbt);
                    WindowCompartment.this.currentTab = i;
                }
            }).setOrigin(EventHandler.Origin.DirectChild, tab2));
            CraftGUIUtil.linkWidgets(tab2, compartmentPages);
            x += 24;
        }

        x = x + 16;
        this.setSize(new IPoint((float)Math.max(32 + compartmentWidth, 252), this.h()));
        controlCompartment.setPosition(new IPoint((this.w() - controlCompartment.w()) / 2.0F, controlCompartment.y()));
        new ControlPlayerInventory(this, true);
        ControlSlide slide = new ControlSlide(this, 0.0F, 134.0F, 136.0F, 92.0F, Position.Left);
        slide.setLabel("Tab Properties");
        slide.setSlide(false);
        slide.addHelp("Tab Properties");
        slide.addHelp("The label, colour and icon of the Tab can be altered here. Clicking on the icon with a held item will change it.");
        Panel tabPropertyPanel = new Panel(slide, 16.0F, 8.0F, 112.0F, 76.0F, MinecraftGUI.PanelType.Gray);
        int y2 = 4;
        new ControlText(tabPropertyPanel, new IPoint(4.0F, (float)y2), "Tab Name:");
        y2 = y2 + 12;
        this.tabName = new ControlTextEdit(tabPropertyPanel, 4.0F, (float)y2, 104.0F, 12.0F);
        this.tabName.addSelfEventHandler((new EventTextEdit.Handler() {
            public void onEvent(EventTextEdit event) {
                CompartmentTab tab = WindowCompartment.this.getCurrentTab();
                tab.setName((String)event.getValue());
                NBTTagCompound nbt = new NBTTagCompound();
                tab.writeToNBT(nbt);
                WindowCompartment.this.sendClientAction("comp-change-tab", nbt);
            }
        }).setOrigin(EventHandler.Origin.Self, this.tabName));
        y2 = y2 + 20;
        new ControlText(tabPropertyPanel, new IPoint(4.0F, (float)y2), "Tab Icon: ");
        this.tabIcon = new ControlItemDisplay(tabPropertyPanel, 58.0F, (float)(y2 - 4));
        this.tabIcon.setItemStack(new ItemStack(Items.paper));
        this.tabIcon.addAttribute(Attribute.MouseOver);
        this.tabIcon.addSelfEventHandler(new EventMouse.Down.Handler() {
            public void onEvent(EventMouse.Down event) {
                if(WindowCompartment.this.getHeldItemStack() != null) {
                    CompartmentTab tab = WindowCompartment.this.getCurrentTab();
                    ItemStack stack = WindowCompartment.this.getHeldItemStack().copy();
                    stack.stackSize = 1;
                    tab.setIcon(stack);
                    NBTTagCompound nbt = new NBTTagCompound();
                    tab.writeToNBT(nbt);
                    WindowCompartment.this.sendClientAction("comp-change-tab", nbt);
                }
            }
        });
        this.tabColour = new ControlColourSelector(tabPropertyPanel, 82.0F, (float)(y2 - 4), 16.0F, 16.0F, EnumColor.White);
        this.tabIcon.addHelp("Icon for Current Tab");
        this.tabIcon.addHelp("Click here with an item to change");
        y2 = y2 + 20;
        new ControlText(tabPropertyPanel, new IPoint(4.0F, (float)y2), "Colour: ");
        int cw = 8;
        Panel panelColour = new Panel(tabPropertyPanel, 40.0F, (float)(y2 - 4), (float)(cw * 8 + 2), (float)(cw * 2 + 1), MinecraftGUI.PanelType.Gray);

        for(int cc = 0; cc < 16; ++cc) {
            final ControlColourSelector color = new ControlColourSelector(panelColour, (float)(1 + cw * (cc % 8)), (float)(1 + cw * (cc / 8)), (float)cw, (float)cw, EnumColor.values()[cc]);
            color.addSelfEventHandler(new EventMouse.Down.Handler() {
                public void onEvent(EventMouse.Down event) {
                    CompartmentTab tab = WindowCompartment.this.getCurrentTab();
                    tab.setColor(color.getValue());
                    NBTTagCompound nbt = new NBTTagCompound();
                    tab.writeToNBT(nbt);
                    WindowCompartment.this.sendClientAction("comp-change-tab", nbt);
                }
            });
            color.addHelp("Colour Selector");
            color.addHelp("Select a colour to highlight the current tab");
        }

        y2 = y2 + 20;
        ControlButton searchButton = new ControlButton(controlCompartment, (float)(compartmentWidth - 24 - 64 - 8), (float)compartmentPageHeight, 64.0F, 16.0F, "Search") {
            protected void onMouseClick(EventMouse.Down event) {
                WindowCompartment.this.createSearchDialog();
            }

            public void onRenderBackground() {
                Object texture = this.isMouseOver()?CraftGUITexture.TabHighlighted:CraftGUITexture.Tab;
                CraftGUI.Render.texture(CraftGUI.Render.getTexture(texture).crop(Position.Bottom, 8.0F), this.getArea());
            }
        };
        searchButton.addHelp("Search Button");
        searchButton.addHelp("Clicking this will open the Search dialog. This allows you to search the inventory for specific items.");
    }

    public void createSearchDialog() {
        Dialog var10001 = new Dialog(this, 252.0F, 192.0F) {
            Control slotGrid;
            String textSearch = "";
            boolean sortByName = false;
            boolean includeItems = true;
            boolean includeBlocks = true;

            public void onClose() {
            }

            public void initialise() {
                ControlScrollableContent<IWidget> scroll = new ControlScrollableContent(this, 124.0F, 16.0F, 116.0F, 92.0F, 6.0F) {
                    public void onRenderBackground() {
                        CraftGUI.Render.colour(11184810);
                        CraftGUI.Render.texture((Object)CraftGUITexture.Outline, (IArea)this.getArea().inset(new IBorder(0.0F, 6.0F, 0.0F, 0.0F)));
                    }
                };
                this.slotGrid = new Control(scroll, 1.0F, 1.0F, 108.0F, 18.0F);
                scroll.setScrollableContent(this.slotGrid);
                new ControlPlayerInventory(this, true);
                (new ControlTextEdit(this, 16.0F, 16.0F, 100.0F, 14.0F)).addEventHandler(new EventTextEdit.Handler() {
                    public void onEvent(EventTextEdit event) {
                        textSearch = (String)event.value;
                        updateSearch();
                    }
                });
                this.includeItems = true;
                this.includeBlocks = true;
                ControlCheckbox var10001 = new ControlCheckbox(this, 16.0F, 40.0F, 100.0F, "Sort A-Z", this.sortByName) {
                    protected void onValueChanged(boolean value) {
                        sortByName = value;
                        updateSearch();
                    }
                };
                var10001 = new ControlCheckbox(this, 16.0F, 64.0F, 100.0F, "Include Items", this.includeItems) {
                    protected void onValueChanged(boolean value) {
                        includeItems = value;
                        updateSearch();
                    }
                };
                var10001 = new ControlCheckbox(this, 16.0F, 88.0F, 100.0F, "Include Blocks", this.includeBlocks) {
                    protected void onValueChanged(boolean value) {
                        includeBlocks = value;
                        updateSearch();
                    }
                };
                this.updateSearch();
            }

            private void updateSearch() {
                Map<Integer, String> slotIds = new HashMap();
                IInventory inv = WindowCompartment.this.getInventory();

                for(int i = 0; i < inv.getSizeInventory(); ++i) {
                    ItemStack stack = inv.getStackInSlot(i);
                    if(stack != null) {
                        String name = stack.getDisplayName().toLowerCase();
                        if((this.textSearch == null || name.contains(this.textSearch)) && (this.includeBlocks || Block.getBlockFromItem(stack.getItem()) == Blocks.air) && (this.includeItems || Block.getBlockFromItem(stack.getItem()) != Blocks.air)) {
                            slotIds.put(Integer.valueOf(i), name);
                        }
                    }
                }
                //TODO: FIX
                /*if(this.sortByName) {
                    LinkedList list = new LinkedList(slotIds.entrySet());
                    Collections.sort(list, new Comparator(){

                        public int compare(Object o1, Object o2) {
                            return - ((Comparable)((Map.Entry)o2).getValue()).compareTo(((Map.Entry)o1).getValue());
                        }
                    });
                    LinkedHashMap result = new LinkedHashMap();
                    for (Map.Entry entry : list) {
                        result.put(entry.getKey(), entry.getValue());
                    }
                    slotIds = result;
                }*/

                int y = 0;
                int x = 0;
                int width = 108;
                int height = 2 + 18 * (1 + (slotIds.size() - 1) / 6);
                this.slotGrid.deleteAllChildren();
                this.slotGrid.setSize(new IPoint((float)width, (float)height));
                Iterator i$ = slotIds.keySet().iterator();

                while(i$.hasNext()) {
                    int k = ((Integer)i$.next()).intValue();
                    (new ControlSlot(this.slotGrid, (float)x, (float)y)).assign(k);
                    x += 18;
                    if(x >= 108) {
                        x = 0;
                        y += 18;
                    }
                }

                while(y < 108 || x != 0) {
                    new ControlSlot(this.slotGrid, (float)x, (float)y);
                    x += 18;
                    if(x >= 108) {
                        x = 0;
                        y += 18;
                    }
                }

            }
        };
    }

    public void onUpdateClient() {
        super.onUpdateClient();
        this.updateTabs();
    }

    public void updateTabs() {
        this.tabName.setValue(this.getCurrentTab().getName());
        this.tabIcon.setItemStack(this.getCurrentTab().getIcon());
        this.tabColour.setValue(this.getCurrentTab().getColor());
    }

    public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound action) {
        super.recieveGuiNBT(side, player, name, action);
        if(name.equals("tab-change")) {
            this.currentTab = action.getByte("i");
        }

    }

    public String getTitle() {
        return "Compartment";
    }

    protected AbstractMod getMod() {
        return BinnieCore.instance;
    }

    protected String getName() {
        return "compartment";
    }

    public void alterRequest(TransferRequest request) {
        if(request.getDestination() == this.getInventory()) {
            ComponentCompartmentInventory inv = (ComponentCompartmentInventory)Machine.getMachine(this.getInventory()).getInterface(ComponentCompartmentInventory.class);
            request.setTargetSlots(inv.getSlotsForTab(this.currentTab));
        }

    }

    public CompartmentTab getTab(int i) {
        return ((ComponentCompartmentInventory)Machine.getInterface(ComponentCompartmentInventory.class, this.getInventory())).getTab(i);
    }

    public CompartmentTab getCurrentTab() {
        return this.getTab(this.currentTab);
    }
}
