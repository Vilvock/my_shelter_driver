package br.com.app5m.appshelterdriver.ui.activity

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBarDrawerToggle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import br.com.app5m.appshelterdriver.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.fragment_home_map.*

class HomeAct : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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

//            val intent = Intent(this, DrawserAct::class.java)
//
//            when (it.itemId) {
//
//                R.id.nav_profile -> {
//                    intent.putExtra("value", "1")
//                    startActivity(intent)
//                }
//
//                R.id.nav_doubts -> {
//                    intent.putExtra("value", "2")
//                    startActivity(intent)
//                }
//                R.id.nav_contact -> {
//                    intent.putExtra("value", "3")
//                    startActivity(intent)
//                }
//
//                R.id.nav_exit -> {
//                    Message(this).exit(this)
//                }
//            }


            // Close the drawer
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

        //alterar compasso de posição
   /*     map.view?.let { mapView->
            mapView.findViewWithTag<View>("GoogleMapMyLocationButton").parent?.let { parent->
                val vg: ViewGroup = parent as ViewGroup
                vg.post {
                    val mapCompass : View = parent.getChildAt(4)
                    val rlp = RelativeLayout.LayoutParams(mapCompass.height, mapCompass.height)
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP,0)
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,0)
                    rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)

                    val bottomMargin = (40 * Resources.getSystem().displayMetrics.density).toInt()
                    val leftMargin = (5 * Resources.getSystem().displayMetrics.density).toInt()
                    rlp.setMargins(leftMargin,0,0,bottomMargin)
                    mapCompass.layoutParams = rlp
                }
            }
        }*/

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