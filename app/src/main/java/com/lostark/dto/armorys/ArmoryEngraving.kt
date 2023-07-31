package com.lostark.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArmoryEngraving(
    @SerializedName("Effects")
    val effects: List<Effect>,//val effects: List<EffectX>
    @SerializedName("Engravings")
    val engravings: List<Engraving>
): Serializable