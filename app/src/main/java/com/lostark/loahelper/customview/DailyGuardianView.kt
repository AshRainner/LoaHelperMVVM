package com.lostark.loahelper.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.DailyGuardianViewBinding

class DailyGuardianView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<DailyGuardianViewBinding>(
    context,
    attrs,
    defStyleAttr
) {

    override fun init(context: Context?){

    }

    override fun getAttrs(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.DailyGuardianViewAttr)
        binding.dailyGuardianPriceEdit.setText(typedArray.getText(R.styleable.DailyGuardianViewAttr_guardianPriceText))
        binding.dailyGuardianImage.setImageResource(typedArray.getResourceId(R.styleable.DailyGuardianViewAttr_guardianImageSrc,R.drawable.raid_icon))
        typedArray.recycle()
    }
    public fun setPrice(price:Double){
        binding.dailyGuardianPriceEdit.setText(price.toString())
    }
    public fun setImage(imageUrl:String){
        Glide.with(this).load(imageUrl).into(binding.dailyGuardianImage)
    }
    override fun inflateBinding(inflater: LayoutInflater): DailyGuardianViewBinding {
        return DailyGuardianViewBinding.inflate(inflater)
    }
}