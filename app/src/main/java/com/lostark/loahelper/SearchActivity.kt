package com.lostark.loahelper

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.lostark.adapter.RecentNameListAdapter
import com.lostark.api.LoaRetrofitObj
import com.lostark.callbackinterface.RecentDeleteButtonClick
import com.lostark.database.AppDatabase
import com.lostark.database.table.Notice
import com.lostark.database.table.RecentCharInfo
import com.lostark.dto.characters.Characters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SearchActivity : AppCompatActivity(), RecentDeleteButtonClick {
    val ACCEPT = "application/json"
    lateinit var db : AppDatabase
    var recentNameList = mutableListOf<RecentCharInfo>()
    lateinit var recentNameListAdapter : RecentNameListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.char_search_activity)
        db = AppDatabase.getInstance(applicationContext)!!
        getRecentInfo()
        recentCharListViewSet()
        setSerchEditText()
    }
    private fun getRecentInfo(){
        recentNameList.clear()
        db.recentCharInfoDAO().getRecentUpdate().forEach{
            recentNameList.add(it)
        }
    }

    private fun setSerchEditText(){
        val charSearchEdit = findViewById<EditText>(R.id.char_search_name)
        charSearchEdit.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KEYCODE_ENTER) {
                searchInfo(charSearchEdit.text.toString()) { result ->
                    if(result!=null) {
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss")
                        val time = LocalDateTime.now().format(formatter)
                        val recentInfo = RecentCharInfo(
                            result.armoryProfile.characterName,
                            result.armoryProfile.serverName,
                            result.armoryProfile.itemMaxLevel,
                            result.armoryProfile.characterClassName,
                            time
                        )
                        Log.d("들어옴", "setSerchEditText: ")
                        db.recentCharInfoDAO().insertCharInfo(recentInfo)
                        getRecentInfo()
                        recentNameListAdapter.updateList(recentNameList)
                    }
                    else{
                        Log.d("없음", "널임: ")
                    }
                }

            }
            false
        }
    }

    private fun searchInfo(name: String, callback: (Characters?) -> Unit){
        var result:Characters? = null
        val key = db.keyDao().getKey()
        val call = LoaRetrofitObj.getRetrofitService().getCharacters(ACCEPT, key, name)
        call.enqueue(object : Callback<Characters>{
            override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                result = response.body()
                callback(result)
            }

            override fun onFailure(call: Call<Characters>, t: Throwable) {
                result = null
                callback(null)
            }
        })

    }


    private fun recentCharListViewSet(){
        val recentCharNameListView = findViewById<ListView>(R.id.recent_char_name_list)
        recentNameListAdapter = RecentNameListAdapter(recentNameList)
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
        db.recentCharInfoDAO().deleteCharInfo(recentNameList.get(position))
        recentNameList.removeAt(position)
        recentNameListAdapter.updateList(recentNameList)
    }
}