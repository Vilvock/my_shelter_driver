package br.com.app5m.appshelterdriver.fragment.auth.phoneValidation

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import br.com.app5m.appshelterdriver.R
import kotlinx.android.synthetic.main.fragment_phone_validation1.*
import kotlinx.android.synthetic.main.fragment_phone_validation2.*


/**
 * A simple [Fragment] subclass.
 * Use the [PhoneValidation2Frag.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhoneValidation2Frag : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_validation2, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val first = "Verifique suas mensagens SMS. Nós te enviamos o PIN no número "
        val next = "<font color='#828282'>51981856895</font>"
        pinCodeTv.setText(HtmlCompat.fromHtml(first + next, HtmlCompat.FROM_HTML_MODE_LEGACY))



    }
}