public class BinaryTree<E extends Comparable<E>> {

    private static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node<E> root;

    public BinaryTree() {
        this.root = null;
    }

    private Node<E> insertRecursive(Node<E> node, E data) {
        if (node == null) {
            return new Node<>(data);
        }

        int compare = data.compareTo(node.data);

        if (compare < 0) {
            node.left = insertRecursive(node.left, data);
        } else if (compare > 0) {
            node.right = insertRecursive(node.right, data);
        }

        // if compare == 0, do nothing. No need for duplicates

        return node;
    }

    public void insert(E data) {
        this.root = insertRecursive(this.root, data);
    }

    private E searchRecursive(Node<E> node, E data) {
        if (node == null) {
            return null;
        }

        int compare = data.compareTo(node.data);

        if (compare == 0) {
            return node.data;
        } else if (compare < 0) {
            return searchRecursive(node.left, data);
        } else {
            return searchRecursive(node.right, data);
        }
    }

    public E search(E data) {
        return searchRecursive(this.root, data);
    }

    public void inOrder() {
        inOrderRecursive(this.root);
        System.out.println();
    }

    private void inOrderRecursive(Node<E> node) {
        if (node == null) return;

        inOrderRecursive(node.left);
        System.out.print(node.data + " ");
        inOrderRecursive(node.right);

    }

    public boolean isEmpty() {
        return this.root == null;
    }
}


