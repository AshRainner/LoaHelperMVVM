package com.lostark.dto.armorys.tooltips

import com.google.gson.annotations.SerializedName

data class IndentStringGroupData(
    @SerializedName("Element_000")
    val element0: IndentStringGroupElementData,
    @SerializedName("Element_001")
    val element1: IndentStringGroupElementData
)
