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
import com.lostark.loahelper.databinding.CharSearchDetailAbilityEngravingViewBinding
import com.lostark.loahelper.databinding.CharSearchDetailAbilityGemViewBinding
import com.lostark.loahelper.dto.armorys.Gem
import com.lostark.loahelper.dto.armorys.tooltips.Tooltip

class CharSearchGemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailAbilityGemViewBinding>(
    context,
    attrs,
    defStyleAttr
) {

    lateinit var gemName: String
    lateinit var gemTier: String
    var gemDetail: String? = null
    lateinit var gemGrade: String

    lateinit var imageUrl:String

    override fun init(context: Context?) {

    }
    fun setImageBackground(grade : String){
        binding.run {
            when (grade) {//이미지 백그라운드
                "고대" -> {
                    charSearchDetailGemImage.setBackgroundResource(R.drawable.ancient_background)
                }
                "유물" -> {
                    charSearchDetailGemImage.setBackgroundResource(R.drawable.relic_background)

                }
                "전설" -> {
                    charSearchDetailGemImage.setBackgroundResource(R.drawable.legend_background)
                }
                "영웅" -> {
                    charSearchDetailGemImage.setBackgroundResource(R.drawable.hero_background)

                }
                "희귀" -> {
                    charSearchDetailGemImage.setBackgroundResource(R.drawable.rare_background)
                }
                "고급" -> {
                    charSearchDetailGemImage.setBackgroundResource(R.drawable.advanced_background)
                }
            }
        }
    }
    fun setGemImageText(gem:Gem, tooltip:Tooltip) {
        binding.run {
            Glide.with(this@CharSearchGemView)
                .load(gem.icon)
                .into(charSearchDetailGemImage)
            charSearchDetailGemTextCard.visibility=visibility
            imageUrl = gem.icon
            setImageBackground(gem.grade)
            gemName = tooltip.elements.get("Element_000")?.value.toString()
            gemGrade =
                (tooltip.elements.get("Element_001")?.value as com.lostark.loahelper.dto.armorys.tooltips.ItemTitleData).leftStr0
            gemTier =
                " | " + (tooltip.elements.get("Element_001")?.value as com.lostark.loahelper.dto.armorys.tooltips.ItemTitleData).leftStr2.replace(
                    "아이템 ",
                    ""
                )
            var pattern = "].*".toRegex()
            val itemPartBoxKeys =
                tooltip.elements.filter { it.value.type == "ItemPartBox" }.keys.toList()
            for (key in itemPartBoxKeys) {
                val itemPartBoxData =
                    tooltip.elements.get(key)?.value as com.lostark.loahelper.dto.armorys.tooltips.ItemPartData
                gemDetail = pattern.find(itemPartBoxData.element1)?.value?.replace("] ", "[")
                    ?.replace(" 피해", "] 피해")?.replace(" 재사용", "] 재사용")
            }
            charSearchDetailGemLevel.visibility = View.VISIBLE
            charSearchDetailGemLevel.text = gem.level.toString()

        }
    }
        fun setSkillGemImageText(
            gem: Gem,
            tooltip: Tooltip
        ) {
            binding.run {
                setGemImageText(gem, tooltip)
                Glide.with(this@CharSearchGemView)
                    .load(gem.icon)
                    .into(charSearchDetailGemImage)
                /*val cornerRadiusDp = 20f // 변경하려는 코너 반지름 값 (dp)
                val cornerRadiusPx = (cornerRadiusDp * resources.displayMetrics.density).toInt()
                charSearchDetailGemCardView.radius = cornerRadiusPx.toFloat()*/
                val textSizeInPt = 15f // 변경하려는 텍스트 크기 값 (pt)
                val scaledDensity = resources.displayMetrics.scaledDensity
                val textSizeInPx = (textSizeInPt * scaledDensity)

                charSearchDetailGemLevel.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeInPx)
                charSearchDetailGemTextCard.visibility=visibility
            }
        }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 여기가 핵심!
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
    override fun getAttrs(attrs: AttributeSet?) {

    }

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailAbilityGemViewBinding {
        return CharSearchDetailAbilityGemViewBinding.inflate(inflater)
    }
}