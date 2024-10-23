/*****
 @file: Node.java
 ∗ @description: This program implements a generic Node for binary tree structures,
  *               supporting basic operations like setting/getting children, retrieving
  *               the stored element, and checking if the Node is a leaf.
 ∗ @author: Aashi Patel
 ∗ @date: October 24, 2024
 ****/
public class Node<T extends Comparable<T>> {
    private T element; // The element stored in the node
    private Node<T> left; // Reference to the left child node
    private Node<T> right; // Reference to the right child node

    // Constructs a new Node with the given element and initializes the left and right children to null
    public Node(T element) {
        this.element = element;
        this.left = null;
        this.right = null;
    }

    // Sets the element stored in this Node to the given value
    public void setElement(T element) {
        this.element = element;
    }

    // Sets the left child of this Node to the given Node
    public void setLeft(Node<T> left) {
        this.left = left;
    }

    // Sets the right child of this Node to the given Node
    public void setRight(Node<T> right) {
        this.right = right;
    }

    // Sets the left child of this Node to the given Node
    public Node<T> getLeft() {
        return left;
    }

    // Returns the right child of this Node
    public Node<T> getRight() {
        return right;
    }

    // Returns the element stored in this Node
    public T getElement() {
        return element;
    }

    // Checks if this Node is a leaf (has no children)
    public boolean isLeaf() {
        return left == null && right == null;
    }
}