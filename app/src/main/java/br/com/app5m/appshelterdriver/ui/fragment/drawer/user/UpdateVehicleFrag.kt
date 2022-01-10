package br.com.app5m.appshelterdriver.ui.fragment.drawer.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.RideControl
import br.com.app5m.appshelterdriver.controller.UserControl
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.models.User
import br.com.app5m.appshelterdriver.models.Vehicle
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
import br.com.app5m.appshelterdriver.ui.fragment.auth.RecoverPasswordFrag
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.Validation
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_update_vehicle.*
import kotlinx.android.synthetic.main.fragment_update_vehicle.board_et
import kotlinx.android.synthetic.main.fragment_update_vehicle.colorCar_sp
import kotlinx.android.synthetic.main.fragment_update_vehicle.mark_et
import kotlinx.android.synthetic.main.fragment_update_vehicle.model_et
import kotlinx.android.synthetic.main.fragment_update_vehicle.typeCar_sp
import kotlinx.android.synthetic.main.fragment_update_vehicle.year_et


class UpdateVehicleFrag : Fragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var userControl: UserControl
    private lateinit var validation: Validation
    private lateinit var rideControl: RideControl

    private lateinit var vehicleResponseInfo: User

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
        rideControl = RideControl(requireContext(), this, useful)

        useful.openLoading()

        rideControl.listVehicleTypes()

        loadClicks()

    }

    override fun uResponse(list: List<User>, type: String) {

        useful.closeLoading()

        vehicleResponseInfo = list[0]


        if (type == "updateVehicleData") {
            if (vehicleResponseInfo.status == "01") {

                rideControl.listVehicleTypes()
            } else {
                SingleToast.INSTANCE.show(requireContext(), vehicleResponseInfo.msg!!, Toast.LENGTH_LONG)
            }

        } else {

            mark_et.setText(vehicleResponseInfo.mark)
            model_et.setText(vehicleResponseInfo.model)
            board_et.setText(vehicleResponseInfo.board)
            year_et.setText(vehicleResponseInfo.year)


            colorCar_sp.setSelection((colorCar_sp.adapter as ArrayAdapter<String?>)
                .getPosition(vehicleResponseInfo.color))

            typeCar_sp.setSelection(vehicleResponseInfo.typeCar!!.toInt())


        }

    }

    override fun rResponse(list: List<Ride>, type: String) {

        val typeCarNameList = ArrayList<String>()

        typeCarNameList.add("Selecione")

        for (item in list) {
            typeCarNameList.add(item.name!!)
        }

        //sla ta gordo
        val arrayAdapterSpinner = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            typeCarNameList
        )

        typeCar_sp.adapter = arrayAdapterSpinner

        userControl.listVehicle()

    }


    private fun loadClicks() {

        save_bt.setOnClickListener {

            if (!validate()) return@setOnClickListener

            val vehicle = User()

            useful.openLoading()

            vehicle.id = vehicleResponseInfo.id
            vehicle.mark = mark_et.text.toString()
            vehicle.model = model_et.text.toString()
            vehicle.board = board_et.text.toString()
            vehicle.year = year_et.text.toString()
            vehicle.modelYear = year_et.text.toString()
            vehicle.color = (colorCar_sp.selectedItem).toString()
            vehicle.typeCar = (typeCar_sp.selectedItemPosition).toString()

            userControl.updateVehicleData(vehicle)

        }

    }

    private fun validate(): Boolean {

        if (!validation.validateTextField(mark_et)) return false
        if (!validation.validateTextField(model_et)) return false
        if (!validation.validateTextField(board_et)) return false
        if (!validation.year(year_et)) return false

        if (colorCar_sp.selectedItemPosition == 0) {

            SingleToast.INSTANCE.show(context, "Selecione uma cor para seu veículo!",
                Toast.LENGTH_SHORT)

            return false
        }

        if (typeCar_sp.selectedItemPosition == 0) {

            SingleToast.INSTANCE.show(context, "Selecione qual é seu tipo de veículo!",
                Toast.LENGTH_SHORT)

            return false
        }

        return true
    }
}