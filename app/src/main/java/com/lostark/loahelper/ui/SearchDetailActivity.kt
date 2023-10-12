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
import com.lostark.loahelper.databinding.CharSearchDetailActivityBinding
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
            searchDetailCharLevel.text = "전투 : Lv." + charInfo.armoryProfile.characterLevel.toString()
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
        val characterFragment = CharactersFragment(characters,charInfo)
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
        elixirSpecialDetailString: String?
    ) {
        binding.charSearchDetailDrawer.armorDrawer.run {
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
            charSearchDetailDrawerArmorQualityProgress.setProgress(view.getArmorQuality().text.toString().toInt())
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
                    charSearchDetailDrawerElixir2.text = pattern.replace(it.element1?.contentStr!!, "")
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

    fun setCardDialog(view: CharSearchCardView, cardLayout: LinearLayout) {
        val cardName = cardLayout.findViewById<TextView>(R.id.char_search_detail_drawer_card_name)
        val cardDescription =
            cardLayout.findViewById<TextView>(R.id.char_search_detail_drawer_card_description)
        val cardView =
            cardLayout.findViewById<CharSearchCardView>(R.id.char_search_detail_drawer_card_view)
        cardView.setCardImageText(view.card)
        cardName.text = view.getCardName().text
        cardDescription.text = view.cardDescription
    }

    fun setBottomEngravingDialog(
        view: CharSearchEngravingBottomView,
        engravingLayout: LinearLayout
    ) {
        val engravingName =
            engravingLayout.findViewById<TextView>(R.id.char_search_detail_drawer_engraving_name)
        engravingName.text = view.engravingDrawerName
        val engravingImage =
            engravingLayout.findViewById<ImageView>(R.id.char_search_detail_drawer_engraving_image)
        Glide.with(this).load(view.imageUrl).into(engravingImage)
        val engravingPoint =
            engravingLayout.findViewById<TextView>(R.id.char_search_detail_drawer_engraving_point)
        engravingPoint.visibility = View.GONE
        val engravingDetailLayout =
            engravingLayout.findViewById<LinearLayout>(R.id.char_search_detail_engraving_detail_layout)

        val engravingDetailTextView = TextView(this)
        engravingDetailTextView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        engravingDetailTextView.text = view.engravingString
        engravingDetailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6f)
        engravingDetailTextView.setTextColor(Color.parseColor("#808080"))
        engravingDetailLayout.addView(engravingDetailTextView)

    }

    fun setSkillDialog(view: CharSearchSkillLayoutView, skillLayout: LinearLayout) {
        val skillName =
            skillLayout.findViewById<TextView>(R.id.char_search_detail_drawer_skill_name)
        val skillImage =
            skillLayout.findViewById<ImageView>(R.id.char_search_detail_drawer_skill_image)
        val skillType =
            skillLayout.findViewById<TextView>(R.id.char_search_detail_drawer_skill_type)
        val skillStance =
            skillLayout.findViewById<TextView>(R.id.char_search_detail_drawer_skill_stance)
        val skillCool =
            skillLayout.findViewById<TextView>(R.id.char_search_detail_drawer_skill_cool)
        val skillEffectLayout =
            skillLayout.findViewById<LinearLayout>(R.id.char_search_detail_drawer_skill_effect_layout)
        val skillEffect =
            skillLayout.findViewById<TextView>(R.id.char_search_detail_drawer_skill_effect_text)
        val skillDetail =
            skillLayout.findViewById<TextView>(R.id.char_search_detail_drawer_skill_detail)

        skillName.text = view.getSkillName().text
        Glide.with(this).load(view.imageUrl).into(skillImage)

        skillType.text = view.skillType
        skillStance.text = view.skillStance
        skillCool.text = view.skillCool
        if (view.skillEffect != "") {
            skillEffect.text = view.skillEffect
            skillEffectLayout.visibility = View.VISIBLE
        }
        skillDetail.text = view.skillDescription

    }

    fun setEngrvingDialog(view: CharSearchEngravingBookView, engravingLayout: LinearLayout) {
        val engravingName =
            engravingLayout.findViewById<TextView>(R.id.char_search_detail_drawer_engraving_name)
        engravingName.text = view.getEngravingName().text
        val engravingImage =
            engravingLayout.findViewById<ImageView>(R.id.char_search_detail_drawer_engraving_image)
        Glide.with(this).load(view.imageUrl).into(engravingImage)
        val engravingPoint =
            engravingLayout.findViewById<TextView>(R.id.char_search_detail_drawer_engraving_point)
        engravingPoint.text = view.getEngravingPoint().text
        val engravingDetailLayout =
            engravingLayout.findViewById<LinearLayout>(R.id.char_search_detail_engraving_detail_layout)
        view.engravingStringList.forEach {
            val engravingLevelView = TextView(this)
            engravingLevelView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            engravingLevelView.setPadding(0, 2, 0, 0)
            engravingLevelView.setTextColor(ContextCompat.getColor(this, R.color.black))
            engravingLevelView.setLineSpacing(0f, 5f)
            engravingLevelView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6f)
            engravingLevelView.setTypeface(null, Typeface.BOLD)
            engravingLevelView.setLineSpacing(
                0f,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics)
            )//5dp로 설정
            engravingDetailLayout.addView(engravingLevelView)
            val splitString = it.split(" - ")
            engravingLevelView.text = splitString.get(0)
            val engravingDetailTextView = TextView(this)
            engravingDetailTextView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            engravingDetailTextView.text = splitString.get(1)
            engravingDetailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6f)
            engravingDetailTextView.setTextColor(Color.parseColor("#808080"))
            engravingDetailLayout.addView(engravingDetailTextView)
        }
    }

    fun setGemDialog(view: CharSearchGemView, gemLayout: LinearLayout) {
        val gemName =
            gemLayout.findViewById<TextView>(R.id.char_search_detail_drawer_gem_name)
        gemName.text = view.gemName
        val gemImage =
            gemLayout.findViewById<ImageView>(R.id.char_search_detail_drawer_gem_image)
        Glide.with(this).load(view.imageUrl).into(gemImage)
        val gemTier =
            gemLayout.findViewById<TextView>(R.id.char_search_detail_drawer_gem_tier)
        gemTier.text = view.gemTier

        val gemGrade = gemLayout.findViewById<TextView>(R.id.char_search_detail_drawer_gem_grade)
        gemGrade.text = view.gemGrade
        when {
            view.gemGrade.contains("고대") -> {
                gemImage.setBackgroundResource(R.drawable.ancient_background)
                gemGrade.setTextColor(Color.parseColor("#d9ae43"))
            }

            view.gemGrade.contains("유물") -> {
                gemImage.setBackgroundResource(R.drawable.relic_background)
                gemGrade.setTextColor(Color.parseColor("#E45B0A"))
            }


            view.gemGrade.contains("전설") -> {
                gemImage.setBackgroundResource(R.drawable.legend_background)
                gemGrade.setTextColor(Color.parseColor("#E08808"))
            }


            view.gemGrade.contains("영웅") -> {
                gemImage.setBackgroundResource(R.drawable.hero_background)
                gemGrade.setTextColor(Color.parseColor("#A41ED4"))
            }


            view.gemGrade.contains("희귀") -> {
                gemImage.setBackgroundResource(R.drawable.rare_background)
                gemGrade.setTextColor(Color.parseColor("#268AD3"))
            }

            view.gemGrade.contains("고급") -> {
                gemImage.setBackgroundResource(R.drawable.advanced_background)
                gemGrade.setTextColor(Color.parseColor("#8FDB32"))
            }
        }


        val gemDetail = gemLayout.findViewById<TextView>(R.id.char_search_detail_drawer_gem_detail)
        gemDetail.text = view.gemDetail
    }

    fun setRuneDialog(view: CharSearchRuneView, runeLayout: LinearLayout) {
        val runeName =
            runeLayout.findViewById<TextView>(R.id.char_search_detail_drawer_rune_name)
        runeName.text = view.runeName
        val runeImage =
            runeLayout.findViewById<ImageView>(R.id.char_search_detail_drawer_rune_image)
        Glide.with(this).load(view.imageUrl).into(runeImage)
        val runeGrade = runeLayout.findViewById<TextView>(R.id.char_search_detail_drawer_rune_grade)
        runeGrade.text = view.runeGrade
        when {
            view.runeGrade.contains("고대") -> {
                runeImage.setBackgroundResource(R.drawable.ancient_background)
                runeGrade.setTextColor(Color.parseColor("#d9ae43"))
            }

            view.runeGrade.contains("유물") -> {
                runeImage.setBackgroundResource(R.drawable.relic_background)
                runeGrade.setTextColor(Color.parseColor("#E45B0A"))
            }


            view.runeGrade.contains("전설") -> {
                runeImage.setBackgroundResource(R.drawable.legend_background)
                runeGrade.setTextColor(Color.parseColor("#E08808"))
            }


            view.runeGrade.contains("영웅") -> {
                runeImage.setBackgroundResource(R.drawable.hero_background)
                runeGrade.setTextColor(Color.parseColor("#A41ED4"))
            }


            view.runeGrade.contains("희귀") -> {
                runeImage.setBackgroundResource(R.drawable.rare_background)
                runeGrade.setTextColor(Color.parseColor("#268AD3"))
            }

            view.runeGrade.contains("고급") -> {
                runeImage.setBackgroundResource(R.drawable.advanced_background)
                runeGrade.setTextColor(Color.parseColor("#8FDB32"))
            }
        }

        val runeDetail =
            runeLayout.findViewById<TextView>(R.id.char_search_detail_drawer_rune_detail)
        runeDetail.text = view.runeDescription
    }

    fun setAvatarDialog(view:CharSearchAvatarView,avatarLayout:LinearLayout){
        val avatarName = avatarLayout.findViewById<TextView>(R.id.char_search_detail_drawer_avatar_name)
        val avatarImage = avatarLayout.findViewById<ImageView>(R.id.char_search_detail_drawer_avatar_image)
        val avatarGradeType = avatarLayout.findViewById<TextView>(R.id.char_search_detail_drawer_avatar_grade_type)
        val avatarLine = avatarLayout.findViewById<TextView>(R.id.char_search_detail_drawer_avatar_line)
        val avatarEffect = avatarLayout.findViewById<TextView>(R.id.char_search_detail_drawer_avatar_default_effect)
        val avatarTendencies = avatarLayout.findViewById<TextView>(R.id.char_search_detail_drawer_avatar_tendencies_effect)

        avatarName.text = view.avatarNameString
        Glide.with(this).load(view.imageUrl).into(avatarImage)
        avatarGradeType.text = view.avatarGrade +" "+ view.avatarTypeString
        avatarLine.text = " | "+view.avatarLine
        avatarEffect.visibility=View.GONE
        avatarTendencies.visibility=View.GONE

        when {
            view.avatarGrade.contains("고대") -> {
                avatarImage.setBackgroundResource(R.drawable.ancient_background)
                avatarGradeType.setTextColor(Color.parseColor("#d9ae43"))
            }

            view.avatarGrade.contains("유물") -> {
                avatarImage.setBackgroundResource(R.drawable.relic_background)
                avatarGradeType.setTextColor(Color.parseColor("#E45B0A"))
            }


            view.avatarGrade.contains("전설") -> {
                avatarImage.setBackgroundResource(R.drawable.legend_background)
                avatarGradeType.setTextColor(Color.parseColor("#E08808"))
            }


            view.avatarGrade.contains("영웅") -> {
                avatarImage.setBackgroundResource(R.drawable.hero_background)
                avatarGradeType.setTextColor(Color.parseColor("#A41ED4"))
            }


            view.avatarGrade.contains("희귀") -> {
                avatarImage.setBackgroundResource(R.drawable.rare_background)
                avatarGradeType.setTextColor(Color.parseColor("#268AD3"))
            }

            view.avatarGrade.contains("고급") -> {
                avatarImage.setBackgroundResource(R.drawable.advanced_background)
                avatarGradeType.setTextColor(Color.parseColor("#8FDB32"))
            }
        }

        if(view.defaultEffect!=""){
            avatarEffect.visibility=View.VISIBLE
            avatarEffect.text=view.defaultEffect
        }
        if(view.tendenciesEffect!=""){
            avatarTendencies.visibility=View.VISIBLE
            avatarTendencies.text=view.tendenciesEffect
        }
    }


    fun setTripodDialog(view: CharSearchTripodView, tripodLayout: LinearLayout) {
        val tripodName =
            tripodLayout.findViewById<TextView>(R.id.char_search_detail_drawer_tripod_name)
        tripodName.text = view.getTripodName().text
        val tripodImage =
            tripodLayout.findViewById<ImageView>(R.id.char_search_detail_drawer_tripod_image)
        Glide.with(this).load(view.imageUrl).into(tripodImage)
        val tripodLevel =
            tripodLayout.findViewById<TextView>(R.id.char_search_detail_drawer_tripod_level)
        tripodLevel.text = view.tripodDialogLevel

        val tripodDetail =
            tripodLayout.findViewById<TextView>(R.id.char_search_detail_drawer_tripod_detail)
        tripodDetail.text = view.tripodDescription
    }

    fun setAccessoryDialog(view: CharSearchAccessoryView, accessoryLayout: LinearLayout) {
        val powerChar = listOf("버서커", "디스트로이어", "워로드", "홀리나이트", "슬레이어")
        val intChar = listOf("아르카나", "서머너", "바드", "소서리스", "도화가", "기상술사")

        var primaryStats = "민첩 "
        if (powerChar.contains(charInfo.armoryProfile.characterClassName))
            primaryStats = "힘 "
        else if (intChar.contains(charInfo.armoryProfile.characterClassName))
            primaryStats = "지능 "

        val accessoryName =
            accessoryLayout.findViewById<TextView>(R.id.char_search_detail_drawer_accessory_name)
        val accessoryImage =
            accessoryLayout.findViewById<ImageView>(R.id.char_search_detail_drawer_accessory_image)
        accessoryName.text = view.itemName
        accessoryName.setTextColor(view.getAccessoryName().textColors)
        accessoryImage.background = view.getAccessoryImage().background
        Glide.with(this)
            .load(view.imageUrl)
            .into(accessoryImage)

        val accessoryType =
            accessoryLayout.findViewById<TextView>(R.id.char_search_detail_drawer_accessory_detail_type)
        val accessoryTier =
            accessoryLayout.findViewById<TextView>(R.id.char_search_detail_drawer_accessory_tier)
        accessoryType.text = view.itemType
        accessoryType.setTextColor(view.getAccessoryName().currentTextColor)
        accessoryTier.text = view.itemTier
        println(accessoryTier.text)
        val accessoryQuality =
            accessoryLayout.findViewById<TextView>(R.id.char_search_detail_drawer_accessory_quality)
        val accessoryQualityProgressBar =
            accessoryLayout.findViewById<RoundCornerProgressBar>(R.id.char_search_detail_drawer_accessory_quality_progress)
        val accessoryDefaultEffect =
            accessoryLayout.findViewById<TextView>(R.id.char_search_detail_drawer_accessory_default_effect)

        if (view.type == "스톤") {
            accessoryQuality.visibility = View.GONE
            accessoryQualityProgressBar.visibility = View.GONE
            val abilityStonePlus =
                accessoryLayout.findViewById<TextView>(R.id.char_search_detail_drawer_ability_stone_plus_text)
            val abilityStoneMinus =
                accessoryLayout.findViewById<TextView>(R.id.char_search_detail_drawer_ability_stone_minus_text)

            abilityStonePlus.visibility = View.VISIBLE
            abilityStoneMinus.visibility = View.VISIBLE

            abilityStonePlus.text = view.getStonPlus().text
            abilityStoneMinus.text = view.getStonMinus().text
            accessoryDefaultEffect.text = view.defaultEffect
        } else {
            accessoryQuality.text = view.getAccessoryQuality().text
            accessoryQuality.setTextColor((view.getAccessoryQuality().background as ColorDrawable).color)
            accessoryQualityProgressBar.setProgress(view.getAccessoryQuality().text.toString().toInt())
            accessoryQualityProgressBar.setProgressColor((view.getAccessoryQuality().background as ColorDrawable).color)
            accessoryDefaultEffect.text = primaryStats + view.defaultEffect
        }


        if (view.type == "팔찌") {
            accessoryQuality.visibility = View.GONE
            accessoryQualityProgressBar.visibility = View.GONE

            val nonBraceletLayout =
                accessoryLayout.findViewById<LinearLayout>(R.id.char_search_detail_non_bracelet_layout)
            val braceletLayout =
                accessoryLayout.findViewById<LinearLayout>(R.id.char_search_detail_bracelet_layout)
            val braceletStat =
                accessoryLayout.findViewById<TextView>(R.id.char_search_detail_drawer_bracelet_stat)
            braceletStat.text = view.braceletAbilityString
            braceletStat.visibility = View.VISIBLE
            nonBraceletLayout.visibility = View.GONE

            view.braceletAbilityList.forEach {
                val abilityTextView = TextView(this)
                abilityTextView.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                abilityTextView.setPadding(0, 5, 0, 0)
                abilityTextView.setTextColor(ContextCompat.getColor(this, R.color.black))
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
                braceletLayout.addView(abilityTextView)
                if (it.contains("] ")) {
                    val splitString = it.split("] ")
                    abilityTextView.text = splitString.get(0).replace("[", "").replace("] ", "")
                    val abilityDetailTextView = TextView(this)
                    abilityDetailTextView.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    abilityDetailTextView.text = splitString.get(1)
                    abilityDetailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6f)
                    abilityDetailTextView.setTextColor(ContextCompat.getColor(this, R.color.black))
                    braceletLayout.addView(abilityDetailTextView)
                } else {
                    abilityTextView.text = it
                }
            }
        } else {
            val accessoryAdditionalEffect =
                accessoryLayout.findViewById<TextView>(R.id.char_search_detail_drawer_accessory_additional_effect)
            view.additionalEffect?.let { accessoryAdditionalEffect.text = it }
                ?: run { accessoryAdditionalEffect.visibility = View.GONE }

            val accessoryPlusEngraving =
                accessoryLayout.findViewById<TextView>(R.id.char_search_detail_drawer_accessory_plus_engraving)
            val accessoryMinusEngraving =
                accessoryLayout.findViewById<TextView>(R.id.char_search_detail_drawer_accessory_minus_engraving)

            accessoryPlusEngraving.text = view.plusEngravingString
            accessoryMinusEngraving.text = view.minusEngravingString
        }

    }

    fun openDialog(view: Any, additionString: String?) {
        val dialog = BottomSheetDialog(this)
        val dialogView = layoutInflater.inflate(R.layout.char_search_detail_drawer, null)
        val dialogOkButton = dialogView.findViewById<Button>(R.id.char_search_detail_drawer_button)
        dialog.setContentView(dialogView)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED // 나올 때 전체 크기많큼 다 나오기
        dialog.behavior.isHideable = false //밑으로 드래그해서 끄는거 막기
        dialog.behavior.isDraggable = false //드래그 막기
        when (view) {
            is CharSearchArmorView -> {
                val armorLayout = dialogView.findViewById<LinearLayout>(R.id.armor_drawer)
                armorLayout.visibility = View.VISIBLE
                //binding.charSearchDetailDrawer.armorDrawer.drawViewMain.visibility=View.VISIBLE
                setArmorDialog(view, additionString)//여기서는 엘릭서 35각 40각 효과 이름
            }
            is CharSearchAccessoryView -> {
                val accessoryLayout = dialogView.findViewById<LinearLayout>(R.id.accessory_drawer)
                accessoryLayout.visibility = View.VISIBLE
                //binding.charSearchDetailDrawer.accessoryDrawer.drawerView.visibility=View.VISIBLE
                setAccessoryDialog(view, accessoryLayout)
            }
            is CharSearchEngravingBookView -> {
                val engravingLayout = dialogView.findViewById<LinearLayout>(R.id.engraving_drawer)
                engravingLayout.visibility = View.VISIBLE
                setEngrvingDialog(view, engravingLayout)
            }
            is CharSearchGemView -> {
                val gemLayout = dialogView.findViewById<LinearLayout>(R.id.gem_drawer)
                gemLayout.visibility = View.VISIBLE
                setGemDialog(view, gemLayout)
            }
            is CharSearchEngravingBottomView -> {
                val engravingLayout = dialogView.findViewById<LinearLayout>(R.id.engraving_drawer)
                engravingLayout.visibility = View.VISIBLE
                setBottomEngravingDialog(view, engravingLayout)
            }
            is CharSearchCardView -> {
                val cardLayout = dialogView.findViewById<LinearLayout>(R.id.card_drawer)
                cardLayout.visibility = View.VISIBLE
                setCardDialog(view, cardLayout)
            }
            is CharSearchRuneView -> {
                val runeLayout = dialogView.findViewById<LinearLayout>(R.id.rune_drawer)
                runeLayout.visibility = View.VISIBLE
                setRuneDialog(view, runeLayout)
            }
            is CharSearchSkillLayoutView -> {
                val skillLayout = dialogView.findViewById<LinearLayout>(R.id.skill_drawer)
                skillLayout.visibility = View.VISIBLE
                setSkillDialog(view, skillLayout)
            }
            is CharSearchTripodView -> {
                val tripodLayout = dialogView.findViewById<LinearLayout>(R.id.tripod_drawer)
                tripodLayout.visibility = View.VISIBLE
                setTripodDialog(view, tripodLayout)
            }
            is CharSearchAvatarView->{
                val avatarLayout = dialogView.findViewById<LinearLayout>(R.id.avatar_drawer)
                avatarLayout.visibility = View.VISIBLE
                setAvatarDialog(view,avatarLayout)
            }
            is CharSearchCollectionEquipmentView->{
                val collectionEquipmentLayout = dialogView.findViewById<LinearLayout>(R.id.collection_drawer)
                collectionEquipmentLayout.visibility=View.VISIBLE
                setCollectionDialog(view,collectionEquipmentLayout)
            }
        }
        dialogOkButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()


    }

    fun setCollectionDialog(view: CharSearchCollectionEquipmentView, collectionEquipmentLayout: LinearLayout) {

        val itemName=collectionEquipmentLayout.findViewById<TextView>(R.id.char_search_detail_drawer_collection_equ_name)
        val itemDescription = collectionEquipmentLayout.findViewById<TextView>(R.id.char_search_detail_drawer_collection_equ_description)
        val itemEffect = collectionEquipmentLayout.findViewById<TextView>(R.id.char_search_detail_drawer_collection_equ_default_effect)
        val itemImageView = collectionEquipmentLayout.findViewById<ImageView>(R.id.char_search_detail_drawer_collection_equ_image)


        itemName.text=view.itemName
        Glide.with(this).load(view.imageUrl).into(itemImageView)
        itemDescription.text = view.descriptionString
        itemEffect.text = view.effectString

        when(view.itemGrade) {
            "고대"-> {
                itemImageView.setBackgroundResource(R.drawable.ancient_background)
                itemName.setTextColor(Color.parseColor("#d9ae43"))
            }

            "유물"-> {
                itemImageView.setBackgroundResource(R.drawable.relic_background)
                itemName.setTextColor(Color.parseColor("#E45B0A"))
            }


            "전설" -> {
                itemImageView.setBackgroundResource(R.drawable.legend_background)
                itemName.setTextColor(Color.parseColor("#E08808"))
            }


            "영웅"-> {
                itemImageView.setBackgroundResource(R.drawable.hero_background)
                itemName.setTextColor(Color.parseColor("#A41ED4"))
            }


            "희귀"-> {
                itemImageView.setBackgroundResource(R.drawable.rare_background)
                itemName.setTextColor(Color.parseColor("#268AD3"))
            }

            "고급" -> {
                itemImageView.setBackgroundResource(R.drawable.advanced_background)
                itemName.setTextColor(Color.parseColor("#8FDB32"))
            }
        }

    }

    inline fun <reified T : Serializable> Intent.getSerializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as T
    }
}