package br.com.app5m.appshelterdriver.helper

import br.com.app5m.appshelterdriver.model.CreditCard
import br.com.app5m.appshelterdriver.model.CreditCardFlag
import br.com.app5m.appshelterdriver.model.Local
import br.com.app5m.appshelterdriver.model.VehicleColor


interface RecyclerItemClickListener {


    fun onClickListenerLocalDestination(local: Local){
        //optional body
    }
    fun onClickListenerMyAdresses(local: Local){
        //optional body
    }
    fun onClickListenerVeicleColor(vehicleColor: VehicleColor){
        //optional body
    }
    fun onClickListenerMyPayMethods(creditCard: CreditCard){
        //optional body
    }
    fun onClickListenerCreditCardFlag(CreditCardFlag: CreditCardFlag){
        //optional body
    }
}