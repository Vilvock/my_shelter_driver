package br.com.app5m.appshelterdriver.fragment.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.adapter.DocumentStatusAdapter
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.model.DriverDocument
import kotlinx.android.synthetic.main.fragment_document_status.*






class DocumentStatusFragment : Fragment(),RecyclerItemClickListener {
    private var listDocumentStatus = ArrayList<DriverDocument>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(br.com.app5m.appshelterdriver.R.layout.fragment_document_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureInitialViews()

        listDocumentStatus.add(DriverDocument())
        listDocumentStatus.add(DriverDocument())
        listDocumentStatus.add(DriverDocument())
        listDocumentStatus.add(DriverDocument())
        listDocumentStatus.add(DriverDocument())


    }
    fun configureInitialViews() {

        documentStatusRv.apply {
            setHasFixedSize(true)

        }
        val myPromotionsAdapter = DocumentStatusAdapter(requireContext(), listDocumentStatus, this)

        val layoutDocumentStatus: RecyclerView.LayoutManager = GridLayoutManager(context, 1)

        documentStatusRv.layoutManager = layoutDocumentStatus

        documentStatusRv.adapter = myPromotionsAdapter
    }
}