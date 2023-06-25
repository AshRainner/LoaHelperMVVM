package com.lostark.dto.characters


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EffectXX(
    @SerializedName("Description")
    val description: String,
    @SerializedName("GemSlot")
    val gemSlot: Int,
    @SerializedName("Icon")
    val icon: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Tooltip")
    val tooltip: String
): Serializable