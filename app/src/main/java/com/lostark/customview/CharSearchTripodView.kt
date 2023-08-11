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
import com.lostark.dto.armorys.Tripod
import com.lostark.dto.armorys.tooltips.*
import com.lostark.loahelper.R

class CharSearchTripodView : LinearLayout {

    lateinit var tripodTierDia: View
    lateinit var tripodImage: ImageView
    lateinit var tripodTier: TextView
    lateinit var tripodName: TextView
    lateinit var tripodLevel: TextView

    lateinit var tripodDescription: String
    lateinit var tripodDialogLevel: String

    lateinit var imageUrl:String

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.char_search_detail_ability_tripod_view, this, false)
        addView(view)
        tripodTierDia = view.findViewById(R.id.char_search_detail_tripod_tier_diamond)
        tripodImage = view.findViewById(R.id.char_search_detail_tripod_image)
        tripodTier = view.findViewById(R.id.char_search_detail_tripod_tier)
        tripodName = view.findViewById(R.id.char_search_detail_tripod_name)
        tripodLevel = view.findViewById(R.id.char_search_detail_tripod_level)


    }
    fun setDiamondBackground(tier : Int,slot:Int){
        when(tier) {//이미지 백그라운드
            0-> {
                tripodTierDia.setBackgroundColor((Color.parseColor("#4dd0ff")))
            }//#99e4ff 1티어
            1-> {
                tripodTierDia.setBackgroundColor((Color.parseColor("#56ea56")))
            }//#6ded6d 2티어
            2-> {
                tripodTierDia.setBackgroundColor((Color.parseColor("#ffbc1f")))
            }//#ffcb52 3티어
        }
        tripodTier.text=slot.toString()
    }
    fun setTripodImageText(tripod: Tripod){
        Glide.with(this).load(tripod.icon).into(tripodImage)
        tripodName.text=tripod.name
        tripodLevel.text="Lv."+tripod.level.toString()
    }
}