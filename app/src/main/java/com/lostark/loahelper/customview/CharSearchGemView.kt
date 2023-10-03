package com.lostark.loahelper.customview

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.lostark.loahelper.R

class CharSearchGemView : LinearLayout {

    lateinit var gemCardView: MaterialCardView
    lateinit var gemImage: ImageView
    lateinit var gemLevel: TextView
    lateinit var defaultGemView: ImageView

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
        gemLevel = findViewById(R.id.char_search_detail_gem_level)
        defaultGemView = findViewById(R.id.char_search_detail_gem_image_default)


    }

    fun goneDefaultGem(){
        defaultGemView.visibility = View.GONE
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
    fun setGemImageText(gem: com.lostark.loahelper.dto.armorys.Gem, tooltip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip){
        defaultGemView.visibility=View.GONE
        Glide.with(this)
            .load(gem.icon)
            .into(gemImage)
        imageUrl = gem.icon
        setImageBackground(gem.grade)
        gemName = tooltip.elements.get("Element_000")?.value.toString()
        gemGrade = (tooltip.elements.get("Element_001")?.value as com.lostark.loahelper.dto.armorys.tooltips.ItemTitleData).leftStr0
        gemTier = " | "+(tooltip.elements.get("Element_001")?.value as com.lostark.loahelper.dto.armorys.tooltips.ItemTitleData).leftStr2.replace("아이템 ","")
        var pattern = "].*".toRegex()
        val itemPartBoxKeys =
            tooltip.elements.filter { it.value.type == "ItemPartBox" }.keys.toList()
        for(key in itemPartBoxKeys){
            val itemPartBoxData = tooltip.elements.get(key)?.value as com.lostark.loahelper.dto.armorys.tooltips.ItemPartData
            gemDetail = pattern.find(itemPartBoxData.element1)?.value?.replace("] ","[")?.replace(" 피해","] 피해")?.replace(" 재사용","] 재사용")
        }
        gemLevel.visibility=View.VISIBLE
        gemLevel.text = gem.level.toString()

    }
    fun setSkillGemImageText(gem: com.lostark.loahelper.dto.armorys.Gem, tooltip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip){
        setGemImageText(gem,tooltip)
        Glide.with(this)
            .load(gem.icon)
            .circleCrop()
            .into(gemImage)
        val cornerRadiusDp = 20f // 변경하려는 코너 반지름 값 (dp)
        val cornerRadiusPx = (cornerRadiusDp * resources.displayMetrics.density).toInt()

        gemCardView.radius = cornerRadiusPx.toFloat()
        val textSizeInPt = 15f // 변경하려는 텍스트 크기 값 (pt)
        val scaledDensity = resources.displayMetrics.scaledDensity
        val textSizeInPx = (textSizeInPt * scaledDensity)

        gemLevel.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeInPx)

    }
}