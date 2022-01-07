package br.com.app5m.appshelterdriver.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.fragment.drawer.other.UserAccountFrag
import br.com.app5m.appshelterdriver.ui.fragment.drawer.other.UserPaymentsFrag
import br.com.app5m.appshelterdriver.ui.fragment.drawer.user.UpdateVehicleFrag
import br.com.app5m.appshelterdriver.ui.fragment.drawer.user.UserProfileFrag
import br.com.app5m.appshelterdriver.ui.fragment.drawer.user.UserRidesInfoContainerFrag
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.toolbar_yellow.*


class DrawerContainerAct : AppCompatActivity() {

    private lateinit var useful: Useful

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_container)
        setSupportActionBar(toolbar)

        useful = Useful(this)
        useful.setActionBar(this, supportActionBar!!, "", 0)

        if (intent.extras != null) {
            when (intent!!.extras!!.getString("key")) {

                "profile" -> {
                    useful.startFragment(UserProfileFrag(), supportFragmentManager)
                }
                "change_vehicle" -> {
                    useful.startFragment(UpdateVehicleFrag(), supportFragmentManager)

                }
                "rides" -> {
                    useful.startFragment(UserRidesInfoContainerFrag(), supportFragmentManager)

                }

                "myPayments" -> {
                    useful.startFragment(UserPaymentsFrag(), supportFragmentManager)

                }
                "bank_account" -> {
                    useful.startFragment(UserAccountFrag(), supportFragmentManager)

                }


            }

        }



    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}