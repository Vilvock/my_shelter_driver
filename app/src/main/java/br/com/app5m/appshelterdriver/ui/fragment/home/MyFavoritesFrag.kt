package br.com.app5m.appshelterdriver.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.adapter.LastDestinationsAdapter
import br.com.app5m.appshelterdriver.ui.adapter.MyAdressesAdapter
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.models.Local
import kotlinx.android.synthetic.main.dialog_veicle_color.*
import kotlinx.android.synthetic.main.fragment_chose_adestination.*


/**
 * A simple [Fragment] subclass.
 * Use the [MyFavoritesFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyFavoritesFrag : Fragment(),RecyclerItemClickListener {
    private var listLocalDestination  = ArrayList<Local>()
    private var listMyAdresses  = ArrayList<Local>()


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
        return inflater.inflate(R.layout.fragment_my_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureInitialViews()

        listMyAdresses.add(Local())
        listLocalDestination.add(Local())
        listLocalDestination.add(Local())
        listLocalDestination.add(Local())
        listLocalDestination.add(Local())
        listLocalDestination.add(Local())
        listLocalDestination.add(Local())

    }


    fun configureInitialViews(){
        myAdressesRv.apply {
            setHasFixedSize(true)
             val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(resources.getDrawable(R.drawable.item_decoration_margin_left, null))
            myAdressesRv.addItemDecoration(itemDecoration)

        }
        val myAdressesAdapter = MyAdressesAdapter(requireContext(),listMyAdresses,this)

        val layoutManagerAdresses: RecyclerView.LayoutManager = GridLayoutManager(context, 1)

        myAdressesRv.layoutManager = layoutManagerAdresses

        myAdressesRv.adapter = myAdressesAdapter



        lastDestinationsRv.apply {
            setHasFixedSize(true)
//            addItemDecoration(CustomDivider(this.context, DividerItemDecoration.VERTICAL))
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(resources.getDrawable(R.drawable.item_decoration_margin_left, null))
            lastDestinationsRv.addItemDecoration(itemDecoration)

        }


        val lastDestinationAdapter = LastDestinationsAdapter(requireContext(),listLocalDestination,this)

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 1)

        lastDestinationsRv.layoutManager = layoutManager

        lastDestinationsRv.adapter = lastDestinationAdapter

    }
}