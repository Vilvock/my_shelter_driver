package br.com.app5m.appshelterdriver.ui.dialog

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.util.Useful
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottom_view_typeaccount.*


/**
 * A simple [Fragment] subclass.
 */
class TypeAccountFragDialog (private val bottomSheetDialogFragment: BottomSheetDialogFragment) : BottomSheetDialogFragment() {

    private lateinit var useful: Useful

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_view_typeaccount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())

        account_bt.setOnClickListener {
            useful.showDefaultDialogView(requireActivity().supportFragmentManager, "add_bank")

            bottomSheetDialogFragment.dismiss()
        }

        pix_bt.setOnClickListener {
            useful.showDefaultDialogView(requireActivity().supportFragmentManager, "pix")

            bottomSheetDialogFragment.dismiss()
        }


    }

}