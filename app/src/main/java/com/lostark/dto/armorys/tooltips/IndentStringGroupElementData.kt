package com.lostark.dto.armorys.tooltips

import com.google.gson.annotations.SerializedName

data class IndentStringGroupElementData(
    @SerializedName("contentStr")
    val contentStr:ContenStrData,
    @SerializedName("topStr")
    val topStr:String
)
