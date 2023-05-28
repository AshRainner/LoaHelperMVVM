package com.lostark.customview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import com.lostark.loahelper.R
import com.lostark.searchablespinnerlibrary.SearchableSpinner

class AccessoryView : LinearLayout {
    private lateinit var imageView : ImageView
    private lateinit var engravingSpinner:Array<SearchableSpinner>
    private lateinit var engravingPlusMinusSpinner:Array<Spinner>

    constructor(context: Context?) : super(context){
        init(context)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context,attrs){
        init(context)
        getAttrs(attrs)
    }

    private fun init(context: Context?){
        val view = LayoutInflater.from(context).inflate(R.layout.engraving_accessory_view,this,false)
        addView(view)
        engravingSpinner = arrayOf(
            findViewById(R.id.engraving_spinner_one),
            findViewById(R.id.engraving_spinner_two),
            findViewById(R.id.engraving_spinner_three))
        engravingPlusMinusSpinner= arrayOf(
            findViewById(R.id.engraving_spinner_one_plus),
            findViewById(R.id.engraving_spinner_two_plus),
            findViewById(R.id.engraving_spinner_three_minus)
        )
        val plusValue = arrayOf("","+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8", "+9", "+10")
        val plusSpinnerAdapter = ArrayAdapter(context!!, R.layout.engraving_spinner_item, plusValue)
        plusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        engravingPlusMinusSpinner.forEach {it.adapter=plusSpinnerAdapter}

        val minusValue = arrayOf("","-1","-2","-3","-4")
        val minusSpinnerAdapter = ArrayAdapter(context!!, R.layout.engraving_spinner_item, minusValue)
        minusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        engravingPlusMinusSpinner[2].adapter=minusSpinnerAdapter

        val engraving = resources.getStringArray(R.array.engraving)
        val engravingSpinnerAdapter =
            ArrayAdapter<String>(context!!, R.layout.engraving_spinner_item, engraving)
        engravingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val minusEngraving = resources.getStringArray(R.array.engraving_minus)
        val minusEngravingSpinnerAdapter =
            ArrayAdapter(context!!,R.layout.engraving_spinner_item,minusEngraving)
        minusEngravingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        engravingSpinner.forEach { it.adapter=engravingSpinnerAdapter }
        engravingSpinner[2].adapter=minusEngravingSpinnerAdapter

        imageView = findViewById(R.id.accessory_image)
    }

    @SuppressLint("ResourceAsColor")
    private fun getAttrs(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.AccessoryViewAttr)
        imageView.setImageResource(typedArray.getResourceId(R.styleable.AccessoryViewAttr_accessoryImageSrc,R.drawable.necklace))
        imageView.setBackgroundResource(typedArray.getResourceId(R.styleable.AccessoryViewAttr_imageBackground,R.drawable.ancient_background))
        typedArray.recycle()
    }
}