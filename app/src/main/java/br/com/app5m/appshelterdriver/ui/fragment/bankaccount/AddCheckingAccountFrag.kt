package br.com.app5m.appshelterdriver.ui.fragment.bankaccount

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
import br.com.app5m.appshelterdriver.ui.adapter.AddBankFormAdapter
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_add_checking_account.*
import kotlinx.android.synthetic.main.fragment_add_pix.*


class AddCheckingAccountFrag : Fragment() , RecyclerItemClickListener {
    private lateinit var useful: Useful

    private var listTravelHistorys = ArrayList<Travel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_checking_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureInitialViews()
        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())

    }


    override fun onClickListenerTravelHistory(travel: Travel) {
        useful = Useful(requireContext())
        activity?.let { it1 ->
            useful.startFragmentOnBack(
                AddBankFormFrag(),
                it1.supportFragmentManager
            )
        }


    }

    fun configureInitialViews() {
        accountBankForms_rv.apply {
            setHasFixedSize(true)
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(
                resources.getDrawable(
                    R.drawable.item_decoration_margin_left,
                    null
                )
            )
            accountBankForms_rv.addItemDecoration(itemDecoration)

        }
        val myAdressesAdapter = AddBankFormAdapter(requireContext(), listTravelHistorys, this)

        val layoutManagerAdresses: RecyclerView.LayoutManager = GridLayoutManager(context, 1)

        accountBankForms_rv.layoutManager = layoutManagerAdresses

        accountBankForms_rv.adapter = myAdressesAdapter
    }
}
