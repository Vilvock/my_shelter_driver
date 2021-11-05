package br.com.app5m.appshelterdriver.util

import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.models.User


interface RecyclerItemClickListener {

    fun onClickListenerVehicle(ride: Ride?){
        //optional body
    }

    fun onClickListenerPaymentMethod(user: User?){
        //optional body
    }


}