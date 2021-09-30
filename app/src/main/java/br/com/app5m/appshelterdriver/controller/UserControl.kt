package br.com.app5m.appshelterdriver.controller

import android.content.Context
import android.util.Log
import br.com.app5m.appshelterpassenger.config.RetrofitInitializer
import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.controller.webservice.WebService
import br.com.app5m.appshelterdriver.helper.Preferences
import br.com.app5m.appshelterdriver.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserControl(context: Context, private val result: WSResult): Callback<List<User>> {

    private val service = RetrofitInitializer().retrofit(
        true).create(WebService::class.java)
    private val preferences = Preferences(context)
    private var type = ""
    private val user = User()

    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
        if (response.isSuccessful){
            response.body()?.let { result.uResponse(it, type) }
        }else{
            result.error("Erro não esperado.")
        }
    }

    override fun onFailure(call: Call<List<User>>, t: Throwable) {
        result.error("Não foi possível contatar o servidor.")
        Log.d("error", "onFailure: " + t.message)
    }

//    fun login(user: User){
//
//        type = "login"
//
//        user.token = WSConstants().token
//
//        val param: Call<List<User>> = service.login(user)
//        param.enqueue(this)
//    }
//
//    fun register(user: User){
//
//        type = "register"
//        user.token = WSConstants().token
//
//        val param: Call<List<User>> = service.register(user)
//        param.enqueue(this)
//    }
//
//    fun listId(){
//
//        type = "listId"
//
//        user.id = preferences.getUserData()!!.id
//        user.token = WSConstants().token
//
//        val param: Call<List<User>> = service.listId("",user)
//        param.enqueue(this)
//    }
//
//    fun updatePassword(password: String){
//
//        type = "updatePassword"
//
//        user.id = preferences.getUserData()!!.id
//        user.password = password
//        user.token = WSConstants().token
//
//        val param: Call<List<User>> = service.updatePassword(user)
//        param.enqueue(this)
//    }
//
//    fun updateUserData(user: User){
//
//        /*
//    {
//        "id": 37342,
//        "nome": "Cadastro Teste",
//        "email": "teste@teste.com",
//        "celular": "51995087890",
//        "token": "pluralTv2021@"
//    }*/
//
//        type = "updateUserData"
//
//        user.id = preferences.getUserData()!!.id
//        user.token = WSConstants().token
//
//        val param: Call<List<User>> = service.updateUserData(user)
//        param.enqueue(this)
//    }
//
//
//    fun fcm(user: User) {
//
//        type = "fcm"
//
//        user.id = preferences.getUserData()!!.id
//
//        val param: Call<List<User>> = service.fcm(user)
//        param.enqueue(object : Callback<List<User>> {
//            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
//                if (response.isSuccessful) {
//                    if (response.body() != null) {
//                        response.body()?.let { result.uResponse(it, type) }
//                        //testando com list no result
//                    }
//                } else {
//                    Log.i("erro", "Erro não esperado.")
//                }
//            }
//
//            override fun onFailure(call: Call<List<User>>, t: Throwable) {
//                Log.i("erro", "Não foi possível contatar o servidor.")
//            }
//        })
//
//
//    }

}