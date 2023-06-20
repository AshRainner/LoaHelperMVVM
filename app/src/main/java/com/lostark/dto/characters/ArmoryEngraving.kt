package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName

data class ArmoryEngraving(
    @SerializedName("Effects")
    val effects: List<EffectX>,
    @SerializedName("Engravings")
    val engravings: List<Engraving>
)