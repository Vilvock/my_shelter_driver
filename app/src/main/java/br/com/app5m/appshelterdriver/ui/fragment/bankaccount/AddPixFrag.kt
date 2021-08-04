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
import br.com.app5m.appshelterdriver.model.Travel
import br.com.app5m.appshelterdriver.ui.adapter.AddPixFormAdapter
import br.com.app5m.appshelterdriver.ui.adapter.MyPaymentsAdapter
import br.com.app5m.appshelterdriver.ui.fragment.signin.UploadDocumentsFrag
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_add_pix.*
import kotlinx.android.synthetic.main.fragment_my_payments.*


class AddPixFrag : Fragment() , RecyclerItemClickListener {
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
        return inflater.inflate(R.layout.fragment_add_pix, container, false)
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
        activity?.let { it1 -> useful.startFragmentOnBack(AddPixFormFrag(), it1.supportFragmentManager) }


    }

    fun configureInitialViews() {
        pixForms_rv.apply {
            setHasFixedSize(true)
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(
                resources.getDrawable(
                    R.drawable.item_decoration_margin_left,
                    null
                )
            )
            pixForms_rv.addItemDecoration(itemDecoration)

        }
        val myAdressesAdapter = AddPixFormAdapter(requireContext(), listTravelHistorys, this)

        val layoutManagerAdresses: RecyclerView.LayoutManager = GridLayoutManager(context, 1)

        pixForms_rv.layoutManager = layoutManagerAdresses

        pixForms_rv.adapter = myAdressesAdapter
    }
}