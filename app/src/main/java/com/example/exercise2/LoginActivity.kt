package com.example.exercise2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditTextLogin: EditText
    private lateinit var passwordEditTextLogin: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        passwordEditTextLogin = findViewById(R.id.passwordEditTextLogin)
        emailEditTextLogin = findViewById(R.id.emailEditTextLogin)
        loginButton = findViewById(R.id.loginButtonLogin)
        registerButton = findViewById(R.id.registerButtonLogin)

        val helper = DbHelper(this)

        loginButton.setOnClickListener {
            if (isValid()) {
                // Fetching user credentials.
                val userId = helper.getUserCredentials(
                    emailEditTextLogin.text.toString(),
                    passwordEditTextLogin.text.toString()
                )

                // Check if user exists.
                if (userId == null) {
                    showErrorMessage("Invalid Credentials.")
                    return@setOnClickListener
                }

                // Setting user id in shared preferences.
                val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putLong("userId", userId)
                editor.apply()

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Success")
                builder.setMessage("Logged In successfully.")
                builder.setNeutralButton("Ok") { _, _  ->
                    // Starting new activity.
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                builder.show()
            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun isValid(): Boolean {
        if (emailEditTextLogin.text.toString().isBlank()) {
            showErrorMessage("Email cannot be blank.")
            return false
        }

        if (passwordEditTextLogin.text.toString().isBlank()) {
            showErrorMessage("Password cannot be blank.")
            return false
        }
        return true
    }

    private fun showErrorMessage(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setNeutralButton("Ok", null)

        builder.show()
    }
}