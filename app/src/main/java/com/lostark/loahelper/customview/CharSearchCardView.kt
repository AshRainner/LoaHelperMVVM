package com.lostark.loahelper.customview

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchDetailAbilityAccessoryViewBinding
import com.lostark.loahelper.databinding.CharSearchDetailAbilityCardViewBinding
import com.lostark.loahelper.dto.armorys.Card

class CharSearchCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailAbilityCardViewBinding>(
    context,
    attrs,
    defStyleAttr
) {
    lateinit var card: Card
    lateinit var cardDescription:String
    lateinit var imageUrl:String


    override fun init(context: Context?) {

    }
    fun setImageBackground(grade : String){
        binding.run {
            when (grade) {//이미지 백그라운드
                "전설" -> {
                    charSearchDetailCardGradeImage.setBackgroundResource(R.drawable.card_legend_background)
                }
                "영웅" -> {
                    charSearchDetailCardGradeImage.setBackgroundResource(R.drawable.card_hero_background)

                }
                "희귀" -> {
                    charSearchDetailCardGradeImage.setBackgroundResource(R.drawable.card_rare_background)
                }
                "고급" -> {
                    charSearchDetailCardGradeImage.setBackgroundResource(R.drawable.card_advanced_background)
                }
                "일반" -> {
                    charSearchDetailCardGradeImage.setBackgroundResource(R.drawable.card_common_background)
                }
            }
        }
    }
    fun setCardImageText(card:Card, tooltip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip){
        binding.run {
            Glide.with(this@CharSearchCardView)
                .load(card.icon)
                .into(charSearchDetailCardImage)
            println("카드 이미지 : " + card.icon)
            charSearchDetailCardNameGra.setBackgroundResource(R.drawable.card_name_gra)
            imageUrl = card.icon
            setImageBackground(card.grade)
            charSearchDetailCardName.text = card.name
            if (card.awakeCount != 0)
                charSearchDetailCardAwakeningLevel.text = card.awakeCount.toString()
            cardDescription = tooltip.elements.get("Element_002")?.value as String
        }
    }
    fun setCardImageText(card:Card){
        binding.run {
            Glide.with(this@CharSearchCardView)
                .load(card.icon)
                .into(charSearchDetailCardImage)
            imageUrl = card.icon
            setImageBackground(card.grade)
            if (card.awakeCount != 0) {
                charSearchDetailCardAwakeningLevel.text = card.awakeCount.toString()
                charSearchDetailCardAwakeningLevel.setTextSize(TypedValue.COMPLEX_UNIT_PT, 12f)
                val params = charSearchDetailCardAwakeningLevel.layoutParams as ViewGroup.MarginLayoutParams
                val leftMargin = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 68f, resources.displayMetrics
                ).toInt()
                params.leftMargin = leftMargin
                charSearchDetailCardAwakeningLevel.layoutParams = params
            }
        }
    }

    fun getCardName() = binding.charSearchDetailCardName

    override fun getAttrs(attrs: AttributeSet?) {
    }

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailAbilityCardViewBinding {
        return CharSearchDetailAbilityCardViewBinding.inflate(inflater)
    }
}