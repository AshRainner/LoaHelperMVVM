package com.lostark.dto.armorys.tooltips


import com.google.gson.annotations.SerializedName

data class CommonSkillTitleData(
    @SerializedName("leftText")
    val leftText: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("middleText")
    val middleText: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("slotData")
    val slotData: SlotData
)