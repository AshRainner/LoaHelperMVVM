package com.lostark.loahelper.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchDetailCollectionEquItemBinding
import com.lostark.loahelper.databinding.CharSearchDetailCollectionItemViewBinding

class CharSearchCollectionItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailCollectionItemViewBinding>(
    context,
    attrs,
    defStyleAttr
) {
    override fun getAttrs(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.CollectionItemViewAttr)
        binding.collectionName.text = typedArray.getText(R.styleable.CollectionItemViewAttr_collectionName)
        typedArray.recycle()
    }

    override fun init(context: Context?) {

    }

    fun selected(select:Boolean){
        binding.run {
            if (select) {
                collectionAchievementProgress.setProgressColor(Color.parseColor("#f0f0f0"))
                collectionName.setTextColor(Color.parseColor("#ffffff"))
                collectionPercent.setTextColor(Color.parseColor("#ffffff"))
                collectionHaveItem.setTextColor(Color.parseColor("#ffffff"))
                collectionBackgroundImage.visibility = View.VISIBLE
                collectionItemLayout.setBackgroundColor(Color.parseColor("#808b00ff"))
            } else {
                collectionAchievementProgress.setProgressColor(Color.parseColor("#8b00ff"))
                collectionName.setTextColor(Color.parseColor("#000000"))
                collectionPercent.setTextColor(Color.parseColor("#000000"))
                collectionHaveItem.setTextColor(Color.parseColor("#000000"))
                collectionBackgroundImage.visibility = View.INVISIBLE
                collectionItemLayout.setBackgroundColor(Color.parseColor("#008b00ff"))
            }
        }
    }
    fun getBackgroundImage()=binding.collectionBackgroundImage
    fun getProgressBar()=binding.collectionAchievementProgress
    fun getHave()=binding.collectionHaveItem
    fun getPercent()=binding.collectionPercent

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailCollectionItemViewBinding {
        return CharSearchDetailCollectionItemViewBinding.inflate(inflater)
    }
}