package com.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.database.controller.NodeController;
import com.database.structure.NodeStructure;

/**
 * Test suite for the NodeController (Trie-based database).
 */
public class NodeControllerTest {

    private NodeStructure userDB;

    @BeforeEach
    public void setUp() {
        userDB = new NodeController();
    }

    @Test
    public void testAddAndGetUser_Success() {
        // 1. Add a new user
        boolean added = userDB.addAccount("raj@email.com", "pass123");
        
        // 2. Verify it was added successfully
        assertTrue(added, "Should return true on successful add");
        
        // 3. Verify the user now exists
        assertTrue(userDB.userExists("raj@email.com"), "User should exist after being added");
        
        // 4. Verify the password can be retrieved
        assertEquals("pass123", userDB.getPassword("raj@email.com"), "Password should match");
    }

    @Test
    public void testAddUser_AlreadyExists() {
        // 1. Add a user
        userDB.addAccount("user@test.com", "pass1");
        
        // 2. Try to add the same user again
        boolean addedAgain = userDB.addAccount("user@test.com", "pass2");
        
        // 3. Verify it returned false
        assertFalse(addedAgain, "Should return false when adding a duplicate user");
        
        // 4. Verify the original password is still intact
        assertEquals("pass1", userDB.getPassword("user@test.com"), "Original password should not be overwritten");
    }

    @Test
    public void testGetPassword_UserNotFound() {
        userDB.addAccount("user@test.com", "pass1");
        
        assertNull(userDB.getPassword("nonexistent@user.com"), "Should return null for a user that does not exist");
    }

    @Test
    public void testUserExists_False() {
        assertFalse(userDB.userExists("nouser@here.com"), "Should return false for an empty database");
        
        userDB.addAccount("user@test.com", "pass1");
        assertFalse(userDB.userExists("nouser@here.com"), "Should return false for a user that does not exist");
    }

    @Test
    public void testDeleteAccount_Success() {
        String username = "user-to-delete@test.com";
        userDB.addAccount(username, "deleteMe");
        
        // Verify user exists before delete
        assertTrue(userDB.userExists(username), "User should exist before delete");
        
        // Delete the user
        boolean deleted = userDB.deleteAcc(username);
        assertTrue(deleted, "deleteAcc should return true for a successful deletion");
        
        // Verify user no longer exists
        assertFalse(userDB.userExists(username), "User should not exist after being deleted");
        
        // Verify the password is gone
        assertNull(userDB.getPassword(username), "Password should be null after deletion");
    }

    @Test
    public void testDeleteAccount_NotFound() {
        // Try to delete a user that doesn't exist
        boolean deleted = userDB.deleteAcc("nonexistent@user.com");
        assertFalse(deleted, "deleteAcc should return false for a user that does not exist");
    }

    @Test
    public void testIsPrefix_ForRuntimeCheck() {
        // 1. Add multiple users with common prefixes
        userDB.addAccount("raj@email.com", "pass1");
        userDB.addAccount("rajesh@email.com", "pass2");
        userDB.addAccount("ram@email.com", "pass3");
        
        // 2. Test valid prefixes
        assertTrue(userDB.isPrefix("r"), "Should be a prefix");
        assertTrue(userDB.isPrefix("ra"), "Should be a prefix");
        assertTrue(userDB.isPrefix("raj"), "Should be a prefix for both raj and rajesh");
        assertTrue(userDB.isPrefix("ram"), "Should be a prefix");
        
        // 3. Test a full username (a full username is also a prefix)
        assertTrue(userDB.isPrefix("raj@email.com"), "A full user is also a prefix");

        // 4. Test invalid prefixes
        assertFalse(userDB.isPrefix("z"), "Should not be a prefix");
        assertFalse(userDB.isPrefix("rax"), "Should not be a prefix");
        assertFalse(userDB.isPrefix("rajesh@email.com-extra"), "Should not be a prefix");
    }

    @Test
    public void testPrefix_AfterDelete() {
        // 1. Add two users
        userDB.addAccount("raj", "pass1");
        userDB.addAccount("rajesh", "pass2");
        
        // 2. Verify "raj" is a prefix
        assertTrue(userDB.isPrefix("raj"), "Prefix should exist");
        
        // 3. Delete the shorter user
        userDB.deleteAcc("raj");
        
        // 4. Verify "raj" is *still* a prefix (because "rajesh" exists)
        assertTrue(userDB.isPrefix("raj"), "Prefix should still exist for 'rajesh'");
        
        // 5. Verify the "raj" user is gone, but the prefix remains
        assertFalse(userDB.userExists("raj"), "User 'raj' should be deleted");
        assertTrue(userDB.userExists("rajesh"), "User 'rajesh' should still exist");
    }
}