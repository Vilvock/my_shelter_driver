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
import br.com.app5m.appshelterdriver.ui.adapter.AccountAdapter
import br.com.app5m.appshelterdriver.util.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.empty_list.*
import kotlinx.android.synthetic.main.fragment_user_account.*
import kotlinx.android.synthetic.main.fragment_user_account.screenTitle_tv
import kotlinx.android.synthetic.main.fragment_user_account.swipeRefresh
import kotlinx.android.synthetic.main.fragment_user_payments.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class UserAccountFrag : Fragment(), WSResult, RecyclerItemClickListener {

    private lateinit var useful: Useful
    private lateinit var bankControl: BankControl

    val bankList = ArrayList<Bank>()
    var position: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        useful = Useful(requireContext())
        bankControl = BankControl(requireContext(), this, useful)

        configRecycler()

        screenTitle_tv.text = "Financeiro"
        title_tv.text = "Não foram encontrados resultados!"
        list_iv.setImageDrawable(resources.getDrawable(R.drawable.default_search))

        swipeRefresh.setOnRefreshListener {
            useful.openLoading()
            bankControl.listBanksUser() }
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
                useful.showDefaultDialogView(requireActivity().supportFragmentManager, "account")
            }

        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun bResponse(list: List<Bank>, type: String) {

        if (swipeRefresh.isRefreshing) swipeRefresh.isRefreshing = false

        if (type == "delete"){
            bankControl.listBanksUser()
            return
        }

        useful.closeLoading()

        bankList.clear()
        bankList.addAll(list)

        if (bankList[0].rows != "0") {
            banks_rv.visibility = View.VISIBLE
            emptyList_layout.visibility = View.GONE
        } else {

            banks_rv.visibility = View.GONE
            emptyList_layout.visibility = View.VISIBLE
        }

        banks_rv.adapter!!.notifyDataSetChanged()

    }

    private fun configRecycler() {

        val bankAdapter = AccountAdapter(bankList, requireContext(), useful, this, this)

        banks_rv.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bankAdapter
        }
    }

    override fun onClickListenerBank(positionBank: Int) {

        this.position = positionBank

        useful.showDefaultDialogView(parentFragmentManager, "update_account")


    }
}