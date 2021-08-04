package br.com.app5m.appshelterdriver.ui.fragment.bankaccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_add_bank_account.*
import kotlinx.android.synthetic.main.fragment_bank_account.*


class AddBankAccountFrag : Fragment() {
    private lateinit var useful: Useful

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_bank_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())
        addPix_bt.setOnClickListener {
            activity?.let { it1 -> useful.startFragmentOnBack(AddPixFrag(), it1.supportFragmentManager) }

        }
        addCheckingAccount_bt.setOnClickListener {
            activity?.let { it1 -> useful.startFragmentOnBack(AddCheckingAccountFrag(), it1.supportFragmentManager) }

        }
    }

}