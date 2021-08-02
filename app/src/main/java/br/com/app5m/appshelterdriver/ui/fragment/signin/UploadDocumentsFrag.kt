package br.com.app5m.appshelterdriver.ui.fragment.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_phone_validate2.*
import kotlinx.android.synthetic.main.fragment_phone_validate2.verify_bt
import kotlinx.android.synthetic.main.fragment_upload_documents.*


class UploadDocumentsFrag : Fragment() {
    private lateinit var useful: Useful

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_documents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())

        send_bt.setOnClickListener {

            activity?.let { it1 -> useful.startFragmentOnBack(WaitingApproval(), it1.supportFragmentManager) }

        }

        /*   verify_bt.setOnClickListener {

               startActivity(Intent(requireContext(), HomeAct::class.java))
               requireActivity().finishAffinity()

           }*/
    }

}