package com.lostark.loahelper

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ScrollView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.lostark.adapter.EventViewPagerAdapter
import com.lostark.adapter.RaidViewPagerAdapter
import com.lostark.customview.HomeButtonView
import com.lostark.customview.RaidButtonView

class RaidDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.raid_detail_activity)
        viewPagerSet()
    }

    fun viewPagerSet(){
        //val raidImagePager = findViewById<ViewPager2>(R.id.raid_detail_viewPager)
        val raidName = intent.getStringExtra("RaidName")
        val tabLayout = findViewById<TabLayout>(R.id.raid_detail_tab)
        val scrollView = findViewById<ScrollView>(R.id.raid_detail_scroll_view)
        var raidDrawableList = mutableListOf<Int>()

        val raidImage = findViewById<ImageView>(R.id.raid_detail_image)
        when (raidName){
            "valtan" -> {
                raidDrawableList.add(R.drawable.valtan_1)
                raidDrawableList.add(R.drawable.valtan_2)
            }
            "biackiss"->{
                raidDrawableList.add(R.drawable.biackiss_1)
                raidDrawableList.add(R.drawable.biackiss_2)
                raidDrawableList.add(R.drawable.biackiss_3)
            }
            "koukusaton"->{
                raidDrawableList.add(R.drawable.koukusaton_1)
                raidDrawableList.add(R.drawable.koukusaton_2)
                raidDrawableList.add(R.drawable.koukusaton_3)
            }
        }
        raidImage.setImageResource(raidDrawableList.get(0))

        raidDrawableList.forEachIndexed { index, i ->
            tabLayout.addTab(tabLayout.newTab().setText((index+1).toString()+"관문"))
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                raidImage.setImageResource(raidDrawableList.get(tab?.position!!))
                scrollView.fullScroll(ScrollView.FOCUS_UP)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        /*val raidViewPagerAdapter = RaidViewPagerAdapter(raidDrawableList,raidImagePager)
        raidImagePager.adapter = raidViewPagerAdapter*/
    }

}