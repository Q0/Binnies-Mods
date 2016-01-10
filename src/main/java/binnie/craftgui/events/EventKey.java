package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;

public abstract class EventKey extends Event {
    char character;
    int key;

    public EventKey(IWidget origin, char character, int key) {
        super(origin);
        this.character = character;
        this.key = key;
    }

    public char getCharacter() {
        return this.character;
    }

    public int getKey() {
        return this.key;
    }

    public static class Down extends EventKey {
        public Down(IWidget origin, char character, int key) {
            super(origin, character, key);
        }

        public abstract static class Handler extends EventHandler {
            public Handler() {
                super(EventKey.Down.class);
            }
        }
    }

    public static class Up extends EventKey {
        public Up(IWidget origin, char character, int key) {
            super(origin, character, key);
        }

        public abstract static class Handler extends EventHandler {
            public Handler() {
                super(EventKey.Up.class);
            }
        }
    }
}
