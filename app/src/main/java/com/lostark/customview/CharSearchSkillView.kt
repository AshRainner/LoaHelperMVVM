package com.lostark.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.google.gson.GsonBuilder
import com.lostark.adapter.ValueDataAdapter
import com.lostark.dto.armorys.*
import com.lostark.dto.armorys.tooltips.*
import com.lostark.loahelper.R
import com.lostark.loahelper.SearchDetailActivity

class CharSearchSkillView : LinearLayout {

    lateinit var skillLayout: CharSearchSkillLayoutView
    lateinit var runeView: CharSearchRuneView
    lateinit var gemImage1: CharSearchGemView
    lateinit var gemImage2: CharSearchGemView
    lateinit var tripodLayout: LinearLayout
    lateinit var tripodView1: CharSearchTripodView
    lateinit var tripodView2: CharSearchTripodView
    lateinit var tripodView3: CharSearchTripodView

    lateinit var imageUrl: String

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.char_search_detail_skill_view, this, false)
        addView(view)
        skillLayout = view.findViewById(R.id.char_search_detail_skill_layout)
        runeView = view.findViewById(R.id.char_search_detail_skill_rune_view)
        gemImage1 = view.findViewById(R.id.char_search_detail_skill_gem_1)
        gemImage2 = view.findViewById(R.id.char_search_detail_skill_gem_2)
        tripodLayout = view.findViewById(R.id.char_search_detail_skill_tripod_layout)
        tripodView1 = view.findViewById(R.id.char_search_detail_skill_tripod_1)
        tripodView2 = view.findViewById(R.id.char_search_detail_skill_tripod_2)
        tripodView3 = view.findViewById(R.id.char_search_detail_skill_tripod_3)
        gemImage1.goneDefaultGem()
        gemImage2.goneDefaultGem()


    }

    fun setDialog(view: Any) {
        when (view) {
            is CharSearchGemView -> {
                view.setOnClickListener {
                    (context as SearchDetailActivity).openDialog(it, "")
                }
            }
            is CharSearchRuneView -> {
                view.setOnClickListener {
                    (context as SearchDetailActivity).openDialog(it, "")
                }
            }
            is CharSearchSkillLayoutView -> {
                view.setOnClickListener {
                    (context as SearchDetailActivity).openDialog(it, "")
                }
            }
            is CharSearchTripodView -> {
                view.setOnClickListener {
                    (context as SearchDetailActivity).openDialog(it, "")
                }
            }
        }
    }

    fun setGem(gemList: List<Gem>) {
        val useGemList =
            gemList.filter { it.tooltip.contains(skillLayout.skillName.text) }
        useGemList.forEachIndexed { index, gem ->
            if (gem.name.contains("홍염")) {
                gemImage2.setSkillGemImageText(gem, toolTipDeserialization(gem))
            } else {
                gemImage1.setSkillGemImageText(gem, toolTipDeserialization(gem))
            }
        }
        gemImage1.gemDetail?.let { setDialog(gemImage1) }
        gemImage2.gemDetail?.let { setDialog(gemImage2) }

    }

    fun toolTipDeserialization(vararg items: Any?): Tooltip {

        val gson = GsonBuilder()
            .registerTypeAdapter(ValueData::class.java, ValueDataAdapter())
            .create()
        val pattern = "<.*?>".toRegex()
        val pattern2 = "<BR>|<br>".toRegex()
        val tooltips = items.mapNotNull { item ->
            when (item) {
                is ArmoryEquipment -> item.tooltip
                is Engraving -> item.tooltip
                is Gem -> item.tooltip
                is Card -> item.tooltip
                is Rune -> item.tooltip
                is ArmorySkill -> item.tooltip
                else -> ""
            }?.replace(pattern2, "\n")?.replace(pattern, "")
        }
        val jsonString = "{\n\"Elements\":\n${tooltips.joinToString(separator = ",\n")}\n}"
        return gson.fromJson(jsonString, Tooltip::class.java)
    }

    fun setImageText(skill: ArmorySkill) {
        skillLayout.setImageText(skill, toolTipDeserialization(skill))
        setDialog(skillLayout)

        skill.rune?.let {
            runeView.setRuneImageText(it, toolTipDeserialization(it))
            setDialog(runeView)
        }
        val useTripod = skill.tripods.filter { it.isSelected }

        if (useTripod != null) {
            if (useTripod.size != 0)
                tripodLayout.visibility = View.VISIBLE
            val tripodList = listOf(tripodView1, tripodView2, tripodView3)
            useTripod.sortedBy { it.tier }.forEachIndexed { index, tripod ->
                val tripodView = tripodList.get(index)
                tripodView.setDiamondBackground(tripod.tier, tripod.slot)
                tripodView.setTripodImageText(tripod, index, toolTipDeserialization(skill))
                tripodView.visibility = View.VISIBLE
                setDialog(tripodView)
            }
        }
    }
}