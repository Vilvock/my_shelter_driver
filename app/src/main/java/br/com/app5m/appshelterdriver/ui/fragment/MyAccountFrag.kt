package br.com.app5m.appshelterdriver.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.activity.DrawerContainerAct
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_my_account.*


class MyAccountFrag : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back_bt_account.setOnClickListener {
            activity?.finish()
            val intent = Intent(context, DrawerContainerAct::class.java)
            intent.putExtra("key", "bank_account")
            startActivity(intent)
        }

    }

}