package com.lostark.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.lostark.dto.armorys.ArmoryEquipment
import com.lostark.dto.armorys.tooltips.ItemPartData
import com.lostark.dto.armorys.tooltips.Tooltip
import com.lostark.dto.characters.CharactersInfo
import com.lostark.loahelper.R

class CharSearchCollectionEquipmentView : LinearLayout {

    lateinit var collectionEquipmentImageView: ImageView
    lateinit var collectionEquipmentTextView:TextView


    lateinit var itemName:String
    lateinit var imageUrl:String
    lateinit var itemGrade:String
    lateinit var effectString:String
    lateinit var descriptionString:String

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }


    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.char_search_detail_collection_equ_item, this, false)
        addView(view)
        collectionEquipmentImageView = view.findViewById(R.id.char_search_detail_collection_equ_image)
        collectionEquipmentTextView = view.findViewById(R.id.char_search_detail_collection_equ_text)

    }

    fun setBackground(grade:String){
        when(grade) {//이미지 백그라운드
            "고대" -> {
                collectionEquipmentImageView.setBackgroundResource(R.drawable.ancient_background)
                collectionEquipmentTextView.setTextColor(Color.parseColor("#d9ae43"))
            }
            "유물" -> {
                collectionEquipmentImageView.setBackgroundResource(R.drawable.relic_background)
                collectionEquipmentTextView.setTextColor(Color.parseColor("#E45B0A"))

            }
            "전설" -> {
                collectionEquipmentImageView.setBackgroundResource(R.drawable.legend_background)
                collectionEquipmentTextView.setTextColor(Color.parseColor("#E08808"))
            }
            "영웅" -> {
                collectionEquipmentImageView.setBackgroundResource(R.drawable.hero_background)
                collectionEquipmentTextView.setTextColor(Color.parseColor("#A41ED4"))

            }
            "희귀" -> {
                collectionEquipmentImageView.setBackgroundResource(R.drawable.rare_background)
                collectionEquipmentTextView.setTextColor(Color.parseColor("#268AD3"))
            }
        }
    }

    fun setTextImage(armory:ArmoryEquipment,toolTip: Tooltip){
        Glide.with(this).load(armory.icon).into(collectionEquipmentImageView)
        setBackground(armory.grade)
        collectionEquipmentTextView.text=armory.grade+" "+armory.type

        itemName=armory.name
        itemGrade = armory.grade
        imageUrl=armory.icon
        effectString=(toolTip.elements.get("Element_004")?.value as ItemPartData).element1
        descriptionString=toolTip.elements.get("Element_005")?.value.toString()
    }
}