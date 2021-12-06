package br.com.app5m.appshelterdriver.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.controller.webservice.WebService
import br.com.app5m.appshelterdriver.models.UAddress
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import br.com.app5m.appshelterdriver.config.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UAddressControl(private val context: Context, private val result: WSResult, private val useful: Useful): Callback<List<UAddress>> {

    private val service = RetrofitInitializer().retrofit(
        true).create(WebService::class.java)
    private val preferences = Preferences(context)
    private var type = ""
    private lateinit var uAddress: UAddress

    override fun onResponse(call: Call<List<UAddress>>, response: Response<List<UAddress>>) {
        if (response.isSuccessful){
            response.body()?.let { result.uAResponse(it, type) }
        }else{
            useful.closeLoading()
            SingleToast.INSTANCE.show(context, "Ocorreu um erro não esperado, tente novamente mais tarde.",
                Toast.LENGTH_LONG)
            Log.d("error", "onFailure: " + response.message())
        }
    }

    override fun onFailure(call: Call<List<UAddress>>, t: Throwable) {
        useful.closeLoading()
        SingleToast.INSTANCE.show(context, "Não foi possível contatar o servidor.",
            Toast.LENGTH_LONG)
        Log.d("error", "onFailure: " + t.message)
    }

    fun saveAddress(uAddress: UAddress){

        type = "saveAddress"

        uAddress.token = WSConstants.TOKEN

        val param: Call<List<UAddress>> = service.saveAddress(uAddress)
        param.enqueue(this)
    }

    fun listUserAddress(idUser: String){

        type = "listAddress"

        uAddress.token = WSConstants.TOKEN

        val param: Call<List<UAddress>> = service.listAddress(idUser, uAddress)
        param.enqueue(this)
    }


    fun updateAddress(uAddress: UAddress){

        type = "updateAddress"
/*
        {
            "token": "shelter_movel#2021",
            "cep": "94814480",
            "cidade": "Alvorada",
            "estado": "RS",
            "endereco": "Rua Osvaldo Aranha",
            "numero": "159",
            "bairro": "Formoza",
            "id": 4
        }*/

        uAddress.token = WSConstants.TOKEN

        val param: Call<List<UAddress>> = service.updateAddressData(uAddress)
        param.enqueue(this)
    }

    fun deleteAddress(uAddress: UAddress){

        type = "saveAddress"
/*
        {
            "id": 2,
            "token": "shelter_movel#2021"
        }*/

        uAddress.token = WSConstants.TOKEN

        val param: Call<List<UAddress>> = service.deleteAddress(uAddress)
        param.enqueue(this)
    }

}