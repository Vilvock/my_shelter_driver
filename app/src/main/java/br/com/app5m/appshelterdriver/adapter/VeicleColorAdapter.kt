package br.com.app5m.appshelterdriver.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R

import br.com.app5m.appshelterdriver.model.VeicleColor
import kotlinx.android.synthetic.main.adapter_veicle_color.view.*


class VeicleColorAdapter(val arrayList: ArrayList<VeicleColor>, val context: Context) :
    RecyclerView.Adapter<VeicleColorAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(model: VeicleColor) {

            itemView.veicleColorNameTv.text = model.name
            itemView.veicleColorIv.setImageResource(model.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_veicle_color,parent,false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])
    }

}