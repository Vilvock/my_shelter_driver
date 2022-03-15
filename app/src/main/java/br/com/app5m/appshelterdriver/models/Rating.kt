package br.com.app5m.appshelterdriver.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

class Rating : Serializable {

    @field:SerializedName("rows")
    var rows: String? = null

    @field:SerializedName("id")
    var id: String? = null

    @field:SerializedName("type")
    var type: String? = null

    @field:SerializedName("msg")
    var msg: String? = null

    @field:SerializedName("status")
    var status: String? = null

    @field:SerializedName("token")
    var token: String? = null

    @field:SerializedName("nome")
    var name: String? = null

    @field:SerializedName("nome_passageiro")
    var passengerName: String? = null

    @field:SerializedName("id_passageiro")
    var idPassenger: String? = null

    @field:SerializedName("id_motorista")
    var idDriver: String? = null

    @field:SerializedName("id_corrida")
    var idRide: String? = null

    @field:SerializedName("estrelas")
    var stars: String? = null

    @field:SerializedName("descricao")
    var desc: String? = null

    @field:SerializedName("media")
    var average: String? = null

    @field:SerializedName("limit")
    var limit: String? = null
}