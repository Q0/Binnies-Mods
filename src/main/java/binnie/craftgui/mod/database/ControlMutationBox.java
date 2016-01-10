package binnie.craftgui.mod.database;

import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.mod.database.ControlMutationItem;
import binnie.craftgui.mod.database.WindowAbstractDatabase;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IMutation;
import java.util.List;

class ControlMutationBox extends ControlListBox {
   private int index;
   private ControlMutationBox.Type type;
   private IAlleleSpecies species = null;

   public IWidget createOption(IMutation value, int y) {
      return new ControlMutationItem((ControlList)this.getContent(), value, this.species, y);
   }

   public ControlMutationBox(IWidget parent, int x, int y, int width, int height, ControlMutationBox.Type type) {
      super(parent, (float)x, (float)y, (float)width, (float)height, 12.0F);
      this.type = type;
   }

   public void setSpecies(IAlleleSpecies species) {
      if(species != this.species) {
         this.species = species;
         this.index = 0;
         this.movePercentage(-100.0F);
         BreedingSystem system = ((WindowAbstractDatabase)this.getSuperParent()).getBreedingSystem();
         List<IMutation> discovered = system.getDiscoveredMutations(Window.get(this).getWorld(), Window.get(this).getUsername());
         if(species != null) {
            if(this.type == ControlMutationBox.Type.Resultant) {
               this.setOptions(system.getResultantMutations(species));
            } else {
               List<IMutation> mutations = system.getFurtherMutations(species);
               int i = 0;

               while(i < mutations.size()) {
                  IMutation mutation = (IMutation)mutations.get(i);
                  if(!discovered.contains(mutations) && !((IAlleleSpecies)mutation.getTemplate()[0]).isCounted()) {
                     mutations.remove(i);
                  } else {
                     ++i;
                  }
               }

               this.setOptions(mutations);
            }
         }
      }

   }

   static enum Type {
      Resultant,
      Further;

      private Type() {
      }
   }
}
