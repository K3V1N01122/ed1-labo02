package ed.lab;

import java.util.Comparator;

public class E03AVLTree<T> {

    private final Comparator<T> comparator;
    private AVLNode<T> root;
    private int size;

    public E03AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
        this.size = 0;
    }

    public void insert(T value) {
        root = insert(root, value);
    }

    private AVLNode<T> insert(AVLNode<T> node, T value) {
        if (node == null) {
            size++;
            return new AVLNode<>(value);
        }

        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = insert(node.left, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, value);
        } else {
            return node; // No se permiten duplicados
        }

        return balance(node);
    }

    public void delete(T value) {
        root = delete(root, value);
    }

    private AVLNode<T> delete(AVLNode<T> node, T value) {
        if (node == null) return null;

        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = delete(node.left, value);
        } else if (cmp > 0) {
            node.right = delete(node.right, value);
        } else {
            size--;
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            AVLNode<T> minLargerNode = getMin(node.right);
            node.value = minLargerNode.value;
            node.right = delete(node.right, node.value);
        }
        return balance(node);
    }

    public T search(T value) {
        AVLNode<T> node = searchNode(root, value);
        return node != null ? node.value : null;
    }

    private AVLNode<T> searchNode(AVLNode<T> node, T value) {
        if (node == null) return null;

        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) return searchNode(node.left, value);
        if (cmp > 0) return searchNode(node.right, value);
        return node;
    }

    public int height() {
        return getHeight(root);
    }

    private int getHeight(AVLNode<T> node) {
        return node == null ? 0 : node.height;
    }

    public int size() {
        return size;
    }

    private AVLNode<T> balance(AVLNode<T> node) {
        if (node == null) return null;

        updateHeight(node);
        int balanceFactor = getHeight(node.left) - getHeight(node.right);

        if (balanceFactor > 1) {
            if (getHeight(node.left.left) >= getHeight(node.left.right)) {
                node = rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }
        } else if (balanceFactor < -1) {
            if (getHeight(node.right.right) >= getHeight(node.right.left)) {
                node = rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
        }

        return node;
    }

    private void updateHeight(AVLNode<T> node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private AVLNode<T> rotateRight(AVLNode<T> y) {
        AVLNode<T> x = y.left;
        y.left = x.right;
        x.right = y;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private AVLNode<T> rotateLeft(AVLNode<T> x) {
        AVLNode<T> y = x.right;
        x.right = y.left;
        y.left = x;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    private AVLNode<T> getMin(AVLNode<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }
}

class AVLNode<T> {
    T value;
    AVLNode<T> left, right;
    int height;

    AVLNode(T value) {
        this.value = value;
        this.height = 1;
    }
}
