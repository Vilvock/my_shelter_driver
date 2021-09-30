package br.com.app5m.appshelterdriver.ui.fragment.driveracesinfo

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
import br.com.app5m.appshelterdriver.ui.adapter.DriveRaceInfoAdapter
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_add_checking_account.*
import kotlinx.android.synthetic.main.fragment_tab_scheduled.*


class TabScheduledFrag : Fragment(),RecyclerItemClickListener {
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
        return inflater.inflate(R.layout.fragment_tab_scheduled, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureInitialViews()
        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
        listTravelHistorys.add(Travel())
    }
    fun configureInitialViews() {
        scheduledRace_rv.apply {
            setHasFixedSize(true)
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(
                resources.getDrawable(
                    R.drawable.item_decoration_layout_no_bg,
                    null
                )
            )
            scheduledRace_rv.addItemDecoration(itemDecoration)

        }
        val myAdressesAdapter = DriveRaceInfoAdapter(requireContext(), listTravelHistorys, this)

        val layoutManagerAdresses: RecyclerView.LayoutManager = GridLayoutManager(context, 1)

        scheduledRace_rv.layoutManager = layoutManagerAdresses

        scheduledRace_rv.adapter = myAdressesAdapter
    }
}