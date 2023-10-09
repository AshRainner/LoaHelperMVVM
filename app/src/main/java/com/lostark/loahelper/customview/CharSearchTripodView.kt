package com.lostark.loahelper.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchDetailSkillTripodViewBinding
import com.lostark.loahelper.databinding.CharSearchDetailSkillViewBinding
import com.lostark.loahelper.dto.armorys.tooltips.Tooltip
import com.lostark.loahelper.dto.armorys.tooltips.TripodSkillCustomData

class CharSearchTripodView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailSkillTripodViewBinding>(
    context,
    attrs,
    defStyleAttr
) {

    lateinit var tripodDescription: String
    lateinit var tripodDialogLevel: String

    lateinit var imageUrl:String


    override fun init(context: Context?) {

    }
    fun setDiamondBackground(tier : Int,slot:Int){
        when(tier) {//이미지 백그라운드
            0-> {
                binding.charSearchDetailTripodTierDiamond.setBackgroundColor((Color.parseColor("#4dd0ff")))
            }//#99e4ff 1티어
            1-> {
                binding.charSearchDetailTripodTierDiamond.setBackgroundColor((Color.parseColor("#56ea56")))
            }//#6ded6d 2티어
            2-> {
                binding.charSearchDetailTripodTierDiamond.setBackgroundColor((Color.parseColor("#ffbc1f")))
            }//#ffcb52 3티어
        }
        binding.charSearchDetailTripodTier.text=slot.toString()
    }
    fun setTripodImageText(tripod: com.lostark.loahelper.dto.armorys.Tripod, index:Int, tooltip: Tooltip){
        binding.run {
            Glide.with(this@CharSearchTripodView).load(tripod.icon).into(charSearchDetailTripodImage)
            imageUrl = tripod.icon
            charSearchDetailTripodName.text = tripod.name
            charSearchDetailTripodLevel.text = "Lv." + tripod.level.toString()
            when (index) {
                0 -> {
                    tripodDialogLevel =
                        (tooltip.elements.get("Element_006")?.value as TripodSkillCustomData).element0.tier
                    tripodDescription =
                        (tooltip.elements.get("Element_006")?.value as TripodSkillCustomData).element0.desc
                }
                1 -> {
                    tripodDialogLevel =
                        (tooltip.elements.get("Element_006")?.value as TripodSkillCustomData).element1.tier
                    tripodDescription =
                        (tooltip.elements.get("Element_006")?.value as TripodSkillCustomData).element1.desc
                }
                2 -> {
                    tripodDialogLevel =
                        (tooltip.elements.get("Element_006")?.value as TripodSkillCustomData).element2.tier
                    tripodDescription =
                        (tooltip.elements.get("Element_006")?.value as TripodSkillCustomData).element2.desc
                }
            }
        }

    }

    fun getTripodName()=binding.charSearchDetailTripodName

    override fun getAttrs(attrs: AttributeSet?) {

    }

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailSkillTripodViewBinding {
        return CharSearchDetailSkillTripodViewBinding.inflate(inflater)
    }
}