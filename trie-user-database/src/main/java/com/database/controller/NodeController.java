package com.database.controller;

import com.database.node.Node;
import com.database.structure.NodeStructure;
/**
 * Manages the user database using a Trie structure.
*/
public class NodeController implements NodeStructure {

    private final Node root;

    /**
     * Constructor initializes the database.
    */
    public NodeController() {
        this.root = new Node();
    }

    /**
     * Adds a new user and password to the database.
     * @param username The email or username.
     * @param password The password to store.
     * @return true if the user was added, false if the username already exists.
     */
    @Override
    public boolean addAccount(String username, String password) {
        Node current = root;

        for (char ch : username.toCharArray()) {
            if (!current.hasChild(ch)) {
                current = current.addChild(ch);
            } else {
                current = current.getChild(ch);
            }
        }

        if (current.isEndOfUsername()) {
            /* Username already exists */ 
            return false; 
        } else {
            current.setPassword(password);
            return true;
        }
    }

    /**
     * Retrieves the password for a given username.
     * @param username The username to look up.
     * @return The password string, or null if the username doesn't exist.
     */
    @Override
    public String getPassword(String username) {
        Node node = findNode(username);
        if (node != null && node.isEndOfUsername()) {
            return node.getPassword();
        }
        /**Username not found or no password set  */
        return null; 
    }

    /**
     * Checks if a username exists in the database.
     * @param username The username to check.
     * @return true if it exists, false otherwise.
     */
    @Override
    public boolean userExists(String username) {
        Node node = findNode(username);
        return node != null && node.isEndOfUsername();
    }

    /**
     * Deletes a user account.
     * @param username The username to delete.
     * @return true if the account was deleted, false if it didn't exist.
     */
    @Override
    public boolean deleteAcc(String username) {
        Node node = findNode(username);
        if (node == null || !node.isEndOfUsername()) {
            return false; // User doesn't exist
        }

        // User exists, remove their password.
        node.setPassword(null);
        return true;
    }


    /**
     * Helper method to traverse the trie and find the node corresponding to the end of the username string.
     *
     * @param username The username to search for.
     * @return The Node at the end of the string, or null if not found.
     */
    private Node findNode(String username) {
        Node current = root;
        for (char ch : username.toCharArray()) {
            if (current.hasChild(ch)) {
                current = current.getChild(ch);
            } else {
                return null; // Path doesn't exist
            }
        }
        return current;
    }


    /**
     * Checks if any username in the database starts with the given prefix. (For runtime checking / real-time validation)
     * @param prefix The partial username to check.
     * @return true if any user starts with this prefix, false otherwise.
     */
    @Override
    public boolean isPrefix(String prefix) {
        // We just need to find if the path (prefix) exists.
        return findNode(prefix) != null;
    }
}