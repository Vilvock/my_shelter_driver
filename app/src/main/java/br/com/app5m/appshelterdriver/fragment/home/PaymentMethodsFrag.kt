package br.com.app5m.appshelterdriver.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.adapter.MyCreditCardsAdapter
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.model.CreditCard
import kotlinx.android.synthetic.main.fragment_payment_methods.*


class PaymentMethodsFrag : Fragment(),RecyclerItemClickListener {
    private var listMyCreditCards  = ArrayList<CreditCard>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_methods, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureInitialViews()
        listMyCreditCards.add(CreditCard())
        listMyCreditCards.add(CreditCard())
        listMyCreditCards.add(CreditCard())
        listMyCreditCards.add(CreditCard())
    }
    fun configureInitialViews() {

        myCreditCards.apply {
            setHasFixedSize(true)
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(
                resources.getDrawable(R.drawable.item_decoration_layout_no_bg, null)
            )
            myCreditCards.addItemDecoration(itemDecoration)

        }
        val myCreditCardsAdapter = MyCreditCardsAdapter(requireContext(), listMyCreditCards, this)

        val layoutManagerAdresses: RecyclerView.LayoutManager = GridLayoutManager(context, 1)

        myCreditCards.layoutManager = layoutManagerAdresses

        myCreditCards.adapter = myCreditCardsAdapter
    }

}