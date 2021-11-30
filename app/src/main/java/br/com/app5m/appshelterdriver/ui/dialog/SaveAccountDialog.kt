package br.com.app5m.appshelterdriver.ui.dialog

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.BankControl
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Bank
import br.com.app5m.appshelterdriver.ui.fragment.drawer.other.UserAccountFrag
import br.com.app5m.appshelterdriver.util.Mask
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.Validation
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_sign_up.*
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.dialog_bottom_view_saveaccount.*
import kotlinx.android.synthetic.main.dialog_bottom_view_typeaccount.*
import kotlinx.android.synthetic.main.fragment_sign_up.typePerson_sp


/**
 * A simple [Fragment] subclass.
 */
class SaveAccountDialog(private val bottomSheetDialogFragment: BottomSheetDialogFragment, private val isSave: Boolean, private val isBank: Boolean?) :
    BottomSheetDialogFragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var bankControl: BankControl
    private lateinit var validation: Validation

    private lateinit var preferences: Preferences

    private lateinit var userAccountFrag: UserAccountFrag

    private lateinit var cpfMask: TextWatcher
    private lateinit var cnpjMask: TextWatcher

    private lateinit var savedBank: Bank

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_view_saveaccount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())
        validation = Validation(requireContext())
        preferences = Preferences(requireContext())
        bankControl = BankControl(requireContext(), this, useful)

        userAccountFrag = requireActivity().supportFragmentManager.fragments[0] as UserAccountFrag

        if (!isSave) {

            savedBank = userAccountFrag.bankList[userAccountFrag.position!!]


            //fisica
            if (savedBank.type == "1") {
                typePerson_sp.setSelection(0)
            } else { //juridica
                typePerson_sp.setSelection(1)
            }

            if (savedBank.bank != "") {
                title_tv.text = "Atualizar conta bancária"

                pix_ll.visibility = View.GONE

                typeAccount_sp.setSelection((typeAccount_sp.adapter as ArrayAdapter<String?>).getPosition(savedBank.typeAccount))

                bank_et.setText(savedBank.bank)
                cc_et.setText(savedBank.cc)
                ccD_et.setText(savedBank.ccDigit)
                agency_et.setText(savedBank.agency)
                agencyD_et.setText(savedBank.agencyDigit)
            } else {
                title_tv.text = "Atualizar PIX"

                bank_ll.visibility = View.GONE

                typeKeyPix_sp.setSelection((typeKeyPix_sp.adapter as ArrayAdapter<String?>).getPosition(savedBank.typePixKey))
                keyPix_et.setText(savedBank.pixKey)
            }

        }

        if (isBank == true) {

            pix_ll.visibility = View.GONE


        } else if (isBank == false) {

            bank_ll.visibility = View.GONE

            typeKeyPix_sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    when (position) {
                        0 -> {
                            keyPix_et.hint = "CPF"
                            keyPix_et.removeTextChangedListener(cnpjMask)
                            keyPix_et.addTextChangedListener(cpfMask)
                        }
                        1 -> {
                            keyPix_et.hint = ("CNPJ")
                            keyPix_et.removeTextChangedListener(cpfMask)
                            keyPix_et.addTextChangedListener(cnpjMask)
                        }
                        else -> {
                            keyPix_et.hint = ("Outro")
                            keyPix_et.removeTextChangedListener(cnpjMask)
                            keyPix_et.removeTextChangedListener(cpfMask)
                        }
                    }

                }
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        }

        loadMasks()

        save_bt.setOnClickListener {

            //falta validation e printar

            if (!validate()) return@setOnClickListener

            val saveBank = Bank()

            saveBank.type = (typePerson_sp.selectedItemPosition + 1).toString()

            if (isBank == true) {

                saveBank.typeAccount = typeAccount_sp.selectedItem.toString()
                saveBank.bank = bank_et.text.toString()
                saveBank.agency = agency_et.text.toString()
                saveBank.agencyDigit = agencyD_et.text.toString()
                saveBank.cc = cc_et.text.toString()
                saveBank.ccDigit = ccD_et.text.toString()
            } else {

                saveBank.typePixKey = typeKeyPix_sp.selectedItem.toString()
                saveBank.pixKey = keyPix_et.text.toString()
            }


            //acho que nome do banco é melhor ter uma lista e selecionar
            if (isSave) {

                bankControl.saveBank(saveBank)
            } else {

                saveBank.idAccount = savedBank.id

                bankControl.updateBank(saveBank)
            }


        }


    }

    override fun bResponse(list: List<Bank>, type: String) {

        val bankResponseInfo = list[0]

        if (bankResponseInfo.status == "01") {
            bottomSheetDialogFragment.dismiss()
        }

        SingleToast.INSTANCE.show(requireActivity(), bankResponseInfo.msg!!, Toast.LENGTH_SHORT)

    }

    private fun loadMasks() {
        cpfMask = Mask.insert("###.###.###-##", keyPix_et)
        cnpjMask = Mask.insert("##.###.###/####-##", keyPix_et)
    }


    private fun validate() : Boolean {

        if (isBank == true) {

            if (!validation.validateTextField(bank_et)) return false
            if (!validation.validateTextField(cc_et)) return false
            if (!validation.validateTextField(ccD_et)) return false
            if (!validation.validateTextField(agency_et)) return false
            if (!validation.validateTextField(agencyD_et)) return false

        } else if (isBank == false){
            if (typeKeyPix_sp.selectedItem.toString() == "CPF") {
                if (!validation.cpf(keyPix_et)) return false
            } else if (typeKeyPix_sp.selectedItem.toString() == "CNPJ") {
                if (!validation.cnpj(keyPix_et)) return false
            } else {
                if (!validation.validateTextField(keyPix_et)) return false
            }

        }

        return true
    }

}