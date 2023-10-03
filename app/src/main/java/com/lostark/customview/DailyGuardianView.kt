package com.lostark.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import com.bumptech.glide.Glide
import com.lostark.loahelper.R

class DailyGuardianView : RelativeLayout {
    private lateinit var layout : RelativeLayout
    private lateinit var priceEditText : EditText
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
        val view = LayoutInflater.from(context).inflate(R.layout.daily_guardian_view,this,false)
        addView(view)
        layout = findViewById(R.id.daily_guardian_layout)
        priceEditText = findViewById(R.id.daily_guardian_price_edit)
        imageView = findViewById(R.id.daily_guardian_image)
        meterialCard = findViewById(R.id.daily_guardian_card)
    }

    private fun getAttrs(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.DailyGuardianViewAttr)
        priceEditText.setText(typedArray.getText(R.styleable.DailyGuardianViewAttr_guardianPriceText))
        imageView.setImageResource(typedArray.getResourceId(R.styleable.DailyGuardianViewAttr_guardianImageSrc,R.drawable.raid_icon))
        typedArray.recycle()
    }
    public fun setPrice(price:Double){
        priceEditText.setText(price.toString())
    }
    public fun setImage(imageUrl:String){
        Glide.with(this).load(imageUrl).into(imageView)
    }
}