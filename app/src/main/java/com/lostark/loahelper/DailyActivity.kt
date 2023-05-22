package com.lostark.loahelper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import com.lostark.customview.DailyItemView
import com.lostark.customview.RaidButtonView

class DailyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_activity)
        var itemGrid = findViewById<GridLayout>(R.id.daily_item_grid)
        itemGrid.forEachIndexed() { index, item ->
            item as DailyItemView
            item.setPrice(index*10.1)
        }
    }

}