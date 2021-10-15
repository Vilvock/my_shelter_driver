package br.com.app5m.appshelterdriver.models
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
class UAddress : Parcelable {

    var msg: String? = null
    var rows: String? = null
    var uf: String? = null
    var logradouro: String? = null

    @field:SerializedName("localidade")
    var place: String? = null

    @field:SerializedName("token")
    var token: String? = null

    @field:SerializedName("status")
    var status: String? = null

    @field:SerializedName("latitude")
    var latitude: String? = null

    @field:SerializedName("longitude")
    var longitude: String? = null

    @field:SerializedName("cep")
    var cep: String? = null

    @field:SerializedName("estado")
    var state: String? = null

    @field:SerializedName("cidade")
    var city: String? = null

    @field:SerializedName("bairro")
    var neighborhood: String? = null

    @field:SerializedName("numero")
    var number: String? = null

    @field:SerializedName("complemento")
    var complement: String? = null

    @field:SerializedName("endereco")
    var address: String? = null

    @field:SerializedName("id")
    var id: String? = null


}