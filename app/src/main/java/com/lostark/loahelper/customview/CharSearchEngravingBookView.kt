package com.lostark.loahelper.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lostark.loahelper.R

class CharSearchEngravingBookView : LinearLayout {


    lateinit var engravingImage: ImageView
    lateinit var engravingName: TextView
    lateinit var engravingPoint: TextView

    lateinit var engravingStringList:List<String>
    lateinit var imageUrl:String

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
    fun setEngravingImageText(engraving: com.lostark.loahelper.dto.armorys.Engraving, tooltip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip){
        Glide.with(this)
            .load(engraving.icon)
            .circleCrop()
            .into(engravingImage)
        imageUrl = engraving.icon

        engravingName.text = tooltip.elements.get("Element_000")?.value.toString()
        val data = tooltip.elements.get("Element_001")?.value as com.lostark.loahelper.dto.armorys.tooltips.EngraveSkillTitleData
        engravingPoint.text = data.leftText.replace("각인 ","")

        var pattern = "레벨 \\d+[^레벨].*".toRegex(RegexOption.MULTILINE)
        engravingStringList =  pattern.findAll((tooltip.elements.get("Element_003")?.value as com.lostark.loahelper.dto.armorys.tooltips.ItemPartData).element1).map { it.value }.toList()
        /*println(engravingStringList)
        pattern.findAll((tooltip.elements.get("Element_003")?.value as ItemPartData).element1).forEach {
            println(it.value)
        }*/
    }
}