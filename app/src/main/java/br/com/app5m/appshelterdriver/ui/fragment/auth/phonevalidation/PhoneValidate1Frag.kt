package br.com.app5m.appshelterdriver.ui.fragment.auth.phonevalidation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_phone_validate1.*


/**
 * A simple [Fragment] subclass.
 * Use the [PhoneValidate1Frag.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhoneValidate1Frag : Fragment() {

    private lateinit var useful: Useful

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_validate1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        useful = Useful(requireContext())

        next_phonevalidation_bt.setOnClickListener {

            activity?.let { it1 -> useful.startFragmentOnBack(PhoneValidate2Frag(), it1.supportFragmentManager) }

        }

    }
}