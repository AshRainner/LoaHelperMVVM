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
import com.lostark.loahelper.databinding.DailyItemViewBinding

class DailyItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<DailyItemViewBinding>(
    context,
    attrs,
    defStyleAttr
) {

    override fun init(context: Context?){
    }

    override fun getAttrs(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.DailyItemViewAttr)
        binding.dailyItemPriceEdit.setText(typedArray.getText(R.styleable.DailyItemViewAttr_itemPriceText))
        binding.dailyItemImage.setImageResource(typedArray.getResourceId(R.styleable.DailyItemViewAttr_itemImageSrc,R.drawable.raid_icon))
        binding.dailyItemImage.setBackgroundResource(typedArray.getResourceId(R.styleable.DailyItemViewAttr_itemImageBackground,0))
        typedArray.recycle()
    }
    public fun setPrice(price:Double){
        binding.dailyItemPriceEdit.setText(price.toString())
    }
    public fun setImage(imageUrl:String){
        Glide.with(this).load(imageUrl).into( binding.dailyItemImage)
    }
    public fun getEditText():EditText{
        return binding.dailyItemPriceEdit
    }
    override fun inflateBinding(inflater: LayoutInflater): DailyItemViewBinding {
        return DailyItemViewBinding.inflate(inflater)
    }
}