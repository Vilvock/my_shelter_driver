package br.com.app5m.appshelterdriver.models
import java.io.Serializable

class UAddress: Serializable {

    constructor()
    constructor(
        titulo: String?,
        rua: String?,
        bairro: String?,
        numero: String?,
        tipoderesidencia: String?,
        logradouro: String?,
        cep: String?,
    ) {
        this.titulo = titulo
        this.rua = rua
        this.bairro = bairro
        this.numero = numero
        this.tipoderesidencia = tipoderesidencia
        this.logradouro = logradouro
        this.cep = cep
    }

    var titulo: String? = null
    var rua: String? = null
    var bairro: String? = null
    var numero: String? = null
    var tipoderesidencia: String? = null
    var logradouro: String? = null
    var cep: String? = null


}