package br.com.app5m.appshelterdriver.controller

import android.content.Context
import android.util.Log
import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.controller.webservice.WebService
import br.com.app5m.appshelterdriver.helper.Preferences
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterpassenger.config.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RideControl(context: Context, private val result: WSResult): Callback<List<Ride>> {

    private val service = RetrofitInitializer().retrofit(
        true).create(WebService::class.java)
    private val preferences = Preferences(context)
    private var type = ""
    private val ride = Ride()


    override fun onResponse(call: Call<List<Ride>>, response: Response<List<Ride>>) {
        if (response.isSuccessful){
            response.body()?.let { result.rResponse(it, type) }
        }else{
            result.error("Erro não esperado.")
        }
    }

    override fun onFailure(call: Call<List<Ride>>, t: Throwable) {
        result.error("Não foi possível contatar o servidor.")
        Log.d("error", "onFailure: " + t.message)
    }

    fun findRidesRequests(ride: Ride){

        type = "findRidesRequests"

/*        {
	"tipo_carro": 1,
	"longitude": "-51.052373",
	"latitude": "-30.0137134"
}*/

        val param: Call<List<Ride>> = service.findRidesRequests(ride)
        param.enqueue(this)
    }


    fun findProcess(ride: Ride){

        type = "findProcess"

/*        {
{
	"id_motorista": 4,
	"tipo_carro": 1,
	"latitude": "-30.0166311",
	"longitude": "-51.0544592",
	"token": "shelter_movel#2021"
}
        }*/

        ride.token = WSConstants.TOKEN

        val param: Call<List<Ride>> = service.findProcessDriver(ride)
        param.enqueue(this)
    }

    fun startRide(ride: Ride){

        type = "start"

/*        {
{
	"id": 0,
	"token": "shelter_movel#2021"
}
        }*/

        ride.token = WSConstants.TOKEN

        val param: Call<List<Ride>> = service.startRide(ride)
        param.enqueue(this)
    }

    fun acceptRide(ride: Ride){

        type = "accept"

/*        {
{
	"id": 0,
	"id_motorista": 4,
	"token": "shelter_movel#2021"
}
        }*/

        ride.token = WSConstants.TOKEN

        val param: Call<List<Ride>> = service.acceptRide(ride)
        param.enqueue(this)
    }

    fun finishRide(ride: Ride){

        type = "finish"

/*        {
{
	"id": 1,
	"id_motorista": 4,
	"token": "shelter_movel#2021"
}
        }*/

        ride.token = WSConstants.TOKEN

        val param: Call<List<Ride>> = service.finishRide(ride)
        param.enqueue(this)
    }

    fun findAllDriver(ride: Ride){

        type = "findAll"

/*        {
{
	"id_motorista": 4,
	"token": "shelter_movel#2021"
}  }*/

        ride.token = WSConstants.TOKEN

        val param: Call<List<Ride>> = service.findAllDriver(ride)
        param.enqueue(this)
    }

    fun cancelRidePassenger(ride: Ride){

        type = "cancel"

/*        {
{
	"id": 1,
	"id_motorista": 4,
	"token": "shelter_movel#2021"
}
        }*/

        ride.token = WSConstants.TOKEN

        val param: Call<List<Ride>> = service.cancelRideDriver(ride)
        param.enqueue(this)
    }

}