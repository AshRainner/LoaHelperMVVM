package com.lostark.loahelper

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.util.Xml
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.lostark.adapter.RecentNameListAdapter
import com.lostark.api.LoaRetrofitObj
import com.lostark.callbackinterface.RecentDeleteButtonClick
import com.lostark.database.AppDatabase
import com.lostark.database.table.Notice
import com.lostark.database.table.RecentCharInfo
import com.lostark.dto.characters.Characters
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.exp

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
        val charInfo = intent.getSerializable("charInfo") as Characters?
        val itemLevel = charInfo!!.armoryProfile.itemMaxLevel.replace(",","").toFloat().toInt()
        val charImgUrl = charInfo.armoryProfile.characterImage

        charGradationSet(itemLevel)
        charImageSet(charImgUrl)
        charInfoSet(charInfo)
    }

    fun charInfoSet(charInfo:Characters){
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


    fun charImageSet(charImgUrl:String) {
        val charImage = findViewById<ImageView>(R.id.search_detail_char_img)
        /*Glide.with(this)
            .load(charImgUrl)
            .into(charImage)*/
        Log.d(
            "charImage.layoutParams : ",
            "width : {${charImage.layoutParams.width}} height : {${charImage.layoutParams.height}}"
        )

        Glide.with(this)
            .asBitmap()
            .load(charImgUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // 이미지를 설정
                    val x = 25
                    val y = 50 // 원하는 부분의 y 좌표
                    val croppedBitmap = Bitmap.createBitmap(resource, x, y, resource.width-200, resource.height-350)
                    charImage.setImageBitmap(croppedBitmap)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Do nothing
                }
            })
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
    inline fun <reified T : Serializable> Intent.getSerializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as T
    }
}