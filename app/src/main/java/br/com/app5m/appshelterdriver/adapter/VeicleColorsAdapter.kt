package br.com.app5m.appshelterdriver.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener

import br.com.app5m.appshelterdriver.model.VehicleColor


class VeicleColorsAdapter(private val context: Context, private val listVehicleColor: List<VehicleColor>,
                          private val clickOnListener: RecyclerItemClickListener
)
    : RecyclerView.Adapter<VeicleColorsAdapter.VeicleColorViewHolder>() {


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
        val veicleColor = listVehicleColor[position]




        holder.itemView.setOnClickListener { clickOnListener.onClickListenerVeicleColor(veicleColor) }

    }

    override fun getItemCount(): Int {
        return listVehicleColor.size
    }

    class VeicleColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}