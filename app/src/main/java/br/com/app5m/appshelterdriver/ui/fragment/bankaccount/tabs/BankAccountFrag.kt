package br.com.app5m.appshelterdriver.ui.fragment.bankaccount.tabs

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
import kotlinx.android.synthetic.main.fragment_bank_account.*


class BankAccountFrag : Fragment() , RecyclerItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bank_account, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configInitialViews()

    }
    private fun configInitialViews(){
        val adapter: FragmentStateAdapter = ScreenSlidePagerAdapter(activity)
        mPagerBankAccount.adapter = adapter

/*
        mPager.setPageTransformer(ZoomOutPageTransformer())
*/

        TabLayoutMediator(tabViewBankAccount, mPagerBankAccount) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.text = "Pix"
                1 -> tab.text = "Conta Bancária"
            }
        }.attach()
    }

    private class ScreenSlidePagerAdapter(fa: FragmentActivity?) :
        FragmentStateAdapter(fa!!) {

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 ->  MyPixFrag()
                else -> MyBankFrag()
            }
        }



        override fun getItemCount(): Int {
            return 2
        }

    }
}