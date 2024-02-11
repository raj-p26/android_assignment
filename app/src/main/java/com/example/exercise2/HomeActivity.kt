package com.example.exercise2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HomeActivity : AppCompatActivity() {
    private lateinit var messageTextView: TextView
    private lateinit var logoutButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        messageTextView = findViewById(R.id.messageTextView)
        logoutButton = findViewById(R.id.logoutButton)

        val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
        if (!prefs.contains("userId")) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val email = DbHelper(this)
            .getUserById(prefs.getLong("userId", -1L))

        val text = String.format(resources.getString(R.string.home_message), email)
        messageTextView.setText(text)

        logoutButton.setOnClickListener {
            prefs
                .edit()
                .clear()
                .apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}