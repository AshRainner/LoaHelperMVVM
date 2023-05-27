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
import androidx.viewpager2.widget.ViewPager2
import com.lostark.adapter.EventViewPagerAdapter
import com.lostark.adapter.RaidViewPagerAdapter
import com.lostark.customview.HomeButtonView
import com.lostark.customview.RaidButtonView
import com.lostark.loahelper.databinding.ActivityMainBinding

class RaidDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.raid_detail_activity)
        viewPagerSet()
    }

    fun viewPagerSet(){
        val raidImagePager = findViewById<ViewPager2>(R.id.raid_detail_viewPager)
        val raidName = intent.getStringExtra("RaidName")
        var raidDrawableList = mutableListOf<Int>()
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
        val raidViewPagerAdapter = RaidViewPagerAdapter(raidDrawableList,raidImagePager)
        raidImagePager.adapter = raidViewPagerAdapter
    }

}