package binnie.craftgui.minecraft;

import java.util.*;
import java.util.Map.Entry;

class ListMap implements List {
    private LinkedHashMap map = new LinkedHashMap();

    ListMap() {
        super();
    }

    public int size() {
        int i = -1;
        Iterator i$ = this.map.keySet().iterator();

        while (i$.hasNext()) {
            int k = ((Integer) i$.next()).intValue();
            if (k > i) {
                i = k;
            }
        }

        return i + 1;
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public boolean contains(Object o) {
        return this.map.containsValue(o);
    }

    public Iterator iterator() {
        return this.map.values().iterator();
    }

    public Object[] toArray() {
        return this.map.values().toArray();
    }

    public Object[] toArray(Object[] a) {
        return this.map.values().toArray(a);
    }

    public boolean add(Object e) {
        if (this.get(this.size()) == null) {
            this.add(this.size(), e);
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean containsAll(Collection c) {
        return this.map.values().containsAll(c);
    }

    public boolean addAll(Collection c) {
        return false;
    }

    public boolean addAll(int index, Collection c) {
        return false;
    }

    public boolean removeAll(Collection c) {
        return false;
    }

    public boolean retainAll(Collection c) {
        return false;
    }

    public void clear() {
        this.map.clear();
    }

    public Object get(int index) {
        return this.map.get(Integer.valueOf(index));
    }

    public Object set(int index, Object element) {
        this.map.put(Integer.valueOf(index), element);
        return element;
    }

    public void add(int index, Object element) {
        this.map.put(Integer.valueOf(index), element);
    }

    public Object remove(int index) {
        return null;
    }

    public int indexOf(Object o) {
        for (Entry<Integer, T> entry : this.map.entrySet()) {
            if (entry.getValue() == o) {
                return ((Integer) entry.getKey()).intValue();
            }
        }

        return 0;
    }

    public int lastIndexOf(Object o) {
        return this.indexOf(o);
    }

    public ListIterator listIterator() {
        return null;
    }

    public ListIterator listIterator(int index) {
        return null;
    }

    public List subList(int fromIndex, int toIndex) {
        return null;
    }
}
