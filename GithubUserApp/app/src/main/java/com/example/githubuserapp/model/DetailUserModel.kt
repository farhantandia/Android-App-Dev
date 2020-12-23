package com.example.githubuserapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel
class DetailUserModel() : Parcelable {
    @SerializedName("login")
   var login: String? = null

    @SerializedName("id")
    var id = 0

    @SerializedName("email")
    val email: Any? = null

    @SerializedName("followers")
     var followers = 0

    @SerializedName("avatar_url")
     var avatarUrl: String? = null

    @SerializedName("following")
    private var following = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("location")
     var location: String? = null

    @SerializedName("company")
    var company: String? = null

    constructor(parcel: android.os.Parcel) : this() {
        login = parcel.readString().toString()
        id = parcel.readInt()
        followers = parcel.readInt()
        avatarUrl = parcel.readString().toString()
        following = parcel.readInt()
        name = parcel.readString().toString()
        location = parcel.readString().toString()
        company = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeString(login)
        parcel.writeInt(id)
        parcel.writeInt(followers)
        parcel.writeString(avatarUrl)
        parcel.writeInt(following)
        parcel.writeString(name)
        parcel.writeString(location)
        parcel.writeString(company)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailUserModel> {
        override fun createFromParcel(parcel: android.os.Parcel): DetailUserModel {
            return DetailUserModel(parcel)
        }

        override fun newArray(size: Int): Array<DetailUserModel?> {
            return arrayOfNulls(size)
        }
    }
}