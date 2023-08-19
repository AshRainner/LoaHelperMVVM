package com.lostark.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.lostark.dto.armorys.tooltips.*
import java.lang.reflect.Type

class ValueDataAdapter : JsonDeserializer<ValueData<*>> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ValueData<*> {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type")?.asString?:""
        val value = when(type){
            "ItemTitle" ->{
                val itemTitleData = context?.deserialize<ItemTitleData>(
                    jsonObject.get("value"),
                    ItemTitleData::class.java
                )
                itemTitleData
            }
            "ItemPartBox"->{
                val itemPartData = context?.deserialize<ItemPartData>(
                    jsonObject.get("value"),
                    ItemPartData::class.java
                )
                itemPartData
            }
            "Progress"->{
                "프로그레스"
            }
            "IndentStringGroup"->{
                val indentStringGroupData = context?.deserialize<IndentStringGroupData>(
                    jsonObject.get("value"),
                    IndentStringGroupData::class.java
                )
                indentStringGroupData
            }
            "EngraveSkillTitle"->{
                val engraveSkillTitle = context?.deserialize<EngraveSkillTitleData>(
                    jsonObject.get("value"),
                    EngraveSkillTitleData::class.java
                )
                engraveSkillTitle
            }
            "Card"->{
                val card = context?.deserialize<CardData>(
                    jsonObject.get("value"),
                    CardData::class.java
                )
                card
            }
            "CommonSkillTitle"->{
                val commonSkillTitle = context?.deserialize<CommonSkillTitleData>(
                    jsonObject.get("value"),
                    CommonSkillTitleData::class.java
                )
                commonSkillTitle
            }
            "TripodSkillCustom"->{
                val tripodSkillCustom = context?.deserialize<TripodSkillCustomData>(
                    jsonObject.get("value"),
                    TripodSkillCustomData::class.java
                )
                tripodSkillCustom
            }
            "SymbolString"->{
                val symbolString = context?.deserialize<SymbolStringData>(
                    jsonObject.get("value"),
                    SymbolStringData::class.java
                )
                symbolString
            }
            "AvatarAttribute"->{
                "아바타"
            }
            else->{
                jsonObject.get("value")?.asString?:""
                /*inner = jsonObject.get("IsInner")?.asBoolean?:false
                isSet = jsonObject.get("IsSet")?.asBoolean?:false*/
            }
        }

        return ValueData(type,value)
    }
}