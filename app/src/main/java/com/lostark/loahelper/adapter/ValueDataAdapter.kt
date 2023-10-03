package com.lostark.loahelper.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ValueDataAdapter : JsonDeserializer<com.lostark.loahelper.dto.armorys.tooltips.ValueData<*>> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): com.lostark.loahelper.dto.armorys.tooltips.ValueData<*> {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type")?.asString?:""
        val value = when(type){
            "ItemTitle" ->{
                val itemTitleData = context?.deserialize<com.lostark.loahelper.dto.armorys.tooltips.ItemTitleData>(
                    jsonObject.get("value"),
                    com.lostark.loahelper.dto.armorys.tooltips.ItemTitleData::class.java
                )
                itemTitleData
            }
            "ItemPartBox"->{
                val itemPartData = context?.deserialize<com.lostark.loahelper.dto.armorys.tooltips.ItemPartData>(
                    jsonObject.get("value"),
                    com.lostark.loahelper.dto.armorys.tooltips.ItemPartData::class.java
                )
                itemPartData
            }
            "Progress"->{
                "프로그레스"
            }
            "IndentStringGroup"->{
                val indentStringGroupData = context?.deserialize<com.lostark.loahelper.dto.armorys.tooltips.IndentStringGroupData>(
                    jsonObject.get("value"),
                    com.lostark.loahelper.dto.armorys.tooltips.IndentStringGroupData::class.java
                )
                indentStringGroupData
            }
            "EngraveSkillTitle"->{
                val engraveSkillTitle = context?.deserialize<com.lostark.loahelper.dto.armorys.tooltips.EngraveSkillTitleData>(
                    jsonObject.get("value"),
                    com.lostark.loahelper.dto.armorys.tooltips.EngraveSkillTitleData::class.java
                )
                engraveSkillTitle
            }
            "Card"->{
                val card = context?.deserialize<com.lostark.loahelper.dto.armorys.tooltips.CardData>(
                    jsonObject.get("value"),
                    com.lostark.loahelper.dto.armorys.tooltips.CardData::class.java
                )
                card
            }
            "CommonSkillTitle"->{
                val commonSkillTitle = context?.deserialize<com.lostark.loahelper.dto.armorys.tooltips.CommonSkillTitleData>(
                    jsonObject.get("value"),
                    com.lostark.loahelper.dto.armorys.tooltips.CommonSkillTitleData::class.java
                )
                commonSkillTitle
            }
            "TripodSkillCustom"->{
                val tripodSkillCustom = context?.deserialize<com.lostark.loahelper.dto.armorys.tooltips.TripodSkillCustomData>(
                    jsonObject.get("value"),
                    com.lostark.loahelper.dto.armorys.tooltips.TripodSkillCustomData::class.java
                )
                tripodSkillCustom
            }
            "SymbolString"->{
                val symbolString = context?.deserialize<com.lostark.loahelper.dto.armorys.tooltips.SymbolStringData>(
                    jsonObject.get("value"),
                    com.lostark.loahelper.dto.armorys.tooltips.SymbolStringData::class.java
                )
                symbolString
            }
            "AvatarAttribute"->{
                "아바타"
            }
            "SetItemGroup"->{
                "세트아이템"
            }
            else->{
                jsonObject.get("value")?.asString?:""
                /*inner = jsonObject.get("IsInner")?.asBoolean?:false
                isSet = jsonObject.get("IsSet")?.asBoolean?:false*/
            }
        }

        return com.lostark.loahelper.dto.armorys.tooltips.ValueData(type, value)
    }
}