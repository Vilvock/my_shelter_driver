package br.com.app5m.appshelterdriver.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.util.Useful
import kotlinx.android.synthetic.main.activity_sucess_info.*

class SucessInfoAct : AppCompatActivity() {

    private lateinit var useful: Useful

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sucess_info)

        useful = Useful(this)

        ok_bt.setOnClickListener {

            startActivity(Intent(this, HomeAct::class.java))
            finishAffinity()

        }

    }

}