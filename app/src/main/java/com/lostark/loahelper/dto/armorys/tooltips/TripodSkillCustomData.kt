package com.lostark.loahelper.dto.armorys.tooltips


import com.google.gson.annotations.SerializedName

data class TripodSkillCustomData(
    @SerializedName("Element_000")
    val element0: com.lostark.loahelper.dto.armorys.tooltips.ElementData,
    @SerializedName("Element_001")
    val element1: com.lostark.loahelper.dto.armorys.tooltips.ElementData,
    @SerializedName("Element_002")
    val element2: com.lostark.loahelper.dto.armorys.tooltips.ElementData
)