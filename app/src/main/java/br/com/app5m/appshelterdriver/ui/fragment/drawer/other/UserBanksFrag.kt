package br.com.app5m.appshelterdriver.ui.fragment.drawer.other

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.BankControl
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Bank
import br.com.app5m.appshelterdriver.ui.adapter.BankAdapter
import br.com.app5m.appshelterdriver.util.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.fragment_user_banks.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class UserBanksFrag : Fragment(), WSResult, RecyclerItemClickListener {

    private lateinit var useful: Useful
    private lateinit var bankControl: BankControl

    private var bankList = ArrayList<Bank>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_banks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        useful = Useful(requireContext())
        bankControl = BankControl(requireContext(), this, useful)


        swipeRefresh.setOnRefreshListener { bankControl.listBanksUser() }
    }

    override fun onResume() {
        super.onResume()
        useful.openLoading()
        bankControl.listBanksUser()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_add -> {

            }

        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun bResponse(list: List<Bank>, type: String) {

        if (swipeRefresh.isRefreshing) swipeRefresh.isRefreshing = false

        useful.closeLoading()

        bankList.clear()
        bankList.addAll(list)

        banks_rv.visibility = View.VISIBLE
        banks_rv.adapter!!.notifyDataSetChanged()

    }

    private fun configRecycler() {

        val bankAdapter = BankAdapter(bankList, requireContext())

        banks_rv.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bankAdapter
        }
    }
}