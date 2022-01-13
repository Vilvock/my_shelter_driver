package br.com.app5m.appshelterdriver.ui.dialog.ride_flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.default_bottom_sheet_dialog_container.*

import android.util.Log
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.MapBottomPaddingDelegate
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
import br.com.app5m.appshelterdriver.util.Useful


/**
 * A simple [Fragment] subclass.
 */
class RideFlowContainerBottomFrag: Fragment() {


    private lateinit var useful: Useful

    private lateinit var fragment: Fragment

    private lateinit var homeActContext: HomeAct


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rideflow_bottom_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())
        homeActContext = requireActivity() as HomeAct

        if (homeActContext.screenStageLiveData.value == HomeAct.MainScreenStage.WAITING_PICKUP
            || homeActContext.screenStageLiveData.value == HomeAct.MainScreenStage.ONGOING_RIDE) {
            sheet_container.post {
                (requireActivity() as? MapBottomPaddingDelegate)?.setMapVerticalPadding(
                    sheet_container.height
                )
            }
        }

        val transaction = childFragmentManager.beginTransaction()

        when(tag) {

            //rideflow

            "accept" -> {
                fragment = AcceptRideFragDialog(this)
            }

            "waiting" -> {
                fragment = InitRideFragDialog(this)
            }

            "ongoing" -> {

                fragment = OnGoingRideFragDialog(this)
            }

            "finish" -> {

                fragment = FinishedRideDetailsFragDialog(this)
            }

            "cancel" -> {
                fragment = CancelRideFragDialog(this)
            }

        }

        transaction.replace(R.id.containerViewChild, fragment).commitAllowingStateLoss()

        close_imageButton.setOnClickListener {

            val homeActContext = requireActivity() as? HomeAct

            if (requireActivity() == homeActContext) {

                Log.d("TAG", "onDestroy: " + homeActContext.screenStageLiveData.value)
                if (homeActContext.screenStageLiveData.value != HomeAct.MainScreenStage.RELOAD_OVERVIEW_STATEMENT) {
                    if (homeActContext.screenStageLiveData.value == HomeAct.MainScreenStage.WAITING_PICKUP
                        || homeActContext.screenStageLiveData.value == HomeAct.MainScreenStage.ONGOING_RIDE) {

                        useful.showRideFlowFrag(requireActivity().supportFragmentManager, "cancel")

                    } else {

                        homeActContext.notifyScreenStageChanged(HomeAct.MainScreenStage.RELOAD_OVERVIEW_STATEMENT)
                    }
                }
            }

//            useful.dismissRideFlowFrag(requireActivity().supportFragmentManager)
        }

    }
}