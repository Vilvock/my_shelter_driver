package br.com.app5m.appshelterdriver.ui.fragment.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.UserControl
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.User
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.Validation
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFrag : Fragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var preferences: Preferences
    private lateinit var userControl: UserControl
    private lateinit var validation: Validation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())
        preferences = Preferences(requireContext())
        validation = Validation(requireContext())
        userControl = UserControl(requireContext(), this, useful)

        loadClicks()

    }

    override fun uResponse(list: List<User>, type: String) {

        useful.closeLoading()

        val user = list[0]

        if (user.status.equals("01")) {
            preferences.setLogin(true)
            preferences.setUserData(user)
            startActivity(Intent(context, HomeAct::class.java))
            requireActivity().finishAffinity()
        } else {

            SingleToast.INSTANCE.show(context, user.msg!!, Toast.LENGTH_LONG)
        }

    }

    private fun loadClicks(){

        login_bt.setOnClickListener {

            if (!validate()) return@setOnClickListener

            val user = User()
            user.email = email_et.text.toString()
            user.password = password_et.text.toString()

            useful.openLoading()

            userControl.login(user)

        }

        forgetPassword_tv.setOnClickListener {
            useful.startFragmentOnBack(RecoverPasswordFrag(), requireActivity().supportFragmentManager)
        }

    }

    private fun validate(): Boolean {
        return if (!validation.email(email_et)) false
        else (validation.password(password_et, 1))
    }

}