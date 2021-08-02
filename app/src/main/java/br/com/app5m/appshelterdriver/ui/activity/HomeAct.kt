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
import br.com.app5m.appshelterdriver.ui.dialog.ChooseDestinationFragDialog
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*

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

                R.id.nav_myCards -> {
                    intent.putExtra("key", "cards")
                }
                R.id.nav_myRides -> {
                    intent.putExtra("key", "rides")
                }
                R.id.nav_favoriteDrivers -> {
                    intent.putExtra("key", "favoriteDrivers")
                }
                R.id.nav_myEmployees-> {
                    intent.putExtra("key", "myEmployees")
                }
                R.id.nav_employeesHistoryRides -> {
                    intent.putExtra("key", "historyEmployees")
                }
                else -> {
                    intent.putExtra("key", "registerEmployee")
                }
            }

            startActivity(intent)

            // Close the drawer
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

        beginRide_bt.setOnClickListener {

            useful.showDefaultDialogView(supportFragmentManager, ChooseDestinationFragDialog())

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

/*    override fun onResume() {
        super.onResume()

        val headerView: View = nav_view.getHeaderView(0)
        headerView.nameHeader_tv.text = Preferences(
            this
        ).getUserData()?.name
        headerView.emailHeader_tv.text = Preferences(
            this
        ).getUserData()?.email

    }*/

}