package com.lostark.dto.armorys.tooltips

import com.google.gson.annotations.SerializedName

data class ContentStrData(
    @SerializedName("Element_000")
    val element0: ElementData,
    @SerializedName("Element_001")
    val element1: ElementData,
    @SerializedName("Element_002")
    val element2: ElementData,
    @SerializedName("Element_003")
    val element3: ElementData,
    @SerializedName("Element_004")
    val element4: ElementData,
    @SerializedName("Element_005")
    val element5: ElementData
)
