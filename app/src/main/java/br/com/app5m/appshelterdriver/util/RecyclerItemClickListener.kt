package br.com.app5m.appshelterdriver.util

import br.com.app5m.appshelterdriver.models.Bank
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.models.User
import java.text.FieldPosition


interface RecyclerItemClickListener {

    fun onClickListenerVehicle(ride: Ride?){
        //optional body
    }

    fun onClickListenerPaymentMethod(user: User?){
        //optional body
    }

    fun onClickListenerBank(positionBank: Int){
        //optional body
    }


}