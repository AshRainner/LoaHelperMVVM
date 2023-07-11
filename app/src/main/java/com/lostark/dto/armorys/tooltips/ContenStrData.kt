package com.lostark.dto.armorys.tooltips

import com.google.gson.annotations.SerializedName

data class ContenStrData(
    @SerializedName("Element_000")
    val Element_000: ContenStrElementData,
    @SerializedName("Element_001")
    val Element_001: ContenStrElementData,
    @SerializedName("Element_002")
    val Element_002: ContenStrElementData
)
