package binnie.craftgui.core;

import binnie.craftgui.core.IWidgetAttribute;

public enum Attribute implements IWidgetAttribute {
   MouseOver,
   CanFocus,
   NeedsDeletion,
   AlwaysOnTop,
   BlockTooltip;

   private Attribute() {
   }
}
