package br.com.app5m.appshelterdriver.ui.fragment.drawer.user

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
import br.com.app5m.appshelterdriver.ui.fragment.auth.RecoverPasswordFrag
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.Validation
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import kotlinx.android.synthetic.main.fragment_update_vehicle.*


class UpdateVehicleFrag : Fragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var userControl: UserControl
    private lateinit var validation: Validation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_vehicle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())
        validation = Validation(requireContext())
        userControl = UserControl(requireContext(), this, useful)

        useful.openLoading()
        userControl.listVehicle()


        loadClicks()

    }

    override fun uResponse(list: List<User>, type: String) {

        useful.closeLoading()

        val user = list[0]

        if (user.status.equals("01")) {

        } else {
//
//            SingleToast.INSTANCE.show(context, user.msg!!, Toast.LENGTH_LONG)
        }

    }

    private fun loadClicks() {

        save_bt.setOnClickListener {

            if (!validate()) return@setOnClickListener

            val vehicle = User()

            useful.openLoading()

            userControl.updateVehicleData(vehicle)

        }

    }

    private fun validate(): Boolean {

        if (!validation.validateTextField(mark_et)) return false
        if (!validation.validateTextField(model_et)) return false
        if (!validation.validateTextField(board_et)) return false
        if (!validation.validateTextField(year_et)) return false

        if (colorCar_sp.selectedItemPosition == 0) {

            SingleToast.INSTANCE.show(context, "Selecione uma cor para seu veículo!",
                Toast.LENGTH_SHORT)

            return false
        }

        if (typeCar_sp.selectedItemPosition == 0) {

            SingleToast.INSTANCE.show(context, "Selecione qual seu tipo de veículo!",
                Toast.LENGTH_SHORT)

            return false
        }

        return true
    }
}