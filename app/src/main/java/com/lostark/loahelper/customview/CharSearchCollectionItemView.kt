package com.lostark.loahelper.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import com.lostark.loahelper.R

class CharSearchCollectionItemView : LinearLayout {

    lateinit var collectionHaveText:TextView
    lateinit var collectionName:TextView
    lateinit var percentText:TextView
    lateinit var progressBar: RoundCornerProgressBar
    lateinit var backgroundImageView:ImageView
    lateinit var mainLayout:LinearLayout

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
        getAttrs(attrs)
    }

    private fun getAttrs(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.CollectionItemViewAttr)
        collectionName.text = typedArray.getText(R.styleable.CollectionItemViewAttr_collectionName)
        typedArray.recycle()
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.char_search_detail_collection_item_view, this, false)
        addView(view)
        collectionHaveText = view.findViewById(R.id.collection_have_item)
        collectionName = view.findViewById(R.id.collection_name)
        percentText = view.findViewById(R.id.collection_percent)
        progressBar = view.findViewById(R.id.collection_achievement_progress)
        backgroundImageView = view.findViewById(R.id.collection_background_image)
        mainLayout = view.findViewById(R.id.collection_item_layout)
    }

    fun selected(select:Boolean){
        if (select){
            progressBar.setProgressColor(Color.parseColor("#f0f0f0"))
            collectionName.setTextColor(Color.parseColor("#ffffff"))
            percentText.setTextColor(Color.parseColor("#ffffff"))
            collectionHaveText.setTextColor(Color.parseColor("#ffffff"))
            backgroundImageView.visibility=View.VISIBLE
            mainLayout.setBackgroundColor(Color.parseColor("#808b00ff"))
        }
        else{
            progressBar.setProgressColor(Color.parseColor("#8b00ff"))
            collectionName.setTextColor(Color.parseColor("#000000"))
            percentText.setTextColor(Color.parseColor("#000000"))
            collectionHaveText.setTextColor(Color.parseColor("#000000"))
            backgroundImageView.visibility=View.INVISIBLE
            mainLayout.setBackgroundColor(Color.parseColor("#008b00ff"))
        }

    }
}