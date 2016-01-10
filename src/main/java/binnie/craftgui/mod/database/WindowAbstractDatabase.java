package binnie.craftgui.mod.database;

import binnie.core.BinnieCore;
import binnie.core.genetics.BreedingSystem;
import binnie.core.util.IValidator;
import binnie.craftgui.controls.ControlTextEdit;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.controls.listbox.ControlTextOption;
import binnie.craftgui.controls.page.ControlPage;
import binnie.craftgui.controls.page.ControlPages;
import binnie.craftgui.controls.tab.ControlTab;
import binnie.craftgui.controls.tab.ControlTabBar;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventTextEdit;
import binnie.craftgui.events.EventValueChanged;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlHelp;
import binnie.craftgui.mod.database.ControlBranchBox;
import binnie.craftgui.mod.database.ControlSpeciesBox;
import binnie.craftgui.mod.database.DatabaseTab;
import binnie.craftgui.mod.database.IDatabaseMode;
import binnie.craftgui.mod.database.PageAbstract;
import binnie.craftgui.window.Panel;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IClassification;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public abstract class WindowAbstractDatabase extends Window {
   private float selectionBoxWidth;
   private final float infoBoxWidth;
   private final float infoBoxHeight;
   private final float infoTabWidth;
   private final float modeTabWidth;
   private final float searchBoxHeight;
   private Map modes;
   boolean isNEI;
   private BreedingSystem system;
   private Panel panelInformation;
   private Panel panelSearch;
   private ControlPages modePages;
   private IAlleleSpecies gotoSpecies;

   public void changeMode(IDatabaseMode mode) {
      this.modePages.setValue(mode);
   }

   public WindowAbstractDatabase(EntityPlayer player, Side side, boolean nei, BreedingSystem system, float wid) {
      super(100.0F, 192.0F, player, (IInventory)null, side);
      this.selectionBoxWidth = 95.0F;
      this.infoBoxWidth = 144.0F;
      this.infoBoxHeight = 176.0F;
      this.infoTabWidth = 16.0F;
      this.modeTabWidth = 22.0F;
      this.searchBoxHeight = 16.0F;
      this.modes = new HashMap();
      this.panelInformation = null;
      this.panelSearch = null;
      this.modePages = null;
      this.gotoSpecies = null;
      this.isNEI = nei;
      this.system = system;
      this.selectionBoxWidth = wid;
   }

   public ControlPages getInfoPages(IDatabaseMode mode) {
      return ((WindowAbstractDatabase.ModeWidgets)this.modes.get(mode)).infoPages;
   }

   public boolean isNEI() {
      return this.isNEI;
   }

   public BreedingSystem getBreedingSystem() {
      return this.system;
   }

   public WindowAbstractDatabase(EntityPlayer player, Side side, boolean nei, BreedingSystem system) {
      this(player, side, nei, system, 95.0F);
   }

   protected WindowAbstractDatabase.ModeWidgets createMode(IDatabaseMode mode, WindowAbstractDatabase.ModeWidgets widgets) {
      this.modes.put(mode, widgets);
      return widgets;
   }

   public void initialiseClient() {
      this.setSize(new IPoint(176.0F + this.selectionBoxWidth + 22.0F + 8.0F, 208.0F));
      this.addEventHandler(new EventValueChanged.Handler() {
         public void onEvent(EventValueChanged event) {
            if(event.getOrigin().getParent() instanceof ControlPage && !(event.getValue() instanceof DatabaseTab)) {
               ControlPage parent = (ControlPage)event.getOrigin().getParent();
               if(parent.getValue() instanceof IDatabaseMode) {
                  for(IWidget widget : parent.getWidgets()) {
                     if(widget instanceof ControlPages) {
                        if(event.getValue() == null) {
                           widget.hide();
                        } else {
                           widget.show();

                           for(IWidget widget2 : widget.getWidgets()) {
                              if(widget2 instanceof PageAbstract) {
                                 ((PageAbstract)widget2).onValueChanged(event.getValue());
                              }
                           }
                        }
                     }
                  }
               }
            }

         }
      });
      this.addEventHandler((new EventTextEdit.Handler() {
         public void onEvent(final EventTextEdit event) {
            for(WindowAbstractDatabase.ModeWidgets widgets : WindowAbstractDatabase.this.modes.values()) {
               widgets.listBox.setValidator(new IValidator() {
                  public boolean isValid(IWidget object) {
                     return event.getValue() == "" || ((ControlTextOption)object).getText().toLowerCase().contains(((String)event.getValue()).toLowerCase());
                  }
               });
            }

         }
      }).setOrigin(EventHandler.Origin.DirectChild, this));
      new ControlHelp(this, 4.0F, 4.0F);
      this.panelInformation = new Panel(this, 24.0F, 24.0F, 144.0F, 176.0F, MinecraftGUI.PanelType.Black);
      this.panelInformation.setColour(860416);
      this.panelSearch = new Panel(this, 176.0F, 24.0F, this.selectionBoxWidth, 160.0F, MinecraftGUI.PanelType.Black);
      this.panelSearch.setColour(860416);
      this.modePages = new ControlPages(this, 0.0F, 0.0F, this.getSize().x(), this.getSize().y());
      new ControlTextEdit(this, 176.0F, 184.0F, this.selectionBoxWidth, 16.0F);
      this.createMode(WindowAbstractDatabase.Mode.Species, new WindowAbstractDatabase.ModeWidgets(WindowAbstractDatabase.Mode.Species, this) {
         public void createListBox(IArea area) {
            GameProfile playerName = WindowAbstractDatabase.this.getUsername();
            Collection<IAlleleSpecies> speciesList = !this.database.isNEI?this.database.system.getDiscoveredSpecies(this.database.getWorld(), playerName):this.database.system.getAllSpecies();
            this.listBox = new ControlSpeciesBox(this.modePage, area.x(), area.y(), area.w(), area.h());
            this.listBox.setOptions(speciesList);
         }
      });
      this.createMode(WindowAbstractDatabase.Mode.Branches, new WindowAbstractDatabase.ModeWidgets(WindowAbstractDatabase.Mode.Branches, this) {
         public void createListBox(IArea area) {
            EntityPlayer player = this.database.getPlayer();
            GameProfile playerName = WindowAbstractDatabase.this.getUsername();
            Collection<IClassification> speciesList = (Collection)(!this.database.isNEI?this.database.system.getDiscoveredBranches(this.database.getWorld(), playerName):this.database.system.getAllBranches());
            this.listBox = new ControlBranchBox(this.modePage, area.x(), area.y(), area.w(), area.h());
            this.listBox.setOptions(speciesList);
         }
      });
      this.createMode(WindowAbstractDatabase.Mode.Breeder, new WindowAbstractDatabase.ModeWidgets(WindowAbstractDatabase.Mode.Breeder, this) {
         public void createListBox(IArea area) {
            this.listBox = new ControlListBox(this.modePage, area.x(), area.y(), area.w(), area.h(), 12.0F);
         }
      });
      this.addTabs();
      ControlTabBar<IDatabaseMode> tab = new ControlTabBar(this, 176.0F + this.selectionBoxWidth, 24.0F, 22.0F, 176.0F, Position.Right) {
         public ControlTab createTab(final float x, final float y, final float w, final float h, final IDatabaseMode value) {
            return new ControlTab(this, x, y, w, h, value) {
               public String getName() {
                  return ((IDatabaseMode)this.value).getName();
               }
            };
         }
      };
      tab.setValues(this.modePages.getValues());
      CraftGUIUtil.linkWidgets(tab, this.modePages);
      this.changeMode(WindowAbstractDatabase.Mode.Species);

      for(IDatabaseMode mode : this.modes.keySet()) {
         ((WindowAbstractDatabase.ModeWidgets)this.modes.get(mode)).infoTabs = new ControlTabBar(((WindowAbstractDatabase.ModeWidgets)this.modes.get(mode)).modePage, 8.0F, 24.0F, 16.0F, 176.0F, Position.Left);
         ((WindowAbstractDatabase.ModeWidgets)this.modes.get(mode)).infoTabs.setValues(((WindowAbstractDatabase.ModeWidgets)this.modes.get(mode)).infoPages.getValues());
         CraftGUIUtil.linkWidgets(((WindowAbstractDatabase.ModeWidgets)this.modes.get(mode)).infoTabs, ((WindowAbstractDatabase.ModeWidgets)this.modes.get(mode)).infoPages);
      }

   }

   public void initialiseServer() {
      IBreedingTracker tracker = this.system.getSpeciesRoot().getBreedingTracker(this.getWorld(), this.getUsername());
      if(tracker != null) {
         tracker.synchToPlayer(this.getPlayer());
      }

   }

   protected void addTabs() {
   }

   public void gotoSpecies(IAlleleSpecies value) {
      if(value != null) {
         this.modePages.setValue(WindowAbstractDatabase.Mode.Species);
         this.changeMode(WindowAbstractDatabase.Mode.Species);
         ((WindowAbstractDatabase.ModeWidgets)this.modes.get(this.modePages.getValue())).listBox.setValue(value);
      }

   }

   public void gotoSpeciesDelayed(IAlleleSpecies species) {
      this.gotoSpecies = species;
   }

   public void onUpdateClient() {
      super.onUpdateClient();
      if(this.gotoSpecies != null) {
         ((WindowAbstractDatabase)this.getSuperParent()).gotoSpecies(this.gotoSpecies);
         this.gotoSpecies = null;
      }

   }

   public static enum Mode implements IDatabaseMode {
      Species,
      Branches,
      Breeder;

      private Mode() {
      }

      public String getName() {
         return BinnieCore.proxy.localise("gui.database.mode." + this.name().toLowerCase());
      }
   }

   public abstract static class ModeWidgets {
      public WindowAbstractDatabase database;
      public ControlPage modePage;
      private ControlPages infoPages;
      public ControlListBox listBox;
      private ControlTabBar infoTabs;

      public ModeWidgets(IDatabaseMode mode, WindowAbstractDatabase database) {
         super();
         this.database = database;
         this.modePage = new ControlPage(database.modePages, 0.0F, 0.0F, database.getSize().x(), database.getSize().y(), mode);
         IArea listBoxArea = database.panelSearch.area().inset(2);
         this.createListBox(listBoxArea);
         CraftGUIUtil.alignToWidget(this.listBox, database.panelSearch);
         CraftGUIUtil.moveWidget(this.listBox, new IPoint(2.0F, 2.0F));
         this.infoPages = new ControlPages(this.modePage, 0.0F, 0.0F, 144.0F, 176.0F);
         CraftGUIUtil.alignToWidget(this.infoPages, database.panelInformation);
      }

      public abstract void createListBox(IArea var1);
   }
}
