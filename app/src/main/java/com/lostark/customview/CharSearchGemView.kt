package com.lostark.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.lostark.dto.armorys.ArmoryEquipment
import com.lostark.dto.armorys.Gem
import com.lostark.dto.armorys.tooltips.*
import com.lostark.loahelper.R

class CharSearchGemView : LinearLayout {

    lateinit var gemCardView: MaterialCardView
    lateinit var gemImage: ImageView
    lateinit var gemName: String
    lateinit var gemTier: String
    var gemDetail: String? = null
    lateinit var gemGrade: String

    lateinit var imageUrl:String

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.char_search_detail_ability_gem_view, this, false)
        addView(view)
        gemImage = findViewById(R.id.char_search_detail_gem_image)
        gemCardView = findViewById(R.id.char_search_detail_gem_card_view)

    }
    fun setImageBackground(grade : String){
        when(grade) {//이미지 백그라운드
            "고대" -> {
                gemImage.setBackgroundResource(R.drawable.ancient_background)
            }
            "유물" -> {
                gemImage.setBackgroundResource(R.drawable.relic_background)

            }
            "전설" -> {
                gemImage.setBackgroundResource(R.drawable.legend_background)
            }
            "영웅" -> {
                gemImage.setBackgroundResource(R.drawable.hero_background)

            }
            "희귀" -> {
                gemImage.setBackgroundResource(R.drawable.rare_background)
            }
            "고급" -> {
                gemImage.setBackgroundResource(R.drawable.advanced_background)
            }
        }
    }
    fun setGemImageText(gem: Gem, tooltip: Tooltip){
        Glide.with(this)
            .load(gem.icon)
            .into(gemImage)
        imageUrl = gem.icon
        setImageBackground(gem.grade)
        gemName = tooltip.elements.get("Element_000")?.value.toString()
        gemGrade = (tooltip.elements.get("Element_001")?.value as ItemTitleData).leftStr0
        gemTier = " | "+(tooltip.elements.get("Element_001")?.value as ItemTitleData).leftStr2.replace("아이템 ","")
        var pattern = "].*".toRegex()
        val itemPartBoxKeys =
            tooltip.elements.filter { it.value.type == "ItemPartBox" }.keys.toList()
        for(key in itemPartBoxKeys){
            val itemPartBoxData = tooltip.elements.get(key)?.value as ItemPartData
            gemDetail = pattern.find(itemPartBoxData.element1)?.value?.replace("] ","[")?.replace(" 피해","] 피해")?.replace(" 재사용","] 재사용")
        }

    }
}