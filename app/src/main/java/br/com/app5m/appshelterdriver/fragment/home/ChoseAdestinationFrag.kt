package br.com.app5m.appshelterdriver.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.adapter.LastDestinationAdapter
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.model.Local
import kotlinx.android.synthetic.main.dialog_veicle_color.*
import kotlinx.android.synthetic.main.fragment_chose_adestination.*


/**
 * A simple [Fragment] subclass.
 * Use the [ChoseAdestination.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChoseAdestination : Fragment(),RecyclerItemClickListener {
    private var listLocalDestination  = ArrayList<Local>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chose_adestination, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureInitialViews()
        listLocalDestination.add(Local())
        listLocalDestination.add(Local())
        listLocalDestination.add(Local())
        listLocalDestination.add(Local())
        listLocalDestination.add(Local())
        listLocalDestination.add(Local())
        listLocalDestination.add(Local())

    }


    fun configureInitialViews(){
        lastDestinationsRv.addItemDecoration(VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE))
        //or
        //or
        lastDestinationsRv.addItemDecoration(DividerItemDecoration(activity))
        //or
        //or
        lastDestinationsRv.addItemDecoration(
            DividerItemDecoration(activity, R.drawable.divider)
        )


        val lastDestination = LastDestinationAdapter(requireContext(),listLocalDestination,this)

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 1)

        lastDestinationsRv.layoutManager = layoutManager

        lastDestinationsRv.adapter = lastDestination

    }
}