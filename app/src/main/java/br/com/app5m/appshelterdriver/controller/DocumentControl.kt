package br.com.app5m.appshelterdriver.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.controller.webservice.WebService
import br.com.app5m.appshelterdriver.models.Document
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
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

class DocumentControl(private val context: Context, private val result: WSResult, private val useful: Useful): Callback<List<Document>> {

    private val service = RetrofitInitializer().retrofit(
        true).create(WebService::class.java)
    private val preferences = Preferences(context)
    private var type = ""
    private lateinit var document: Document

    override fun onResponse(call: Call<List<Document>>, response: Response<List<Document>>) {
        if (response.isSuccessful){
            response.body()?.let { result.dResponse(it, type) }
        }else{
            useful.closeLoading()
            SingleToast.INSTANCE.show(context, "Ocorreu um erro não esperado, tente novamente mais tarde.",
                Toast.LENGTH_LONG)
            Log.d("error", "onFailure: " + response.message())
        }
    }

    override fun onFailure(call: Call<List<Document>>, t: Throwable) {
        useful.closeLoading()
        SingleToast.INSTANCE.show(context, "Não foi possível contatar o servidor.",
            Toast.LENGTH_LONG)
        Log.d("error", "onFailure: " + t.message)
    }

    fun listDocDriver(){

        type = "listDoc"

        document = Document()

        document.token = WSConstants.TOKEN

        val param: Call<List<Document>> = service.listDocDriver(preferences.getUserData()!!.id!!, document)
        param.enqueue(this)
    }

    fun updateDocument(file: File, idFile: String){

        type = "updateDoc"

        val id: RequestBody = idFile.toRequestBody(MultipartBody.FORM)

        val doc: MultipartBody.Part

        val requestFile: RequestBody = file.asRequestBody("image/".toMediaTypeOrNull())
        doc = MultipartBody.Part.createFormData("doc", file.name, requestFile)


        val param: Call<List<Document>> = service.updateDoc(id, doc)
        param.enqueue(this)
    }

}