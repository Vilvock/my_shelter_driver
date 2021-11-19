package br.com.app5m.appshelterdriver.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.BankControl
import br.com.app5m.appshelterdriver.controller.RideControl
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Bank
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottom_view_acceptride.*
import kotlinx.android.synthetic.main.dialog_bottom_view_acceptride.rideInfo_tv
import kotlinx.android.synthetic.main.dialog_bottom_view_addbankaccount.*
import kotlinx.android.synthetic.main.dialog_bottom_view_finishedridedetails.*


/**
 * A simple [Fragment] subclass.
 */
class AddBankFragDialog (private val bottomSheetDialogFragment: BottomSheetDialogFragment, private val isSave: Boolean) :
    BottomSheetDialogFragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var bankControl: BankControl

    private lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_bottom_view_addbankaccount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())
        preferences = Preferences(requireContext())
        bankControl = BankControl(requireContext(), this, useful)

        save_bt.setOnClickListener {

            val saveBank = Bank()

            //

            //acho que nome do banco é melhor ter uma lista e selecionar
            if (isSave) {
//                {
//                    "token": "shelter_movel#2021",
//                    "id_user": 11,

//                    "type": 1, v
//                    "tipo_chave_pix": "CPF",

//                    "chave_pix": "884.192.000-93",
//                    "banco": "Bradesco", v
//                    "agencia": "0736",v
//                    "agencia_d": "1",v
//                    "cc": "0151152",v
//                    "cc_d": "1",v
//                    "tipo_conta": "corrente" v
//                }
                bankControl.saveBank(saveBank)
            } else {

//                {
//                    "token": "shelter_movel#2021",
//                    "id_conta": 1,
//                    "type": 1,
//                    "tipo_chave_pix": "CPF",
//                    "chave_pix": "884.192.000-93",
//                    "banco": "NU pagamentos",
//                    "agencia": "0736",
//                    "agencia_d": "1",
//                    "cc": "0151152",
//                    "cc_d": "1",
//                    "tipo_conta": "corrente"
//                }
                bankControl.updateBank(saveBank)
            }


        }


    }

    override fun bResponse(list: List<Bank>, type: String) {

        val bankResponseInfo = list[0]



    }
}