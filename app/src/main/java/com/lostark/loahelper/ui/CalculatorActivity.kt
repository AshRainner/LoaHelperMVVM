package com.lostark.loahelper.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.lostark.loahelper.R
import com.lostark.loahelper.database.table.GemItems
import com.lostark.loahelper.database.table.Items
import com.lostark.loahelper.databinding.CalculatorActivityBinding
import com.lostark.loahelper.viewmodel.DataViewModel


class CalculatorActivity : BaseActivity<CalculatorActivityBinding>() {
    private val dataViewModel: DataViewModel by provideViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(CalculatorActivityBinding::inflate)
        setTabLayout()
    }

    fun setTabLayout(){

        val mapItemList = dataViewModel.getMapItemList()
        val lv1Gem = dataViewModel.getLv1GemData()
        binding.run {
            calculatorTab.addTab(calculatorTab.newTab().setText("경매"))
            calculatorTab.addTab(calculatorTab.newTab().setText("지도"))

            val auctionFragment = CalculatorAuctionFragment()
            val mapFragment = CalculatorMapFragment(mapItemList, lv1Gem)
            val fragmentManager = supportFragmentManager

            fragmentManager.beginTransaction()
                .replace(R.id.calculator_fragment_container, auctionFragment).commit()

            calculatorTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val selectedFragment = when (tab?.position) {
                        0 -> auctionFragment
                        1 -> mapFragment
                        else -> null
                    }

                    selectedFragment?.let {
                        fragmentManager.beginTransaction()
                            .replace(R.id.calculator_fragment_container, it)
                            .commit()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }
    inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? =
        when {
            Build.VERSION.SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
        }

    inline fun <reified T : Parcelable> Intent.paracelableExtra(key: String): T? =
        when {
            Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableExtra(key)
        }

}