package com.lostark.dto.armorys.tooltips


import com.google.gson.annotations.SerializedName

data class TripodSkillCustomData(
    @SerializedName("Element_000")
    val element0: ElementData,
    @SerializedName("Element_001")
    val element1: ElementData,
    @SerializedName("Element_002")
    val element2: ElementData
)