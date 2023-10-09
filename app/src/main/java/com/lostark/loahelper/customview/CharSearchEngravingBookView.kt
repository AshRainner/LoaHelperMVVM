package com.lostark.loahelper.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchDetailAbilityEngravingBookViewBinding
import com.lostark.loahelper.databinding.CharSearchDetailCollectionListViewBinding
import com.lostark.loahelper.dto.armorys.Engraving
import com.lostark.loahelper.dto.armorys.tooltips.Tooltip

class CharSearchEngravingBookView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailAbilityEngravingBookViewBinding>(
    context,
    attrs,
    defStyleAttr
) {


    lateinit var engravingStringList:List<String>
    lateinit var imageUrl:String

    override fun init(context: Context?) {

    }
    override fun getAttrs(attrs: AttributeSet?) {

    }
    fun setEngravingImageText(engraving: Engraving, tooltip: Tooltip){
        Glide.with(this)
            .load(engraving.icon)
            .circleCrop()
            .into(binding.charSearchDetailEngravingImage)
        imageUrl = engraving.icon

        binding.charSearchDetailEngravingName.text = tooltip.elements.get("Element_000")?.value.toString()
        val data = tooltip.elements.get("Element_001")?.value as com.lostark.loahelper.dto.armorys.tooltips.EngraveSkillTitleData
        binding.charSearchDetailEngravingPoint.text = data.leftText.replace("각인 ","")

        var pattern = "레벨 \\d+[^레벨].*".toRegex(RegexOption.MULTILINE)
        engravingStringList =  pattern.findAll((tooltip.elements.get("Element_003")?.value as com.lostark.loahelper.dto.armorys.tooltips.ItemPartData).element1).map { it.value }.toList()
    }

    fun getEngravingName()=binding.charSearchDetailEngravingName
    fun getEngravingPoint()=binding.charSearchDetailEngravingPoint

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailAbilityEngravingBookViewBinding {
        return CharSearchDetailAbilityEngravingBookViewBinding.inflate(inflater)
    }
}