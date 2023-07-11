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
        val type = jsonObject.get("type").asString
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
            else->{
                jsonObject.get("value").asString
            }
        }
        return ValueData(type,value)
    }
}