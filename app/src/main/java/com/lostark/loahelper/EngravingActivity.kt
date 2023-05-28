package com.lostark.loahelper

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.lostark.callbackinterface.SpinnerChangedCallback
import com.lostark.customview.AccessoryView
import com.lostark.customview.BookView
import com.lostark.customview.SeletedEngravingView

class EngravingActivity : AppCompatActivity(), SpinnerChangedCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.engraving_activity)
        spinnerSet()
    }

    fun spinnerSet(){
        val necklaceView = findViewById<AccessoryView>(R.id.neck_lace_view)
        val earringView1 = findViewById<AccessoryView>(R.id.ear_ring_view_1)
        val earringView2 = findViewById<AccessoryView>(R.id.ear_ring_view_2)
        val ringView1 = findViewById<AccessoryView>(R.id.ring_view_1)
        val ringView2 = findViewById<AccessoryView>(R.id.ring_view_2)
        val abilityStoneView = findViewById<AccessoryView>(R.id.ability_view)
        val engravingBookView1 = findViewById<BookView>(R.id.book_view_1)
        val engravingBookView2 = findViewById<BookView>(R.id.book_view_2)
        necklaceView.setSpinnerChangedCallback(this)
        earringView1.setSpinnerChangedCallback(this)
        earringView2.setSpinnerChangedCallback(this)
        ringView1.setSpinnerChangedCallback(this)
        ringView2.setSpinnerChangedCallback(this)
        abilityStoneView.setSpinnerChangedCallback(this)
        engravingBookView1.setSpinnerChangedCallback(this)
        engravingBookView2.setSpinnerChangedCallback(this)
    }

    override fun onEngravingSpinnerChanged(name: String, value: String) {
        Log.d(name+":"+value, "onEngravingSpinnerChanged: ")
        //addSelectedEngravingView(value)
    }

    override fun onPlusMinusSpinnerChanged(name: String, value: String) {
        Log.d(name+":"+value, "onPlusMinusSpinnerChanged: ")
    }

    fun addSelectedEngravingView(value:String){
        if(value!="각인"&&value!="감소 각인") {
            val selectedEngravingView = SeletedEngravingView(this)
            selectedEngravingView.engravingText.text = value
            val layout = findViewById<LinearLayout>(R.id.selected_engraving_view_layout)
            layout.addView(selectedEngravingView)
        }
    }
    fun changedSeletedOval(name:String,value:String){

    }

}