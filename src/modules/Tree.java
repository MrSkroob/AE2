package modules;

import java.util.Comparator;
import java.lang.Math;

public abstract class Tree<T> {
    Comparator<? super T> comparator;
    Node head;

    protected class Node {
        Node left;
        Node right;
        Node parent;
        T key;

        public Node(T key) {
            this.key = key;
        }
    }

    public Tree(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    protected abstract void delete(Node node);
    protected abstract void insert(Node node);
    public void insert(T value) {
        insert(new Node(value));
    }

    public int height(Node startNode) {
        if (startNode == null) {
            return -1;
        }
        return Math.max(height(startNode.left), height(startNode.right)) + 1;
    }

    public int height() {
        return height(this.head);
    }

    public int size(Node startNode) {
        if (startNode == null) {
            return 0;
        }
        return size(startNode.left) + size(startNode.right) + 1;
    }

    public int size() {
        return size(this.head);
    }

    public Node predecessor(Node startNode) {
        if (startNode.left != null) {
            return max(startNode.left);
        }
        Node parentNode = startNode.parent;
        while (parentNode != null && startNode == parentNode.left) {
            startNode = parentNode;
            parentNode = parentNode.parent;
        }
        return parentNode;
    }

    public Node successor(Node startNode) {
        if (startNode.right != null) {
            return min(startNode.right);
        }
        Node parentNode = startNode.parent;
        while (parentNode != null && startNode == parentNode.right) {
            startNode = parentNode;
            parentNode = parentNode.parent;
        }
        return parentNode;
    }

    public Node min(Node startNode) {
        while (startNode.left != null) {
            startNode = startNode.left;
        } 
        return startNode;
    }

    public Node min() {
        return min(this.head);
    };

    public Node max(Node startNode) {
        while (startNode.right != null) {
            startNode = startNode.right;
        }
        return startNode;
    }

    public Node max() {
        return max(this.head);
    }
}
