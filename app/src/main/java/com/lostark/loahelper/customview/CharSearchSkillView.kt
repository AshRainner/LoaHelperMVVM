package com.lostark.loahelper.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.google.gson.GsonBuilder
import com.lostark.loahelper.R
import com.lostark.loahelper.adapter.ValueDataAdapter
import com.lostark.loahelper.databinding.CharSearchDetailSkillLayoutViewBinding
import com.lostark.loahelper.databinding.CharSearchDetailSkillViewBinding
import com.lostark.loahelper.ui.SearchDetailActivity
import com.lostark.loahelper.dto.armorys.*
import com.lostark.loahelper.dto.armorys.tooltips.Tooltip

class CharSearchSkillView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailSkillViewBinding>(
    context,
    attrs,
    defStyleAttr
) {

    lateinit var imageUrl: String

    override fun init(context: Context?) {

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
            gemList.filter { it.tooltip.contains(binding.charSearchDetailSkillLayout.getSkillName().text) }
        useGemList.forEachIndexed { index, gem ->
            if (gem.name.contains("홍염")) {
                binding.charSearchDetailSkillGem2.setSkillGemImageText(gem, toolTipDeserialization(gem))
            } else {
                binding.charSearchDetailSkillGem1.setSkillGemImageText(gem, toolTipDeserialization(gem))
            }
        }
        binding.charSearchDetailSkillGem1.gemDetail?.let { setDialog(binding.charSearchDetailSkillGem1) }
        binding.charSearchDetailSkillGem2.gemDetail?.let { setDialog(binding.charSearchDetailSkillGem2) }

    }

    fun toolTipDeserialization(vararg items: Any?): Tooltip {

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
                is Rune -> item.tooltip
                is ArmorySkill -> item.tooltip
                else -> ""
            }?.replace(pattern2, "\n")?.replace(pattern, "")
        }
        val jsonString = "{\n\"Elements\":\n${tooltips.joinToString(separator = ",\n")}\n}"
        return gson.fromJson(jsonString, com.lostark.loahelper.dto.armorys.tooltips.Tooltip::class.java)
    }

    fun setImageText(skill: ArmorySkill) {
        binding.run {
            charSearchDetailSkillLayout.setImageText(skill, toolTipDeserialization(skill))
            setDialog(charSearchDetailSkillLayout)

            skill.rune?.let {
                charSearchDetailSkillRuneView.setRuneImageText(it, toolTipDeserialization(it))
                setDialog(charSearchDetailSkillRuneView)
            }
            val useTripod = skill.tripods.filter { it.isSelected }

            if (useTripod != null) {
                if (useTripod.size != 0)
                    charSearchDetailSkillTripodLayout.visibility = View.VISIBLE
                val tripodList = listOf(charSearchDetailSkillTripod1, charSearchDetailSkillTripod2, charSearchDetailSkillTripod3)
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

    override fun getAttrs(attrs: AttributeSet?) {
    }

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailSkillViewBinding {
        return CharSearchDetailSkillViewBinding.inflate(inflater)
    }
}