package br.com.app5m.appshelterdriver.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.models.Bank
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.util.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.util.Useful
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import kotlinx.android.synthetic.main.dialog_bottom_view_initride.*


class BankAdapter(private val list: List<Bank>,
                  private val context: Context,
                    private val recyclerItemClickListener: RecyclerItemClickListener):
        RecyclerView.Adapter<BankAdapter.Holder>(){


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_banks, parent, false))
    }

    @SuppressLint("NotifyDataSetChanged", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val bank = list[position]

//        [{"id":1,"type":1,"tipo_chave_pix":"CPF","chave_pix":"884.192.000-93",
//            "banco":"NU pagamentos","agencia":736,"agencia_d":1,"cc":151152,
//            "cc_d":1,"tipo_conta":"corrente","rows":1}]



        holder.itemView.setOnClickListener {
            recyclerItemClickListener.onClickListenerBank(position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

}