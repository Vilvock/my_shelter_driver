package br.com.app5m.appshelterdriver.controller.webservice

import br.com.app5m.appshelterdriver.models.User


interface WSResult {

    //user

//    fun responseU(user: User?, type: String?){}
    fun uResponse(list: List<User>, type: String){}

    fun error(error: String){}

}