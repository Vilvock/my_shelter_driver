package br.com.app5m.appshelterdriver.models

import br.com.app5m.appshelterdriver.models.Distance
import br.com.app5m.appshelterdriver.models.Duration
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Leg : Serializable{

    @field:SerializedName("distance")
    var distance = Distance()

    @field:SerializedName("duration")
    var duration = Duration()

    @field:SerializedName("points")
    var polyLinePoints : String? = null
}