package br.com.app5m.appshelterdriver.ui.fragment.bankaccount

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
import kotlinx.android.synthetic.main.fragment_add_bank_form.*
import kotlinx.android.synthetic.main.fragment_add_pix_form.*


class AddBankFormFrag : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_bank_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        save_bt_addBank.setOnClickListener {
            activity?.finishAffinity()
            startActivity(Intent(requireContext(), HomeAct::class.java))
        }
    }

}