package br.com.app5m.appshelterdriver.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.adapter.VeicleColorsAdapter
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import br.com.app5m.appshelterdriver.model.VehicleColor
import kotlinx.android.synthetic.main.dialog_veicle_color.*

class VehicleColorDialog: DialogFragment(), RecyclerItemClickListener{
    private val TAG = "VeicleColorDialog"


    private var veicleColorList  = ArrayList<VehicleColor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme) }
//se nao for em tela cheia a dialog nao esta abrindo.
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
        veicleColorList.add(VehicleColor())
        veicleColorList.add(VehicleColor())
        veicleColorList.add(VehicleColor())
        veicleColorList.add(VehicleColor())
    }
    fun configureInitialViews(){
        val veicleColorAdapter = VeicleColorsAdapter(requireContext(),veicleColorList,this)

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 1)

        veicleColorRv.layoutManager = layoutManager

        veicleColorRv.adapter = veicleColorAdapter

    }


}