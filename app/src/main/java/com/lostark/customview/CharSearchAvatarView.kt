package com.lostark.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.lostark.adapter.ValueDataAdapter
import com.lostark.dto.armorys.*
import com.lostark.dto.armorys.tooltips.*
import com.lostark.loahelper.R
import com.lostark.loahelper.SearchDetailActivity

class CharSearchAvatarView : LinearLayout {


    lateinit var leftLayout:LinearLayout
    lateinit var rightLayout:LinearLayout
    lateinit var avatarImage: ImageView
    lateinit var avatarName: TextView
    lateinit var avatarType: TextView
    lateinit var avatarNameLeft: TextView
    lateinit var avatarTypeLeft: TextView

    lateinit var imageUrl: String
    lateinit var itemDescription: String
    lateinit var defaultEffect: String//기본효과
    lateinit var additionalEffect: String//추가효과
    lateinit var avatarTypeString:String

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

    private fun getAttrs(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.AvatarViewAttr)
        avatarType.text = typedArray.getText(R.styleable.AvatarViewAttr_avatarTypeText)
        avatarTypeLeft.text = typedArray.getText(R.styleable.AvatarViewAttr_avatarTypeText)
        val avatarLeftRight = typedArray.getText(R.styleable.AvatarViewAttr_avatarLeftRight).toString()
        if(avatarLeftRight== "right"){
            leftLayout.visibility = View.GONE
            rightLayout.visibility = View.VISIBLE
        }else if(avatarLeftRight == "left"){
            leftLayout.visibility = View.VISIBLE
            rightLayout.visibility = View.GONE
        }
        typedArray.recycle()
    }

    fun setImageBackground(grade : String){
        when(grade) {//이미지 백그라운드
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

    fun setImageText(avatar: ArmoryAvatar,tooltip:Tooltip?) {
        imageUrl=avatar.icon

        Glide.with(this).load(avatar.icon).into(avatarImage)

        setImageBackground(avatar.grade)

        if(avatar.type.contains("상의")||avatar.type.contains("하의")||avatar.type.contains("악기")||avatar.type.contains("얼굴1")){
            rightLayout.visibility=View.GONE
            leftLayout.visibility=View.VISIBLE
            avatarNameLeft.visibility= View.VISIBLE
            avatarNameLeft.text = avatar.name
        }
        else{
            rightLayout.visibility=View.VISIBLE
            leftLayout.visibility=View.GONE
            avatarName.visibility=View.VISIBLE
            avatarName.text = avatar.name
        }

    }
}