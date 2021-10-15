package br.com.app5m.appshelterdriver.controller.webservice

import br.com.app5m.appshelterdriver.models.Document
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.models.UAddress
import br.com.app5m.appshelterdriver.models.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface WebService {

    //User

    @POST ("")
    fun fcm(@Body u: User): Call<List<User>>

    @POST("motoristas/login")
    fun login(@Body u: User): Call<List<User>>

    @POST("motoristas/save")
    fun register(@Body u: User): Call<List<User>>

    @POST("motoristas/listid/" + "{id}")
    fun listId(
        @Path("id") idUser: String,
        @Body u: User
    ): Call<List<User>>

    @POST("motoristas/updatepassword")
    fun updatePassword(@Body u: User): Call<List<User>>

    @POST("motoristas/update")
    fun updateUserData(@Body u: User): Call<List<User>>

    @Multipart
    @POST("motoristas/updateavatar")
    fun updateAvatar(@Part("id") id: RequestBody,
                     /*@Part("token") token: RequestBody,*/
                     @Part avatar: MultipartBody.Part): Call<List<User>>

    @POST("motoristas/recuperarsenha")
    fun recoverPassword(@Body u: User): Call<List<User>>

    @POST("motoristas/verificatoken")
    fun verifyToken(@Body u: User): Call<List<User>>

    @POST("motoristas/updatepasswordtoken")
    fun updatePasswordToken(@Body u: User): Call<List<User>>

    @POST("motoristas/updatelocation")
    fun updateLocation(@Body u: User): Call<List<User>>

    @POST("motoristas/updateonline")
    fun updateOnline(@Body u: User): Call<List<User>>

    //Address

    @POST("passageiros/saveendereco")
    fun saveAddress(@Body a: UAddress): Call<List<UAddress>>

    @POST("passageiros/updateendereco")
    fun updateAddressData(@Body a: UAddress): Call<List<UAddress>>

    @POST("passageiros/findenderecos/" + "{id}")
    fun listAddress(
        @Path("id") idUser: String,
        @Body a: UAddress
    ): Call<List<UAddress>>

    @POST("passageiros/deleteendereco")
    fun deleteAddress(@Body a: UAddress): Call<List<UAddress>>

    //document

    @POST("documentos/documentosmotorista/" + "{id}")
    fun listDocDriver(
        @Path("id") idUser: String,
        @Body d: Document
    ): Call<List<Document>>

    @Multipart
    @POST("/documentos/updatedocumento")
    fun updateDoc(@Part("id") id: RequestBody,
        /*@Part("token") token: RequestBody,*/
                     @Part doc: MultipartBody.Part): Call<List<Document>>


    //Ride

    @POST("corridas/findsolicitadas")
    fun findRidesRequests(@Body r: Ride): Call<List<Ride>>

    @POST("corridas/listtiposveiculo")
    fun listVehicleTypes(@Body r: Ride): Call<List<Ride>>

}