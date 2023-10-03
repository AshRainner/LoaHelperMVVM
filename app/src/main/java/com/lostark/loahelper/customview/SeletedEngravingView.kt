package com.lostark.loahelper.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lostark.loahelper.R

class SeletedEngravingView : LinearLayout {

    lateinit var imageInLayout: LinearLayout
    lateinit var engravingText: TextView
    lateinit var circleImageView: Array<ImageView>

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
        getAttrs(attrs)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.selected_engraving_view, this, false)
        addView(view)
        imageInLayout = findViewById(R.id.selected_engraving_layout)
        engravingText = findViewById(R.id.selected_engraving_text)

        circleImageView = Array(imageInLayout.childCount) { index ->
            imageInLayout.getChildAt(index) as ImageView
        }

    }

    @SuppressLint("ResourceAsColor")
    private fun getAttrs(attrs: AttributeSet?) {
    }

    fun setImage(count: Int) {
        circleImageView.forEach { it.setImageResource(R.drawable.non_seleted_circle_shpae) }
        if (count >= 0) {
            var max: Int = if (count > 15)
                15
            else
                count
            for (i in 0..max - 1)
                circleImageView[i].setImageResource(R.drawable.seleted_circle_shpae)
        }
        else{
            var absoluteCount = count*-1
            var max: Int = if (absoluteCount > 15)
                15
            else
                absoluteCount
            for (i in 0..max - 1)
                circleImageView[i].setImageResource(R.drawable.minus_seleted_circle_shpae)
        }
    }
}