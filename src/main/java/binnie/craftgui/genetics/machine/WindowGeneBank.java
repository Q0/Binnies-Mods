package binnie.craftgui.genetics.machine;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextEdit;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.controls.tab.ControlTab;
import binnie.craftgui.controls.tab.ControlTabBar;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventTextEdit;
import binnie.craftgui.events.EventValueChanged;
import binnie.craftgui.genetics.machine.ControlGeneScroll;
import binnie.craftgui.genetics.machine.WindowMachine;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlTabIcon;
import binnie.craftgui.window.Panel;
import binnie.genetics.Genetics;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.genetics.GeneTracker;
import cpw.mods.fml.relauncher.Side;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class WindowGeneBank extends WindowMachine {
   public static IIcon[] iconsDNA;
   public boolean isNei;
   ControlGeneScroll genes;

   public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound action) {
      super.recieveGuiNBT(side, player, name, action);
      if(side == Side.SERVER && name.equals("gene-select")) {
         Gene gene = new Gene(action.getCompoundTag("gene"));
         if(gene != null && !gene.isCorrupted()) {
            ItemStack held = this.getHeldItemStack();
            ItemStack converted = Engineering.addGene(held, gene);
            this.getPlayer().inventory.setItemStack(converted);
            this.getPlayer().inventory.markDirty();
            if(this.getPlayer() instanceof EntityPlayerMP) {
               ((EntityPlayerMP)this.getPlayer()).sendContainerToPlayer(player.inventoryContainer);
            }
         }
      }

   }

   public static Window create(EntityPlayer player, IInventory inventory, Side side) {
      return new WindowGeneBank(player, inventory, side, false);
   }

   public WindowGeneBank(EntityPlayer player, IInventory inventory, Side side, boolean isNEI) {
      super(320, 224, player, inventory, side);
      this.isNei = isNEI;
   }

   public void initialiseServer() {
      GeneTracker tracker = GeneTracker.getTracker(this.getWorld(), this.getUsername());
      if(tracker != null) {
         tracker.synchToPlayer(this.getPlayer());
      }

   }

   public void initialiseClient() {
      this.setTitle("Gene Bank");
      this.addEventHandler(new EventValueChanged.Handler() {
         public void onEvent(EventValueChanged event) {
            if(event.value instanceof BreedingSystem) {
               WindowGeneBank.this.genes.setValue((BreedingSystem)event.value);
            }

         }
      });
      int boxX = 100;
      int x = 16;
      int y = 32;
      new ControlPlayerInventory(this, x, y);
      x = x + 124;
      int geneBoxWidth = 120;
      new Panel(this, (float)(x + 24), 32.0F, (float)geneBoxWidth, 120.0F, MinecraftGUI.PanelType.Black);
      new Panel(this, (float)(x + 24 + geneBoxWidth), 32.0F, 14.0F, 120.0F, MinecraftGUI.PanelType.Gray);
      ControlScrollableContent scroll = new ControlScrollableContent(this, (float)(x + 24 + 2), 34.0F, (float)(geneBoxWidth + 10), 116.0F, 12.0F);
      ControlTextEdit edit = new ControlTextEdit(this, (float)(x + 27 + geneBoxWidth - 70), 18.0F, 80.0F, 12.0F);
      this.addEventHandler((new EventTextEdit.Handler() {
         public void onEvent(EventTextEdit event) {
            WindowGeneBank.this.genes.setFilter((String)event.getValue());
         }
      }).setOrigin(EventHandler.Origin.Self, edit));
      this.genes = new ControlGeneScroll(scroll, 1.0F, 1.0F, (float)geneBoxWidth, 116.0F);
      scroll.setScrollableContent(this.genes);
      this.genes.setGenes(Binnie.Genetics.beeBreedingSystem);
      ControlTabBar<BreedingSystem> tabBar = new ControlTabBar(this, (float)x, 32.0F, 24.0F, 120.0F, Position.Left) {
         public ControlTab createTab(final float x, final float y, final float w, final float h, final BreedingSystem value) {
            return new ControlTabIcon(this, x, y, w, h, value) {
               public void getTooltip(Tooltip tooltip) {
                  tooltip.add(((BreedingSystem)this.getValue()).toString());
                  int totalGenes = 0;
                  int seqGenes = 0;
                  GeneTracker tracker = GeneTracker.getTracker(WindowGeneBank.this.getWorld(), WindowGeneBank.this.getUsername());
                  Map<IChromosomeType, List<IAllele>> genes = Binnie.Genetics.getChromosomeMap(((BreedingSystem)this.getValue()).getSpeciesRoot());

                  for(Entry<IChromosomeType, List<IAllele>> entry : genes.entrySet()) {
                     totalGenes += ((List)entry.getValue()).size();

                     for(IAllele allele : (List)entry.getValue()) {
                        Gene gene = new Gene(allele, (IChromosomeType)entry.getKey(), ((BreedingSystem)this.getValue()).getSpeciesRoot());
                        if(tracker.isSequenced(gene)) {
                           ++seqGenes;
                        }
                     }
                  }

                  tooltip.add("" + seqGenes + "/" + totalGenes + " Genes");
               }
            };
         }
      };
      tabBar.setValues(Binnie.Genetics.getActiveSystems());
      tabBar.setValue(Binnie.Genetics.beeBreedingSystem);
      boxX = x - 8;
      ControlTabBar<String> infoTabs = new ControlTabBar(this, (float)(boxX + 8), 160.0F, 16.0F, 50.0F, Position.Left);
      infoTabs.setValues(Arrays.asList(new String[]{"Stats", "Ranking"}));
      infoTabs.setValue("Info");
      Panel panelProject = new Panel(this, (float)(boxX + 24), 160.0F, (float)(geneBoxWidth + 20), 50.0F, MinecraftGUI.PanelType.Black);
      int totalGenes = 0;
      int seqGenes = 0;

      for(BreedingSystem system : Binnie.Genetics.getActiveSystems()) {
         GeneTracker tracker = GeneTracker.getTracker(this.getWorld(), this.getUsername());
         Map<IChromosomeType, List<IAllele>> genes = Binnie.Genetics.getChromosomeMap(system.getSpeciesRoot());

         for(Entry<IChromosomeType, List<IAllele>> entry : genes.entrySet()) {
            totalGenes += ((List)entry.getValue()).size();

            for(IAllele allele : (List)entry.getValue()) {
               Gene gene = new Gene(allele, (IChromosomeType)entry.getKey(), system.getSpeciesRoot());
               if(tracker.isSequenced(gene)) {
                  ++seqGenes;
               }
            }
         }
      }

      new ControlText(panelProject, new IPoint(4.0F, 4.0F), "§nFull Genome Project");
      new ControlText(panelProject, new IPoint(4.0F, 18.0F), "§oSequenced §r" + seqGenes + "/" + totalGenes + " §oGenes");
   }

   public String getTitle() {
      return "Gene Bank";
   }

   protected AbstractMod getMod() {
      return Genetics.instance;
   }

   protected String getName() {
      return "GeneBank";
   }

   public static class ChromosomeType {
      IChromosomeType chromosome;
      BreedingSystem system;

      public ChromosomeType(IChromosomeType chromosome, BreedingSystem system) {
         super();
         this.chromosome = chromosome;
         this.system = system;
      }

      public String toString() {
         return this.system.getChromosomeName(this.chromosome);
      }
   }
}
