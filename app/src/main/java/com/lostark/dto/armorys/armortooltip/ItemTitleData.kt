package com.lostark.dto.armorys.armortooltip

import com.google.gson.annotations.SerializedName

data class ItemTitleData(
    @SerializedName("bEquip")
    val bEquip:Int,
    @SerializedName("leftStr0")
    val leftStr0:String,
    @SerializedName("leftStr1")
    val leftStr1:String,
    @SerializedName("leftStr2")
    val leftStr2:String,
    @SerializedName("qualityValue")
    val qualityValue:Int,
    @SerializedName("rightStr0")
    val rightStr0:String,
    @SerializedName("value")
    val value: SlotData
)
