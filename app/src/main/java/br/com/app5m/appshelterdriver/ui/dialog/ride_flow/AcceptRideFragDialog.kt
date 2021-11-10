package br.com.app5m.appshelterdriver.ui.dialog.ride_flow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.RideControl
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottom_view_acceptride.*


/**
 * A simple [Fragment] subclass.
 */
class AcceptRideFragDialog (private val bottomSheetDialogFragment: BottomSheetDialogFragment) :
    BottomSheetDialogFragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var rideControl: RideControl

    private lateinit var preferences: Preferences

    private lateinit var homeActContext: HomeAct

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_view_finishedridedetails, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())
        preferences = Preferences(requireContext())
        rideControl = RideControl(requireContext(), this, useful)

        val homeActContext = requireActivity() as HomeAct

        accept_bt.setOnClickListener {

            val acceptRide = Ride()

            //diogo precisa me trazer vehicleboard e vehicle model no login
            acceptRide.id = homeActContext.rideLiveData.value!!.rideId
            acceptRide.vehicleBoard = "teste"
            acceptRide.vehicleModel = "teste"
//            acceptRide.vehicleBoard = preferences.getUserData()!!.vehicle.board
//            acceptRide.vehicleModel = preferences.getUserData()!!.vehicle.model

            Log.d("TAG", "accept:")

            rideControl.acceptRide(acceptRide)
        }


    }

    override fun rResponse(list: List<Ride>, type: String) {

        val rideInfo = list[0]

        if (rideInfo.status == "01") {
            homeActContext.isCameraLock = true
            homeActContext.notifyScreenStageChanged(HomeAct.MainScreenStage.WAITING_PICKUP)

        }

        SingleToast.INSTANCE.show(requireContext(), rideInfo.msg!!, Toast.LENGTH_LONG)
        bottomSheetDialogFragment.dismiss()

    }

}