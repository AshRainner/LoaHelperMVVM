package com.lostark.loahelper.customview

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.DailyItemViewBinding
import com.lostark.loahelper.databinding.RaidButtonViewBinding

class RaidButtonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<RaidButtonViewBinding>(
    context,
    attrs,
    defStyleAttr
) {
    override fun init(context: Context?){

    }

    override fun getAttrs(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.RaidButtonViewAttr)
        binding.raidButton.setText(typedArray.getText(R.styleable.RaidButtonViewAttr_raidButtonText))
        binding.raidButtonImage.setImageResource(typedArray.getResourceId(R.styleable.RaidButtonViewAttr_raidButtonImageSrc,R.drawable.raid_icon))
        typedArray.recycle()
    }

    override fun inflateBinding(inflater: LayoutInflater): RaidButtonViewBinding {
        return RaidButtonViewBinding.inflate(inflater)
    }
    public fun clickEvent(intent: Intent){
        binding.raidButton.setOnClickListener() {
            context.startActivity(intent)
        }
        binding.raidButtonImage.setOnClickListener(){
            context.startActivity(intent)
        }
    }
}