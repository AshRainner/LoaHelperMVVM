package com.lostark.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.lostark.dto.armorys.ArmoryEquipment
import com.lostark.dto.armorys.armortooltip.IndentStringGroupData
import com.lostark.dto.armorys.armortooltip.ItemPartData
import com.lostark.dto.armorys.armortooltip.ItemTitleData
import com.lostark.dto.armorys.armortooltip.Tooltip
import com.lostark.loahelper.R

class CharSearchAccessoryView : LinearLayout {

    private val asDict = mutableMapOf(
        "무기 공격력" to "무공",
        "무력화" to "무력화",
        "물약 중독" to "물중",
        "포" to "포격 강화||포식자",
        "생명의 축복" to "생축",
        "자원의 축복" to "자축",
        "탈출의 달인" to "탈달",
        "폭발물 달인" to "폭달",
        "회피의 달인" to "회달",
        "칼날 방패" to "칼방",
        "각성기 피해" to "각피",
        "보스 피해" to "보피",
        "보호막 강화" to "보막강",
        "회복 강화" to "회복강",
        "마법 방어력" to "마방",
        "물리 방어력" to "물방",
        "받는 피해 감소" to "받피감",
        "최대 생명력" to "최생",
        "아군 강화" to "아강",
        "아이덴티티 획득" to "아덴",
        "추가 피해" to "추피",
        "치명타 피해" to "치피"
    )

    lateinit var accessoryCardView: MaterialCardView
    lateinit var accessoryImage: ImageView
    lateinit var accessoryName: TextView
    lateinit var accessoryQuality: TextView
    lateinit var accessoryAbility: TextView

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
        getAttrs(attrs)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.char_search_detail_ability_accessory_view, this, false)
        addView(view)


        accessoryCardView = findViewById(R.id.accessory_card_view)
        accessoryImage = findViewById(R.id.char_search_detail_accessory_image)
        accessoryName = findViewById(R.id.char_search_detail_accessory_name)
        accessoryQuality = findViewById(R.id.char_search_detail_accessory_quality)
        accessoryAbility = findViewById(R.id.char_search_detail_accessory_ability)

    }
    private fun getAttrs(attrs: AttributeSet?) {

    }
    fun setAccessoryImageText(armory:ArmoryEquipment,tooltip: Tooltip){
        Glide.with(this)
            .load(armory.icon)
            .into(accessoryImage)
        when(armory.grade) {//이미지 백그라운드
            "고대" -> {
                accessoryImage.setBackgroundResource(R.drawable.ancient_background)
                accessoryName.setTextColor(Color.parseColor("#d9ae43"))
            }
            "유물" -> {
                accessoryImage.setBackgroundResource(R.drawable.relic_background)
                accessoryName.setTextColor(Color.parseColor("#E45B0A"))

            }
            "전설" -> {
                accessoryImage.setBackgroundResource(R.drawable.legend_background)
                accessoryName.setTextColor(Color.parseColor("#E08808"))
            }
            "영웅" -> {
                accessoryImage.setBackgroundResource(R.drawable.hero_background)
                accessoryName.setTextColor(Color.parseColor("#A41ED4"))

            }
            "희귀" -> {
                accessoryImage.setBackgroundResource(R.drawable.rare_background)
                accessoryName.setTextColor(Color.parseColor("#268AD3"))
            }
            "고급" -> {
                accessoryImage.setBackgroundResource(R.drawable.advanced_background)
                accessoryName.setTextColor(Color.parseColor("#8FDB32"))
            }
        }
        val itemTitleKeys = tooltip.elements.filter { it.value.type=="ItemTitle"}.keys.toList()

        val itemTitleData = tooltip.elements.get(itemTitleKeys.get(0))?.value as ItemTitleData
        accessoryName.text = armory.type
        accessoryQuality.text = itemTitleData.qualityValue.toString()
        accessoryAbility.visibility=View.VISIBLE
        accessoryQuality.visibility=View.VISIBLE

        val itemPartBoxKeys = tooltip.elements.filter { it.value.type=="ItemPartBox"}.keys.toList()

        for(key in itemPartBoxKeys){
            val itemPartBoxData = tooltip.elements.get(key)?.value as ItemPartData
            var pattern = "(치명|특화|신속|제압|인내|숙련)".toRegex()
            if(itemPartBoxData.element1.contains(pattern)){
                if (armory.type=="목걸이"||armory.type=="팔찌"){
                    pattern = "(치명|특화|신속|제압|인내|숙련)\\s\\+(\\d+)+".toRegex()
                    var ability1:String=""
                    var abilityStat=0
                    var ability2:String=""
                    var seq=true
                    pattern.findAll(itemPartBoxData.element1).forEach {
                        val number = it.groupValues[2].toInt()
                        if (ability1=="") {
                            ability1=it.groupValues[1]
                            abilityStat=number
                        }
                        else{
                            ability2=it.groupValues[1]
                            if(number>abilityStat)
                                seq=false
                        }
                    }
                    if(seq)
                        accessoryAbility.text = ability1.substring(0,1)+ability2.substring(0,1)
                    else
                        accessoryAbility.text = ability2.substring(0,1)+ability1.substring(0,1)
                    if(armory.type=="팔찌")
                        accessoryQuality.visibility=View.GONE
                }
                else{
                    accessoryAbility.text = pattern.find(itemPartBoxData.element1)?.value
                }
            }
        }

    }
}