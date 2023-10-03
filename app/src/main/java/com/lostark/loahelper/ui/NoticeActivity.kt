package com.lostark.loahelper.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.lostark.loahelper.R
import com.lostark.loahelper.adapter.NoticeListAdapter
import com.lostark.loahelper.database.table.Notice

class NoticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notices_activity)
        noticeListViewSet()
    }

    private fun noticeListViewSet(){
        val noticeType = resources.getStringArray(R.array.noticeType)
        val spinnerAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, noticeType)
        val spinner = findViewById<Spinner>(R.id.notice_type_spinner)
        val noticeListView = findViewById<ListView>(R.id.notices_list)
        val noticeSearch = findViewById<EditText>(R.id.notice_search)
        spinner.adapter = spinnerAdapter

        var noticeList = intent.parcelableArrayList<Notice>("NoticeList")!!
        var noticeListCopy = arrayListOf<Notice>()
        noticeListCopy.addAll(noticeList)
        val noticeListAdapter = NoticeListAdapter(noticeList)
        noticeListAdapter.notifyDataSetChanged()
        noticeListView.adapter = noticeListAdapter

        noticeListView.setOnItemClickListener { adapterView, view, positon, id ->
            val item = adapterView.getItemAtPosition(positon) as Notice
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
            startActivity(intent, null)
        }

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p1 is TextView) {
                    p1.setTextColor(Color.BLACK) // 텍스트 색상을 원하는 색으로 변경
                }
                search(noticeList,noticeListCopy,spinner,noticeSearch)
                noticeListAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        noticeSearch.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KEYCODE_ENTER) {
                search(noticeList,noticeListCopy,spinner,noticeSearch)
                noticeListAdapter.notifyDataSetChanged()
            }
            false
        }
    }

    fun search(noticeList:ArrayList<Notice>, noticeListCopy:ArrayList<Notice>, spinner: Spinner, noticeSearch:EditText){
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

        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)

    }
    inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
    }
}