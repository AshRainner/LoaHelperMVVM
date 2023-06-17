package com.lostark.loahelper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.lostark.adapter.NoticeListAdapter
import com.lostark.adapter.RecentNameListAdapter
import com.lostark.api.LoaRetrofitObj
import com.lostark.callbackinterface.RecentDeleteButtonClick
import com.lostark.database.table.Notice
import com.lostark.dto.NoticeDTO
import retrofit2.Call
import retrofit2.Response

class SearchActivity : AppCompatActivity(), RecentDeleteButtonClick {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.char_search_activity)
        recentCharListViewSet()
    }

    private fun recentCharListViewSet(){
        val recentCharNameListView = findViewById<ListView>(R.id.recent_char_name_list)
        val charSearchEdit = findViewById<EditText>(R.id.char_search_name)

        var testList = mutableListOf<Int>()
        testList.add(1)
        testList.add(2)
        val recentNameListAdapter = RecentNameListAdapter(testList)
        recentNameListAdapter.setRecentDeleteButtonClickListener(this)
        recentCharNameListView.adapter = recentNameListAdapter
        recentCharNameListView.setOnItemClickListener { adapterView, view, position, id ->
            Log.d("아이템 클릭됨 : ", "${adapterView.getItemAtPosition(position)} ")
        }
    }

    fun search(noticeList:ArrayList<Notice>,noticeListCopy:ArrayList<Notice>,spinner: Spinner,noticeSearch:EditText){
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

    override fun onDeleteClick(position: Int) {
        Log.d("딜리트 눌림 : ",position.toString())
    }
}