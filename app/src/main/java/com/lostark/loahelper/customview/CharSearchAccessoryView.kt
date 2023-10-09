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
import com.google.android.material.card.MaterialCardView
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchDetailAbilityAccessoryViewBinding
import com.lostark.loahelper.databinding.CharSearchDetailCharactersGridLayoutViewBinding

class CharSearchAccessoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailAbilityAccessoryViewBinding>(
    context,
    attrs,
    defStyleAttr
) {

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

    override fun init(context: Context?) {

    }

    fun setImageBackground(grade: String) {
        binding.run {
            when (grade) {//이미지 백그라운드
                "고대" -> {
                    charSearchDetailAccessoryImage.setBackgroundResource(R.drawable.ancient_background)
                    charSearchDetailAccessoryName.setTextColor(Color.parseColor("#d9ae43"))
                }
                "유물" -> {
                    charSearchDetailAccessoryImage.setBackgroundResource(R.drawable.relic_background)
                    charSearchDetailAccessoryName.setTextColor(Color.parseColor("#E45B0A"))

                }
                "전설" -> {
                    charSearchDetailAccessoryImage.setBackgroundResource(R.drawable.legend_background)
                    charSearchDetailAccessoryName.setTextColor(Color.parseColor("#E08808"))
                }
                "영웅" -> {
                    charSearchDetailAccessoryImage.setBackgroundResource(R.drawable.hero_background)
                    charSearchDetailAccessoryName.setTextColor(Color.parseColor("#A41ED4"))

                }
                "희귀" -> {
                    charSearchDetailAccessoryImage.setBackgroundResource(R.drawable.rare_background)
                    charSearchDetailAccessoryName.setTextColor(Color.parseColor("#268AD3"))
                }
                "고급" -> {
                    charSearchDetailAccessoryImage.setBackgroundResource(R.drawable.advanced_background)
                    charSearchDetailAccessoryName.setTextColor(Color.parseColor("#8FDB32"))
                }
                else->""
            }
        }
    }
    fun setBackGroundColor(quality:Int){
        binding.run {
            when {
                quality == 100 -> {
                    accessoryCardView.setCardBackgroundColor(Color.parseColor("#F2BD2C"))
                    charSearchDetailAccessoryQuality.setBackgroundColor(Color.parseColor("#F2BD2C"))
                }
                quality >= 90 -> {
                    accessoryCardView.setCardBackgroundColor(Color.parseColor("#ff00dd"))
                    charSearchDetailAccessoryQuality.setBackgroundColor(Color.parseColor("#ff00dd"))
                }
                quality >= 70 -> {
                    accessoryCardView.setCardBackgroundColor(Color.parseColor("#1B89F4"))
                    charSearchDetailAccessoryQuality.setBackgroundColor(Color.parseColor("#1B89F4"))
                }
                quality >= 30 -> {
                    accessoryCardView.setCardBackgroundColor(Color.parseColor("#35E81C"))
                    charSearchDetailAccessoryQuality.setBackgroundColor(Color.parseColor("#35E81C"))
                }
                quality >= 10 -> {
                    accessoryCardView.setCardBackgroundColor(Color.parseColor("#D2D208"))
                    charSearchDetailAccessoryQuality.setBackgroundColor(Color.parseColor("#D2D208"))
                }
                quality == 0 -> {
                    accessoryCardView.setCardBackgroundColor(Color.parseColor("#dedfe0"))
                    charSearchDetailAccessoryQuality.setBackgroundColor(Color.parseColor("#dedfe0"))
                }
                else -> {
                    accessoryCardView.setCardBackgroundColor(Color.parseColor("#F5260E"))
                    charSearchDetailAccessoryQuality.setBackgroundColor(Color.parseColor("#F5260E"))
                }
            }
        }
    }

    fun setAccessoryImageText(armory: com.lostark.loahelper.dto.armorys.ArmoryEquipment, tooltip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip) {
        binding.run {
        Glide.with(this@CharSearchAccessoryView)
            .load(armory.icon)
            .into(charSearchDetailAccessoryImage)
        imageUrl = armory.icon
        setImageBackground(armory.grade)
        itemName = armory.name

        val itemTitleKeys = tooltip.elements.filter { it.value.type == "ItemTitle" }.keys.toList()

        val itemTitleData = tooltip.elements.get(itemTitleKeys.get(0))?.value as com.lostark.loahelper.dto.armorys.tooltips.ItemTitleData

        var check = false //어빌 스톤인지 체크
        if (itemTitleData.leftStr0.contains("스톤"))
            check = true
        itemType = itemTitleData.leftStr0
        itemTier = " | " + itemTitleData.leftStr2.replace("아이템 ", "")
        if (check) {
            var pattern = "[A-Z]+".toRegex()
            val stoneLevle = pattern.find(armory.name)
            charSearchDetailAccessoryName.text = "스톤"
            stoneLevle?.let {
                charSearchDetailAccessoryName.text = "스톤 " + stoneLevle.value
            }

            val indentStringGroup =
                tooltip.elements.filter { it.value.type == "IndentStringGroup" }.keys.toList()
            pattern = "\\d".toRegex()
            for (key in indentStringGroup) {
                val indentStringGroupData =
                    tooltip.elements.get(key)?.value as com.lostark.loahelper.dto.armorys.tooltips.IndentStringGroupData

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
                charSearchDetailAbilityStonePlusText.text = plusOne + " · " + plusTwo + " · "
                charSearchDetailAbilityStoneMinusText.text = minusOne

                charSearchDetailAbilityStonePlusText.visibility = View.VISIBLE
                charSearchDetailAbilityStoneMinusText.visibility = View.VISIBLE
                type = "스톤"
            }


        } else {
            charSearchDetailAccessoryName.text = armory.type
            charSearchDetailAccessoryQuality.text = itemTitleData.qualityValue.toString()
            qualityValue = itemTitleData.qualityValue
            charSearchDetailAccessoryAbility.visibility = View.VISIBLE
            charSearchDetailAccessoryQuality.visibility = View.VISIBLE

            val indentStringGroupKeys =
                tooltip.elements.filter { it.value.type == "IndentStringGroup" }.keys.toList()

            for (key in indentStringGroupKeys) {
                val indentStringGroupData =
                    tooltip.elements.get(key)?.value as com.lostark.loahelper.dto.armorys.tooltips.IndentStringGroupData

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
            val itemPartBoxData = tooltip.elements.get(key)?.value as com.lostark.loahelper.dto.armorys.tooltips.ItemPartData
            var pattern = "(치명|특화|신속|제압|인내|숙련)".toRegex()
                if (armory.type == "팔찌") {
                    pattern =
                        "(체력|힘|민첩|지능|치명|신속|특화|제압|인내|숙련|최대 생명력|최대 마나|물리 방어력|무기 공격력|마법 방어력|전투 중 생명력 회복량)\\s\\+|\\[(오뚝이|돌진|강타|타격|마나회수|속공|투자|반전|멸시|무시|전투 중 생명력 회복량|회생|긴급수혈|응급처치|앵콜|쐐기|망치|순환|열정|냉정|비수|약점 노출|깨달음|응원|수확|보상|무기 공격력|우월|습격|정밀|상처약화|분개|기습|결투|적립)\\]".toRegex()
                    braceletAbilityString =
                        pattern.findAll(itemPartBoxData.element1.replace("\n", " "))
                            .map { it.value }.toList().joinToString(" · ").replace("[", "")
                            .replace("]", "").replace(" +", "")
                    //pattern = "(체력|힘|민첩|지능|치명|신속|특화|제압|인내|숙련|최대 생명력|최대 마나|물리 방어력|무기 공격력|마법 방어력|전투 중 생명력 회복량)\\s\\+\\d+|\\[(오뚝이|돌진|강타|타격|마나회수|속궁|투자|반전|멸시|무시|전투 중 생명력 회복량|회생|긴급수혈|응급처치|앵콜|쐐기|망치|순환|열정|냉정|비수|약점 노출|깨달음|응원|수확|보상|무기 공격력|우월|습격|정밀|상처약화|분개|기습|결투|적립)](\\s.*)?".toRegex()
                    pattern =
                        ("(체력|힘|민첩|지능|치명|신속|특화|제압|인내|숙련|최대 생명력|최대 마나|물리 방어력|무기 공격력|마법 방어력|전투 중 생명력 회복량)\\s?\\+\\d+|" +
                                "\\[(속공|투자|반전|멸시|무시|전투 중 생명력 회복량|긴급수혈|응급처치|앵콜|쐐기|망치|순환|열정|냉정|비수|약점 노출|깨달음|응원|수확|보상|무기 공격력|우월|습격|정밀|상처약화|분개|기습|결투|적립)\\].*?\\)|" +
                                "\\[(오뚝이|돌진|강타|타격|마나회수|회생)\\].*?\\.").toRegex()
                    braceletAbilityList =
                        pattern.findAll(itemPartBoxData.element1.replace("\n", " "))
                            .map { it.value }.toList()
                    println(braceletAbilityList)
                }


                if (armory.type == "목걸이" || armory.type == "팔찌") {
                    pattern = "(치명|특화|신속|제압|인내|숙련)\\s\\+(\\d+)+".toRegex()
                    var ability1: String = " "
                    var abilityStat = 0
                    var ability2: String = " "
                    var seq = true
                    pattern.findAll(itemPartBoxData.element1).forEach {
                        val number = it.groupValues[2].toInt()
                        if (ability1 == " ") {
                            ability1 = it.groupValues[1]
                            abilityStat = number
                        } else {
                            ability2 = it.groupValues[1]
                            if (number > abilityStat)
                                seq = false
                        }
                    }
                    if (seq)
                        charSearchDetailAccessoryAbility.text =
                            ability1.substring(0, 1) + ability2.substring(0, 1)
                    else
                        charSearchDetailAccessoryAbility.text =
                            ability2.substring(0, 1) + ability1.substring(0, 1)
                    if (armory.type == "팔찌") {
                        charSearchDetailAccessoryQuality.visibility = View.GONE
                        type = "팔찌"
                    }
                } else {
                    charSearchDetailAccessoryAbility.text = pattern.find(itemPartBoxData.element1)?.value
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
            setBackGroundColor(qualityValue)
        }
    }

    fun getAccessoryName()=binding.charSearchDetailAccessoryName
    fun getAccessoryImage()=binding.charSearchDetailAccessoryImage
    fun getAccessoryQuality()=binding.charSearchDetailAccessoryQuality
    fun getStonPlus()=binding.charSearchDetailAbilityStonePlusText
    fun getStonMinus()=binding.charSearchDetailAbilityStoneMinusText
    override fun getAttrs(attrs: AttributeSet?) {
    }

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailAbilityAccessoryViewBinding {
        return CharSearchDetailAbilityAccessoryViewBinding.inflate(inflater)
    }
}