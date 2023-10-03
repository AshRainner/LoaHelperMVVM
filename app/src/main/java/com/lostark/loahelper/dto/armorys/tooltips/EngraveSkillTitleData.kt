package com.lostark.loahelper.dto.armorys.tooltips

import com.google.gson.annotations.SerializedName

data class EngraveSkillTitleData(
    @SerializedName("forceMiddleText")
    val forceMiddleText:String,
    @SerializedName("leftText")
    val leftText:String,
    @SerializedName("name")
    val name:String,
    @SerializedName("rightText")
    val rightText:String,
    @SerializedName("value")
    val value: com.lostark.loahelper.dto.armorys.tooltips.SlotData
)
