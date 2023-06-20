package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("Tooltip")
    val tooltip: List<String>,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Value")
    val value: String
)