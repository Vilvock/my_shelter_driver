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
import kotlinx.android.synthetic.main.dialog_bottom_view_finishedridedetails.*

/**
 * A simple [Fragment] subclass.
 */
class FinishedRideDetailsFragDialog (private val bottomSheetDialogFragment: BottomSheetDialogFragment) :
    BottomSheetDialogFragment() {

    private lateinit var useful: Useful

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

        homeActContext = requireActivity() as HomeAct

        ok_bt.setOnClickListener {

            bottomSheetDialogFragment.dismiss()
        }

        val destination = "Destino: " + homeActContext.rideLiveData.value!!.destinationAddress
        val distance = "Distância: " + homeActContext.rideLiveData.value!!.distance + " " + homeActContext.rideLiveData.value!!.distanceInitials
        val duration = "Tempo estimado: " + homeActContext.rideLiveData.value!!.routeTime
        val payment = "Tipo de pagamento: " + homeActContext.rideLiveData.value!!.nameTypePayment


        rideInfo_tv.text = "$destination\n$distance\n$duration\n$payment"


    }


}