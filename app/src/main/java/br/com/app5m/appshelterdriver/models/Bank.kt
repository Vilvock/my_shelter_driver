package br.com.app5m.appshelterdriver.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Bank : Parcelable {

    @field:SerializedName("id_user")
    var idUser: String? = null

    @field:SerializedName("type")
    var type: String? = null

    @field:SerializedName("tipo_chave_pix")
    var typePixKey: String? = null

    @field:SerializedName("chave_pix")
    var pixKey: String? = null

    @field:SerializedName("banco")
    var bank: String? = null

    @field:SerializedName("agencia_d")
    var agencyDigit: String? = null

    @field:SerializedName("agencia")
    var agency: String? = null

    @field:SerializedName("cc_d")
    var ccDigit: String? = null

    @field:SerializedName("cc")
    var cc: String? = null

    @field:SerializedName("tipo_conta")
    var typeAccount: String? = null

    @field:SerializedName("token")
    var token: String? = null

}