package br.com.app5m.appshelterdriver.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.model.Local


class LastDestinationsAdapter (private val context: Context, private val listLocalDestination: List<Local>,
                               private val clickOnListener: RecyclerItemClickListener
)
    : RecyclerView.Adapter<LastDestinationsAdapter.LocalDestinationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalDestinationViewHolder {
        val listItem: View = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.adapter_last_destination,
                parent,
                false
            ) // vai conectar com os ids abaixo
        return LocalDestinationViewHolder(listItem)


    }

    override fun onBindViewHolder(holder: LocalDestinationViewHolder, position: Int) {
        val local = listLocalDestination[position]
        
       /* holder.productNameCartTv.text = "Nome do produto"
        holder.valueProductTv.text = "100,00"
*/
        holder.itemView.setOnClickListener { clickOnListener.onClickListenerLocalDestination(local) }

    }

    override fun getItemCount(): Int {
        return listLocalDestination.size
    }

    class LocalDestinationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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