package br.com.app5m.appshelterdriver.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.fragment.home.AddCreditCardFrag
import br.com.app5m.appshelterdriver.fragment.home.MyFavoritesFrag
import br.com.app5m.appshelterdriver.fragment.home.MyPromotionsFrag
import br.com.app5m.appshelterdriver.fragment.home.PaymentMethodsFrag
import br.com.app5m.appshelterdriver.helper.MyUsefulKotlin


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


      MyUsefulKotlin().startFragment(MyPromotionsFrag(), supportFragmentManager)


    }
}