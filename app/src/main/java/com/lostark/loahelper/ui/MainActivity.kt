package com.lostark.loahelper.ui

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.lostark.loahelper.R
import com.lostark.loahelper.adapter.EventViewPagerAdapter
import com.lostark.loahelper.api.LoaRetrofitObj
import com.lostark.loahelper.customview.HomeButtonView
import com.lostark.loahelper.database.AppDatabase
import com.lostark.loahelper.database.table.*
import com.lostark.loahelper.databinding.ApiKeyInputActivityBinding
import com.lostark.loahelper.databinding.MainActivityBinding
import com.lostark.loahelper.viewmodel.DataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class MainActivity : BaseActivity<MainActivityBinding>() {
    private val updateLink = "https://github.com/AshRainner/LoaHelper"

    private val dataViewModel: DataViewModel by provideViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.main_activity)
        initBinding(MainActivityBinding::inflate)
        overridePendingTransition(0, 0)
        drawerSet()
        buttonSet()
        eventSet()
    }

    private fun drawerSet() {

        binding.run {
            mainActivity.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            drawerButton.setOnClickListener {
                mainActivity.openDrawer(GravityCompat.END)
            }
            drawerLayoutButtonSet()

            // include한거라 이렇게 접근해야함
            drawerView.drawerViewReal.setOnTouchListener { _, event -> //터치이벤트 가로채서 뒤에 있는 버튼들 안눌리게
                true
            }
        }


    }

    private fun drawerLayoutButtonSet() {
        binding.drawerView.run {
            keyEdit.setText(dataViewModel.getKey()?.substring(6, dataViewModel.getKey().length))

            keyInsertButton.setOnClickListener {

                CoroutineScope(Dispatchers.IO).launch {
                    val text = dataViewModel.apiKeyCheckMain(keyEdit.text.toString())
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            text,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            updateButton.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(updateLink)))
            }
        }
    }


    private fun buttonSet() {
        binding.run {
            homeCalculatorButton.ClickEvent(
                Intent(this@MainActivity, CalculatorActivity::class.java)
                    .apply {
                        flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    })

            homeRaidButton.ClickEvent(Intent(this@MainActivity, RaidActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            })

            homeNoticeButton.ClickEvent(
                Intent(this@MainActivity, NoticeActivity::class.java)
                    .apply {
                        flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    }
            )

            homeDailyButton.ClickEvent(
                Intent(this@MainActivity, DailyActivity::class.java)
                    .apply {
                        flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    }
            )

            homeEngravingButton.ClickEvent(
                Intent(
                    this@MainActivity,
                    EngravingActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                })

            charSearchButton.ClickEvent(
                Intent(
                    this@MainActivity,
                    SearchActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                })
        }
    }

    private fun eventSet() {
        val eventPager = findViewById<ViewPager2>(R.id.event_image_slider)
        binding.run {
            val eventAdapter = EventViewPagerAdapter(dataViewModel.getEventList(), eventPager)
            eventImageSlider.adapter=eventAdapter
        }
    }

    override fun onBackPressed() {
        val mainActivity = findViewById<DrawerLayout>(R.id.main_activity)
        if (mainActivity.isDrawerOpen(GravityCompat.END)) {
            mainActivity.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }
}