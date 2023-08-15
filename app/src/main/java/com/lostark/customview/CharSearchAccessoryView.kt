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
    lateinit var stonePlusText: TextView
    lateinit var stoneMinusText: TextView

    var itemName: String = ""
    var itemType: String = ""
    var itemTier: String = ""
    var imageUrl: String? = null
    var qualityValue: Int = -1 // 품질
    var defaultEffect: String = ""//기본효과
    var additionalEffect: String? = null //추가효과
    var plusEngravingString: String? = null
    var minusEngravingString: String? = null


    var braceletAbilityString: String = ""
    lateinit var braceletAbilityList: List<String>

    var type = "악세"

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.char_search_detail_ability_accessory_view, this, false)
        addView(view)


        accessoryCardView = findViewById(R.id.accessory_card_view)
        accessoryImage = findViewById(R.id.char_search_detail_accessory_image)
        accessoryName = findViewById(R.id.char_search_detail_accessory_name)
        accessoryQuality = findViewById(R.id.char_search_detail_accessory_quality)
        accessoryAbility = findViewById(R.id.char_search_detail_accessory_ability)
        stonePlusText = findViewById(R.id.char_search_detail_ability_stone_plus_text)
        stoneMinusText = findViewById(R.id.char_search_detail_ability_stone_minus_text)

    }

    fun setImageBackground(grade: String) {
        when (grade) {//이미지 백그라운드
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
    }

    fun setAccessoryImageText(armory: ArmoryEquipment, tooltip: Tooltip) {
        Glide.with(this)
            .load(armory.icon)
            .into(accessoryImage)
        imageUrl = armory.icon
        setImageBackground(armory.grade)
        itemName = armory.name

        val itemTitleKeys = tooltip.elements.filter { it.value.type == "ItemTitle" }.keys.toList()

        val itemTitleData = tooltip.elements.get(itemTitleKeys.get(0))?.value as ItemTitleData

        var check = false //어빌 스톤인지 체크
        if (itemTitleData.leftStr0.contains("스톤"))
            check = true
        itemType = itemTitleData.leftStr0
        itemTier = " | " + itemTitleData.leftStr2.replace("아이템 ", "")
        if (check) {
            var pattern = "[A-Z]+".toRegex()
            val stoneLevle = pattern.find(armory.name)
            accessoryName.text = "스톤"
            stoneLevle?.let {
                accessoryName.text = "스톤 " + stoneLevle.value
            }

            val indentStringGroup =
                tooltip.elements.filter { it.value.type == "IndentStringGroup" }.keys.toList()
            pattern = "\\d".toRegex()
            for (key in indentStringGroup) {
                val indentStringGroupData =
                    tooltip.elements.get(key)?.value as IndentStringGroupData

                val plusEngravingOne =
                    indentStringGroupData.element0.contentStrData.element0.contentStr.replace(
                        "[",
                        ""
                    ).replace("]", "").replace("활성도", "")
                val plusOne =
                    pattern.find(indentStringGroupData.element0.contentStrData.element0.contentStr)?.value

                val plusEngravingTwo =
                    indentStringGroupData.element0.contentStrData.element1.contentStr.replace(
                        "[",
                        ""
                    ).replace("]", "").replace("활성도", "")
                val plusTwo =
                    pattern.find(indentStringGroupData.element0.contentStrData.element1.contentStr)?.value

                plusEngravingString = plusEngravingOne + plusEngravingTwo.replace("\n", "")

                minusEngravingString =
                    indentStringGroupData.element0.contentStrData.element2.contentStr.replace(
                        "[",
                        ""
                    ).replace("]", "").replace("활성도", "")
                val minusOne =
                    pattern.find(indentStringGroupData.element0.contentStrData.element2.contentStr)?.value
                stonePlusText.text = plusOne + " · " + plusTwo + " · "
                stoneMinusText.text = minusOne

                stonePlusText.visibility = View.VISIBLE
                stoneMinusText.visibility = View.VISIBLE
                type = "스톤"
            }


        } else {
            accessoryName.text = armory.type
            accessoryQuality.text = itemTitleData.qualityValue.toString()
            qualityValue = itemTitleData.qualityValue
            accessoryAbility.visibility = View.VISIBLE
            accessoryQuality.visibility = View.VISIBLE

            val indentStringGroupKeys =
                tooltip.elements.filter { it.value.type == "IndentStringGroup" }.keys.toList()

            for (key in indentStringGroupKeys) {
                val indentStringGroupData =
                    tooltip.elements.get(key)?.value as IndentStringGroupData

                if (indentStringGroupData.element0.topStr.contains("무작위 각인 효과")) {
                    val contenStrData = indentStringGroupData.element0.contentStrData
                    var plusEngravingOne = ""
                    contenStrData.element0?.let {
                        plusEngravingOne =
                            it.contentStr.replace("[", "").replace("]", "")
                                .replace("활성도", "")
                    }
                    var plusEngravingTwo = ""
                    contenStrData.element1?.let {
                        plusEngravingTwo =
                            it.contentStr.replace("[", "").replace("]", "")
                                .replace("활성도", "")
                    }
                    plusEngravingString = plusEngravingOne + plusEngravingTwo.replace("\n", "")
                    contenStrData.element2?.let {
                        minusEngravingString =
                            it.contentStr.replace("[", "").replace("]", "")
                                .replace("활성도", "")
                    }

                }
            }
        }
        val itemPartBoxKeys =
            tooltip.elements.filter { it.value.type == "ItemPartBox" }.keys.toList()

        for (key in itemPartBoxKeys) {
            val itemPartBoxData = tooltip.elements.get(key)?.value as ItemPartData
            var pattern = "(치명|특화|신속|제압|인내|숙련)".toRegex()
            if (itemPartBoxData.element1.contains(pattern)) {

                if (armory.type == "팔찌") {
                    pattern =
                        "(체력|힘|민첩|지능|치명|신속|특화|제압|인내|숙련|최대 생명력|최대 마나|물리 방어력|무기 공격력|마법 방어력|전투 중 생명력 회복량)\\s\\+|\\[(오뚝이|돌진|강타|타격|마나회수|속궁|투자|반전|멸시|무시|전투 중 생명력 회복량|회생|긴급수혈|응급처치|앵콜|쐐기|망치|순환|열정|냉정|비수|약점 노출|깨달음|응원|수확|보상|무기 공격력|우월|습격|정밀|상처약화|분개|기습|결투|적립)\\]".toRegex()
                    braceletAbilityString =
                        pattern.findAll(itemPartBoxData.element1.replace("\n", " "))
                            .map { it.value }.toList().joinToString(" · ").replace("[", "")
                            .replace("]", "").replace(" +", "")
                    //pattern = "(체력|힘|민첩|지능|치명|신속|특화|제압|인내|숙련|최대 생명력|최대 마나|물리 방어력|무기 공격력|마법 방어력|전투 중 생명력 회복량)\\s\\+\\d+|\\[(오뚝이|돌진|강타|타격|마나회수|속궁|투자|반전|멸시|무시|전투 중 생명력 회복량|회생|긴급수혈|응급처치|앵콜|쐐기|망치|순환|열정|냉정|비수|약점 노출|깨달음|응원|수확|보상|무기 공격력|우월|습격|정밀|상처약화|분개|기습|결투|적립)](\\s.*)?".toRegex()
                    pattern =
                        "(체력|힘|민첩|지능|치명|신속|특화|제압|인내|숙련|최대 생명력|최대 마나|물리 방어력|무기 공격력|마법 방어력|전투 중 생명력 회복량)\\s?\\+\\d+|\\[(오뚝이|돌진|강타|타격|마나회수|속궁|투자|반전|멸시|무시|전투 중 생명력 회복량|회생|긴급수혈|응급처치|앵콜|쐐기|망치|순환|열정|냉정|비수|약점 노출|깨달음|응원|수확|보상|무기 공격력|우월|습격|정밀|상처약화|분개|기습|결투|적립)\\].*?\\)".toRegex()
                    braceletAbilityList =
                        pattern.findAll(itemPartBoxData.element1.replace("\n", " "))
                            .map { it.value }.toList()
                }


                if (armory.type == "목걸이" || armory.type == "팔찌") {
                    pattern = "(치명|특화|신속|제압|인내|숙련)\\s\\+(\\d+)+".toRegex()
                    var ability1: String = ""
                    var abilityStat = 0
                    var ability2: String = " "
                    var seq = true
                    pattern.findAll(itemPartBoxData.element1).forEach {
                        val number = it.groupValues[2].toInt()
                        if (ability1 == "") {
                            ability1 = it.groupValues[1]
                            abilityStat = number
                        } else {
                            ability2 = it.groupValues[1]
                            if (number > abilityStat)
                                seq = false
                        }
                    }
                    if (seq)
                        accessoryAbility.text =
                            ability1.substring(0, 1) + ability2.substring(0, 1)
                    else
                        accessoryAbility.text =
                            ability2.substring(0, 1) + ability1.substring(0, 1)
                    if (armory.type == "팔찌") {
                        accessoryQuality.visibility = View.GONE
                        type = "팔찌"
                    }
                } else {
                    accessoryAbility.text = pattern.find(itemPartBoxData.element1)?.value
                }
            }
            if (itemPartBoxData.element0.contains("기본 효과")) {
                var pattern = "힘 \\+\\d+|체력 \\+\\d+".toRegex()
                defaultEffect = pattern.findAll(itemPartBoxData.element1).map { it.value }.toList()
                    .joinToString("\n").replace("힘 ", "")
            } else if (itemPartBoxData.element0.contains("추가 효과") || itemPartBoxData.element0.contains(
                    "세공 단계 보너스"
                )
            ) {
                additionalEffect = itemPartBoxData.element1
            }
        }

    }
}