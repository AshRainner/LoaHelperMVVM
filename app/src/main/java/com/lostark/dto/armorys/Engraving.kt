package com.lostark.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Engraving(
    @SerializedName("Icon")
    val icon: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Slot")
    val slot: Int,
    @SerializedName("Tooltip")
    val tooltip: String
): Serializable