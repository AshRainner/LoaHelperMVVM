package com.lostark.loahelper

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.lostark.adapter.EventViewPagerAdapter
import com.lostark.api.LoaRetrofitObj
import com.lostark.customview.HomeButtonView
import com.lostark.database.table.Items
import com.lostark.database.table.LoaEvents
import com.lostark.dto.EventDTO
import retrofit2.Call
import retrofit2.Response
import java.io.Serializable
import kotlin.reflect.typeOf

class MainActivity : AppCompatActivity() {
    val ACCEPT = "application/json"
    val KEY = "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyIsImtpZCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyJ9.eyJpc3MiOiJodHRwczovL2x1ZHkuZ2FtZS5vbnN0b3ZlLmNvbSIsImF1ZCI6Imh0dHBzOi8vbHVkeS5nYW1lLm9uc3RvdmUuY29tL3Jlc291cmNlcyIsImNsaWVudF9pZCI6IjEwMDAwMDAwMDAyNDU2NzMifQ.qNrlF-N2QB6yKCpOmTnW_oD68xJX9wRw_kOtDQkifCYWam9rXq_-2BoCXqi6PFCd7gpqUo-q53e7N_xr7f7bsVDde7yfJJPH_l6gghWDckYyMZp9v7J4eSZvJ7gRkAgxpCpfst26MqDaoTIz1Ptkk76HSG-_sCZ4TaatnivprG4qbR5i57k11qX7lcnzZ1WEzvLyVn59V3BAc2mFAOMpl2xAByoihVZUH5beZRV8l8EULVPvIZqjtH9IToWtI7a4IdFZwIsPzGSFWRtxlz1MUn-JQHSXUN4yICeSTrVuGypiAHHNTSAXLMcYqJs7maPTWoZDmG4KQCEndMxJIqt6Yg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val eventList =  intent.parcelableArrayList<LoaEvents>("EventList")
        val eventPager = findViewById<ViewPager2>(R.id.event_image_slider)
        val eventAdapter = EventViewPagerAdapter(eventList!! ,eventPager)
        eventPager.adapter = eventAdapter

        val stoneList = intent.parcelableArrayList<Items>("StoneList")
        val destructionList = intent.parcelableArrayList<Items>("Destruction")

        val raidButton = findViewById<HomeButtonView>(R.id.home_raid_button)
        val craftButton = findViewById<HomeButtonView>(R.id.home_craft_button)
        val dailyButton = findViewById<HomeButtonView>(R.id.home_daily_button)
        val engravingButton = findViewById<HomeButtonView>(R.id.home_engraving_button)
        val noticeButton = findViewById<HomeButtonView>(R.id.home_notice_button)
        val mokokoButton = findViewById<HomeButtonView>(R.id.home_mokoko_button)
        val testButton = findViewById<Button>(R.id.apiTestButton)

        raidButton.ClickEvent(Intent(this,RaidActivity::class.java))

        noticeButton.ClickEvent(Intent(this,NoticeActivity::class.java))

        dailyButton.ClickEvent(Intent(this,DailyActivity::class.java)
            .putExtra("StoneList",stoneList)
            .putExtra("Destruction",destructionList))
    }
    /*fun <T : Serializable?> getSerializable(activity: Activity, name: String, clazz: Class<T>): ArrayList<T>
    {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            activity.intent.getSerializableExtra(name, clazz)!!
        else
            activity.intent.getSerializableExtra(name) as T
    }*/

    inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
        SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
    }
}