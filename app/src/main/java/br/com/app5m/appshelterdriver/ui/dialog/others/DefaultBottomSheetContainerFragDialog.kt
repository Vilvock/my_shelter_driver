package br.com.app5m.appshelterdriver.ui.dialog.others

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
import br.com.app5m.appshelterdriver.util.Useful
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.default_bottom_sheet_dialog_container.*


/**
 * A simple [Fragment] subclass.
 */
class DefaultBottomSheetContainerFragDialog: BottomSheetDialogFragment() {


    private lateinit var useful: Useful

    private lateinit var fragment: Fragment

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

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

        // mostrar o bottomsheet inteiro
        dialog!!.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            d.setCancelable(false)
            val bottomSheet = d.findViewById<View>(R.id.sheet_container) as FrameLayout?
            val coordinatorLayout = bottomSheet!!.parent as CoordinatorLayout
            val bottomSheetBehavior: BottomSheetBehavior<*> =
                BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.peekHeight = bottomSheet.height
            bottomSheetBehavior.isFitToContents = true
            bottomSheetBehavior.expandedOffset = 0
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            coordinatorLayout.parent.requestLayout()
        }

        val transaction = childFragmentManager.beginTransaction()

        when(tag) {


//            //others
//

            "account" -> {
                fragment = TypeAccountFragDialog(this)
            }

            "add_bank" -> {
                fragment = SaveAccountDialog(this, isSave = true, isBank = true)
            }

            "pix" -> {
                fragment = SaveAccountDialog(this, isSave = true, isBank = false)
            }

            "update_account" -> {
                fragment = SaveAccountDialog(this, false, null)
            }


        }

        transaction.replace(R.id.containerViewChild, fragment).commitAllowingStateLoss()

        close_imageButton.setOnClickListener {

            this.dismiss()
        }

    }

    override fun onStart() {
        super.onStart()


        // mostrar o bottomsheet inteiro
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val bottomSheet: View = dialog.findViewById(R.id.sheet_container)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

//                dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        }
        val view = view
        view!!.post {
            val bottomSheet: View = dialog!!.findViewById(R.id.sheet_container)
            val parent = view.parent as View
            val params =
                parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            bottomSheetBehavior!!.peekHeight = view.measuredHeight
            (bottomSheet.parent as View).setBackgroundColor(Color.TRANSPARENT)
        }
    }
}