package com.lostark.loahelper.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import com.lostark.loahelper.customview.*
import com.lostark.loahelper.dto.armorys.*
import com.lostark.loahelper.R
import com.lostark.loahelper.adapter.ValueDataAdapter


class SkillFragment(private val charInfo:Armories) : Fragment() {

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

    fun findSkillAbility(findText:String, skillList:List<com.lostark.loahelper.dto.armorys.ArmorySkill>?, textView:TextView){
        var max = 0
        if(findText=="5렙 트포"){
            skillList?.forEach {
                max+=it.tripods.filter { it.isSelected&&it.level==5 }.size
            }
        }
        else{
            skillList?.forEach {
                if(it.tooltip.contains(findText)) max+=1
            }
        }
        textView.text=findText+" "+max.toString()+"개"
        var spannableString = SpannableString(textView.text.toString())
        val start = textView.text.toString().indexOf(max.toString()+"개")
        val end = start+(max.toString()+"개").length
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#a653ec")),start,end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannableString
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
            val skillView = CharSearchSkillView(requireContext())
            skillView.setImageText(it)
            skillView.layoutParams = layoutParams
            charInfo.armoryGem?.gems?.let {
                skillView.setGem(it)
            }
            skillLayout.addView(skillView)
        }

        val maxTripodText = view.findViewById<TextView>(R.id.char_search_detail_skill_max_tripod)
        findSkillAbility("5렙 트포",useSkillList,maxTripodText)

        val counterSkillText = view.findViewById<TextView>(R.id.char_search_detail_skill_counter)
        findSkillAbility("카운터",useSkillList,counterSkillText)

        val powerlessText = view.findViewById<TextView>(R.id.char_search_detail_skill_powerless)
        findSkillAbility("무력화",useSkillList,powerlessText)

        val destroyText = view.findViewById<TextView>(R.id.char_search_detail_skill_destroy)
        findSkillAbility("부위 파괴",useSkillList,destroyText)

    }

}