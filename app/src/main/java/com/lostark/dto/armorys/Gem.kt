package com.lostark.dto.armorys


import com.google.gson.annotations.SerializedName
import com.lostark.dto.armorys.tooltips.Tooltip
import java.io.Serializable

data class Gem(
    @SerializedName("Grade")
    val grade: String,
    @SerializedName("Icon")
    val icon: String,
    @SerializedName("Level")
    val level: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Slot")
    val slot: Int,
    @SerializedName("Tooltip")
    val tooltip: String
): Serializable