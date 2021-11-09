package br.com.app5m.appshelterdriver.ui.dialog.ride_flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
import br.com.app5m.appshelterdriver.util.Useful
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_bottom_view_choosedestination.*
import kotlinx.android.synthetic.main.loading.*


/**
 * A simple [Fragment] subclass.
 */
class AcceptRideFragDialog (private val bottomSheetDialogFragment: BottomSheetDialogFragment) : BottomSheetDialogFragment() {

    private lateinit var useful: Useful

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_view_acceptride, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())

        val homeActContext = requireActivity() as HomeAct



    }

}