package br.com.app5m.appshelterdriver.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.controller.webservice.WebService
import br.com.app5m.appshelterdriver.models.Bank
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import br.com.app5m.appshelterpassenger.config.RetrofitInitializer
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

/*        {
{
	"token": "shelter_movel#2021",
	"id_user": 11,
	"type": 1,
	"tipo_chave_pix": "CPF",
	"chave_pix": "884.192.000-93",
	"banco": "Bradesco",
	"agencia": "0736",
	"agencia_d": "1",
	"cc": "0151152",
	"cc_d": "1",
	"tipo_conta": "corrente"
}
        }*/

        val param: Call<List<Bank>> = service.saveBank(bank)
        param.enqueue(this)
    }


    fun listBanksUser(){

        type = "listBanks"

        val param: Call<List<Bank>> = service.listBanks(preferences.getUserData()!!.id!!)
        param.enqueue(this)
    }

    fun updateBank(bank: Bank){

        type = "update"

/*        {
{
	"token": "shelter_movel#2021",
	"id_conta": 1,
	"type": 1,
	"tipo_chave_pix": "CPF",
	"chave_pix": "884.192.000-93",
	"banco": "NU pagamentos",
	"agencia": "0736",
	"agencia_d": "1",
	"cc": "0151152",
	"cc_d": "1",
	"tipo_conta": "corrente"
}
        }*/

        val param: Call<List<Bank>> = service.updateBank(bank)
        param.enqueue(this)
    }



}