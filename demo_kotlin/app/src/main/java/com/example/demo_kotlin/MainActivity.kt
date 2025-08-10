package com.example.demo_kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText
    private lateinit var edtEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnQuery: Button
    private lateinit var lvContacts: ListView
    private lateinit var edtId: EditText
    
    private lateinit var contactList: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var databaseHelper: DatabaseHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        initializeViews()
        
        // Initialize database helper with error handling
        try {
            databaseHelper = DatabaseHelper(this)
            contactList = ArrayList()
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactList)
            lvContacts.adapter = adapter
            
            // Load initial data
            loadContacts()
        } catch (e: Exception) {
            Toast.makeText(this, "Error initializing database: ${e.message}", Toast.LENGTH_LONG).show()
        }

        // Set up click listeners
        setupClickListeners()
    }

    private fun initializeViews() {
        try {
            edtId = findViewById(R.id.edt_id)
            edtName = findViewById(R.id.edt_name)
            edtPhone = findViewById(R.id.edt_phone)
            edtEmail = findViewById(R.id.edt_email)
            btnAdd = findViewById(R.id.btn_add)
            btnUpdate = findViewById(R.id.btn_update)
            btnDelete = findViewById(R.id.btn_delete)
            btnQuery = findViewById(R.id.btn_query)
            lvContacts = findViewById(R.id.lv_contacts)
        } catch (e: Exception) {
            Toast.makeText(this, "Error initializing views: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupClickListeners() {
        btnAdd.setOnClickListener { addContact() }
        btnUpdate.setOnClickListener { updateContact() }
        btnDelete.setOnClickListener { deleteContact() }
        btnQuery.setOnClickListener { loadContacts() }
        
        // Set up list item click listener
        lvContacts.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedContact = contactList[position]
            populateFieldsFromSelection(selectedContact)
        }
    }

    private fun addContact() {
        val name = edtName.text.toString().trim()
        val phone = edtPhone.text.toString().trim()
        val email = edtEmail.text.toString().trim()

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in name and phone fields", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val result = databaseHelper.addContact(name, phone, email)
            if (result != -1L) {
                Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show()
                clearFields()
                loadContacts()
            } else {
                Toast.makeText(this, "Failed to add contact", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error adding contact: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateContact() {
        val idText = edtId.text.toString().trim()
        val name = edtName.text.toString().trim()
        val phone = edtPhone.text.toString().trim()
        val email = edtEmail.text.toString().trim()

        if (idText.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields including ID", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val id = idText.toInt()
            val result = databaseHelper.updateContact(id, name, phone, email)
            if (result > 0) {
                Toast.makeText(this, "Contact updated successfully", Toast.LENGTH_SHORT).show()
                clearFields()
                loadContacts()
            } else {
                Toast.makeText(this, "Failed to update contact", Toast.LENGTH_SHORT).show()
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter a valid ID number", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error updating contact: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteContact() {
        val idText = edtId.text.toString().trim()

        if (idText.isEmpty()) {
            Toast.makeText(this, "Please enter contact ID to delete", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val id = idText.toInt()
            val result = databaseHelper.deleteContact(id)
            if (result > 0) {
                Toast.makeText(this, "Contact deleted successfully", Toast.LENGTH_SHORT).show()
                clearFields()
                loadContacts()
            } else {
                Toast.makeText(this, "Failed to delete contact or contact not found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter a valid ID number", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error deleting contact: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadContacts() {
        try {
            val contacts = databaseHelper.getAllContacts()
            contactList.clear()
            contactList.addAll(contacts)
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Toast.makeText(this, "Error loading contacts: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun populateFieldsFromSelection(selectedContact: String) {
        try {
            // Parse the contact string (format: "ID: x, Name: y, Phone: z, Email: w")
            val parts = selectedContact.split(", ")
            if (parts.size >= 3) {
                edtId.setText(parts[0].substringAfter("ID: "))
                edtName.setText(parts[1].substringAfter("Name: "))
                edtPhone.setText(parts[2].substringAfter("Phone: "))
                if (parts.size > 3) {
                    edtEmail.setText(parts[3].substringAfter("Email: "))
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error parsing contact selection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        edtId.setText("")
        edtName.setText("")
        edtPhone.setText("")
        edtEmail.setText("")
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (::databaseHelper.isInitialized) {
                databaseHelper.close()
            }
        } catch (e: Exception) {
            // Log error but don't crash the app during destruction
        }
    }
}