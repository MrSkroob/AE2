package modules;

import java.util.Comparator;

public class RedBlackTree<T> extends Tree<T> {
    public RedBlackTree(Comparator<? super T> comparator) {
        super(comparator);
    }

    protected class Node extends Tree<T>.Node {
        // true = red
        // false = black
        boolean colour;

        public Node(T key) {
            super(key);
            this.colour = false;
        }

        public void setColour(boolean colour) {
            this.colour = colour;
        }

        public boolean getColour() {
            return colour;
        }

    }

    @Override
    public void insert(Node node) {
        Node y = this.nil;
        Node x = this.root;

        while (x != this.nil) {
            y = x;
            if (comparator.compare(node.key, x.key) < 0) {
                x = x.left;
            }
            else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == this.nil) {
            this.root = node;
        }
        else if (comparator.compare(node.key, y.key) < 0) {
            y.left = node;
        }
        else {
            y.right = node;
        }
        node.left = this.nil;
        node.right = this.nil;
        node.setColour(true);
    }

    @Override
    public void delete(Tree<T>.Node node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void insert(Tree<T>.Node node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }
} 