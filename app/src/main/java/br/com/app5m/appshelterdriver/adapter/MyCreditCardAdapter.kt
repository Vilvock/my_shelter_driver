package br.com.app5m.appshelterdriver.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.model.CreditCard


class MyCreditCardAdapter (private val context: Context, private val listMyCreditCards: List<CreditCard>,
                           private val clickOnListener: RecyclerItemClickListener
)
    : RecyclerView.Adapter<MyCreditCardAdapter.MyCreditCardHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCreditCardHolder {
        val listItem: View = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.adapter_my_payment_methods,
                parent,
                false
            ) // vai conectar com os ids abaixo
        return MyCreditCardHolder(listItem)


    }

    override fun onBindViewHolder(holder: MyCreditCardHolder, position: Int) {
        val card = listMyCreditCards[position]




        holder.itemView.setOnClickListener { clickOnListener.onClickListenerMyPayMethods(card) }

    }

    override fun getItemCount(): Int {
        return listMyCreditCards.size
    }

    class MyCreditCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}