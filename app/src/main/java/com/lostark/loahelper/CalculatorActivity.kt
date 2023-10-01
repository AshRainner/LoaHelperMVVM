package com.lostark.loahelper

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.MotionEvent
import android.view.View
import android.view.View.OnKeyListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.GridLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.google.android.material.tabs.TabLayout
import com.lostark.customview.DailyGuardianView
import com.lostark.customview.DailyItemView
import com.lostark.database.table.GemItems
import com.lostark.database.table.Items
import com.lostark.loahelper.databinding.CalculatorMapFragmentBinding
import java.util.ArrayList
import kotlin.math.round


class CalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator_activity)

        setTabLayout()

    }

    fun setTabLayout(){

        val mapItemList = intent.parcelableArrayList<Items>("MapItemList")!!
        val lv1Gem = intent.paracelableExtra<GemItems>("Lv1Gem")!!
        val tabLayout = findViewById<TabLayout>(R.id.calculator_tab)
        tabLayout.addTab(tabLayout.newTab().setText("경매"))
        tabLayout.addTab(tabLayout.newTab().setText("지도"))

        val auctionFragment = CalculatorAuctionFragment()
        val mapFragment = CalculatorMapFragment(mapItemList,lv1Gem)
        val fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction()
            .replace(R.id.calculator_fragment_container, auctionFragment).commit()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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