package br.com.app5m.appshelterdriver.ui.fragment.drawer.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.ui.fragment.drawer.other.UserHistoricRidesFrag
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import kotlinx.android.synthetic.main.activity_intro_container.*
import kotlinx.android.synthetic.main.activity_intro_container.pagerView
import kotlinx.android.synthetic.main.fragment_user_rides_info_container.*

/**
 * A simple [Fragment] subclass.
 */
class UserRidesInfoContainerFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_rides_info_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configInitialViews()

    }

    private class SectionsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> UserHistoricRidesFrag()
                1 -> UserHistoricRidesFrag()
                else -> UserHistoricRidesFrag()
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }

    private fun configInitialViews(){

        val adapter = SectionsPagerAdapter(requireActivity().supportFragmentManager)

        pagerView.adapter = adapter

//        tabView.setupWithViewPager(pagerView)

        pagerView.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabView))


        loadClicks()

    }

    private fun loadClicks() {



    }


}