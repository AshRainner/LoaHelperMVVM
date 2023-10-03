package com.lostark.loahelper.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lostark.loahelper.R

class CharSearchSkillLayoutView : LinearLayout {

    lateinit var skillImage: ImageView
    lateinit var skillName: TextView
    lateinit var skillLevel: TextView

    lateinit var skillType:String
    lateinit var skillMp:String
    lateinit var skillEffect:String
    lateinit var skillCool:String
    lateinit var skillStance:String
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
            LayoutInflater.from(context).inflate(R.layout.char_search_detail_skill_layout_view, this, false)
        addView(view)
        skillImage = view.findViewById(R.id.char_search_detail_skill_image)
        skillName = view.findViewById(R.id.char_search_detail_skill_name)
        skillLevel = view.findViewById(R.id.char_search_detail_skill_level)

    }

    fun setImageText(skill: com.lostark.loahelper.dto.armorys.ArmorySkill, tooltip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip){
        Glide.with(this)
            .load(skill.icon)
            .into(skillImage)
        imageUrl = skill.icon
        skillName.text = skill.name
        skillLevel.text = skill.level.toString()+"레벨"
        println(tooltip.elements.get("Element_001"))

        skillType = (tooltip.elements.get("Element_001")?.value as com.lostark.loahelper.dto.armorys.tooltips.CommonSkillTitleData).name
        skillStance = " | "+(tooltip.elements.get("Element_001")?.value as com.lostark.loahelper.dto.armorys.tooltips.CommonSkillTitleData).level.replace("[","").replace("]","")
        skillCool = (tooltip.elements.get("Element_001")?.value as com.lostark.loahelper.dto.armorys.tooltips.CommonSkillTitleData).leftText
        var pattern = "마나 \\d+".toRegex()
        skillMp = pattern.find(tooltip.elements.get("Element_004")?.value.toString())?.value?:""

        val skillDescriptionList = tooltip.elements.get("Element_005")?.value.toString().split("\n").toMutableList()
        skillDescription = skillDescriptionList.get(0).replace(". ",".\n")
        skillDescriptionList.removeAt(0)
        skillEffect = skillDescriptionList.joinToString("\n")

    }
}