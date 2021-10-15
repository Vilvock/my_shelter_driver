package br.com.app5m.appshelterdriver.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Ride : Parcelable {

    @field:SerializedName("token")
    var token: String? = null

}