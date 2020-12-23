package com.example.githubuserapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel
class FollowerModel() : Parcelable {
    @SerializedName("login")
    var login: String? = null

    @SerializedName("avatar_url")
    var avatarUrl: String? = null

    @SerializedName("id")
    var id = 0

    constructor(parcel: android.os.Parcel) : this() {
        login = parcel.readString().toString()
        avatarUrl = parcel.readString().toString()
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeString(login)
        parcel.writeString(avatarUrl)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FollowerModel> {
        override fun createFromParcel(parcel: android.os.Parcel): FollowerModel {
            return FollowerModel(parcel)
        }

        override fun newArray(size: Int): Array<FollowerModel?> {
            return arrayOfNulls(size)
        }
    }
}