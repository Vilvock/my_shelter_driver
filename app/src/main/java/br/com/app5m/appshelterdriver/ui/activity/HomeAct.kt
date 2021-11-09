package br.com.app5m.appshelterdriver.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.com.app5m.appshelterdriver.MainAct
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.RideControl
import br.com.app5m.appshelterdriver.controller.UserControl
import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.models.User
import br.com.app5m.appshelterdriver.util.DialogMessages
import br.com.app5m.appshelterdriver.util.MyLocation
import br.com.app5m.appshelterdriver.util.Preferences
import br.com.app5m.appshelterdriver.util.Useful
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.gson.Gson
import com.google.maps.android.PolyUtil
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.baseboard_accept_race.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.loading.*
import kotlinx.android.synthetic.main.nav_header_home.view.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class HomeAct : AppCompatActivity(), OnMapReadyCallback, WSResult {

    private lateinit var useful: Useful
    private lateinit var preferences: Preferences
    private lateinit var rideControl: RideControl
    private lateinit var userControl: UserControl

    private lateinit var myLocation: MyLocation
    private lateinit var locationResult: MyLocation.LocationResult

    private var mMap: GoogleMap? = null

    enum class MainScreenStage {
        OVERVIEW,
        ACCEPT_RIDE,
        WAITING_PICKUP,
        ONGOING_RIDE,
        FINISH_RIDE
    }

    data class MapPlotData(
        val userPosition: LatLng? = null,
        val originLatLng: LatLng? = null,
        val destinationLatLng: LatLng? = null,
        val vehicleLatLng: LatLng? = null,
        val vehicleAngle: Float? = null,
        val freeVehiclesLatLng: List<LatLng>? = null,
        val polyline: List<LatLng>? = null
    )

    val _screenStageLiveData = MutableLiveData<MainScreenStage>()
    val screenStageLiveData: LiveData<MainScreenStage> = _screenStageLiveData

    val _rideLiveData = MutableLiveData<Ride>()
    val rideLiveData: LiveData<Ride> = _rideLiveData

    val _mapPlotDateLiveData = MutableLiveData<MapPlotData?>()
    val mapPlotDateLiveData: LiveData<MapPlotData?> = _mapPlotDateLiveData

    private lateinit var initialsRideInfo: Ride
    private lateinit var lastRideInfo: Ride

    var isCameraLock: Boolean = true
    private var isRatingDialogShowed: Boolean = true

    private lateinit var mapFragment: SupportMapFragment

    private val handler = Handler()
    private var runnable = Runnable { notifyScreenStageChanged(MainScreenStage.OVERVIEW)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        useful = Useful(this)
        preferences = Preferences(this)
        rideControl = RideControl(this, this, useful)
        userControl = UserControl(this, this, useful)
        myLocation = MyLocation(this)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
            IntentFilter("Notification")
        )

        saveFcm()

        imAvailable_sw.setOnCheckedChangeListener { buttonView, isChecked -> //commit prefs on change

            val driverStatus = User()

            driverStatus.latitude = mapPlotDateLiveData.value!!.userPosition!!.latitude.toString()
            driverStatus.longitude = mapPlotDateLiveData.value!!.userPosition!!.longitude.toString()

            if (isChecked) {
                //online
                driverStatus.online = "1"
            } else {
                //offline
                driverStatus.online = "2"
            }

            userControl.updateStatusOnline(driverStatus)
        }

    }

    override fun onRestart() {
        super.onRestart()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            DialogMessages(this).booleanClick("Atenção!",
                "É necessário ter aceitos todas as permissões para obter maior precisão de sua localização!",
                object : DialogMessages.AnswerBoolean {
                    override fun setOnClickListener(boolean: Boolean) {
                        if (boolean) {
                            startActivity(Intent(this@HomeAct, PermissionLocalizationAct::class.java))
                        } else {
                            finishAffinity()
                        }
                    }
                })
        } else {
            if (screenStageLiveData.value == MainScreenStage.OVERVIEW) {
                isCameraLock = true
                mapFragment.getMapAsync(this)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap?) {
        mMap = map
        mMap?.isMyLocationEnabled = false
        mMap?.uiSettings?.apply {
            isRotateGesturesEnabled = false
            isCompassEnabled = false
            isMapToolbarEnabled = false
            isMyLocationButtonEnabled = false
            isTiltGesturesEnabled = false
        }
        //custom map
//        mMap?.setMapStyle(
//            MapStyleOptions.loadRawResourceStyle(
//                requireContext(),
//                R.raw.map_style_json
//            )
//        )

        //por enquanto deixa aqui
            val bundle = intent.extras

            if (bundle != null) {

                val screenStage: MainScreenStage? = intent.getSerializableExtra("notifyScreen") as MainScreenStage?
                val rideId = intent.extras?.getString("rideId")

                if (rideId != null) {
                    _rideLiveData.value!!.rideId = rideId
                }

                notifyScreenStageChanged(screenStage!!)

            } else {

                notifyScreenStageChanged(MainScreenStage.OVERVIEW)
            }

    }

    override fun rResponse(list: List<Ride>, type: String) {

        lastRideInfo = list[0]

        if (type == "findAll") {

            //    pra pegar localizacao do motora eu faco como?
//    {
//        "id":7,
//        "tipo_carro":1,
//        "id_tipo_pagamento":2,
//        "nome_tipo_pagamento":"Dinheiro",
//        "motorista_nome":null,
//        "motorista_avatar":null,
//        "endereco_in":"Av. Pres. Get\u00falio Vargas 5019 Alvorada",
//        "endereco_out":"Av. Tiradentes 81 Alvorada",
//        "latitude_in":"-30.003922",
//        "longitude_in":"-51.052373",
//        "latitude_out":"-30.0137134",
//        "longitude_out":"-51.0847039",
//        "distancia":4,
//        "distancia_sigla":"km",
//        "tempo_rota":10,
//        "polyline":"",
//        "valor_total":" R$ 5,50",
//        "valor_motorista":" R$ 4,95",
//        "valor_dinamica":" R$ 0,00",
//        "data":"21\/10\/2021 - 22:09:17",
//        "status_corrida":"Solicitada",
//        "rows":1
//    }

            if (lastRideInfo.rows != "0") {

//            for (ride in list) {
                when(lastRideInfo.rideStatus) {

                    "Aceita" -> {
                        _rideLiveData.value = lastRideInfo

                        notifyScreenStageChanged(MainScreenStage.WAITING_PICKUP)
                        //mapupdate aqui quando poly parar de ser null
                    }

                    "Em andamento" -> {

                        _rideLiveData.value = lastRideInfo

                        val findRideProcess = Ride()

                        findRideProcess.latitude = mapPlotDateLiveData.value!!.userPosition!!.latitude.toString()
                        findRideProcess.longitude = mapPlotDateLiveData.value!!.userPosition!!.longitude.toString()
                        findRideProcess.carType = rideLiveData.value!!.carType

                        rideControl.findProcess(findRideProcess)


                    }

                    "Finalizada" -> {

                        /*if(isRatingDialogShowed) {
                            isRatingDialogShowed = false
                            notifyScreenStageChanged(MainScreenStage.FINISH_RIDE)
                        }else {*/

                            handler.postDelayed(runnable, 5000)
                       /* }*/

                    }

                    else -> {
                        //cancelada
                        handler.postDelayed(runnable, 5000)
                    }
                }

//            }

            } else {
                handler.postDelayed(runnable, 5000)
            }


            //find andamento
        } else {
            if (lastRideInfo.rows != "0") {
                _rideLiveData.value = lastRideInfo

                notifyScreenStageChanged(MainScreenStage.ONGOING_RIDE)
            }

        }



    }

    override fun uResponse(list: List<User>, type: String) {

        val userInfo = list[0]

        if (type == "updateStatus") {
            if (userInfo.status == "01") {

                val driverLocation = User()

                driverLocation.latitude = mapPlotDateLiveData.value!!.userPosition!!.latitude.toString()
                driverLocation.longitude = mapPlotDateLiveData.value!!.userPosition!!.longitude.toString()

                userControl.updateLocation(driverLocation)

           } else {
                imAvailable_sw.isChecked = false
                SingleToast.INSTANCE.show(this, userInfo.msg!! , Toast.LENGTH_LONG)
           }
        }

    }

    private fun configDrawer() {

        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {}

        drawerToggle.isDrawerIndicatorEnabled = false
        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu)
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        drawerToggle.setToolbarNavigationClickListener {
            if (drawer_layout.isDrawerVisible(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }

        nav_view.itemIconTintList = null
        nav_view.setNavigationItemSelectedListener {

            val intent = Intent(this, DrawerContainerAct::class.java)

            when (it.itemId) {

                R.id.nav_changeVeicle -> {
                    intent.putExtra("key", "change_vehicle")
                }
                R.id.nav_myRides -> {
                    intent.putExtra("key", "rides")
                }

                R.id.nav_myPayments-> {
                    intent.putExtra("key", "myPayments")
                }

                R.id.nav_bankAccount -> {
                    intent.putExtra("key", "bank_account")
                }
                else -> {
                    DialogMessages(this).click("Atenção",
                        "Se você deslogar, precisará realizar o login novamente mais tarde. Deseja continuar?",
                        object : DialogMessages.Answer {
                            override fun setOnClickListener() {
                                preferences.clearUserData()
                                startActivity(Intent(this@HomeAct, MainAct::class.java))
                                finishAffinity()
                            }
                        })
                    return@setNavigationItemSelectedListener true
                }
            }

            startActivity(intent)

            // Close the drawer
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

        val headerView: View = nav_view.getHeaderView(0)
        headerView.nameUser_tv.text = preferences.getUserData()!!.name
        headerView.emailUser_tv.text = preferences.getUserData()!!.email

        headerView.profile_ib.setOnClickListener {
            val intent = Intent(this, DrawerContainerAct::class.java)
            intent.putExtra("key", "profile")
            startActivity(intent)
        }

    }

    private fun saveFcm() {

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult: InstanceIdResult ->

            if (instanceIdResult.token == "") {

                Log.d("TAG", "token vazio")
            }

            if (preferences.getInstanceTokenFcm() == instanceIdResult.token) {

                Log.d("TAG", "não salvou")

            } else {


                val user = User()

                user.typeUser = "2"
                user.type = WSConstants.TYPE_FCM
                user.registrationId = instanceIdResult.token

                preferences.saveInstanceTokenFcm("token", instanceIdResult.token)

                val userControl = UserControl(this, object : WSResult{
                    override fun uResponse(list: List<User>, type: String) {
                        Log.d("TAG", "salve")

                    }
                }, useful)

                userControl.saveFcm(user)

            }
        }
    }

    private fun getRealTimeLocation() {

        Log.d("TAG", "getRealTimeLocation: ")

        locationResult = object : MyLocation.LocationResult() {
            override fun getLocation(location: Location?) {

                if (location == null) {
                    statusCheck()
                    handler.removeCallbacks(runnable)
                } else {

                    _mapPlotDateLiveData.value = MapPlotData(
                        userPosition = LatLng(location.latitude, location.longitude),
                    )

                    if (isCameraLock) {
                        if (screenStageLiveData.value == MainScreenStage.OVERVIEW) {
                            mapPlotUpdated(mapPlotDateLiveData.value)
                            handler.postDelayed(runnable, 5000)
                        }
                    } else {
                        rideControl.findAllDriver()
                    }

                }
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            startActivity(Intent(this, PermissionLocalizationAct::class.java))

            return
        }

        statusCheck()

    }

    private fun statusCheck() {

        //colocar verificacao de internet aqui
        val lm = getSystemService(LOCATION_SERVICE) as LocationManager
        val enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!enabled) {
            DialogMessages(this).buildAlertMessageNoGps()
        } else {
            myLocation.getLocation(locationResult)
        }

    }



    fun notifyScreenStageChanged(newStage: MainScreenStage) {

        _screenStageLiveData.value = newStage

        Log.d("TAG", "notifyScreen: " + screenStageLiveData.value)
        Log.d("TAG", "mapPlotDateLiveData" + mapPlotDateLiveData.value)
        Log.d("TAG", "ridelivedata" + Gson().toJson(rideLiveData.value))

        configDrawer()

        when (newStage) {
            MainScreenStage.OVERVIEW -> {

                getRealTimeLocation()

            }

            MainScreenStage.ACCEPT_RIDE -> {

                handler.removeCallbacks(runnable)


                useful.showDefaultDialogView(supportFragmentManager, "accept")
            }

            MainScreenStage.WAITING_PICKUP -> {


                useful.showDefaultDialogView(supportFragmentManager, "waiting")

                _mapPlotDateLiveData.value = MapPlotData(
                    userPosition = LatLng(mapPlotDateLiveData.value!!.userPosition!!.latitude, mapPlotDateLiveData.value!!.userPosition!!.longitude),
                    originLatLng = LatLng(rideLiveData.value!!.originLatitude!!.toDouble(), rideLiveData.value!!.originLongitude!!.toDouble()),
                    destinationLatLng = LatLng(rideLiveData.value!!.destinationLatitude!!.toDouble(), rideLiveData.value!!.destinationLongitude!!.toDouble()),
//                    polyline = PolyUtil.decode(lastRideInfo.finalRoute.overViewPolyLine.polyLinePoints),
////                polyline = PolyUtil.decode("bbcvDhcrvHp@wA~AnBVNTHzNA`FE~DG|C?`AC?~@AjFAdCAXhB?nAJZDbAXPJb@ZNRLd@GfBGTd@~@hAzBnBdDpCdE`BlCp@" +
////                        "|@jBrCbBlCM`@z@f@~@f@b@ZbArAT^b@z@x@xBAHMx@Gf@aDjAe@Je@Pa@Tq@p@iCnEu@dBAR@XJTr@hAv@`AlEtEtAvOTrAt@fCXjADZAv@a@bB[p@i@f@w@b@wAh@sC" +
////                        "f@m@PQHWXc@b@aBzB{@hAeBrDSh@Mt@Cb@?d@JhAh@bFVnCFhB?pBMp@Wl@eAzAmA~AOZWjAa@bC]hCf@JjAHfAFr@@z@C"),
//                    vehicleAngle = null,
//                    vehicleLatLng = null,
//                    freeVehiclesLatLng = null
                )

                mapPlotUpdated(mapPlotDateLiveData.value)

            }

            MainScreenStage.ONGOING_RIDE -> {


                useful.showDefaultDialogView(supportFragmentManager, "ongoing")

                _mapPlotDateLiveData.value = MapPlotData(
                    originLatLng = LatLng(lastRideInfo.originLatitude!!.toDouble(), lastRideInfo.originLongitude!!.toDouble()),
                    destinationLatLng = LatLng(lastRideInfo.destinationLatitude!!.toDouble(), lastRideInfo.destinationLongitude!!.toDouble()),
                    polyline = PolyUtil.decode(lastRideInfo.finalRoute.overViewPolyLine.polyLinePoints),
//                polyline = PolyUtil.decode("bbcvDhcrvHp@wA~AnBVNTHzNA`FE~DG|C?`AC?~@AjFAdCAXhB?nAJZDbAXPJb@ZNRLd@GfBGTd@~@hAzBnBdDpCdE`BlCp@" +
//                        "|@jBrCbBlCM`@z@f@~@f@b@ZbArAT^b@z@x@xBAHMx@Gf@aDjAe@Je@Pa@Tq@p@iCnEu@dBAR@XJTr@hAv@`AlEtEtAvOTrAt@fCXjADZAv@a@bB[p@i@f@w@b@wAh@sC" +
//                        "f@m@PQHWXc@b@aBzB{@hAeBrDSh@Mt@Cb@?d@JhAh@bFVnCFhB?pBMp@Wl@eAzAmA~AOZWjAa@bC]hCf@JjAHfAFr@@z@C"),
                    vehicleAngle = null,
                    vehicleLatLng = null,
                    freeVehiclesLatLng = null
                )

                mapPlotUpdated(mapPlotDateLiveData.value)

            }

            MainScreenStage.FINISH_RIDE -> {

                handler.removeCallbacks(runnable)


            }

        }
    }


    private fun mapPlotUpdated(mapPlotData: MapPlotData?) {

        mMap?.clear()

        val boundsLatLng = ArrayList<LatLng>()

        mapPlotData?.userPosition?.let {

            mMap?.addMarker(
                MarkerOptions().icon(useful.bitmapDescriptorFromVector(R.drawable.ic_car))
                    .position(it)
                    .title("Localização atual")
                    .anchor(0.5f, 0.5f)
            )!!.showInfoWindow()
            boundsLatLng.add(it)

        }

        mapPlotData?.originLatLng?.let {
            mMap?.addMarker(
                MarkerOptions().icon(useful.bitmapDescriptorFromVector(R.drawable.ic_pin))
                    .position(it)
                    .title("Local de partida")
                    .anchor(0.5f, 0.5f)
            )!!.showInfoWindow()
            boundsLatLng.add(it)
        }

        mapPlotData?.destinationLatLng?.let {
            mMap?.addMarker(
                MarkerOptions().icon(useful.bitmapDescriptorFromVector(R.drawable.ic_destination))
                    .position(it)
                    .title("Destino final")
                    .anchor(0.5f, 0.70f)
            )!!.showInfoWindow()
            boundsLatLng.add(it)
        }


//        if (mapPlotData?.freeVehiclesLatLng != null) {
//
//            val vehiclesHere: List<LatLng>? = mapPlotData.freeVehiclesLatLng
//
//            if (vehiclesHere != null) {
//                if (key2 == "0") key = "1"
//                for (i in vehiclesHere.indices) {
//
//                    mapPlotData.freeVehiclesLatLng?.let {
//                        mMap?.addMarker(
//                            MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
//                                .position(it[i])
//                                .rotation(mapPlotData.vehicleAngle ?: 0f)
//                                .anchor(0.5f, 0.5f)
//                        )
//                        boundsLatLng.add(it[i])
//                    }
//
//                }
//            }
//        }

        mapPlotData?.vehicleLatLng?.let {
            mMap?.addMarker(
                MarkerOptions().icon(useful.bitmapDescriptorFromVector(R.drawable.ic_car))
                    .position(it)
                    .title("Motorista")
                    .rotation(mapPlotData.vehicleAngle ?: 0f)
                    .anchor(0.5f, 0.5f)
            )!!.showInfoWindow()
            boundsLatLng.add(it)
        }

        mapPlotData?.polyline?.let { lineList ->
            mMap?.addPolyline(
                PolylineOptions()
                    .addAll(lineList)
                    .color(ContextCompat.getColor(this, R.color.colorAccent))
            )
        }

        Log.d("TAG", "bounds: $boundsLatLng")

        centerMapOnPoints(boundsLatLng)
    }

    private fun centerMapOnPoints(points: List<LatLng?>) {

        Log.d("TAG", "points: $points")

        val notNullPoints = points.filterNotNull()

        if (notNullPoints.isEmpty())
            return

        if (screenStageLiveData.value == MainScreenStage.OVERVIEW) {
            if (isCameraLock) {
                if (notNullPoints.size == 1) {
                    mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(notNullPoints.first(), 18f))
                    isCameraLock = false
                    return
                }
            } else {
                return
            }
        }

        val builder = LatLngBounds.Builder()

        notNullPoints.forEach { position ->
            builder.include(LatLng(position.latitude, position.longitude))
        }

        val bounds = builder.build()
        val padding = resources.getDimension(R.dimen.map_pin_padding).toInt()
        mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))


    }

    private val myReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val bundle = intent.extras

            if (bundle != null) {

                val screenStage: MainScreenStage? = intent.getSerializableExtra("notifyScreen") as MainScreenStage?
                val rideId = intent.extras?.getString("rideId")

                if (rideId != null) {
                    _rideLiveData.value!!.rideId = rideId
                }

                notifyScreenStageChanged(screenStage!!)

            }
        }
    }

}