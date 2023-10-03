package com.lostark.loahelper.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.lostark.loahelper.R
import com.lostark.loahelper.viewmodel.DataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartActivity() : BaseActivity() {
    private val dataViewModel: DataViewModel by provideViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        CoroutineScope(Dispatchers.IO).launch {
            val code = dataViewModel.setInit(this@StartActivity)
            when (code) {
                200 -> {
                    val intent = Intent(this@StartActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                401 -> {//키가 잘못됨
                    Log.d("여기까지는 왔네2", "onCreate: ")
                }
                503 -> {//서버 점검
                    Log.d("여기까지는 왔네3", "onCreate: ")
                }
            }
        }

    }
}