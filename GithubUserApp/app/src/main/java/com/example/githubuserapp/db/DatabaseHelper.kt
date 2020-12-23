package com.example.githubuserapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.githubuserapp.db.DatabaseContract.UserColumnns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "favoriteusergithub"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_FAVORITES = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.UserColumnns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.UserColumnns.COLUMN_NAME_USERNAME} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumnns.COLUMN_NAME_AVATAR_URL} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumnns.COLUMN_USER_URL} TEXT NOT NULL," +
                " ${DatabaseContract.UserColumnns.COLUMN_NAME_USER_ID} TEXT NOT NULL)"
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}