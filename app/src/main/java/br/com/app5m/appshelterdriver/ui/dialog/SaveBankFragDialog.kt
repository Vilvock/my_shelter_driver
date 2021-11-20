package br.com.app5m.appshelterdriver.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.BankControl
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Bank
import br.com.app5m.appshelterdriver.ui.fragment.drawer.other.UserBanksFrag
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottom_view_savebankaccount.*

/**
 * A simple [Fragment] subclass.
 */
class SaveBankFragDialog (private val bottomSheetDialogFragment: BottomSheetDialogFragment, private val isSave: Boolean) :
    BottomSheetDialogFragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var bankControl: BankControl

    private lateinit var preferences: Preferences

    private lateinit var userBanksFrag: UserBanksFrag

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_view_savebankaccount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())
        preferences = Preferences(requireContext())
        bankControl = BankControl(requireContext(), this, useful)

        save_bt.setOnClickListener {

            //falta validation

            val saveBank = Bank()

            saveBank.type = (typePerson_sp.selectedItemPosition + 1).toString()
            saveBank.typeAccount = (typeAccount_sp.selectedItemPosition + 1).toString()

            saveBank.bank = bank_et.text.toString()
            saveBank.agency = agency_et.text.toString()
            saveBank.agencyDigit = agencyD_et.text.toString()
            saveBank.cc = cc_et.text.toString()
            saveBank.ccDigit = ccD_et.text.toString()

            saveBank.typePixKey = (typeKeyPix_sp.selectedItemPosition + 1).toString()
            saveBank.pixKey = keyPix_et.text.toString()

            //acho que nome do banco é melhor ter uma lista e selecionar
            if (isSave) {
//                {
//                    "token": "shelter_movel#2021", v
//                    "id_user": 11, v

//                    "type": 1, v

//                    "tipo_chave_pix": "CPF", v
//                    "chave_pix": "884.192.000-93", v
//                    "banco": "Bradesco", v
//                    "agencia": "0736",v
//                    "agencia_d": "1",v
//                    "cc": "0151152",v
//                    "cc_d": "1",v
//                    "tipo_conta": "corrente" v
//                }
                bankControl.saveBank(saveBank)
            } else {

                saveBank.idAccount = userBanksFrag.bankList[userBanksFrag.position!!].idAccount

//                {
//                    "token": "shelter_movel#2021",v
//                    "id_conta": 1,v
//                    "type": 1,v
//                    "tipo_chave_pix": "CPF",v
//                    "chave_pix": "884.192.000-93",v
//                    "banco": "NU pagamentos",v
//                    "agencia": "0736",v
//                    "agencia_d": "1",v
//                    "cc": "0151152",v
//                    "cc_d": "1",v
//                    "tipo_conta": "corrente"v
//                }
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

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        userBanksFrag = childFragment as UserBanksFrag
    }
}