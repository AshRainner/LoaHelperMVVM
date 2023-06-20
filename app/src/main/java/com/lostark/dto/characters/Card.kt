package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("AwakeCount")
    val awakeCount: Int,
    @SerializedName("AwakeTotal")
    val awakeTotal: Int,
    @SerializedName("Grade")
    val grade: String,
    @SerializedName("Icon")
    val icon: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Slot")
    val slot: Int,
    @SerializedName("Tooltip")
    val tooltip: String
)