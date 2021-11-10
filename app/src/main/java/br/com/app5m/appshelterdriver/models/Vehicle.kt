package br.com.app5m.appshelterdriver.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
class Vehicle : Parcelable {


    @field:SerializedName("marca")
    var mark: String? = null

    @field:SerializedName("modelo")
    var model: String? = null

    @field:SerializedName("placa")
    var board: String? = null

    @field:SerializedName("cor")
    var color: String? = null

    @field:SerializedName("ano")
    var year: String? = null

    @field:SerializedName("ano_modelo")
    var modelYear: String? = null

    @field:SerializedName("tipo_carro")
    var typeCar: String? = null
}