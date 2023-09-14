package com.lostark.loahelper

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.lostark.adapter.EventViewPagerAdapter
import com.lostark.customview.HomeButtonView
import com.lostark.database.AppDatabase
import com.lostark.database.table.Items
import com.lostark.database.table.Key
import com.lostark.database.table.LoaEvents
import com.lostark.database.table.Notice

class MainActivity : AppCompatActivity() {
    private val updateLink = "https://naver.com"
    val ACCEPT = "application/json"
    val KEY =
        "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyIsImtpZCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyJ9.eyJpc3MiOiJodHRwczovL2x1ZHkuZ2FtZS5vbnN0b3ZlLmNvbSIsImF1ZCI6Imh0dHBzOi8vbHVkeS5nYW1lLm9uc3RvdmUuY29tL3Jlc291cmNlcyIsImNsaWVudF9pZCI6IjEwMDAwMDAwMDAyNDU2NzMifQ.qNrlF-N2QB6yKCpOmTnW_oD68xJX9wRw_kOtDQkifCYWam9rXq_-2BoCXqi6PFCd7gpqUo-q53e7N_xr7f7bsVDde7yfJJPH_l6gghWDckYyMZp9v7J4eSZvJ7gRkAgxpCpfst26MqDaoTIz1Ptkk76HSG-_sCZ4TaatnivprG4qbR5i57k11qX7lcnzZ1WEzvLyVn59V3BAc2mFAOMpl2xAByoihVZUH5beZRV8l8EULVPvIZqjtH9IToWtI7a4IdFZwIsPzGSFWRtxlz1MUn-JQHSXUN4yICeSTrVuGypiAHHNTSAXLMcYqJs7maPTWoZDmG4KQCEndMxJIqt6Yg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        overridePendingTransition(0, 0)
        drawerSet()
        buttonSet()
        eventSet()

    }

    private fun drawerSet() {
        val mainActivity = findViewById<DrawerLayout>(R.id.main_activity)
        val overlayView = findViewById<LinearLayout>(R.id.drawer_view)
        val drawerButton = findViewById<ImageButton>(R.id.drawer_button)

        mainActivity.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        drawerButton.setOnClickListener {
            mainActivity.openDrawer(GravityCompat.END)
        }
        drawerLayoutButtonSet()

        overlayView.setOnTouchListener { _, event -> //터치이벤트 가로채서 뒤에 있는 버튼들 안눌리게
            true
        }

    }

    private fun drawerLayoutButtonSet() {
        val insertButton = findViewById<Button>(R.id.key_insert_button)
        val updateButton = findViewById<Button>(R.id.update_button)
        val keyEditText = findViewById<EditText>(R.id.key_edit)

        val key = intent.getStringExtra("Key") ?: null
        keyEditText.setText(key?.substring(6, key.length))

        insertButton.setOnClickListener {
            val db = AppDatabase.getInstance(applicationContext)!!
            db.keyDao().deleteAllKey()
            db.keyDao().insertKey(Key("bearer " + keyEditText.text.toString()))
        }

        updateButton.setOnClickListener {
            //startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(updateLink)))
        }
    }


    private fun buttonSet() {
        val stoneList = intent.parcelableArrayList<Items>("StoneList")
        val destructionList = intent.parcelableArrayList<Items>("Destruction")

        val noticeList = intent.parcelableArrayList<Notice>("NoticeList")

        val raidButton = findViewById<HomeButtonView>(R.id.home_raid_button)
        val searchButton = findViewById<HomeButtonView>(R.id.char_search_button)
        val dailyButton = findViewById<HomeButtonView>(R.id.home_daily_button)
        val engravingButton = findViewById<HomeButtonView>(R.id.home_engraving_button)
        val noticeButton = findViewById<HomeButtonView>(R.id.home_notice_button)
        val mokokoButton = findViewById<HomeButtonView>(R.id.home_mokoko_button)

        raidButton.ClickEvent(Intent(this, RaidActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        })

        noticeButton.ClickEvent(
            Intent(this, NoticeActivity::class.java)
                .putExtra("NoticeList", noticeList).apply {
                    flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                }
        )

        dailyButton.ClickEvent(
            Intent(this, DailyActivity::class.java)
                .putExtra("StoneList", stoneList)
                .putExtra("Destruction", destructionList).apply {
                    flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                }
        )

        engravingButton.ClickEvent(Intent(this, EngravingActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        })

        searchButton.ClickEvent(Intent(this, SearchActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        })
    }

    private fun eventSet() {
        val eventList = intent.parcelableArrayList<LoaEvents>("EventList")
        val eventPager = findViewById<ViewPager2>(R.id.event_image_slider)
        val eventAdapter = EventViewPagerAdapter(eventList!!, eventPager)
        eventPager.adapter = eventAdapter
    }

    inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? =
        when {
            SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
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