package com.lostark.dto.armorys.armortooltip

import com.google.gson.annotations.SerializedName

data class ItemPartData(
    @SerializedName("Element_000")
    val element0:String,
    @SerializedName("Element_001")
    val element1:String
)
