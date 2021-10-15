package br.com.app5m.appshelterdriver.controller.webservice

import br.com.app5m.appshelterdriver.models.Document
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.models.UAddress
import br.com.app5m.appshelterdriver.models.User


interface WSResult {

    //user

    //    fun responseU(user: User?, type: String?){}
    fun uResponse(list: List<User>, type: String){}

    fun uAResponse(list: List<UAddress>, type: String){}

    fun dResponse(list: List<Document>, type: String){}

    fun rResponse(list: List<Ride>, type: String){}

    fun error(error: String){}

}