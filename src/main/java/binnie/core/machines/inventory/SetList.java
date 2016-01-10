package binnie.core.machines.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class SetList extends ArrayList implements Set {
    private static final long serialVersionUID = 1277112003159980135L;

    public SetList() {
        super();
    }

    public boolean add(Object e) {
        return this.contains(e) ? false : super.add(e);
    }

    public void add(int index, Object e) {
        if (!this.contains(e)) {
            super.add(index, e);
        }

    }

    public boolean addAll(Collection c) {
        return this.addAll(this.size(), c);
    }

    public boolean addAll(int index, Collection c) {
        Collection<E> copy = new ArrayList(c);
        copy.removeAll(this);
        return super.addAll(index, copy);
    }
}
