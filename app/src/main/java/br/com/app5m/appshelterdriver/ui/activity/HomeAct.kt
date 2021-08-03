package br.com.app5m.appshelterdriver.ui.activity

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBarDrawerToggle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.dialog.AceptRaceFragDialog
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.baseboard_acept_race.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*

class HomeAct : AppCompatActivity() {


    private lateinit var useful: Useful

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        useful = Useful(this)

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
                drawer_layout.closeDrawer(GravityCompat.START);
            } else {
                drawer_layout.openDrawer(GravityCompat.START);
            }


        }


        nav_view.itemIconTintList = null
        nav_view.setNavigationItemSelectedListener {

            val intent = Intent(this, DrawerContainerAct::class.java)

            when (it.itemId) {

                R.id.nav_changeVeicle -> {
                    intent.putExtra("key", "change_vehicle")
                }
                R.id.nav_myTrips -> {
                    intent.putExtra("key", "myTrips")
                }
                R.id.nav_travelHistory -> {
                    intent.putExtra("key", "travel_history")
                }
                R.id.nav_myPayments-> {
                    intent.putExtra("key", "myPayments")
                }
                R.id.nav_bankAccount -> {
                    intent.putExtra("key", "bank_account")
                }
                else -> {
                    intent.putExtra("key", "logOut")
                }
            }

            startActivity(intent)

            // Close the drawer
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }


        switchCustom.setOnClickListener{
            if(switchCustom.isChecked){
                useful.showDefaultDialogView(supportFragmentManager, AceptRaceFragDialog())
            }
            else{

            }
        }

        //alterar compasso de posição depois
        map.view?.let { mapView ->
            mapView.findViewWithTag<View>("GoogleMapMyLocationButton").parent?.let { parent ->
                val vg: ViewGroup = parent as ViewGroup
                vg.post {
                    val mapCompass: View = parent.getChildAt(4)
                    val rlp = RelativeLayout.LayoutParams(mapCompass.height, mapCompass.height)
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0)
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)

                    val bottomMargin = (40 * Resources.getSystem().displayMetrics.density).toInt()
                    val leftMargin = (5 * Resources.getSystem().displayMetrics.density).toInt()
                    rlp.setMargins(leftMargin, 0, 0, bottomMargin)
                    mapCompass.layoutParams = rlp
                }
            }
        }

    }
    override fun onResume() {
        super.onResume()

        val headerView: View = nav_view.getHeaderView(0)
        headerView.nameUser_tv.text = "Android Lorem"
        headerView.emailUser_tv.text = "android@aandroid.com"

        headerView.profile_ib.setOnClickListener {


            val intent = Intent(this, DrawerContainerAct::class.java)
            intent.putExtra("key", "profile")
            startActivity(intent)

        }

    }



}