package br.com.app5m.appshelterdriver.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.models.Ride
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import kotlinx.android.synthetic.main.dialog_bottom_view_initride.*


class HistoricRideAdapter(private val list: List<Ride>,
                          private val context: Context):
        RecyclerView.Adapter<HistoricRideAdapter.Holder>(), OnMapReadyCallback {

    var map: GoogleMap? = null

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val mapView: MapView = itemView.findViewById(R.id.lite_listrow_map)
        val rideInfoTv: TextView = itemView.findViewById(R.id.info_tv)
        val priceTv: TextView = itemView.findViewById(R.id.price_tv)
        val dateTv: TextView = itemView.findViewById(R.id.date_tv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_historic_rides, parent, false))
    }

    @SuppressLint("NotifyDataSetChanged", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val historicRide = list[position]

        // Initialise the MapView
        holder.mapView.onCreate(null)
        // Set the map ready callback to receive the GoogleMap object
        holder.mapView.getMapAsync(this)

        val origin = "Embarque: " + historicRide.originAddress
        val destination = "Destino: " + historicRide.destinationAddress
        val distance = "Distância: " + historicRide.distance + " " + historicRide.distanceInitials
        val duration = "Tempo estimado: " + historicRide.routeTime
        val payment = "Tipo de pagamento: " + historicRide.nameTypePayment

        holder.dateTv.text = historicRide.date
        holder.rideInfoTv.text = "$origin\n$destination\n$distance\n$duration\n$payment"
        holder.priceTv.text = historicRide.totalValue


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(context)
        map = googleMap
        setMapLocation();
    }

    /**
     * Displays a {@link LiteListDemoActivity.NamedLocation} on a
     * {@link com.google.android.gms.maps.GoogleMap}.
     * Adds a marker and centers the camera on the NamedLocation with the normal map type.
     */
    fun setMapLocation() {
//        if (map == null) return;
//
//        NamedLocation data = (NamedLocation) mapView.getTag();
//        if (data == null) return;
//
//        // Add a marker for this item and set the camera
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(data.location, 13f));
//        map.addMarker(new MarkerOptions().position(data.location));
//
//        // Set the map type back to normal.
//        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    fun bindView(position: Int) {
//        NamedLocation item = namedLocations[pos];
//        // Store a reference of the ViewHolder object in the layout.
//        layout.setTag(this);
//        // Store a reference to the item in the mapView's tag. We use it to get the
//        // coordinate of a location, when setting the map location.
//        mapView.setTag(item);
        setMapLocation();
//        title.setText(item.name);
    }

}