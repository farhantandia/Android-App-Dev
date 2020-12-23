package com.example.githubuserapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

@Parcel
class ResponseUser() : Parcelable {
    @SerializedName("total_count")
    private var totalCount = 0

    @SerializedName("incomplete_results")
    private val incompleteResults = false

    @SerializedName("items")
    internal var items: List<UserModel>? = null

    constructor(parcel: android.os.Parcel) : this() {
        totalCount = parcel.readInt()
        items = parcel.createTypedArrayList(UserModel)
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeInt(totalCount)
        parcel.writeTypedList(items)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResponseUser> {
        override fun createFromParcel(parcel: android.os.Parcel): ResponseUser {
            return ResponseUser(parcel)
        }

        override fun newArray(size: Int): Array<ResponseUser?> {
            return arrayOfNulls(size)
        }
    }

}