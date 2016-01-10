package binnie.extratrees.api;

import binnie.extratrees.api.ILayout;

public interface IDesign {
   String getName();

   ILayout getTopPattern();

   ILayout getBottomPattern();

   ILayout getNorthPattern();

   ILayout getEastPattern();

   ILayout getSouthPattern();

   ILayout getWestPattern();
}
