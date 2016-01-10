package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;

public abstract class EventHandler {
    Class eventClass;
    EventHandler.Origin origin = EventHandler.Origin.Any;
    IWidget relative = null;

    public EventHandler(Class eventClass) {
        super();
        this.eventClass = eventClass;
    }

    public EventHandler setOrigin(EventHandler.Origin origin, IWidget relative) {
        this.origin = origin;
        this.relative = relative;
        return this;
    }

    public abstract void onEvent(Event var1);

    public final boolean handles(Event e) {
        return this.eventClass.isInstance(e) && this.origin.isOrigin(e.getOrigin(), this.relative);
    }

    public static enum Origin {
        Any,
        Self,
        Parent,
        DirectChild;

        private Origin() {
        }

        public boolean isOrigin(IWidget origin, IWidget test) {
            switch (this) {
                case Any:
                    return true;
                case DirectChild:
                    return test.getWidgets().contains(origin);
                case Parent:
                    return test.getParent() == origin;
                case Self:
                    return test == origin;
                default:
                    return false;
            }
        }
    }
}
