package com.lostark.loahelper.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import com.lostark.loahelper.R
import com.lostark.loahelper.adapter.ValueDataAdapter
import java.text.NumberFormat
import com.lostark.loahelper.customview.*
import com.lostark.loahelper.databinding.CharSearchDetailAbilityFragmentBinding
import com.lostark.loahelper.dto.armorys.Armories
import java.util.*
import com.lostark.loahelper.dto.armorys.*


class AbilityFragment(private val charInfo: Armories) : BaseFragment<CharSearchDetailAbilityFragmentBinding>(CharSearchDetailAbilityFragmentBinding::inflate) {
    val engravingImageDict = mutableMapOf(
        "황후의은총" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_1.png",
        "탈출의명수" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_10.png",
        "고독한기사" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_13.png",
        "구슬동자" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_18.png",
        "전투태세" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_19.png",
        "넘치는교감" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_21.png",
        "강령술" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_29.png",
        "중력수련" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_42.png",
        "굳은의지" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_44.png",
        "중갑착용" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_46.png",
        "마나의흐름" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_63.png",
        "정기흡수" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_65.png",
        "불굴" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_66.png",
        "원한" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_71.png",
        "에테르포식자" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_74.png",
        "두번째동료" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_77.png",
        "상급소환사" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_78.png",
        "분쇄의주먹" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_83.png",
        "실드관통"  to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_89.png",
        "부러진뼈" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_94.png",
        "안정된상태" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_105.png",
        "각성" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_113.png",
        "폭발물전문가" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_121.png",
        "최대마나증가" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_122.png",
        "절실한구원" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_135.png",
        "승부사" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_136.png",
        "광전사의비기" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_138.png",
        "달인의저력" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_147.png",
        "기습의대가" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_148.png",
        "위기모면" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_162.png",
        "마나효율증가" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_166.png",
        "급소타격" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_168.png",
        "바리케이드" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_170.png",
        "화력강화" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_171.png",
        "광기" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_173.png",
        "충격단련" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_177.png",
        "번개의분노" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_191.png",
        "돌격대장" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_210.png",
        "진실된용맹" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_220.png",
        "여신의가호" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_229.png",
        "세맥타통" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_235.png",
        "황제의칙령" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_236.png",
        "저주받은인형" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_237.png",
        "오의강화" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_238.png",
        "강화방패" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_239.png",
        "죽음의습격" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_245.png",
        "초심" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/ability/ability_25.png",
        "역천지체" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/ability/ability_45.png",
        "절정" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_207.png",
        "절제" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_208.png",
        "잔재된기운" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_209.png",
        "버스트" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_210.png",
        "완벽한억제" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_211.png",
        "멈출수없는충동" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_212.png",
        "심판자" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_214.png",
        "축복의오라" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_215.png",
        "아르데타인의기술" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_216.png",
        "진화의유산" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_217.png",
        "공격력감소" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_218.png",
        "방어력감소" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_219.png",
        "공격속도감소" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_220.png",
        "이동속도감소" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_221.png",
        "갈증" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_222.png",
        "달의소리" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_223.png",
        "결투의대가" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_224.png",
        "피스메이커" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_225.png",
        "사냥의시간" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_228.png",
        "오의난무" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_229.png",
        "일격필살" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_230.png",
        "질량증가" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_231.png",
        "추진력" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_232.png",
        "타격의대가" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/ability/ability_233.png",
        "시선집중" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/ability/ability_234.png",
        "아드레날린" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/ability/ability_235.png",
        "속전속결" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/ability/ability_236.png",
        "전문의" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/ability/ability_237.png",
        "긴급구조" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/ability/ability_238.png",
        "정밀단도" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/ability/ability_239.png",
        "점화" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_240.png",
        "환류" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_241.png",
        "강화무기" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_242.png",
        "회귀" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_248.png",
        "만개" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_249.png",
        "질풍노도" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_258.png",
        "이슬비" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_259.png",
        "포식자" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_260.png",
        "처단자" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_261.png",
        "만월의집행자" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_263.png",
        "그믐의경계" to "https://cdn-lostark.game.onstove.com/EFUI_IconAtlas/Ability/Ability_264.png",
        "예리한둔기" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/achieve/achieve_03_40.png",
        "약자무시" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/achieve/achieve_04_30.png",
        "슈퍼차지" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/achieve/achieve_06_14.png",
        "선수필승" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/achieve/achieve_08_62.png",
        "핸드거너" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/buff/buff_600.png",
        "포격강화" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/gl_skill/gl_skill_01_26.png",
        "극의:체술" to "https://cdn-lostark.game.onstove.com/efui_iconatlas/achieve/achieve_07_22.png")

    override fun initView() {
        setArmors()
        setAccessory()
        setEngraving()
        setGem()
        setBottomStatus()
        setBottomEngraving()
        setCard()
    }

    fun setCard(){
        binding.run {
            val cardViewList = listOf(
                card1,
                card2,
                card3,
                card4,
                card5,
                card6
            )
            charInfo.armoryCard?.cards?.forEachIndexed { index, card ->
                toolTipDeserialization(card)?.let {
                    cardViewList.get(index).setCardImageText(card, it)
                    cardViewList.get(index).setOnClickListener {
                        (activity as SearchDetailActivity).openDialog(it, "")
                    }
                }
            }
            charInfo.armoryCard?.effects?.forEach {
                if (it.items.size != 0) {
                    val cardSetTextView = TextView(requireContext())
                    cardSetTextView.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        val marginPx = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics
                        ).toInt()
                        setMargins(marginPx, marginPx, marginPx, marginPx)
                    }
                    val cardSetNameAwake = it.items.get(it.items.size - 1).name//카드 셋네임하고 각성 다 있는거
                    var pattern = "\\d+.*".toRegex()
                    val cardSetName = cardSetNameAwake.replace(pattern, "").trim()

                    pattern = "\\d+각".toRegex()
                    val cardAwake = pattern.find(cardSetNameAwake)?.value


                    cardSetTextView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    cardSetTextView.text = cardSetName
                    cardSetTextView.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.black
                        )
                    )
                    cardAwake?.let {
                        cardSetTextView.text = cardSetTextView.text.toString() + " " + it

                        val spannableString = SpannableString(cardSetTextView.text.toString())
                        val start = cardSetTextView.text.toString().indexOf(it)
                        val end = start + cardAwake.length
                        spannableString.setSpan(
                            ForegroundColorSpan(Color.parseColor("#FF6702")), start, end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        cardSetTextView.text = spannableString

                    }

                    cardSetTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 7f)

                    charSearchDetailAbilityCardLayout.addView(cardSetTextView)

                    println(it.items.get(it.items.size - 1).name)
                }
            }
        }
    }

    fun setBottomEngraving(){
        val engravingList=charInfo.armoryEngraving?.effects
        binding.run {
            engravingList?.forEach {
                val bottomEngravingView = CharSearchEngravingBottomView(requireContext())
                var pattern = "\\d+".toRegex()
                val level = pattern.find(it.name)?.value
                pattern = "Lv.\\s\\d+".toRegex()
                val imageUrl = engravingImageDict[it.name.replace(pattern, "").replace(" ", "")]
                    ?: "https://cdn-lostark.game.onstove.com/efui_iconatlas/achieve/achieve_07_22.png"
                bottomEngravingView.setEngravingImageText(
                    it.name.replace(pattern, ""),
                    it.description,
                    level!!,
                    imageUrl
                )
                bottomEngravingView.layoutParams=LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    val marginPx = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics
                    ).toInt()
                    setMargins(marginPx, marginPx, marginPx, marginPx)
                }
                charSearchDetailAbilityBottomEngravingLayout.addView(bottomEngravingView)
                bottomEngravingView.setOnClickListener {
                    (activity as SearchDetailActivity).openDialog(it, "")
                }
            }
        }
    }

    fun setBottomStatus(){

        binding.run {
            var pattern = "<.*?>".toRegex()
            val powerTooltip = mutableListOf<String>()
            if (charInfo.armoryProfile.stats != null) {
                charInfo.armoryProfile.stats.find { it.type == "공격력" }?.tooltip?.forEach {
                    powerTooltip.add(it.replace(pattern, ""))
                }

                pattern = "\\d+".toRegex()
                charSearchDetailCharLife.text = charInfo.armoryProfile.stats.find { it.type == "최대 생명력" }?.value
                charSearchDetailCharPower.text = NumberFormat.getNumberInstance(Locale.US)
                    .format(charInfo.armoryProfile.stats.find { it.type == "공격력" }?.value?.toInt())
                    ?: "error"
                charSearchDetailCharDefaultPower.text = NumberFormat.getNumberInstance(Locale.US)
                    .format(pattern.find(powerTooltip.get(1))?.value?.toInt()) ?: "error"
                charSearchDetailCharAddPower.text = NumberFormat.getNumberInstance(Locale.US)
                    .format(pattern.find(powerTooltip.get(2))?.value?.toInt()) ?: "error"



                val statViewList = listOf<TextView>(
                    charSearchDetailCriticalAbility,
                    charSearchDetailSpecialtyAbility,
                    charSearchDetailProficiencyAbility,
                    charSearchDetailFastAbility,
                    charSearchDetailPatienceAbility,
                    charSearchDetailSubdueAbility
                )
                val statList = mutableListOf<Int>()
                statViewList.forEachIndexed { index, textView ->
                    statList.add(charInfo.armoryProfile.stats.get(index).value.toInt())
                    textView.text = statList[index].toString()
                }
                statViewList.find { it.text.toString() == statList.max().toString() }?.setTextColor(
                    Color.parseColor("#8a2be2")
                )
                statList.remove(statList.max())
                statViewList.find { it.text.toString() == statList.max().toString() }?.setTextColor(
                    Color.parseColor("#00aaff")
                )
            }
        }

    }

    fun setGem() {

        binding.run {
            val gemViewList = listOf(
                gem1,
                gem2,
                gem3,
                gem4,
                gem5,
                gem6,
                gem7,
                gem8,
                gem9,
                gem10,
                gem11
            )

            val sortedGemList =
                charInfo.armoryGem?.gems?.sortedWith(compareByDescending<com.lostark.loahelper.dto.armorys.Gem> { it.level }.thenBy { it.name })
            sortedGemList?.forEachIndexed { index, gem ->
                toolTipDeserialization(gem)?.let {
                    gemViewList.get(index).setGemImageText(gem, it)
                    gemViewList.get(index).setOnClickListener {
                        (activity as SearchDetailActivity).openDialog(it, "")
                    }
                }
            }
        }
    }

    fun setEngraving() {

        var check = true

        binding.run {
            charInfo.armoryEngraving?.engravings?.forEach {
                if (check) {
                    setEquipmentImageText(charSearchDetailAbilityEngraving1, "0")
                    check = false
                } else setEquipmentImageText(charSearchDetailAbilityEngraving2, "1")
            }
            charSearchDetailAbilityEngraving1.setOnClickListener {
                (activity as SearchDetailActivity).openDialog(it, "")
            }
            charSearchDetailAbilityEngraving2.setOnClickListener {
                (activity as SearchDetailActivity).openDialog(it, "")
            }
        }

    }

    fun setAccessory() {
        binding.run {
            setEquipmentImageText(charSearchDetailAbilityNecklace, "목걸이")

            val earringList = charInfo.armoryEquipment?.filter { it.type == "귀걸이" }

            var earring1Tooltip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip? = null
            var earring2Tooltip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip? = null

            earringList?.forEach {
                if (earring1Tooltip == null) earring1Tooltip = toolTipDeserialization(it)
                else earring2Tooltip = toolTipDeserialization(it)

            }

            earring1Tooltip?.let {
                charSearchDetailAbilityEarring1.setAccessoryImageText(earringList!!.get(0), earring1Tooltip!!)
            }

            earring2Tooltip?.let {
                charSearchDetailAbilityEarring2.setAccessoryImageText(earringList!!.get(1), earring2Tooltip!!)
            }

            val ringList = charInfo.armoryEquipment?.filter { it.type == "반지" }

            var ring1Tooltip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip? = null
            var ring2Tooltip: com.lostark.loahelper.dto.armorys.tooltips.Tooltip? = null

            ringList?.forEach {
                if (ring1Tooltip == null) ring1Tooltip = toolTipDeserialization(it)
                else ring2Tooltip = toolTipDeserialization(it)
            }

            ring1Tooltip?.let {
                charSearchDetailAbilityRing1.setAccessoryImageText(ringList!!.get(0), ring1Tooltip!!)
            }

            ring2Tooltip?.let {
                charSearchDetailAbilityRing2.setAccessoryImageText(ringList!!.get(1), ring2Tooltip!!)
            }
            setEquipmentImageText(charSearchDetailAbilityBracelet, "팔찌")
            setEquipmentImageText(charSearchDetailAbilityStone, "어빌리티 스톤")

            val accessoryViewList = listOf(
                charSearchDetailAbilityNecklace,
                charSearchDetailAbilityEarring1,
                charSearchDetailAbilityEarring2,
                charSearchDetailAbilityRing1,
                charSearchDetailAbilityRing2,
                charSearchDetailAbilityStone,
                charSearchDetailAbilityBracelet
            )
            accessoryViewList.forEach {
                if (it.imageUrl != null)
                    it.setOnClickListener { (activity as SearchDetailActivity).openDialog(it, "") }
            }
        }

    }

    fun setEquipmentImageText(view: Any, type: String) {
        when (view) {
            is CharSearchArmorView -> {
                if(charInfo.armoryEquipment!=null) {
                    val tooltip =
                        toolTipDeserialization(charInfo.armoryEquipment.find { it.type == type })
                    tooltip?.let {
                        view.setArmorImageText(
                            charInfo.armoryEquipment.find { it.type == type }!!,
                            tooltip
                        )
                    }
                }
            }
            is CharSearchAccessoryView -> {
                if(charInfo.armoryEquipment!=null) {
                    val tooltip =
                        toolTipDeserialization(charInfo.armoryEquipment.find { it.type == type })
                    tooltip?.let {
                        view.setAccessoryImageText(
                            charInfo.armoryEquipment.find { it.type == type }!!,
                            tooltip
                        )
                    }
                }

            }
            is CharSearchEngravingBookView -> {
                if (charInfo.armoryEngraving != null) {
                    val tooltip =
                        toolTipDeserialization(charInfo.armoryEngraving.engravings.get(type.toInt()))
                    tooltip?.let {
                        view.setEngravingImageText(
                            charInfo.armoryEngraving.engravings.get(type.toInt()),
                            tooltip
                        )
                    }
                }
            }
        }
    }

    fun setArmors() {

        binding.run {
            setEquipmentImageText(charSearchDetailAbilityHat, "투구")
            setEquipmentImageText(charSearchDetailAbilityShoulder, "어깨")
            setEquipmentImageText(charSearchDetailAbilityTop, "상의")
            setEquipmentImageText(charSearchDetailAbilityBottom, "하의")
            setEquipmentImageText(charSearchDetailAbilityGloves, "장갑")
            setEquipmentImageText(charSearchDetailAbilityWeapon, "무기")

            charSearchDetailAbilityHat?.let {
                if (it.elixirSpecialString != null) {
                    charSearchDetailAbilityWeapon.getArmorElixirSpecial().text = it.elixirSpecialString
                    charSearchDetailAbilityWeapon.getArmorElixirSpecial().visibility = View.VISIBLE
                }
            }
            val elixirSpecialDetailString: String? = charSearchDetailAbilityHat.elixirSpecialDetailString

            val armoryViewList =
                listOf(charSearchDetailAbilityHat, charSearchDetailAbilityShoulder, charSearchDetailAbilityTop,
                    charSearchDetailAbilityBottom, charSearchDetailAbilityGloves, charSearchDetailAbilityWeapon)
            armoryViewList.forEach {
                if (it.imageUrl != null)
                    it.setOnClickListener {
                        (activity as SearchDetailActivity).openDialog(
                            it,
                            elixirSpecialDetailString
                        )
                    }
            }
        }
    }

    fun toolTipDeserialization(vararg items: Any?): com.lostark.loahelper.dto.armorys.tooltips.Tooltip? {
        val gson = GsonBuilder()
            .registerTypeAdapter(com.lostark.loahelper.dto.armorys.tooltips.ValueData::class.java, ValueDataAdapter())
            .create()
        val pattern = "<.*?>".toRegex()
        val pattern2 = "<BR>|<br>".toRegex()
        val tooltips = items.mapNotNull { item ->
            when (item) {
                is ArmoryEquipment -> item.tooltip
                is Engraving -> item.tooltip
                is Gem -> item.tooltip
                is Card -> item.tooltip
                else -> return null
            }?.replace(pattern2, "\n")?.replace(pattern, "")
        }
        val jsonString = "{\n\"Elements\":\n${tooltips.joinToString(separator = ",\n")}\n}"
        return gson.fromJson(jsonString, com.lostark.loahelper.dto.armorys.tooltips.Tooltip::class.java)
    }

}