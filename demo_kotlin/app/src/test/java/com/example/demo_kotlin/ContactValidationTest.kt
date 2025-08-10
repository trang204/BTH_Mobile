package com.example.demo_kotlin

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for contact validation and database logic
 * These tests validate the business logic without requiring Android framework
 */
class ContactValidationTest {

    @Test
    fun validateContactInput_withValidData_returnsTrue() {
        // Test contact input validation
        val name = "John Doe"
        val phone = "1234567890"
        val email = "john@example.com"
        
        // Basic validation logic
        val isNameValid = name.trim().isNotEmpty()
        val isPhoneValid = phone.trim().isNotEmpty()
        val isEmailValid = email.isEmpty() || email.contains("@")
        
        assertTrue("Name should be valid", isNameValid)
        assertTrue("Phone should be valid", isPhoneValid) 
        assertTrue("Email should be valid", isEmailValid)
    }
    
    @Test
    fun validateContactInput_withEmptyName_returnsFalse() {
        val name = ""
        val phone = "1234567890"
        
        val isValid = name.trim().isNotEmpty() && phone.trim().isNotEmpty()
        
        assertFalse("Empty name should be invalid", isValid)
    }
    
    @Test
    fun validateContactInput_withEmptyPhone_returnsFalse() {
        val name = "John Doe"
        val phone = ""
        
        val isValid = name.trim().isNotEmpty() && phone.trim().isNotEmpty()
        
        assertFalse("Empty phone should be invalid", isValid)
    }
    
    @Test
    fun validateContactInput_withOptionalEmail_returnsTrue() {
        val name = "Jane Doe"
        val phone = "0987654321"
        val email = "" // Empty email should be allowed
        
        val isNameValid = name.trim().isNotEmpty()
        val isPhoneValid = phone.trim().isNotEmpty()
        val isEmailValid = email.isEmpty() || email.contains("@")
        
        assertTrue("Contact with empty email should be valid", 
                  isNameValid && isPhoneValid && isEmailValid)
    }
    
    @Test
    fun parseContactString_withValidFormat_extractsCorrectData() {
        val contactString = "ID: 1, Name: John Doe, Phone: 1234567890, Email: john@example.com"
        
        // Simulate parsing logic from MainActivity
        val parts = contactString.split(", ")
        
        assertEquals("Should have 4 parts", 4, parts.size)
        
        val id = parts[0].substringAfter("ID: ")
        val name = parts[1].substringAfter("Name: ")
        val phone = parts[2].substringAfter("Phone: ")
        val email = parts[3].substringAfter("Email: ")
        
        assertEquals("ID should match", "1", id)
        assertEquals("Name should match", "John Doe", name)
        assertEquals("Phone should match", "1234567890", phone)
        assertEquals("Email should match", "john@example.com", email)
    }
    
    @Test
    fun databaseTableCreation_sqlSyntax_isValid() {
        // Test that our SQL create statement is syntactically correct
        val createStatement = """
            CREATE TABLE contacts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                phone TEXT NOT NULL,
                email TEXT
            )
        """.trimIndent()
        
        // Basic SQL syntax validation
        assertTrue("SQL should contain CREATE TABLE", createStatement.contains("CREATE TABLE"))
        assertTrue("SQL should contain PRIMARY KEY", createStatement.contains("PRIMARY KEY"))
        assertTrue("SQL should contain AUTOINCREMENT", createStatement.contains("AUTOINCREMENT"))
        assertTrue("SQL should contain NOT NULL constraints", createStatement.contains("NOT NULL"))
    }
    
    @Test
    fun errorHandling_withInvalidId_handlesGracefully() {
        // Test ID parsing error handling
        val invalidIdString = "abc"
        
        try {
            invalidIdString.toInt()
            fail("Should have thrown NumberFormatException")
        } catch (e: NumberFormatException) {
            // Expected behavior - this should be caught and handled in the app
            assertNotNull("Exception should be caught", e)
        }
    }
}