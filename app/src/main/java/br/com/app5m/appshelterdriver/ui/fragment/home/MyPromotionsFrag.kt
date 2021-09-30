package br.com.app5m.appshelterdriver.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.adapter.MyPromotionsAdapter
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.models.Promotion
import kotlinx.android.synthetic.main.fragment_my_promotions.*


class MyPromotionsFrag : Fragment(),RecyclerItemClickListener {
    private var listMyPromotions = ArrayList<Promotion>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_promotions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureInitialViews()

        listMyPromotions.add(Promotion())
        listMyPromotions.add(Promotion())
        listMyPromotions.add(Promotion())
        listMyPromotions.add(Promotion())
    }
    fun configureInitialViews() {
        myPromotionsRv.apply {
            setHasFixedSize(true)
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(
                resources.getDrawable(
                    R.drawable.item_decoration_layout_no_bg,
                    null
                )
            )
            myPromotionsRv.addItemDecoration(itemDecoration)

        }
        val myPromotionsAdapter = MyPromotionsAdapter(requireContext(), listMyPromotions, this)

        val layoutManagerAdresses: RecyclerView.LayoutManager = GridLayoutManager(context, 1)

        myPromotionsRv.layoutManager = layoutManagerAdresses

        myPromotionsRv.adapter = myPromotionsAdapter
    }
}