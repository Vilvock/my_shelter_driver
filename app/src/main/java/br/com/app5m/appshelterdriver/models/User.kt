package br.com.app5m.appshelterdriver.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
class User : Parcelable {


    @field:SerializedName("veiculo")
    var vehicle: Vehicle = Vehicle()

    @field:SerializedName("latitude")
    var latitude: String? = null

    @field:SerializedName("longitude")
    var longitude: String? = null

    @field:SerializedName("avatar")
    var avatar: String? = null

    @field:SerializedName("online")
    var online: String? = null

    @field:SerializedName("on")
    var on: String? = null

    @field:SerializedName("id")
    var id: String? = null

    @field:SerializedName("id_user")
    var idUser: String? = null

    @field:SerializedName("id_motorista")
    var driverId: String? = null

    @field:SerializedName("email")
    var email: String? = null

    @field:SerializedName("password")
    var password: String? = null

    @field:SerializedName("genero")
    var gender: String? = null

    @field:SerializedName("rows")
    var rows: String? = null

    @field:SerializedName("fcm")
    var fcm: String? = null

    @field:SerializedName("tipo")
    var typeUser: String? = null

    @field:SerializedName("type")
    var type: String? = null

    @field:SerializedName("tipo_pessoa")
    var typePerson: String? = null

    @field:SerializedName("cpf")
    var cpf: String? = null

    @field:SerializedName("data_nascimento")
    var birth: String? = null

    @field:SerializedName("msg")
    var msg: String? = null

    @field:SerializedName("status")
    var status: String? = null

    @field:SerializedName("token")
    var token: String? = null

    @field:SerializedName("nome")
    var name: String? = null

    @field:SerializedName("documento")
    var document: String? = null

    @field:SerializedName("endereco")
    var address: String? = null

    @field:SerializedName("cep")
    var cep: String? = null

    @field:SerializedName("cidade")
    var city: String? = null

    @field:SerializedName("estado")
    var state: String? = null

    @field:SerializedName("bairro")
    var neighborhood: String? = null

    @field:SerializedName("celular")
    var cellphone: String? = null

    @field:SerializedName("token_senha")
    var tokenPassword: String? = null

    @field:SerializedName("registration_id")
    var registrationId: String? = null

}