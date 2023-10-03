package com.lostark.loahelper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.activity.OnBackPressedCallback

class RaidDifficultyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.raid_difficulty_activity)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
                overridePendingTransition(0, 0)
            }
        }

        this.onBackPressedDispatcher.addCallback(this,callback)
        val raidName = intent.getStringExtra("RaidName")
        val normalButton = findViewById<Button>(R.id.raid_difficult_button_normal)
        val hardButton = findViewById<Button>(R.id.raid_difficult_button_hard)

        normalButton.setOnClickListener {
            startActivity(Intent(this,RaidDetailActivity::class.java).putExtra("RaidName",raidName+"Normal"))
        }

        hardButton.setOnClickListener {
            startActivity(Intent(this,RaidDetailActivity::class.java).putExtra("RaidName",raidName+"Hard"))
        }


    }
}