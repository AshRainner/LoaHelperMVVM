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

class CharSearchAvatarView : LinearLayout {


    lateinit var leftLayout: LinearLayout
    lateinit var rightLayout: LinearLayout
    lateinit var avatarImage: ImageView
    lateinit var avatarName: TextView
    lateinit var avatarType: TextView
    lateinit var avatarNameLeft: TextView
    lateinit var avatarTypeLeft: TextView

    var imageUrl = ""
    var defaultEffect = ""//기본효과
    var tendenciesEffect = ""//성향효과
    lateinit var avatarNameString: String//아바타이름
    lateinit var avatarTypeString: String//ex 무기 아바타
    lateinit var avatarGrade: String//아바타 등급
    lateinit var avatarLine: String//아바타 계열 ex 건슬링어 전용

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
        getAttrs(attrs)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.char_search_detail_avatar_view, this, false)
        addView(view)
        avatarImage = view.findViewById(R.id.char_search_detail_avatar_image)
        avatarName = view.findViewById(R.id.char_search_detail_avatar_name)
        avatarType = view.findViewById(R.id.char_search_detail_avatar_type)
        avatarNameLeft = view.findViewById(R.id.char_search_detail_avatar_name_left)
        avatarTypeLeft = view.findViewById(R.id.char_search_detail_avatar_type_left)
        leftLayout = view.findViewById(R.id.char_search_detail_avatar_left_layout)
        rightLayout = view.findViewById(R.id.char_search_detail_avatar_right_layout)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AvatarViewAttr)
        avatarType.text = typedArray.getText(R.styleable.AvatarViewAttr_avatarTypeText)
        avatarTypeLeft.text = typedArray.getText(R.styleable.AvatarViewAttr_avatarTypeText)
        val avatarLeftRight =
            typedArray.getText(R.styleable.AvatarViewAttr_avatarLeftRight).toString()
        if (avatarLeftRight == "right") {
            leftLayout.visibility = View.GONE
            rightLayout.visibility = View.VISIBLE
        } else if (avatarLeftRight == "left") {
            leftLayout.visibility = View.VISIBLE
            rightLayout.visibility = View.GONE
        }
        typedArray.recycle()
    }

    fun setImageBackground(grade: String) {
        avatarGrade = grade
        when (grade) {//이미지 백그라운드
            "고대" -> {
                avatarImage.setBackgroundResource(R.drawable.ancient_background)
                avatarName.setTextColor(Color.parseColor("#d9ae43"))
                avatarNameLeft.setTextColor(Color.parseColor("#d9ae43"))
            }
            "유물" -> {
                avatarImage.setBackgroundResource(R.drawable.relic_background)
                avatarName.setTextColor(Color.parseColor("#E45B0A"))
                avatarNameLeft.setTextColor(Color.parseColor("#E45B0A"))

            }
            "전설" -> {
                avatarImage.setBackgroundResource(R.drawable.legend_background)
                avatarName.setTextColor(Color.parseColor("#E08808"))
                avatarNameLeft.setTextColor(Color.parseColor("#E08808"))
            }
            "영웅" -> {
                avatarImage.setBackgroundResource(R.drawable.hero_background)
                avatarName.setTextColor(Color.parseColor("#A41ED4"))
                avatarNameLeft.setTextColor(Color.parseColor("#A41ED4"))

            }
            "희귀" -> {
                avatarImage.setBackgroundResource(R.drawable.rare_background)
                avatarName.setTextColor(Color.parseColor("#268AD3"))
                avatarNameLeft.setTextColor(Color.parseColor("#268AD3"))
            }
            "고급" -> {
                avatarImage.setBackgroundResource(R.drawable.advanced_background)
                avatarName.setTextColor(Color.parseColor("#8FDB32"))
                avatarNameLeft.setTextColor(Color.parseColor("#8FDB32"))
            }
        }
    }

    fun setImageText(avatar: com.lostark.loahelper.dto.armorys.ArmoryAvatar, tooltip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip) {
        imageUrl = avatar.icon

        Glide.with(this).load(avatar.icon).into(avatarImage)

        setImageBackground(avatar.grade)

        if (avatar.type.contains("상의") || avatar.type.contains("하의") || avatar.type.contains("악기") || avatar.type.contains(
                "얼굴1"
            )
        ) {
            rightLayout.visibility = View.GONE
            leftLayout.visibility = View.VISIBLE
            avatarNameLeft.visibility = View.VISIBLE
            avatarNameLeft.text = avatar.name
        } else {
            rightLayout.visibility = View.VISIBLE
            leftLayout.visibility = View.GONE
            avatarName.visibility = View.VISIBLE
            avatarName.text = avatar.name
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
        val tendenciesList = symbolString?.let {
            pattern.findAll(it)?.forEach {
                tendenciesEffect += it.value.replace(": ", "+") + "\n"
            }
        }
        if (tendenciesEffect.isNotEmpty())
            tendenciesEffect = tendenciesEffect.substring(0, tendenciesEffect.length - 1)
    }
}