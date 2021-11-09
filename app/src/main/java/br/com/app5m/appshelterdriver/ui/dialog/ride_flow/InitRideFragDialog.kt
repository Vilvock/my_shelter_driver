package br.com.app5m.appshelterdriver.ui.dialog.ride_flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.default_bottom_sheet_dialog_container.*
import kotlinx.android.synthetic.main.loading.*


/**
 * A simple [Fragment] subclass.
 */
class InitRideFragDialog (private val bottomSheetDialogFragment: BottomSheetDialogFragment)
    : BottomSheetDialogFragment(), WSResult {

//    private lateinit var useful: Useful
//    private lateinit var rideControl: RideControl
//    private lateinit var mapControl: MapControl
//
//    private lateinit var preferences: Preferences
//
//    private lateinit var homeActContext: HomeAct

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_view_initride, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetDialogFragment.close_imageButton.visibility = View.GONE
//        useful = Useful(requireContext())
//        rideControl = RideControl(requireContext(), this, useful)
//        mapControl = MapControl(requireContext(), this, useful)
//
//        homeActContext = requireActivity() as HomeAct
//
//
//        if (homeActContext.rideLiveData.value!!.rideStatus == null) {
//            val mapRoutes = Map()
//
//            mapRoutes.originLatitude = homeActContext.rideLiveData.value!!.originLatitude
//            mapRoutes.originLongitude = homeActContext.rideLiveData.value!!.originLongitude
//            mapRoutes.destinationLatitude = homeActContext.rideLiveData.value!!.destinationLatitude
//            mapRoutes.destinationLongitude = homeActContext.rideLiveData.value!!.destinationLongitude
//
//            mapControl.searchRouteByCoordinate(mapRoutes)
//        }
//
//        loading.setOnClickListener {
//
//            homeActContext.notifyScreenStageChanged(HomeAct.MainScreenStage.WAITING_PICKUP)
//            bottomSheetDialogFragment.dismiss()
//
//        }
//
//        swipe_bt.setOnStateChangeListener {
//
//            useful.showDefaultDialogView(requireActivity().supportFragmentManager, "cancel")
//            bottomSheetDialogFragment.dismiss()
//        }
//
//
//    }
//
//    override fun mResponse(response: Map, type: String) {
//
//        val routesDetails = response
//
//        if (type == "searchByCoordinate") {
//            if (routesDetails.status == "OK") {
//
//                //               "id_de": 11,
//                //               "endereco_in": "Av. Pres. Getúlio Vargas 5019 Alvorada",
//                //               "endereco_out": "Av. Tiradentes 81 Alvorada",
//                //               "latitude_in": "-30.003922",
//                //               "longitude_in": "-51.052373",
//                //               "latitude_out": "-30.0137134",
//                //               "longitude_out": "-51.0847039",
//                //               "distancia": "4",
//                //               "distancia_sigla": "km",
//                //               "tempo_rota": "10",
//                //               "tipo_carro": 1,
//                //               "forma_pagamento": 2
//
//                val requestRide = Ride()
//
//                requestRide.originAddress = homeActContext.rideLiveData.value!!.origin
//                requestRide.destinationAddress = homeActContext.rideLiveData.value!!.destination
//                requestRide.originLatitude = homeActContext.rideLiveData.value!!.originLatitude
//                requestRide.originLongitude = homeActContext.rideLiveData.value!!.originLongitude
//                requestRide.destinationLatitude = homeActContext.rideLiveData.value!!.destinationLatitude
//                requestRide.destinationLongitude = homeActContext.rideLiveData.value!!.destinationLongitude
//                requestRide.distance = routesDetails.routeList[0].legList[0].distance.text!!.replace(" km", "")
//                requestRide.distanceInitials = "km"
//                requestRide.routeTime = routesDetails.routeList[0].legList[0].duration.text!!.replace(" mins", "")
//                requestRide.carType = homeActContext.rideLiveData.value!!.carType
//                requestRide.paymentMethod = homeActContext.rideLiveData.value!!.paymentMethod
//
//                //            "card_id": "CRC-S4MZWXFWEGXR",
//                //            "cvv": "123",
//                //            "id_moip": "CUS-OA5QFB7AZT4T"
//                if (homeActContext.rideLiveData.value!!.paymentMethod == "1") {
//                    requestRide.cardId = homeActContext.rideLiveData.value!!.cardId
//                    requestRide.cardId = homeActContext.rideLiveData.value!!.cvv
//                    requestRide.cardId = preferences.getUserData()!!.moipId
//                }
//
//                Log.d("TAG", "request ride" + Gson().toJson(requestRide))
//
//                rideControl.requestRide(requestRide)
//            } else {
//
//                SingleToast.INSTANCE.show(requireContext(), "Não foi possível traçar uma rota para sua viagem, tente novamente mais tarde.", Toast.LENGTH_LONG)
//                bottomSheetDialogFragment.dismiss()
//            }
//        }
//
//
//    }

//    override fun rResponse(list: List<Ride>, type: String) {
//
//        val solicitedRideInfo = list[0]
//
//        homeActContext._rideLiveData.value = solicitedRideInfo
//
////        [
////            {
////                "status":"01",
////                "msg":"Corrida solicitada.",
////                "id_corrida":105,
////                "id_de":"11",
////                "endereco_in":"Alvorada - RS, Brasil",
////                "endereco_out":"Porto Alegre, RS, Brasil",
////                "latitude_in":"-29.999906655234",
////                "longitude_in":"-51.0784874483943",
////                "latitude_out":"-30.0368176",
////                "longitude_out":"-51.2089887",
////                "distancia":"23.6 km",
////                "distancia_sigla":"km",
////                "tempo_rota":null,
////                "polyline":null,
////                "valor_total":" R$ 5,50",
////                "status_corrida":1,
////                "forma_pagamento":"Dinheiro",
////                "tipo_carro":"1"
////            }
////        ]
//
    }


}