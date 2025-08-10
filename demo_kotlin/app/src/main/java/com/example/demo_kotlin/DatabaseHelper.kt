package com.example.demo_kotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "contacts.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_CONTACTS = "contacts"
        
        // Column names
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_EMAIL = "email"
        
        // Create table statement
        private const val CREATE_CONTACTS_TABLE = """
            CREATE TABLE $TABLE_CONTACTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_PHONE TEXT NOT NULL,
                $COLUMN_EMAIL TEXT
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(CREATE_CONTACTS_TABLE)
            Log.d("DatabaseHelper", "Contacts table created successfully")
        } catch (e: SQLiteException) {
            Log.e("DatabaseHelper", "Error creating table: ${e.message}")
            throw e
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
            onCreate(db)
            Log.d("DatabaseHelper", "Database upgraded from version $oldVersion to $newVersion")
        } catch (e: SQLiteException) {
            Log.e("DatabaseHelper", "Error upgrading database: ${e.message}")
            throw e
        }
    }

    /**
     * Add a new contact to the database
     * @param name Contact name
     * @param phone Contact phone number
     * @param email Contact email (optional)
     * @return Row ID of newly inserted contact, -1 if error occurred
     */
    fun addContact(name: String, phone: String, email: String): Long {
        var result: Long = -1
        try {
            val db = this.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_NAME, name.trim())
                put(COLUMN_PHONE, phone.trim())
                put(COLUMN_EMAIL, email.trim().ifEmpty { null })
            }
            
            result = db.insert(TABLE_CONTACTS, null, values)
            Log.d("DatabaseHelper", "Contact added with ID: $result")
        } catch (e: SQLiteException) {
            Log.e("DatabaseHelper", "Error adding contact: ${e.message}")
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Unexpected error adding contact: ${e.message}")
        }
        return result
    }

    /**
     * Update an existing contact
     * @param id Contact ID
     * @param name New name
     * @param phone New phone number
     * @param email New email
     * @return Number of rows affected
     */
    fun updateContact(id: Int, name: String, phone: String, email: String): Int {
        var result = 0
        try {
            val db = this.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_NAME, name.trim())
                put(COLUMN_PHONE, phone.trim())
                put(COLUMN_EMAIL, email.trim().ifEmpty { null })
            }
            
            result = db.update(TABLE_CONTACTS, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
            Log.d("DatabaseHelper", "Updated $result contact(s)")
        } catch (e: SQLiteException) {
            Log.e("DatabaseHelper", "Error updating contact: ${e.message}")
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Unexpected error updating contact: ${e.message}")
        }
        return result
    }

    /**
     * Delete a contact by ID
     * @param id Contact ID to delete
     * @return Number of rows affected
     */
    fun deleteContact(id: Int): Int {
        var result = 0
        try {
            val db = this.writableDatabase
            result = db.delete(TABLE_CONTACTS, "$COLUMN_ID = ?", arrayOf(id.toString()))
            Log.d("DatabaseHelper", "Deleted $result contact(s)")
        } catch (e: SQLiteException) {
            Log.e("DatabaseHelper", "Error deleting contact: ${e.message}")
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Unexpected error deleting contact: ${e.message}")
        }
        return result
    }

    /**
     * Get all contacts from the database
     * @return List of contact strings
     */
    fun getAllContacts(): ArrayList<String> {
        val contactList = ArrayList<String>()
        var cursor: Cursor? = null
        
        try {
            val db = this.readableDatabase
            cursor = db.query(
                TABLE_CONTACTS,
                null,
                null,
                null,
                null,
                null,
                "$COLUMN_ID ASC"
            )

            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                    val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                    val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)) ?: ""
                    
                    val contactString = "ID: $id, Name: $name, Phone: $phone, Email: $email"
                    contactList.add(contactString)
                } while (cursor.moveToNext())
            }
            
            Log.d("DatabaseHelper", "Retrieved ${contactList.size} contacts")
        } catch (e: SQLiteException) {
            Log.e("DatabaseHelper", "Error retrieving contacts: ${e.message}")
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Unexpected error retrieving contacts: ${e.message}")
        } finally {
            cursor?.close()
        }
        
        return contactList
    }

    /**
     * Get a specific contact by ID
     * @param id Contact ID
     * @return Contact data or null if not found
     */
    fun getContactById(id: Int): String? {
        var cursor: Cursor? = null
        try {
            val db = this.readableDatabase
            cursor = db.query(
                TABLE_CONTACTS,
                null,
                "$COLUMN_ID = ?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )

            if (cursor.moveToFirst()) {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)) ?: ""
                
                return "ID: $id, Name: $name, Phone: $phone, Email: $email"
            }
        } catch (e: SQLiteException) {
            Log.e("DatabaseHelper", "Error retrieving contact by ID: ${e.message}")
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Unexpected error retrieving contact by ID: ${e.message}")
        } finally {
            cursor?.close()
        }
        
        return null
    }

    /**
     * Get the total number of contacts
     * @return Contact count
     */
    fun getContactCount(): Int {
        var count = 0
        var cursor: Cursor? = null
        
        try {
            val db = this.readableDatabase
            cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_CONTACTS", null)
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0)
            }
        } catch (e: SQLiteException) {
            Log.e("DatabaseHelper", "Error getting contact count: ${e.message}")
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Unexpected error getting contact count: ${e.message}")
        } finally {
            cursor?.close()
        }
        
        return count
    }
}