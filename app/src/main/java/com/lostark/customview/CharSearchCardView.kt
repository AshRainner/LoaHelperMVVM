package com.lostark.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.lostark.dto.armorys.ArmoryEquipment
import com.lostark.dto.armorys.Card
import com.lostark.dto.armorys.Gem
import com.lostark.dto.armorys.tooltips.*
import com.lostark.loahelper.R

class CharSearchCardView : LinearLayout {

    lateinit var cardGradeImage: ImageView
    lateinit var cardImage: ImageView
    lateinit var cardLevel: TextView
    lateinit var cardNameGra: ImageView
    lateinit var cardNameView: TextView

    lateinit var card: Card
    lateinit var cardDescription:String
    lateinit var imageUrl:String

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.char_search_detail_ability_card_view, this, false)
        addView(view)
        cardGradeImage = findViewById(R.id.char_search_detail_card_grade_image)
        cardImage = findViewById(R.id.char_search_detail_card_image)
        cardLevel = findViewById(R.id.char_search_detail_card_awakening_level)
        cardNameView = findViewById(R.id.char_search_detail_card_name)
        cardNameGra = findViewById(R.id.char_search_detail_card_name_gra)

    }
    fun setImageBackground(grade : String){
        when(grade) {//이미지 백그라운드
            "전설" -> {
                cardGradeImage.setBackgroundResource(R.drawable.card_legend_background)
            }
            "영웅" -> {
                cardGradeImage.setBackgroundResource(R.drawable.card_hero_background)

            }
            "희귀" -> {
                cardGradeImage.setBackgroundResource(R.drawable.card_rare_background)
            }
            "고급" -> {
                cardGradeImage.setBackgroundResource(R.drawable.card_advanced_background)
            }
            "일반" -> {
                cardGradeImage.setBackgroundResource(R.drawable.card_common_background)
            }
        }
    }
    fun setCardImageText(card: Card, tooltip: Tooltip){
        Glide.with(this)
            .load(card.icon)
            .into(cardImage)
        println("카드 이미지 : "+card.icon)
        cardNameGra.setBackgroundResource(R.drawable.card_name_gra)
        imageUrl = card.icon
        setImageBackground(card.grade)
        cardNameView.text = card.name
        if(card.awakeCount != 0)
            cardLevel.text = card.awakeCount.toString()

        this.card = card

        cardDescription = tooltip.elements.get("Element_002")?.value as String

    }
    fun setCardImageText(card: Card){
        Glide.with(this)
            .load(card.icon)
            .into(cardImage)
        println("카드 이미지 : "+card.icon)
        imageUrl = card.icon
        setImageBackground(card.grade)
        if(card.awakeCount != 0) {
            cardLevel.text = card.awakeCount.toString()
            cardLevel.setTextSize(TypedValue.COMPLEX_UNIT_PT,12f)
            val params = cardLevel.layoutParams as ViewGroup.MarginLayoutParams
            val leftMargin = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 68f, resources.displayMetrics
            ).toInt()
            params.leftMargin = leftMargin
            cardLevel.layoutParams = params
        }


    }
}