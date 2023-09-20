package com.lostark.customview

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.lostark.loahelper.R

class DailyItemView : RelativeLayout {
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
        val view = LayoutInflater.from(context).inflate(R.layout.daily_item_view,this,false)
        addView(view)
        layout = findViewById(R.id.daily_item_layout)
        priceEditText = findViewById(R.id.daily_item_price_edit)
        imageView = findViewById(R.id.daily_item_image)
        meterialCard = findViewById(R.id.daily_item_card)
    }

    private fun getAttrs(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.DailyItemViewAttr)
        priceEditText.setText(typedArray.getText(R.styleable.DailyItemViewAttr_itemPriceText))
        imageView.setImageResource(typedArray.getResourceId(R.styleable.DailyItemViewAttr_itemImageSrc,R.drawable.raid_icon))
        imageView.setBackgroundResource(typedArray.getResourceId(R.styleable.DailyItemViewAttr_itemImageBackground,0))
        typedArray.recycle()
    }
    public fun setPrice(price:Double){
        priceEditText.setText(price.toString())
    }
    public fun setImage(imageUrl:String){
        Glide.with(this).load(imageUrl).into(imageView)
    }
    public fun getEditText():EditText{
        return priceEditText
    }
}