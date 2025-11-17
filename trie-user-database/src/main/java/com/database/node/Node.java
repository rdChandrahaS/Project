package com.database.node;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents one node in the Trie (Prefix Tree).
 */
public class Node {

    private final Map<Character, Node> childrens;
    
    // Stores the password *if* this node is the end of a valid username.
    private String password;

    /**
     * Constructor for a new Node.
     */
    public Node() {
        // Initialize the map.
        this.childrens = new HashMap<>();
        this.password = null; // No password by default
    }

    /* --- Helper Methods --- */

    public boolean hasChild(char ch) {
        return this.childrens.containsKey(ch);
    }

    public Node getChild(char ch) {
        return this.childrens.get(ch);
    }

    /**
     * Adds a new child node for the given character and returns the new child node.
     */
    public Node addChild(char ch) {
        Node newChild = new Node();
        this.childrens.put(ch, newChild);
        return newChild;
    }

    public boolean isEndOfUsername() {
        return this.password != null;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Used for deleting. If a node has no children, it can be pruned.
    public boolean hasNoChildren() {
        return this.childrens.isEmpty();
    }

    // Used for deleting.
    public void removeChild(char ch) {
        this.childrens.remove(ch);
    }
}