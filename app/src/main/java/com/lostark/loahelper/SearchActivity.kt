package com.lostark.loahelper

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.lostark.adapter.RecentNameListAdapter
import com.lostark.api.LoaRetrofitObj
import com.lostark.callbackinterface.RecentDeleteButtonClick
import com.lostark.database.AppDatabase
import com.lostark.database.table.RecentCharInfo
import com.lostark.dto.armorys.Armories
import com.lostark.dto.characters.Characters
import com.lostark.dto.characters.CharactersInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SearchActivity : AppCompatActivity(), RecentDeleteButtonClick{
    val ACCEPT = "application/json"
    lateinit var db: AppDatabase
    var recentNameList = mutableListOf<RecentCharInfo>()
    lateinit var recentNameListAdapter: RecentNameListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.char_search_activity)
        db = AppDatabase.getInstance(applicationContext)!!
        getRecentInfo()
        recentCharListViewSet()
        setSerchEditText()
    }


    override fun onResume() {
        super.onResume()
        getRecentInfo()
        recentNameListAdapter.updateList(recentNameList)
    }

    private fun getRecentInfo() {
        recentNameList.clear()
        db.recentCharInfoDAO().getRecentUpdate().forEach {
            recentNameList.add(it)
        }
    }

    private fun setSerchEditText() {
        val charSearchEdit = findViewById<EditText>(R.id.char_search_name)
        charSearchEdit.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KEYCODE_ENTER) {
                var serverName = ""
                exceptionSearch(charSearchEdit.text.toString()) {
                    serverName = it?.find {
                        it.characterName.uppercase() == charSearchEdit.text.toString().uppercase()
                    }?.serverName.toString()
                }
                searchInfo(charSearchEdit.text.toString()) { armory ->
                    if (armory != null) {
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss")
                        val time = LocalDateTime.now().format(formatter)
                        armory.armoryProfile.serverName = serverName
                        val recentInfo = RecentCharInfo(
                            armory.armoryProfile.characterName,
                            armory.armoryProfile.serverName,
                            armory.armoryProfile.itemMaxLevel,
                            armory.armoryProfile.characterClassName,
                            time
                        )
                        //recentNameListAdapter.updateList(recentNameList)
                        searchCharacters(charSearchEdit.text.toString()) {
                            db.recentCharInfoDAO().insertCharInfo(recentInfo)
                            getRecentInfo()
                            if (it != null)
                                startActivity(
                                    Intent(
                                        Intent(
                                            this,
                                            SearchDetailActivity::class.java
                                        )
                                    ).putExtra("charInfo", armory).putExtra("characters", it)
                                )
                            else
                                Log.d("없음", "널임2: ")
                        }
                    } else {
                        Log.d("없음", "널임: ")
                    }
                }

            }
            false
        }
    }

    private fun exceptionSearch(
        name: String,
        callback: (Characters?) -> Unit
    ) {//서치 인포에서 서치를 했는데 유령 계정이라 서버이름이 ""인 경우 수행하는 서치
        var result: Characters? = null
        val key = db.keyDao().getKey()
        val call = LoaRetrofitObj.getRetrofitService().getCharacters(ACCEPT, key, name)
        call.enqueue(object : Callback<Characters> {
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

    private fun searchInfo(name: String, callback: (Armories?) -> Unit) {
        var result: Armories? = null
        val key = db.keyDao().getKey()
        val call = LoaRetrofitObj.getRetrofitService().getArmories(ACCEPT, key, name)
        call.enqueue(object : Callback<Armories> {
            override fun onResponse(call: Call<Armories>, response: Response<Armories>) {
                result = response.body()
                callback(result)
            }

            override fun onFailure(call: Call<Armories>, t: Throwable) {
                result = null
                callback(null)
            }
        })
    }

    private fun searchCharacters(name: String, callback: (ArrayList<CharactersInfo>?) -> Unit) {
        var result: Characters? = null
        val key = db.keyDao().getKey()
        val call = LoaRetrofitObj.getRetrofitService().getCharacters(ACCEPT, key, name)
        call.enqueue(object : Callback<Characters> {
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

    private fun recentCharListViewSet() {
        val recentCharNameListView = findViewById<ListView>(R.id.recent_char_name_list)
        recentNameListAdapter = RecentNameListAdapter(recentNameList)
        recentNameListAdapter.setRecentDeleteButtonClickListener(this)
        recentCharNameListView.adapter = recentNameListAdapter
        recentCharNameListView.setOnItemClickListener { adapterView, view, position, id ->
            //og.d("아이템 클릭됨 : ", "${adapterView.getItemAtPosition(position)} ")
            var serverName = ""
            exceptionSearch(recentNameList.get(position).charName) {
                serverName = it?.find {
                    it.characterName.uppercase() == recentNameList.get(position).charName.uppercase()
                }?.serverName.toString()
            }
            searchInfo(recentNameList.get(position).charName) { armory ->
                if (armory != null) {
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss")
                    val time = LocalDateTime.now().format(formatter)
                    armory.armoryProfile.serverName = serverName
                    val recentInfo = RecentCharInfo(
                        armory.armoryProfile.characterName,
                        armory.armoryProfile.serverName,
                        armory.armoryProfile.itemMaxLevel,
                        armory.armoryProfile.characterClassName,
                        time
                    )
                    //recentNameListAdapter.updateList(recentNameList)
                    searchCharacters(recentNameList.get(position).charName) {
                        db.recentCharInfoDAO().insertCharInfo(recentInfo)
                        getRecentInfo()
                        if (it != null)
                            startActivity(
                                Intent(
                                    Intent(
                                        this,
                                        SearchDetailActivity::class.java
                                    )
                                ).putExtra("charInfo", armory).putExtra("characters", it)
                            )
                        else
                            Log.d("없음", "널임2: ")
                    }
                } else {
                    Log.d("없음", "널임: ")
                }
            }
        }
    }

    inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? =
        when {
            Build.VERSION.SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
            else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
        }

    override fun onDeleteClick(position: Int) {
        db.recentCharInfoDAO().deleteCharInfo(recentNameList.get(position))
        recentNameList.removeAt(position)
        recentNameListAdapter.updateList(recentNameList)
    }

    fun getSearchInfo(charName: String,callback:(Armories?)->Unit) {
        searchInfo(charName) { armory ->
            if (armory != null) {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss")
                val time = LocalDateTime.now().format(formatter)
                val recentInfo = RecentCharInfo(
                    armory.armoryProfile.characterName,
                    armory.armoryProfile.serverName,
                    armory.armoryProfile.itemMaxLevel,
                    armory.armoryProfile.characterClassName,
                    time
                )
                db.recentCharInfoDAO().insertCharInfo(recentInfo)
                getRecentInfo()
                callback(armory)
            } else {
                Log.d("없음", "널임: ")
            }
            false
        }
    }
    fun getSearchCharacters(name:String,dbInstance:AppDatabase,callback:(ArrayList<CharactersInfo>?)->Unit){
        db = dbInstance
        searchCharacters(name){result->
            callback(result)
        }
    }
}