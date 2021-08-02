package br.com.app5m.appshelterdriver.helper

import br.com.app5m.appshelterdriver.model.*


interface RecyclerItemClickListener {


    fun onClickListenerLocalDestination(local: Local){
        //optional body
    }
    fun onClickListenerMyAdresses(local: Local){
        //optional body
    }
    fun onClickListenerMyPromotions(promotion: Promotion){
        //optional body
    }
    fun onClickListenerDocumentStatus(documentDriver: DriverDocument){
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