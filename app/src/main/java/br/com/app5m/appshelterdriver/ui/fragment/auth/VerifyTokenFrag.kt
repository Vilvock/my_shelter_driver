package br.com.app5m.appshelterdriver.ui.fragment.auth

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
import br.com.app5m.appshelterdriver.ui.fragment.auth.UpdatePasswordByTokenFrag
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.Validation
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_recover_password.*
import kotlinx.android.synthetic.main.fragment_verify_token.*


/**
 * A simple [Fragment] subclass.
 */
class VerifyTokenFrag : Fragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var preferences: Preferences
    private lateinit var userControl: UserControl
    private lateinit var validation: Validation

    private val user = User()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify_token, container, false)
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

            user.tokenPassword?.let { UpdatePasswordByTokenFrag(it) }?.let {
                useful.startFragmentOnBack(
                    it, requireActivity().supportFragmentManager)
            }

        } else {

            SingleToast.INSTANCE.show(context, user.msg!!, Toast.LENGTH_LONG)
        }

    }

    private fun loadClicks() {

        verify_bt.setOnClickListener {

            user.tokenPassword = token_et.text.toString()

            useful.openLoading()

            userControl.verifyToken(user)

        }

    }

}