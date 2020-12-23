package com.example.githubuserapp.db

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuserapp.db.DatabaseContract.AUTHORITY
import com.example.githubuserapp.db.DatabaseContract.UserColumnns.Companion.CONTENT_URI
import com.example.githubuserapp.db.DatabaseContract.UserColumnns.Companion.TABLE_NAME

@Suppress("UNREACHABLE_CODE")
class GithubProvider :ContentProvider() {
    companion object {
        private const val USER = 1
        private const val USER_ID = 2

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favoritesHelper: FavoriteHelper

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
            sUriMatcher.addURI(
                AUTHORITY,
                "$TABLE_NAME/#",
                USER_ID
            )
        }
    }
    override fun onCreate(): Boolean {
        favoritesHelper = FavoriteHelper.getInstance(context as Context)
        favoritesHelper.open()
        return true
    }

    override fun query(
        uri: Uri,
        strings: Array<String>?,
        s: String?,
        strings1: Array<String>?,
        s1: String?
    ): Cursor? {

        return when (sUriMatcher.match(uri)) {
            USER -> favoritesHelper.querybyUserName()
            USER_ID -> favoritesHelper.querybyUserID(uri.lastPathSegment.toString())
            else -> null
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val added: Long = when (USER_ID) {
            sUriMatcher.match(uri) -> favoritesHelper.insert(contentValues)
            else -> 0
        }
        return Uri.parse("$CONTENT_URI/$added")

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        val deleted: Int = when (USER_ID) {
            sUriMatcher.match(uri) -> favoritesHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted
    }
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")

    }

}