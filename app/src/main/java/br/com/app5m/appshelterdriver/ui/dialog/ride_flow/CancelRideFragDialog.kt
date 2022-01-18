package br.com.app5m.appshelterdriver.ui.dialog.ride_flow

import android.os.Bundle
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
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.default_bottom_sheet_dialog_container.*
import kotlinx.android.synthetic.main.dialog_bottom_view_cancelride.*


/**
 * A simple [Fragment] subclass.
 */
class CancelRideFragDialog (private val rideFlowContainerBottomFrag: RideFlowContainerBottomFrag) :
    Fragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var rideControl: RideControl
    private lateinit var homeActContext: HomeAct

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_view_cancelride, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rideFlowContainerBottomFrag.close_imageButton.visibility = View.GONE
        useful = Useful(requireContext())
        rideControl = RideControl(requireContext(), this, useful)

        homeActContext = requireActivity() as HomeAct

        continueRide_bt.setOnClickListener {
            when (homeActContext.screenStageLiveData.value) {
                HomeAct.MainScreenStage.WAITING_PICKUP -> {

                    useful.showRideFlowFrag(requireActivity().supportFragmentManager, "waiting")

                }

                HomeAct.MainScreenStage.ONGOING_RIDE -> {

                    useful.showRideFlowFrag(requireActivity().supportFragmentManager, "ongoing")

                }

                else -> {}
            }
        }

        cancel_bt.setOnClickListener {

            if (homeActContext.screenStageLiveData.value == HomeAct.MainScreenStage.WAITING_PICKUP
                || homeActContext.screenStageLiveData.value == HomeAct.MainScreenStage.ONGOING_RIDE) {

                val cancelRide = Ride()

                if (homeActContext.rideLiveData.value!!.rideId == null) {

                    cancelRide.id = homeActContext.rideLiveData.value!!.id
                } else {

                    cancelRide.id = homeActContext.rideLiveData.value!!.rideId
                }

                rideControl.cancelRidePassenger(cancelRide)

            }  else {

                homeActContext.isCameraLock = true
                homeActContext.notifyScreenStageChanged(HomeAct.MainScreenStage.RELOAD_OVERVIEW_STATEMENT)
            }

        }

    }

    override fun rResponse(list: List<Ride>, type: String) {

        val rideInfo = list[0]

        if (rideInfo.status == "01") {
            homeActContext.isCameraLock = true
            homeActContext.notifyScreenStageChanged(HomeAct.MainScreenStage.RELOAD_OVERVIEW_STATEMENT)

        } else {
            SingleToast.INSTANCE.show(requireActivity(), rideInfo.msg!!, Toast.LENGTH_LONG)
        }

    }

}