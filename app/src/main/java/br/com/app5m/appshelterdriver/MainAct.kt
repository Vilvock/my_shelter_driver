package br.com.app5m.appshelterdriver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import br.com.app5m.appshelterdriver.ui.activity.IntroContainerAct
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading.*


class MainAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start()

    }


    private fun start() {
        Handler().postDelayed({

            loading.visibility = View.GONE
            authButtons_ll.visibility = View.VISIBLE


            loadClicks()
        }, 3500)
    }

    private fun loadClicks() {

        //depois adicionar preferences para add intro so na primeira vez
        signUp_bt.setOnClickListener {

            startActivity(Intent(this, IntroContainerAct::class.java).putExtra("key", 0))
            finishAffinity()

        }

        login_bt.setOnClickListener {

            startActivity(Intent(this, IntroContainerAct::class.java).putExtra("key", 1))
            finishAffinity()

        }

    }

}