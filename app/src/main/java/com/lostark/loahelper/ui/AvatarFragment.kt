package com.lostark.loahelper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.lostark.loahelper.customview.*
import com.lostark.loahelper.dto.armorys.*
import com.lostark.loahelper.R
import com.lostark.loahelper.adapter.ValueDataAdapter


class AvatarFragment(private val charInfo: com.lostark.loahelper.dto.armorys.Armories) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.char_search_detail_avatar_fragment, container, false)
        setAvatar(view)
        return view
    }

    fun setAvatar(view: View) {
        val intelliTextView =
            view.findViewById<TextView>(R.id.char_search_detail_avatar_tendencies_intelli)
        val courageTextView =
            view.findViewById<TextView>(R.id.char_search_detail_avatar_tendencies_courage)
        val kindTextView =
            view.findViewById<TextView>(R.id.char_search_detail_avatar_tendencies_kind)
        val charmingTextView =
            view.findViewById<TextView>(R.id.char_search_detail_avatar_tendencies_charming)

        intelliTextView.text =
            " " + charInfo.armoryProfile.tendencies.get(0).point.toString()//지성, 담력, 매력, 친절 순으로 4개있음
        courageTextView.text = " " + charInfo.armoryProfile.tendencies.get(1).point.toString()
        charmingTextView.text = " " + charInfo.armoryProfile.tendencies.get(2).point.toString()
        kindTextView.text = " " + charInfo.armoryProfile.tendencies.get(3).point.toString()


        val characterImageView =
            view.findViewById<ImageView>(R.id.char_search_detail_avatar_char_image)

        charInfo.armoryProfile.characterImage?.let {
            Glide.with(this)
                .load(it)
                .into(characterImageView)
        }

        val weaponAvatarView =
            view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_weapon)
        val weaponInnerAvatarView =
            view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_weapon_inner)

        val hatAvatarView =
            view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_hat)
        val hatInnerAvatarView =
            view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_hat_inner)

        val topAvatarView =
            view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_top)
        val topInnerAvatarView =
            view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_top_inner)

        val bottomAvatarView =
            view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_bottom)
        val bottomInnerAvatarView =
            view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_bottom_inner)

        val face1AvatarView =
            view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_face1)
        val face2AvatarView =
            view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_face2)

        val instrumentAvatarView =
            view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_instrument)
        val moveAvatarView =
            view.findViewById<CharSearchAvatarView>(R.id.char_search_detail_avatar_move)

        val viewList = listOf(weaponAvatarView,weaponInnerAvatarView,hatAvatarView,hatInnerAvatarView,topAvatarView,topInnerAvatarView,bottomAvatarView,bottomInnerAvatarView,face1AvatarView,face2AvatarView,instrumentAvatarView,moveAvatarView)



        charInfo.armoryAvatars?.let {
            val groupedAvatarList = it.groupBy { it.type }
            groupedAvatarList.get("머리 아바타")?.let { hatAvatarList ->
                setAvatarView(hatAvatarView, hatInnerAvatarView, hatAvatarList)
            }
            groupedAvatarList.get("무기 아바타")?.let { weaponAvatarList ->
                setAvatarView(weaponAvatarView, weaponInnerAvatarView, weaponAvatarList)
            }
            groupedAvatarList.get("상의 아바타")?.let { topAvatarList ->
                setAvatarView(topAvatarView, topInnerAvatarView, topAvatarList)
            }
            groupedAvatarList.get("하의 아바타")?.let { bottomAvatarList ->
                setAvatarView(bottomAvatarView, bottomInnerAvatarView, bottomAvatarList)
            }
            groupedAvatarList.get("악기 아바타")?.let {
                val armoryAvatar = it.get(0)
                toolTipDeserialization(armoryAvatar)?.let {
                    instrumentAvatarView.setImageText(armoryAvatar, it)
                }
            }
            groupedAvatarList.get("얼굴1 아바타")?.let {
                val armoryAvatar = it.get(0)
                toolTipDeserialization(armoryAvatar)?.let {
                    face1AvatarView.setImageText(armoryAvatar, it)
                }

            }
            groupedAvatarList.get("얼굴2 아바타")?.let {
                val armoryAvatar = it.get(0)
                toolTipDeserialization(armoryAvatar)?.let {
                    face2AvatarView.setImageText(armoryAvatar, it)
                }

            }
            groupedAvatarList.get("")?.let {
                val armoryAvatar = it.get(0)
                toolTipDeserialization(armoryAvatar)?.let {
                    moveAvatarView.setImageText(armoryAvatar, it)
                }
            }
        }
        viewList.forEach {
            if(it.imageUrl!=""){
                it.setOnClickListener { (activity as SearchDetailActivity).openDialog(it, "") }
            }
        }
    }

    fun setAvatarView(
        useView: CharSearchAvatarView,
        useInnerView: CharSearchAvatarView?,
        avatarList: List<com.lostark.loahelper.dto.armorys.ArmoryAvatar>
    ) {
        if (avatarList.size == 1) {
            toolTipDeserialization(avatarList.get(0))?.let {
                useView.setImageText(avatarList.get(0), it)
            }
        } else {
            avatarList.forEach {
                val armoryAvatar = it
                if (it.isInner) {
                    toolTipDeserialization(it)?.let {
                        useView.setImageText(armoryAvatar, it)
                    }

                } else {
                    toolTipDeserialization(it)?.let {
                        useInnerView?.setImageText(armoryAvatar, it)
                    }
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
                is com.lostark.loahelper.dto.armorys.ArmoryEquipment -> item.tooltip
                is com.lostark.loahelper.dto.armorys.Engraving -> item.tooltip
                is com.lostark.loahelper.dto.armorys.Gem -> item.tooltip
                is com.lostark.loahelper.dto.armorys.Card -> item.tooltip
                is com.lostark.loahelper.dto.armorys.ArmoryAvatar -> item.tooltip
                else -> return null
            }?.replace(pattern2, "\n")?.replace(pattern, "")
        }
        val jsonString = "{\n\"Elements\":\n${tooltips.joinToString(separator = ",\n")}\n}"
        return gson.fromJson(jsonString, com.lostark.loahelper.dto.armorys.tooltips.Tooltip::class.java)
    }

}