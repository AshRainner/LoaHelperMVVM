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
import com.google.gson.GsonBuilder
import com.lostark.adapter.ValueDataAdapter
import com.lostark.dto.armorys.*
import com.lostark.dto.armorys.tooltips.*
import com.lostark.loahelper.R

class CharSearchSkillView : LinearLayout {

    lateinit var skillLayout: LinearLayout
    lateinit var skillImage: ImageView
    lateinit var skillName: TextView
    lateinit var skillLevel: TextView
    lateinit var runeImage: ImageView
    lateinit var runeName: TextView
    lateinit var gemImage1: CharSearchGemView
    lateinit var gemImage2: CharSearchGemView
    lateinit var tripodLayout: LinearLayout
    lateinit var tripodView1 :CharSearchTripodView
    lateinit var tripodView2 :CharSearchTripodView
    lateinit var tripodView3 :CharSearchTripodView

    lateinit var skillDescription: String

    lateinit var imageUrl:String

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.char_search_detail_skill_view, this, false)
        addView(view)
        skillLayout = view.findViewById(R.id.char_search_detail_skill_layout)
        skillImage = view.findViewById(R.id.char_search_detail_skill_image)
        skillName = view.findViewById(R.id.char_search_detail_skill_name)
        skillLevel = view.findViewById(R.id.char_search_detail_skill_level)
        runeImage = view.findViewById(R.id.char_search_detail_skill_rune_image)
        runeName = view.findViewById(R.id.char_search_detail_skill_rune_name)
        gemImage1 = view.findViewById(R.id.char_search_detail_skill_gem_1)
        gemImage2 = view.findViewById(R.id.char_search_detail_skill_gem_2)
        tripodLayout = view.findViewById(R.id.char_search_detail_skill_tripod_layout)
        tripodView1 = view.findViewById(R.id.char_search_detail_skill_tripod_1)
        tripodView2 = view.findViewById(R.id.char_search_detail_skill_tripod_2)
        tripodView3 = view.findViewById(R.id.char_search_detail_skill_tripod_3)
        gemImage1.goneDefaultGem()
        gemImage2.goneDefaultGem()


    }
    fun setRuneImageBackground(grade : String){//룬, 보석
        when(grade) {//이미지 백그라운드
            "전설" -> runeImage.setBackgroundResource(R.drawable.legend_background)
            "영웅" -> runeImage.setBackgroundResource(R.drawable.hero_background)
            "희귀" -> runeImage.setBackgroundResource(R.drawable.rare_background)
            "고급" -> runeImage.setBackgroundResource(R.drawable.advanced_background)
        }
    }

    fun setGem(gemList:List<Gem>){
        val useGemList=gemList.filter { it.tooltip.contains(skillName.text) }.sortedByDescending { it.name }

        val gemImageList = listOf(gemImage1,gemImage2)
        useGemList.forEachIndexed { index, gem->
            gemImageList.getOrNull(index)?.setSkillGemImageText(gem,toolTipDeserialization(gem))
        }

    }

    fun toolTipDeserialization(vararg items: Any?): Tooltip {
        val gson = GsonBuilder()
            .registerTypeAdapter(ValueData::class.java, ValueDataAdapter())
            .create()
        val pattern = "<.*?>".toRegex()
        val pattern2 = "<BR>|<br>".toRegex()
        val tooltips = items.mapNotNull { item ->
            when (item) {
                is ArmoryEquipment -> item.tooltip
                is Engraving -> item.tooltip
                is Gem -> item.tooltip
                is Card -> item.tooltip
                else -> ""
            }?.replace(pattern2, "\n")?.replace(pattern, "")
        }
        val jsonString = "{\n\"Elements\":\n${tooltips.joinToString(separator = ",\n")}\n}"
        return gson.fromJson(jsonString, Tooltip::class.java)
    }

    fun setImageText(skill: ArmorySkill){
        Glide.with(this)
            .load(skill.icon)
            .into(skillImage)
        imageUrl = skill.icon
        skillName.text = skill.name
        skillLevel.text = skill.level.toString()+"레벨"
        skill.rune?.let {
            Glide.with(this)
                .load(it.icon)
                .into(runeImage)
            setRuneImageBackground(it.grade)
            runeName.text = it.name
            runeName.visibility=View.VISIBLE
        }
        val useTripod = skill.tripods.filter { it.isSelected }

        if(useTripod!=null){
            if(useTripod.size!=0)
                tripodLayout.visibility=View.VISIBLE
            val tripodList = listOf(tripodView1,tripodView2,tripodView3)
            useTripod.sortedBy { it.tier }.forEachIndexed { index,tripod->
                val tripodView = tripodList.get(index)
                tripodView.setDiamondBackground(tripod.tier,tripod.slot)
                tripodView.setTripodImageText(tripod)
                tripodView.visibility=View.VISIBLE
            }
        }
    }
}