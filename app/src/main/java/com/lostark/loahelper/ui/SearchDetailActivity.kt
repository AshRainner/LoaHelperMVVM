package com.lostark.loahelper.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import com.amar.library.ui.StickyScrollView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.lostark.loahelper.customview.*
import com.lostark.loahelper.R
import com.lostark.loahelper.database.AppDatabase
import com.lostark.loahelper.databinding.CharSearchActivityBinding
import com.lostark.loahelper.databinding.CharSearchDetailAccessoryDialogBinding
import com.lostark.loahelper.databinding.CharSearchDetailActivityBinding
import com.lostark.loahelper.databinding.CharSearchDetailArmorDialogBinding
import com.lostark.loahelper.databinding.CharSearchDetailAvatarDialogBinding
import com.lostark.loahelper.databinding.CharSearchDetailCardDialogBinding
import com.lostark.loahelper.databinding.CharSearchDetailCollectionEquDialogBinding
import com.lostark.loahelper.databinding.CharSearchDetailDrawerBinding
import com.lostark.loahelper.databinding.CharSearchDetailEngravingDialogBinding
import com.lostark.loahelper.databinding.CharSearchDetailGemDialogBinding
import com.lostark.loahelper.databinding.CharSearchDetailRuneDialogBinding
import com.lostark.loahelper.databinding.CharSearchDetailSkillDialogBinding
import com.lostark.loahelper.databinding.CharSearchDetailTripodDialogBinding
import com.lostark.loahelper.databinding.RaidActivityBinding
import com.lostark.loahelper.dto.armorys.Armories
import com.lostark.loahelper.dto.characters.CharactersInfo
import com.lostark.loahelper.viewmodel.DataViewModel
import java.io.Serializable
import java.util.*

class SearchDetailActivity : BaseActivity<CharSearchDetailActivityBinding>() {
    lateinit var charInfo: Armories
    lateinit var characters: ArrayList<CharactersInfo>
    private val dataViewModel: DataViewModel by provideViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.char_search_detail_activity)
        initBinding(CharSearchDetailActivityBinding::inflate)
        charInfo = (intent.getSerializable("charInfo") as Armories?)!!
        characters = (intent.getSerializable("characters") as? ArrayList<CharactersInfo>?)!!
        val itemLevel = charInfo.armoryProfile.itemMaxLevel.replace(",", "").toFloat().toInt()
        val charImgUrl = charInfo.armoryProfile.characterImage
        charGradationSet(itemLevel)
        charImageSet(charImgUrl)
        charInfoSet()
        setFragment()
        binding.searchDetailScrollView.post {
            binding.searchDetailScrollView.scrollTo(0, 0)
        }
    }


    fun charInfoSet() {
        binding.run {
            searchDetailServerName.text = charInfo.armoryProfile.serverName
            searchDetailCharName.text = charInfo.armoryProfile.characterName
            searchDetailCharItemLevel.text = "아이템 : " + charInfo.armoryProfile.itemMaxLevel
            searchDetailCharLevel.text =
                "전투 : Lv." + charInfo.armoryProfile.characterLevel.toString()
            searchDetailExpeditionLevel.text = "원정대 : Lv." + charInfo.armoryProfile.expeditionLevel
            searchDetailCharTitle.text = "칭호 : " + charInfo.armoryProfile.title
            searchDetailCharGuild.text = "길드 : " + charInfo.armoryProfile.guildName
            searchDetailCharPvp.text = "PVP : " + charInfo.armoryProfile.pvpGradeName
            searchDetailTerritory.text =
                "영지 : Lv." + charInfo.armoryProfile.townLevel + " " + charInfo.armoryProfile.townName
            when (charInfo.armoryProfile.characterClassName) {
                "버서커" -> searchDetailCharClass.setImageResource(R.drawable.class_berserker)
                "디스트로이어" -> searchDetailCharClass.setImageResource(R.drawable.class_destroyer)
                "워로드" -> searchDetailCharClass.setImageResource(R.drawable.class_warload)
                "홀리나이트" -> searchDetailCharClass.setImageResource(R.drawable.class_holyknight)
                "슬레이어" -> searchDetailCharClass.setImageResource(R.drawable.class_slayer)
                "아르카나" -> searchDetailCharClass.setImageResource(R.drawable.class_arcana)
                "서머너" -> searchDetailCharClass.setImageResource(R.drawable.class_summoner)
                "바드" -> searchDetailCharClass.setImageResource(R.drawable.class_bard)
                "소서리스" -> searchDetailCharClass.setImageResource(R.drawable.class_sorceress)
                "배틀마스터" -> searchDetailCharClass.setImageResource(R.drawable.class_battlemaster)
                "인파이터" -> searchDetailCharClass.setImageResource(R.drawable.class_infighter)
                "기공사" -> searchDetailCharClass.setImageResource(R.drawable.class_soulfist)
                "창술사" -> searchDetailCharClass.setImageResource(R.drawable.class_glaivier)
                "스트라이커" -> searchDetailCharClass.setImageResource(R.drawable.class_striker)
                "블레이드" -> searchDetailCharClass.setImageResource(R.drawable.class_blade)
                "데모닉" -> searchDetailCharClass.setImageResource(R.drawable.class_shadowhunter)
                "리퍼" -> searchDetailCharClass.setImageResource(R.drawable.class_reaper)
                "호크아이" -> searchDetailCharClass.setImageResource(R.drawable.class_horkeye)
                "데빌헌터" -> searchDetailCharClass.setImageResource(R.drawable.class_devilhunter)
                "블래스터" -> searchDetailCharClass.setImageResource(R.drawable.class_blaster)
                "스카우터" -> searchDetailCharClass.setImageResource(R.drawable.class_scouter)
                "건슬링어" -> searchDetailCharClass.setImageResource(R.drawable.class_gunslinger)
                "도화가" -> searchDetailCharClass.setImageResource(R.drawable.class_artist)
                "기상술사" -> searchDetailCharClass.setImageResource(R.drawable.class_aeromancer)
                else -> searchDetailCharClass.setImageResource(R.drawable.class_gunslinger)
            }

        }


    }


    fun charImageSet(charImgUrl: String?) {
        binding.run {
            searchDetailCharImg?.let {
                Glide.with(this@SearchDetailActivity)
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
                            searchDetailCharImg.setImageBitmap(croppedBitmap)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            // Do nothing
                        }
                    })
            } ?: {

            }
        }
        /*Glide.with(this)
            .load(charImgUrl)
            .into(charImage)*/
        /*Log.d(
            "charImage.layoutParams : ",
            "width : {${charImage.layoutParams.width}} height : {${charImage.layoutParams.height}}"
        )*/

    }

    fun setFragment() {
        val abilityFragment = AbilityFragment(charInfo)
        val skillsFragment = SkillFragment(charInfo)
        val characterFragment = CharactersFragment(characters, charInfo)
        val avatarFragment = AvatarFragment(charInfo)
        val collectionFragment = CollectionFragment(charInfo)
        val fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction()
            .replace(R.id.search_detail_fragment_container, abilityFragment).commit()

        binding.searchDetailTab.addTab(binding.searchDetailTab.newTab().setText("능력치"))
        binding.searchDetailTab.addTab(binding.searchDetailTab.newTab().setText("스킬"))
        binding.searchDetailTab.addTab(binding.searchDetailTab.newTab().setText("아바타"))
        binding.searchDetailTab.addTab(binding.searchDetailTab.newTab().setText("수집형 포인트"))
        binding.searchDetailTab.addTab(binding.searchDetailTab.newTab().setText("보유 캐릭터"))


        binding.searchDetailTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val selectedFragment = when (tab?.position) {
                    0 -> abilityFragment
                    1 -> skillsFragment
                    2 -> avatarFragment
                    3 -> collectionFragment
                    4 -> characterFragment
                    else -> null
                }

                selectedFragment?.let {
                    fragmentManager.beginTransaction()
                        .replace(R.id.search_detail_fragment_container, it)
                        .commit()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


    }

    fun charGradationSet(level: Int) {

        val random = Random(level.toLong())

        val red = random.nextInt(100) + 25
        val green = random.nextInt(50) + 10
        val blue = random.nextInt(100) + 25

        val selectedColor = Color.rgb(red, green, blue) // R, G, B 값을 사용하여 RGB 색상 생성
        val drawable = createGradientDrawable(
            Color.parseColor(
                "#${
                    Integer.toHexString(selectedColor).substring(2)
                }"
            )
        )
        binding.searchDetailCharGra.background = drawable
    }

    fun createGradientDrawable(startColor: Int): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        drawable.colors = intArrayOf(startColor, 0x00000000, 0x00000000)
        drawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT
        return drawable
    }

    fun setArmorDialog(
        view: CharSearchArmorView,
        binding: CharSearchDetailArmorDialogBinding,
        elixirSpecialDetailString: String?
    ) {
        binding.run {
            charSearchDetailDrawerArmorName.text = view.getArmorName().text
            charSearchDetailDrawerArmorName.setTextColor(view.getArmorName().textColors)
            charSearchDetailDrawerArmorImage.background = view.getArmorImage().background
            Glide.with(this@SearchDetailActivity)
                .load(view.imageUrl)
                .into(charSearchDetailDrawerArmorImage)

            charSearchDetailDrawerArmorDetailType.text = view.itemDetailType
            charSearchDetailDrawerArmorDetailType.setTextColor(view.getArmorName().currentTextColor)
            charSearchDetailDrawerArmorDetail.text = view.itemDetail
            charSearchDetailDrawerArmorQuality.text = view.getArmorQuality().text
            charSearchDetailDrawerArmorQuality.setTextColor((view.getArmorQuality().background as ColorDrawable).color)
            charSearchDetailDrawerArmorQualityProgress.setProgress(
                view.getArmorQuality().text.toString().toInt()
            )
            charSearchDetailDrawerArmorQualityProgress.setProgressColor((view.getArmorQuality().background as ColorDrawable).color)

            view.defaultEffect?.let {
                charSearchDetailDrawerArmorDefaultEffect.text = view.defaultEffect
            } ?: run {
                charSearchDetailDrawerArmorDefaultEffect.visibility = View.GONE
            }

            view.additionalEffect?.let {
                charSearchDetailDrawerArmorAdditionalEffect.text = view.additionalEffect
            } ?: run {
                charSearchDetailDrawerArmorAdditionalEffectLayout.visibility = View.GONE
            }

            view.elixirData?.let {
                val pattern = "\\[(.*)\\]\\s".toRegex()
                charSearchDetailDrawerElixir1.text = pattern.replace(it.element0?.contentStr!!, "")

                if (it.element1 == null) {
                    charSearchDetailDrawerElixir2.visibility = View.GONE
                } else {
                    charSearchDetailDrawerElixir2.text =
                        pattern.replace(it.element1?.contentStr!!, "")
                }
            } ?: run {
                charSearchDetailDrawerArmorElixirLayout.visibility = View.GONE
            }

            elixirSpecialDetailString?.let {
                charSearchDetailDrawerElixir3.text = it
            } ?: run {
                charSearchDetailDrawerElixir3.visibility = View.GONE
            }

            view.setLevel?.let {
                charSearchDetailDrawerSetLevel.text = it
            } ?: run {
                charSearchDetailDrawerSetLevel.visibility = View.GONE
            }
        }
    }

    fun setCardDialog(view: CharSearchCardView, binding: CharSearchDetailCardDialogBinding) {
        binding.charSearchDetailDrawerCardView.setCardImageText(view.card)
        binding.charSearchDetailDrawerCardName.text = view.getCardName().text
        binding.charSearchDetailDrawerCardDescription.text = view.cardDescription
    }

    fun setBottomEngravingDialog(
        view: CharSearchEngravingBottomView,
        binding: CharSearchDetailEngravingDialogBinding
    ) {
        binding.run {

            charSearchDetailDrawerEngravingName.text = view.engravingDrawerName
            Glide.with(this@SearchDetailActivity).load(view.imageUrl)
                .into(charSearchDetailDrawerEngravingImage)
            charSearchDetailDrawerEngravingPoint.visibility = View.GONE

            val engravingDetailTextView = TextView(this@SearchDetailActivity)
            engravingDetailTextView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            engravingDetailTextView.text = view.engravingString
            engravingDetailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6f)
            engravingDetailTextView.setTextColor(Color.parseColor("#808080"))
            charSearchDetailEngravingDetailLayout.addView(engravingDetailTextView)
        }

    }

    fun setSkillDialog(
        view: CharSearchSkillLayoutView,
        binding: CharSearchDetailSkillDialogBinding
    ) {

        binding.run {


            charSearchDetailDrawerSkillName.text = view.getSkillName().text
            Glide.with(this@SearchDetailActivity).load(view.imageUrl)
                .into(charSearchDetailDrawerSkillImage)

            charSearchDetailDrawerSkillType.text = view.skillType
            charSearchDetailDrawerSkillStance.text = view.skillStance
            charSearchDetailDrawerSkillCool.text = view.skillCool
            if (view.skillEffect != "") {
                charSearchDetailDrawerSkillEffectText.text = view.skillEffect
                charSearchDetailDrawerSkillEffectLayout.visibility = View.VISIBLE
            }
            charSearchDetailDrawerSkillDetail.text = view.skillDescription
        }
    }

    fun setEngrvingDialog(
        view: CharSearchEngravingBookView,
        binding: CharSearchDetailEngravingDialogBinding
    ) {

        binding.run {
            charSearchDetailDrawerEngravingName.text = view.getEngravingName().text
            Glide.with(this@SearchDetailActivity).load(view.imageUrl)
                .into(charSearchDetailDrawerEngravingImage)
            charSearchDetailDrawerEngravingPoint.text = view.getEngravingPoint().text
            view.engravingStringList.forEach {
                val engravingLevelView = TextView(this@SearchDetailActivity)
                engravingLevelView.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                engravingLevelView.setPadding(0, 2, 0, 0)
                engravingLevelView.setTextColor(
                    ContextCompat.getColor(
                        this@SearchDetailActivity,
                        R.color.black
                    )
                )
                engravingLevelView.setLineSpacing(0f, 5f)
                engravingLevelView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6f)
                engravingLevelView.setTypeface(null, Typeface.BOLD)
                engravingLevelView.setLineSpacing(
                    0f,
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        5f,
                        resources.displayMetrics
                    )
                )//5dp로 설정
                charSearchDetailEngravingDetailLayout.addView(engravingLevelView)
                val splitString = it.split(" - ")
                engravingLevelView.text = splitString.get(0)
                val engravingDetailTextView = TextView(this@SearchDetailActivity)
                engravingDetailTextView.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                engravingDetailTextView.text = splitString.get(1)
                engravingDetailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6f)
                engravingDetailTextView.setTextColor(Color.parseColor("#808080"))
                charSearchDetailEngravingDetailLayout.addView(engravingDetailTextView)
            }
        }
    }

    fun setGemDialog(view: CharSearchGemView, binding: CharSearchDetailGemDialogBinding) {
        binding.run {
            charSearchDetailDrawerGemName.text = view.gemName
            Glide.with(this@SearchDetailActivity).load(view.imageUrl)
                .into(charSearchDetailDrawerGemImage)
            charSearchDetailDrawerGemTier.text = view.gemTier
            charSearchDetailDrawerGemGrade.text = view.gemGrade
            when {
                view.gemGrade.contains("고대") -> {
                    charSearchDetailDrawerGemImage.setBackgroundResource(R.drawable.ancient_background)
                    charSearchDetailDrawerGemGrade.setTextColor(Color.parseColor("#d9ae43"))
                }

                view.gemGrade.contains("유물") -> {
                    charSearchDetailDrawerGemImage.setBackgroundResource(R.drawable.relic_background)
                    charSearchDetailDrawerGemGrade.setTextColor(Color.parseColor("#E45B0A"))
                }


                view.gemGrade.contains("전설") -> {
                    charSearchDetailDrawerGemImage.setBackgroundResource(R.drawable.legend_background)
                    charSearchDetailDrawerGemGrade.setTextColor(Color.parseColor("#E08808"))
                }


                view.gemGrade.contains("영웅") -> {
                    charSearchDetailDrawerGemImage.setBackgroundResource(R.drawable.hero_background)
                    charSearchDetailDrawerGemGrade.setTextColor(Color.parseColor("#A41ED4"))
                }


                view.gemGrade.contains("희귀") -> {
                    charSearchDetailDrawerGemImage.setBackgroundResource(R.drawable.rare_background)
                    charSearchDetailDrawerGemGrade.setTextColor(Color.parseColor("#268AD3"))
                }

                view.gemGrade.contains("고급") -> {
                    charSearchDetailDrawerGemImage.setBackgroundResource(R.drawable.advanced_background)
                    charSearchDetailDrawerGemGrade.setTextColor(Color.parseColor("#8FDB32"))
                }
            }

            charSearchDetailDrawerGemDetail.text = view.gemDetail
        }
    }

    fun setRuneDialog(view: CharSearchRuneView, binding: CharSearchDetailRuneDialogBinding) {
        binding.run {
            charSearchDetailDrawerRuneName.text = view.runeName
            Glide.with(this@SearchDetailActivity).load(view.imageUrl)
                .into(charSearchDetailDrawerRuneImage)
            charSearchDetailDrawerRuneGrade.text = view.runeGrade
            when {
                view.runeGrade.contains("고대") -> {
                    charSearchDetailDrawerRuneImage.setBackgroundResource(R.drawable.ancient_background)
                    charSearchDetailDrawerRuneGrade.setTextColor(Color.parseColor("#d9ae43"))
                }

                view.runeGrade.contains("유물") -> {
                    charSearchDetailDrawerRuneImage.setBackgroundResource(R.drawable.relic_background)
                    charSearchDetailDrawerRuneGrade.setTextColor(Color.parseColor("#E45B0A"))
                }


                view.runeGrade.contains("전설") -> {
                    charSearchDetailDrawerRuneImage.setBackgroundResource(R.drawable.legend_background)
                    charSearchDetailDrawerRuneGrade.setTextColor(Color.parseColor("#E08808"))
                }


                view.runeGrade.contains("영웅") -> {
                    charSearchDetailDrawerRuneImage.setBackgroundResource(R.drawable.hero_background)
                    charSearchDetailDrawerRuneGrade.setTextColor(Color.parseColor("#A41ED4"))
                }


                view.runeGrade.contains("희귀") -> {
                    charSearchDetailDrawerRuneImage.setBackgroundResource(R.drawable.rare_background)
                    charSearchDetailDrawerRuneGrade.setTextColor(Color.parseColor("#268AD3"))
                }

                view.runeGrade.contains("고급") -> {
                    charSearchDetailDrawerRuneImage.setBackgroundResource(R.drawable.advanced_background)
                    charSearchDetailDrawerRuneGrade.setTextColor(Color.parseColor("#8FDB32"))
                }
            }
            charSearchDetailDrawerRuneDetail.text = view.runeDescription
        }
    }

    fun setAvatarDialog(view: CharSearchAvatarView, binding: CharSearchDetailAvatarDialogBinding) {
        binding.run {
            charSearchDetailDrawerAvatarName.text = view.avatarNameString
            Glide.with(this@SearchDetailActivity).load(view.imageUrl)
                .into(charSearchDetailDrawerAvatarImage)
            charSearchDetailDrawerAvatarGradeType.text =
                view.avatarGrade + " " + view.avatarTypeString
            charSearchDetailDrawerAvatarLine.text = " | " + view.avatarLine
            charSearchDetailDrawerAvatarDefaultEffect.visibility = View.GONE
            charSearchDetailDrawerAvatarTendenciesEffect.visibility = View.GONE

            when {
                view.avatarGrade.contains("고대") -> {
                    charSearchDetailDrawerAvatarImage.setBackgroundResource(R.drawable.ancient_background)
                    charSearchDetailDrawerAvatarGradeType.setTextColor(Color.parseColor("#d9ae43"))
                }

                view.avatarGrade.contains("유물") -> {
                    charSearchDetailDrawerAvatarImage.setBackgroundResource(R.drawable.relic_background)
                    charSearchDetailDrawerAvatarGradeType.setTextColor(Color.parseColor("#E45B0A"))
                }


                view.avatarGrade.contains("전설") -> {
                    charSearchDetailDrawerAvatarImage.setBackgroundResource(R.drawable.legend_background)
                    charSearchDetailDrawerAvatarGradeType.setTextColor(Color.parseColor("#E08808"))
                }


                view.avatarGrade.contains("영웅") -> {
                    charSearchDetailDrawerAvatarImage.setBackgroundResource(R.drawable.hero_background)
                    charSearchDetailDrawerAvatarGradeType.setTextColor(Color.parseColor("#A41ED4"))
                }


                view.avatarGrade.contains("희귀") -> {
                    charSearchDetailDrawerAvatarImage.setBackgroundResource(R.drawable.rare_background)
                    charSearchDetailDrawerAvatarGradeType.setTextColor(Color.parseColor("#268AD3"))
                }

                view.avatarGrade.contains("고급") -> {
                    charSearchDetailDrawerAvatarImage.setBackgroundResource(R.drawable.advanced_background)
                    charSearchDetailDrawerAvatarGradeType.setTextColor(Color.parseColor("#8FDB32"))
                }
            }

            if (view.defaultEffect != "") {
                charSearchDetailDrawerAvatarDefaultEffect.visibility = View.VISIBLE
                charSearchDetailDrawerAvatarDefaultEffect.text = view.defaultEffect
            }
            if (view.tendenciesEffect != "") {
                charSearchDetailDrawerAvatarTendenciesEffect.visibility = View.VISIBLE
                charSearchDetailDrawerAvatarTendenciesEffect.text = view.tendenciesEffect
            }
        }
    }


    fun setTripodDialog(view: CharSearchTripodView, binding: CharSearchDetailTripodDialogBinding) {
        binding.charSearchDetailDrawerTripodName.text = view.getTripodName().text
        Glide.with(this).load(view.imageUrl).into(binding.charSearchDetailDrawerTripodImage)
        binding.charSearchDetailDrawerTripodLevel.text = view.tripodDialogLevel
        binding.charSearchDetailDrawerTripodDetail.text = view.tripodDescription
    }

    fun setAccessoryDialog(
        view: CharSearchAccessoryView,
        binding: CharSearchDetailAccessoryDialogBinding
    ) {
        val powerChar = listOf("버서커", "디스트로이어", "워로드", "홀리나이트", "슬레이어")
        val intChar = listOf("아르카나", "서머너", "바드", "소서리스", "도화가", "기상술사")

        var primaryStats = "민첩 "
        if (powerChar.contains(charInfo.armoryProfile.characterClassName))
            primaryStats = "힘 "
        else if (intChar.contains(charInfo.armoryProfile.characterClassName))
            primaryStats = "지능 "

        binding.run {
            charSearchDetailDrawerAccessoryName.text = view.itemName
            charSearchDetailDrawerAccessoryName.setTextColor(view.getAccessoryName().textColors)
            charSearchDetailDrawerAccessoryImage.background = view.getAccessoryImage().background
            Glide.with(this@SearchDetailActivity)
                .load(view.imageUrl)
                .into(charSearchDetailDrawerAccessoryImage)
            charSearchDetailDrawerAccessoryDetailType.text = view.itemType
            charSearchDetailDrawerAccessoryDetailType.setTextColor(view.getAccessoryName().currentTextColor)
            charSearchDetailDrawerAccessoryTier.text = view.itemTier


            if (view.type == "스톤") {
                charSearchDetailDrawerAccessoryQuality.visibility = View.GONE
                charSearchDetailDrawerAccessoryQualityProgress.visibility = View.GONE

                charSearchDetailDrawerAbilityStonePlusText.visibility = View.VISIBLE
                charSearchDetailDrawerAbilityStoneMinusText.visibility = View.VISIBLE

                charSearchDetailDrawerAbilityStonePlusText.text = view.getStonPlus().text
                charSearchDetailDrawerAbilityStoneMinusText.text = view.getStonMinus().text
                charSearchDetailDrawerAccessoryDefaultEffect.text = view.defaultEffect
            } else {
                charSearchDetailDrawerAccessoryQuality.text = view.getAccessoryQuality().text
                charSearchDetailDrawerAccessoryQuality.setTextColor((view.getAccessoryQuality().background as ColorDrawable).color)
                charSearchDetailDrawerAccessoryQualityProgress.setProgress(
                    view.getAccessoryQuality().text.toString().toInt()
                )
                charSearchDetailDrawerAccessoryQualityProgress.setProgressColor((view.getAccessoryQuality().background as ColorDrawable).color)
                charSearchDetailDrawerAccessoryDefaultEffect.text =
                    primaryStats + view.defaultEffect
            }


            if (view.type == "팔찌") {
                charSearchDetailDrawerAccessoryQuality.visibility = View.GONE
                charSearchDetailDrawerAccessoryQualityProgress.visibility = View.GONE

                charSearchDetailDrawerBraceletStat.text = view.braceletAbilityString
                charSearchDetailDrawerBraceletStat.visibility = View.VISIBLE
                charSearchDetailNonBraceletLayout.visibility = View.GONE

                view.braceletAbilityList.forEach {
                    val abilityTextView = TextView(this@SearchDetailActivity)
                    abilityTextView.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    abilityTextView.setPadding(0, 5, 0, 0)
                    abilityTextView.setTextColor(
                        ContextCompat.getColor(
                            this@SearchDetailActivity,
                            R.color.black
                        )
                    )
                    abilityTextView.setLineSpacing(0f, 5f)
                    abilityTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 8f)
                    abilityTextView.setTypeface(null, Typeface.BOLD)
                    abilityTextView.setLineSpacing(
                        0f,
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            5f,
                            resources.displayMetrics
                        )
                    )//5dp로 설정
                    charSearchDetailBraceletLayout.addView(abilityTextView)
                    if (it.contains("] ")) {
                        val splitString = it.split("] ")
                        abilityTextView.text = splitString.get(0).replace("[", "").replace("] ", "")
                        val abilityDetailTextView = TextView(this@SearchDetailActivity)
                        abilityDetailTextView.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        abilityDetailTextView.text = splitString.get(1)
                        abilityDetailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6f)
                        abilityDetailTextView.setTextColor(
                            ContextCompat.getColor(
                                this@SearchDetailActivity,
                                R.color.black
                            )
                        )
                        charSearchDetailBraceletLayout.addView(abilityDetailTextView)
                    } else {
                        abilityTextView.text = it
                    }
                }
            } else {
                view.additionalEffect?.let {
                    charSearchDetailDrawerAccessoryAdditionalEffect.text = it
                }
                    ?: run {
                        charSearchDetailDrawerAccessoryAdditionalEffect.visibility = View.GONE
                    }

                charSearchDetailDrawerAccessoryPlusEngraving.text = view.plusEngravingString
                charSearchDetailDrawerAccessoryMinusEngraving.text = view.minusEngravingString
            }

        }


    }

    fun openDialog(view: Any, additionString: String?) {
        val dialog = BottomSheetDialog(this)
        val dialogViewBinding = CharSearchDetailDrawerBinding.inflate(layoutInflater)
        dialog.setContentView(dialogViewBinding.root)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED // 나올 때 전체 크기많큼 다 나오기
        dialog.behavior.isHideable = false //밑으로 드래그해서 끄는거 막기
        dialog.behavior.isDraggable = false //드래그 막기
        when (view) {
            is CharSearchArmorView -> {
                dialogViewBinding.armorDrawer.drawerViewMain.visibility = View.VISIBLE
                setArmorDialog(
                    view,
                    dialogViewBinding.armorDrawer,
                    additionString
                )//여기서는 엘릭서 35각 40각 효과 이름
            }
            is CharSearchAccessoryView -> {
                dialogViewBinding.accessoryDrawer.drawerViewMain.visibility = View.VISIBLE
                setAccessoryDialog(view, dialogViewBinding.accessoryDrawer)
            }
            is CharSearchEngravingBookView -> {
                dialogViewBinding.engravingDrawer.drawerViewMain.visibility = View.VISIBLE
                setEngrvingDialog(view, dialogViewBinding.engravingDrawer)
            }
            is CharSearchGemView -> {
                dialogViewBinding.gemDrawer.drawerViewMain.visibility = View.VISIBLE
                setGemDialog(view, dialogViewBinding.gemDrawer)
            }
            is CharSearchEngravingBottomView -> {
                dialogViewBinding.engravingDrawer.drawerViewMain.visibility = View.VISIBLE
                setBottomEngravingDialog(view, dialogViewBinding.engravingDrawer)
            }
            is CharSearchCardView -> {
                dialogViewBinding.cardDrawer.drawerViewMain.visibility = View.VISIBLE
                setCardDialog(view, dialogViewBinding.cardDrawer)
            }
            is CharSearchRuneView -> {
                dialogViewBinding.runeDrawer.drawerViewMain.visibility = View.VISIBLE
                setRuneDialog(view, dialogViewBinding.runeDrawer)
            }
            is CharSearchSkillLayoutView -> {
                dialogViewBinding.skillDrawer.drawerViewMain.visibility = View.VISIBLE
                setSkillDialog(view, dialogViewBinding.skillDrawer)
            }
            is CharSearchTripodView -> {
                dialogViewBinding.tripodDrawer.drawerViewMain.visibility = View.VISIBLE
                setTripodDialog(view, dialogViewBinding.tripodDrawer)
            }
            is CharSearchAvatarView -> {
                dialogViewBinding.avatarDrawer.drawerViewMain.visibility = View.VISIBLE
                setAvatarDialog(view, dialogViewBinding.avatarDrawer)
            }
            is CharSearchCollectionEquipmentView -> {
                dialogViewBinding.collectionDrawer.drawerViewMain.visibility = View.VISIBLE
                setCollectionDialog(view, dialogViewBinding.collectionDrawer)
            }
        }
        dialogViewBinding.charSearchDetailDrawerButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    fun setCollectionDialog(
        view: CharSearchCollectionEquipmentView,
        binding: CharSearchDetailCollectionEquDialogBinding
    ) {

        binding.run {
            charSearchDetailDrawerCollectionEquName.text = view.itemName
            Glide.with(this@SearchDetailActivity).load(view.imageUrl).into(charSearchDetailDrawerCollectionEquImage)
            charSearchDetailDrawerCollectionEquDescription.text = view.descriptionString
            charSearchDetailDrawerCollectionEquDefaultEffect.text = view.effectString

            when (view.itemGrade) {
                "고대" -> {
                    charSearchDetailDrawerCollectionEquImage.setBackgroundResource(R.drawable.ancient_background)
                    charSearchDetailDrawerCollectionEquName.setTextColor(Color.parseColor("#d9ae43"))
                }

                "유물" -> {
                    charSearchDetailDrawerCollectionEquImage.setBackgroundResource(R.drawable.relic_background)
                    charSearchDetailDrawerCollectionEquName.setTextColor(Color.parseColor("#E45B0A"))
                }


                "전설" -> {
                    charSearchDetailDrawerCollectionEquImage.setBackgroundResource(R.drawable.legend_background)
                    charSearchDetailDrawerCollectionEquName.setTextColor(Color.parseColor("#E08808"))
                }


                "영웅" -> {
                    charSearchDetailDrawerCollectionEquImage.setBackgroundResource(R.drawable.hero_background)
                    charSearchDetailDrawerCollectionEquName.setTextColor(Color.parseColor("#A41ED4"))
                }


                "희귀" -> {
                    charSearchDetailDrawerCollectionEquImage.setBackgroundResource(R.drawable.rare_background)
                    charSearchDetailDrawerCollectionEquName.setTextColor(Color.parseColor("#268AD3"))
                }

                "고급" -> {
                    charSearchDetailDrawerCollectionEquImage.setBackgroundResource(R.drawable.advanced_background)
                    charSearchDetailDrawerCollectionEquName.setTextColor(Color.parseColor("#8FDB32"))
                }
            }
        }

    }

    inline fun <reified T : Serializable> Intent.getSerializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as T
    }
}