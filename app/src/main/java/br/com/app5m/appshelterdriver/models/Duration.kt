package br.com.app5m.appshelterdriver.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

class Duration : Serializable{

    @field:SerializedName("text")
    var text: String? = null

    @field:SerializedName("value")
    var value: String? = null

}