package br.com.app5m.appshelterdriver.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.fragment.signin.LoginFrag
import br.com.app5m.appshelterdriver.ui.fragment.signin.SignUpFrag
import br.com.app5m.appshelterdriver.util.Useful


class AuthContainerAct() : AppCompatActivity() {

    private lateinit var useful: Useful

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_container)

        useful = Useful(this)
        if (intent.extras != null) {

            if (intent.extras!!.getInt("key") == 0) {
                useful.startFragment(SignUpFrag(), supportFragmentManager)
            } else {
                useful.startFragment(LoginFrag(), supportFragmentManager)
            }

        }

    }
}