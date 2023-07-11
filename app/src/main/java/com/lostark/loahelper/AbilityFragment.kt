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
import com.lostark.customview.CharSearchEngravingBookView
import com.lostark.dto.armorys.Armories
import com.lostark.dto.armorys.ArmoryEngraving
import com.lostark.dto.armorys.ArmoryEquipment
import com.lostark.dto.armorys.Engraving
import com.lostark.dto.armorys.tooltips.Tooltip
import com.lostark.dto.armorys.tooltips.ValueData


class AbilityFragment(private val charInfo:Armories) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.char_search_detail_ability_fragment,container,false)

        setArmors(view)
        setAccessory(view)
        setEngraving(view)

        return view
    }

    fun setEngraving(view:View){
        val engraving1View = view.findViewById<CharSearchEngravingBookView>(R.id.char_search_detail_ability_engraving1)
        val engraving2View = view.findViewById<CharSearchEngravingBookView>(R.id.char_search_detail_ability_engraving2)

        var check = true


        charInfo.armoryEngraving?.engravings?.forEach{
            if(check){
                setEquipmentImageText(engraving1View,"0")
                check=false
            }
            else setEquipmentImageText(engraving2View,"1")
        }

    }

    fun setAccessory(view:View){
        val necklaceView = view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_necklace)

        setEquipmentImageText(necklaceView,"목걸이")

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
            setBackGroudColor(earring1View)
        }

        earring2Tooltip?.let {
            earring2View.setAccessoryImageText(earringList.get(1),earring2Tooltip!!)
            setBackGroudColor(earring2View)
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
            setBackGroudColor(ring1View)
        }

        ring2Tooltip?.let {
            ring2View.setAccessoryImageText(ringList.get(1),ring2Tooltip!!)
            setBackGroudColor(ring2View)
        }

        val braceletView = view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_bracelet)
        setEquipmentImageText(braceletView,"팔찌")

        val stonetView = view.findViewById<CharSearchAccessoryView>(R.id.char_search_detail_ability_stone)
        setEquipmentImageText(stonetView,"어빌리티 스톤")

    }

    fun setEquipmentImageText(view:Any, type:String){
            when (view){
                is CharSearchArmorView ->{
                    val tooltip = toolTipDeserialization(charInfo.armoryEquipment.find { it.type==type })
                    tooltip?.let {
                        view.setArmorImageText(charInfo.armoryEquipment.find { it.type==type }!!,tooltip)
                        setBackGroudColor(view)
                    }

                }
                is CharSearchAccessoryView ->{
                    val tooltip = toolTipDeserialization(charInfo.armoryEquipment.find { it.type==type })
                    tooltip?.let {
                        view.setAccessoryImageText(charInfo.armoryEquipment.find { it.type==type }!!,tooltip)
                        setBackGroudColor(view)
                    }

                }
                is CharSearchEngravingBookView->{
                    val tooltip = toolTipDeserialization(charInfo.armoryEngraving.engravings.get(type.toInt()))
                    tooltip?.let {
                        view.setEngravingImageText(charInfo.armoryEngraving.engravings.get(type.toInt()),tooltip)
                    }
                }
            }
    }

    fun setArmors(view:View){

        val hatView = view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_hat)
        setEquipmentImageText(hatView,"투구")

        val shoulderView = view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_shoulder)
        setEquipmentImageText(shoulderView,"어깨")

        val topView = view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_top)
        setEquipmentImageText(topView,"상의")

        val bottomView = view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_bottom)
        setEquipmentImageText(bottomView,"하의")

        val glovesView = view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_gloves)
        setEquipmentImageText(glovesView,"장갑")

        val weaponView = view.findViewById<CharSearchArmorView>(R.id.char_search_detail_ability_weapon)
        setEquipmentImageText(weaponView,"무기")

        hatView?.let {
            if(hatView.elixirSpecialString!=null) {
                weaponView.armorElixirSpecial.text = hatView.elixirSpecialString
                weaponView.armorElixirSpecial.visibility = View.VISIBLE
            }
        }
        hatView.setOnClickListener{
            (activity as SearchDetailActivity).openArmorDialog()
        }
    }

    fun setBackGroudColor(view:Any){
        when (view){
            is CharSearchAccessoryView->{
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
            is CharSearchArmorView->{
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
                else -> return null
            }?.replace(pattern2,"\n")?.replace(pattern, "")
        }
        val jsonString = "{\n\"Elements\":\n${tooltips.joinToString(separator = ",\n")}\n}"
        println(jsonString)
        return gson.fromJson(jsonString, Tooltip::class.java)
    }

}