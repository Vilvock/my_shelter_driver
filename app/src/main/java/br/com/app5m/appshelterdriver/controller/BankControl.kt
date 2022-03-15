package br.com.app5m.appshelterdriver.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.controller.webservice.WebService
import br.com.app5m.appshelterdriver.models.Bank
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import br.com.app5m.appshelterdriver.config.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BankControl(private val context: Context, private val result: WSResult, private val useful: Useful): Callback<List<Bank>> {

    private val service = RetrofitInitializer().retrofit(
        true).create(WebService::class.java)
    private val preferences = Preferences(context)
    private var type = ""
    private lateinit var bank : Bank

    override fun onResponse(call: Call<List<Bank>>, response: Response<List<Bank>>) {
        if (response.isSuccessful){
            response.body()?.let { result.bResponse(it, type) }
        }else{
            useful.closeLoading()
            SingleToast.INSTANCE.show(context, "Ocorreu um erro não esperado, tente novamente mais tarde.",
                Toast.LENGTH_LONG)
            Log.d("error", "onFailure: " + response.message())
        }
    }

    override fun onFailure(call: Call<List<Bank>>, t: Throwable) {
        useful.closeLoading()
        SingleToast.INSTANCE.show(context, "Não foi possível contatar o servidor.",
            Toast.LENGTH_LONG)
        Log.d("error", "onFailure: " + t.message)
    }


    fun saveBank(bank: Bank){

        type = "save"

        bank.idUser = preferences.getUserData()!!.id
        bank.token = WSConstants.TOKEN

        val param: Call<List<Bank>> = service.saveBank(bank)
        param.enqueue(this)
    }


    fun listBanksUser(){

        type = "listBanks"

        bank = Bank()
        bank.token = WSConstants.TOKEN

        val param: Call<List<Bank>> = service.listBanks(preferences.getUserData()!!.id!!, bank)
        param.enqueue(this)
    }

    fun updateBank(bank: Bank){

        type = "update"

        bank.token = WSConstants.TOKEN

        val param: Call<List<Bank>> = service.updateBank(bank)
        param.enqueue(this)
    }

    fun deleteBank(idBank: String){

        type = "delete"

        bank = Bank()
        bank.token = WSConstants.TOKEN

        val param: Call<List<Bank>> = service.deleteBank(idBank, bank)
        param.enqueue(this)
    }


}