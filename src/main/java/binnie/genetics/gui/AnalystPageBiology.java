package binnie.genetics.gui;

import binnie.Binnie;
import binnie.botany.api.IFlower;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.control.ControlIconDisplay;
import binnie.genetics.gui.ControlAnalystPage;
import binnie.genetics.item.ModuleItem;
import forestry.api.apiculture.IBee;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import forestry.plugins.PluginApiculture;
import java.text.DecimalFormat;
import net.minecraftforge.common.EnumPlantType;

public class AnalystPageBiology extends ControlAnalystPage {
   public AnalystPageBiology(IWidget parent, IArea area, IIndividual ind) {
      super(parent, area);
      this.setColour(26214);
      int y = 4;
      (new ControlTextCentered(this, (float)y, "§nBiology")).setColour(this.getColour());
      y = y + 12;
      if(ind instanceof IBee) {
         IBee bee = (IBee)ind;
         if(bee.getGenome().getNocturnal()) {
            (new ControlIconDisplay(this, (this.w() - 64.0F) / 2.0F, (float)y, ModuleItem.iconAllDay.getIcon())).addTooltip("Active all day and night");
         } else if(bee.getGenome().getPrimary().isNocturnal()) {
            (new ControlIconDisplay(this, (this.w() - 64.0F) / 2.0F, (float)y, ModuleItem.iconNight.getIcon())).addTooltip("Active at night");
         } else {
            (new ControlIconDisplay(this, (this.w() - 64.0F) / 2.0F, (float)y, ModuleItem.iconDaytime.getIcon())).addTooltip("Active during the day");
         }

         if(!bee.getGenome().getTolerantFlyer()) {
            (new ControlIconDisplay(this, (this.w() - 64.0F) / 2.0F + 24.0F, (float)y, ModuleItem.iconNoRain.getIcon())).addTooltip("Cannot work during rain");
         } else {
            (new ControlIconDisplay(this, (this.w() - 64.0F) / 2.0F + 24.0F, (float)y, ModuleItem.iconRain.getIcon())).addTooltip("Can work during rain");
         }

         if(bee.getGenome().getCaveDwelling()) {
            (new ControlIconDisplay(this, (this.w() - 64.0F) / 2.0F + 48.0F, (float)y, ModuleItem.iconNoSky.getIcon())).addTooltip("Can work underground");
         } else {
            (new ControlIconDisplay(this, (this.w() - 64.0F) / 2.0F + 48.0F, (float)y, ModuleItem.iconSky.getIcon())).addTooltip("Cannot work underground");
         }

         y = y + 30;
      } else if(ind instanceof IButterfly) {
         IButterfly moth = (IButterfly)ind;
         if(moth.getGenome().getNocturnal()) {
            (new ControlIconDisplay(this, (this.w() - 64.0F) / 2.0F, (float)y, ModuleItem.iconAllDay.getIcon())).addTooltip("Active all day and night");
         } else if(moth.getGenome().getPrimary().isNocturnal()) {
            (new ControlIconDisplay(this, (this.w() - 64.0F) / 2.0F, (float)y, ModuleItem.iconNight.getIcon())).addTooltip("Active at night");
         } else {
            (new ControlIconDisplay(this, (this.w() - 64.0F) / 2.0F, (float)y, ModuleItem.iconDaytime.getIcon())).addTooltip("Active during the day");
         }

         if(!moth.getGenome().getTolerantFlyer()) {
            (new ControlIconDisplay(this, (this.w() - 64.0F) / 2.0F + 24.0F, (float)y, ModuleItem.iconNoRain.getIcon())).addTooltip("Cannot work during rain");
         } else {
            (new ControlIconDisplay(this, (this.w() - 64.0F) / 2.0F + 24.0F, (float)y, ModuleItem.iconRain.getIcon())).addTooltip("Can work during rain");
         }

         if(moth.getGenome().getFireResist()) {
            (new ControlIconDisplay(this, (this.w() - 64.0F) / 2.0F + 48.0F, (float)y, ModuleItem.iconNoFire.getIcon())).addTooltip("Nonflammable");
         } else {
            (new ControlIconDisplay(this, (this.w() - 64.0F) / 2.0F + 48.0F, (float)y, ModuleItem.iconFire.getIcon())).addTooltip("Flammable");
         }

         y = y + 30;
      } else if(ind instanceof ITree) {
         (new ControlTextCentered(this, (float)y, "§oSappiness: " + Binnie.Genetics.treeBreedingSystem.getAlleleName(EnumTreeChromosome.SAPPINESS, ind.getGenome().getActiveAllele(EnumTreeChromosome.SAPPINESS)))).setColour(this.getColour());
         y = y + 20;
      } else {
         y = y + 10;
      }

      if(ind instanceof IBee) {
         IBee bee = (IBee)ind;
         int fertility = bee.getGenome().getFertility();
         (new ControlTextCentered(this, (float)y, "§l" + fertility + "§r drone" + (fertility > 1?"s":"") + " per hive")).setColour(this.getColour());
         y = y + 22;
         int lifespan = bee.getGenome().getLifespan() * PluginApiculture.ticksPerBeeWorkCycle;
         (new ControlTextCentered(this, (float)y, "Average Lifespan")).setColour(this.getColour());
         y = y + 12;
         (new ControlTextCentered(this, (float)y, "§l" + this.getMCDayString((float)lifespan * (bee.getGenome().getNocturnal()?1.0F:2.0F)))).setColour(this.getColour());
         y = y + 22;
      }

      if(ind instanceof IButterfly) {
         IButterfly bee = (IButterfly)ind;
         int fertility = bee.getGenome().getFertility();
         (new ControlTextCentered(this, (float)y, "Lays §l" + fertility + "§r caterpillar" + (fertility > 1?"s":"") + " before dying")).setColour(this.getColour());
         y = y + 32;
         float caterpillarMatureTime = 1365.3999F * (float)Math.round((float)bee.getGenome().getLifespan() / (float)(bee.getGenome().getFertility() * 2));
         (new ControlTextCentered(this, (float)y, "Caterpillar Gestation")).setColour(this.getColour());
         y = y + 12;
         (new ControlTextCentered(this, (float)y, "§l" + this.getMCDayString(caterpillarMatureTime))).setColour(this.getColour());
         y = y + 22;
         int speed = (int)(20.0F * bee.getGenome().getSpeed());
         (new ControlTextCentered(this, (float)y, "Flight Speed")).setColour(this.getColour());
         y = y + 12;
         (new ControlTextCentered(this, (float)y, "§l" + speed + "§r blocks per second")).setColour(this.getColour());
         y = y + 22;
      }

      if(ind instanceof ITree) {
         ITree tree = (ITree)ind;
         int fertility = (int)(1.0F / tree.getGenome().getFertility());
         (new ControlTextCentered(this, (float)y, "1 Sapling per §l" + fertility + "§r leave" + (fertility > 1?"s":""))).setColour(this.getColour());
         y = y + 22;
         float butterflySpawn = 1365.3999F / (tree.getGenome().getSappiness() * tree.getGenome().getYield() * 0.5F);
         (new ControlTextCentered(this, (float)y, "Butterfies spawn every\n" + this.getTimeString(butterflySpawn) + " per leaf")).setColour(this.getColour());
         y = y + 34;
         (new ControlTextCentered(this, (float)y, "Plant Types")).setColour(this.getColour());
         y = y + 12;

         for(EnumPlantType type : tree.getGenome().getPlantTypes()) {
            (new ControlTextCentered(this, (float)y, "§o" + type.name())).setColour(this.getColour());
            y += 12;
         }
      }

      if(ind instanceof IFlower) {
         IFlower flower = (IFlower)ind;
         float butterflySpawn = 1365.3999F / (flower.getGenome().getSappiness() * 0.2F);
         (new ControlTextCentered(this, (float)y, "Butterfies spawn every\n" + this.getTimeString(butterflySpawn))).setColour(this.getColour());
         y = y + 30;
         float CHANCE_DISPERSAL = 0.8F;
         CHANCE_DISPERSAL = CHANCE_DISPERSAL + 0.2F * (float)flower.getGenome().getFertility();
         float CHANCE_POLLINATE = 0.6F;
         CHANCE_POLLINATE = CHANCE_POLLINATE + 0.25F * (float)flower.getGenome().getFertility();
         float CHANCE_SELFPOLLINATE = 0.2F * CHANCE_POLLINATE;
         if(CHANCE_DISPERSAL > 1.0F) {
            CHANCE_DISPERSAL = 1.0F;
         }

         if(CHANCE_POLLINATE > 1.0F) {
            CHANCE_POLLINATE = 1.0F;
         }

         float dispersalTime = 1365.3999F / CHANCE_DISPERSAL;
         float pollinateTime = 1365.3999F / CHANCE_POLLINATE;
         float lifespan = (float)flower.getMaxAge() * 20.0F * 68.27F / flower.getGenome().getAgeChance();
         float floweringLifespan = (float)(flower.getMaxAge() - 1) * 20.0F * 68.27F / flower.getGenome().getAgeChance();
         floweringLifespan = floweringLifespan - 1365.3999F;
         (new ControlTextCentered(this, (float)y, "Average Lifespan")).setColour(this.getColour());
         y = y + 12;
         (new ControlTextCentered(this, (float)y, "§l" + this.getMCDayString(lifespan))).setColour(this.getColour());
         y = y + 22;
         (new ControlTextCentered(this, (float)y, "Seed Dispersal")).setColour(this.getColour());
         y = y + 12;
         (new ControlTextCentered(this, (float)y, "§o" + (int)(floweringLifespan / dispersalTime) + " per lifetime")).setColour(this.getColour());
         y = y + 22;
         (new ControlTextCentered(this, (float)y, "Pollination")).setColour(this.getColour());
         y = y + 12;
         (new ControlTextCentered(this, (float)y, "§o" + (int)(floweringLifespan / pollinateTime) + " per lifetime")).setColour(this.getColour());
         y = y + 22;
      }

      this.setSize(new IPoint(this.w(), (float)y));
   }

   private String getMCDayString(float time) {
      float seconds = time / 20.0F;
      float minutes = seconds / 60.0F;
      float days = minutes / 20.0F;
      DecimalFormat df = new DecimalFormat("#.0");
      return df.format((double)days) + " MC days";
   }

   public String getTitle() {
      return "Biology";
   }
}
