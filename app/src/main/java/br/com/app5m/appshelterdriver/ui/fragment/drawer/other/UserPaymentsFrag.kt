package br.com.app5m.appshelterdriver.ui.fragment.drawer.other

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.BankControl
import br.com.app5m.appshelterdriver.controller.UserControl
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Bank
import br.com.app5m.appshelterdriver.models.Receive
import br.com.app5m.appshelterdriver.models.User
import br.com.app5m.appshelterdriver.ui.adapter.AccountAdapter
import br.com.app5m.appshelterdriver.ui.adapter.ReceiveAdapter
import br.com.app5m.appshelterdriver.util.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.empty_list.*
import kotlinx.android.synthetic.main.fragment_user_account.*
import kotlinx.android.synthetic.main.fragment_user_account.swipeRefresh
import kotlinx.android.synthetic.main.fragment_user_historic_rides.*
import kotlinx.android.synthetic.main.fragment_user_payments.*
import kotlinx.android.synthetic.main.fragment_user_payments.screenTitle_tv
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class UserPaymentsFrag : Fragment(), WSResult, RecyclerItemClickListener {

    private lateinit var useful: Useful
    private lateinit var userControl: UserControl

    private val paymentList = ArrayList<Receive>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_payments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        useful = Useful(requireContext())
        userControl = UserControl(requireContext(), this, useful)


        configRecycler()

        screenTitle_tv.text = "Meus pagamentos"
        title_tv.text = "Não foram encontrados resultados!"
        list_iv.setImageDrawable(resources.getDrawable(R.drawable.default_search))

        swipeRefresh.setOnRefreshListener {
            useful.openLoading()
            userControl.listReceives() }
    }

    override fun onResume() {
        super.onResume()
        useful.openLoading()
        userControl.listReceives()
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun uResponse(list: List<User>, type: String) {

        if (swipeRefresh.isRefreshing) swipeRefresh.isRefreshing = false

        useful.closeLoading()

        paymentList.clear()
        paymentList.addAll(list[0].receiveList)

        if (paymentList[0].rows != "0") {
            payments_rv.visibility = View.VISIBLE
            emptyList_layout.visibility = View.GONE
        } else {

            payments_rv.visibility = View.GONE
            emptyList_layout.visibility = View.VISIBLE
        }

        payments_rv.adapter!!.notifyDataSetChanged()
    }

    private fun configRecycler() {

        val receiveAdapter = ReceiveAdapter(paymentList, requireContext(), this)

        payments_rv.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = receiveAdapter
        }
    }

}