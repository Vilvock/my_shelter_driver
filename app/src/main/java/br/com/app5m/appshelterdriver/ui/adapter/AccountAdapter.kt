package br.com.app5m.appshelterdriver.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.models.Bank
import br.com.app5m.appshelterdriver.util.RecyclerItemClickListener

class AccountAdapter(private val list: List<Bank>,
                     private val context: Context,
                     private val recyclerItemClickListener: RecyclerItemClickListener):
        RecyclerView.Adapter<AccountAdapter.Holder>(){


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bankTv: TextView = itemView.findViewById(R.id.bank_tv)
        val ccTv: TextView = itemView.findViewById(R.id.cc_tv)
        val ccDTv: TextView = itemView.findViewById(R.id.ccD_tv)
        val agencyTv: TextView = itemView.findViewById(R.id.agency_tv)
        val agencyDTv: TextView = itemView.findViewById(R.id.agencyD_tv)
        val bankLl: LinearLayout = itemView.findViewById(R.id.bank_ll)
        val pixLL: LinearLayout = itemView.findViewById(R.id.pix_ll)
        val titleTv: TextView = itemView.findViewById(R.id.title_tv)
        val typePix: TextView = itemView.findViewById(R.id.typePix_tv)
        val pixKeyTv: TextView = itemView.findViewById(R.id.pixKey_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_accounts, parent, false))
    }

    @SuppressLint("NotifyDataSetChanged", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val bank = list[position]

        if (bank.bank != "") {

            holder.titleTv.text = "Conta Bancária"

            holder.pixLL.visibility = View.GONE

            holder.bankTv.text = bank.bank
            holder.ccTv.text = bank.cc
            holder.ccDTv.text = bank.ccDigit
            holder.agencyTv.text = bank.agency
            holder.agencyDTv.text = bank.agencyDigit

        } else {

            holder.titleTv.text = "PIX"

            holder.bankLl.visibility = View.GONE

            holder.typePix.text = bank.typePixKey
            holder.pixKeyTv.text = bank.pixKey

        }

        holder.itemView.setOnClickListener {
            recyclerItemClickListener.onClickListenerBank(position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}