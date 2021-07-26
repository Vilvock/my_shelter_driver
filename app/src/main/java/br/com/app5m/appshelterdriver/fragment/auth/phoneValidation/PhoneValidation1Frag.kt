package br.com.app5m.appshelterdriver.fragment.auth.phoneValidation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import br.com.app5m.appshelterdriver.R
import kotlinx.android.synthetic.main.fragment_phone_validation1.*



/**
 * A simple [Fragment] subclass.
 * Use the [PhoneValidation1Frag.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhoneValidation1Frag : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_validation1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}