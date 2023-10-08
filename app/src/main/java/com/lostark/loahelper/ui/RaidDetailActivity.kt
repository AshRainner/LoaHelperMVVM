package com.lostark.loahelper.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchActivityBinding
import com.lostark.loahelper.databinding.RaidDetailActivityBinding
import com.lostark.loahelper.viewmodel.DataViewModel

class RaidDetailActivity : BaseActivity<RaidDetailActivityBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(RaidDetailActivityBinding::inflate)
        viewPagerSet()
    }

    fun viewPagerSet(){
        val raidName = intent.getStringExtra("RaidName")
        var raidDrawableList = mutableListOf<Int>()
        var plus=1

        val raidImage = findViewById<ImageView>(R.id.raid_detail_image)
        binding.run {
            when (raidName){
                "valtan" -> {
                    raidDrawableList.add(R.drawable.valtan_1)
                    raidDrawableList.add(R.drawable.valtan_2)
                }
                "biackiss"->{
                    raidDrawableList.add(R.drawable.biackiss_2)
                    raidDrawableList.add(R.drawable.biackiss_3)
                }
                "koukusaton"->{
                    raidDrawableList.add(R.drawable.koukusaton_1)
                    raidDrawableList.add(R.drawable.koukusaton_2)
                    raidDrawableList.add(R.drawable.koukusaton_3)
                }
                "abrelshudNormal"->{
                    raidDrawableList.add(R.drawable.abrelshud_n_1)
                    raidDrawableList.add(R.drawable.abrelshud_n_2)
                    raidDrawableList.add(R.drawable.abrelshud_n_3)
                    raidDrawableList.add(R.drawable.abrelshud_n_4)
                }
                "abrelshudHard"->{
                    raidDrawableList.add(R.drawable.abrelshud_h_1)
                    raidDrawableList.add(R.drawable.abrelshud_h_2)
                    raidDrawableList.add(R.drawable.abrelshud_h_3)
                    raidDrawableList.add(R.drawable.abrelshud_h_4)
                }
                "illiakanNormal"->{
                    raidDrawableList.add(R.drawable.illiakan_n_1)
                    raidDrawableList.add(R.drawable.illiakan_n_2)
                    raidDrawableList.add(R.drawable.illiakan_n_3)
                }
                "illiakanHard"->{
                    raidDrawableList.add(R.drawable.illiakan_h_1)
                    raidDrawableList.add(R.drawable.illiakan_h_2)
                    raidDrawableList.add(R.drawable.illiakan_h_3)
                    raidDrawableList.add(R.drawable.illiakan_h_4)
                }
                "kayangel"->{
                    raidDrawableList.add(R.drawable.kayangel_1)
                    raidDrawableList.add(R.drawable.kayangel_2)
                    plus=2
                }
                "ivorytower"->{
                    raidDrawableList.add(R.drawable.ivorytower_1)
                    raidDrawableList.add(R.drawable.ivorytower_2)
                    raidDrawableList.add(R.drawable.ivorytower_3)
                    raidDrawableList.add(R.drawable.ivorytower_4)
                }
            }
            raidImage.setImageResource(raidDrawableList.get(0))

            raidDrawableList.forEachIndexed { index, i ->
                raidDetailTab.addTab(raidDetailTab.newTab().setText((index+plus).toString()+"관문"))
            }

            raidDetailTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    raidDetailImage.setImageResource(raidDrawableList.get(tab?.position!!))
                    raidDetailScrollView.fullScroll(ScrollView.FOCUS_UP)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }

    }

}