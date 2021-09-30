package br.com.app5m.appshelterdriver.models

import java.io.Serializable

class VehicleColor: Serializable{

    constructor()
    constructor(
        name: String,
        image: Int
        ) {

        this.name = name
        this.image = image

    }

    var name: String? = null
    var image = 0


}



