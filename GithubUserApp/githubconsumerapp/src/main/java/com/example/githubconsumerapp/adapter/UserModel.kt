package com.example.githubconsumerapp.adapter

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel
@Parcel
class UserModel() : Parcelable {
    @SerializedName("login")
    lateinit var login: String

    @SerializedName("avatar_url")
    var avatarUrl: String? = null

    @SerializedName("id")
    var id = 0
    @SerializedName("url")
    var url: String? = null

    constructor(parcel: android.os.Parcel) : this() {
        login = parcel.readString().toString()
        avatarUrl = parcel.readString()
        id = parcel.readInt()
        url = parcel.readString()

    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeString(login)
        parcel.writeString(avatarUrl)
        parcel.writeInt(id)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: android.os.Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }


}