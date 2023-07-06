package com.lostark.dto.armorys.armortooltip

import com.google.gson.annotations.SerializedName

data class IndentStringGroupElementData(
    @SerializedName("contentStr")
    val contentStr:ContenStrData,
    @SerializedName("topStr")
    val topStr:String
)
