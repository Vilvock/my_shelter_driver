package br.com.app5m.appshelterdriver.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LocationUser: Serializable {

    @field:SerializedName("id")
    var id: String? = null

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

    @field:SerializedName("token")
    var token: String? = null

    @field:SerializedName("status")
    var status: String? = null

    @field:SerializedName("msg")
    var msg: String? = null

    @field:SerializedName("rows")
    var rows: String? = null

    @field:SerializedName("distancia")
    var distance: String? = null



}