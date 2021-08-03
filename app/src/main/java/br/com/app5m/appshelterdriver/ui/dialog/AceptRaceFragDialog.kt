package br.com.app5m.appshelterdriver.ui.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.app5m.appshelterdriver.R
import kotlinx.android.synthetic.main.dialog_bottom_view_acepit_race.*

/**
 * A simple [Fragment] subclass.
 */
class AceptRaceFragDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_view_acepit_race, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        indicatorHorizontal.setProgressCompat(25, true)

        aceptRace_bt.setOnClickListener {
            fragmentManager?.let { it1 -> RaceDetailsFragDialog().show(it1, "DialogCuponsFrag") }
            this.dismiss();
        }
    }


}