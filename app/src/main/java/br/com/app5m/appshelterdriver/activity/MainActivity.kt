package br.com.app5m.appshelterdriver.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.adapter.LastDestinationAdapter
import br.com.app5m.appshelterdriver.dialog.VeicleColorDialog
import br.com.app5m.appshelterdriver.fragment.auth.phoneValidation.PhoneValidation1Frag
import br.com.app5m.appshelterdriver.fragment.auth.phoneValidation.PhoneValidation2Frag
import br.com.app5m.appshelterdriver.fragment.home.ChoseAdestination
import br.com.app5m.appshelterdriver.fragment.signIn.SignUpFrag
import br.com.app5m.appshelterdriver.helper.MyUsefulKotlin


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


      MyUsefulKotlin().startFragment(ChoseAdestination(), supportFragmentManager)


    }
}