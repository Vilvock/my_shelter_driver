package br.com.app5m.appshelterdriver.models

import android.os.Parcelable
import br.com.app5m.appshelterdriver.models.Route
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Map : Parcelable {

    @field:SerializedName("routes")
    var routeList = ArrayList<Route>()

    @field:SerializedName("token")
    var token: String? = null

    @field:SerializedName("endereco")
    var address: String? = null

    @field:SerializedName("endereco_formatado")
    var addressFormatted: String? = null

    @field:SerializedName("latitude")
    var latitude: String? = null

    @field:SerializedName("longitude")
    var longitude: String? = null

    @field:SerializedName("status")
    var status: String? = null

    @field:SerializedName("latitude_origem")
    var originLatitude: String? = null

    @field:SerializedName("longitude_origem")
    var originLongitude: String? = null

    @field:SerializedName("latitude_destino")
    var destinationLatitude: String? = null

    @field:SerializedName("longitude_destino")
    var destinationLongitude: String? = null

}