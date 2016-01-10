package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.extratrees.genetics.ButterflySpecies;
import binnie.extratrees.genetics.ExtraTreeFruitGene;
import binnie.extratrees.genetics.ExtraTreeMutation;
import binnie.extratrees.genetics.ExtraTreeSpecies;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IClassification.EnumClassLevel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ModuleGenetics implements IInitializable {
   String[] branches = new String[]{"Malus Maleae Amygdaloideae Rosaceae", "Musa   Musaceae Zingiberales Commelinids Angiosperms", "Sorbus Maleae", "Tsuga   Pinaceae", "Fraxinus Oleeae  Oleaceae Lamiales Asterids Angiospems"};
   List classifications = new ArrayList();

   public ModuleGenetics() {
      super();
   }

   public void preInit() {
      for(ExtraTreeFruitGene fruit : ExtraTreeFruitGene.values()) {
         AlleleManager.alleleRegistry.registerAllele(fruit);
      }

      for(ExtraTreeSpecies species : ExtraTreeSpecies.values()) {
         AlleleManager.alleleRegistry.registerAllele(species);
      }

   }

   public void init() {
   }

   public void postInit() {
      ExtraTreeSpecies.init();
      ExtraTreeFruitGene.init();

      for(ExtraTreeSpecies species : ExtraTreeSpecies.values()) {
         Binnie.Genetics.getTreeRoot().registerTemplate(species.getTemplate());
      }

      ExtraTreeMutation.init();
      if(BinnieCore.isLepidopteryActive()) {
         for(ButterflySpecies species : ButterflySpecies.values()) {
            AlleleManager.alleleRegistry.registerAllele(species);
            Binnie.Genetics.getButterflyRoot().registerTemplate(species.getTemplate());
            String scientific = species.branchName.substring(0, 1).toUpperCase() + species.branchName.substring(1).toLowerCase();
            String uid = "trees." + species.branchName.toLowerCase();
            IClassification branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
            if(branch == null) {
               branch = AlleleManager.alleleRegistry.createAndRegisterClassification(EnumClassLevel.GENUS, uid, scientific);
            }

            species.branch = branch;
            species.branch.addMemberSpecies(species);
         }
      }

   }

   private void generateBranches() {
      for(String hierarchy : this.branches) {
         List<String> set = new ArrayList();

         for(String string : hierarchy.split(" ", 0)) {
            set.add(string.toLowerCase());
         }

         this.classifications.add(set);
      }

      for(ExtraTreeSpecies species : ExtraTreeSpecies.values()) {
         IClassification branch = this.getOrCreateClassification(EnumClassLevel.GENUS, species.branchName);
         branch.addMemberSpecies(species);
         species.branch = branch;
         IClassification clss = branch;
         int currentLevel = EnumClassLevel.GENUS.ordinal();

         label169:
         while(clss.getParent() == null) {
            Iterator i$ = this.classifications.iterator();

            List<String> set;
            while(true) {
               if(!i$.hasNext()) {
                  continue label169;
               }

               set = (List)i$.next();
               if(set.contains(clss.getScientific().toLowerCase())) {
                  break;
               }
            }

            String nextLevel = "";

            for(int index = set.indexOf(clss.getScientific().toLowerCase()) + 1; nextLevel.length() == 0; --currentLevel) {
               try {
                  nextLevel = (String)set.get(index++);
               } catch (IndexOutOfBoundsException var13) {
                  throw new RuntimeException("Reached end point at " + (String)set.get(index - 2));
               }
            }

            IClassification parent = this.getOrCreateClassification(EnumClassLevel.values()[currentLevel], nextLevel);
            parent.addMemberGroup(clss);
            System.out.println("Went from " + clss.getScientific() + " to " + parent.getScientific());
            clss = parent;
         }
      }

   }

   private IClassification getOrCreateClassification(EnumClassLevel level, String name) {
      if(level == EnumClassLevel.GENUS) {
         name = "trees." + name;
      }

      String uid = level.name().toLowerCase(Locale.ENGLISH) + "." + name.toLowerCase();
      return AlleleManager.alleleRegistry.getClassification(uid) != null?AlleleManager.alleleRegistry.getClassification(uid):AlleleManager.alleleRegistry.createAndRegisterClassification(level, name.toLowerCase(), name);
   }
}
