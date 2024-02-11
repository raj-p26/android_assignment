package com.example.exercise2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, "exercise2", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("""
        CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            first_name VARCHAR NOT NULL,
            last_name VARCHAR NOT NULL,
            email VARCHAR NOT NULL,
            password VARCHAR NOT NULL
        )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("""
            DROP TABLE IF EXISTS users
        """)
        onCreate(db)
    }

    fun addUser(firstName: String, lastName: String, email: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("first_name", firstName)
        values.put("last_name", lastName)
        values.put("email", email)
        values.put("password", password)

        return db.insert("users", null, values)
    }

    fun getUserById(id: Long): String? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT email FROM users WHERE id=?", arrayOf(id.toString()))
        var userEmail: String
        if (!cursor.moveToFirst()) {
            return null
        }

        do {
            userEmail = cursor.getString(0)
        } while (false)
        cursor.close()

        return userEmail
    }

    fun getUserCredentials(email: String, password: String): Long? {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT id FROM users WHERE email=? AND password=?",
            arrayOf(email, password)
        )
        if (!cursor.moveToFirst()) {
            return null
        }

        var userId: Long
        do {
            userId = cursor.getLong(0)
        } while (false)
        cursor.close()

        return userId
    }
}