package com.ramgdev.chakulapap.model

import android.os.Parcel
import android.os.Parcelable

data class MenuItems(
    val id:Int = 0,
    val menuImage:String? = "",
    val menuName:String? = "",
    val menuPrice:String? = "",
    val accountType: String? = "",
    val accountNumber: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(menuImage)
        parcel.writeString(menuName)
        parcel.writeString(menuPrice)
        parcel.writeString(accountType)
        parcel.writeString(accountNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MenuItems> {
        override fun createFromParcel(parcel: Parcel): MenuItems {
            return MenuItems(parcel)
        }

        override fun newArray(size: Int): Array<MenuItems?> {
            return arrayOfNulls(size)
        }
    }
}
