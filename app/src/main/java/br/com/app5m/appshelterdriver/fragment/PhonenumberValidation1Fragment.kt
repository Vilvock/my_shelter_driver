package br.com.app5m.appshelterdriver.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import br.com.app5m.appshelterdriver.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_phonenumber_validation1.*
import kotlinx.android.synthetic.main.fragment_phonenumber_validation1.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [PhonenumberValidation1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhonenumberValidation1Fragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phonenumber_validation1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = listOf("93", "55", "61", "257")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
//        phoneCountryTi.phoneCountryAcTv?.setAdapter(adapter)
        phoneCountryAcTv.setText(arrayAdater.getItem)

    }
}