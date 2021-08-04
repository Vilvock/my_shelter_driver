package br.com.app5m.appshelterdriver

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import br.com.app5m.appshelterdriver.helper.Preferences
import br.com.app5m.appshelterdriver.ui.activity.HomeAct
import br.com.app5m.appshelterdriver.ui.activity.IntroContainerAct
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.fragment_get_started.*
import kotlinx.android.synthetic.main.loading.*


class MainAct : AppCompatActivity() {
    private var preferences: Preferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences = Preferences(this)

        start()

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
        configureInitialViews()

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
      /*  signUp_bt.setOnClickListener {
            startActivity(Intent(this, HomeAct::class.java))
            this.finishAffinity()
        }
        login_bt.setOnClickListener {
            startActivity(Intent(this, HomeAct::class.java))
            this.finishAffinity()
        }*/

    }

    fun configureInitialViews(){



    }

}