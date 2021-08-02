package br.com.app5m.appshelterdriver.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.model.DriverDocument


class DocumentStatusAdapter (private val context: Context, private val listDocumentStatus: List<DriverDocument>,
                             private val clickOnListener: RecyclerItemClickListener
)
    : RecyclerView.Adapter<DocumentStatusAdapter.DocumentStatusViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentStatusViewHolder {
        val listItem: View = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.adapter_document_status,
                parent,
                false
            ) // vai conectar com os ids abaixo
        return DocumentStatusViewHolder(listItem)


    }

    override fun onBindViewHolder(holder: DocumentStatusViewHolder, position: Int) {
        val status = listDocumentStatus[position]

        /* holder.productNameCartTv.text = "Nome do produto"
         holder.valueProductTv.text = "100,00"
 */
        holder.itemView.setOnClickListener { clickOnListener.onClickListenerDocumentStatus(status) }

    }

    override fun getItemCount(): Int {
        return listDocumentStatus.size
    }

    class DocumentStatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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