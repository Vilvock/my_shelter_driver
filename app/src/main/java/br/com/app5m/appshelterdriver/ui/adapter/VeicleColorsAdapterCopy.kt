package br.com.app5m.appshelterdriver.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R

import br.com.app5m.appshelterdriver.models.VehicleColor
import kotlinx.android.synthetic.main.adapter_vehicle_color.view.*

class VeicleColorsAdapterCopy(val arrayList: ArrayList<VehicleColor>, val context: Context) :
    RecyclerView.Adapter<VeicleColorsAdapterCopy.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(vehicleColor: VehicleColor) {

            itemView.veicleColorNameTv.text = vehicleColor.name
            itemView.veicleColorIv.setImageResource(vehicleColor.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_vehicle_color,parent,false)
        Toast.makeText(context, "vamo ve", Toast.LENGTH_SHORT).show()
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])

        holder.itemView.setOnClickListener {
            if(position == 0){

            }
        }
    }

}
