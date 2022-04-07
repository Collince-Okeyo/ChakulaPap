package com.ramgdev.chakulapap.model

import android.os.Parcel
import android.os.Parcelable

data class HotelModel(
    val hotelId: Int=0,
    val hotelImage: String?= "",
    val hotelName: String?= "",
    val hotelMenu: MenuItems? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(hotelId)
        parcel.writeString(hotelImage)
        parcel.writeString(hotelName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HotelModel> {
        override fun createFromParcel(parcel: Parcel): HotelModel {
            return HotelModel(parcel)
        }

        override fun newArray(size: Int): Array<HotelModel?> {
            return arrayOfNulls(size)
        }
    }
}