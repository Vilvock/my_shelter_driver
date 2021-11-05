package br.com.app5m.appshelterdriver.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Route : Serializable {


    @field:SerializedName("legs")
    var legList = ArrayList<Leg>()

    @field:SerializedName("overview_polyline")
    var overViewPolyLine = Leg()


}