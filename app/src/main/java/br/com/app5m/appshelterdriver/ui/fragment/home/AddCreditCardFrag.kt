package br.com.app5m.appshelterdriver.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.adapter.CreditCardFlagsAdapter
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.models.CreditCardFlag
import kotlinx.android.synthetic.main.fragment_add_credit_card.*


class AddCreditCardFrag : Fragment(),RecyclerItemClickListener {
    private var listCreditCardFrags  = ArrayList<CreditCardFlag>()


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
        listCreditCardFrags.add(CreditCardFlag())
        listCreditCardFrags.add(CreditCardFlag())
        listCreditCardFrags.add(CreditCardFlag())
        listCreditCardFrags.add(CreditCardFlag())
        listCreditCardFrags.add(CreditCardFlag())
        listCreditCardFrags.add(CreditCardFlag())
        configureInitialViews()
    }

    fun configureInitialViews() {

        creditCardFlagsRv.apply {
            setHasFixedSize(true)
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
            itemDecoration.setDrawable(
                resources.getDrawable(R.drawable.item_decoration_layout_no_bg, null)
            )
            creditCardFlagsRv.addItemDecoration(itemDecoration)

        }
        val creditCardFlagsAdapter = CreditCardFlagsAdapter(requireContext(), listCreditCardFrags, this)

        val layoutManagerAdresses: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        creditCardFlagsRv.layoutManager = layoutManagerAdresses

        creditCardFlagsRv.adapter = creditCardFlagsAdapter
    }

}