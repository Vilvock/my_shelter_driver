package br.com.app5m.appshelterdriver.ui.fragment.drawer.other

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.RideControl
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.ui.adapter.HistoricRideAdapter
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.empty_list.*
import kotlinx.android.synthetic.main.fragment_user_historic_rides.*
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class UserHistoricRidesFrag : Fragment(), WSResult {

    private lateinit var useful: Useful
    private lateinit var rideControl: RideControl

    private var historicRides = ArrayList<Ride>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_historic_rides, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        useful = Useful(requireContext())
        rideControl = RideControl(requireContext(), this, useful)

        configRecycler()

        screenTitle_tv.text = "Minhas viagens"
        title_tv.text = "Não foram encontrados resultados!"
        list_iv.setImageDrawable(resources.getDrawable(R.drawable.default_search))

        swipeRefresh.setOnRefreshListener {
            useful.openLoading()
            rideControl.findAllDriver()}
    }

    override fun onResume() {
        super.onResume()
        useful.openLoading()
        rideControl.findAllDriver()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun rResponse(list: List<Ride>, type: String) {

        if (swipeRefresh.isRefreshing) swipeRefresh.isRefreshing = false

        useful.closeLoading()

        historicRides.clear()
        historicRides.addAll(list)

        if (type == "findAll") {

            if (historicRides[0].rows != "0") {

                historicRides_rv.visibility = View.VISIBLE
                emptyList_layout.visibility = View.GONE

            } else {
                historicRides_rv.visibility = View.GONE
                emptyList_layout.visibility = View.VISIBLE
            }

            historicRides_rv.adapter!!.notifyDataSetChanged()
        }

    }


    private fun configRecycler(){

        val historicRideAdapter = HistoricRideAdapter(historicRides, requireContext())

        historicRides_rv.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historicRideAdapter
        }

    }
}