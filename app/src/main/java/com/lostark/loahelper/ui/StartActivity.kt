package com.lostark.loahelper.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchActivityBinding
import com.lostark.loahelper.databinding.StartActivityBinding
import com.lostark.loahelper.viewmodel.DataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartActivity() : BaseActivity<StartActivityBinding>() {
    private val dataViewModel: DataViewModel by provideViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            val code = dataViewModel.setInit(this@StartActivity)
            when (code) {
                200 -> {
                    val intent = Intent(this@StartActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                401 -> {//키가 잘못됨
                    val intent = Intent(this@StartActivity, ApiKeyInputActivity::class.java)
                    Log.d("여기까지는 왔네2", "onCreate: ")
                    startActivity(intent)
                    finish()
                }
                503 -> {//서버 점검
                    Log.d("여기까지는 왔네3", "onCreate: ")
                }
                0->{
                    val intent = Intent(this@StartActivity, ApiKeyInputActivity::class.java)
                    Log.d("여기까지는 왔네4", "onCreate: ")
                    startActivity(intent)
                }
            }
        }

    }
}