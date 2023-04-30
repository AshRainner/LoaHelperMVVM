package com.lostark.loahelper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.lostark.customview.HomeButtonView
import com.lostark.loahelper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var raidButton = findViewById<HomeButtonView>(R.id.home_raid_button)
        var craftButton = findViewById<HomeButtonView>(R.id.home_craft_button)
        var dailyButton = findViewById<HomeButtonView>(R.id.home_daily_button)
        var engravingButton = findViewById<HomeButtonView>(R.id.home_engraving_button)
        var upadateButton = findViewById<HomeButtonView>(R.id.home_update_button)
        var mokokoButton = findViewById<HomeButtonView>(R.id.home_mokoko_button)

        raidButton.ClickEvent(Intent(this,RaidActivity::class.java))
    }

}