package br.com.app5m.appshelterdriver.ui.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.models.User
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_phone_validation1.*


/**
 * A simple [Fragment] subclass.
 */
class PhoneValidation1Frag(private val user: User) : Fragment() {

    private lateinit var useful: Useful

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_validation1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        useful = Useful(requireContext())

        next_bt.setOnClickListener {

            activity?.let { it1 -> useful.startFragmentOnBack(PhoneValidation2Frag(user), it1.supportFragmentManager) }

        }

    }
}