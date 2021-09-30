package br.com.app5m.appshelterdriver.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.models.Local


class MyAdressesAdapter (private val context: Context, private val listMyAdresses: List<Local>,
                         private val clickOnListener: RecyclerItemClickListener
)
    : RecyclerView.Adapter<MyAdressesAdapter.MyAdressesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdressesViewHolder {
        val listItem: View = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.adapter_my_adresses,
                parent,
                false
            ) // vai conectar com os ids abaixo
        return MyAdressesViewHolder(listItem)


    }

    override fun onBindViewHolder(holder: MyAdressesViewHolder, position: Int) {
        val local = listMyAdresses[position]
        
       /* holder.productNameCartTv.text = "Nome do produto"
        holder.valueProductTv.text = "100,00"
*/
        holder.itemView.setOnClickListener { clickOnListener.onClickListenerMyAdresses(local) }

    }

    override fun getItemCount(): Int {
        return listMyAdresses.size
    }

    class MyAdressesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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