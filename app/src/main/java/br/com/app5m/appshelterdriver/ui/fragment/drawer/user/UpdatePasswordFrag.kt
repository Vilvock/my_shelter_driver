package br.com.app5m.appshelterdriver.ui.fragment.drawer.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.UserControl
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.User
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.Validation
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import kotlinx.android.synthetic.main.fragment_update_password.*

/**
 * A simple [Fragment] subclass.
 */
class UpdatePasswordFrag : Fragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var userControl: UserControl
    private lateinit var validation: Validation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())
        validation = Validation(requireContext())
        userControl = UserControl(requireContext(), this, useful)

        save_bt.setOnClickListener {

            if (!validate()) return@setOnClickListener

            val newPassword = password_et.text.toString()

            useful.openLoading()
            userControl.updatePassword(newPassword)

        }

    }

    override fun uResponse(list: List<User>, type: String) {

        useful.closeLoading()

        val user = list[0]

        SingleToast.INSTANCE.show(context, user.msg!!, Toast.LENGTH_LONG)


    }

    private fun validate(): Boolean {
        return if (!validation.password(password_et, 0)) false
        else validation.coPassword(password_et, coPassword_et)
    }

}