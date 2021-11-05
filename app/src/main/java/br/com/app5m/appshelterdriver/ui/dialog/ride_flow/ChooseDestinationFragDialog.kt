package br.com.app5m.appshelterdriver.ui.dialog.ride_flow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottom_view_choosedestination.*


/**
 * A simple [Fragment] subclass.
 */
class ChooseDestinationFragDialog (private val bottomSheetDialogFragment: BottomSheetDialogFragment) : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_view_choosedestination, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        destination_et.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                if (destination_et.text.toString().isEmpty()) {
                    SingleToast.INSTANCE.show(requireContext(), "Insira um destino válido para sua viagem!", Toast.LENGTH_SHORT)
                    return@OnEditorActionListener true
                }

//                val intent = Intent(requireActivity(), SearchDestinationAct::class.java)
//                    .putExtra("destination", destination_et.text.toString())
//                // send intent for result
//                requireActivity().startActivityForResult(intent, 1)

                bottomSheetDialogFragment.dismiss()

            }
            false
        })
    }

}