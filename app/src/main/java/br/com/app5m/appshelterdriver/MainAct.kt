package br.com.app5m.appshelterdriver

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import br.com.app5m.appshelterdriver.models.LocationUser
import br.com.app5m.appshelterdriver.ui.activity.AuthContainerAct
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
import br.com.app5m.appshelterdriver.ui.activity.IntroContainerAct
import br.com.app5m.appshelterdriver.ui.activity.PermissionLocalizationAct
import br.com.app5m.appshelterdriver.util.DialogMessages
import br.com.app5m.appshelterdriver.util.MyLocation
import br.com.app5m.appshelterdriver.util.Preferences
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.fragment_get_started.*
import kotlinx.android.synthetic.main.loading.*


class MainAct : AppCompatActivity() {

    private lateinit var preferences: Preferences
    private lateinit var myLocation: MyLocation
    private lateinit var locationResult: MyLocation.LocationResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = Preferences(this)
        myLocation = MyLocation(this)

        preferences

        locationResult = object : MyLocation.LocationResult() {

            override fun getLocation(location: Location?) {

                if (location == null) {
                    preferences.clearUserLocation()
                    statusCheck()
                } else {
                    val locationUser = LocationUser()

                    locationUser.latitude = location.latitude.toString()
                    locationUser.longitude = location.longitude.toString()

                    preferences.setUserLocation(locationUser)

                    start2()
                }
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {


            startActivity(Intent(this@MainAct, PermissionLocalizationAct::class.java))


            return
        }

        statusCheck()
    }

    override fun onRestart() {
        super.onRestart()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            DialogMessages(this).booleanClick("Atenção!",
                "É necessário ter aceitos todas as permissões para obter maior precisão de sua localização!",
                object : DialogMessages.AnswerBoolean {
                    override fun setOnClickListener(boolean: Boolean) {
                        if (boolean) {
                            startActivity(Intent(this@MainAct, PermissionLocalizationAct::class.java))
                        } else {
                            finishAffinity()
                        }
                    }
                })
        } else {

            statusCheck()

        }
    }

    private fun start2() {

        if (preferences.getLogin()) {

            startActivity(Intent(this, HomeAct::class.java))
            finishAffinity()

        } else {

            loading.visibility = View.GONE
            authButtons_ll.visibility = View.VISIBLE

            if (preferences.getInt(
                    preferences.ENTERING_FIRST_TIME, 1) == 1) {

                signUp_bt.setOnClickListener {

                    startActivity(Intent(this, IntroContainerAct::class.java).putExtra("key", 0))

                }

                login_bt.setOnClickListener {

                    startActivity(Intent(this, IntroContainerAct::class.java).putExtra("key", 1))

                }


            } else {

                signUp_bt.setOnClickListener {

                    startActivity(Intent(this, AuthContainerAct::class.java).putExtra("key", 0))
                }

                login_bt.setOnClickListener {

                    startActivity(Intent(this, AuthContainerAct::class.java).putExtra("key", 1))

                }
            }
        }

    }


    private fun statusCheck() {

        val lm = getSystemService(LOCATION_SERVICE) as LocationManager
        val enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!enabled) {
            DialogMessages(this).buildAlertMessageNoGps()
        } else {
            myLocation.getLocation(locationResult)
        }

    }
}
