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
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.com.app5m.appshelterdriver.MainAct
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.controller.DocumentControl
import br.com.app5m.appshelterdriver.controller.RideControl
import br.com.app5m.appshelterdriver.controller.UserControl
import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
import br.com.app5m.appshelterdriver.controller.webservice.WSResult
import br.com.app5m.appshelterdriver.models.Document
import br.com.app5m.appshelterdriver.models.Ride
import br.com.app5m.appshelterdriver.models.User
import br.com.app5m.appshelterdriver.ui.MapBottomPaddingDelegate
import br.com.app5m.appshelterdriver.util.*
import br.com.app5m.appshelterdriver.util.visual.SingleToast
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.gson.Gson
import com.google.maps.android.PolyUtil
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_send_document.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.bottom_im_available.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.default_bottom_sheet_dialog_container.*
import kotlinx.android.synthetic.main.loading.*
import kotlinx.android.synthetic.main.nav_header_home.view.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import kotlinx.android.synthetic.main.top_credit_available.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeAct : AppCompatActivity(), OnMapReadyCallback, WSResult, MapBottomPaddingDelegate {

    private lateinit var useful: Useful
    private lateinit var preferences: Preferences
    private lateinit var rideControl: RideControl
    private lateinit var userControl: UserControl
    private lateinit var documentControl: DocumentControl

    private lateinit var myLocation: MyLocation
    private lateinit var locationResult: MyLocation.LocationResult

    enum class MainScreenStage {
        RELOAD_OVERVIEW_STATEMENT,
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

    private lateinit var mapFragment: SupportMapFragment

    private lateinit var lastRideInfo: Ride

    private var mMap: GoogleMap? = null
    private var driverMarker: Marker? = null

    private var userLatLng: LatLng? = null
    private var userBearing = 0F


    private val interpolator = LinearInterpolator()
    private var animatingJob: Job? = null
    private val vehicleInterpolator = LinearFixed()

    var isCameraLock: Boolean = true
    lateinit var notificationRideId: String

    private val handler = Handler()
    private var runnable = Runnable { getRealTimeLocation()}

    private val DRIVER_POSITION_TRACKING_RATE = 1000L
    private val DELAY_HANDLER = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        useful = Useful(this)
        preferences = Preferences(this)
        rideControl = RideControl(this, this, useful)
        userControl = UserControl(this, this, useful)
        documentControl = DocumentControl(this, this, useful)
        myLocation = MyLocation(this)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
            IntentFilter("Notification")
        )

        saveFcm()

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
            if (screenStageLiveData.value == MainScreenStage.RELOAD_OVERVIEW_STATEMENT) {
                isCameraLock = true
                mapFragment.getMapAsync(this)
            }
        }
    }

    override fun onStop() {
        handler.removeCallbacks(runnable)
        super.onStop()
    }

    override fun onPause() {
        handler.removeCallbacks(runnable)
        super.onPause()
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
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
            if (intent.extras != null) {

                val screenStage: MainScreenStage? = intent.getSerializableExtra("notifyScreen") as MainScreenStage?
                val rideId = intent.extras?.getString("rideId")

                if (screenStage == MainScreenStage.ACCEPT_RIDE) {
                    if (rideId != null) {

                        notificationRideId = rideId

                    }
                }

                notifyScreenStageChanged(screenStage!!)

            } else {

                notifyScreenStageChanged(MainScreenStage.RELOAD_OVERVIEW_STATEMENT)
            }

    }

    override fun dResponse(list: List<Document>, type: String) {

        val docInfo1 = list[0]
        val docInfo2 = list[1]

        if (type == "listDoc") {

            if (docInfo1.status == "Aprovado" && docInfo2.status == "Aprovado") {

                bottom_im_available.visibility = View.VISIBLE
                bottom_documentation_off.visibility = View.GONE

                val driverLocation = User()

                driverLocation.latitude = userLatLng!!.latitude.toString()
                driverLocation.longitude = userLatLng!!.longitude.toString()

                userControl.updateLocation(driverLocation)

            } else {

                bottom_im_available.visibility = View.GONE
                bottom_documentation_off.visibility = View.VISIBLE

                bottom_documentation_off.setOnClickListener {
                    startActivity(Intent(this, SendDocumentAct::class.java))

                    SingleToast.INSTANCE.show(this,
                        "É necessário ter todos os seus documentos aprovados para poder realizar viagens pela Shelter.", Toast.LENGTH_LONG)

                }
            }
        }

    }


    override fun uResponse(list: List<User>, type: String) {

        val userInfo = list[0]

        if (type == "updateStatus") {
            if (userInfo.status != "01") {

                SingleToast.INSTANCE.show(this, "Não foi possível atualizar seu status, tente novamente mais tarde!",
                    Toast.LENGTH_LONG)

            } else {

                imAvailable_sw.isEnabled = true

            }
        } else if (type == "currentLocationStatus"){

            imAvailable_sw.isChecked = userInfo.on == "1"

            imAvailable_sw.setOnCheckedChangeListener { buttonView, isChecked -> //commit prefs on change
//
//                imAvailable_sw.isEnabled = false

                val driverStatus = User()

                driverStatus.latitude = userLatLng!!.latitude.toString()
                driverStatus.longitude = userLatLng!!.longitude.toString()

                if (isChecked) {
                    //online
                    driverStatus.online = "1"
                } else {
                    //offline
                    driverStatus.online = "2"
                }

                userControl.updateStatusOnline(driverStatus)
            }

            userControl.listCredit()

        } else if (type == "listCredit"){

            if (screenStageLiveData.value == MainScreenStage.RELOAD_OVERVIEW_STATEMENT) {
                top_credit_available.visibility = View.VISIBLE

                currentCredit_tv.text = userInfo.credit
            } else {
                top_credit_available.visibility = View.GONE
            }

        } else {

            userControl.findCurrentLocationStatus()

        }

    }

    override fun rResponse(list: List<Ride>, type: String) {

        lastRideInfo = list[0]

        _rideLiveData.value = lastRideInfo

        if (type == "findAll") {
            if (lastRideInfo.rows != "0") {

                when(lastRideInfo.rideStatus) {
                    "Aceita" -> {

                        //primeira vez q entrar vai overview  e so vai aparecer uma vez a dialog
                        if (screenStageLiveData.value != MainScreenStage.WAITING_PICKUP) {
                            useful.showRideFlowFrag(supportFragmentManager, "waiting")
                        }


                        notifyScreenStageChanged(MainScreenStage.WAITING_PICKUP)

                    }

                    "Em andamento" -> {

                        val findRideProcess = Ride()

                        findRideProcess.latitude = mapPlotDateLiveData.value!!.userPosition!!.latitude.toString()
                        findRideProcess.longitude = mapPlotDateLiveData.value!!.userPosition!!.longitude.toString()
                        findRideProcess.carType = rideLiveData.value!!.carType

                        rideControl.findProcess(findRideProcess)

                    }

                    "Finalizada" -> {

                        if (screenStageLiveData.value == MainScreenStage.ACCEPT_RIDE) {
                            notifyScreenStageChanged(MainScreenStage.ACCEPT_RIDE)
                        } else {
                            notifyScreenStageChanged(MainScreenStage.RELOAD_OVERVIEW_STATEMENT)
                        }
                    }

                    else -> {
                        //cancelada

                        if (screenStageLiveData.value == MainScreenStage.ACCEPT_RIDE) {
                            notifyScreenStageChanged(MainScreenStage.ACCEPT_RIDE)
                        } else {
                            notifyScreenStageChanged(MainScreenStage.RELOAD_OVERVIEW_STATEMENT)
                        }
                    }
                }

            } else {
                notifyScreenStageChanged(MainScreenStage.RELOAD_OVERVIEW_STATEMENT)
            }

            //find andamento
        } else {
            if (lastRideInfo.rows != "0") {

                if (screenStageLiveData.value != MainScreenStage.ONGOING_RIDE) {
                    useful.showRideFlowFrag(supportFragmentManager, "ongoing")
                }

                notifyScreenStageChanged(MainScreenStage.ONGOING_RIDE)
            }
        }

    }


    override fun setMapVerticalPadding(bottomPx: Int, topPx: Int?) {
        val tPadding = topPx ?: toolbar?.height ?: 0
        val hPadding = resources.getDimension(R.dimen.map_horizontal_padding).toInt()
        mMap?.setPadding(hPadding, tPadding, hPadding, bottomPx)
    }

    private fun resetVerticalPaddingScreen() {
        (this as? MapBottomPaddingDelegate)?.setMapVerticalPadding(0)
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

                R.id.nav_changeVehicle -> {
                    intent.putExtra("key", "change_vehicle")
                }
                R.id.nav_myRides -> {
                    intent.putExtra("key", "rides")
                }

                R.id.nav_myPayments-> {
                    intent.putExtra("key", "myPayments")
                }

                R.id.nav_account -> {
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

        Glide.with(this)
            .load(WSConstants.AVATAR_USER + preferences.getUserData()!!.avatar).into(headerView.userAvatar_iv)

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


                    if (location.latitude != mapPlotDateLiveData.value?.userPosition?.latitude
                        && location.longitude != mapPlotDateLiveData.value?.userPosition?.longitude) {
                        isCameraLock = true
                    }

                    userLatLng = LatLng (location.latitude, location.longitude)
                    userBearing = location.bearing


                    _mapPlotDateLiveData.value = MapPlotData(
                        userPosition = LatLng(userLatLng!!.latitude, userLatLng!!.longitude),
                        vehicleAngle = userBearing
                    )

                    if (screenStageLiveData.value == MainScreenStage.RELOAD_OVERVIEW_STATEMENT) {

                        mapPlotUpdated(mapPlotDateLiveData.value)

                        documentControl.listDocDriver()

                    }

                }

                rideControl.findAllDriver()
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

        handler.removeCallbacks(runnable)

        _screenStageLiveData.value = newStage
        configDrawer()

        Log.d("TAG", "notifyScreen: " + screenStageLiveData.value)
        Log.d("TAG", "mapPlotDateLiveData" + mapPlotDateLiveData.value)
        Log.d("TAG", "ridelivedata" + Gson().toJson(rideLiveData.value))

        when (newStage) {
            MainScreenStage.RELOAD_OVERVIEW_STATEMENT -> {
                useful.dismissRideFlowFrag(supportFragmentManager)

                resetVerticalPaddingScreen()

            }

            MainScreenStage.ACCEPT_RIDE -> {

                useful.showRideFlowFrag(supportFragmentManager, "accept")

            }

            MainScreenStage.WAITING_PICKUP  -> {

                //mapupdate aqui quando poly parar de ser null

                if (rideLiveData.value != null) {
                    _mapPlotDateLiveData.value = MapPlotData(
                        userPosition = LatLng(userLatLng!!.latitude, userLatLng!!.longitude),
                        vehicleAngle = userBearing,
                        originLatLng = LatLng(rideLiveData.value!!.originLatitude!!.toDouble(), rideLiveData.value!!.originLongitude!!.toDouble())/*,
                        destinationLatLng = LatLng(rideLiveData.value!!.destinationLatitude!!.toDouble(), rideLiveData.value!!.destinationLongitude!!.toDouble())*/)

                    mapPlotUpdated(mapPlotDateLiveData.value)
                }

            }
            MainScreenStage.ONGOING_RIDE -> {

                if (rideLiveData.value != null) {

                    var polyLineFormatted: List<LatLng>? = null

                    if (rideLiveData.value?.finalRoute?.overViewPolyLine?.polyLinePoints != null) {
                       polyLineFormatted = PolyUtil.decode(rideLiveData.value?.finalRoute?.overViewPolyLine?.polyLinePoints)
                    }

                    _mapPlotDateLiveData.value = MapPlotData(
                        userPosition = LatLng(userLatLng!!.latitude, userLatLng!!.longitude),
                        vehicleAngle = userBearing,
//                        originLatLng = LatLng(rideLiveData.value!!.originLatitude!!.toDouble(), rideLiveData.value!!.originLongitude!!.toDouble()),
                        destinationLatLng = LatLng(rideLiveData.value!!.destinationLatitude!!.toDouble(), rideLiveData.value!!.destinationLongitude!!.toDouble()),
                        polyline = polyLineFormatted)

                    mapPlotUpdated(mapPlotDateLiveData.value)
                }

            }
            MainScreenStage.FINISH_RIDE -> {


                useful.showRideFlowFrag(supportFragmentManager, "finish")


            }

        }

        if (screenStageLiveData.value != MainScreenStage.ACCEPT_RIDE
            && screenStageLiveData.value != MainScreenStage.FINISH_RIDE) {
            handler.postDelayed(runnable, DELAY_HANDLER)
        }

    }


    private fun mapPlotUpdated(mapPlotData: MapPlotData?) {

        mMap?.clear()

        val boundsLatLng = ArrayList<LatLng>()

        mapPlotData?.userPosition?.let {
            run {
                driverMarker = mMap?.addMarker(
                    MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin_driver))
                        .rotation(mapPlotData.vehicleAngle ?: 0f)
                        .position(it)
                        .title("Localização atual")
                        .anchor(0.5f, 0.5f)
                )

//                driverMarker!!.showInfoWindow()

                animateVehiclePosition(it, mapPlotData.vehicleAngle ?: 0f)
                boundsLatLng.add(it)
            }
        }


        mapPlotData?.originLatLng?.let {
            mMap?.addMarker(
                MarkerOptions().icon(useful.bitmapDescriptorFromVector(R.drawable.ic_pin))
                    .position(it)
                    .title("Local de partida")
                    .anchor(0.5f, 0.5f)
            )
            boundsLatLng.add(it)
        }

        mapPlotData?.destinationLatLng?.let {
            mMap?.addMarker(
                MarkerOptions().icon(useful.bitmapDescriptorFromVector(R.drawable.ic_destination))
                    .position(it)
                    .title("Destino final")
                    .anchor(0.5f, 0.70f)
            )
            boundsLatLng.add(it)
        }

        mapPlotData?.polyline?.let { lineList ->
            mMap?.addPolyline(
                PolylineOptions()
                    .addAll(lineList)
                    .color(ContextCompat.getColor(this, R.color.colorPrimary))
            )
        }

        Log.d("TAG", "bounds: $boundsLatLng")

        centerMapOnPoints(boundsLatLng)
    }

    private fun animateVehiclePosition(newPosition: LatLng, newAngle: Float) {

        Log.v("Angle", newAngle.toString())

        animatingJob?.cancel()
        animatingJob = lifecycleScope.launch {

            var elapsed: Long
            var t = 0f
            var v: Float
            val start = SystemClock.uptimeMillis()

            val startPosition = driverMarker?.position
            val startRotation = driverMarker?.rotation

            while (t < 1 && driverMarker != null) {
                // Calculate progress using interpolator
                elapsed = SystemClock.uptimeMillis() - start
                t = elapsed / DRIVER_POSITION_TRACKING_RATE.toFloat()
                v = interpolator.getInterpolation(t)

                driverMarker?.position =
                    vehicleInterpolator.interpolate(v, startPosition!!, newPosition)
                driverMarker?.rotation =
                    vehicleInterpolator.interpolateAngle(v, startRotation!!, newAngle)

                delay(16)
            }
        }
    }


    private fun centerMapOnPoints(points: List<LatLng?>) {

        Log.d("TAG", "points: $points")

        val notNullPoints = points.filterNotNull()

        if (notNullPoints.isEmpty())
            return

        if (screenStageLiveData.value == MainScreenStage.RELOAD_OVERVIEW_STATEMENT) {
            if (isCameraLock) {
                if (notNullPoints.size == 1) {
                    mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(notNullPoints.first(), 18f))
                    isCameraLock = false
                }
            }
        } else {

            val builder = LatLngBounds.Builder()

            notNullPoints.forEach { position ->
                builder.include(LatLng(position.latitude, position.longitude))
            }

            val bounds = builder.build()
            val padding = resources.getDimension(R.dimen.map_pin_padding).toInt()
            mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))

        }

    }

    private val myReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            handler.removeCallbacks(runnable)

            if (intent.extras != null) {

                val screenStage: MainScreenStage? = intent.getSerializableExtra("notifyScreen") as MainScreenStage?
                val rideId = intent.getStringExtra("rideId")

                Log.d("TAG", "onReceive: " + rideId)

                if (screenStage == MainScreenStage.ACCEPT_RIDE) {
                    if (rideId != null) {

                        notificationRideId = rideId

                    }
                }

                notifyScreenStageChanged(screenStage!!)

            }
        }
    }

}