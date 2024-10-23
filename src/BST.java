/*****
 @file: BST.java
 ∗ @description: This program implements a Binary Search Tree (BST) data structure
 ∗ @author: Aashi Patel
 ∗ @date: October 24, 2024
 ****/

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

// BST class implements a generic Binary Search Tree data structure
// It supports basic operations such as insert, remove, search, and iteration
public class BST<T extends Comparable<T>> implements Iterable<T> {
    private Node root;
    private int size;

    // Nested Node class represents a node in the BST
    // Each node contains an element and references to its left and right child nodes
    public class Node {
        private T element;
        private Node left;
        private Node right;

        // Constructor for creating a new node with a given element
        public Node(T element) {
            this.element = element;
            this.left = null;
            this.right = null;
        }

        // Setter for updating the element in the node
        public void setElement(T element) {
            this.element = element;
        }

        // Setter for updating the left child of the node
        public void setLeft(Node left) {
            this.left = left;
        }

        // Setter for updating the right child of the node
        public void setRight(Node right) {
            this.right = right;
        }

        // Getter for accessing the left child of the node
        public Node getLeft() {
            return left;
        }
        // Getter for accessing the right child of the node
        public Node getRight() {
            return right;
        }
        // Getter for accessing the element stored in the node
        public T getElement() {
            return element;
        }

        // Getter and setter methods...
        // Checks if the node is a leaf (has no children)
        public boolean isLeaf() {
            return left == null && right == null;
        }
    }

    // Implement the constructor
    // Constructs an empty BST
    public BST() {
        root = null;
        size = 0;
    }

    // Removes all elements from the BST
    public void clear() {
        root = null;
        size = 0;
    }

    // Returns the number of elements in the BST
    public int size() {
        return size;
    }


    // Inserts a new element into the BST
    public void insert(T element) {
        root = insert(root, element);
        size++;
    }

    // Recursive helper method for insert
    private Node insert(Node node, T element) {
        if (node == null) {
            return new Node(element);
        }

        int cmp = element.compareTo(node.getElement());
        if (cmp < 0) {
            node.setLeft(insert(node.getLeft(), element));
        } else if (cmp > 0) {
            node.setRight(insert(node.getRight(), element));
        }

        return node;
    }

    // Removes an element from the BST
    public Node remove(T element) {
        Node removed = search(element);
        if (removed != null) {
            root = remove(root, element);
            size--;
        }
        return removed;
    }

    // Recursive helper method for remove
    private Node remove(Node node, T element) {
        if (node == null) {
            return null;
        }

        int cmp = element.compareTo(node.getElement());
        if (cmp < 0) {
            node.setLeft(remove(node.getLeft(), element));
        } else if (cmp > 0) {
            node.setRight(remove(node.getRight(), element));
        } else {
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }

            Node minNode = findMin(node.getRight());
            node.setElement(minNode.getElement());
            node.setRight(remove(node.getRight(), minNode.getElement()));
        }

        return node;
    }

    // Finds the minimum element in a subtree
    private Node findMin(Node node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    // Searches for an element in the BST
    public Node search(T element) {
        return search(root, element);
    }

    // Recursive helper method for search
    private Node search(Node node, T element) {
        if (node == null || element.equals(node.getElement())) {
            return node;
        }

        int cmp = element.compareTo(node.getElement());
        if (cmp < 0) {
            return search(node.getLeft(), element);
        } else {
            return search(node.getRight(), element);
        }
    }


    // Implement the iterator method
    // Returns an iterator over the elements in the BST in in-order traversal
    @Override
    public Iterator<T> iterator() {
        return new BSTIterator();
    }

    // Implement the BST Iterator class
    // BST Iterator class implements an in-order traversal iterator for the BST
    private class BSTIterator implements Iterator<T> {
        private Stack<Node> stack;

        // Constructs a new BSTIterator
        public BSTIterator() {
            stack = new Stack<>();
            pushLeft(root);
        }

        // Pushes all left children of a node onto the stack
        private void pushLeft (Node node){
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
        }

        // Checks if there are more elements in the iteration
        @Override
        public boolean hasNext () {
            return !stack.isEmpty();
        }

        // Returns the next element in the iteration
        @Override
        public T next () {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = stack.pop();
            pushLeft(node.getRight());
            return node.getElement();
        }
    }

}
