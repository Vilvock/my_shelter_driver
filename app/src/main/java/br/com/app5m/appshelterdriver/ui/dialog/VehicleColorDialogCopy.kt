/*
package br.com.app5m.appshelterdriver.ui.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.adapter.VeicleColorAdapter
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.model.VeicleColor
import kotlinx.android.synthetic.main.dialog_veicle_color.*
import kotlin.collections.ArrayList

class VeicleColorDialogCopy: DialogFragment(), RecyclerItemClickListener{
    private val TAG = "VeicleColorDialog"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_veicle_color, container)




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        configureInitialViews()

    }
    fun configureInitialViews(){
        val arrayList = ArrayList<VeicleColor>()

        arrayList.add(VeicleColor("azul",R.drawable.blue_circle))
        arrayList.add(VeicleColor("black",R.drawable.black_circle))

        val myAdapter = context?.let { VeicleColorAdapter(arrayList, it) }

        veicleColorRv.layoutManager = LinearLayoutManager(context)
        veicleColorRv.adapter = myAdapter

    }


}*/
