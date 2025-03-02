package br.com.app5m.appshelterdriver.ui.dialog.ride_flow

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.createMapChooser
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.default_bottom_sheet_dialog_container.*
import kotlinx.android.synthetic.main.dialog_bottom_view_acceptride.*
import kotlinx.android.synthetic.main.dialog_bottom_view_finishedridedetails.*
import kotlinx.android.synthetic.main.dialog_bottom_view_initride.*
import kotlinx.android.synthetic.main.dialog_bottom_view_ongoing.*
import kotlinx.android.synthetic.main.dialog_bottom_view_ongoing.passengerAvatar_iv
import kotlinx.android.synthetic.main.dialog_bottom_view_ongoing.passengerInfo_tv
import kotlinx.android.synthetic.main.dialog_bottom_view_ongoing.rideInfo_tv


/**
 * A simple [Fragment] subclass.
 */
class OnGoingRideFragDialog (private val rideFlowContainerBottomFrag: RideFlowContainerBottomFrag) : Fragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var rideControl: RideControl

    private lateinit var homeActContext: HomeAct

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_view_ongoing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())
        rideControl = RideControl(requireContext(), this, useful)

        homeActContext = requireActivity() as HomeAct

        finish_bt.setOnClickListener{
            useful.openLoading()

            val finishRide = Ride()

            //diogo precisa me trazer vehicleboard e vehicle model no login
            if (homeActContext.rideLiveData.value!!.rideId == null) {

                finishRide.id = homeActContext.rideLiveData.value!!.id
            } else {

                finishRide.id = homeActContext.rideLiveData.value!!.rideId
            }

            rideControl.finishRide(finishRide)

        }

        openMapNavigation_bt.setOnClickListener(::openMapChooser)

        var passengerAvatar = homeActContext.rideLiveData.value!!.userAvatar
        if (homeActContext.rideLiveData.value!!.userAvatar == null || homeActContext.rideLiveData.value!!.userName == "") {
            passengerAvatar = homeActContext.rideLiveData.value!!.passengerAvatar
        }

        Glide.with(requireContext())
            .load(WSConstants.AVATAR_USER + passengerAvatar).into(passengerAvatar_iv)

        var passengerName = homeActContext.rideLiveData.value!!.userName
        if (homeActContext.rideLiveData.value!!.userName == null || homeActContext.rideLiveData.value!!.userName == "") {
            passengerName = homeActContext.rideLiveData.value!!.passengerName
        }

        passengerInfo_tv.text = passengerName


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
            homeActContext.notifyScreenStageChanged(HomeAct.MainScreenStage.FINISH_RIDE)

        }

    }

    private fun openMapChooser(v: View) {
        try {

            val lat = homeActContext.rideLiveData.value?.destinationLatitude ?: return
            val lng = homeActContext.rideLiveData.value?.destinationLongitude ?: return

            val gmmIntentUri = Uri.parse("geo:$lat,$lng?q=$lat,$lng")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            startActivity(mapIntent.createMapChooser(requireContext()))

        } catch (ex: ActivityNotFoundException) {
            // If Waze is not installed, open it in Google Play:
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"))
            startActivity(intent)
        }
    }



}