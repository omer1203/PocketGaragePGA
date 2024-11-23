package com.example.homepage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.homepage.db.DatabaseClient
import com.example.homepage.db.User
import com.example.homepage.db.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDbActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_db) // Ensure this layout exists (activity_user_db.xml)

        // Access the database and DAO
        val db = DatabaseClient.getDatabase(applicationContext)
        val userDao = db.userDao()

        // Using Coroutine to perform database operations in a background thread
        lifecycleScope.launch {
            // Insert a new user
            val newUser = User(uid = 1, firstName = "John", lastName = "Doe")
            userDao.insertAll(newUser)

            // Query all users
            val users = userDao.getAll()

            // Find a user by name
            val foundUser = userDao.findByName("John", "Doe")

            // Update UI on the main thread (optional)
            withContext(Dispatchers.Main) {
                // Just a quick example of displaying users and foundUser
                if (foundUser != null) {
                    Toast.makeText(
                        this@UserDbActivity,
                        "User found: ${foundUser.firstName} ${foundUser.lastName}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this@UserDbActivity, "User not found", Toast.LENGTH_SHORT).show()
                }

                // Display all users in a Toast (just for demonstration purposes)
                users.forEach {
                    Toast.makeText(this@UserDbActivity, "User: ${it.firstName} ${it.lastName}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

