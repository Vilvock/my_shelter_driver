package br.com.app5m.appshelterdriver.models

import br.com.app5m.appshelterdriver.models.Route
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Ride : Serializable {

    @field:SerializedName("modelo_veiculo")
    var vehicleModel: String? = null

    @field:SerializedName("placa_veiculo")
    var vehicleBoard: String? = null

    @field:SerializedName("id_motorista")
    var driverId: String? = null

    @field:SerializedName("rota_corrida_final")
    var finalRoute = Route()

    @field:SerializedName("latitude")
    var latitude: String? = null

    @field:SerializedName("longitude")
    var longitude: String? = null

    @field:SerializedName("valor_min")
    var minValue: String? = null

    @field:SerializedName("valor_km")
    var kmValue: String? = null

    @field:SerializedName("dinamica")
    var dinamicPrice: String? = null

    @field:SerializedName("msg")
    var msg: String? = null

    @field:SerializedName("id_moip")
    var moipId: String? = null

    @field:SerializedName("cvv")
    var cvv: String? = null

    @field:SerializedName("card_id")
    var cardId: String? = null

    @field:SerializedName("status")
    var status: String? = null

    @field:SerializedName("polyline")
    var polylineRoute: String? = null

    @field:SerializedName("id_passageiro")
    var idPassenger: String? = null

    @field:SerializedName("rows")
    var rows: String? = null

    @field:SerializedName("id_corrida")
    var rideId: String? = null

    @field:SerializedName("status_corrida")
    var rideStatus: String? = null

    @field:SerializedName("data")
    var date: String? = null

    @field:SerializedName("valor_dinamica")
    var dinamicValue: String? = null

    @field:SerializedName("valor_motorista")
    var driverValue: String? = null

    @field:SerializedName("valor_total")
    var totalValue: String? = null

    @field:SerializedName("motorista_avatar")
    var driverAvatar: String? = null

    @field:SerializedName("motorista_nome")
    var driverName: String? = null

    @field:SerializedName("nome_tipo_pagamento")
    var nameTypePayment: String? = null

    @field:SerializedName("id_tipo_pagamento")
    var idTypePayment: String? = null

    @field:SerializedName("id")
    var id: String? = null

    @field:SerializedName("tempo_rota")
    var routeTime: String? = null

    @field:SerializedName("tipo_carro")
    var carType: String? = null

    @field:SerializedName("forma_pagamento")
    var paymentMethod: String? = null

    @field:SerializedName("distancia")
    var distance: String? = null

    @field:SerializedName("distancia_sigla")
    var distanceInitials: String? = null

    @field:SerializedName("latitude_in")
    var originLatitude: String? = null

    @field:SerializedName("longitude_in")
    var originLongitude: String? = null

    @field:SerializedName("latitude_out")
    var destinationLatitude: String? = null

    @field:SerializedName("longitude_out")
    var destinationLongitude: String? = null

    @field:SerializedName("id_de")
    var idFrom: String? = null

    @field:SerializedName("endereco_in")
    var originAddress: String? = null

    @field:SerializedName("endereco_out")
    var destinationAddress: String? = null

    @field:SerializedName("token")
    var token: String? = null

    @field:SerializedName("origem")
    var origin: String? = null

    @field:SerializedName("destino")
    var destination: String? = null

}