package br.com.app5m.appshelterdriver.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.fragment.MultiIntroFrag
import kotlinx.android.synthetic.main.activity_intro_container.*

class IntroContainerAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_container)

        if (intent.extras != null) {

            configInitialViews()

        }
    }

    private class SectionsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> MultiIntroFrag()
                1 -> MultiIntroFrag()
                2 -> MultiIntroFrag()
                else -> MultiIntroFrag()
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 4
        }
    }

    private fun configInitialViews(){
        val adapter = SectionsPagerAdapter(supportFragmentManager)

        pagerView.adapter = adapter

        introIndicator.setViewPager(pagerView)

        pagerView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                if (position == 3) {
                    next_intro_bt.setOnClickListener {

                        startActivity(Intent(this@IntroContainerAct,
                            AuthContainerAct::class.java).putExtra("key", intent.extras!!.getInt("key")))

                        finishAffinity()
                    }
                } else {
                    next_intro_bt.setOnClickListener {
                        pagerView.currentItem++
                    }
                }
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })

        loadClicks()

    }

    private fun loadClicks() {

        next_intro_bt.setOnClickListener {
            pagerView.currentItem++
        }

        skip_intro_bt.setOnClickListener {


            startActivity(Intent(this@IntroContainerAct,
                AuthContainerAct::class.java).putExtra("key", 0))

            finishAffinity()

        }

    }

}