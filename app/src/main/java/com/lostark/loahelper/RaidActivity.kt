package com.lostark.loahelper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.OnBackPressedCallback
import com.lostark.customview.RaidButtonView

class RaidActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.raid_activity)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
                overridePendingTransition(0, 0)
            }
        }

        this.onBackPressedDispatcher.addCallback(this,callback)

        val valtanButton = findViewById<RaidButtonView>(R.id.valtan_button)
        val biackissButton = findViewById<RaidButtonView>(R.id.biackiss_button)
        val koukusatonButton = findViewById<RaidButtonView>(R.id.koukusaton_button)
        val abrelshudButton = findViewById<RaidButtonView>(R.id.abrelshud_button)
        val illiakanButton = findViewById<RaidButtonView>(R.id.illiakan_button)
        val kayangelButton = findViewById<RaidButtonView>(R.id.kayangel_button)
        val ivorytowerButton = findViewById<RaidButtonView>(R.id.ivorytower_button)
        valtanButton.ClickEvent(Intent(this,RaidDetailActivity::class.java).putExtra("RaidName","valtan"))
        biackissButton.ClickEvent(Intent(this,RaidDetailActivity::class.java).putExtra("RaidName","biackiss"))
        koukusatonButton.ClickEvent(Intent(this,RaidDetailActivity::class.java).putExtra("RaidName","koukusaton"))
        abrelshudButton.ClickEvent(Intent(this,RaidDifficultyActivity::class.java).putExtra("RaidName","abrelshud"))
        illiakanButton.ClickEvent(Intent(this,RaidDifficultyActivity::class.java).putExtra("RaidName","illiakan"))
        kayangelButton.ClickEvent(Intent(this,RaidDetailActivity::class.java).putExtra("RaidName","kayangel"))
        ivorytowerButton.ClickEvent(Intent(this,RaidDetailActivity::class.java).putExtra("RaidName","ivorytower"))
    }
}