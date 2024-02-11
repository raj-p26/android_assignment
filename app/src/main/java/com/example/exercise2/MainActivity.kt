package com.example.exercise2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        passwordEditText = findViewById(R.id.passwordEditText)
        emailEditText = findViewById(R.id.emailEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        firstNameEditText = findViewById(R.id.firstNameEditText)
        registerButton = findViewById(R.id.registerButton)
        loginButton = findViewById(R.id.loginButton)

        val helper = DbHelper(this)

        var prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)

        if (prefs.contains("userId")) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerButton.setOnClickListener {
            if (isValid()) {
                // Inserting user into database.
                val userId = helper.addUser(
                    firstNameEditText.text.toString(),
                    lastNameEditText.text.toString(),
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )

                // Adding id into shared preferences.
                prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putLong("userId", userId)
                editor.apply()

                // Starting next activity.
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Success")
                builder.setMessage("Logged In successfully.")
                builder.setNeutralButton("Ok") { _, _  ->
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                builder.show()
            }
        }

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun isValid(): Boolean {
        if (firstNameEditText.text.toString().isBlank()) {
            showErrorMessage("First Name cannot be Blank.")
            return false
        }
        if (lastNameEditText.text.toString().isBlank()) {
            showErrorMessage("Last Name cannot be Blank.")
            return false
        }
        if (emailEditText.text.toString().isBlank()) {
            showErrorMessage("Email cannot be Blank.")
            return false
        }
        if (passwordEditText.text.toString().isBlank()) {
            showErrorMessage("Password cannot be Blank.")
            return false
        }

        return true
    }

    private fun showErrorMessage(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setNeutralButton("Ok", null)

        builder.show()
    }
}