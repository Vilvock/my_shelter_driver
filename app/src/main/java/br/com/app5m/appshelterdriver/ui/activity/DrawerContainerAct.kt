package br.com.app5m.appshelterdriver.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import br.com.app5m.appshelterdriver.MainAct
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.fragment.*
import br.com.app5m.appshelterdriver.ui.fragment.auth.UploadDocumentsFrag
import br.com.app5m.appshelterdriver.ui.fragment.drawer.user.UpdateVehicleFrag
import br.com.app5m.appshelterdriver.ui.fragment.drawer.user.UserProfileFrag
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.toolbar_blue.*


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
                "myTrips" -> {
//                    useful.startFragment(DriverRacesInfoContainerFrag(), supportFragmentManager)

                }

                "myPayments" -> {
//                    useful.startFragment(MyPayments(), supportFragmentManager)

                }
                "bank_account" -> {
//                    useful.startFragment(BankAccountFrag(), supportFragmentManager)

                }

                "logOut" -> {
                    this.finishAffinity()
                    val intent = Intent(this, MainAct::class.java)
                    startActivity(intent)


                }
                "upload_documents" -> {
                    useful.startFragment(UploadDocumentsFrag(), supportFragmentManager)

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