package br.com.app5m.appshelterdriver.ui.fragment.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.fragment.auth.phonevalidation.PhoneValidate1Frag
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_sign_up.*


/**
 * A simple [Fragment] subclass.
 */
class SignUpFrag : Fragment() {

    private lateinit var useful: Useful

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        useful = Useful(requireContext())

        next_bt.setOnClickListener {

            activity?.let { it1 -> useful.startFragmentOnBack(PhoneValidate1Frag(), it1.supportFragmentManager) }

        }

    }

}