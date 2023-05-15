package com.lostark.loahelper

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.lostark.adapter.NoticeListAdapter
import com.lostark.api.LoaRetrofitObj
import com.lostark.dto.NoticeDTO
import retrofit2.Call
import retrofit2.Response

class NoticeActivity:AppCompatActivity() {
    val ACCEPT = "application/json"
    val KEY = "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyIsImtpZCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyJ9.eyJpc3MiOiJodHRwczovL2x1ZHkuZ2FtZS5vbnN0b3ZlLmNvbSIsImF1ZCI6Imh0dHBzOi8vbHVkeS5nYW1lLm9uc3RvdmUuY29tL3Jlc291cmNlcyIsImNsaWVudF9pZCI6IjEwMDAwMDAwMDAyNDU2NzMifQ.qNrlF-N2QB6yKCpOmTnW_oD68xJX9wRw_kOtDQkifCYWam9rXq_-2BoCXqi6PFCd7gpqUo-q53e7N_xr7f7bsVDde7yfJJPH_l6gghWDckYyMZp9v7J4eSZvJ7gRkAgxpCpfst26MqDaoTIz1Ptkk76HSG-_sCZ4TaatnivprG4qbR5i57k11qX7lcnzZ1WEzvLyVn59V3BAc2mFAOMpl2xAByoihVZUH5beZRV8l8EULVPvIZqjtH9IToWtI7a4IdFZwIsPzGSFWRtxlz1MUn-JQHSXUN4yICeSTrVuGypiAHHNTSAXLMcYqJs7maPTWoZDmG4KQCEndMxJIqt6Yg"
    lateinit var noticeList: MutableList<NoticeDTO>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notices_activity)
        var noticeType = resources.getStringArray(R.array.noticeType)
        var spinnerAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,noticeType)
        var spinner = findViewById<Spinner>(R.id.notice_type_spinner)
        val noticeListView = findViewById<ListView>(R.id.notices_list)
        spinner.adapter=spinnerAdapter
        val call = LoaRetrofitObj.getRetrofitService().getNotice(ACCEPT,KEY)
        call.enqueue(object : retrofit2.Callback<MutableList<NoticeDTO>>{
            override fun onResponse(
                call: Call<MutableList<NoticeDTO>>,
                response: Response<MutableList<NoticeDTO>>
            ) {
                noticeList=response.body()!!
                val noticeListAdapter = NoticeListAdapter(noticeList)
                noticeListView.adapter=noticeListAdapter
            }

            override fun onFailure(call: Call<MutableList<NoticeDTO>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        noticeListView.setOnItemClickListener { adapterView, view, positon, id ->
            val item = adapterView.getItemAtPosition(positon) as NoticeDTO
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
            startActivity(intent,null)
        }

    }
}