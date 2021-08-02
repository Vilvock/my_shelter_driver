package br.com.app5m.appshelterdriver.ui.fragment.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_upload_documents.*
import kotlinx.android.synthetic.main.fragment_upload_documents.send_bt
import kotlinx.android.synthetic.main.fragment_waiting_approval.*


class WaitingApproval : Fragment() {
    private lateinit var useful: Useful

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_waiting_approval, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())

        docStatus_bt.setOnClickListener {

            activity?.let { it1 -> useful.startFragmentOnBack(DocumentStatusFragment(), it1.supportFragmentManager) }

        }

        exit_bt.setOnClickListener {

            activity?.let { it1 -> useful.startFragmentOnBack(GetStartedFrag(), it1.supportFragmentManager) }

        }


        /*   verify_bt.setOnClickListener {

               startActivity(Intent(requireContext(), HomeAct::class.java))
               requireActivity().finishAffinity()

           }*/

    }

}