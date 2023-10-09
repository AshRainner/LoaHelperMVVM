package com.lostark.loahelper.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchDetailAbilityAccessoryViewBinding
import com.lostark.loahelper.databinding.CharSearchDetailAvatarViewBinding

class CharSearchAvatarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailAvatarViewBinding>(
    context,
    attrs,
    defStyleAttr
) {

    var imageUrl = ""
    var defaultEffect = ""//기본효과
    var tendenciesEffect = ""//성향효과
    lateinit var avatarNameString: String//아바타이름
    lateinit var avatarTypeString: String//ex 무기 아바타
    lateinit var avatarGrade: String//아바타 등급
    lateinit var avatarLine: String//아바타 계열 ex 건슬링어 전용


    override fun init(context: Context?) {

    }

    override fun getAttrs(attrs: AttributeSet?) {
        binding.run {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AvatarViewAttr)
            charSearchDetailAvatarType.text = typedArray.getText(R.styleable.AvatarViewAttr_avatarTypeText)
            charSearchDetailAvatarTypeLeft.text = typedArray.getText(R.styleable.AvatarViewAttr_avatarTypeText)
            val avatarLeftRight =
                typedArray.getText(R.styleable.AvatarViewAttr_avatarLeftRight).toString()
            if (avatarLeftRight == "right") {
                charSearchDetailAvatarLeftLayout.visibility = View.GONE
                charSearchDetailAvatarRightLayout.visibility = View.VISIBLE
            } else if (avatarLeftRight == "left") {
                charSearchDetailAvatarLeftLayout.visibility = View.VISIBLE
                charSearchDetailAvatarRightLayout.visibility = View.GONE
            }
            typedArray.recycle()
        }
    }

    fun setImageBackground(grade: String) {
        avatarGrade = grade
        binding.run {
            when (grade) {//이미지 백그라운드
                "고대" -> {
                    charSearchDetailAvatarImage.setBackgroundResource(R.drawable.ancient_background)
                    charSearchDetailAvatarName.setTextColor(Color.parseColor("#d9ae43"))
                    charSearchDetailAvatarNameLeft.setTextColor(Color.parseColor("#d9ae43"))
                }
                "유물" -> {
                    charSearchDetailAvatarImage.setBackgroundResource(R.drawable.relic_background)
                    charSearchDetailAvatarName.setTextColor(Color.parseColor("#E45B0A"))
                    charSearchDetailAvatarNameLeft.setTextColor(Color.parseColor("#E45B0A"))

                }
                "전설" -> {
                    charSearchDetailAvatarImage.setBackgroundResource(R.drawable.legend_background)
                    charSearchDetailAvatarName.setTextColor(Color.parseColor("#E08808"))
                    charSearchDetailAvatarNameLeft.setTextColor(Color.parseColor("#E08808"))
                }
                "영웅" -> {
                    charSearchDetailAvatarImage.setBackgroundResource(R.drawable.hero_background)
                    charSearchDetailAvatarName.setTextColor(Color.parseColor("#A41ED4"))
                    charSearchDetailAvatarNameLeft.setTextColor(Color.parseColor("#A41ED4"))

                }
                "희귀" -> {
                    charSearchDetailAvatarImage.setBackgroundResource(R.drawable.rare_background)
                    charSearchDetailAvatarName.setTextColor(Color.parseColor("#268AD3"))
                    charSearchDetailAvatarNameLeft.setTextColor(Color.parseColor("#268AD3"))
                }
                "고급" -> {
                    charSearchDetailAvatarImage.setBackgroundResource(R.drawable.advanced_background)
                    charSearchDetailAvatarName.setTextColor(Color.parseColor("#8FDB32"))
                    charSearchDetailAvatarNameLeft.setTextColor(Color.parseColor("#8FDB32"))
                }
            }
        }
    }

    fun setImageText(avatar: com.lostark.loahelper.dto.armorys.ArmoryAvatar, tooltip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip) {
        binding.run {
            imageUrl = avatar.icon

            Glide.with(this@CharSearchAvatarView).load(avatar.icon).into(charSearchDetailAvatarImage)

            setImageBackground(avatar.grade)

            if (avatar.type.contains("상의") || avatar.type.contains("하의") || avatar.type.contains("악기") || avatar.type.contains(
                    "얼굴1"
                )
            ) {
                charSearchDetailAvatarRightLayout.visibility = View.GONE
                charSearchDetailAvatarLeftLayout.visibility = View.VISIBLE
                charSearchDetailAvatarNameLeft.visibility = View.VISIBLE
                charSearchDetailAvatarNameLeft.text = avatar.name
            } else {
                charSearchDetailAvatarRightLayout.visibility = View.VISIBLE
                charSearchDetailAvatarLeftLayout.visibility = View.GONE
                charSearchDetailAvatarName.visibility = View.VISIBLE
                charSearchDetailAvatarName.text = avatar.name
            }

            avatarNameString = avatar.name

            avatarTypeString = avatar.type

            avatarLine = tooltip.elements.get("Element_002")?.value.toString()

            val itemPartKeyList =
                tooltip.elements.filter { it.value.type == "ItemPartBox" }.keys.toList()
            if (itemPartKeyList.isNotEmpty()) {
                defaultEffect =
                    (tooltip.elements.get(itemPartKeyList.get(0))?.value as com.lostark.loahelper.dto.armorys.tooltips.ItemPartData).element1
            }

            val pattern = "지성 : \\d+|담력 : \\d+|매력 : \\d+|친절 : \\d+".toRegex()
            var symbolString: String? = null
            val symbolStringList =
                tooltip.elements.filter { it.value.type == "SymbolString" }?.keys?.toList()
            if (symbolStringList?.isNotEmpty() ?: false) {
                symbolString =
                    (tooltip.elements.get(symbolStringList?.get(0))?.value as com.lostark.loahelper.dto.armorys.tooltips.SymbolStringData).contentStr
            }
            //val symbolString = (tooltip.elements?.get(tooltip.elements.filter { it.value.type=="SymbolString"}?.keys?.toList()?.get(0))?.value as SymbolStringData)?.contentStr
            symbolString?.let {
                pattern.findAll(it)?.forEach {
                    tendenciesEffect += it.value.replace(": ", "+") + "\n"
                }
            }
            if (tendenciesEffect.isNotEmpty())
                tendenciesEffect = tendenciesEffect.substring(0, tendenciesEffect.length - 1)
        }
    }

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailAvatarViewBinding {
        return CharSearchDetailAvatarViewBinding.inflate(inflater)
    }

}