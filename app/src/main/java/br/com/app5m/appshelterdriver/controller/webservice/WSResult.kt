package br.com.app5m.appshelterdriver.controller.webservice

import br.com.app5m.appshelterdriver.models.*


interface WSResult {

    //user

    //    fun responseU(user: User?, type: String?){}
    fun uResponse(list: List<User>, type: String){}

    fun uAResponse(list: List<UAddress>, type: String){}

    fun dResponse(list: List<Document>, type: String){}

    fun rResponse(list: List<Ride>, type: String){}

    fun raResponse(list: List<Rating>, type: String){}

    fun bResponse(list: List<Bank>, type: String){}

    fun error(error: String){}

}