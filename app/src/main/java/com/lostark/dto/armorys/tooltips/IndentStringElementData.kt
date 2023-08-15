package com.lostark.dto.armorys.tooltips

import com.google.gson.annotations.SerializedName

data class IndentStringElementData(
    @SerializedName("contentStr")
    val contentStrData:ContentStrData,
    @SerializedName("topStr")
    val topStr:String
)
