package com.database.structure;

public interface NodeStructure {
    
    /**
     * Adds a new user account.
     * as it needs both username and password.
     * @param username The username/email.
     * @param password The password.
     * @return true if added, false if username already exists.
     */
    boolean addAccount(String username, String password);

    /**
     * Gets the password for a user.
     * @param username The username to look up.
     * @return The password, or null if not found.
     */
    String getPassword(String username);

    /**
     * Deletes a user account.
     * (Using your original requested method name 'deleteAcc')
     * @param username The username to delete.
     * @return true if deleted, false if user not found.
     */
    boolean deleteAcc(String username);

    /**
     * Checks if a username exists.
     * @param username The username to check.
     * @return true if the user exists.
     */
    boolean userExists(String username);

    /**
     * Checks if any username in the database starts with the given prefix.
     * <p>
     * This method is intended for real-time validation, allowing a front-end to check if a partial username (e.g., "raj") is a valid  start for any existing user (e.g., "rajesh") as the user is typing.
     *
     * @param prefix The partial username string to check.
     * @return true if one or more usernames start with this prefix,
     * false if no usernames begin with this string.
     */
    public boolean isPrefix(String prefix);
}