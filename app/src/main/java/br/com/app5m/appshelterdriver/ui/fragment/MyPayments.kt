package br.com.app5m.appshelterdriver.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.models.Travel
import br.com.app5m.appshelterdriver.ui.adapter.MyPaymentsAdapter
import br.com.app5m.appshelterdriver.ui.dialog.MyPaymentsFilterFragDialog
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_my_payments.*


class MyPayments : Fragment(), RecyclerItemClickListener {
    private lateinit var useful: Useful
    private var listTravelHistorys  = ArrayList<Travel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_payments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureInitialViews()

        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
        filter_bt_payments.setOnClickListener {
            fragmentManager?.let { it1 -> MyPaymentsFilterFragDialog().show(it1, "DialogCuponsFrag") }

        }
    }
    fun configureInitialViews() {
        myPaymentsRv.apply {
            setHasFixedSize(true)
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(
                resources.getDrawable(
                    R.drawable.item_decoration_margin_left,
                    null
                )
            )
            myPaymentsRv.addItemDecoration(itemDecoration)

        }
        val myAdressesAdapter = MyPaymentsAdapter(requireContext(), listTravelHistorys, this)

        val layoutManagerAdresses: RecyclerView.LayoutManager = GridLayoutManager(context, 1)

        myPaymentsRv.layoutManager = layoutManagerAdresses

        myPaymentsRv.adapter = myAdressesAdapter
    }
}