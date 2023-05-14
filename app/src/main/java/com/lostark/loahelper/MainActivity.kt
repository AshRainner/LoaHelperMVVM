package com.lostark.loahelper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.lostark.adapter.EventViewPagerAdapter
import com.lostark.api.LoaRetrofitObj
import com.lostark.customview.HomeButtonView
import com.lostark.dto.EventDTO
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val ACCEPT = "application/json"
    val KEY = "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyIsImtpZCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyJ9.eyJpc3MiOiJodHRwczovL2x1ZHkuZ2FtZS5vbnN0b3ZlLmNvbSIsImF1ZCI6Imh0dHBzOi8vbHVkeS5nYW1lLm9uc3RvdmUuY29tL3Jlc291cmNlcyIsImNsaWVudF9pZCI6IjEwMDAwMDAwMDAyNDU2NzMifQ.qNrlF-N2QB6yKCpOmTnW_oD68xJX9wRw_kOtDQkifCYWam9rXq_-2BoCXqi6PFCd7gpqUo-q53e7N_xr7f7bsVDde7yfJJPH_l6gghWDckYyMZp9v7J4eSZvJ7gRkAgxpCpfst26MqDaoTIz1Ptkk76HSG-_sCZ4TaatnivprG4qbR5i57k11qX7lcnzZ1WEzvLyVn59V3BAc2mFAOMpl2xAByoihVZUH5beZRV8l8EULVPvIZqjtH9IToWtI7a4IdFZwIsPzGSFWRtxlz1MUn-JQHSXUN4yICeSTrVuGypiAHHNTSAXLMcYqJs7maPTWoZDmG4KQCEndMxJIqt6Yg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var eventPager = findViewById<ViewPager2>(R.id.event_image_slider)

        var raidButton = findViewById<HomeButtonView>(R.id.home_raid_button)
        var craftButton = findViewById<HomeButtonView>(R.id.home_craft_button)
        var dailyButton = findViewById<HomeButtonView>(R.id.home_daily_button)
        var engravingButton = findViewById<HomeButtonView>(R.id.home_engraving_button)
        var upadateButton = findViewById<HomeButtonView>(R.id.home_update_button)
        var mokokoButton = findViewById<HomeButtonView>(R.id.home_mokoko_button)
        var testButton = findViewById<Button>(R.id.apiTestButton)
        val call = LoaRetrofitObj.getRetrofitService().getEvent(ACCEPT,KEY)
        call.enqueue(object : retrofit2.Callback<MutableList<EventDTO>>{
            override fun onResponse(call: Call<MutableList<EventDTO>>, response: Response<MutableList<EventDTO>>) {
                Log.d(response.body().toString(),"F")
                if(response.isSuccessful){
                    Log.d(response.body().toString(),"ASDF")
                    val eventList: MutableList<EventDTO> = response.body()!!

                    eventList?.forEach{//엘비스 연산 list가 null이 아니면 forEach실행 null일시 뒤에 있는 log.d실행
                        Log.d("타이틀 : ", it.title)
                    } ?: Log.d("이벤트 없음","진행중인 이벤트 없음")
                    eventPager.adapter = EventViewPagerAdapter(eventList,eventPager)
                }
            }

            override fun onFailure(call: Call<MutableList<EventDTO>>, t: Throwable) {
                Log.d("오류",t.toString())
            }
        })

        /*testButton.setOnClickListener(View.OnClickListener {
            Log.d("눌림","눌림")
            val call = LoaRetrofitObj.getRetrofitService().getEvent(ACCEPT,KEY)
            call.enqueue(object : retrofit2.Callback<List<EventDTO>>{
                override fun onResponse(call: Call<List<EventDTO>>, response: Response<List<EventDTO>>) {
                    Log.d(response.body().toString(),"F")
                    if(response.isSuccessful){
                        Log.d(response.body().toString(),"ASDF")
                        val eventList: List<EventDTO>? = response.body()
                        var linkList: MutableList<String> = mutableListOf()
                        eventList?.forEach{//엘비스 연산 list가 null이 아니면 forEach실행 null일시 뒤에 있는 log.d실행
                            Log.d("타이틀 : ", it.title)
                            linkList.add(it.thumbnail)
                        } ?: Log.d("이벤트 없음","진행중인 이벤트 없음")
                    }
                }

                override fun onFailure(call: Call<List<EventDTO>>, t: Throwable) {
                    Log.d("오류",t.toString())
                }
            })
        })*/

        raidButton.ClickEvent(Intent(this,RaidActivity::class.java))
    }

}