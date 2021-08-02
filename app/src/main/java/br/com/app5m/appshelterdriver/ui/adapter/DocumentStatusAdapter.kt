package br.com.app5m.appshelterdriver.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.model.DriverDocument

import com.github.vipulasri.timelineview.TimelineView





class DocumentStatusAdapter (private val context: Context, private val listDocumentStatus: List<DriverDocument>,
                             private val clickOnListener: RecyclerItemClickListener
)
    : RecyclerView.Adapter<DocumentStatusAdapter.DocumentStatusViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentStatusViewHolder {
        val view = View.inflate(parent.context, R.layout.adapter_document_status, null)
        return DocumentStatusViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: DocumentStatusViewHolder, position: Int) {
        val status = listDocumentStatus[position]

        holder.itemView.setOnClickListener { clickOnListener.onClickListenerDocumentStatus(status) }

    }

    override fun getItemCount(): Int {
        return listDocumentStatus.size
    }


    class DocumentStatusViewHolder(itemView: View, viewType: Int) :
        RecyclerView.ViewHolder(itemView) {
        var mTimelineView: TimelineView

        init {
            mTimelineView = itemView.findViewById<View>(R.id.timeline) as TimelineView
            mTimelineView.initLine(viewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }
}