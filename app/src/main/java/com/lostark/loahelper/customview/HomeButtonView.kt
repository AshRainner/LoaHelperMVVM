package com.lostark.loahelper.customview

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.DailyItemViewBinding
import com.lostark.loahelper.databinding.MainButtonViewBinding
import com.lostark.loahelper.databinding.RaidButtonViewBinding

class HomeButtonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<MainButtonViewBinding>(
    context,
    attrs,
    defStyleAttr
) {

    override fun init(context: Context?){
    }

    override fun getAttrs(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.HomeButtonViewAttr)
        binding.homeButton.setText(typedArray.getText(R.styleable.HomeButtonViewAttr_buttonText))
        binding.homeButtonImage.setImageResource(typedArray.getResourceId(R.styleable.HomeButtonViewAttr_buttonImageSrc,R.drawable.raid_icon))
        typedArray.recycle()
    }
    public fun clickEvent(intent: Intent){
        binding.homeButton.setOnClickListener() {
            context.startActivity(intent)
        }
    }
    override fun inflateBinding(inflater: LayoutInflater): MainButtonViewBinding {
        return MainButtonViewBinding.inflate(inflater)
    }
}