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
import com.lostark.loahelper.databinding.CharSearchDetailAbilityEngravingBookViewBinding
import com.lostark.loahelper.databinding.CharSearchDetailAbilityEngravingViewBinding

class CharSearchEngravingBottomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailAbilityEngravingViewBinding>(
    context,
    attrs,
    defStyleAttr
) {


    lateinit var engravingString:String
    lateinit var engravingDrawerName:String

    lateinit var imageUrl: String


    override fun init(context: Context?) {

    }
    override fun getAttrs(attrs: AttributeSet?) {

    }
    fun setEngravingImageText(name:String,description: String,level:String,imageUrl:String){
        Glide.with(this)
            .load(imageUrl)
            .circleCrop()
            .into(binding.charSearchDetailBottomEngravingImage)
        this.imageUrl = imageUrl

        engravingString = description

        binding.charSearchDetailBottomEngravingName.text = level+" "+name

        engravingDrawerName =name+" Lv."+level
        if(name.contains("감소"))
            binding.charSearchDetailBottomEngravingName.setTextColor(Color.parseColor("#FF0044"))
    }

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailAbilityEngravingViewBinding {
       return CharSearchDetailAbilityEngravingViewBinding.inflate(inflater)
    }
}