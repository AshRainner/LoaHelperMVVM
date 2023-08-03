package com.lostark.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lostark.dto.armorys.Engraving
import com.lostark.dto.armorys.tooltips.EngraveSkillTitleData
import com.lostark.dto.armorys.tooltips.ItemPartData
import com.lostark.dto.armorys.tooltips.Tooltip
import com.lostark.loahelper.R

class CharSearchEngravingBottomView : LinearLayout {


    lateinit var engravingImage: ImageView
    lateinit var engravingName: TextView

    lateinit var engravingString:String
    lateinit var engravingDrawerName:String

    lateinit var imageUrl: String

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
        getAttrs(attrs)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.char_search_detail_ability_engraving_view, this, false)
        engravingImage = view.findViewById(R.id.char_search_detail_bottom_engraving_image)
        engravingName = view.findViewById(R.id.char_search_detail_bottom_engraving_name)
        addView(view)

    }
    private fun getAttrs(attrs: AttributeSet?) {

    }
    fun setEngravingImageText(name:String,description: String,level:String,imageUrl:String){
        Glide.with(this)
            .load(imageUrl)
            .circleCrop()
            .into(engravingImage)
        this.imageUrl = imageUrl

        engravingString = description

        engravingName.text = level+" "+name

        engravingDrawerName =name+" Lv."+level
        println("이름 : "+name)
        if(name.contains("감소"))
            engravingName.setTextColor(Color.parseColor("#FF0044"))
    }
}