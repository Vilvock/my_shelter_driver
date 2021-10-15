package br.com.app5m.appshelterdriver.controller

import android.content.Context
import android.util.Log
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.controller.webservice.WebService
import br.com.app5m.appshelterdriver.helper.Preferences
import br.com.app5m.appshelterdriver.models.Document
import br.com.app5m.appshelterpassenger.config.RetrofitInitializer
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class DocumentControl(context: Context, private val result: WSResult): Callback<List<Document>> {


    //n sei se sera necessario no motora, se ser ja tem

    private val service = RetrofitInitializer().retrofit(
        true).create(WebService::class.java)
    private val preferences = Preferences(context)
    private var type = ""
    private val document = Document()

    override fun onResponse(call: Call<List<Document>>, response: Response<List<Document>>) {
        if (response.isSuccessful){
            response.body()?.let { result.dResponse(it, type) }
        }else{
            result.error("Erro não esperado.")
        }
    }

    override fun onFailure(call: Call<List<Document>>, t: Throwable) {
        result.error("Não foi possível contatar o servidor.")
        Log.d("error", "onFailure: " + t.message)
    }

    fun listDocDriver(){

        type = "listDoc"

        document.token = WSConstants().TOKEN

        val param: Call<List<Document>> = service.listDocDriver("", document)
        param.enqueue(this)
    }

    fun updateDocument(idUser: String, file: File){

        type = "updateDoc"

        val id: RequestBody = idUser.toRequestBody(MultipartBody.FORM)
//        val token = WSConstants().token.toRequestBody(MultipartBody.FORM)

        val doc: MultipartBody.Part

        val requestFile: RequestBody = file.asRequestBody("image/".toMediaTypeOrNull())
        doc = MultipartBody.Part.createFormData("document", file.name, requestFile)


        val param: Call<List<Document>> = service.updateDoc(id, /*token,*/ doc)
        param.enqueue(this)
    }

}