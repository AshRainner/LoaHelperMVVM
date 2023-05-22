package com.lostark.loahelper

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.lostark.adapter.EventViewPagerAdapter
import com.lostark.api.LoaRetrofitObj
import com.lostark.database.AppDatabase
import com.lostark.database.dao.EventsDAO
import com.lostark.database.dao.KeyDAO
import com.lostark.database.table.LoaEvents
import com.lostark.database.table.Key
import com.lostark.database.table.UpdateT
import com.lostark.dto.EventDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class StartActivity() : AppCompatActivity() {
    val KEY =
        "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyIsImtpZCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyJ9.eyJpc3MiOiJodHRwczovL2x1ZHkuZ2FtZS5vbnN0b3ZlLmNvbSIsImF1ZCI6Imh0dHBzOi8vbHVkeS5nYW1lLm9uc3RvdmUuY29tL3Jlc291cmNlcyIsImNsaWVudF9pZCI6IjEwMDAwMDAwMDAyNDU2NzMifQ.qNrlF-N2QB6yKCpOmTnW_oD68xJX9wRw_kOtDQkifCYWam9rXq_-2BoCXqi6PFCd7gpqUo-q53e7N_xr7f7bsVDde7yfJJPH_l6gghWDckYyMZp9v7J4eSZvJ7gRkAgxpCpfst26MqDaoTIz1Ptkk76HSG-_sCZ4TaatnivprG4qbR5i57k11qX7lcnzZ1WEzvLyVn59V3BAc2mFAOMpl2xAByoihVZUH5beZRV8l8EULVPvIZqjtH9IToWtI7a4IdFZwIsPzGSFWRtxlz1MUn-JQHSXUN4yICeSTrVuGypiAHHNTSAXLMcYqJs7maPTWoZDmG4KQCEndMxJIqt6Yg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        val intent = Intent(this@StartActivity, MainActivity::class.java)
        val db = AppDatabase.getInstance(applicationContext)!!

        if (db.keyDao().getKey() != null) {//키가 비어 있지 않다면
            val key = db.keyDao().getKey()

            val time = LoaTimeCheck()

            val recentTimeList = db.UpdateDAO().getRecentUpdate()!!
            //db.UpdateDAO().deleteUpdate(UpdateT("2023-05-22T10"))
            if (recentTimeList.isNotEmpty()) {
                val recentTime = recentTimeList.get(0).recentUpdate
                if (compareToDate(time, recentTime)) {
                    Log.d("여기까진옴", "히히히히히히ㅣ히히히: ")
                    db.eventsDao().deleteAllEvents()
                    Thread {
                        insertEvents(db.eventsDao(), key)
                        intent.putExtra(
                            "EventList",
                            db.eventsDao().getEventList() as ArrayList<LoaEvents>
                        )
                        val testList = db.eventsDao().getEventList()
                        startActivity(intent)
                        finish()
                    }.start()
                }
            } else {
                db.UpdateDAO().insertUpdate(UpdateT(time.split('T')[0]))
            }
        } else {
            db.keyDao().insertKey(Key(KEY))
        }
    }

    fun insertEvents(eventsDao: EventsDAO, key: String): List<LoaEvents> {
        val call = LoaRetrofitObj.getRetrofitService().getEvent("application/json", key)
        val eventList = call.execute().body()
        eventList?.forEach {//엘비스 연산 list가 null이 아니면 forEach실행 null일시 뒤에 있는 log.d실행
            eventsDao.insertEvents(LoaEvents(it.link, it.title, it.thumbnail))
        }
        Log.d("보내기 전 사이즈 : ", eventsDao.getEventList().size.toString())
        return eventsDao.getEventList()
    }

    fun compareToDate(time: String, recentTime: String): Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val timeDate = sdf.parse(time.split("T").get(0))
        val recentTimeDate = sdf.parse(recentTime.split("T").get(0))
        val cmp = timeDate.compareTo(recentTimeDate)
        return when {
            cmp > 0 -> {
                true
            }
            cmp < 0 -> {
                false
            }
            else -> {
                true
            }
        }
    }

    fun LoaTimeCheck(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH")
        cal.time = Date()
        if (sdf.format(cal.time).toString().split('T')[1].toInt() < 10)
            cal.add(Calendar.DATE, -1)
        return sdf.format(cal.time)
    }

}