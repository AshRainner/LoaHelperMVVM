package com.lostark.loahelper

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.lostark.customview.HomeButtonView
import com.lostark.customview.RaidButtonView

class RaidActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.raid_activity)
        val valtanButton = findViewById<RaidButtonView>(R.id.valtan_button)
        val biackissButton = findViewById<RaidButtonView>(R.id.biackiss_button)
        val koukusatonButton = findViewById<RaidButtonView>(R.id.koukusaton_button)
        valtanButton.ClickEvent(Intent(this,RaidDetailActivity::class.java).putExtra("RaidName","valtan"))
        biackissButton.ClickEvent(Intent(this,RaidDetailActivity::class.java).putExtra("RaidName","biackiss"))
        koukusatonButton.ClickEvent(Intent(this,RaidDetailActivity::class.java).putExtra("RaidName","koukusaton"))
    }

}