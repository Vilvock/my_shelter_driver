package br.com.app5m.appshelterdriver.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.app5m.appshelterdriver.R
import com.google.android.material.datepicker.MaterialDatePicker


class MyPaymentsFilterFragDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_my_payments_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val picker = builder.build()

        fragmentManager?.let { picker.show(it,"teste") }

    }

}