package com.lostark.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lostark.dto.armorys.Rune
import com.lostark.dto.armorys.tooltips.*
import com.lostark.loahelper.R

class CharSearchRuneView : LinearLayout {

    lateinit var runeImageView: ImageView
    lateinit var runeNameView: TextView


    lateinit var runeName:String
    lateinit var imageUrl:String
    lateinit var runeGrade:String
    lateinit var runeDescription:String

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.char_search_detail_rune_view, this, false)
        runeImageView = view.findViewById(R.id.char_search_detail_skill_rune_image)
        runeNameView = view.findViewById(R.id.char_search_detail_skill_rune_name)
        addView(view)


    }
    fun setRuneImageBackground(grade : String){//룬, 보석
        when(grade) {//이미지 백그라운드
            "전설" -> runeImageView.setBackgroundResource(R.drawable.legend_background)
            "영웅" -> runeImageView.setBackgroundResource(R.drawable.hero_background)
            "희귀" -> runeImageView.setBackgroundResource(R.drawable.rare_background)
            "고급" -> runeImageView.setBackgroundResource(R.drawable.advanced_background)
        }
    }

    fun setRuneImageText(rune: Rune, tooltip: Tooltip){
        Glide.with(this)
            .load(rune.icon)
            .into(runeImageView)
        imageUrl = rune.icon
        runeGrade = rune.grade+" 룬"
        setRuneImageBackground(rune.grade)

        runeNameView.text = rune.name
        runeNameView.visibility= View.VISIBLE
        runeName = rune.name
        runeDescription = (tooltip.elements.get("Element_002")?.value as ItemPartData).element1 //스킬 룬 설명

    }
}