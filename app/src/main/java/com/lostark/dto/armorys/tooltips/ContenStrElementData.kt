package com.lostark.dto.armorys.tooltips

import com.google.gson.annotations.SerializedName

data class ContenStrElementData(
    @SerializedName("bPoint")
    val bPoint:Any,
    @SerializedName("contentStr")
    val contentStr:String
)
