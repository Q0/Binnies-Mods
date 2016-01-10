package binnie.core.triggers;

import binnie.core.machines.component.IBuildcraft;
import buildcraft.api.statements.IActionExternal;
import buildcraft.api.statements.IActionProvider;
import buildcraft.api.statements.IStatementContainer;
import cpw.mods.fml.common.Optional.Method;
import java.util.Collection;
import java.util.LinkedList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

class ActionProvider implements IActionProvider {
   ActionProvider() {
      super();
   }

   @Method(
      modid = "BuildCraft|Silicon"
   )
   public Collection getInternalActions(IStatementContainer container) {
      return null;
   }

   @Method(
      modid = "BuildCraft|Silicon"
   )
   public Collection getExternalActions(ForgeDirection side, TileEntity tile) {
      LinkedList<IActionExternal> list = new LinkedList();
      if(tile instanceof IBuildcraft.ActionProvider) {
         ((IBuildcraft.ActionProvider)tile).getActions(list);
      }

      LinkedList<IActionExternal> list2 = new LinkedList();

      for(IActionExternal action : list2) {
         if(action != null && action.getUniqueTag() != null) {
            list.add(action);
         }
      }

      return list2;
   }
}
