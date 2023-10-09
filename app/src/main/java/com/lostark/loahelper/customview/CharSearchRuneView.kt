package com.lostark.loahelper.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchDetailAbilityGemViewBinding
import com.lostark.loahelper.databinding.CharSearchDetailRuneViewBinding

class CharSearchRuneView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailRuneViewBinding>(
    context,
    attrs,
    defStyleAttr
) {
        lateinit var runeName:String
    lateinit var imageUrl:String
    lateinit var runeGrade:String
    lateinit var runeDescription:String

    override fun init(context: Context?) {

    }
    fun setRuneImageBackground(grade : String){//룬, 보석
        when(grade) {//이미지 백그라운드
            "전설" -> binding.charSearchDetailSkillRuneImage.setBackgroundResource(R.drawable.legend_background)
            "영웅" -> binding.charSearchDetailSkillRuneImage.setBackgroundResource(R.drawable.hero_background)
            "희귀" -> binding.charSearchDetailSkillRuneImage.setBackgroundResource(R.drawable.rare_background)
            "고급" -> binding.charSearchDetailSkillRuneImage.setBackgroundResource(R.drawable.advanced_background)
        }
    }

    fun setRuneImageText(rune: com.lostark.loahelper.dto.armorys.Rune, tooltip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip){
        Glide.with(this)
            .load(rune.icon)
            .into(binding.charSearchDetailSkillRuneImage)
        imageUrl = rune.icon
        runeGrade = rune.grade+" 룬"
        setRuneImageBackground(rune.grade)

        binding.charSearchDetailSkillRuneName.text = rune.name
        binding.charSearchDetailSkillRuneName.visibility= View.VISIBLE
        runeName = rune.name
        runeDescription = (tooltip.elements.get("Element_002")?.value as com.lostark.loahelper.dto.armorys.tooltips.ItemPartData).element1 //스킬 룬 설명

    }

    override fun getAttrs(attrs: AttributeSet?) {
    }

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailRuneViewBinding {
        return CharSearchDetailRuneViewBinding.inflate(inflater)
    }
}