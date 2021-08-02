package br.com.app5m.appshelterdriver.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.util.Useful


class DrawerContainerAct : AppCompatActivity() {

    private lateinit var useful: Useful

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer_container)

        useful = Useful(this)
        useful.setActionBar(this, supportActionBar!!, "", 0)

        if (intent.extras != null) {
            when (intent!!.extras!!.getString("key")) {

                "cards" -> {

                }
                "rides" -> {

                }
                "favoriteDrivers" -> {

                }
                "myEmployees" -> {

                }
                "historyEmployees" -> {

                }
                "registerEmployee" -> {

                }

            }

        }



    }
}