package com.lostark.loahelper.ui

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
import com.lostark.loahelper.R
import com.lostark.loahelper.adapter.RecentNameListAdapter
import com.lostark.loahelper.api.LoaRetrofitObj
import com.lostark.loahelper.callbackinterface.RecentDeleteButtonClick
import com.lostark.loahelper.database.AppDatabase
import com.lostark.loahelper.database.table.RecentCharInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SearchActivity : AppCompatActivity(), RecentDeleteButtonClick {
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
        callback: (com.lostark.loahelper.dto.characters.Characters?) -> Unit
    ) {//서치 인포에서 서치를 했는데 유령 계정이라 서버이름이 ""인 경우 수행하는 서치
        var result: com.lostark.loahelper.dto.characters.Characters? = null
        val key = db.keyDao().getKey()
        val call = LoaRetrofitObj.getRetrofitService().getCharacters(ACCEPT, key, name)
        call.enqueue(object : Callback<com.lostark.loahelper.dto.characters.Characters> {
            override fun onResponse(call: Call<com.lostark.loahelper.dto.characters.Characters>, response: Response<com.lostark.loahelper.dto.characters.Characters>) {
                result = response.body()
                callback(result)
            }

            override fun onFailure(call: Call<com.lostark.loahelper.dto.characters.Characters>, t: Throwable) {
                result = null
                callback(null)
            }
        })
    }

    private fun searchInfo(name: String, callback: (com.lostark.loahelper.dto.armorys.Armories?) -> Unit) {
        var result: com.lostark.loahelper.dto.armorys.Armories? = null
        val key = db.keyDao().getKey()
        val call = LoaRetrofitObj.getRetrofitService().getArmories(ACCEPT, key, name)
        call.enqueue(object : Callback<com.lostark.loahelper.dto.armorys.Armories> {
            override fun onResponse(call: Call<com.lostark.loahelper.dto.armorys.Armories>, response: Response<com.lostark.loahelper.dto.armorys.Armories>) {
                result = response.body()
                callback(result)
            }

            override fun onFailure(call: Call<com.lostark.loahelper.dto.armorys.Armories>, t: Throwable) {
                result = null
                callback(null)
            }
        })
    }

    private fun searchCharacters(name: String, callback: (ArrayList<com.lostark.loahelper.dto.characters.CharactersInfo>?) -> Unit) {
        var result: com.lostark.loahelper.dto.characters.Characters? = null
        val key = db.keyDao().getKey()
        val call = LoaRetrofitObj.getRetrofitService().getCharacters(ACCEPT, key, name)
        call.enqueue(object : Callback<com.lostark.loahelper.dto.characters.Characters> {
            override fun onResponse(call: Call<com.lostark.loahelper.dto.characters.Characters>, response: Response<com.lostark.loahelper.dto.characters.Characters>) {
                result = response.body()
                callback(result)
            }

            override fun onFailure(call: Call<com.lostark.loahelper.dto.characters.Characters>, t: Throwable) {
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

    fun getSearchInfo(charName: String,callback:(com.lostark.loahelper.dto.armorys.Armories?)->Unit) {
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
    fun getSearchCharacters(name:String, dbInstance: AppDatabase, callback:(ArrayList<com.lostark.loahelper.dto.characters.CharactersInfo>?)->Unit){
        db = dbInstance
        searchCharacters(name){result->
            callback(result)
        }
    }
}