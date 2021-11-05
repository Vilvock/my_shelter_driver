package br.com.app5m.appshelterdriver.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
import br.com.app5m.appshelterpassenger.config.RetrofitInitializer
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.controller.webservice.WebService
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import br.com.app5m.appshelterdriver.models.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UserControl(private val context: Context, private val result: WSResult, private val useful: Useful): Callback<List<User>> {

    private val service = RetrofitInitializer().retrofit(
        true).create(WebService::class.java)
    private val preferences = Preferences(context)
    private var type = ""
    private lateinit var user: User

    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
        if (response.isSuccessful){
            response.body()?.let { result.uResponse(it, type) }
        }else{
            SingleToast.INSTANCE.show(context, "Ocorreu um erro não esperado, tente novamente mais tarde.",
                Toast.LENGTH_LONG)
            Log.d("error", "onFailure: " + response.message())
        }
    }

    override fun onFailure(call: Call<List<User>>, t: Throwable) {
        useful.closeLoading()
        SingleToast.INSTANCE.show(context, "Não foi possível contatar o servidor.",
            Toast.LENGTH_LONG)
        Log.d("error", "onFailure: " + t.message)
    }

    fun login(user: User){

        type = "login"


/*        {
            "email": "diogocosta.js@gmail.com",
            "password": "dig1208s",
            "token": "shelter_movel#2021"
        }*/

        user.token = WSConstants.TOKEN

        val param: Call<List<User>> = service.login(user)
        param.enqueue(this)
    }

    fun register(user: User){

        type = "register"

/*

{
    "token": "shelter_movel#2021",
    "nome": "José",
    "genero": 1,
    "email": "jose@gmail.com",
    "tipo_pessoa": 1,
    "cpf": "761.548.216-08",
    "data_nascimento": "12/08/1991",
    "telefone": "(51)34432000",
    "celular": "(51)995087890",
    "latitude": "-29.9980309",
    "longitude": "-51.0760357",
    "veiculo":{
        "marca": "Ford",
        "modelo": "Mustang",
        "placa": "IQQ3344",
        "cor": "Vermelho",
        "ano": "2015",
        "ano_modelo": "2015"
    }
}

        }*/


        user.token = WSConstants.TOKEN

        val param: Call<List<User>> = service.register(user)
        param.enqueue(this)
    }

    fun listId(){

        type = "listId"

        user = User()

        user.id = preferences.getUserData()!!.id
        user.token = WSConstants.TOKEN

        val param: Call<List<User>> = service.listId(user.id!! ,user)
        param.enqueue(this)
    }

    fun updatePassword(password: String){

        type = "updatePassword"

        user.id = preferences.getUserData()!!.id
        user.password = password
        user.token = WSConstants.TOKEN

        val param: Call<List<User>> = service.updatePassword(user)
        param.enqueue(this)
    }

    fun updateUserData(user: User){
/*
        {
            "nome": "Teste de Motorista",
            "email": "diogocosta.js@gmail.com",
            "cpf": "761.548.216-03",
            "data_nascimento": "12/08/1996",
            "celular": "(51)995087890",
            "id": 7,
            "token": "shelter_movel#2021"
        }*/

        type = "updateUserData"

        user.id = preferences.getUserData()!!.id
        user.token = WSConstants.TOKEN

        val param: Call<List<User>> = service.updateUserData(user)
        param.enqueue(this)
    }

    fun updateAvatar(idUser: String, file: File){

        type = "updateAvatar"

        val id: RequestBody = idUser.toRequestBody(MultipartBody.FORM)
//        val token = WSConstants().token.toRequestBody(MultipartBody.FORM)

        val avatar: MultipartBody.Part

        val requestFile: RequestBody = file.asRequestBody("image/".toMediaTypeOrNull())
        avatar = MultipartBody.Part.createFormData("avatar", file.name, requestFile)


        val param: Call<List<User>> = service.updateAvatar(id, /*token,*/ avatar)
        param.enqueue(this)
    }

    fun recoverPassword(user: User){

        type = "recover"


/*        {
            "email": "diogocosta.js@gmail.com",
        }*/


        val param: Call<List<User>> = service.recoverPassword(user)
        param.enqueue(this)
    }

    fun verifyToken(user: User){

        type = "verify"
/*
        {
            "token_senha": "45d6637b718d0f24a237069fe41b0db4"
        }*/


        val param: Call<List<User>> = service.verifyToken(user)
        param.enqueue(this)
    }

    fun updatePasswordByToken(user: User){

        type = "updateTokenPassword"
/*
    {
    "token_senha": "45d6637b718d0f24a237069fe41b0db4",
    "password": "dig1208s"
}*/


        val param: Call<List<User>> = service.updatePasswordToken(user)
        param.enqueue(this)
    }

    fun updateLocation(user: User){

        type = "updateLocation"
 /*       {
            "latitude": "-30.0036668",
            "longitude": "-51.0546295",
            "id_motorista": "9"
        }*/

        user.driverId = preferences.getUserData()!!.id

        val param: Call<List<User>> = service.updateLocation(user)
        param.enqueue(this)
    }

    fun updateStatusOnline(user: User){

        type = "updateStatus"

        user.driverId = preferences.getUserData()!!.id
        user.token = WSConstants.TOKEN


        val param: Call<List<User>> = service.updateOnline(user)
        param.enqueue(this)
    }


    fun listCredit(){

        type = "listCredit"

        user = User()

        user.id = preferences.getUserData()!!.id
        user.token = WSConstants.TOKEN

        val param: Call<List<User>> = service.listCredit(user.id!!, user)
        param.enqueue(this)
    }

    fun listReceipts(){

        type = "listReceipts"

        user = User()

        user.id = preferences.getUserData()!!.id
        user.token = WSConstants.TOKEN

        val param: Call<List<User>> = service.listReceipts(user.id!!, user)
        param.enqueue(this)
    }


    fun saveFcm(user: User) {

        type = "fcm"

        user.id = "11"
//        user.id = preferences.getUserData()!!.id

        val param: Call<List<User>> = service.fcm(user)
        param.enqueue(this)


    }

}