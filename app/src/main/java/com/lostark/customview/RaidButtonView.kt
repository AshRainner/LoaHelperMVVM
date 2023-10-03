package com.lostark.customview

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import com.lostark.loahelper.R

class RaidButtonView : RelativeLayout {
    private lateinit var layout : RelativeLayout
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
        val view = LayoutInflater.from(context).inflate(R.layout.raid_button_view,this,false)
        addView(view)
        layout = findViewById(R.id.raid_button_layout)
        textButton = findViewById(R.id.raid_button)
        imageView = findViewById(R.id.raid_button_image)
        meterialCard = findViewById(R.id.raid_meterial_card)
    }

    private fun getAttrs(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.RaidButtonViewAttr)
        textButton.setText(typedArray.getText(R.styleable.RaidButtonViewAttr_raidButtonText))
        imageView.setImageResource(typedArray.getResourceId(R.styleable.RaidButtonViewAttr_raidButtonImageSrc,R.drawable.raid_icon))
        typedArray.recycle()
    }
    public fun ClickEvent(intent: Intent){
        textButton.setOnClickListener() {
            context.startActivity(intent)
        }
        imageView.setOnClickListener(){
            context.startActivity(intent)
        }
    }
}