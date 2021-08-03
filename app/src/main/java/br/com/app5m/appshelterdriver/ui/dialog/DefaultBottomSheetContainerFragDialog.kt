package br.com.app5m.appshelterdriver.ui.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.activity.DrawerContainerAct
import br.com.app5m.appshelterdriver.util.Useful
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.default_bottom_sheet_dialog_container.*


/**
 * A simple [Fragment] subclass.
 */
class DefaultBottomSheetContainerFragDialog (private val fragment: Fragment) : BottomSheetDialogFragment() {

    private lateinit var useful: Useful

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.default_bottom_sheet_dialog_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())

        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.containerViewChild, fragment).commit()

//        // Right here!
//        BottomSheetBehavior.from(sheet_container).state =
//            BottomSheetBehavior.STATE_EXPANDED

        close_imageButton.setOnClickListener {
            this.dismiss()
            val intent = Intent(context, DrawerContainerAct::class.java)

        }

    }
    fun closeContainerDialog(){
        this.dismiss()
    }
}