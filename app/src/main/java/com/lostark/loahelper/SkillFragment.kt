package com.lostark.loahelper

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
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.google.gson.GsonBuilder
import com.lostark.adapter.ValueDataAdapter
import com.lostark.customview.*
import com.lostark.dto.armorys.*
import com.lostark.dto.armorys.tooltips.Tooltip
import com.lostark.dto.armorys.tooltips.ValueData
import org.w3c.dom.Text
import java.text.NumberFormat
import java.util.*


class SkillFragment(private val charInfo: Armories) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.char_search_detail_skills_fragment, container, false)
        setSkillPoint(view)
        setSkills(view)
        return view
    }

    fun setSkillPoint(view:View){
        val skillPoint = view.findViewById<TextView>(R.id.char_search_detail_skills_point)
        skillPoint.text = charInfo.armoryProfile.usingSkillPoint.toString()+" / "+charInfo.armoryProfile.totalSkillPoint.toString()
    }

    fun setSkills(view:View){
        val skillLayout = view.findViewById<LinearLayout>(R.id.char_search_detail_skill_main_layout)

        val useSkillList = charInfo.armorySkills?.filter { it.rune!=null || it.level!=1}?.sortedByDescending { it.level }
        val marginBottomDp = 2 // 변경하려는 marginBottom 값 (dp)

        val marginBottomPx = (marginBottomDp * resources.displayMetrics.density).toInt()

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, marginBottomPx)
        useSkillList?.forEach{
            val skillView = CharSearchSkillView(context)
            skillView.setImageText(it)
            skillView.layoutParams = layoutParams
            charInfo.armoryGem?.gems?.let {
                skillView.setGem(it)
            }
            skillLayout.addView(skillView)
        }

        val maxTripodText = view.findViewById<TextView>(R.id.char_search_detail_skill_max_tripod)
        var max = 0
        useSkillList?.forEach {
            max+=it.tripods.filter { it.isSelected&&it.level==5 }.size
        }
        maxTripodText.text="5렙 트포 "+max.toString()+"개"
        var spannableString = SpannableString(maxTripodText.text.toString())
        var start = maxTripodText.text.toString().indexOf(max.toString()+"개")
        var end = start+(max.toString()+"개").length
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#a653ec")),start,end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        maxTripodText.text = spannableString

        val counterSkillText = view.findViewById<TextView>(R.id.char_search_detail_skill_counter)

        max=0
        useSkillList?.forEach {
            if(it.tooltip.contains("카운터")) max+=1
        }

        counterSkillText.text="카운터 "+max.toString()+"개"
        spannableString = SpannableString(counterSkillText.text.toString())
        start = counterSkillText.text.toString().indexOf(max.toString()+"개")
        end = start+(max.toString()+"개").length
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#a653ec")),start,end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        counterSkillText.text = spannableString

        val powerlessText = view.findViewById<TextView>(R.id.char_search_detail_skill_powerless)

        max=0
        useSkillList?.forEach {
            if(it.tooltip.contains("무력화")) max+=1
        }

        powerlessText.text="무력화 "+max.toString()+"개"
        spannableString = SpannableString(powerlessText.text.toString())
        start = powerlessText.text.toString().indexOf(max.toString()+"개")
        end = start+(max.toString()+"개").length
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#a653ec")),start,end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        powerlessText.text = spannableString

        val destroyText = view.findViewById<TextView>(R.id.char_search_detail_skill_destroy)

        max=0
        useSkillList?.forEach {
            if(it.tooltip.contains("부위 파괴")) max+=1
        }

        destroyText.text="부위 파괴 "+max.toString()+"개"
        spannableString = SpannableString(destroyText.text.toString())
        start = destroyText.text.toString().indexOf(max.toString()+"개")
        end = start+(max.toString()+"개").length
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#a653ec")),start,end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        destroyText.text = spannableString

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
                is Gem -> item.tooltip
                is Card -> item.tooltip
                else -> return null
            }?.replace(pattern2, "\n")?.replace(pattern, "")
        }
        val jsonString = "{\n\"Elements\":\n${tooltips.joinToString(separator = ",\n")}\n}"
        return gson.fromJson(jsonString, Tooltip::class.java)
    }

}