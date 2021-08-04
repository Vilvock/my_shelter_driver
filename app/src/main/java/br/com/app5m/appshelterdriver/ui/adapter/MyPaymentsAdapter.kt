package br.com.app5m.appshelterdriver.ui.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.model.Travel


class MyPaymentsAdapter (private val context: Context, private val listTravelHistory: List<Travel>,
                         private val clickOnListener: RecyclerItemClickListener
)
    : RecyclerView.Adapter<MyPaymentsAdapter.TravelHistoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelHistoryViewHolder {
        val listItem: View = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.adapter_my_payments,
                parent,
                false
            ) // vai conectar com os ids abaixo
        return TravelHistoryViewHolder(listItem)


    }

    override fun onBindViewHolder(holder: TravelHistoryViewHolder, position: Int) {
        val travel = listTravelHistory[position]

        /* holder.productNameCartTv.text = "Nome do produto"
         holder.valueProductTv.text = "100,00"
 */
        holder.itemView.setOnClickListener { clickOnListener.onClickListenerTravelHistory(travel) }

    }

    override fun getItemCount(): Int {
        return listTravelHistory.size
    }

    class TravelHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
/*        val productNameCartTv: TextView
        val valueProductTv: TextView
        val productImageIv: ImageView

        init {
            productNameCartTv = itemView.findViewById(R.id.productNameCartTv)
            valueProductTv = itemView.findViewById(R.id.valueProductTv)
            productImageIv = itemView.findViewById(R.id.productImageIv)


        }*/
    }
}
