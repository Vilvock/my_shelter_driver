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
import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottom_view_initride.*


/**
 * A simple [Fragment] subclass.
 */
class InitRideFragDialog (private val rideFlowContainerBottomFrag: RideFlowContainerBottomFrag)
    : Fragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var rideControl: RideControl

    private lateinit var preferences: Preferences

    private lateinit var homeActContext: HomeAct

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_view_initride, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        useful = Useful(requireContext())
        preferences = Preferences(requireContext())
        rideControl = RideControl(requireContext(), this, useful)

        homeActContext = requireActivity() as HomeAct

        init_bt.setOnClickListener {

            useful.openLoading()

            val initRide = Ride()

            //diogo precisa me trazer vehicleboard e vehicle model no login
            if (homeActContext.rideLiveData.value!!.rideId == null) {

                initRide.id = homeActContext.rideLiveData.value!!.id
            } else {

                initRide.id = homeActContext.rideLiveData.value!!.rideId
            }

            Log.d("TAG", "init:")

            rideControl.startRide(initRide)
        }

        Glide.with(requireContext())
            .load(WSConstants.AVATAR_USER + homeActContext.rideLiveData.value!!.passengerAvatar).into(passengerAvatar_iv)

        passengerInfo_tv.text = homeActContext.rideLiveData.value!!.passengerName


        val origin = "Embarque: " + homeActContext.rideLiveData.value!!.originAddress
        val destination = "Destino: " + homeActContext.rideLiveData.value!!.destinationAddress
        val distance = "Distância: " + homeActContext.rideLiveData.value!!.distance + " " + homeActContext.rideLiveData.value!!.distanceInitials
        val duration = "Tempo estimado: " + homeActContext.rideLiveData.value!!.routeTime
        val payment = "Tipo de pagamento: " + homeActContext.rideLiveData.value!!.nameTypePayment
        val value = "Valor da corrida: " + homeActContext.rideLiveData.value!!.totalValue

        rideInfo_tv.text = "$origin\n$destination\n$distance\n$duration\n$payment\n$value"

    }

    override fun rResponse(list: List<Ride>, type: String) {

        useful.closeLoading()

        val rideInfo = list[0]

//        SingleToast.INSTANCE.show(requireActivity(), rideInfo.msg!!, Toast.LENGTH_LONG)

        if (rideInfo.status == "01") {
            homeActContext.isCameraLock = true
            homeActContext.notifyScreenStageChanged(HomeAct.MainScreenStage.RELOAD_OVERVIEW_STATEMENT)

        }

    }
}