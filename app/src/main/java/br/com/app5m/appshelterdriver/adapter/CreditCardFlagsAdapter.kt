package br.com.app5m.appshelterdriver.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.model.CreditCardFlag


class CreditCardFlagsAdapter (private val context: Context, private val listCreditCardFrags: List<CreditCardFlag>,
                              private val clickOnListener: RecyclerItemClickListener
)
    : RecyclerView.Adapter<CreditCardFlagsAdapter.CreditCardFlagHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditCardFlagHolder {
        val listItem: View = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.fragment_credit_card_flags_adapter,
                parent,
                false
            ) // vai conectar com os ids abaixo
        return CreditCardFlagHolder(listItem)


    }

    override fun onBindViewHolder(holder: CreditCardFlagHolder, position: Int) {
        val flag = listCreditCardFrags[position]




        holder.itemView.setOnClickListener { clickOnListener.onClickListenerCreditCardFlag(flag) }

    }

    override fun getItemCount(): Int {
        return listCreditCardFrags.size
    }

    class CreditCardFlagHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}