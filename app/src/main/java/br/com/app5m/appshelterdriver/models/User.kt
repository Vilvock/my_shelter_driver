package br.com.app5m.appshelterdriver.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
class User : Parcelable {

    @field:SerializedName("id")
    var id: String? = null

    @field:SerializedName("email")
    var email: String? = null

    @field:SerializedName("password")
    var password: String? = null

    @field:SerializedName("rows")
    var rows: String? = null

    @field:SerializedName("fcm")
    var fcm: String? = null

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

    @field:SerializedName("endereco")
    var address: String? = null

    @field:SerializedName("cep")
    var cep: String? = null

    @field:SerializedName("cidade")
    var city: String? = null

    @field:SerializedName("estado")
    var state: String? = null

    @field:SerializedName("celular")
    var phone: String? = null

    @field:SerializedName("token_senha")
    var tokenPassword: String? = null

}