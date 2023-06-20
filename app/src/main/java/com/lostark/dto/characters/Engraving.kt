package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName

data class Engraving(
    @SerializedName("Icon")
    val icon: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Slot")
    val slot: Int,
    @SerializedName("Tooltip")
    val tooltip: String
)