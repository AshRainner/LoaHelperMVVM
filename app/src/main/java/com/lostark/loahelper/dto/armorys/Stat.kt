package com.lostark.loahelper.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Stat(
    @SerializedName("Tooltip")
    val tooltip: List<String>,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Value")
    val value: String
): Serializable