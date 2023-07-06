package com.lostark.loahelper

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import com.lostark.adapter.ValueDataAdapter
import com.lostark.customview.CharSearchAccessoryView
import com.lostark.customview.CharSearchArmorView
import com.lostark.dto.armorys.Armories
import com.lostark.dto.armorys.ArmoryEquipment
import com.lostark.dto.armorys.armortooltip.Tooltip
import com.lostark.dto.armorys.armortooltip.ValueData


class AbilityFragment(private val charInfo:Armories) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.char_search_detail_ability_fragment,container,false)

        setArmors(view)
        setAccessory(view)

        return view
    }

    fun setAccessory(view:View){
        val necklaceView = view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_necklace)
        val necklaceTooltip = toolTipDeserialization(charInfo.armoryEquipment.find { it.type=="목걸이" })

        necklaceTooltip?.let {
            necklaceView.setAccessoryImageText(charInfo.armoryEquipment.find { it.type=="목걸이" }!!,necklaceTooltip)
            setArmorsBackGroudColor(necklaceView)
        }

        val earring1View = view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_earring1)
        val earring2View = view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_earring2)

        val earringList=charInfo.armoryEquipment.filter { it.type=="귀걸이" }

        var earring1Tooltip:Tooltip?=null
        var earring2Tooltip:Tooltip?=null

        earringList.forEach{
            if(earring1Tooltip == null) earring1Tooltip = toolTipDeserialization(it)
            else earring2Tooltip = toolTipDeserialization(it)

        }

        earring1Tooltip?.let {
            earring1View.setAccessoryImageText(earringList.get(0),earring1Tooltip!!)
            setArmorsBackGroudColor(earring1View)
        }

        earring2Tooltip?.let {
            earring2View.setAccessoryImageText(earringList.get(1),earring2Tooltip!!)
            setArmorsBackGroudColor(earring2View)
        }

        val ring1View = view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_ring1)
        val ring2View = view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_ring2)

        val ringList=charInfo.armoryEquipment.filter { it.type=="반지" }

        var ring1Tooltip:Tooltip?=null
        var ring2Tooltip:Tooltip?=null

        ringList.forEach{
            if(ring1Tooltip == null) ring1Tooltip = toolTipDeserialization(it)
            else ring2Tooltip = toolTipDeserialization(it)
        }

        ring1Tooltip?.let {
            ring1View.setAccessoryImageText(ringList.get(0),ring1Tooltip!!)
            setArmorsBackGroudColor(ring1View)
        }

        ring2Tooltip?.let {
            ring2View.setAccessoryImageText(ringList.get(1),ring2Tooltip!!)
            setArmorsBackGroudColor(ring2View)
        }

        val braceletView = view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_bracelet)
        val braceletTooltip = toolTipDeserialization(charInfo.armoryEquipment.find { it.type=="팔찌" })

        braceletTooltip?.let {
            braceletView.setAccessoryImageText(charInfo.armoryEquipment.find { it.type=="팔찌" }!!,braceletTooltip)
            setArmorsBackGroudColor(braceletView)
        }








    }

    fun setArmors(view:View){
        val hatToolTip = toolTipDeserialization(charInfo.armoryEquipment.find { it.type=="투구" })

        val hatView = view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_hat)

        hatToolTip?.let {
            hatView.setArmorImageText(charInfo.armoryEquipment.find { it.type=="투구" }!!,hatToolTip)
            setArmorsBackGroundColor(hatView)
        }

        val shoulderToolTip = toolTipDeserialization(charInfo.armoryEquipment.find { it.type=="어깨" })

        val shoulderView = view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_shoulder)

        shoulderToolTip?.let {
            shoulderView.setArmorImageText(charInfo.armoryEquipment.find { it.type=="어깨" }!!,shoulderToolTip)
            setArmorsBackGroundColor(shoulderView)
        }

        val topView = view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_top)

        val topToolTip = toolTipDeserialization(charInfo.armoryEquipment.find { it.type=="상의" })

        topToolTip?.let {
            topView.setArmorImageText(charInfo.armoryEquipment.find { it.type=="상의" }!!,topToolTip)
            setArmorsBackGroundColor(topView)
        }

        val bottomView = view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_bottom)

        val bottomToolTip = toolTipDeserialization(charInfo.armoryEquipment.find { it.type=="하의" })

        bottomToolTip?.let {
            bottomView.setArmorImageText(charInfo.armoryEquipment.find { it.type=="하의" }!!,bottomToolTip)
            setArmorsBackGroundColor(bottomView)
        }

        val glovesView = view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_gloves)

        val golovesToolTip = toolTipDeserialization(charInfo.armoryEquipment.find { it.type=="장갑" })

        golovesToolTip?.let {
            glovesView.setArmorImageText(charInfo.armoryEquipment.find { it.type=="장갑" }!!,golovesToolTip)
            setArmorsBackGroundColor(glovesView)
        }

        val weaponView = view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_weapon)

        val weaponToolTip = toolTipDeserialization(charInfo.armoryEquipment.find { it.type=="무기" })

        weaponToolTip?.let {
            weaponView.setArmorImageText(charInfo.armoryEquipment.find { it.type=="무기" }!!,weaponToolTip)
            setArmorsBackGroundColor(weaponView)
        }

        hatView?.let {
            if(hatView.elixirSpecialString!=null) {
                weaponView.armorElixirSpecial.text = hatView.elixirSpecialString
                weaponView.armorElixirSpecial.visibility = View.VISIBLE
            }
        }
    }

    fun setArmorsBackGroudColor(view:CharSearchAccessoryView){
        val quality = view.accessoryQuality.text.toString().toInt()
        when{
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

    fun setArmorsBackGroundColor(view:CharSearchArmorView){
        val quality = view.armorQuality.text.toString().toInt()
        when{
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

    fun toolTipDeserialization(armory: ArmoryEquipment?): Tooltip? {
        if(armory == null)
            return null
        val gson = GsonBuilder()
            .registerTypeAdapter(ValueData::class.java, ValueDataAdapter())
            .create()
        val pattern = "<.*?>".toRegex()
        val replaceTooltip = armory.tooltip.replace(pattern,"")
        val jsonString = "{\n\"Elements\":\n" + replaceTooltip + "\n}"
        println(jsonString)
        return gson.fromJson(jsonString, Tooltip::class.java)
    }

}