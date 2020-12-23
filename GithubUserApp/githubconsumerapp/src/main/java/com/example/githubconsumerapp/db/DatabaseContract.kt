package com.example.githubconsumerapp.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.example.githubuserapp"
    const val SCHEME = "content"

    class UserColumnns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_user"
            val _ID = "_id"
            val COLUMN_NAME_USERNAME = "username"
            val COLUMN_NAME_AVATAR_URL = "avatar_url"
            val COLUMN_USER_URL = "user_url"
            val COLUMN_NAME_USER_ID = "user_id"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()

        }
    }
}