package br.com.app5m.appshelterdriver.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.fragment.auth.LoginFrag
import br.com.app5m.appshelterdriver.ui.fragment.auth.SignUpFrag
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.toolbar_yellow.*

class AuthContainerAct() : AppCompatActivity() {

    private lateinit var useful: Useful

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_container)
        setSupportActionBar(toolbar)

        useful = Useful(this)

        useful.setActionBar(this, supportActionBar!!, "", 0)

        if (intent.extras != null) {

            if (intent.extras!!.getInt("key") == 0) {
                useful.startFragment(SignUpFrag(), supportFragmentManager)
            } else {
                useful.startFragment(LoginFrag(), supportFragmentManager)
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