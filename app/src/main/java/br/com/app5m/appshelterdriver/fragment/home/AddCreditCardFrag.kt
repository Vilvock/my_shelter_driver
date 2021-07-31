package br.com.app5m.appshelterdriver.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_add_credit_card.*


class AddCreditCardFrag : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(br.com.app5m.appshelterdriver.R.layout.fragment_add_credit_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val myList = creditCardFlagsRv as RecyclerView
        myList.layoutManager = layoutManager

    }



}