package com.lostark.loahelper.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArmoryGem(
    @SerializedName("Effects")
    val effects: List<com.lostark.loahelper.dto.armorys.Effect>,
    @SerializedName("Gems")
    val gems: List<com.lostark.loahelper.dto.armorys.Gem>
): Serializable