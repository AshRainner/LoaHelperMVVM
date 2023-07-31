package com.lostark.loahelper

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import com.google.gson.GsonBuilder
import com.lostark.adapter.ValueDataAdapter
import com.lostark.customview.*
import com.lostark.dto.armorys.*
import com.lostark.dto.armorys.tooltips.Tooltip
import com.lostark.dto.armorys.tooltips.ValueData
import java.text.NumberFormat
import java.util.*


class AbilityFragment(private val charInfo: Armories) : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.char_search_detail_ability_fragment, container, false)

        setArmors(view)
        setAccessory(view)
        setEngraving(view)
        setGem(view)
        setBottomStatus(view)
        setBottomEngraving(view)
        return view
    }

    fun setBottomEngraving(view:View){
        val bottomEngravingLayout = view.findViewById<LinearLayout>(R.id.char_search_detail_ability_bottom_engraving_layout)

        val engravingList=(activity as SearchDetailActivity).charInfo.armoryEngraving.effects
        engravingList.forEach {
            val bottomEngravingView = CharSearchEngravingBottomView(view.context)
            var pattern = "\\d+".toRegex()
            val level = pattern.find(it.name)?.value
            pattern = "Lv.\\s\\d+".toRegex()
            val imageUrl = engravingImageDict[it.name.replace(pattern,"").replace(" ","")]?:"https://cdn-lostark.game.onstove.com/efui_iconatlas/achieve/achieve_07_22.png"

            bottomEngravingView.setEngravingImageText(it.name.replace(pattern,""),it.description,level!!,imageUrl)
            bottomEngravingLayout.addView(bottomEngravingView)
        }
    }

    fun setBottomStatus(view: View){
        val charPowerView = view.findViewById<TextView>(R.id.char_search_detail_char_power)
        val charDefaultPowerView = view.findViewById<TextView>(R.id.char_search_detail_char_default_power)
        val charAddPowerView = view.findViewById<TextView>(R.id.char_search_detail_char_add_power)
        val charLifeView = view.findViewById<TextView>(R.id.char_search_detail_char_life)

        var pattern = "<.*?>".toRegex()
        val powerTooltip = mutableListOf<String>()
        charInfo.armoryProfile.stats.find { it.type == "공격력"}?.tooltip?.forEach {
            powerTooltip.add(it.replace(pattern,""))
        }

        pattern="\\d+".toRegex()
        charLifeView.text = charInfo.armoryProfile.stats.find { it.type == "최대 생명력" }?.value
        charPowerView.text = NumberFormat.getNumberInstance(Locale.US).format(charInfo.armoryProfile.stats.find { it.type == "공격력" }?.value?.toInt())?:"error"
        charDefaultPowerView.text = NumberFormat.getNumberInstance(Locale.US).format(pattern.find(powerTooltip.get(1))?.value?.toInt())?:"error"
        charAddPowerView.text = NumberFormat.getNumberInstance(Locale.US).format(pattern.find(powerTooltip.get(2))?.value?.toInt())?:"error"



        val criticalText = view.findViewById<TextView>(R.id.char_search_detail_critical_ability)//치명
        val specialtyText = view.findViewById<TextView>(R.id.char_search_detail_specialty_ability)//특화
        val fastText = view.findViewById<TextView>(R.id.char_search_detail_fast_ability)//신속
        val subdueText = view.findViewById<TextView>(R.id.char_search_detail_subdue_ability)//제압
        val patienceText = view.findViewById<TextView>(R.id.char_search_detail_patience_ability)//인내
        val proficiencyText = view.findViewById<TextView>(R.id.char_search_detail_proficiency_ability)//숙련

        val statViewList = listOf<TextView>(criticalText,specialtyText,proficiencyText,fastText,patienceText,subdueText)
        val statList = mutableListOf<Int>()
        statViewList.forEachIndexed { index, textView ->
            statList.add(charInfo.armoryProfile.stats.get(index).value.toInt())
            textView.text = statList[index].toString()
        }
        statViewList.find { it.text.toString() == statList.max().toString()}?.setTextColor(
            Color.parseColor("#8a2be2"))
        statList.remove(statList.max())
        statViewList.find { it.text.toString() == statList.max().toString()}?.setTextColor(
            Color.parseColor("#00aaff"))


    }

    fun setGem(view: View) {
        val gem1View = view.findViewById<CharSearchGemView>(R.id.gem_1)
        val gem2View = view.findViewById<CharSearchGemView>(R.id.gem_2)
        val gem3View = view.findViewById<CharSearchGemView>(R.id.gem_3)
        val gem4View = view.findViewById<CharSearchGemView>(R.id.gem_4)
        val gem5View = view.findViewById<CharSearchGemView>(R.id.gem_5)
        val gem6View = view.findViewById<CharSearchGemView>(R.id.gem_6)
        val gem7View = view.findViewById<CharSearchGemView>(R.id.gem_7)
        val gem8View = view.findViewById<CharSearchGemView>(R.id.gem_8)
        val gem9View = view.findViewById<CharSearchGemView>(R.id.gem_9)
        val gem10View = view.findViewById<CharSearchGemView>(R.id.gem_10)
        val gem11View = view.findViewById<CharSearchGemView>(R.id.gem_11)

        val gemViewList = listOf(
            gem1View,
            gem2View,
            gem3View,
            gem4View,
            gem5View,
            gem6View,
            gem7View,
            gem8View,
            gem9View,
            gem10View,
            gem11View
        )

        val sortedGemList = charInfo.armoryGem?.gems?.sortedWith(compareByDescending<Gem> { it.level }.thenBy { it.name })

        sortedGemList?.forEachIndexed { index, gem ->
            toolTipDeserialization(gem)?.let {
                gemViewList.get(index).setGemImageText(gem, it)
                gemViewList.get(index).setOnClickListener {
                    (activity as SearchDetailActivity).openDialog(it, "")
                }
            }
        }

    }

    fun setEngraving(view: View) {
        val engraving1View =
            view.findViewById<CharSearchEngravingBookView>(R.id.char_search_detail_ability_engraving1)
        val engraving2View =
            view.findViewById<CharSearchEngravingBookView>(R.id.char_search_detail_ability_engraving2)

        var check = true

        charInfo.armoryEngraving?.engravings?.forEach {
            if (check) {
                setEquipmentImageText(engraving1View, "0")
                check = false
            } else setEquipmentImageText(engraving2View, "1")
        }
        engraving1View.setOnClickListener {
            (activity as SearchDetailActivity).openDialog(it, "")
        }
        engraving2View.setOnClickListener {
            (activity as SearchDetailActivity).openDialog(it, "")
        }

    }

    fun setAccessory(view: View) {
        val necklaceView =
            view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_necklace)

        setEquipmentImageText(necklaceView, "목걸이")

        val earring1View =
            view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_earring1)
        val earring2View =
            view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_earring2)

        val earringList = charInfo.armoryEquipment.filter { it.type == "귀걸이" }

        var earring1Tooltip: Tooltip? = null
        var earring2Tooltip: Tooltip? = null

        earringList.forEach {
            if (earring1Tooltip == null) earring1Tooltip = toolTipDeserialization(it)
            else earring2Tooltip = toolTipDeserialization(it)

        }

        earring1Tooltip?.let {
            earring1View.setAccessoryImageText(earringList.get(0), earring1Tooltip!!)
            setBackGroudColor(earring1View)
        }

        earring2Tooltip?.let {
            earring2View.setAccessoryImageText(earringList.get(1), earring2Tooltip!!)
            setBackGroudColor(earring2View)
        }

        val ring1View =
            view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_ring1)
        val ring2View =
            view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_ring2)

        val ringList = charInfo.armoryEquipment.filter { it.type == "반지" }

        var ring1Tooltip: Tooltip? = null
        var ring2Tooltip: Tooltip? = null

        ringList.forEach {
            if (ring1Tooltip == null) ring1Tooltip = toolTipDeserialization(it)
            else ring2Tooltip = toolTipDeserialization(it)
        }

        ring1Tooltip?.let {
            ring1View.setAccessoryImageText(ringList.get(0), ring1Tooltip!!)
            setBackGroudColor(ring1View)
        }

        ring2Tooltip?.let {
            ring2View.setAccessoryImageText(ringList.get(1), ring2Tooltip!!)
            setBackGroudColor(ring2View)
        }

        val braceletView =
            view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_bracelet)
        setEquipmentImageText(braceletView, "팔찌")

        val stoneView =
            view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_stone)
        setEquipmentImageText(stoneView, "어빌리티 스톤")

        val accessoryViewList = listOf(
            necklaceView,
            earring1View,
            earring2View,
            ring1View,
            ring2View,
            stoneView,
            braceletView
        )
        accessoryViewList.forEach {
            if (it.imageUrl != null)
                it.setOnClickListener { (activity as SearchDetailActivity).openDialog(it, "") }
        }

    }

    fun setEquipmentImageText(view: Any, type: String) {
        when (view) {
            is CharSearchArmorView -> {
                val tooltip =
                    toolTipDeserialization(charInfo.armoryEquipment.find { it.type == type })
                tooltip?.let {
                    view.setArmorImageText(
                        charInfo.armoryEquipment.find { it.type == type }!!,
                        tooltip
                    )
                    setBackGroudColor(view)
                }

            }
            is CharSearchAccessoryView -> {
                val tooltip =
                    toolTipDeserialization(charInfo.armoryEquipment.find { it.type == type })
                tooltip?.let {
                    view.setAccessoryImageText(
                        charInfo.armoryEquipment.find { it.type == type }!!,
                        tooltip
                    )
                    setBackGroudColor(view)
                }

            }
            is CharSearchEngravingBookView -> {
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

    fun setArmors(view: View) {

        val hatView = view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_hat)
        setEquipmentImageText(hatView, "투구")

        val shoulderView =
            view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_shoulder)
        setEquipmentImageText(shoulderView, "어깨")

        val topView = view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_top)
        setEquipmentImageText(topView, "상의")

        val bottomView =
            view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_bottom)
        setEquipmentImageText(bottomView, "하의")

        val glovesView =
            view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_gloves)
        setEquipmentImageText(glovesView, "장갑")

        val weaponView =
            view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_weapon)
        setEquipmentImageText(weaponView, "무기")

        hatView?.let {
            if (hatView.elixirSpecialString != null) {
                weaponView.armorElixirSpecial.text = hatView.elixirSpecialString
                weaponView.armorElixirSpecial.visibility = View.VISIBLE
            }
        }
        val elixirSpecialDetailString: String? = hatView.elixirSpecialDetailString

        val armoryViewList =
            listOf(hatView, shoulderView, topView, bottomView, glovesView, weaponView)
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

    fun setBackGroudColor(view: Any) {
        when (view) {
            is CharSearchAccessoryView -> {
                val quality = view.accessoryQuality.text.toString().toInt()
                when {
                    quality == 100 -> {
                        view.accessoryCardView.setCardBackgroundColor(Color.parseColor("#F2BD2C"))
                        view.accessoryQuality.setBackgroundColor(Color.parseColor("#F2BD2C"))
                    }
                    quality >= 90 -> {
                        view.accessoryCardView.setCardBackgroundColor(Color.parseColor("#ff00dd"))
                        view.accessoryQuality.setBackgroundColor(Color.parseColor("#ff00dd"))
                    }
                    quality >= 70 -> {
                        view.accessoryCardView.setCardBackgroundColor(Color.parseColor("#1B89F4"))
                        view.accessoryQuality.setBackgroundColor(Color.parseColor("#1B89F4"))
                    }
                    quality >= 30 -> {
                        view.accessoryCardView.setCardBackgroundColor(Color.parseColor("#35E81C"))
                        view.accessoryQuality.setBackgroundColor(Color.parseColor("#35E81C"))
                    }
                    quality >= 10 -> {
                        view.accessoryCardView.setCardBackgroundColor(Color.parseColor("#D2D208"))
                        view.accessoryQuality.setBackgroundColor(Color.parseColor("#D2D208"))
                    }
                    quality == 0 -> {
                        view.accessoryCardView.setCardBackgroundColor(Color.parseColor("#dedfe0"))
                        view.accessoryQuality.setBackgroundColor(Color.parseColor("#dedfe0"))
                    }
                    else -> {
                        view.accessoryCardView.setCardBackgroundColor(Color.parseColor("#F5260E"))
                        view.accessoryQuality.setBackgroundColor(Color.parseColor("#F5260E"))
                    }
                }
            }
            is CharSearchArmorView -> {
                val quality = view.armorQuality.text.toString().toInt()
                when {
                    quality == 100 -> {
                        view.armorCardView.setCardBackgroundColor(Color.parseColor("#F2BD2C"))
                        view.armorQuality.setBackgroundColor(Color.parseColor("#F2BD2C"))
                    }
                    quality >= 90 -> {
                        view.armorCardView.setCardBackgroundColor(Color.parseColor("#ff00dd"))
                        view.armorQuality.setBackgroundColor(Color.parseColor("#ff00dd"))
                    }
                    quality >= 70 -> {
                        view.armorCardView.setCardBackgroundColor(Color.parseColor("#1B89F4"))
                        view.armorQuality.setBackgroundColor(Color.parseColor("#1B89F4"))
                    }
                    quality >= 30 -> {
                        view.armorCardView.setCardBackgroundColor(Color.parseColor("#35E81C"))
                        view.armorQuality.setBackgroundColor(Color.parseColor("#35E81C"))
                    }
                    quality >= 10 -> {
                        view.armorCardView.setCardBackgroundColor(Color.parseColor("#D2D208"))
                        view.armorQuality.setBackgroundColor(Color.parseColor("#D2D208"))
                    }
                    quality == 0 -> {
                        view.armorCardView.setCardBackgroundColor(Color.parseColor("#dedfe0"))
                        view.armorQuality.setBackgroundColor(Color.parseColor("#dedfe0"))
                    }
                    else -> {
                        view.armorCardView.setCardBackgroundColor(Color.parseColor("#F5260E"))
                        view.armorQuality.setBackgroundColor(Color.parseColor("#F5260E"))
                    }
                }
            }
        }

    }

    fun toolTipDeserialization(vararg items: Any?): Tooltip? {
        val gson = GsonBuilder()
            .registerTypeAdapter(ValueData::class.java, ValueDataAdapter())
            .create()
        val pattern = "<.*?>".toRegex()
        val pattern2 = "<BR>|<br>".toRegex()
        val tooltips = items.mapNotNull { item ->
            when (item) {
                is ArmoryEquipment -> item.tooltip
                is Engraving -> item.tooltip
                is Gem -> {
                    item.tooltip
                }
                else -> return null
            }?.replace(pattern2, "\n")?.replace(pattern, "")
        }
        val jsonString = "{\n\"Elements\":\n${tooltips.joinToString(separator = ",\n")}\n}"
        return gson.fromJson(jsonString, Tooltip::class.java)
    }

}