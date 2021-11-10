package br.com.app5m.appshelterdriver.models

import com.google.gson.annotations.SerializedName

class Document {

    @field:SerializedName("token")
    var token: String? = null

    @field:SerializedName("status")
    var status: String? = null

    @field:SerializedName("msg")
    var msg: String? = null

    @field:SerializedName("rows")
    var rows: String? = null

    @field:SerializedName("id")
    var id: String? = null

}