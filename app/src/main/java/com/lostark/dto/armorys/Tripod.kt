package com.lostark.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Tripod(
    @SerializedName("Icon")
    val icon: String,
    @SerializedName("IsSelected")
    val isSelected: Boolean,
    @SerializedName("Level")
    val level: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Slot")
    val slot: Int,
    @SerializedName("Tier")
    val tier: Int,
    @SerializedName("Tooltip")
    val tooltip: String
): Serializable