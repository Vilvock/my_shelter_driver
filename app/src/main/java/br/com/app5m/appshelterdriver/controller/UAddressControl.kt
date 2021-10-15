package br.com.app5m.appshelterdriver.controller

import android.content.Context
import android.util.Log
import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.controller.webservice.WebService
import br.com.app5m.appshelterdriver.helper.Preferences
import br.com.app5m.appshelterdriver.models.UAddress
import br.com.app5m.appshelterpassenger.config.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UAddressControl(context: Context, private val result: WSResult): Callback<List<UAddress>> {


    //n sei se sera necessario no motora, se ser ja tem

    private val service = RetrofitInitializer().retrofit(
        true).create(WebService::class.java)
    private val preferences = Preferences(context)
    private var type = ""
    private val uAddress = UAddress()

    override fun onResponse(call: Call<List<UAddress>>, response: Response<List<UAddress>>) {
        if (response.isSuccessful){
            response.body()?.let { result.uAResponse(it, type) }
        }else{
            result.error("Erro não esperado.")
        }
    }

    override fun onFailure(call: Call<List<UAddress>>, t: Throwable) {
        result.error("Não foi possível contatar o servidor.")
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