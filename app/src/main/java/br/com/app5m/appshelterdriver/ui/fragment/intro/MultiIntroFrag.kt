package br.com.app5m.appshelterdriver.ui.fragment.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.app5m.appshelterdriver.R

/**
 * A simple [Fragment] subclass.
 */
class MultiIntroFrag (/*private val title:String, private val msg:String*/) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multi_intro, container, false)
    }


}