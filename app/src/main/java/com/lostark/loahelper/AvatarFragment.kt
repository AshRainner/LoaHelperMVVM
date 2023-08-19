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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.lostark.adapter.ValueDataAdapter
import com.lostark.customview.*
import com.lostark.dto.armorys.*
import com.lostark.dto.armorys.tooltips.Tooltip
import com.lostark.dto.armorys.tooltips.ValueData
import org.w3c.dom.Text
import java.text.NumberFormat
import java.util.*


class AvatarFragment(private val charInfo: Armories) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.char_search_detail_avatar_fragment, container, false)
        setAvatar(view)
        return view
    }

    fun setAvatar(view:View){
        val intelliTextView = view.findViewById<TextView>(R.id.char_search_detail_avatar_tendencies_intelli)
        val courageTextView = view.findViewById<TextView>(R.id.char_search_detail_avatar_tendencies_courage)
        val kindTextView = view.findViewById<TextView>(R.id.char_search_detail_avatar_tendencies_kind)
        val charmingTextView = view.findViewById<TextView>(R.id.char_search_detail_avatar_tendencies_charming)

        intelliTextView.text = " "+charInfo.armoryProfile.tendencies.get(0).point.toString()//지성, 담력, 매력, 친절 순으로 4개있음
        courageTextView.text = " "+charInfo.armoryProfile.tendencies.get(1).point.toString()
        kindTextView.text = " "+charInfo.armoryProfile.tendencies.get(2).point.toString()
        charmingTextView.text = " "+charInfo.armoryProfile.tendencies.get(3).point.toString()

        val characterImageView = view.findViewById<ImageView>(R.id.char_search_detail_avatar_char_image)

        charInfo.armoryProfile.characterImage?.let{
            Glide.with(this)
                .load(it)
                .into(characterImageView)
        }

        val weaponAvatarView = view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_weapon)
        val weaponInnerAvatarView = view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_weapon_inner)

        val hatAvatarView = view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_hat)
        val hatInnerAvatarView = view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_hat_inner)

        val topAvatarView = view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_top)
        val topInnerAvatarView = view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_top_inner)

        val bottomAvatarView = view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_bottom)
        val bottomInnerAvatarView = view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_bottom_inner)

        val face1AvatarView = view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_face1)
        val face2AvatarView = view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_face2)

        val instrumentAvatarView = view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_instrument)
        val moveAvatarView = view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_move)



        charInfo.armoryAvatars?.let {
            val groupedAvatarList = it.groupBy { it.type }
            groupedAvatarList.get("머리 아바타")?.let { hatAvatarList->
                setAvatarView(hatAvatarView,hatInnerAvatarView,hatAvatarList)
                }
            groupedAvatarList.get("무기 아바타")?.let { weaponAvatarList->
                setAvatarView(weaponAvatarView,weaponInnerAvatarView,weaponAvatarList)
            }
            groupedAvatarList.get("상의 아바타")?.let { topAvatarList->
                setAvatarView(topAvatarView,topInnerAvatarView,topAvatarList)
            }
            groupedAvatarList.get("하의 아바타")?.let { bottomAvatarList->
                setAvatarView(bottomAvatarView,bottomInnerAvatarView,bottomAvatarList)
            }
            groupedAvatarList.get("악기 아바타")?.let {
                instrumentAvatarView.setImageText(it.get(0),toolTipDeserialization(it.get(0)))
            }
            groupedAvatarList.get("얼굴1 아바타")?.let {
                face1AvatarView.setImageText(it.get(0),toolTipDeserialization(it.get(0)))
            }
            groupedAvatarList.get("얼굴2 아바타")?.let {
                face2AvatarView.setImageText(it.get(0),toolTipDeserialization(it.get(0)))
            }
            groupedAvatarList.get("")?.let {
                moveAvatarView.setImageText(it.get(0),toolTipDeserialization(it.get(0)))
            }
        }
    }

    fun setAvatarView(useView:CharSearchAvatarView,useInnerView:CharSearchAvatarView?,avatarList:List<ArmoryAvatar>){
        if(avatarList.size==1){
            useView.setImageText(avatarList.get(0),toolTipDeserialization(avatarList.get(0)))
        }
        else{
            avatarList.forEach{
                if(it.isInner){
                    useView.setImageText(it,toolTipDeserialization(it))
                }
                else{
                    useInnerView?.setImageText(it,toolTipDeserialization(it))
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
                is Gem -> item.tooltip
                is Card -> item.tooltip
                is ArmoryAvatar -> item.tooltip
                else -> return null
            }?.replace(pattern2, "\n")?.replace(pattern, "")
        }
        val jsonString = "{\n\"Elements\":\n${tooltips.joinToString(separator = ",\n")}\n}"
        return gson.fromJson(jsonString, Tooltip::class.java)
    }

}