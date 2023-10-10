package com.lostark.loahelper.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lostark.loahelper.R
import com.lostark.loahelper.api.LoaRetrofitObj
import com.lostark.loahelper.database.AppDatabase
import com.lostark.loahelper.database.table.Key
import com.lostark.loahelper.databinding.ApiKeyInputActivityBinding
import com.lostark.loahelper.viewmodel.DataViewModel
import retrofit2.Response


class ApiKeyInputActivity : BaseActivity<ApiKeyInputActivityBinding>() {
    private val dataViewModel: DataViewModel by provideViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(ApiKeyInputActivityBinding::inflate)
        binding.run {
            apiKeyInsertButton.setOnClickListener {
                Thread{
                    apiKeyCheck(apiKeyInputEdit.text.toString())
                }.start()
            }
        }

    }
    private fun apiKeyCheck(key:String) {
        val errorCode = dataViewModel.apiKeyCheck("bearer "+key)
        binding.run {
            when (errorCode) {
                200 -> {
                    Log.d("올바른 키입니다.", "errorCode: ")
                    dataViewModel.insertKey("bearer "+key)
                    val intent = Intent(this@ApiKeyInputActivity, StartActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                401 -> {
                    apiKeyStatusText.text = "올바르지 않은 키 입니다. 다시 입력해주세요."
                    Log.d("올바르지 않은 키입니다.", "errorCode: ")
                    dataViewModel.deleteKey()
                }
                503 -> {
                    apiKeyStatusText.text = "서버가 점검 중입니다. 나중에 다시 시도해주세요."
                    Log.d("서버가 점검 중입니다.", "errorCode")
                }
                else->{}
            }
        }

    }

}