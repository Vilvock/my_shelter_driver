package br.com.app5m.appshelterdriver.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.fragment.auth.phonevalidation.PhoneValidate2Frag
import br.com.app5m.appshelterdriver.ui.fragment.signin.UploadDocumentsFrag
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_update_vehicle.*


class UpdateVehicleFrag : Fragment() {
    private lateinit var useful: Useful

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_vehicle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())

        next_bt2.setOnClickListener {
            activity?.let { it1 -> useful.startFragmentOnBack(UploadDocumentsFrag(), it1.supportFragmentManager) }

        }
    }

}