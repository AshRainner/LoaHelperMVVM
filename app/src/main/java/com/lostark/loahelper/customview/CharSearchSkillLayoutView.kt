package com.lostark.loahelper.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchDetailRuneViewBinding
import com.lostark.loahelper.databinding.CharSearchDetailSkillLayoutViewBinding

class CharSearchSkillLayoutView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailSkillLayoutViewBinding>(
    context,
    attrs,
    defStyleAttr
){
    lateinit var skillType:String
    lateinit var skillMp:String
    lateinit var skillEffect:String
    lateinit var skillCool:String
    lateinit var skillStance:String
    lateinit var skillDescription: String
    lateinit var imageUrl:String

    override fun init(context: Context?) {

    }

    fun setImageText(skill: com.lostark.loahelper.dto.armorys.ArmorySkill, tooltip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip){
        Glide.with(this)
            .load(skill.icon)
            .into(binding.charSearchDetailSkillImage)
        imageUrl = skill.icon
        binding.charSearchDetailSkillName.text = skill.name
        binding.charSearchDetailSkillLevel.text = skill.level.toString()+"레벨"

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

    fun getSkillName()=binding.charSearchDetailSkillName


    override fun getAttrs(attrs: AttributeSet?) {
    }

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailSkillLayoutViewBinding {
        return CharSearchDetailSkillLayoutViewBinding.inflate(inflater)
    }
}