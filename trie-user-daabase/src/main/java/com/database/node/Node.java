package com.database.node; // Using your original package

import java.util.HashMap;
import java.util.Map;

/**
 * Represents one node in the Trie (Prefix Tree).
 * (Using your original requested class name 'Node')
 */
public class Node { // Renamed from TrieNode

    // Using your original field name 'childrens'
    private final Map<Character, Node> childrens;
    
    // Stores the password *if* this node is the end of a valid username.
    private String password;

    /**
     * Constructor for a new Node.
     */
    public Node() {
        // Initialize the map.
        this.childrens = new HashMap<>(); // Renamed from children
        this.password = null; // No password by default
    }

    // --- Helper Methods ---

    public boolean hasChild(char ch) {
        return this.childrens.containsKey(ch); // Renamed from children
    }

    public Node getChild(char ch) {
        return this.childrens.get(ch); // Renamed from children
    }

    /**
     * Adds a new child node for the given character
     * and returns the new child node.
     */
    public Node addChild(char ch) {
        Node newChild = new Node();
        this.childrens.put(ch, newChild); // Renamed from children
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
        return this.childrens.isEmpty(); // Renamed from children
    }

    // Used for deleting.
    public void removeChild(char ch) {
        this.childrens.remove(ch); // Renamed from children
    }
}