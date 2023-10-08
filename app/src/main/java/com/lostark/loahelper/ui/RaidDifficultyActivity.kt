package com.lostark.loahelper.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchActivityBinding
import com.lostark.loahelper.databinding.RaidDifficultyActivityBinding

class RaidDifficultyActivity : BaseActivity<RaidDifficultyActivityBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(RaidDifficultyActivityBinding::inflate)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
                overridePendingTransition(0, 0)
            }
        }

        this.onBackPressedDispatcher.addCallback(this,callback)
        val raidName = intent.getStringExtra("RaidName")

        binding.raidDifficultButtonNormal.setOnClickListener {
            startActivity(Intent(this, RaidDetailActivity::class.java).putExtra("RaidName",raidName+"Normal"))
        }

        binding.raidDifficultButtonHard.setOnClickListener {
            startActivity(Intent(this, RaidDetailActivity::class.java).putExtra("RaidName",raidName+"Hard"))
        }

    }
}