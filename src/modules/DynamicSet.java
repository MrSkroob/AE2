package modules;

import java.util.Comparator;

public class DynamicSet<T> extends RedBlackTree<T> {
    public DynamicSet(Comparator<? super T> comparator) {
        super(comparator);
    }

    public void add(T x) {
        insert(x);
    }

    public void remove(T x) {
        Node node = search(x);
        delete(node);
    }

    public boolean isElement(T x) {
        return search(x) != null;
    }

    public boolean setEmpty() {
        return size() == 0;
    }

    public int setSize() {
        return size();
    }
}
