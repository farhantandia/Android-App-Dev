package com.example.githubuserapp.db

import android.database.Cursor
import com.example.githubuserapp.model.UserModel
import java.util.ArrayList

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()

        notesCursor?.apply {
            while (moveToNext()) {
                val user =
                    UserModel()
                user.login = getString(getColumnIndexOrThrow(DatabaseContract.UserColumnns.COLUMN_NAME_USERNAME))
                user.id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColumnns.COLUMN_NAME_USER_ID))
                user.avatarUrl = getString(getColumnIndexOrThrow(DatabaseContract.UserColumnns.COLUMN_NAME_AVATAR_URL))
                user.url = getString(getColumnIndexOrThrow(DatabaseContract.UserColumnns.COLUMN_USER_URL))
                users.add(user)
            }
        }
        return users
    }
}