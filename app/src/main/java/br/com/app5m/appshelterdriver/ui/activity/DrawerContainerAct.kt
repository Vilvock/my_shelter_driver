package br.com.app5m.appshelterdriver.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil.setContentView
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.fragment.UserProfileFrag
import br.com.app5m.appshelterdriver.ui.fragment.UserRidesInfoContainerFrag
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.activity_drawer_container.*


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
                "cards" -> {
                    useful.startFragment(UserProfileFrag(), supportFragmentManager)

                }
                "rides" -> {

                    useful.startFragment(UserRidesInfoContainerFrag(), supportFragmentManager)
                }
                "favoriteDrivers" -> {
                    useful.startFragment(UserRidesInfoContainerFrag(), supportFragmentManager)

                }
                "myEmployees" -> {
                    useful.startFragment(UserRidesInfoContainerFrag(), supportFragmentManager)

                }
                "historyEmployees" -> {
                    useful.startFragment(UserRidesInfoContainerFrag(), supportFragmentManager)

                }
                "registerEmployee" -> {
                    useful.startFragment(UserRidesInfoContainerFrag(), supportFragmentManager)

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