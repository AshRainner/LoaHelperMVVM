package com.lostark.loahelper

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.GsonBuilder
import com.lostark.adapter.CharSearchViewPagerAdapter
import com.lostark.adapter.ValueDataAdapter
import com.lostark.customview.CharSearchAccessoryView
import com.lostark.customview.CharSearchArmorView
import com.lostark.dto.armorys.Armories
import com.lostark.dto.armorys.tooltips.Tooltip
import com.lostark.dto.armorys.tooltips.ValueData
import java.io.Serializable
import java.util.*

class SearchDetailActivity : AppCompatActivity() {
    /*
    *  "Classes": [
    "버서커",
    "디스트로이어",
    "워로드",
    "홀리나이트",
    "슬레이어",
    "아르카나",
    "서머너",
    "바드",
    "소서리스",
    "배틀마스터",
    "인파이터",
    "기공사",
    "창술사",
    "스트라이커",
    "블레이드",
    "데모닉",
    "리퍼",
    "호크아이",
    "데빌헌터",
    "블래스터",
    "스카우터",
    "건슬링어",
    "도화가",
    "기상술사"
  ]*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.char_search_detail_activity)
        val charInfo = intent.getSerializable("charInfo") as Armories?
        val itemLevel = charInfo!!.armoryProfile.itemMaxLevel.replace(",","").toFloat().toInt()
        val charImgUrl = charInfo.armoryProfile.characterImage

        charGradationSet(itemLevel)
        charImageSet(charImgUrl)
        charInfoSet(charInfo)
        setFragment(charInfo)
        //testGson(charInfo)
    }
    fun testGson(charInfo:Armories){
        val gson = GsonBuilder()
            .registerTypeAdapter(ValueData::class.java, ValueDataAdapter())
            .create()

        val jsonString = "{\n\"Elements\":\n"+charInfo.armoryEquipment.get(1).tooltip+"\n}"
        val tooltip = gson.fromJson(jsonString, Tooltip::class.java)
        tooltip.elements.forEach { (key, value) ->
            println("Element: $key")
            println("Type: ${value.type}")
            println("Value: ${value.value}")
            println()
        }

    }

    fun charInfoSet(charInfo:Armories){
        val serverName = findViewById<TextView>(R.id.search_detail_server_name)
        val charName = findViewById<TextView>(R.id.search_detail_char_name)
        val itemLevel = findViewById<TextView>(R.id.search_detail_char_item_level)
        val level = findViewById<TextView>(R.id.search_detail_char_level)
        val expedition = findViewById<TextView>(R.id.search_detail_expedition_level)
        val title = findViewById<TextView>(R.id.search_detail_char_title)
        val guild = findViewById<TextView>(R.id.search_detail_char_guild)
        val pvp = findViewById<TextView>(R.id.search_detail_char_pvp)
        val territory = findViewById<TextView>(R.id.search_detail_territory)
        val _class = findViewById<ImageView>(R.id.search_detail_char_class)

        serverName.text=charInfo.armoryProfile.serverName
        charName.text=charInfo.armoryProfile.characterName
        itemLevel.text="아이템 : "+charInfo.armoryProfile.itemMaxLevel
        level.text= "전투 : Lv."+charInfo.armoryProfile.characterLevel.toString()
        expedition.text="원정대 : Lv."+charInfo.armoryProfile.expeditionLevel
        title.text="칭호 : "+charInfo.armoryProfile.title
        guild.text = "길드 : "+charInfo.armoryProfile.guildName
        pvp.text = "PVP : "+charInfo.armoryProfile.pvpGradeName
        territory.text = "영지 : Lv."+charInfo.armoryProfile.townLevel+" "+charInfo.armoryProfile.townName

        when(charInfo.armoryProfile.characterClassName){
            "버서커"->_class.setImageResource(R.drawable.class_berserker)
            "디스트로이어"->_class.setImageResource(R.drawable.class_destroyer)
            "워로드"->_class.setImageResource(R.drawable.class_warload)
            "홀리나이트"->_class.setImageResource(R.drawable.class_holyknight)
            "슬레이어"->_class.setImageResource(R.drawable.class_slayer)
            "아르카나"->_class.setImageResource(R.drawable.class_arcana)
            "서머너"->_class.setImageResource(R.drawable.class_summoner)
            "바드"->_class.setImageResource(R.drawable.class_bard)
            "소서리스"->_class.setImageResource(R.drawable.class_sorceress)
            "배틀마스터"->_class.setImageResource(R.drawable.class_battlemaster)
            "인파이터"->_class.setImageResource(R.drawable.class_infighter)
            "기공사"->_class.setImageResource(R.drawable.class_soulfist)
            "창술사"->_class.setImageResource(R.drawable.class_glaivier)
            "스트라이커"->_class.setImageResource(R.drawable.class_striker)
            "블레이드"->_class.setImageResource(R.drawable.class_blade)
            "데모닉"->_class.setImageResource(R.drawable.class_shadowhunter)
            "리퍼"->_class.setImageResource(R.drawable.class_reaper)
            "호크아이"->_class.setImageResource(R.drawable.class_horkeye)
            "데빌헌터"->_class.setImageResource(R.drawable.class_devilhunter)
            "블래스터"->_class.setImageResource(R.drawable.class_blaster)
            "스카우터"->_class.setImageResource(R.drawable.class_scouter)
            "건슬링어"->_class.setImageResource(R.drawable.class_gunslinger)
            "도화가"->_class.setImageResource(R.drawable.class_artist)
            "기상술사"->_class.setImageResource(R.drawable.class_aeromancer)
            else ->_class.setImageResource(R.drawable.class_gunslinger)
        }

    }


    fun charImageSet(charImgUrl:String?) {
        val charImage = findViewById<ImageView>(R.id.search_detail_char_img)
        /*Glide.with(this)
            .load(charImgUrl)
            .into(charImage)*/
        /*Log.d(
            "charImage.layoutParams : ",
            "width : {${charImage.layoutParams.width}} height : {${charImage.layoutParams.height}}"
        )*/
        charImgUrl?.let {
            Glide.with(this)
                .asBitmap()
                .load(charImgUrl)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        // 이미지를 설정
                        val x = 25
                        val y = 50 // 원하는 부분의 y 좌표
                        val croppedBitmap = Bitmap.createBitmap(
                            resource,
                            x,
                            y,
                            resource.width - 200,
                            resource.height - 350
                        )
                        charImage.setImageBitmap(croppedBitmap)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // Do nothing
                    }
                })
        }?:{

        }
    }
    fun setFragment(charInfo: Armories){
        val viewPager = findViewById<ViewPager2>(R.id.search_detail_view_pager)
        val abilityFragment = AbilityFragment(charInfo)
        val viewPagerAdapter = CharSearchViewPagerAdapter(this)
        viewPagerAdapter.addFragment(abilityFragment)
        viewPager.adapter=viewPagerAdapter
    }

    fun charGradationSet(level:Int) {
        val gradationImageView = findViewById<ImageView>(R.id.search_detail_char_gra)

        val random = Random(level.toLong())

        val red = random.nextInt(100)+25
        val green = random.nextInt(50)+10
        val blue = random.nextInt(100)+25

        val selectedColor = Color.rgb(red, green, blue) // R, G, B 값을 사용하여 RGB 색상 생성
        val drawable = createGradientDrawable(Color.parseColor("#${Integer.toHexString(selectedColor).substring(2)}"))
        gradationImageView.background = drawable
    }
    fun createGradientDrawable(startColor: Int): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        drawable.colors = intArrayOf(startColor,0x00000000, 0x00000000)
        drawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT
        return drawable
    }

    fun setArmorDialog(view:CharSearchArmorView,armorLayout:LinearLayout,elixirSpecialDetailString:String?){
        val armorName = armorLayout.findViewById<TextView>(R.id.char_search_detail_drawer_armor_name)
        val armorImage = armorLayout.findViewById<ImageView>(R.id.char_search_detail_drawer_armor_image)
        armorName.text = view.armorName.text
        armorName.setTextColor(view.armorName.textColors)
        armorImage.background = view.armorImage.background
        Glide.with(this)
            .load(view.imageUrl)
            .into(armorImage)

        val armorDetailType = armorLayout.findViewById<TextView>(R.id.char_search_detail_drawer_armor_detail_type)
        val armorDetail = armorLayout.findViewById<TextView>(R.id.char_search_detail_drawer_armor_detail)
        armorDetailType.text = view.itemDetailType
        armorDetailType.setTextColor(view.armorName.currentTextColor)
        armorDetail.text = view.itemDetail

        val armorQuality = armorLayout.findViewById<TextView>(R.id.char_search_detail_drawer_armor_quality)
        val armorQualityProgressBar = armorLayout.findViewById<ProgressBar>(R.id.char_search_detail_drawer_armor_quality_progress)

        armorQuality.text=view.armorQuality.text
        armorQualityProgressBar.progress = view.armorQuality.text.toString().toInt()
        armorQualityProgressBar.progressTintList = ColorStateList.valueOf((view.armorQuality.background as ColorDrawable).color)

        val armorDefaultEffect = armorLayout.findViewById<TextView>(R.id.char_search_detail_drawer_armor_default_effect)
        view.defaultEffect?.let {
            armorDefaultEffect.text = view.defaultEffect
        }?: run{
            armorDefaultEffect.visibility = View.GONE
        }
        val additionalEffectLayout = armorLayout.findViewById<LinearLayout>(R.id.char_search_detail_drawer_armor_additional_effect_layout)

        val armorAdditionalEffect = armorLayout.findViewById<TextView>(R.id.char_search_detail_drawer_armor_additional_effect)

        view.additionalEffect?.let {
            armorAdditionalEffect.text = view.additionalEffect
        }?: run{
            additionalEffectLayout.visibility = View.GONE
        }

        val elixirLayout = armorLayout.findViewById<LinearLayout>(R.id.char_search_detail_drawer_armor_elixir_layout)

        val armorElixir1 = armorLayout.findViewById<TextView>(R.id.char_search_detail_drawer_elixir_1)
        val armorElixir2 = armorLayout.findViewById<TextView>(R.id.char_search_detail_drawer_elixir_2)
        val armorElixir3 = armorLayout.findViewById<TextView>(R.id.char_search_detail_drawer_elixir_3)

        view.elixirData?.let {
            val pattern = "\\[(.*)\\]\\s".toRegex()
            armorElixir1.text = pattern.replace(it.Element_000?.contentStr!!,"")

            if (it.Element_001==null){
                armorElixir2.visibility=View.GONE
            }
            else {
                armorElixir2.text = pattern.replace(it.Element_001?.contentStr!!,"")
            }
        }?: run{
            elixirLayout.visibility=View.GONE
        }

        elixirSpecialDetailString?.let {
            armorElixir3.text=it
        }?: run{
            armorElixir3.visibility=View.GONE
        }

        val armorSetLevel = armorLayout.findViewById<TextView>(R.id.char_search_detail_drawer_set_level)

        view.setLevel?.let {
            armorSetLevel.text=it
        }?: run{
            armorSetLevel.visibility=View.GONE
        }
    }

    fun openDialog(view:Any,elixirSpecialDetailString:String?){
        val armorDialog = BottomSheetDialog(this)
        val armorDialogView = layoutInflater.inflate(R.layout.char_search_detail_drawer,null)
        val armorDialogOkButton = armorDialogView.findViewById<Button>(R.id.char_search_detail_drawer_button)
        armorDialog.setContentView(armorDialogView)
        armorDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED // 나올 때 전체 크기많큼 다 나오기
        armorDialog.behavior.isHideable = false //밑으로 드래그해서 끄는거 막기
        armorDialog.behavior.isDraggable=false //드래그 막기
        val armorLayout = armorDialogView.findViewById<LinearLayout>(R.id.armor_drawer)
        when(view){
            is CharSearchArmorView ->{
                setArmorDialog(view,armorLayout,elixirSpecialDetailString)
            }
            is CharSearchAccessoryView ->{

            }
            is CharSearchAccessoryView -> {

            }
        }
        armorDialogOkButton.setOnClickListener{
            armorDialog.dismiss()
        }
        armorDialog.show()


    }

    inline fun <reified T : Serializable> Intent.getSerializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as T
    }
}