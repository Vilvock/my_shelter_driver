package br.com.app5m.appshelterdriver.ui.fragment.bankaccount.tabs

import android.content.Intent
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
import br.com.app5m.appshelterdriver.model.Travel
import br.com.app5m.appshelterdriver.ui.adapter.MyBankAdapter
import br.com.app5m.appshelterdriver.ui.adapter.MyPixAdapter
import br.com.app5m.appshelterdriver.ui.fragment.bankaccount.AddPixFrag
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_my_bank.*
import kotlinx.android.synthetic.main.fragment_my_pix.*


class MyPixFrag : Fragment(), RecyclerItemClickListener {
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
        return inflater.inflate(R.layout.fragment_my_pix, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        useful = Useful(requireContext())

        add_pix_account_bt.setOnClickListener {
            activity?.let { it1 -> useful.startFragmentOnBack(AddPixFrag(), it1.supportFragmentManager) }

        }
        configureInitialViews()

        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
    }

    fun configureInitialViews() {
        my_pix_rv.apply {
            setHasFixedSize(true)
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(
                resources.getDrawable(
                    R.drawable.item_decoration_layout_no_bg,
                    null
                )
            )
            my_pix_rv.addItemDecoration(itemDecoration)

        }
        val myAdressesAdapter = MyPixAdapter(requireContext(), listTravelHistorys, this)

        val layoutManagerAdresses: RecyclerView.LayoutManager = GridLayoutManager(context, 1)

        my_pix_rv.layoutManager = layoutManagerAdresses

        my_pix_rv.adapter = myAdressesAdapter
    }
}