package br.com.app5m.appshelterdriver.ui.fragment.bankaccount.tabs

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
import br.com.app5m.appshelterdriver.ui.adapter.MyBankAdapter
import br.com.app5m.appshelterdriver.ui.fragment.bankaccount.AddCheckingAccountFrag
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_my_bank.*
import kotlinx.android.synthetic.main.fragment_my_payments.*


class MyBankFrag : Fragment(),RecyclerItemClickListener {
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
        return inflater.inflate(R.layout.fragment_my_bank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_bank_account_bt.setOnClickListener {
            useful = Useful(requireContext())
            activity?.let { it1 -> useful.startFragmentOnBack(AddCheckingAccountFrag(), it1.supportFragmentManager) }
        }
        configureInitialViews()

        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())

    }
    fun configureInitialViews() {
        myBank_rv.apply {
            setHasFixedSize(true)
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(
                resources.getDrawable(
                    R.drawable.item_decoration_layout_no_bg,
                    null
                )
            )
            myBank_rv.addItemDecoration(itemDecoration)

        }
        val myAdressesAdapter = MyBankAdapter(requireContext(), listTravelHistorys, this)

        val layoutManagerAdresses: RecyclerView.LayoutManager = GridLayoutManager(context, 1)

        myBank_rv.layoutManager = layoutManagerAdresses

        myBank_rv.adapter = myAdressesAdapter
    }
}