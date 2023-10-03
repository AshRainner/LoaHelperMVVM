package com.lostark.loahelper.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArmoryEngraving(
    @SerializedName("Effects")
    val effects: List<com.lostark.loahelper.dto.armorys.Effect>,//val effects: List<EffectX>
    @SerializedName("Engravings")
    val engravings: List<com.lostark.loahelper.dto.armorys.Engraving>
): Serializable