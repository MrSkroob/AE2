package modules;

import java.util.Comparator;

public class DynamicSet<T> extends RedBlackTree<T> {
    public DynamicSet(Comparator<? super T> comparator) {
        super(comparator);
    }

    public void add(T x) {
        if (isElement(x)) return;
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

    public DynamicSet<T> union(DynamicSet<T> set) {
        DynamicSet<T> newSet = new DynamicSet<>(comparator);
        Node curNode = this.root;
        while (curNode != null) {
            newSet.add(curNode.key);
            curNode = successor(curNode);
        }

        return newSet;
    }

    public DynamicSet<T> intersection(DynamicSet<T> set) {
        DynamicSet<T> newSet = new DynamicSet<>(comparator);
        Node curNode = this.root;
        while (curNode != null) {
            if (set.isElement(curNode.key)) {
                // the BST assumes that you're trying to insert the node
                // ITSELF.
                // so if we ever decide to do anything to the node, 
                // it'll affect all trees which have that node -
                // a very nasty and tricky to debug bug. 
                newSet.insert(curNode.key);
            }
            curNode = successor(curNode);
        }

        return newSet;
    }

    public DynamicSet<T> difference(DynamicSet<T> set) {
        DynamicSet<T> newSet = new DynamicSet<>(comparator);
        DynamicSet<T> intersection = intersection(set);

        // add all nodes not in the intersection from THIS set.
        Node curNode = this.root;
        while (curNode != null) {
            if (!intersection.isElement(curNode.key)) {
                newSet.insert(curNode.key);
            }
            curNode = successor(curNode);
        }

        // add nodes from OTHER set.
        curNode = set.root;
        while (curNode != null) {
            if (!intersection.isElement(curNode.key)) {
                newSet.insert(curNode.key);
            }
            curNode = set.successor(curNode);
        }

        return newSet;
    }

    public boolean subset(DynamicSet<T> set) {
        // assuming these sets are separate objects completely...
        Node curNode = this.root;
        while (curNode != null) {
            if (!set.isElement(curNode.key)) {
                return false;
            }
        }
        return true;
    }
}
