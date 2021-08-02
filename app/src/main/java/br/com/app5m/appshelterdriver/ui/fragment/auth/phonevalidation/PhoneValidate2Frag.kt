package br.com.app5m.appshelterdriver.ui.fragment.auth.phonevalidation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_phone_validate2.*



/**
 * A simple [Fragment] subclass.
 * Use the [PhoneValidate2Frag.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhoneValidate2Frag : Fragment() {

    private lateinit var useful: Useful

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_validate2, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        useful = Useful(requireContext())

        val first = "Verifique suas mensagens SMS. Nós te enviamos o PIN no número "
        val next = "<font color='#828282'>51981856895</font>"
        pinCodeTv.setText(HtmlCompat.fromHtml(first + next, HtmlCompat.FROM_HTML_MODE_LEGACY))

        verify_bt.setOnClickListener {

            startActivity(Intent(requireContext(), HomeAct::class.java))
            requireActivity().finishAffinity()

        }
    }
}