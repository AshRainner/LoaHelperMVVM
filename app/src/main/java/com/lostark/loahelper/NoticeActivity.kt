package com.lostark.loahelper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.lostark.adapter.NoticeListAdapter
import com.lostark.api.LoaRetrofitObj
import com.lostark.dto.NoticeDTO
import retrofit2.Call
import retrofit2.Response

class NoticeActivity : AppCompatActivity() {
    val ACCEPT = "application/json"
    val KEY =
        "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyIsImtpZCI6IktYMk40TkRDSTJ5NTA5NWpjTWk5TllqY2lyZyJ9.eyJpc3MiOiJodHRwczovL2x1ZHkuZ2FtZS5vbnN0b3ZlLmNvbSIsImF1ZCI6Imh0dHBzOi8vbHVkeS5nYW1lLm9uc3RvdmUuY29tL3Jlc291cmNlcyIsImNsaWVudF9pZCI6IjEwMDAwMDAwMDAyNDU2NzMifQ.qNrlF-N2QB6yKCpOmTnW_oD68xJX9wRw_kOtDQkifCYWam9rXq_-2BoCXqi6PFCd7gpqUo-q53e7N_xr7f7bsVDde7yfJJPH_l6gghWDckYyMZp9v7J4eSZvJ7gRkAgxpCpfst26MqDaoTIz1Ptkk76HSG-_sCZ4TaatnivprG4qbR5i57k11qX7lcnzZ1WEzvLyVn59V3BAc2mFAOMpl2xAByoihVZUH5beZRV8l8EULVPvIZqjtH9IToWtI7a4IdFZwIsPzGSFWRtxlz1MUn-JQHSXUN4yICeSTrVuGypiAHHNTSAXLMcYqJs7maPTWoZDmG4KQCEndMxJIqt6Yg"
    lateinit var noticeList: MutableList<NoticeDTO>
    lateinit var noticeListCopy: MutableList<NoticeDTO>
    lateinit var noticeListAdapter: NoticeListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notices_activity)
        val noticeType = resources.getStringArray(R.array.noticeType)
        val spinnerAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noticeType)
        val spinner = findViewById<Spinner>(R.id.notice_type_spinner)
        val noticeListView = findViewById<ListView>(R.id.notices_list)
        val noticeSearch = findViewById<EditText>(R.id.notice_search)
        spinner.adapter = spinnerAdapter
        val call = LoaRetrofitObj.getRetrofitService().getNotice(ACCEPT, KEY)
        call.enqueue(object : retrofit2.Callback<MutableList<NoticeDTO>> {
            override fun onResponse(
                call: Call<MutableList<NoticeDTO>>,
                response: Response<MutableList<NoticeDTO>>
            ) {
                noticeList = response.body()!!
                noticeListCopy = mutableListOf()
                noticeListCopy.addAll(noticeList)
                noticeListAdapter = NoticeListAdapter(noticeList)
                noticeListAdapter.notifyDataSetChanged()
                noticeListView.adapter = noticeListAdapter
            }

            override fun onFailure(call: Call<MutableList<NoticeDTO>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        noticeListView.setOnItemClickListener { adapterView, view, positon, id ->
            val item = adapterView.getItemAtPosition(positon) as NoticeDTO
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
            startActivity(intent, null)
        }
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(::noticeList.isInitialized)
                    search(spinner, noticeSearch)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        noticeSearch.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KEYCODE_ENTER) {
                search(spinner,noticeSearch)
            }
            false
        }
    }
    fun search(spinner: Spinner,noticeSearch:EditText){
        noticeList.clear()
        noticeListCopy.forEach {
            if (spinner.selectedItem.toString().equals("전체")) {
                if (it.title.contains(noticeSearch.text))
                    noticeList.add(it)
            }
            else
                if (it.title.contains(noticeSearch.text) && it.type == spinner.selectedItem.toString())
                    noticeList.add(it)
        }
        noticeListAdapter.notifyDataSetChanged()
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)
    }
}