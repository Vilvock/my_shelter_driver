package br.com.app5m.appshelterdriver.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.controller.webservice.WebService
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import br.com.app5m.appshelterpassenger.config.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RideControl(private val context: Context, private val result: WSResult, private val useful: Useful): Callback<List<Ride>> {

    private val service = RetrofitInitializer().retrofit(
        true).create(WebService::class.java)
    private val preferences = Preferences(context)
    private var type = ""
    private lateinit var ride : Ride

    override fun onResponse(call: Call<List<Ride>>, response: Response<List<Ride>>) {
        if (response.isSuccessful){
            response.body()?.let { result.rResponse(it, type) }
        }else{
            useful.closeLoading()
            SingleToast.INSTANCE.show(context, "Ocorreu um erro não esperado, tente novamente mais tarde.",
                Toast.LENGTH_LONG)
            Log.d("error", "onFailure: " + response.message())
        }
    }

    override fun onFailure(call: Call<List<Ride>>, t: Throwable) {
        useful.closeLoading()
        SingleToast.INSTANCE.show(context, "Não foi possível contatar o servidor.",
            Toast.LENGTH_LONG)
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

        ride.driverId = preferences.getUserData()!!.id
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

    fun findAllDriver(){

        type = "findAll"

        ride = Ride()

        ride.driverId = preferences.getUserData()!!.id
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