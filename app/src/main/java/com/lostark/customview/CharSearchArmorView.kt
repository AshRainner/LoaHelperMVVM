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
import com.lostark.dto.armorys.tooltips.*
import com.lostark.loahelper.R

class CharSearchArmorView : LinearLayout {

    private val asDict = mutableMapOf(
        "무기 공격력" to "무공",
        "무력화" to "무력화",
        "물약 중독" to "물중",
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

    lateinit var armorCardView: MaterialCardView
    lateinit var armorImage: ImageView
    lateinit var armorName: TextView
    lateinit var armorQuality: TextView
    lateinit var armorSetLevel: TextView
    lateinit var armorElixir: TextView
    lateinit var armorElixirSpecial: TextView

    var itemDetail: String = ""
    var itemDetailType: String = ""
    var imageUrl: String?=null
    var qualityValue: Int = -1 // 품질
    var defaultEffect:String?=null //기본효과
    var additionalEffect:String?=null //추가효과
    var elixirData : ContentStrData? = null
    var elixirSpecialDetailString: String? = null
    var setLevel:String?=null

    var elixirSpecialString:String?=null
    var elixirLevel = 0

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.char_search_detail_ability_armor_view, this, false)
        addView(view)
        armorImage = findViewById(R.id.armor_image)
        armorName = findViewById(R.id.armor_name)
        armorQuality = findViewById(R.id.armor_quality)
        armorSetLevel = findViewById(R.id.armor_set_level)
        armorElixir = findViewById(R.id.armor_elixir)
        armorCardView = findViewById(R.id.armor_card_view)
        armorElixirSpecial = findViewById(R.id.armor_elixir_special)

    }
    fun setImageBackground(grade : String){
        when(grade) {//이미지 백그라운드
            "고대" -> {
                armorImage.setBackgroundResource(R.drawable.ancient_background)
                armorName.setTextColor(Color.parseColor("#d9ae43"))
            }
            "유물" -> {
                armorImage.setBackgroundResource(R.drawable.relic_background)
                armorName.setTextColor(Color.parseColor("#E45B0A"))

            }
            "전설" -> {
                armorImage.setBackgroundResource(R.drawable.legend_background)
                armorName.setTextColor(Color.parseColor("#E08808"))
            }
            "영웅" -> {
                armorImage.setBackgroundResource(R.drawable.hero_background)
                armorName.setTextColor(Color.parseColor("#A41ED4"))

            }
            "희귀" -> {
                armorImage.setBackgroundResource(R.drawable.rare_background)
                armorName.setTextColor(Color.parseColor("#268AD3"))
            }
            "고급" -> {
                armorImage.setBackgroundResource(R.drawable.advanced_background)
                armorName.setTextColor(Color.parseColor("#8FDB32"))
            }
        }
    }
    fun setArmorImageText(armory:ArmoryEquipment,tooltip: Tooltip){
        Glide.with(this)
            .load(armory.icon)
            .into(armorImage)
        imageUrl = armory.icon
        setImageBackground(armory.grade)

        val itemTitleKeys = tooltip.elements.filter { it.value.type=="ItemTitle"}.keys.toList()

        val itemTitleData = tooltip.elements.get(itemTitleKeys.get(0))?.value as ItemTitleData
        armorName.text = armory.name
        if (itemTitleData.qualityValue!=-1) {
            qualityValue = itemTitleData.qualityValue
            armorQuality.text = itemTitleData.qualityValue.toString()
            itemDetailType = itemTitleData.leftStr0
            val pattern = "\\d+|티어 \\d".toRegex()
            pattern.findAll(itemTitleData.leftStr2).forEach {
                itemDetail+=" | "+it.value
            }

        }
        else {
            qualityValue = 0
            armorQuality.text = "0"
        }

        armorQuality.visibility=View.VISIBLE



        val itemPartBoxKeys = tooltip.elements.filter { it.value.type=="ItemPartBox"}.keys.toList()

        var check=false // 세트레벨이 존재하는지 체크

        for (key in itemPartBoxKeys){
            val itemPartData = tooltip.elements.get(key)?.value as ItemPartData
            if (itemPartData.element0.contains("세트 효과 레벨")){
                check=true
                val pattern = "Lv\\.\\d".toRegex()
                setLevel = itemPartData.element1
                armorSetLevel.text = pattern.find(itemPartData.element1)?.value
            }
            else if (itemPartData.element0.contains("기본 효과")){
                defaultEffect = itemPartData.element1
            }
            else if (itemPartData.element0.contains("추가 효과")){
                additionalEffect = itemPartData.element1
            }
        }
        if(check)
            armorSetLevel.visibility= View.VISIBLE

        check=false // 엘릭서가 있는지 체크

        val elixirKeys = tooltip.elements.filter { it.value.type=="IndentStringGroup"}.keys.toList()
        for (key in elixirKeys){
            val indentStringGroupData = tooltip.elements.get(key)?.value as IndentStringGroupData
            if (indentStringGroupData?.element0?.topStr?.contains("엘릭서") == true){
                elixirData = indentStringGroupData.element0.contentStrData
                check=true
                var pattern = "(아군 강화|아이덴티티 획득|추가 피해|치명타 피해) Lv\\.\\d|(마법 방어력|물리 방어력|받는 피해 감소|최대 생명력) Lv\\.\\d|(각성기 피해|보스 피해|보호막 강화|회복강화) Lv\\.\\d|(민첩|힘|지능|공격력|마나|무기 공격력|무력화|물약 중독|방랑자|생명의 축복|자원의 축복|탈출의 달인|폭발물 달인|회피의 달인) Lv\\.\\d|(강맹|달인|선각자|선봉대|신념|진군|칼날 방패|행운|회심).{6}Lv\\.\\d".toRegex()
                var elixirStr1 = indentStringGroupData.element0.contentStrData.element0?.contentStr
                var elixirStr2 = indentStringGroupData.element0.contentStrData.element1?.contentStr

                elixirStr1 = elixirStr1?.let { pattern.find(it)?.value.toString() }
                elixirStr2 = elixirStr2?.let { pattern.find(it)?.value.toString() }

                if (elixirStr1 != null) {
                    elixirStr1 = elixirStr1.replace("Lv.","").replace(" (질서) "," ").replace(" (혼돈) "," ")
                }
                if (elixirStr2 != null) {
                    elixirStr2 = elixirStr2.replace("Lv.","").replace(" (질서) "," ").replace(" (혼돈) "," ")
                }

                elixirStr1 = asDict.keys.fold(elixirStr1) { acc, key ->
                    acc?.replace(key, asDict[key] ?: "")
                }
                elixirStr2 = asDict.keys.fold(elixirStr2) { acc, key ->
                    acc?.replace(key, asDict[key] ?: "")
                }
                if(elixirStr2!=null)
                    armorElixir.text = elixirStr1+" · "+elixirStr2
                else
                    armorElixir.text=elixirStr1

                pattern = "\\d".toRegex()
                elixirLevel=pattern.find(elixirStr1!!)?.value.toString().toInt()

                if (elixirStr2 != null) {
                    elixirLevel+=pattern.find(elixirStr2)?.value.toString().toInt()
                }
            }
            else if (indentStringGroupData?.element0?.topStr?.contains("연성 추가 효과")==true){
                elixirSpecialDetailString
                var pattern = "(강맹|달인|선각자|선봉대|신념|진군|칼날 방패|행운|회심)\\s\\([12]단계\\)".toRegex()

                val elixirName = pattern.find(indentStringGroupData.element0.topStr)?.value
                elixirSpecialString = elixirName?.replace("(","")?.replace(")","")

                elixirSpecialString = asDict.keys.fold(elixirSpecialString) { acc, key ->
                    acc?.replace(key, asDict[key] ?: "")
                }
                pattern = "\\d".toRegex()
                val elixirSetLevel = elixirSpecialString?.let { pattern.find(it) }?.value?.toInt()
                elixirSetLevel?.let {
                    elixirSpecialDetailString = elixirName+"\n"
                    when(it){
                        1->elixirSpecialDetailString += indentStringGroupData.element0.contentStrData.element0.contentStr
                        2->elixirSpecialDetailString += indentStringGroupData.element0.contentStrData.element0.contentStr+indentStringGroupData.element0.contentStrData.element1.contentStr
                    }
                }

            }
        }
        if (check)
            armorElixir.visibility=View.VISIBLE


    }
}