package binnie.genetics.gui;

import binnie.Binnie;
import binnie.botany.api.IFlower;
import binnie.core.AbstractMod;
import binnie.core.genetics.BreedingSystem;
import binnie.core.machines.inventory.SlotValidator;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.scroll.ControlScrollBar;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.Widget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.events.EventKey;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.InventoryType;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlide;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import binnie.craftgui.window.Panel;
import binnie.genetics.Genetics;
import binnie.genetics.gui.AnalystPageAppearance;
import binnie.genetics.gui.AnalystPageBehaviour;
import binnie.genetics.gui.AnalystPageBiology;
import binnie.genetics.gui.AnalystPageClimate;
import binnie.genetics.gui.AnalystPageDatabase;
import binnie.genetics.gui.AnalystPageDescription;
import binnie.genetics.gui.AnalystPageFruit;
import binnie.genetics.gui.AnalystPageGenome;
import binnie.genetics.gui.AnalystPageGrowth;
import binnie.genetics.gui.AnalystPageKaryogram;
import binnie.genetics.gui.AnalystPageMutations;
import binnie.genetics.gui.AnalystPageProducts;
import binnie.genetics.gui.AnalystPageSoil;
import binnie.genetics.gui.AnalystPageSpecimen;
import binnie.genetics.gui.AnalystPageWood;
import binnie.genetics.gui.ControlAnalystPage;
import binnie.genetics.gui.ControlTooltip;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.Analyser;
import binnie.genetics.machine.ModuleMachine;
import cpw.mods.fml.relauncher.Side;
import forestry.api.apiculture.IBee;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class WindowAnalyst extends Window {
   IWidget baseWidget = null;
   ControlScrollableContent leftPage;
   ControlScrollableContent rightPage;
   Control tabBar = null;
   Panel analystPanel;
   List analystPages = new ArrayList();
   IArea analystPageSize = null;
   boolean isDatabase = false;
   boolean isMaster = false;
   boolean lockedSearch = false;
   private Control analystNone;
   private ControlSlide slideUpInv;
   IIndividual current = null;
   BreedingSystem currentSystem = null;

   public WindowAnalyst(EntityPlayer player, IInventory inventory, Side side, boolean database, boolean master) {
      super(312.0F, 230.0F, player, inventory, side);
      this.isDatabase = database;
      this.isMaster = master;
      this.lockedSearch = this.isDatabase;
   }

   protected AbstractMod getMod() {
      return Genetics.instance;
   }

   protected String getName() {
      return "Analyst";
   }

   private void setupValidators() {
      if(!this.isDatabase) {
         this.getWindowInventory().setValidator(0, new SlotValidator.Individual() {
            public boolean isValid(ItemStack itemStack) {
               return Analyser.isAnalysed(itemStack) || Analyser.isAnalysable(itemStack) && WindowAnalyst.this.getWindowInventory().getStackInSlot(1) != null;
            }
         });
         this.getWindowInventory().setValidator(1, new SlotValidator.Item(GeneticsItems.DNADye.get(1), ModuleMachine.IconDye));
      }

   }

   public void initialiseServer() {
      for(BreedingSystem system : Binnie.Genetics.getActiveSystems()) {
         IBreedingTracker tracker = system.getSpeciesRoot().getBreedingTracker(this.getWorld(), this.getUsername());
         if(tracker != null) {
            tracker.synchToPlayer(this.getPlayer());
         }
      }

      this.setupValidators();
   }

   public void initialiseClient() {
      this.setTitle(!this.isDatabase?"Analyst":(this.isMaster?"Master Registry":"Registry"));
      BreedingSystem system = Binnie.Genetics.beeBreedingSystem;
      IIndividual ind = system.getDefaultIndividual();
      system.getSpeciesRoot().getMemberStack(ind, system.getDefaultType());
      this.getWindowInventory().createSlot(0);
      this.baseWidget = new Widget(this);
      int x = 16;
      int y = 28;
      if(this.isDatabase) {
         for(final BreedingSystem syst : Binnie.Genetics.getActiveSystems()) {
            Control var10001 = new Control(this, (float)x, (float)y, 20.0F, 20.0F) {
               public void initialise() {
                  this.addAttribute(Attribute.MouseOver);
                  this.addSelfEventHandler(new EventMouse.Down.Handler() {
                     public void onEvent(EventMouse.Down event) {
                        WindowAnalyst.this.setSystem(syst);
                     }
                  });
               }

               public void getTooltip(Tooltip tooltip) {
                  tooltip.add(syst.getName());
               }

               public void onRenderBackground() {
                  CraftGUI.Render.colour(syst.getColour());
                  int outset = WindowAnalyst.this.getSystem() == syst?1:0;
                  CraftGUI.Render.texture((Object)CraftGUITexture.TabOutline, (IArea)this.getArea().outset(outset));
                  if(WindowAnalyst.this.getSystem() == syst) {
                     CraftGUI.Render.colour(1140850688 + syst.getColour());
                     CraftGUI.Render.texture((Object)CraftGUITexture.TabSolid, (IArea)this.getArea().outset(outset));
                  }

                  CraftGUI.Render.item(new IPoint(2.0F, 2.0F), syst.getItemStackRepresentitive());
               }
            };
            x += 22;
         }
      } else {
         (new ControlSlot(this, (float)x, (float)(y + 1))).assign(InventoryType.Window, 0);
         x = x + 22;
         (new ControlSlot(this, (float)x, (float)(y + 1))).assign(InventoryType.Window, 1);
         x = x + 26;
         this.setupValidators();
      }

      this.tabBar = new Control(this, (float)x, 28.0F, this.w() - 16.0F - (float)x, 20.0F);
      this.analystPanel = new Panel(this, 16.0F, 54.0F, 280.0F, 164.0F, MinecraftGUI.PanelType.Outline) {
         public void onRenderBackground() {
            CraftGUI.Render.gradientRect(this.getArea(), 1157627903, 1728053247);
            super.onRenderBackground();
         }

         public void initialise() {
            this.setColour(4473924);
            float sectionWidth = (this.w() - 8.0F - 4.0F) / 2.0F;
            WindowAnalyst.this.leftPage = new ControlScrollableContent(this, 3.0F, 3.0F, sectionWidth + 2.0F, this.h() - 8.0F + 2.0F, 0.0F) {
               public void onRenderBackground() {
                  if(this.getContent() != null) {
                     CraftGUI.Render.colour(this.getContent().getColour());
                     CraftGUI.Render.texture((Object)CraftGUITexture.TabOutline, (IArea)this.getArea());
                  }
               }
            };
            ControlScrollBar var10001 = new ControlScrollBar(this, sectionWidth + 2.0F - 3.0F, 6.0F, 3.0F, this.h() - 8.0F + 2.0F - 6.0F, WindowAnalyst.this.leftPage) {
               public void onRenderBackground() {
                  if(this.isEnabled()) {
                     if(WindowAnalyst.this.leftPage.getContent() != null) {
                        CraftGUI.Render.gradientRect(this.getArea(), 1140850688 + WindowAnalyst.this.leftPage.getContent().getColour(), 1140850688 + WindowAnalyst.this.leftPage.getContent().getColour());
                        CraftGUI.Render.solid(this.getRenderArea(), WindowAnalyst.this.leftPage.getContent().getColour());
                     }
                  }
               }
            };
            WindowAnalyst.this.rightPage = new ControlScrollableContent(this, 3.0F + sectionWidth + 4.0F, 3.0F, sectionWidth + 2.0F, this.h() - 8.0F + 2.0F, 0.0F) {
               public void onRenderBackground() {
                  if(this.getContent() != null) {
                     CraftGUI.Render.colour(this.getContent().getColour());
                     CraftGUI.Render.texture((Object)CraftGUITexture.TabOutline, (IArea)this.getArea());
                  }
               }
            };
            var10001 = new ControlScrollBar(this, sectionWidth + 2.0F - 3.0F + sectionWidth + 4.0F, 6.0F, 3.0F, this.h() - 8.0F + 2.0F - 6.0F, WindowAnalyst.this.rightPage) {
               public void onRenderBackground() {
                  if(this.isEnabled()) {
                     if(WindowAnalyst.this.rightPage.getContent() != null) {
                        CraftGUI.Render.gradientRect(this.getArea(), 1140850688 + WindowAnalyst.this.rightPage.getContent().getColour(), 1140850688 + WindowAnalyst.this.rightPage.getContent().getColour());
                        CraftGUI.Render.solid(this.getRenderArea(), WindowAnalyst.this.rightPage.getContent().getColour());
                     }
                  }
               }
            };
            WindowAnalyst.this.analystPageSize = new IArea(1.0F, 1.0F, sectionWidth, this.h() - 8.0F);
         }
      };
      if(!this.isDatabase) {
         this.slideUpInv = new ControlSlide(this, (this.getSize().x() - 244.0F) / 2.0F, this.getSize().y() - 80.0F + 1.0F, 244.0F, 80.0F, Position.Bottom);
         new ControlPlayerInventory(this.slideUpInv, true);
         this.slideUpInv.setSlide(false);
      }

      this.addEventHandler(new EventKey.Down.Handler() {
         public void onEvent(EventKey.Down event) {
            if(event.getKey() == 205) {
               WindowAnalyst.this.shiftPages(true);
            }

            if(event.getKey() == 203) {
               WindowAnalyst.this.shiftPages(false);
            }

         }
      });
      if(!this.isDatabase) {
         this.analystNone = new Control(this.analystPanel, 0.0F, 0.0F, this.analystPanel.w(), this.analystPanel.h()) {
            public void initialise() {
               (new ControlTextCentered(this, 20.0F, "Add a bee, tree, flower or butterfly to the top left slot. DNA Dye is required if it has not been analysed yet. This dye can also convert vanilla items to breedable individuals.")).setColour(4473924);
               new ControlPlayerInventory(this);
            }
         };
      }

      this.setIndividual((IIndividual)null);
      this.setSystem(Binnie.Genetics.beeBreedingSystem);
   }

   public void setIndividual(IIndividual ind) {
      if(!this.isDatabase) {
         if(ind == null) {
            this.analystNone.show();
            this.slideUpInv.hide();
         } else {
            this.analystNone.hide();
            this.slideUpInv.show();
         }
      }

      if(ind != this.current && (ind == null || this.current == null || !ind.isGeneticEqual(this.current))) {
         this.current = ind;
         boolean systemChange = ind != null && ind.getGenome().getSpeciesRoot() != this.getSystem().getSpeciesRoot();
         if(systemChange) {
            this.currentSystem = Binnie.Genetics.getSystem(ind.getGenome().getSpeciesRoot());
         }

         this.updatePages(systemChange);
      }
   }

   public void setSystem(BreedingSystem system) {
      if(system != this.currentSystem) {
         this.currentSystem = system;
         this.current = null;
         this.updatePages(true);
      }
   }

   public void updatePages(boolean systemChange) {
      int oldLeft = -1;
      int oldRight = -1;
      if(!systemChange) {
         oldLeft = this.analystPages.indexOf(this.leftPage.getContent());
         oldRight = this.analystPages.indexOf(this.rightPage.getContent());
      }

      ControlAnalystPage databasePage = null;
      if(this.isDatabase && !systemChange) {
         databasePage = this.analystPages.size() > 0?(ControlAnalystPage)this.analystPages.get(0):null;
      }

      this.analystPages.clear();
      this.setPage(this.leftPage, (ControlAnalystPage)null);
      this.setPage(this.rightPage, (ControlAnalystPage)null);
      if(this.isDatabase) {
         this.analystPages.add(databasePage != null?databasePage:new AnalystPageDatabase(this.analystPanel, this.analystPageSize, this.currentSystem, this.isMaster));
      }

      if(this.current != null) {
         this.analystPages.add(new AnalystPageDescription(this.analystPanel, this.analystPageSize, this.current));
         this.analystPages.add(new AnalystPageGenome(this.analystPanel, this.analystPageSize, true, this.current));
         if(!this.isDatabase) {
            this.analystPages.add(new AnalystPageGenome(this.analystPanel, this.analystPageSize, false, this.current));
            this.analystPages.add(new AnalystPageKaryogram(this.analystPanel, this.analystPageSize, this.current));
         }

         if(!(this.current instanceof ITree)) {
            this.analystPages.add(new AnalystPageClimate(this.analystPanel, this.analystPageSize, this.current));
         }

         if(this.current instanceof IBee) {
            this.analystPages.add(new AnalystPageProducts(this.analystPanel, this.analystPageSize, (IBee)this.current));
         } else if(this.current instanceof ITree) {
            this.analystPages.add(new AnalystPageFruit(this.analystPanel, this.analystPageSize, (ITree)this.current));
            this.analystPages.add(new AnalystPageWood(this.analystPanel, this.analystPageSize, (ITree)this.current));
         } else if(this.current instanceof IFlower) {
            this.analystPages.add(new AnalystPageSoil(this.analystPanel, this.analystPageSize, (IFlower)this.current));
         } else if(this.current instanceof IButterfly) {
            this.analystPages.add(new AnalystPageSpecimen(this.analystPanel, this.analystPageSize, (IButterfly)this.current));
         }

         this.analystPages.add(new AnalystPageBiology(this.analystPanel, this.analystPageSize, this.current));
         if(!(this.current instanceof IBee) && !(this.current instanceof IButterfly)) {
            if(this.current instanceof ITree) {
               this.analystPages.add(new AnalystPageGrowth(this.analystPanel, this.analystPageSize, this.current));
            } else if(this.current instanceof IFlower) {
               this.analystPages.add(new AnalystPageAppearance(this.analystPanel, this.analystPageSize, (IFlower)this.current));
            }
         } else {
            this.analystPages.add(new AnalystPageBehaviour(this.analystPanel, this.analystPageSize, this.current));
         }

         this.analystPages.add(new AnalystPageMutations(this.analystPanel, this.analystPageSize, this.current, this.isMaster));
      }

      this.tabBar.deleteAllChildren();
      final float width = this.tabBar.w() / (float)this.analystPages.size();
      final float x = 0.0F;

      for(final ControlAnalystPage page : this.analystPages) {
         ControlTooltip var10001 = new ControlTooltip(this.tabBar, x, 0.0F, width, this.tabBar.h()) {
            ControlAnalystPage value;

            public void getTooltip(Tooltip tooltip) {
               tooltip.add(this.value.getTitle());
            }

            protected void initialise() {
               super.initialise();
               this.addAttribute(Attribute.MouseOver);
               this.value = page;
               this.addSelfEventHandler(new EventMouse.Down.Handler() {
                  public void onEvent(EventMouse.Down event) {
                     int currentIndex = WindowAnalyst.this.analystPages.indexOf(WindowAnalyst.this.rightPage.getContent());
                     int clickedIndex = WindowAnalyst.this.analystPages.indexOf(value);
                     if(WindowAnalyst.this.isDatabase) {
                        if(clickedIndex != 0 && clickedIndex != currentIndex) {
                           WindowAnalyst.this.setPage(WindowAnalyst.this.rightPage, value);
                        }
                     } else {
                        if(clickedIndex < 0) {
                           clickedIndex = 0;
                        }

                        if(clickedIndex < currentIndex) {
                           ++clickedIndex;
                        }

                        WindowAnalyst.this.setPage(WindowAnalyst.this.rightPage, (ControlAnalystPage)null);
                        WindowAnalyst.this.setPage(WindowAnalyst.this.leftPage, (ControlAnalystPage)null);
                        WindowAnalyst.this.setPage(WindowAnalyst.this.rightPage, (ControlAnalystPage)WindowAnalyst.this.analystPages.get(clickedIndex));
                        WindowAnalyst.this.setPage(WindowAnalyst.this.leftPage, (ControlAnalystPage)WindowAnalyst.this.analystPages.get(clickedIndex - 1));
                     }

                  }
               });
            }

            public void onRenderBackground() {
               boolean active = this.value == WindowAnalyst.this.leftPage.getContent() || this.value == WindowAnalyst.this.rightPage.getContent();
               CraftGUI.Render.colour((active?-16777216:1140850688) + this.value.getColour());
               CraftGUI.Render.texture((Object)CraftGUITexture.TabSolid, (IArea)this.getArea().inset(1));
               CraftGUI.Render.colour(this.value.getColour());
               CraftGUI.Render.texture((Object)CraftGUITexture.TabOutline, (IArea)this.getArea().inset(1));
               super.onRenderBackground();
            }
         };
         x += width;
      }

      if(this.analystPages.size() > 0) {
         this.setPage(this.leftPage, (ControlAnalystPage)this.analystPages.get(oldLeft >= 0?oldLeft:0));
      }

      if(this.analystPages.size() > 1) {
         this.setPage(this.rightPage, (ControlAnalystPage)this.analystPages.get(oldRight >= 0?oldRight:1));
      }

   }

   public void shiftPages(boolean right) {
      if(this.analystPages.size() >= 2) {
         int leftIndex = this.analystPages.indexOf(this.leftPage.getContent());
         int rightIndex = this.analystPages.indexOf(this.rightPage.getContent());
         if(!right || rightIndex + 1 < this.analystPages.size()) {
            if(this.lockedSearch || right || leftIndex > 0) {
               if(this.lockedSearch || right || rightIndex > 1) {
                  int newRightIndex = rightIndex + (right?1:-1);
                  int newLeftIndex = this.lockedSearch?0:newRightIndex - 1;
                  float oldRightPercent = 0.0F;
                  float oldLeftPercent = 0.0F;
                  if(newLeftIndex == rightIndex) {
                     oldRightPercent = this.rightPage.getPercentageIndex();
                  }

                  if(newRightIndex == leftIndex) {
                     oldLeftPercent = this.leftPage.getPercentageIndex();
                  }

                  this.setPage(this.leftPage, (ControlAnalystPage)null);
                  this.setPage(this.rightPage, (ControlAnalystPage)null);
                  this.setPage(this.leftPage, (ControlAnalystPage)this.analystPages.get(newLeftIndex));
                  this.setPage(this.rightPage, (ControlAnalystPage)this.analystPages.get(newRightIndex));
                  ((ControlAnalystPage)this.analystPages.get(newLeftIndex)).show();
                  if(oldRightPercent != 0.0F) {
                     this.leftPage.setPercentageIndex(oldRightPercent);
                  }

                  if(oldLeftPercent != 0.0F) {
                     this.rightPage.setPercentageIndex(oldLeftPercent);
                  }

               }
            }
         }
      }
   }

   public void setPage(ControlScrollableContent side, ControlAnalystPage page) {
      ControlAnalystPage existingPage = (ControlAnalystPage)side.getContent();
      if(existingPage != null) {
         existingPage.hide();
         side.setScrollableContent((IWidget)null);
      }

      if(page != null) {
         page.show();
         side.setScrollableContent(page);
         side.setPercentageIndex(0.0F);
         page.setPosition(side.pos().add(1.0F, 1.0F));
      }

   }

   public void onWindowInventoryChanged() {
      super.onWindowInventoryChanged();
      if(this.getWindowInventory().getStackInSlot(0) != null && !Analyser.isAnalysed(this.getWindowInventory().getStackInSlot(0))) {
         this.getWindowInventory().setInventorySlotContents(0, Analyser.analyse(this.getWindowInventory().getStackInSlot(0)));
         this.getWindowInventory().decrStackSize(1, 1);
      }

      IIndividual ind = AlleleManager.alleleRegistry.getIndividual(this.getWindowInventory().getStackInSlot(0));
      if(ind != null) {
         ind.getGenome().getSpeciesRoot().getBreedingTracker(this.getWorld(), this.getUsername()).registerBirth(ind);
      }

      if(this.isClient()) {
         this.setStack(this.getWindowInventory().getStackInSlot(0));
      } else if(this.isServer()) {
         ;
      }

   }

   public void setStack(ItemStack stack) {
      IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
      this.setIndividual(ind);
   }

   public IIndividual getIndividual() {
      return this.current;
   }

   public BreedingSystem getSystem() {
      return this.currentSystem;
   }
}
