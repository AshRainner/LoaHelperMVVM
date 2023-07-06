package com.lostark.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArmorySkill(
    @SerializedName("Icon")
    val icon: String,
    @SerializedName("IsAwakening")
    val isAwakening: Boolean,
    @SerializedName("Level")
    val level: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Rune")
    val rune: Rune,
    @SerializedName("Tooltip")
    val tooltip: String,
    @SerializedName("Tripods")
    val tripods: List<Tripod>,
    @SerializedName("Type")
    val type: String
): Serializable