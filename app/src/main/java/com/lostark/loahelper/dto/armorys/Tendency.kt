package com.lostark.loahelper.dto.armorys


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Tendency(
    @SerializedName("MaxPoint")
    val maxPoint: Int,
    @SerializedName("Point")
    val point: Int,
    @SerializedName("Type")
    val type: String
): Serializable