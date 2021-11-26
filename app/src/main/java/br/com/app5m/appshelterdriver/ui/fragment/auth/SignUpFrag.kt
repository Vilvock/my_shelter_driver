package br.com.app5m.appshelterdriver.ui.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.RideControl
import br.com.app5m.appshelterdriver.controller.UserControl
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.models.User
import br.com.app5m.appshelterdriver.ui.activity.DocumentPdfWebViewAct
import br.com.app5m.appshelterdriver.ui.activity.SucessInfoAct
import br.com.app5m.appshelterdriver.util.Mask
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.Validation
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import kotlinx.android.synthetic.main.fragment_sign_up.*


/**
 * A simple [Fragment] subclass.
 */
class SignUpFrag : Fragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var preferences: Preferences
    private lateinit var userControl: UserControl
    private lateinit var validation: Validation

    private lateinit var cpfMask: TextWatcher
    private lateinit var cnpjMask: TextWatcher

    private lateinit var rideControl: RideControl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())
        validation = Validation(requireContext())
        preferences = Preferences(requireContext())

        rideControl = RideControl(requireContext(), this, useful)
        userControl = UserControl(requireContext(), this, useful)

        useful.openLoading()
        rideControl.listVehicleTypes()

        loadClicks()
        loadMasks()

        typePerson_sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when (position) {
                    0 -> {
                        document_et.hint = "CPF"
                        document_et.removeTextChangedListener(cnpjMask)
                        document_et.addTextChangedListener(cpfMask)
                    }
                    1 -> {
                        document_et.hint = ("CNPJ")
                        document_et.removeTextChangedListener(cpfMask)
                        document_et.addTextChangedListener(cnpjMask)
                    }
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

    }

    override fun rResponse(list: List<Ride>, type: String) {

        useful.closeLoading()

        val typeCarNameList = ArrayList<String>()

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

    }

    override fun uResponse(list: List<User>, type: String) {

        useful.closeLoading()

        val user = list[0]

        if (user.status.equals("01")) {
            preferences.setLogin(true)
            preferences.setUserData(user)

            startActivity(Intent(requireContext(), SucessInfoAct::class.java))
            requireActivity().finish()

        } else {

            SingleToast.INSTANCE.show(context, user.msg!!, Toast.LENGTH_LONG)
        }


    }

    private fun loadMasks() {
        cpfMask = Mask.insert("###.###.###-##", document_et)
        cnpjMask = Mask.insert("##.###.###/####-##", document_et)
    }

    private fun loadClicks(){

        next_bt.setOnClickListener {


            //ver depois se tem cnpj no juridico pra mandar, dai eu mudo o mask


            if (!validate()) return@setOnClickListener

            val user = User()

            user.name = name_et.text.toString() + " " + lastName_et.text.toString()
            user.email = email_et.text.toString()
            user.cellphone = cellphone_et.text.toString()
            user.password = password_et.text.toString()
            user.gender = (gender_sp.selectedItemPosition + 1).toString()
            user.typePerson = (typePerson_sp.selectedItemPosition + 1).toString()
            user.cpf = document_et.text.toString()
            user.birth = birth_et.text.toString()
            user.latitude = preferences.getUserLocation()!!.latitude
            user.longitude = preferences.getUserLocation()!!.longitude

            //carro

            user.vehicle.mark = mark_et.text.toString()
            user.vehicle.model = model_et.text.toString()
            user.vehicle.board = board_et.text.toString()
            user.vehicle.color = "Vermelho"
            user.vehicle.year = year_et.text.toString()
            user.vehicle.modelYear = year_et.text.toString()
            user.vehicle.typeCar = (typeCar_sp.selectedItemPosition + 1).toString()

            useful.openLoading()

            userControl.register(user)


//            useful.startFragmentOnBack(PhoneValidation1Frag(user), requireActivity().supportFragmentManager)

        }

        terms_tv.setOnClickListener {
            startActivity(Intent(requireContext(), DocumentPdfWebViewAct::class.java ))
        }

    }

    //validacao para carro dps
    private fun validate() : Boolean {
        if (!validation.name(name_et)) return false
        if (!validation.lastName(lastName_et)) return false
        if (!validation.date(birth_et)) return false
        if (!validation.email(email_et)) return false
        if (!validation.cellphone(cellphone_et)) return false

        if (typePerson_sp.selectedItemPosition == 0) {
            if (!validation.cpf(document_et)) return false
        } else {
            if (!validation.cnpj(document_et)) return false
        }

        if (!validation.password(password_et, 0)) return false
        if (!validation.coPassword(password_et, coPassword_et)) return false

        return true
    }
}