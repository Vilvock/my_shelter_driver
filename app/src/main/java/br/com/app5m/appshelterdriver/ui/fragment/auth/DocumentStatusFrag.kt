package br.com.app5m.appshelterdriver.ui.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_document_status.*

class DocumentStatusFrag : Fragment() {

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


    }
    fun configureInitialViews() {

        identityRv.apply {
            setHasFixedSize(true)

        }
//        val documentStatusAdapter = DocumentStatusAdapter(requireContext(), listDocumentStatus, this)
//
//        val layoutDocumentStatus: RecyclerView.LayoutManager = GridLayoutManager(context, 1)
//
//        identityRv.layoutManager = layoutDocumentStatus
//
//        identityRv.adapter = documentStatusAdapter
//        ////////////////////////////////
//        cnhRv.apply {
//            setHasFixedSize(true)
//
//        }
//        val cnhRvStatusAdapter = DocumentStatusAdapter(requireContext(), listDocumentStatus, this)
//
//        val layoutcnhRvStatus: RecyclerView.LayoutManager = GridLayoutManager(context, 1)
//
//        cnhRv.layoutManager = layoutcnhRvStatus
//
//        cnhRv.adapter = cnhRvStatusAdapter

    }
}