package com.lostark.customview

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lostark.loahelper.R

class HomeButtonView : LinearLayout {
    private lateinit var layout : LinearLayout
    private lateinit var textButton : Button
    private lateinit var imageView : ImageView
    private lateinit var meterialCard : com.google.android.material.card.MaterialCardView

    constructor(context: Context?) : super(context){
        init(context)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context,attrs){
        init(context)
        getAttrs(attrs)
    }

    private fun init(context: Context?){
        val view = LayoutInflater.from(context).inflate(R.layout.home_button_view,this,false)
        addView(view)
        layout = findViewById(R.id.home_button_layout)
        textButton = findViewById(R.id.home_button)
        imageView = findViewById(R.id.home_button_image)
        meterialCard = findViewById(R.id.home_meterial_card)
    }

    private fun getAttrs(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.HomeButtonViewAttr)
        textButton.setText(typedArray.getText(R.styleable.HomeButtonViewAttr_buttonText))
        textButton.setOnClickListener{
            Log.d("!@#","!@#")
        }
        imageView.setImageResource(typedArray.getResourceId(R.styleable.HomeButtonViewAttr_buttonImageSrc,R.drawable.raid_icon))
        typedArray.recycle()
    }
    public fun ClickEvent(intent: Intent){
        textButton.setOnClickListener() {
            context.startActivity(intent)
        }
    }
}