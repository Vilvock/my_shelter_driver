package br.com.app5m.appshelterdriver.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import br.com.app5m.appshelterdriver.config.RetrofitInitializer
import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.controller.webservice.WebService
import br.com.app5m.appshelterdriver.models.Rating
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RatingControl(private val context: Context, private val result: WSResult, private val useful: Useful):
    Callback<List<Rating>> {

    private val service = RetrofitInitializer().retrofit(
        true).create(WebService::class.java)
    private val preferences = Preferences(context)
    private var type = ""
    private lateinit var rating: Rating


    override fun onResponse(call: Call<List<Rating>>, response: Response<List<Rating>>) {
        if (response.isSuccessful){
            response.body()?.let { result.raResponse(it, type) }
        }else{
            useful.closeLoading()
            SingleToast.INSTANCE.show(context, "Ocorreu um erro não esperado, tente novamente mais tarde.",
                Toast.LENGTH_LONG)
            Log.d("error", "onFailure: " + response.message())
        }
    }

    override fun onFailure(call: Call<List<Rating>>, t: Throwable) {
        useful.closeLoading()
        SingleToast.INSTANCE.show(context, "Não foi possível contatar o servidor.",
            Toast.LENGTH_LONG)
        Log.d("error", "onFailure: " + t.message)
    }

    fun listAverage(idDriver: String){

        type = "list_average"

        rating = Rating()

        rating.idDriver = idDriver
        rating.token = WSConstants.TOKEN

        val param: Call<List<Rating>> = service.averageDriverRating(rating)
        param.enqueue(this)
    }

    fun findRatings(rating: Rating){

        type = "find_ratings"

        rating.token = WSConstants.TOKEN

        val param: Call<List<Rating>> = service.findRatings(rating)
        param.enqueue(this)
    }


}