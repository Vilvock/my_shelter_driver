package br.com.app5m.appshelterdriver.ui.dialog.ride_flow

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.RideControl
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.models.User
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_bottom_view_choosedestination.*
import kotlinx.android.synthetic.main.dialog_bottom_view_vehicle.*
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class ChooseTypeVehicleFragDialog (private val bottomSheetDialogFragment: BottomSheetDialogFragment) :
    BottomSheetDialogFragment(), WSResult, RecyclerItemClickListener {

    private lateinit var useful: Useful
    private lateinit var rideControl: RideControl
    private lateinit var preferences: Preferences
    private lateinit var homeActContext: HomeAct

    private var vehicleList = ArrayList<Ride>()

    private lateinit var paymentMethodType : User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_view_vehicle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())
        preferences = Preferences(requireContext())
        rideControl = RideControl(requireContext(), this, useful)

        homeActContext = requireActivity() as HomeAct

//        homeActContext.rideLiveData.value?.let { rideControl.listVehicleTypes(it) }

//        configRecycler()
//
//        paymentMethods_ll.setOnClickListener {
//            val intent = Intent(requireActivity(), PaymentMethodsAct::class.java)
//            // send intent for result
//            requireActivity().startActivityForResult(intent, 2)
//
//        }

        next_bt.setOnClickListener {

            //validacoes

            if (homeActContext.rideLiveData.value!!.carType == null) {
                SingleToast.INSTANCE.show(requireContext(), "É necessário escolher o tipo de veiculo no qual você deseja fazer a viagem.", Toast.LENGTH_LONG)
                return@setOnClickListener
            }

//            if (paymentMethodType.cardId != "2") {
//
//                //cvv n ta vindo do banco?
//                homeActContext._rideLiveData.value!!.paymentMethod = "1"
//                homeActContext._rideLiveData.value!!.cardId = paymentMethodType.cardId
//                homeActContext._rideLiveData.value!!.cvv = paymentMethodType.cvv
//                homeActContext._rideLiveData.value!!.moipId = preferences.getUserData()!!.moipId
//
////            "card_id": "CRC-S4MZWXFWEGXR",
////            "cvv": "123",
////            "id_moip": "CUS-OA5QFB7AZT4T"
//            } else {
//
//                homeActContext._rideLiveData.value!!.paymentMethod = "2"  // 2 dinheiro 1 cartao
//            }

            Log.d("TAG", "typeVehiclePayment" + Gson().toJson(homeActContext.rideLiveData.value))

            homeActContext.notifyScreenStageChanged(HomeAct.MainScreenStage.DRIVER_SEARCH)
            bottomSheetDialogFragment.dismiss()

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2) {
            if (data != null) {
                paymentMethodType = (data.getSerializableExtra("paymentMethodType") as User?)!!

                Log.d("TAG", "payment: " + Gson().toJson(paymentMethodType))
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun rResponse(list: List<Ride>, type: String) {

        vehicleList.clear()
        vehicleList.addAll(list)

        typeVehicles_rv.adapter!!.notifyDataSetChanged()

    }


//    private fun configRecycler(){
//
//        val vehicleTypeAdapter = VehicleTypeAdapter(vehicleList, requireContext(), this)
//
//        typeVehicles_rv.apply {
//            setHasFixedSize(false)
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = vehicleTypeAdapter
//        }
//
//    }

    override fun onClickListenerVehicle(ride: Ride?) {

        homeActContext._rideLiveData.value!!.carType = ride?.carType

        Log.d("TAG", "carType" + ride?.carType)

    }


}