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
import com.lostark.loahelper.databinding.CharSearchDetailAvatarFragmentBinding


class AvatarFragment(private val charInfo: Armories) : BaseFragment<CharSearchDetailAvatarFragmentBinding>(CharSearchDetailAvatarFragmentBinding::inflate) {

    override fun initView() {
        setAvatar()
    }

    fun setAvatar() {
        binding.run {
            charSearchDetailAvatarTendenciesIntelli.text =
                " " + charInfo.armoryProfile.tendencies.get(0).point.toString()//지성, 담력, 매력, 친절 순으로 4개있음
            charSearchDetailAvatarTendenciesCourage.text = " " + charInfo.armoryProfile.tendencies.get(1).point.toString()
            charSearchDetailAvatarTendenciesCharming.text = " " + charInfo.armoryProfile.tendencies.get(2).point.toString()
            charSearchDetailAvatarTendenciesKind.text = " " + charInfo.armoryProfile.tendencies.get(3).point.toString()

            charInfo.armoryProfile.characterImage?.let {
                Glide.with(this@AvatarFragment)
                    .load(it)
                    .into(charSearchDetailAvatarCharImage)
            }

            val viewList = listOf(
                charSearchDetailAvatarWeapon,
                charSearchDetailAvatarWeaponInner,
                charSearchDetailAvatarHat,
                charSearchDetailAvatarHatInner,
                charSearchDetailAvatarTop,
                charSearchDetailAvatarTopInner,
                charSearchDetailAvatarBottom,
                charSearchDetailAvatarBottomInner,
                charSearchDetailAvatarFace1,
                charSearchDetailAvatarFace2,
                charSearchDetailAvatarInstrument,
                charSearchDetailAvatarMove
            )



            charInfo.armoryAvatars?.let {
                val groupedAvatarList = it.groupBy { it.type }
                groupedAvatarList.get("머리 아바타")?.let { hatAvatarList ->
                    setAvatarView(charSearchDetailAvatarHat, charSearchDetailAvatarHatInner, hatAvatarList)
                }
                groupedAvatarList.get("무기 아바타")?.let { weaponAvatarList ->
                    setAvatarView(charSearchDetailAvatarWeapon, charSearchDetailAvatarWeaponInner, weaponAvatarList)
                }
                groupedAvatarList.get("상의 아바타")?.let { topAvatarList ->
                    setAvatarView(charSearchDetailAvatarTop, charSearchDetailAvatarTopInner, topAvatarList)
                }
                groupedAvatarList.get("하의 아바타")?.let { bottomAvatarList ->
                    setAvatarView(charSearchDetailAvatarBottom, charSearchDetailAvatarBottomInner, bottomAvatarList)
                }
                groupedAvatarList.get("악기 아바타")?.let {
                    val armoryAvatar = it.get(0)
                    toolTipDeserialization(armoryAvatar)?.let {
                        charSearchDetailAvatarInstrument.setImageText(armoryAvatar, it)
                    }
                }
                groupedAvatarList.get("얼굴1 아바타")?.let {
                    val armoryAvatar = it.get(0)
                    toolTipDeserialization(armoryAvatar)?.let {
                        charSearchDetailAvatarFace1.setImageText(armoryAvatar, it)
                    }

                }
                groupedAvatarList.get("얼굴2 아바타")?.let {
                    val armoryAvatar = it.get(0)
                    toolTipDeserialization(armoryAvatar)?.let {
                        charSearchDetailAvatarFace2.setImageText(armoryAvatar, it)
                    }

                }
                groupedAvatarList.get("")?.let {
                    val armoryAvatar = it.get(0)
                    toolTipDeserialization(armoryAvatar)?.let {
                        charSearchDetailAvatarMove.setImageText(armoryAvatar, it)
                    }
                }
            }
            viewList.forEach {
                if (it.imageUrl != "") {
                    it.setOnClickListener { (activity as SearchDetailActivity).openDialog(it, "") }
                }
            }
        }
    }

    fun setAvatarView(
        useView: CharSearchAvatarView,
        useInnerView: CharSearchAvatarView?,
        avatarList: List<ArmoryAvatar>
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