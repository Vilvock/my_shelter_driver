package br.com.app5m.appshelterdriver.model

import java.io.Serializable

class VeicleColor: Serializable{

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



