package binnie.extratrees.carpentry;

import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.ILayout;
import binnie.extratrees.api.IToolHammer;
import binnie.extratrees.carpentry.BlockCarpentryPanel;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.carpentry.ModuleCarpentry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class DesignBlock {
   IDesign design;
   IDesignMaterial primaryMaterial;
   IDesignMaterial secondaryMaterial;
   int rotation = 0;
   ForgeDirection facing = ForgeDirection.UP;
   boolean panel = false;

   public String toString() {
      return super.toString() + " { design:" + this.design + " }, { primary:" + this.primaryMaterial + " }, { secondary:" + this.secondaryMaterial + " }, { rotation:" + this.rotation + " }, { facing:" + this.facing + " }";
   }

   public IDesign getDesign() {
      return this.design;
   }

   public IDesignMaterial getPrimaryMaterial() {
      return this.primaryMaterial;
   }

   public IDesignMaterial getSecondaryMaterial() {
      return this.secondaryMaterial;
   }

   DesignBlock(IDesignSystem system, IDesignMaterial primaryWood, IDesignMaterial secondaryWood, IDesign design, int rotation, ForgeDirection dir) {
      super();
      this.design = design;
      this.primaryMaterial = primaryWood;
      this.secondaryMaterial = secondaryWood;
      this.rotation = rotation;
      this.facing = dir;
      if(design == null) {
         this.design = EnumDesign.Blank;
      }

      if(primaryWood == null) {
         this.primaryMaterial = system.getDefaultMaterial();
      }

      if(secondaryWood == null) {
         this.secondaryMaterial = system.getDefaultMaterial();
      }

      if(this.rotation > 3 || this.rotation < 0) {
         this.rotation = 0;
      }

      if(this.facing == null || this.facing == ForgeDirection.UNKNOWN) {
         this.facing = ForgeDirection.UP;
      }

   }

   public int getPrimaryColour() {
      return this.getPrimaryMaterial().getColour();
   }

   public int getSecondaryColour() {
      return this.getSecondaryMaterial().getColour();
   }

   ForgeDirection getRotation(ForgeDirection dir, ModuleCarpentry.Axis axis) {
      if(axis == ModuleCarpentry.Axis.Y) {
         switch(dir) {
         case EAST:
            return ForgeDirection.NORTH;
         case NORTH:
            return ForgeDirection.WEST;
         case SOUTH:
            return ForgeDirection.EAST;
         case WEST:
            return ForgeDirection.SOUTH;
         case UP:
         case DOWN:
         case UNKNOWN:
         default:
            return dir;
         }
      } else if(axis == ModuleCarpentry.Axis.X) {
         switch(dir) {
         case EAST:
            return ForgeDirection.UP;
         case NORTH:
         case SOUTH:
         case UNKNOWN:
         default:
            return dir;
         case WEST:
            return ForgeDirection.DOWN;
         case UP:
            return ForgeDirection.WEST;
         case DOWN:
            return ForgeDirection.EAST;
         }
      } else {
         switch(dir) {
         case EAST:
         case WEST:
         case UNKNOWN:
         default:
            return dir;
         case NORTH:
            return ForgeDirection.DOWN;
         case SOUTH:
            return ForgeDirection.UP;
         case UP:
            return ForgeDirection.NORTH;
         case DOWN:
            return ForgeDirection.SOUTH;
         }
      }
   }

   public ILayout getLayout(ForgeDirection dir) {
      dir = dir.getRotation(ForgeDirection.DOWN);
      ForgeDirection adjustedDir = dir;
      switch(this.getFacing()) {
      case EAST:
         adjustedDir = dir.getRotation(ForgeDirection.EAST);
         adjustedDir = adjustedDir.getRotation(ForgeDirection.NORTH);
         break;
      case NORTH:
         adjustedDir = dir.getRotation(ForgeDirection.EAST);
         adjustedDir = adjustedDir.getRotation(ForgeDirection.SOUTH);
         adjustedDir = adjustedDir.getRotation(ForgeDirection.SOUTH);
         break;
      case SOUTH:
         adjustedDir = dir.getRotation(ForgeDirection.EAST);
         break;
      case WEST:
         adjustedDir = dir.getRotation(ForgeDirection.EAST);
         adjustedDir = adjustedDir.getRotation(ForgeDirection.SOUTH);
      case UP:
      case UNKNOWN:
      default:
         break;
      case DOWN:
         adjustedDir = dir.getRotation(ForgeDirection.EAST);
         adjustedDir = adjustedDir.getRotation(ForgeDirection.EAST);
      }

      for(int i = 0; i < this.rotation; ++i) {
         adjustedDir = adjustedDir.getRotation(ForgeDirection.DOWN);
      }

      ILayout layout;
      switch(adjustedDir) {
      case EAST:
         layout = this.getDesign().getEastPattern();
         break;
      case NORTH:
         layout = this.getDesign().getNorthPattern();
         break;
      case SOUTH:
         layout = this.getDesign().getSouthPattern();
         break;
      case WEST:
         layout = this.getDesign().getWestPattern();
         break;
      case UP:
      default:
         layout = this.getDesign().getTopPattern();
         break;
      case DOWN:
         layout = this.getDesign().getBottomPattern();
      }

      switch(this.getFacing()) {
      case EAST:
         switch(dir) {
         case NORTH:
            layout = layout.rotateLeft();
            break;
         case SOUTH:
         case UP:
            layout = layout.rotateRight();
            break;
         case WEST:
            layout = layout.flipHorizontal();
            break;
         case DOWN:
            layout = layout.rotateLeft().flipHorizontal();
         }

         if(dir == ForgeDirection.EAST) {
            for(int i = 0; i < this.rotation; ++i) {
               layout = layout.rotateRight();
            }
         }

         if(dir == ForgeDirection.WEST) {
            for(int i = 0; i < this.rotation; ++i) {
               layout = layout.rotateLeft();
            }
         }
         break;
      case NORTH:
         switch(dir) {
         case EAST:
            layout = layout.rotateRight();
            return layout;
         case NORTH:
            for(int i = 0; i < this.rotation; ++i) {
               layout = layout.rotateRight();
            }

            return layout;
         case SOUTH:
            layout = layout.flipHorizontal();

            for(int i = 0; i < this.rotation; ++i) {
               layout = layout.rotateLeft();
            }

            return layout;
         case WEST:
            layout = layout.rotateLeft();
            return layout;
         case UP:
         default:
            return layout;
         case DOWN:
            layout = layout.flipHorizontal();
            return layout;
         }
      case SOUTH:
         switch(dir) {
         case EAST:
            layout = layout.rotateLeft();
            return layout;
         case NORTH:
            layout = layout.flipHorizontal();

            for(int i = 0; i < this.rotation; ++i) {
               layout = layout.rotateLeft();
            }

            return layout;
         case SOUTH:
            for(int i = 0; i < this.rotation; ++i) {
               layout = layout.rotateRight();
            }

            return layout;
         case WEST:
            layout = layout.rotateRight();
            return layout;
         case UP:
            layout = layout.rotateRight().rotateRight();
            return layout;
         case DOWN:
            layout = layout.flipVertical();
            return layout;
         default:
            return layout;
         }
      case WEST:
         switch(dir) {
         case EAST:
            layout = layout.flipHorizontal();

            for(int i = 0; i < this.rotation; ++i) {
               layout = layout.rotateLeft();
            }

            return layout;
         case NORTH:
            layout = layout.rotateRight();
            return layout;
         case SOUTH:
         case UP:
            layout = layout.rotateLeft();
            return layout;
         case WEST:
            for(int i = 0; i < this.rotation; ++i) {
               layout = layout.rotateRight();
            }

            return layout;
         case DOWN:
            layout = layout.rotateLeft().flipVertical();
            return layout;
         default:
            return layout;
         }
      case UP:
         if(dir == ForgeDirection.DOWN || dir == ForgeDirection.UP) {
            for(int i = 0; i < this.rotation; ++i) {
               layout = layout.rotateRight();
            }
         }
         break;
      case DOWN:
         switch(dir) {
         case EAST:
         case NORTH:
         case SOUTH:
         case WEST:
            layout = layout.rotateRight().rotateRight();
            break;
         case UP:
         case DOWN:
            layout = layout.flipVertical();
         }

         if(dir == ForgeDirection.DOWN || dir == ForgeDirection.UP) {
            for(int i = 0; i < this.rotation; ++i) {
               layout = layout.rotateLeft();
            }
         }
      }

      return layout;
   }

   public IIcon getPrimaryIcon(IDesignSystem system, ForgeDirection dir) {
      ILayout l = this.getLayout(dir);
      return l == null?null:l.getPrimaryIcon(system);
   }

   public IIcon getSecondaryIcon(IDesignSystem system, ForgeDirection dir) {
      ILayout l = this.getLayout(dir);
      return l == null?null:l.getSecondaryIcon(system);
   }

   public IIcon getIcon(IDesignSystem system, boolean secondary, ForgeDirection dir) {
      return secondary?this.getSecondaryIcon(system, dir):this.getPrimaryIcon(system, dir);
   }

   public ForgeDirection getFacing() {
      return this.facing;
   }

   public int getRotation() {
      return this.rotation;
   }

   public void rotate(int face, ItemStack hammer, EntityPlayer player, World world, int x, int y, int z) {
      ForgeDirection dir = ForgeDirection.getOrientation(face);
      IToolHammer hammerI = (IToolHammer)hammer.getItem();
      if(player.isSneaking()) {
         if(this.panel) {
            ForgeDirection newFacing = this.getFacing();

            while(true) {
               newFacing = ForgeDirection.getOrientation(newFacing.ordinal() + 1);
               if(newFacing == ForgeDirection.UNKNOWN) {
                  newFacing = ForgeDirection.DOWN;
               }

               if(newFacing == this.getFacing() || BlockCarpentryPanel.isValidPanelPlacement(world, x, y, z, newFacing)) {
                  break;
               }
            }

            if(newFacing != this.getFacing()) {
               hammerI.onHammerUsed(hammer, player);
            }

            this.setFacing(newFacing);
         } else {
            if(dir != this.getFacing()) {
               hammerI.onHammerUsed(hammer, player);
            }

            this.setFacing(dir);
         }
      } else {
         ++this.rotation;
         hammerI.onHammerUsed(hammer, player);
      }

      if(this.rotation > 3) {
         this.rotation = 0;
      }

      if(this.rotation < 0) {
         this.rotation = 3;
      }

   }

   public void setFacing(ForgeDirection facing) {
      this.facing = facing;
   }

   public int getBlockMetadata(IDesignSystem system) {
      return ModuleCarpentry.getBlockMetadata(system, this);
   }

   public int getItemMetadata(IDesignSystem system) {
      return ModuleCarpentry.getItemMetadata(system, this);
   }

   public void setPanel() {
      this.panel = true;
   }

   public String getString() {
      String type = "";
      if(this.getPrimaryMaterial() != this.getSecondaryMaterial()) {
         type = this.getPrimaryMaterial().getName() + " and " + this.getSecondaryMaterial().getName();
      } else {
         type = this.getPrimaryMaterial().getName();
      }

      return super.toString() + " " + "{" + type + " " + this.getDesign().getName() + " " + (this.panel?"Panel":"Tile") + ", Facing:" + this.getFacing().toString() + ", Rotation:" + this.getRotation() + "}";
   }
}
