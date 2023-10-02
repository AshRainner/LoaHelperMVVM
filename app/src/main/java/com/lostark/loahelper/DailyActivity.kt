package com.lostark.loahelper

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.MotionEvent
import android.view.View
import android.view.View.OnKeyListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.GridLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.lostark.customview.DailyGuardianView
import com.lostark.customview.DailyItemView
import com.lostark.database.table.Items
import kotlin.math.round


class DailyActivity : AppCompatActivity() {

    val rewardList = arrayOf(
        arrayOf(0,22,200), //데칼
        arrayOf(0,32,270), //쿤겔
        arrayOf(1,20,150), //칼엘
        arrayOf(1,28,200), //하누
        arrayOf(2,16,150), //소나벨
        arrayOf(2,24,200) //가르가디스
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_activity)

        val restCheckBox = findViewById<CheckBox>(R.id.rest_check_box)
        val guardianGrid = findViewById<GridLayout>(R.id.daily_guardian_grid)
        val itemGrid = findViewById<GridLayout>(R.id.daily_item_grid)
        val stoneList = intent.parcelableArrayList<Items>("StoneList")!!
        stoneList?.forEach { Log.d(it.name, "onCreate: ") }
        val destructionList = intent.parcelableArrayList<Items>("Destruction")!!
        var restDivide = 2
        restCheckBox.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked){
                restDivide = 1
            }
            else{
                restDivide = 2
            }
            rewardList.forEachIndexed { index, item ->
                val gridItem = guardianGrid[index] as DailyGuardianView
                val stone = itemGrid[item[0]] as DailyItemView
                val destruction = itemGrid[item[0]+3] as DailyItemView
                if(destruction.getEditText().text.toString()!=""&&stone.getEditText().text.toString()!="") {
                    val price = (stone.getEditText().text.toString()
                        .toDouble() * (item[1] / restDivide)) + ((destruction.getEditText().text.toString()
                        .toDouble() * (item[2] / restDivide)) / 10)
                    //명돌 + 파괴석들 파괴석은 10개 단위임
                    gridItem.setPrice(round(price * 10) / 10)
                }
            }
        }

        val textWatcher = object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                    rewardList.forEachIndexed { index, item ->
                        val gridItem = guardianGrid[index] as DailyGuardianView
                        val stone = itemGrid[item[0]] as DailyItemView
                        val destruction = itemGrid[item[0] + 3] as DailyItemView
                        if(destruction.getEditText().text.toString()!=""&&stone.getEditText().text.toString()!="") {
                            val price = (stone.getEditText().text.toString()
                                .toDouble() * (item[1] / restDivide)) + ((destruction.getEditText().text.toString()
                                .toDouble() * (item[2] / restDivide)) / 10)
                            //명돌 + 파괴석들 파괴석은 10개 단위임
                            gridItem.setPrice(round(price * 10) / 10)
                        }
                    }
            }
        }
        stoneList.forEachIndexed(){index, item ->
            val gridItem = itemGrid[index] as DailyItemView
            gridItem.setPrice(item.yDayAvgPrice)
            gridItem.setImage(item.iconUrl)
            gridItem.getEditText().addTextChangedListener(textWatcher)
        }
        destructionList.forEachIndexed(){index, item ->
            val gridItem = itemGrid[index + stoneList?.size!!] as DailyItemView
            gridItem.setPrice(item.yDayAvgPrice)
            gridItem.setImage(item.iconUrl)
            gridItem.getEditText().addTextChangedListener(textWatcher)
        }
        rewardList.forEachIndexed { index, item ->
            val gridItem = guardianGrid[index] as DailyGuardianView
            val stone = itemGrid[item[0]] as DailyItemView
            val destruction = itemGrid[item[0]+3] as DailyItemView
            val price = (stone.getEditText().text.toString().toDouble() * (item[1]/restDivide))+((destruction.getEditText().text.toString().toDouble() * (item[2]/restDivide))/10)
            //명돌 + 파괴석들 파괴석은 10개 단위임
            gridItem.setPrice(round(price*10)/10)
        }

    }
    inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
    }

}