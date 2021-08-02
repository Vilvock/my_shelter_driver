package br.com.app5m.appshelterdriver.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.model.Promotion


class MyPromotionsAdapter (private val context: Context, private val listPromotions: List<Promotion>,
                           private val clickOnListener: RecyclerItemClickListener
)
    : RecyclerView.Adapter<MyPromotionsAdapter.MyPromotionsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPromotionsViewHolder {
        val listItem: View = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.adapter_mypromotions,
                parent,
                false
            ) // vai conectar com os ids abaixo
        return MyPromotionsViewHolder(listItem)


    }

    override fun onBindViewHolder(holder: MyPromotionsViewHolder, position: Int) {
        val promotion = listPromotions[position]

        /* holder.productNameCartTv.text = "Nome do produto"
         holder.valueProductTv.text = "100,00"
 */
        holder.itemView.setOnClickListener { clickOnListener.onClickListenerMyPromotions(promotion) }

    }

    override fun getItemCount(): Int {
        return listPromotions.size
    }

    class MyPromotionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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