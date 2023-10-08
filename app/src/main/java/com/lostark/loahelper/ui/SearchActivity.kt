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
import androidx.lifecycle.Observer
import com.lostark.loahelper.R
import com.lostark.loahelper.adapter.RecentNameListAdapter
import com.lostark.loahelper.api.LoaRetrofitObj
import com.lostark.loahelper.callbackinterface.RecentDeleteButtonClick
import com.lostark.loahelper.database.AppDatabase
import com.lostark.loahelper.database.table.RecentCharInfo
import com.lostark.loahelper.databinding.CharSearchActivityBinding
import com.lostark.loahelper.viewmodel.DataViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SearchActivity : BaseActivity<CharSearchActivityBinding>(), RecentDeleteButtonClick {
    private val dataViewModel: DataViewModel by provideViewModel()
    lateinit var recentNameListAdapter: RecentNameListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(CharSearchActivityBinding::inflate)
        dataViewModel.recentNameList.observe(this, Observer {
            recentNameListAdapter.updateList(it)
        })
        dataViewModel.updateRecentCharInfoList()

        recentCharListViewSet()
        setSerchEditText()
    }

    override fun onResume() {
        super.onResume()
        dataViewModel.updateRecentCharInfoList()
        //recentNameListAdapter.updateList(dataViewModel.getRecentCharInfo())
    }

    private fun setSerchEditText() {
        val charSearchEdit = findViewById<EditText>(R.id.char_search_name)
        charSearchEdit.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KEYCODE_ENTER) {
                var serverName = ""
                dataViewModel.serverNameSearch(charSearchEdit.text.toString()) {
                    serverName = it?.find {
                        it.characterName.uppercase() == charSearchEdit.text.toString().uppercase()
                    }?.serverName.toString()
                }
                dataViewModel.searchInfo(charSearchEdit.text.toString()) { armory ->
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
                        dataViewModel.searchCharacters(charSearchEdit.text.toString()) {
                            dataViewModel.insertRecentCharInfo(recentInfo)
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

    private fun recentCharListViewSet() {

        binding.run {
            recentNameListAdapter = RecentNameListAdapter(dataViewModel.getRecentCharInfo())
            recentNameListAdapter.setRecentDeleteButtonClickListener(this@SearchActivity)
            recentCharNameList.adapter = recentNameListAdapter
            recentCharNameList.setOnItemClickListener { adapterView, view, position, id ->
                //og.d("아이템 클릭됨 : ", "${adapterView.getItemAtPosition(position)} ")
                var serverName = ""
                dataViewModel.serverNameSearch(dataViewModel.getRecentCharInfo().get(position).charName) {
                    serverName = it?.find {
                        it.characterName.uppercase() == dataViewModel.getRecentCharInfo().get(position).charName.uppercase()
                    }?.serverName.toString()
                }
                dataViewModel.searchInfo(dataViewModel.getRecentCharInfo().get(position).charName) { armory ->
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
                        dataViewModel.searchCharacters(dataViewModel.getRecentCharInfo().get(position).charName) {
                            dataViewModel.insertRecentCharInfo(recentInfo)
                            if (it != null)
                                startActivity(
                                    Intent(
                                        Intent(
                                            this@SearchActivity,
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
    }

    override fun onDeleteClick(position: Int) {
        dataViewModel.getRecentCharInfoDao().deleteCharInfo(dataViewModel.getRecentCharInfo().get(position))
        dataViewModel.updateRecentCharInfoList()
        recentNameListAdapter.updateList(dataViewModel.getRecentCharInfo())
    }
}