package modules;

import java.util.Comparator;
import java.util.StringJoiner;
import java.lang.Math;

// welcome to hell
// you might wonder: hey! why is everything protected rather than public?
// in the DynamicSet.java file we have the methods defined with the exact same
// names and parameters as the spec, which are different to the *true* implementation in here.
// and it seems silly to have multiple methods to do the same thing.

public class RedBlackTree<T> {
    Comparator<? super T> comparator;
    Node root;
    Node nil;

    protected class Node {
        Node left;
        Node right;
        Node parent;
        T key;
        boolean isRed;

        public Node(T key) {
            this.isRed = false;
            this.key = key;
        }

        public String toString() {
            if (this.key == null) {
                return "null";
            }
            return this.key.toString();
        }
    }

    public RedBlackTree(Comparator<? super T> comparator) {
        this.nil = new Node(null);
        this.root = this.nil;
        this.comparator = comparator;
    }

    public boolean isNull(Node node) {
        return node == null || node.key == null;
    }

    protected Node search(Node node, T key) {
        if (isNull(node)) {
            return null;
        }

        if (comparator.compare(node.key, key) == 0) {
            return node;
        }

        if (comparator.compare(key, node.key) > 0) {
            return search(node.right, key);
        }

        return search(root.left, key);
    }

    protected Node search(T key) {
        return search(this.root, key);
    }

    private void fixDelete(Node node) {
        while (node != root && !node.isRed) {
            if (node == node.parent.left) {
                Node y = node.parent.right;

                if (y.isRed) {
                    y.isRed = false;
                    node.parent.isRed = true;
                    leftRotate(node.parent);
                    y = node.parent.right;
                }

                if (!node.left.isRed && !node.right.isRed) {
                    y.isRed = true;
                    node = node.parent;
                }
                else {
                    if (!y.right.isRed) {
                        y.left.isRed = false;
                        y.isRed = true;
                        rightRotate(y);
                        y = node.parent.right;
                    }

                    y.isRed = node.parent.isRed;
                    node.parent.isRed = false;
                    y.parent.isRed = false;
                    leftRotate(node.parent);
                    node = root;
                }
            }
            else {
                Node y = node.parent.left;

                if (y.isRed) {
                    y.isRed = false;
                    node.parent.isRed = true;
                    rightRotate(node.parent);
                    y = node.parent.left;
                }

                if (!y.right.isRed && !y.left.isRed) {
                    y.isRed = true;
                    node = node.parent;
                }
                else {
                    if (!y.left.isRed) {
                        y.right.isRed = false;
                        y.isRed = true;
                        leftRotate(y);
                        y = node.parent.left;
                    }

                    y.isRed = node.parent.isRed;
                    node.parent.isRed = false;
                    y.left.isRed = false;
                    rightRotate(node.parent);
                    node = root;
                }
            }
        }

        if (node != null) {
            node.isRed = false;
        }
    }

    private void transplant(Node x, Node y) {
        if (x.parent == null) {
            this.root = y;
        }
        else if (x == x.parent.left) {
            x.parent.left = y;
        }
        else {
            x.parent.right = y;
        }

        if (y != null) {
            y.parent = x.parent;
        }
    }

    protected void delete(Node node) {
        Node y = node;
        Node x;
        boolean yWasRed = y.isRed;

        if (node.left == null) {
            x = node.right;
            transplant(node, node.right);
        }
        else if (node.right == null) {
            x = node.left;
            transplant(node, node.left);
        }
        else {
            y = min(node.right);
            yWasRed = y.isRed;
            x = y.right;

            if (y.parent == node) {
                if (x != null) {
                    x.parent = y;
                }
            }
            else {
                transplant(y, y.right);
                y.right = node.right;
                y.right.parent = y;
            }

            transplant(node, y);
            y.left = node.left;
            y.left.parent = y;
            y.isRed = node.isRed;
        }

        if (!yWasRed) {
            fixDelete(node);
        }
    };

    private void leftRotate(Node node) {
        Node y = node.right;
        node.right = y.left;
        if (y.left != null) {
            y.left.parent = node;
        }
        y.parent = node.parent;
        if (node.parent == null) {
            this.root = y;
        }
        else if (node == node.parent.left) {
            node.parent.left = y;
        }
        else {
            node.parent.right = y;
        }
        y.left = node;
        node.parent = y;
    }

    private void rightRotate(Node node) {
        Node y = node.left;
        node.left = y.right;
        if (y.right != null) {
            y.right.parent = node;
        }
        y.parent = node.parent;
        if (node.parent == null) {
            this.root = y;
        }
        else if (node == node.parent.right) {
            node.parent.right = y;
        }
        else {
            node.parent.left = y;
        }
        y.right = node;
        node.parent = y;
    }

    private void fixInsert(Node node) {
        while (node.parent.isRed) {
            if (node.parent == node.parent.parent.left) {
                Node y = node.parent.parent.right;
                if (y.isRed) {
                    node.parent.isRed = false;
                    y.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                }
                else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    rightRotate(node.parent.parent);
                }
            }
            else {
                Node y = node.parent.parent.left;
                if (!y.isRed) {
                    node.parent.isRed = false;
                    y.isRed = false;
                    node.parent.parent.isRed = true;
                    node = node.parent.parent;
                }
                else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    leftRotate(node.parent.parent);
                }

            }
        }

        this.root.isRed = false;
    }

    protected void insert(Node node) {
        Node y = this.nil;
        Node x = this.root;

        while (x != nil) {
            y = x;
            if (comparator.compare(node.key, x.key) < 0) {
                x = x.left;
            }
            else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == nil) {
            this.root = node;
        }
        else if (comparator.compare(node.key, y.key) < 0) {
            y.left = node;
        }
        else if (comparator.compare(node.key, y.key) > 0) {
            y.right = node;
        }
        node.left = nil;
        node.right = nil;
        node.isRed = true;
        fixInsert(node);
    };

    protected void insert(T value) {
        insert(new Node(value));
    }

    protected int height(Node startNode) {
        if (isNull(startNode)) {
            return -1;
        }
        return Math.max(height(startNode.left), height(startNode.right)) + 1;
    }

    protected int height() {
        return height(this.root);
    }

    protected int size(Node startNode) {
        if (isNull(startNode)) {
            return 0;
        }
        return size(startNode.left) + size(startNode.right) + 1;
    }

    protected int size() {
        return size(this.root);
    }

    protected Node predecessor(Node startNode) {
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

    protected Node successor(Node startNode) {
        if (startNode.right != null) {
            return min(startNode.right);
        }
        while (startNode.parent != null && startNode == startNode.parent.right) {
            startNode = startNode.parent;
        }

        return startNode.parent;
    }

    protected Node min(Node startNode) {
        while (!isNull(startNode.left)) {
            startNode = startNode.left;
        } 
        return startNode;
    }

    protected Node min() {
        return min(this.root);
    };

    protected Node max(Node startNode) {
        while (!isNull(startNode.right)) {
            startNode = startNode.right;
        }
        return startNode;
    }

    protected Node max() {
        return max(this.root);
    }

    public String toString() {
        StringJoiner output = new StringJoiner(",");
        Node curNode = min(this.root);
        System.out.println(curNode);
        while (!isNull(curNode)) {
            output.add(curNode.toString());
            curNode = successor(curNode);
        }
        return output.toString();
    }
}
