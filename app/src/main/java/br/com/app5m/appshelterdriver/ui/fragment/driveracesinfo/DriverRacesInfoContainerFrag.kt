package br.com.app5m.appshelterdriver.ui.fragment.driveracesinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.app5m.appshelterdriver.R
import br.com.app5m.appshelterdriver.helper.RecyclerItemClickListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_user_rides_info_container.*

/**
 * A simple [Fragment] subclass.
 */
class DriverRacesInfoContainerFrag : Fragment() , RecyclerItemClickListener {

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
    private fun configInitialViews(){
        val adapter: FragmentStateAdapter = ScreenSlidePagerAdapter(activity)
        mPager.adapter = adapter

/*
        mPager.setPageTransformer(ZoomOutPageTransformer())
*/

        TabLayoutMediator(tabView, mPager) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.text = "Agendadas"
                1 -> tab.text = "Completas"
                2 -> tab.text = "Canceladas"
            }
        }.attach()
    }

    private class ScreenSlidePagerAdapter(fa: FragmentActivity?) :
        FragmentStateAdapter(fa!!) {

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 ->  TabScheduledFrag()
                1 ->  TabScheduledFrag()
                else -> TabScheduledFrag()
            }
        }



        override fun getItemCount(): Int {
            return 3
        }

    }
}