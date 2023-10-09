package com.lostark.loahelper.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchDetailCharactersGridItemViewBinding
import com.lostark.loahelper.databinding.CharSearchDetailCollectionEquItemBinding

class CharSearchCollectionEquipmentView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailCollectionEquItemBinding>(
    context,
    attrs,
    defStyleAttr
) {


    lateinit var itemName:String
    lateinit var imageUrl:String
    lateinit var itemGrade:String
    lateinit var effectString:String
    lateinit var descriptionString:String


    override fun init(context: Context?) {

    }

    fun setBackground(grade:String){
        when(grade) {//이미지 백그라운드
            "고대" -> {
                binding.charSearchDetailCollectionEquImage.setBackgroundResource(R.drawable.ancient_background)
                binding.charSearchDetailCollectionEquText.setTextColor(Color.parseColor("#d9ae43"))
            }
            "유물" -> {
                binding.charSearchDetailCollectionEquImage.setBackgroundResource(R.drawable.relic_background)
                binding.charSearchDetailCollectionEquText.setTextColor(Color.parseColor("#E45B0A"))

            }
            "전설" -> {
                binding.charSearchDetailCollectionEquImage.setBackgroundResource(R.drawable.legend_background)
                binding.charSearchDetailCollectionEquText.setTextColor(Color.parseColor("#E08808"))
            }
            "영웅" -> {
                binding.charSearchDetailCollectionEquImage.setBackgroundResource(R.drawable.hero_background)
                binding.charSearchDetailCollectionEquText.setTextColor(Color.parseColor("#A41ED4"))

            }
            "희귀" -> {
                binding.charSearchDetailCollectionEquImage.setBackgroundResource(R.drawable.rare_background)
                binding.charSearchDetailCollectionEquText.setTextColor(Color.parseColor("#268AD3"))
            }
        }
    }

    fun setTextImage(armory: com.lostark.loahelper.dto.armorys.ArmoryEquipment, toolTip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip){
        Glide.with(this).load(armory.icon).into(binding.charSearchDetailCollectionEquImage)
        setBackground(armory.grade)
        binding.charSearchDetailCollectionEquText.text=armory.grade+" "+armory.type

        itemName=armory.name
        itemGrade = armory.grade
        imageUrl=armory.icon
        effectString=(toolTip.elements.get("Element_004")?.value as com.lostark.loahelper.dto.armorys.tooltips.ItemPartData).element1
        descriptionString=toolTip.elements.get("Element_005")?.value.toString()
    }

    override fun getAttrs(attrs: AttributeSet?) {
    }

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailCollectionEquItemBinding {
        return CharSearchDetailCollectionEquItemBinding.inflate(inflater)
    }
}