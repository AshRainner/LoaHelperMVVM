package com.lostark.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lostark.dto.armorys.Engraving
import com.lostark.dto.armorys.tooltips.Tooltip
import com.lostark.loahelper.R

class CharSearchEngravingBookView : LinearLayout {


    lateinit var engravingImage: ImageView
    lateinit var engravingName: TextView
    lateinit var engravingPoint: TextView

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
        getAttrs(attrs)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.char_search_detail_ability_engraving_book_view, this, false)
        engravingImage = view.findViewById(R.id.char_search_detail_engraving_image)
        engravingName = view.findViewById(R.id.char_search_detail_engraving_name)
        engravingPoint = view.findViewById(R.id.char_search_detail_engraving_point)
        addView(view)

    }
    private fun getAttrs(attrs: AttributeSet?) {

    }
    fun setEngravingImageText(engraving:Engraving,tooltip: Tooltip){
        Glide.with(this)
            .load(engraving.icon)
            .circleCrop()
            .into(engravingImage)

        engravingName.text = tooltip.elements.get("Element_000")?.value.toString()
        //engravingPoint.text = tooltip.elements.get("Element_001")?.value

    }
}