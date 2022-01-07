package br.com.app5m.appshelterdriver.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.models.Bank
import br.com.app5m.appshelterdriver.models.Receive
import br.com.app5m.appshelterdriver.models.User
import br.com.app5m.appshelterdriver.util.RecyclerItemClickListener

class ReceiveAdapter(private val list: List<Receive>,
                     private val context: Context,
                     private val recyclerItemClickListener: RecyclerItemClickListener):
        RecyclerView.Adapter<ReceiveAdapter.Holder>(){


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receiveInfoTv: TextView = itemView.findViewById(R.id.receiveInfo_tv)
        val viewDetailsTv: TextView = itemView.findViewById(R.id.viewDetails_tv)
        val dateTv: TextView = itemView.findViewById(R.id.date_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_receives, parent, false))
    }

    @SuppressLint("NotifyDataSetChanged", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val receive = list[position]

        holder.receiveInfoTv.text = "Tipo: " + receive.typeUser +
                "\nValor: " + receive.value +
                "\n\nBanco: " + receive.bank

        holder.dateTv.text = receive.date

        holder.viewDetailsTv.setOnClickListener {

        }

//        {
//            "id": 5,
//            "id_conta": 3,

//            "tipo": "PIX",
//            "data": "05\/11\/2021 - 07:44:49",
//            "valor": " R$ 1,00",
//            "comprovante": "4517-7826ab84891547e5682ee76c09a94e30.jpg",
//            "banco": "Bradesco",
//            "agencia": 736,
//            "cc": 151152,
//            "chave_pix": "884.192.000-93",
//            "tipo_chave_pix": "CPF",

//            "rows": 4
//        },

    }

    override fun getItemCount(): Int {
        return list.size
    }

}