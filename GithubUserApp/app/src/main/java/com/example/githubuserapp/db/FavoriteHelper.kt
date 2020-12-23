package com.example.githubuserapp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.githubuserapp.db.DatabaseContract.UserColumnns.Companion.COLUMN_NAME_USERNAME
import com.example.githubuserapp.db.DatabaseContract.UserColumnns.Companion.COLUMN_NAME_USER_ID
import com.example.githubuserapp.db.DatabaseContract.UserColumnns.Companion.TABLE_NAME
import java.sql.SQLException

class FavoriteHelper(context: Context) {
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase
    private lateinit var query: String

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var dataBaseHelper: DatabaseHelper
        private var INSTANCE: FavoriteHelper? = null
        fun getInstance(context: Context): FavoriteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteHelper(context)
            }


    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        Companion.dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }


    fun querybyUserName(UserName: String=""): Cursor {
        if (UserName==""){
            query = "Select * from " + DATABASE_TABLE
            return database.rawQuery(query,null)

        }else{
            query = "Select * from $DATABASE_TABLE where $COLUMN_NAME_USERNAME like '%$UserName%'"
            return database.rawQuery(query,null)
        }

    }


    fun querybyUserID(UserID: String?): Cursor {
        val Query = "Select * from " + DATABASE_TABLE + " where " + COLUMN_NAME_USER_ID + " = " + UserID
        return database.rawQuery(Query,null)
    }


    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }


    fun deleteById(user_id: String): Int {
        return database.delete(DATABASE_TABLE, "$COLUMN_NAME_USER_ID = '$user_id'", null)
    }

    init {
        dataBaseHelper = DatabaseHelper(context)
    }
}