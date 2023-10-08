package com.lostark.loahelper.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.lostark.loahelper.customview.RaidButtonView
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.RaidActivityBinding
import com.lostark.loahelper.databinding.RaidDetailActivityBinding

class RaidActivity : BaseActivity<RaidActivityBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(RaidActivityBinding::inflate)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
                overridePendingTransition(0, 0)
            }
        }

        this.onBackPressedDispatcher.addCallback(this,callback)
        binding.run {
            valtanButton.ClickEvent(
                Intent(
                    this@RaidActivity,
                    RaidDetailActivity::class.java
                ).putExtra("RaidName", "valtan")
            )
            biackissButton.ClickEvent(
                Intent(
                    this@RaidActivity,
                    RaidDetailActivity::class.java
                ).putExtra("RaidName", "biackiss")
            )
            koukusatonButton.ClickEvent(
                Intent(
                    this@RaidActivity,
                    RaidDetailActivity::class.java
                ).putExtra("RaidName", "koukusaton")
            )
            abrelshudButton.ClickEvent(
                Intent(
                    this@RaidActivity,
                    RaidDifficultyActivity::class.java
                ).putExtra("RaidName", "abrelshud")
            )
            illiakanButton.ClickEvent(
                Intent(
                    this@RaidActivity,
                    RaidDifficultyActivity::class.java
                ).putExtra("RaidName", "illiakan")
            )
            kayangelButton.ClickEvent(
                Intent(
                    this@RaidActivity,
                    RaidDetailActivity::class.java
                ).putExtra("RaidName", "kayangel")
            )
            ivorytowerButton.ClickEvent(
                Intent(
                    this@RaidActivity,
                    RaidDetailActivity::class.java
                ).putExtra("RaidName", "ivorytower")
            )
        }
    }
}