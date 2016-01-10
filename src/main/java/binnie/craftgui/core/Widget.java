package binnie.craftgui.core;

import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITopLevelWidget;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.IWidgetAttribute;
import binnie.craftgui.core.RenderStage;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.Event;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventWidget;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Widget implements IWidget {
   private IWidget parent = null;
   private List subWidgets = new ArrayList();
   private List attributes = new ArrayList();
   private IPoint position = new IPoint(0.0F, 0.0F);
   private IPoint size = new IPoint(0.0F, 0.0F);
   private IPoint offset = new IPoint(0.0F, 0.0F);
   IArea cropArea;
   IWidget cropWidget;
   boolean cropped = false;
   int colour = 16777215;
   private Collection globalEventHandlers = new ArrayList();
   private boolean enabled = true;
   private boolean visible = true;

   public Widget(IWidget parent) {
      super();
      this.parent = parent;
      if(parent != null) {
         parent.addWidget(this);
      }

   }

   public List getAttributes() {
      return this.attributes;
   }

   public boolean hasAttribute(IWidgetAttribute attribute) {
      return this.attributes.contains(attribute);
   }

   public boolean addAttribute(IWidgetAttribute attribute) {
      return this.attributes.add(attribute);
   }

   public final void deleteChild(IWidget child) {
      if(child != null) {
         child.delete();
         this.subWidgets.remove(child);
      }
   }

   public final void deleteAllChildren() {
      while(!this.subWidgets.isEmpty()) {
         this.deleteChild((IWidget)this.subWidgets.get(0));
      }

   }

   public final IWidget getParent() {
      return this.parent;
   }

   public final ITopLevelWidget getSuperParent() {
      return this.isTopLevel()?(ITopLevelWidget)this:this.parent.getSuperParent();
   }

   public final IWidget addWidget(IWidget widget) {
      if(this.subWidgets.size() != 0 && ((IWidget)this.subWidgets.get(this.subWidgets.size() - 1)).hasAttribute(Attribute.AlwaysOnTop)) {
         this.subWidgets.add(this.subWidgets.size() - 1, widget);
      } else {
         this.subWidgets.add(widget);
      }

      this.onAddChild(widget);
      return widget;
   }

   protected void onAddChild(IWidget widget) {
   }

   public final List getWidgets() {
      return this.subWidgets;
   }

   public final boolean isTopLevel() {
      return this instanceof ITopLevelWidget;
   }

   public final IPoint pos() {
      return this.position.add(this.offset);
   }

   public final IPoint size() {
      return this.size;
   }

   public final IArea area() {
      return this.getArea();
   }

   public final IPoint getPosition() {
      return this.pos();
   }

   public final IArea getArea() {
      return new IArea(IPoint.ZERO, this.size());
   }

   public final IPoint getOriginalPosition() {
      return this.position;
   }

   public IArea getCroppedZone() {
      return this.cropArea;
   }

   public void setCroppedZone(IWidget relative, IArea area) {
      this.cropArea = area;
      this.cropped = true;
      this.cropWidget = relative;
   }

   public final IPoint getAbsolutePosition() {
      return this.isTopLevel()?this.getPosition():this.getParent().getAbsolutePosition().add(this.getPosition());
   }

   public final IPoint getOriginalAbsolutePosition() {
      return this.isTopLevel()?this.getOriginalPosition():this.getParent().getOriginalPosition().sub(this.getOriginalPosition());
   }

   public final IPoint getSize() {
      return this.size();
   }

   public final IPoint getOffset() {
      return this.offset;
   }

   public final void setPosition(IPoint vector) {
      if(!vector.equals(this.position)) {
         this.position = new IPoint(vector);
         this.callEvent(new EventWidget.ChangePosition(this));
      }

   }

   public final void setSize(IPoint vector) {
      if(!vector.equals(this.size)) {
         this.size = new IPoint(vector);
         this.callEvent(new EventWidget.ChangeSize(this));
      }

   }

   public final void setOffset(IPoint vector) {
      if(vector != this.offset) {
         this.offset = new IPoint(vector);
         this.callEvent(new EventWidget.ChangeOffset(this));
      }

   }

   public final void setColour(int colour) {
      if(this.colour != colour) {
         this.colour = colour;
         this.callEvent(new EventWidget.ChangeColour(this));
      }

   }

   public final int getColour() {
      return this.colour;
   }

   public boolean canMouseOver() {
      return this.hasAttribute(Attribute.MouseOver);
   }

   public boolean canFocus() {
      return this.hasAttribute(Attribute.CanFocus);
   }

   public void addEventHandler(EventHandler handler) {
      this.globalEventHandlers.add(handler);
   }

   public void addSelfEventHandler(EventHandler handler) {
      this.addEventHandler(handler.setOrigin(EventHandler.Origin.Self, this));
   }

   public final void callEvent(Event event) {
      this.getSuperParent().recieveEvent(event);
   }

   public final void recieveEvent(Event event) {
      for(EventHandler handler : this.globalEventHandlers) {
         if(handler.handles(event)) {
            handler.onEvent(event);
         }
      }

      try {
         for(IWidget child : this.getWidgets()) {
            child.recieveEvent(event);
         }

      } catch (ConcurrentModificationException var4) {
         ;
      }
   }

   public final IPoint getMousePosition() {
      return this.getSuperParent().getAbsoluteMousePosition();
   }

   public final IPoint getRelativeMousePosition() {
      return this.isTopLevel()?this.getMousePosition():this.getParent().getRelativeMousePosition().sub(this.getPosition());
   }

   public boolean isCroppedWidet() {
      return this.cropped;
   }

   public final IWidget getCropWidget() {
      return (IWidget)(this.cropWidget == null?this:this.cropWidget);
   }

   public final void render() {
      if(this.isVisible()) {
         CraftGUI.Render.preRender(this);
         this.onRender(RenderStage.PreChildren);

         for(IWidget widget : this.getWidgets()) {
            widget.render();
         }

         for(IWidget widget : this.getWidgets()) {
            CraftGUI.Render.preRender(widget);
            widget.onRender(RenderStage.PostSiblings);
            CraftGUI.Render.postRender(widget);
         }

         this.onRender(RenderStage.PostChildren);
         CraftGUI.Render.postRender(this);
      }

   }

   public final void updateClient() {
      if(this.isVisible()) {
         if(this.getSuperParent() == this) {
            ((ITopLevelWidget)this).updateTopLevel();
         }

         this.onUpdateClient();
         List<IWidget> deletedWidgets = new ArrayList();

         for(IWidget widget : this.getWidgets()) {
            if(widget.hasAttribute(Attribute.NeedsDeletion)) {
               deletedWidgets.add(widget);
            } else {
               widget.updateClient();
            }
         }

         for(IWidget widget : deletedWidgets) {
            this.deleteChild(widget);
         }

      }
   }

   public final boolean calculateIsMouseOver() {
      IPoint mouse = this.getRelativeMousePosition();
      if(!this.cropped) {
         return this.isMouseOverWidget(mouse);
      } else {
         IWidget cropRelative = (IWidget)(this.cropWidget != null?this.cropWidget:this);
         IPoint pos = IPoint.sub(cropRelative.getAbsolutePosition(), this.getAbsolutePosition());
         IPoint size = new IPoint(this.cropArea.size().x(), this.cropArea.size().y());
         boolean inCrop = mouse.x() > pos.x() && mouse.y() > pos.y() && mouse.x() < pos.x() + size.x() && mouse.y() < pos.y() + size.y();
         return inCrop && this.isMouseOverWidget(mouse);
      }
   }

   public boolean isMouseOverWidget(IPoint relativeMouse) {
      return this.getArea().contains(relativeMouse);
   }

   public final void enable() {
      this.enabled = true;
      this.callEvent(new EventWidget.Enable(this));
   }

   public final void disable() {
      this.enabled = false;
      this.callEvent(new EventWidget.Disable(this));
   }

   public final void show() {
      this.visible = true;
      this.callEvent(new EventWidget.Show(this));
   }

   public final void hide() {
      this.visible = false;
      this.callEvent(new EventWidget.Hide(this));
   }

   public boolean isEnabled() {
      return this.enabled && (this.isTopLevel() || this.getParent().isEnabled() && this.getParent().isChildEnabled(this));
   }

   public final boolean isVisible() {
      return this.visible && (this.isTopLevel() || this.getParent().isVisible() && this.getParent().isChildVisible(this));
   }

   public final boolean isFocused() {
      return this.getSuperParent().isFocused(this);
   }

   public final boolean isDragged() {
      return this.getSuperParent().isDragged(this);
   }

   public final boolean isMouseOver() {
      return this.getSuperParent().isMouseOver(this);
   }

   public boolean isChildVisible(IWidget child) {
      return true;
   }

   public boolean isChildEnabled(IWidget child) {
      return true;
   }

   public void onRender(RenderStage stage) {
      if(stage == RenderStage.PreChildren) {
         this.onRenderBackground();
      }

      if(stage == RenderStage.PostChildren) {
         this.onRenderForeground();
      }

      if(stage == RenderStage.PostSiblings) {
         this.onRenderOverlay();
      }

   }

   public void onRenderBackground() {
   }

   public void onRenderForeground() {
   }

   public void onRenderOverlay() {
   }

   public void onUpdateClient() {
   }

   public final void delete() {
      this.getSuperParent().widgetDeleted(this);
      this.onDelete();
   }

   public void onDelete() {
   }

   public Object getWidget(Class x) {
      for(IWidget child : this.getWidgets()) {
         if(x.isInstance(child)) {
            return child;
         }

         T found = child.getWidget(x);
         if(found != null) {
            return found;
         }
      }

      return null;
   }

   public final boolean contains(IPoint position) {
      return this.getArea().contains(position);
   }

   public void scheduleDeletion() {
      this.addAttribute(Attribute.NeedsDeletion);
   }

   public int getLevel() {
      int level = this.getParent() == null?0:this.getParent().getLevel();
      int index = this.getParent() == null?0:this.getParent().getWidgets().indexOf(this);
      return level + index;
   }

   public boolean isDescendant(IWidget widget) {
      IWidget clss = this;

      while(clss != widget) {
         clss = clss.getParent();
         if(clss == null) {
            return false;
         }
      }

      return true;
   }

   public float x() {
      return this.pos().x();
   }

   public float y() {
      return this.pos().y();
   }

   public float w() {
      return this.size().x();
   }

   public float h() {
      return this.size().y();
   }

   public IWidget getWidget() {
      return this;
   }
}
