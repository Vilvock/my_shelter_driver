package br.com.app5m.appshelterdriver.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener

import br.com.app5m.appshelterdriver.model.VeicleColor
import kotlinx.android.synthetic.main.adapter_veicle_color.view.*


class VeicleColorAdapter(private val context: Context, private val listVeicleColor: List<VeicleColor>,
                         private val clickOnListener: RecyclerItemClickListener
)
    : RecyclerView.Adapter<VeicleColorAdapter.VeicleColorViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VeicleColorViewHolder {
        val listItem: View = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.adapter_veicle_color,
                parent,
                false
            ) // vai conectar com os ids abaixo
        return VeicleColorViewHolder(listItem)


    }

    override fun onBindViewHolder(holder: VeicleColorViewHolder, position: Int) {
        val veicleColor = listVeicleColor[position]




        holder.itemView.setOnClickListener { clickOnListener.onClickListenerVeicleColor(veicleColor) }

    }

    override fun getItemCount(): Int {
        return listVeicleColor.size
    }

    class VeicleColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}